package com.wego.services;

import com.ning.http.client.AsyncCompletionHandlerBase;
import com.ning.http.client.Request;

public interface AsyncHttpCachedService {
  void executeRequest(Request request, AsyncCompletionHandlerBase handler) throws Exception;
}
