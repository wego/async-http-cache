package com.wego.httpcache.fixtures;

import com.ning.http.client.FluentCaseInsensitiveStringsMap;
import java.util.Collections;

public class HeaderFixture {
  public static FluentCaseInsensitiveStringsMap create() {
    return createWithKeyAndValue("key", "value");
  }

  public static FluentCaseInsensitiveStringsMap createWithKeyAndValue(String key, String value) {
    FluentCaseInsensitiveStringsMap map = new FluentCaseInsensitiveStringsMap();
    map.put(key, Collections.singletonList(value));
    return map;
  }
}
