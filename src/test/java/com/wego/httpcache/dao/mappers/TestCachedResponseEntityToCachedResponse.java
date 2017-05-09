package com.wego.httpcache.dao.mappers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyList;
import static org.mockito.Mockito.when;

import com.ning.http.client.cookie.Cookie;
import com.wego.httpcache.dao.entities.CachedResponseEntity;
import com.wego.httpcache.dao.models.CachedResponse;
import com.wego.httpcache.fixtures.CachedResponseEntityFixture;
import com.wego.httpcache.fixtures.CookieFixture;
import java.util.Arrays;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TestCachedResponseEntityToCachedResponse {
  @Mock private CookieEntityToCookie cookieEntityToCookie;

  @InjectMocks
  private CachedResponseEntityToCachedResponse cachedResponseEntityToCachedResponse =
      new CachedResponseEntityToCachedResponse();

  @Test
  public void transform_whenInputParamIsNull_returnsNull() throws Exception {
    assertThat(cachedResponseEntityToCachedResponse.transform(null)).isNull();
  }

  @Test
  public void transform_whenValidInputParam_returnsCachedResponse() throws Exception {
    final CachedResponseEntity cachedResponseEntity = CachedResponseEntityFixture.create();
    final Cookie cookie = CookieFixture.create();

    when(cookieEntityToCookie.transform(anyList())).thenReturn(Arrays.asList(cookie));

    CachedResponse subject = cachedResponseEntityToCachedResponse.transform(cachedResponseEntity);

    assertThat(subject.getId()).isEqualTo(cachedResponseEntity.getId());
    assertThat(subject.getCookies()).contains(cookie);
    assertThat(subject.getHeaders().toString())
        .contains(cachedResponseEntity.getHeaders().toString());
    assertThat(subject.getResponseBody()).isEqualTo(cachedResponseEntity.getResponseBody());
    assertThat(subject.getStatusCode()).isEqualTo(cachedResponseEntity.getStatusCode());
    assertThat(subject.getStatusText()).isEqualTo(cachedResponseEntity.getStatusText());
  }
}
