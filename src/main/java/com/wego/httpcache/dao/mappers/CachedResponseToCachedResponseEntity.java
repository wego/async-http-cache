package com.wego.httpcache.dao.mappers;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.wego.httpcache.dao.entities.CachedResponseEntity;
import com.wego.httpcache.dao.models.CachedResponse;
import java.io.IOException;

@Singleton
public class CachedResponseToCachedResponseEntity {

  @Inject
  private CookieToCookieEntity cookieToCookieEntity;

  public CachedResponseEntity transform(CachedResponse cachedResponse) throws IOException {
    CachedResponseEntity cachedResponseEntity = null;

    if (cachedResponse != null) {
      cachedResponseEntity =
          new CachedResponseEntity.Builder()
              .setId(cachedResponse.getId())
              .setResponseBody(cachedResponse.getResponseBody())
              .setCookies(cookieToCookieEntity.transform(cachedResponse.getCookies()))
              .setHeaders(cachedResponse.getHeaders())
              .setStatusText(cachedResponse.getStatusText())
              .setStatusCode(cachedResponse.getStatusCode())
              .build();
    }

    return cachedResponseEntity;
  }
}
