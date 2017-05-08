package com.wego.httpcache.services.factories;

import com.ning.http.client.AsyncHttpClient;
import com.wego.httpcache.services.AsyncHttpCacheService;

public interface AsyncHttpServiceFactory {
  AsyncHttpCacheService create(long ttl);

  AsyncHttpCacheService create(AsyncHttpClient asyncHttpClient, long ttl);
}
