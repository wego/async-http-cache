package com.wego.httpcache.dao.models;

import com.ning.http.client.FluentCaseInsensitiveStringsMap;
import com.ning.http.client.Response;
import com.ning.http.client.cookie.Cookie;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.nio.ByteBuffer;
import java.util.List;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;

public class CachedResponse implements Response {
  private String id;
  private String responseBody;
  private List<Cookie> cookies;
  private FluentCaseInsensitiveStringsMap headers;
  private Integer statusCode;
  private String statusText;

  public int getStatusCode() {
    return statusCode != null ? statusCode : 0;
  }

  public String getStatusText() {
    return statusText;
  }

  public byte[] getResponseBodyAsBytes() throws IOException {
    return new byte[0];
  }

  public ByteBuffer getResponseBodyAsByteBuffer() throws IOException {
    return null;
  }

  public InputStream getResponseBodyAsStream() throws IOException {
    return new ByteArrayInputStream(responseBody.getBytes());
  }

  public String getResponseBodyExcerpt(int maxLength, String charset) throws IOException {
    return null;
  }

  public String getResponseBodyExcerpt(int maxLength) throws IOException {
    return null;
  }

  public String getResponseBody(String charset) throws IOException {
    return null;
  }

  public String getResponseBody() throws IOException {
    return responseBody;
  }

  public URI getUri() throws MalformedURLException {
    return null;
  }

  public String getContentType() {
    return null;
  }

  public String getHeader(String name) {
    return headers.getFirstValue(name);
  }

  public List<String> getHeaders(String name) {
    return headers.get(name);
  }

  public FluentCaseInsensitiveStringsMap getHeaders() {
    return headers;
  }

  public boolean isRedirected() {
    return false;
  }

  public List<Cookie> getCookies() {
    return cookies;
  }

  public boolean hasResponseStatus() {
    return statusCode > 0;
  }

  public boolean hasResponseHeaders() {
    return MapUtils.isNotEmpty(headers);
  }

  public boolean hasResponseBody() {
    return StringUtils.isNotEmpty(responseBody);
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

    public Builder(Response response) {
      this.cookies = response.getCookies();
      if (response.hasResponseBody()) {
        try {
          this.responseBody = response.getResponseBody();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
      this.headers = response.getHeaders();
      this.statusCode = response.getStatusCode();
      this.statusText = response.getStatusText();
    }

    public Builder setCookies(List<Cookie> cookies) {
      this.cookies = cookies;
      return this;
    }

    public Builder setResponseBody(String responseBody) {
      this.responseBody = responseBody;
      return this;
    }

    public Builder setId(String id) {
      this.id = id;
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

    public CachedResponse build() {
      CachedResponse cachedResponse = new CachedResponse();
      cachedResponse.id = this.id;
      cachedResponse.responseBody = this.responseBody;
      cachedResponse.cookies = this.cookies;
      cachedResponse.headers = this.headers;
      cachedResponse.statusCode = this.statusCode;
      cachedResponse.statusText = this.statusText;
      return cachedResponse;
    }
  }
}
