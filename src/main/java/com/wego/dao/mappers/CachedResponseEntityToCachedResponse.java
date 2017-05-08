package com.wego.dao.mappers;

import com.wego.dao.entities.CachedResponseEntity;
import com.wego.dao.models.CachedResponse;

public class CachedResponseEntityToCachedResponse {

  public CachedResponse transform(CachedResponseEntity cachedResponseEntity) {
    return new CachedResponse.Builder()
        .setId(cachedResponseEntity.getId())
        .setStatusCode(cachedResponseEntity.getStatusCode())
        .setStatusText(cachedResponseEntity.getStatusText())
        .setHeaders(cachedResponseEntity.getHeaders())
        .setCookies(cachedResponseEntity.getCookies())
        .setResponseBody(cachedResponseEntity.getResponseBody())
        .build();
  }
}
