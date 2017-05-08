package com.wego.httpcache.dao.mappers;

import com.wego.httpcache.dao.entities.CachedResponseEntity;
import com.wego.httpcache.dao.models.CachedResponse;

public class CachedResponseEntityToCachedResponse {

  public CachedResponse transform(CachedResponseEntity cachedResponseEntity) {
    CachedResponse cachedResponse = null;

    if (cachedResponseEntity != null) {
      cachedResponse =
          new CachedResponse.Builder()
              .setId(cachedResponseEntity.getId())
              .setStatusCode(cachedResponseEntity.getStatusCode())
              .setStatusText(cachedResponseEntity.getStatusText())
              .setHeaders(cachedResponseEntity.getHeaders())
              .setCookies(cachedResponseEntity.getCookies())
              .setResponseBody(cachedResponseEntity.getResponseBody())
              .build();
    }

    return cachedResponse;
  }
}
