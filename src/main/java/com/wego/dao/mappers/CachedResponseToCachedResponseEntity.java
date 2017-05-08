package com.wego.dao.mappers;

import com.wego.dao.entities.CachedResponseEntity;
import com.wego.dao.models.CachedResponse;
import java.io.IOException;

public class CachedResponseToCachedResponseEntity {

  public CachedResponseEntity transform(CachedResponse cachedResponse) {
    CachedResponseEntity cachedResponseEntity = null;
    String responseBody = null;

    try {
      responseBody = cachedResponse.getResponseBody();
    } catch (IOException e) {
      e.printStackTrace();
    }

    cachedResponseEntity =
        new CachedResponseEntity.Builder()
            .setResponseBody(cachedResponse.getId())
            .setResponseBody(responseBody)
            .setCookies(cachedResponse.getCookies())
            .setHeaders(cachedResponse.getHeaders())
            .setStatusText(cachedResponse.getStatusText())
            .setStatusCode(cachedResponse.getStatusCode())
            .build();

    return cachedResponseEntity;
  }
}
