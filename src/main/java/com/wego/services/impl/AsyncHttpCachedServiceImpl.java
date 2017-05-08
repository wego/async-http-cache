package com.wego.services.impl;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.ning.http.client.AsyncCompletionHandlerBase;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.Request;
import com.ning.http.client.Response;
import com.wego.dao.models.CachedResponse;
import com.wego.services.AsyncHttpCachedService;
import com.wego.services.CachedResponseService;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
import redis.clients.util.MurmurHash;

public class AsyncHttpCachedServiceImpl implements AsyncHttpCachedService {

  @Inject private CachedResponseService cachedResponseService;

  @Inject private AsyncHttpClient asyncHttpClient;

  @Inject
  public AsyncHttpCachedServiceImpl() {}

  @Inject
  public AsyncHttpCachedServiceImpl(@Assisted AsyncHttpClient asyncHttpClient) {
    this.asyncHttpClient = asyncHttpClient;
  }

  @Override
  public void executeRequest(Request request, AsyncCompletionHandlerBase handler) throws Exception {
    String responseId = buildResponseId(request);

    Optional<CachedResponse> cachedResponse = cachedResponseService.findById(responseId);

    if (cachedResponse.isPresent()) {
      handler.onCompleted(cachedResponse.get());
    } else {
      this.asyncHttpClient.executeRequest(request, buildCachingHandler(handler, responseId));
    }
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
      final AsyncCompletionHandlerBase handler, final String responseId) {
    return new AsyncCompletionHandlerBase() {
      @Override
      public Response onCompleted(Response response) throws Exception {
        CachedResponse cachedResponse =
            new CachedResponse.Builder(response).setId(responseId).build();
        cachedResponseService.save(cachedResponse);

        return handler.onCompleted(response);
      }

      @Override
      public void onThrowable(Throwable e) {
        handler.onThrowable(e);
      }
    };
  }
}
