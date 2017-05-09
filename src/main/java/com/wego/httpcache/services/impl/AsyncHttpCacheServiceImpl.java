package com.wego.httpcache.services.impl;

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
import org.apache.commons.lang3.StringUtils;
import redis.clients.util.MurmurHash;

public class AsyncHttpCacheServiceImpl implements AsyncHttpCacheService {

  @Inject private CachedResponseService cachedResponseService;

  @Inject private AsyncHttpClient asyncHttpClient;

  private long ttl;

  @Inject
  public AsyncHttpCacheServiceImpl(@Assisted AsyncHttpClient asyncHttpClient, @Assisted long ttl) {
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
      responseListenableFuture =
          this.asyncHttpClient.executeRequest(
              request, buildCachingHandler(handler, responseId, ttl));
    }

    return Optional.ofNullable(responseListenableFuture);
  }

  private String buildResponseId(Request request) {
    String requestStringId =
        StringUtils.join(
            request,
            request.getBodyEncoding(),
            Lists.newArrayList(request.getCookies()).toString());
    return String.valueOf(MurmurHash.hash64A(requestStringId.getBytes(), 0));
  }

  private AsyncCompletionHandlerBase buildCachingHandler(
      final AsyncCompletionHandlerBase handler, final String responseId, final long cachingTtl) {

    return new AsyncCompletionHandlerBase() {
      @Override
      public Response onCompleted(Response response) throws Exception {
        CachedResponse cachedResponse =
            new CachedResponse.Builder(response).setId(responseId).build();
        cachedResponseService.save(cachedResponse, cachingTtl);

        return handler.onCompleted(response);
      }

      @Override
      public void onThrowable(Throwable e) {
        handler.onThrowable(e);
      }
    };
  }
}
