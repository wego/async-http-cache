package com.wego.httpcache.dao.mappers;

import static org.assertj.core.api.Assertions.assertThat;

import com.ning.http.client.cookie.Cookie;
import com.wego.httpcache.dao.entities.CookieEntity;
import com.wego.httpcache.fixtures.CookieFixture;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;

public class TestCookieToCookieEntity {
  private CookieToCookieEntity cookieToCookieEntity = new CookieToCookieEntity();

  @Test
  public void transform_whenInputIsNull_returnsNull() throws Exception {
    Cookie cookie = null;
    assertThat(cookieToCookieEntity.transform(cookie)).isEqualTo(null);
  }

  @Test
  public void transform_whenValidInput_returnsCookieEntity() throws Exception {
    Cookie cookie = CookieFixture.create();

    CookieEntity cookieEntity = cookieToCookieEntity.transform(cookie);

    assertThat(cookieEntity.getName()).isEqualTo(cookie.getName());
    assertThat(cookieEntity.getValue()).isEqualTo(cookie.getValue());
    assertThat(cookieEntity.getRawValue()).isEqualTo(cookie.getRawValue());
    assertThat(cookieEntity.getDomain()).isEqualTo(cookie.getDomain());
    assertThat(cookieEntity.getPath()).isEqualTo(cookie.getPath());
    assertThat(cookieEntity.getExpires()).isEqualTo(cookie.getExpires());
    assertThat(cookieEntity.getMaxAge()).isEqualTo(cookie.getMaxAge());
    assertThat(cookieEntity.isSecure()).isEqualTo(cookie.isSecure());
    assertThat(cookieEntity.isHttpOnly()).isEqualTo(cookie.isHttpOnly());
  }

  @Test
  public void transform_returnsListOfCookieEntity() throws Exception {
    Cookie cookie = CookieFixture.create();

    List<CookieEntity> cookieEntities = cookieToCookieEntity.transform(Arrays.asList(cookie));

    assertThat(cookieEntities.size()).isEqualTo(1);
  }

}