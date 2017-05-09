package com.wego.httpcache.dao.mappers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyList;
import static org.mockito.Mockito.when;

import com.wego.httpcache.dao.entities.CachedResponseEntity;
import com.wego.httpcache.dao.entities.CookieEntity;
import com.wego.httpcache.dao.models.CachedResponse;
import com.wego.httpcache.fixtures.CachedResponseFixture;
import com.wego.httpcache.fixtures.CookieEntityFixture;
import java.util.Arrays;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TestCachedResponseToCachedResponseEntity {
  @InjectMocks
  private CachedResponseToCachedResponseEntity cachedResponseToCachedResponseEntity =
      new CachedResponseToCachedResponseEntity();

  @Mock private CookieToCookieEntity cookieToCookieEntity;

  @Test
  public void transform_whenInputParamIsNull_returnsNull() throws Exception {
    assertThat(cachedResponseToCachedResponseEntity.transform(null)).isNull();
  }

  @Test
  public void transform_whenValidInputParam_returnsCachedResponseEntity() throws Exception {
    CachedResponse cachedResponse = CachedResponseFixture.create();
    CookieEntity cookieEntity = CookieEntityFixture.create();

    when(cookieToCookieEntity.transform(anyList())).thenReturn(Arrays.asList(cookieEntity));

    CachedResponseEntity cachedResponseEntity =
        cachedResponseToCachedResponseEntity.transform(cachedResponse);

    assertThat(cachedResponseEntity.getId()).isEqualTo(cachedResponse.getId());
    assertThat(cachedResponseEntity.getCookies()).contains(cookieEntity);
    assertThat(cachedResponseEntity.getHeaders().toString())
        .contains(cachedResponse.getHeaders().toString());
    assertThat(cachedResponseEntity.getResponseBody()).isEqualTo(cachedResponse.getResponseBody());
    assertThat(cachedResponseEntity.getStatusCode()).isEqualTo(cachedResponse.getStatusCode());
    assertThat(cachedResponseEntity.getStatusText()).isEqualTo(cachedResponse.getStatusText());
  }
}
