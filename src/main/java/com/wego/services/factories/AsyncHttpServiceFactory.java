package com.wego.services.factories;

import com.ning.http.client.AsyncHttpClient;
import com.wego.services.AsyncHttpCachedService;

public interface AsyncHttpServiceFactory {
  AsyncHttpCachedService create();

  AsyncHttpCachedService create(AsyncHttpClient asyncHttpClient);
}
