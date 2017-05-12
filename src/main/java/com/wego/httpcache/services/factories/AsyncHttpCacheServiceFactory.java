package com.wego.httpcache.services.factories;

import com.ning.http.client.AsyncHttpClient;
import com.wego.httpcache.services.AsyncHttpCacheService;

public interface AsyncHttpCacheServiceFactory {
  AsyncHttpCacheService create(String serviceName, AsyncHttpClient asyncHttpClient, long ttl);
}
