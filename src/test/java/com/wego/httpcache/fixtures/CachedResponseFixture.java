package com.wego.httpcache.fixtures;

import com.wego.httpcache.dao.models.CachedResponse;
import java.util.Arrays;

public class CachedResponseFixture {
  public static CachedResponse create() {
    return new CachedResponse.Builder()
        .setId("ID")
        .setResponseBody("Body")
        .setStatusCode(200)
        .setStatusText("")
        .setHeaders(HeaderFixture.create())
        .setCookies(Arrays.asList(CookieFixture.create()))
        .build();
  }
}
