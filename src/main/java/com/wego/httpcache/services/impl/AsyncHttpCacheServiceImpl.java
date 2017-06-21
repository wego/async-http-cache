package com.wego.httpcache.services.impl;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.ning.http.client.AsyncCompletionHandlerBase;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.ListenableFuture;
import com.ning.http.client.Request;
import com.ning.http.client.Response;
import com.wego.httpcache.dao.models.CachedResponse;
import com.wego.httpcache.services.AsyncHttpCacheService;
import com.wego.httpcache.services.CachedResponseService;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;

import redis.clients.util.MurmurHash;

public class AsyncHttpCacheServiceImpl implements AsyncHttpCacheService {
  private static final String DELIMITER = ":";
  @Inject private CachedResponseService cachedResponseService;
  private String serviceName;
  private AsyncHttpClient asyncHttpClient;
  private long ttl;

  private final Cache<String, ListenableFuture<Response>> cache = CacheBuilder.newBuilder()
      .expireAfterWrite(2, TimeUnit.MINUTES)
      .build();

  @Inject
  public AsyncHttpCacheServiceImpl(
      @Assisted String serviceName, @Assisted AsyncHttpClient asyncHttpClient, @Assisted long ttl) {
    this.serviceName = serviceName;
    this.asyncHttpClient = asyncHttpClient;
    this.ttl = ttl;
  }

  @Override
  public Optional<ListenableFuture<Response>> executeRequest(
      Request request, AsyncCompletionHandlerBase handler) throws Exception {
    return executeRequest(request, handler, ttl);
  }

  @Override
  public Optional<ListenableFuture<Response>> executeRequest(
      Request request, AsyncCompletionHandlerBase handler, long ttl) throws Exception {

    ListenableFuture<Response> responseListenableFuture = null;
    String responseId = buildResponseId(request);

    Optional<CachedResponse> cachedResponse = cachedResponseService.findById(responseId);

    if (cachedResponse.isPresent()) {
      handler.onCompleted(cachedResponse.get());
    } else {
      responseListenableFuture = cache.get(responseId, () -> {
        return this.asyncHttpClient.executeRequest(request,
            buildCachingHandler(handler, responseId, ttl));
      });
      handler.onCompleted(responseListenableFuture.get());
    }

    return Optional.ofNullable(responseListenableFuture);
  }

  private String buildResponseId(Request request) {
    String requestStringId =
        StringUtils.join(
            request, request.getStringData(), Lists.newArrayList(request.getCookies())
                .toString());
    return StringUtils.joinWith(
        DELIMITER, serviceName, String.valueOf(MurmurHash.hash64A(requestStringId.getBytes(), 0)));
  }

  private AsyncCompletionHandlerBase buildCachingHandler(
      final AsyncCompletionHandlerBase handler, final String responseId, final long cachingTtl) {

    return new AsyncCompletionHandlerBase() {
      @Override
      public Response onCompleted(Response response) throws Exception {
        CachedResponse cachedResponse =
            new CachedResponse.Builder(response).setId(responseId)
                .build();
        cachedResponseService.save(cachedResponse, cachingTtl);

        return response;
      }

      @Override
      public void onThrowable(Throwable e) {
        handler.onThrowable(e);
      }
    };
  }
}
