package com.wego.httpcache.fixtures;

import com.wego.httpcache.dao.entities.CachedResponseEntity;
import java.util.Arrays;

public class CachedResponseEntityFixture {
  public static CachedResponseEntity create() {
    return new CachedResponseEntity.Builder()
        .setId("ID")
        .setResponseBody("Body")
        .setStatusCode(200)
        .setStatusText("Ok")
        .setHeaders(HeaderFixture.create())
        .setCookies(Arrays.asList(CookieEntityFixture.create()))
        .build();
  }
}
