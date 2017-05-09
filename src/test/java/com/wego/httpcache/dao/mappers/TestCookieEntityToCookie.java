package com.wego.httpcache.dao.mappers;

import static org.assertj.core.api.Assertions.assertThat;

import com.ning.http.client.cookie.Cookie;
import com.wego.httpcache.dao.entities.CookieEntity;
import com.wego.httpcache.fixtures.CookieEntityFixture;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;

public class TestCookieEntityToCookie {
  private CookieEntityToCookie cookieEntityToCookie = new CookieEntityToCookie();

  @Test
  public void transform_whenInputIsNull_returnsNull() throws Exception {
    CookieEntity cookieEntity = null;
    assertThat(cookieEntityToCookie.transform(cookieEntity)).isEqualTo(null);
  }

  @Test
  public void transform_whenValidInput_returnsCookieEntity() throws Exception {
    CookieEntity cookieEntity = CookieEntityFixture.create();

    Cookie cookie = cookieEntityToCookie.transform(cookieEntity);

    assertThat(cookie.getName()).isEqualTo(cookieEntity.getName());
    assertThat(cookie.getValue()).isEqualTo(cookieEntity.getValue());
    assertThat(cookie.getRawValue()).isEqualTo(cookieEntity.getRawValue());
    assertThat(cookie.getDomain()).isEqualTo(cookieEntity.getDomain());
    assertThat(cookie.getPath()).isEqualTo(cookieEntity.getPath());
    assertThat(cookie.getExpires()).isEqualTo(cookieEntity.getExpires());
    assertThat(cookie.getMaxAge()).isEqualTo(cookieEntity.getMaxAge());
    assertThat(cookie.isSecure()).isEqualTo(cookieEntity.isSecure());
    assertThat(cookie.isHttpOnly()).isEqualTo(cookieEntity.isHttpOnly());
  }

  @Test
  public void transform_returnsListOfCookieEntity() throws Exception {
    CookieEntity cookieEntity = CookieEntityFixture.create();

    List<Cookie> cookies = cookieEntityToCookie.transform(Arrays.asList(cookieEntity));

    assertThat(cookies.size()).isEqualTo(1);
  }
}
