package com.wego.httpcache.fixtures;

import com.wego.httpcache.dao.entities.CookieEntity;
import org.apache.commons.lang3.StringUtils;

public class CookieEntityFixture {
  public static CookieEntity create() {
    return createWithKeyAndValue("key", "value");
  }

  public static CookieEntity createWithKeyAndValue(String key, String value) {
    return new CookieEntity.Builder()
        .setName(key)
        .setValue(value)
        .setRawValue(StringUtils.join(key, "=", value))
        .setDomain("wego.com")
        .setPath("./")
        .setExpires(1)
        .setMaxAge(2)
        .setSecure(true)
        .setHttpOnly(true)
        .build();
  }
}
