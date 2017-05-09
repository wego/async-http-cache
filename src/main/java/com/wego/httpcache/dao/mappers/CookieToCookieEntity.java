package com.wego.httpcache.dao.mappers;

import com.google.inject.Singleton;
import com.ning.http.client.cookie.Cookie;
import com.wego.httpcache.dao.entities.CookieEntity;
import java.util.List;
import java.util.stream.Collectors;

@Singleton
public class CookieToCookieEntity {

  public CookieEntity transform(Cookie cookie) {
    CookieEntity cookieEntity = null;

    if (cookie != null) {
      cookieEntity =
          new CookieEntity.Builder()
              .setName(cookie.getName())
              .setValue(cookie.getValue())
              .setRawValue(cookie.getRawValue())
              .setDomain(cookie.getDomain())
              .setPath(cookie.getPath())
              .setExpires(cookie.getExpires())
              .setMaxAge(cookie.getMaxAge())
              .setSecure(cookie.isSecure())
              .setHttpOnly(cookie.isHttpOnly())
              .build();
    }

    return cookieEntity;
  }

  public List<CookieEntity> transform(List<Cookie> cookies) {
    return cookies.stream().map(this::transform).collect(Collectors.toList());
  }
}
