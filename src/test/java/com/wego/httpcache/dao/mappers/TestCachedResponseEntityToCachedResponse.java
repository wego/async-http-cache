package com.wego.httpcache.dao.mappers;

import static org.assertj.core.api.Assertions.assertThat;

import com.wego.httpcache.dao.entities.CachedResponseEntity;
import com.wego.httpcache.dao.models.CachedResponse;
import com.wego.httpcache.fixtures.CachedResponseEntityFixture;
import org.junit.Test;

public class TestCachedResponseEntityToCachedResponse {
  private CachedResponseEntityToCachedResponse cachedResponseEntityToCachedResponse =
      new CachedResponseEntityToCachedResponse();

  @Test
  public void transform_whenInputParamIsNull_returnsNull() throws Exception {
    assertThat(cachedResponseEntityToCachedResponse.transform(null)).isNull();
  }

  @Test
  public void transform_whenValidInputParam_returnsCachedResponse() throws Exception {
    CachedResponseEntity cachedResponseEntity = CachedResponseEntityFixture.create();

    CachedResponse cachedResponse =
        cachedResponseEntityToCachedResponse.transform(cachedResponseEntity);

    assertThat(cachedResponse.getId()).isEqualTo(cachedResponseEntity.getId());
    assertThat(cachedResponse.getCookies()).isEqualTo(cachedResponseEntity.getCookies());
    assertThat(cachedResponse.getHeaders().toString())
        .contains(cachedResponseEntity.getHeaders().toString());
    assertThat(cachedResponse.getResponseBody()).isEqualTo(cachedResponseEntity.getResponseBody());
    assertThat(cachedResponse.getStatusCode()).isEqualTo(cachedResponseEntity.getStatusCode());
    assertThat(cachedResponse.getStatusText()).isEqualTo(cachedResponseEntity.getStatusText());
  }
}
