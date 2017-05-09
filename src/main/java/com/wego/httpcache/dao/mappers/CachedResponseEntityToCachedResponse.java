package com.wego.httpcache.dao.mappers;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.wego.httpcache.dao.entities.CachedResponseEntity;
import com.wego.httpcache.dao.models.CachedResponse;

@Singleton
public class CachedResponseEntityToCachedResponse {

  @Inject
  private CookieEntityToCookie cookieEntityToCookie;

  public CachedResponse transform(CachedResponseEntity cachedResponseEntity) {
    CachedResponse cachedResponse = null;

    if (cachedResponseEntity != null) {
      cachedResponse =
          new CachedResponse.Builder()
              .setId(cachedResponseEntity.getId())
              .setStatusCode(cachedResponseEntity.getStatusCode())
              .setStatusText(cachedResponseEntity.getStatusText())
              .setHeaders(cachedResponseEntity.getHeaders())
              .setCookies(cookieEntityToCookie.transform(cachedResponseEntity.getCookies()))
              .setResponseBody(cachedResponseEntity.getResponseBody())
              .build();
    }

    return cachedResponse;
  }
}
