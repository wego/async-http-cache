package com.wego.httpcache.dao.mappers;

import com.google.inject.Singleton;
import com.ning.http.client.cookie.Cookie;
import com.wego.httpcache.dao.entities.CookieEntity;
import java.util.List;
import java.util.stream.Collectors;

@Singleton
public class CookieEntityToCookie {

  public Cookie transform(CookieEntity cookieEntity) {
    Cookie cookie = null;

    if (cookieEntity != null) {
      cookie =
          Cookie.newValidCookie(
              cookieEntity.getName(),
              cookieEntity.getValue(),
              cookieEntity.getDomain(),
              cookieEntity.getRawValue(),
              cookieEntity.getPath(),
              cookieEntity.getExpires(),
              cookieEntity.getMaxAge(),
              cookieEntity.isSecure(),
              cookieEntity.isHttpOnly());
    }

    return cookie;
  }

  public List<Cookie> transform(List<CookieEntity> cookieEntities) {
    return cookieEntities.stream().map(this::transform).collect(Collectors.toList());
  }
}
