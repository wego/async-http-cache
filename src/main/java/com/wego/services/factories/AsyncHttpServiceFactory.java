package com.wego.services.factories;

import com.ning.http.client.AsyncHttpClient;
import com.wego.services.AsyncHttpCacheService;

public interface AsyncHttpServiceFactory {
  AsyncHttpCacheService create(long ttl);

  AsyncHttpCacheService create(AsyncHttpClient asyncHttpClient, long ttl);
}
