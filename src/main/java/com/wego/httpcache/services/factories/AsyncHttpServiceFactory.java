package com.wego.httpcache.services.factories;

import com.wego.httpcache.services.AsyncHttpCacheService;

public interface AsyncHttpServiceFactory {
  AsyncHttpCacheService create(long ttl);
}
