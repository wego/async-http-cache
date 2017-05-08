package com.wego.dao.entities;

import com.ning.http.client.FluentCaseInsensitiveStringsMap;
import com.ning.http.client.cookie.Cookie;
import java.util.List;

public class CachedResponseEntity {

  private String id;
  private List<Cookie> cookies;
  private String responseBody;
  private FluentCaseInsensitiveStringsMap headers;
  private Integer statusCode;
  private String statusText;

  public List<Cookie> getCookies() {
    return cookies;
  }

  public String getResponseBody() {
    return responseBody;
  }

  public FluentCaseInsensitiveStringsMap getHeaders() {
    return headers;
  }

  public Integer getStatusCode() {
    return statusCode;
  }

  public String getStatusText() {
    return statusText;
  }

  public String getId() {
    return id;
  }

  public static final class Builder {

    private String id;
    private List<Cookie> cookies;
    private String responseBody;
    private FluentCaseInsensitiveStringsMap headers;
    private Integer statusCode;
    private String statusText;

    public Builder() {}

    public Builder setId(String id) {
      this.id = id;
      return this;
    }

    public Builder setCookies(List<Cookie> cookies) {
      this.cookies = cookies;
      return this;
    }

    public Builder setResponseBody(String responseBody) {
      this.responseBody = responseBody;
      return this;
    }

    public Builder setHeaders(FluentCaseInsensitiveStringsMap headers) {
      this.headers = headers;
      return this;
    }

    public Builder setStatusCode(Integer statusCode) {
      this.statusCode = statusCode;
      return this;
    }

    public Builder setStatusText(String statusText) {
      this.statusText = statusText;
      return this;
    }

    public CachedResponseEntity build() {
      CachedResponseEntity cachedResponseEntity = new CachedResponseEntity();
      cachedResponseEntity.statusCode = this.statusCode;
      cachedResponseEntity.headers = this.headers;
      cachedResponseEntity.statusText = this.statusText;
      cachedResponseEntity.cookies = this.cookies;
      cachedResponseEntity.id = this.id;
      cachedResponseEntity.responseBody = this.responseBody;
      return cachedResponseEntity;
    }
  }
}
