package com.wego.httpcache.services;

import com.ning.http.client.AsyncCompletionHandlerBase;
import com.ning.http.client.ListenableFuture;
import com.ning.http.client.Request;
import com.ning.http.client.Response;
import java.util.Optional;

public interface AsyncHttpCacheService {
  Optional<ListenableFuture<Response>> executeRequest(
      Request request, AsyncCompletionHandlerBase handler) throws Exception;

  Optional<ListenableFuture<Response>> executeRequest(
      Request request, AsyncCompletionHandlerBase handler, long ttl) throws Exception;
}
