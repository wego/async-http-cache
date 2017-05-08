package com.wego.fixtures;

import com.ning.http.client.Request;
import com.ning.http.client.RequestBuilder;
import com.ning.http.client.cookie.Cookie;
import org.apache.commons.lang3.StringUtils;

public class RequestFixture {
  public static Request create(String method, String url) {
    return new RequestBuilder().setMethod(method).setUrl(url).build();
  }

  public static Request createWithQueryParam(String method, String url, String key, String value) {
    return new RequestBuilder().setMethod(method).setUrl(url).addQueryParameter(key, value).build();
  }

  public static Request createWithHeader(String method, String url, String key, String value) {
    return new RequestBuilder().setMethod(method).setUrl(url).addHeader(key, value).build();
  }

  public static Request createWithCookie(String method, String url, String key, String value) {
    Cookie cookie = new Cookie(key, value, StringUtils.join(key, value), "", "", 1, 2, true, true);
    return new RequestBuilder().setMethod(method).setUrl(url).addCookie(cookie).build();
  }

  public static Request createWithParams(String method, String url, String key, String value) {
    return new RequestBuilder().setMethod(method).setUrl(url).addParameter(key, value).build();
  }

  public static Request createWithFullParams() {
    return new RequestBuilder()
        .setMethod("GET")
        .setUrl("http://localhost:8089/resources/")
        .setHeader("Header", "test")
        .addQueryParameter("queryParam", "test")
        .addParameter("param", "test")
        .addCookie(new Cookie("cookie", "test", "", "", "", 1, 2, true, true))
        .build();
  }
}
