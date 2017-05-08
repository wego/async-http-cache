package com.wego.httpcache.dao.mappers;

import static org.assertj.core.api.Assertions.assertThat;

import com.wego.httpcache.dao.entities.CachedResponseEntity;
import com.wego.httpcache.dao.models.CachedResponse;
import com.wego.httpcache.fixtures.CachedResponseFixture;
import org.junit.Test;

public class TestCachedResponseToCachedResponseEntity {
  private CachedResponseToCachedResponseEntity cachedResponseToCachedResponseEntity =
      new CachedResponseToCachedResponseEntity();

  @Test
  public void transform_whenInputParamIsNull_returnsNull() throws Exception {
    assertThat(cachedResponseToCachedResponseEntity.transform(null)).isNull();
  }

  @Test
  public void transform_whenValidInputParam_returnsCachedResponseEntity() throws Exception {
    CachedResponse cachedResponse = CachedResponseFixture.create();

    CachedResponseEntity cachedResponseEntity =
        cachedResponseToCachedResponseEntity.transform(cachedResponse);

    assertThat(cachedResponseEntity.getId()).isEqualTo(cachedResponse.getId());
    assertThat(cachedResponseEntity.getCookies()).isEqualTo(cachedResponse.getCookies());
    assertThat(cachedResponseEntity.getHeaders().toString())
        .contains(cachedResponse.getHeaders().toString());
    assertThat(cachedResponseEntity.getResponseBody()).isEqualTo(cachedResponse.getResponseBody());
    assertThat(cachedResponseEntity.getStatusCode()).isEqualTo(cachedResponse.getStatusCode());
    assertThat(cachedResponseEntity.getStatusText()).isEqualTo(cachedResponse.getStatusText());
  }
}
