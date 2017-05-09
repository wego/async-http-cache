package com.wego.httpcache.dao.impl.guava;

import static org.assertj.core.api.Assertions.assertThat;

import com.wego.httpcache.dao.CachedResponseDao;
import com.wego.httpcache.dao.entities.CachedResponseEntity;
import com.wego.httpcache.fixtures.CachedResponseEntityFixture;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;

public class TestJsonGuavaCachedResponseImpl {
  private CachedResponseDao cachedResponseDao = new JsonGuavaCachedResponseImpl();

  @Before
  public void setUp() throws Exception {}

  @Test
  public void save_withCorrectInputData_returnsCachedResponseEntity() throws Exception {
    CachedResponseEntity cachedResponseEntity = CachedResponseEntityFixture.create();

    assertThat(cachedResponseDao.save(cachedResponseEntity, 10)).isEqualTo(cachedResponseEntity);
  }

  @Test
  public void findById_whenFound_returnsCachedResponseEntity() throws Exception {
    CachedResponseEntity cachedResponseEntity = CachedResponseEntityFixture.create();

    cachedResponseDao.save(cachedResponseEntity, 10);

    Optional<CachedResponseEntity> foundCachedResponseEntity =
        cachedResponseDao.findById(cachedResponseEntity.getId());

    assertThat(foundCachedResponseEntity.get().getId()).isEqualTo(cachedResponseEntity.getId());
    assertThat(foundCachedResponseEntity.get().getStatusText())
        .isEqualTo(cachedResponseEntity.getStatusText());
    assertThat(foundCachedResponseEntity.get().getStatusCode())
        .isEqualTo(cachedResponseEntity.getStatusCode());
    assertThat(foundCachedResponseEntity.get().getResponseBody())
        .isEqualTo(cachedResponseEntity.getResponseBody());
    assertThat(foundCachedResponseEntity.get().getCookies().size())
        .isEqualTo(cachedResponseEntity.getCookies().size());
    assertThat(foundCachedResponseEntity.get().getCookies().get(0).getName())
        .isEqualTo(cachedResponseEntity.getCookies().get(0).getName());
    assertThat(foundCachedResponseEntity.get().getHeaders().keySet())
        .containsAll(cachedResponseEntity.getHeaders().keySet());
    assertThat(foundCachedResponseEntity.get().getHeaders().values())
        .containsAll(cachedResponseEntity.getHeaders().values());
  }
}
