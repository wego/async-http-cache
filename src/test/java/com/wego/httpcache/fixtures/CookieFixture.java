package com.wego.httpcache.fixtures;

import com.ning.http.client.cookie.Cookie;
import org.apache.commons.lang3.StringUtils;

public class CookieFixture {
  public static Cookie create() {
    return createWithKeyAndValue("key", "value");
  }

  public static Cookie createWithKeyAndValue(String key, String value) {
    return new Cookie(
        key, value, StringUtils.join(key, "=", value), "wego.com", "./", 1, 2, true, true);
  }
}
