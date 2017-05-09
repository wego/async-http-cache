package com.wego.httpcache.dao.entities;

public class CookieEntity {
  private String name;
  private String value;
  private String rawValue;
  private String domain;
  private String path;
  private long expires;
  private int maxAge;
  private boolean secure;
  private boolean httpOnly;

  public CookieEntity() {}

  public String getName() {
    return name;
  }

  public String getValue() {
    return value;
  }

  public String getRawValue() {
    return rawValue;
  }

  public String getDomain() {
    return domain;
  }

  public String getPath() {
    return path;
  }

  public int getMaxAge() {
    return maxAge;
  }

  public boolean isSecure() {
    return secure;
  }

  public boolean isHttpOnly() {
    return httpOnly;
  }

  public long getExpires() {
    return expires;
  }

  public static final class Builder {

    private String name;
    private String value;
    private String rawValue;
    private String domain;
    private String path;
    private long expires;
    private int maxAge;
    private boolean secure;
    private boolean httpOnly;

    public Builder() {}

    public Builder setName(String name) {
      this.name = name;
      return this;
    }

    public Builder setValue(String value) {
      this.value = value;
      return this;
    }

    public Builder setRawValue(String rawValue) {
      this.rawValue = rawValue;
      return this;
    }

    public Builder setDomain(String domain) {
      this.domain = domain;
      return this;
    }

    public Builder setPath(String path) {
      this.path = path;
      return this;
    }

    public Builder setExpires(long expires) {
      this.expires = expires;
      return this;
    }

    public Builder setMaxAge(int maxAge) {
      this.maxAge = maxAge;
      return this;
    }

    public Builder setSecure(boolean secure) {
      this.secure = secure;
      return this;
    }

    public Builder setHttpOnly(boolean httpOnly) {
      this.httpOnly = httpOnly;
      return this;
    }

    public CookieEntity build() {
      CookieEntity cookieEntity = new CookieEntity();
      cookieEntity.domain = this.domain;
      cookieEntity.maxAge = this.maxAge;
      cookieEntity.secure = this.secure;
      cookieEntity.httpOnly = this.httpOnly;
      cookieEntity.rawValue = this.rawValue;
      cookieEntity.name = this.name;
      cookieEntity.path = this.path;
      cookieEntity.value = this.value;
      cookieEntity.expires = this.expires;
      return cookieEntity;
    }
  }
}
