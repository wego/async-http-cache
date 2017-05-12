package com.wego.httpcache.samples;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.ning.http.client.AsyncCompletionHandlerBase;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.ListenableFuture;
import com.ning.http.client.Request;
import com.ning.http.client.RequestBuilder;
import com.ning.http.client.Response;
import com.wego.httpcache.services.AsyncHttpCacheService;
import com.wego.httpcache.services.factories.AsyncHttpCacheServiceFactory;
import java.io.IOException;
import java.util.Optional;

public class SampleApp {

  public static AsyncHttpCacheService getAsyncHttpCacheService() {
    Injector injector = Guice.createInjector(new SampleModule());

    AsyncHttpCacheServiceFactory asyncHttpCacheServiceFactory =
        injector.getInstance(AsyncHttpCacheServiceFactory.class);

    AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
    return asyncHttpCacheServiceFactory.create("SampleApp", asyncHttpClient, 10000);
  }

  public static void main(String[] args) throws Exception {
    AsyncHttpCacheService asyncHttpCacheService = getAsyncHttpCacheService();

    Request request = new RequestBuilder().setMethod("GET").setUrl("http://wego.com").build();

    AsyncCompletionHandlerBase asyncCompletionHandlerBase =
        new AsyncCompletionHandlerBase() {
          @Override
          public Response onCompleted(Response response) throws IOException {
            System.out.print(response.getStatusCode());
            return response;
          }
        };

    Optional<ListenableFuture<Response>> responseListenableFuture =
        asyncHttpCacheService.executeRequest(request, asyncCompletionHandlerBase);

    responseListenableFuture.get().get();
  }
}
