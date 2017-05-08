package com.wego.httpcache.services.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

import com.google.common.collect.Lists;
import com.wego.httpcache.dao.CachedResponseDao;
import com.wego.httpcache.dao.entities.CachedResponseEntity;
import com.wego.httpcache.dao.mappers.CachedResponseEntityToCachedResponse;
import com.wego.httpcache.dao.mappers.CachedResponseToCachedResponseEntity;
import com.wego.httpcache.dao.models.CachedResponse;
import com.wego.httpcache.services.CachedResponseService;
import java.util.List;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TestCachedResponseServiceImpl {
  private static long CACHING_TTL = 60;

  @InjectMocks
  private CachedResponseService cachedResponseService = new CachedResponseServiceImpl();

  @Mock private CachedResponseDao cachedResponseDao;
  @Mock private CachedResponseEntityToCachedResponse cachedResponseEntityToCachedResponse;
  @Mock private CachedResponseToCachedResponseEntity cachedResponseToCachedResponseEntity;

  @Before
  public void setUp() throws Exception {}

  @Test
  public void save_whenInputObjectIsNull_returnNull() {
    Assertions.assertThat(cachedResponseService.save(null, CACHING_TTL)).isNull();
  }

  @Test
  public void save_whenInputObjectIsNotNull_saveCachedResponse() throws Exception {
    final CachedResponse cachedResponse = new CachedResponse();
    final CachedResponseEntity cachedResponseEntity = new CachedResponseEntity();
    final List<CachedResponse> savedCachedResponses = Lists.newArrayList();

    when(cachedResponseToCachedResponseEntity.transform(cachedResponse))
        .thenReturn(cachedResponseEntity);
    when(cachedResponseDao.save(any(CachedResponseEntity.class), eq(CACHING_TTL)))
        .thenAnswer(
            invocation -> {
              savedCachedResponses.add(invocation.getArgumentAt(0, CachedResponse.class));
              return cachedResponseEntity;
            });

    Assertions.assertThat(cachedResponseService.save(cachedResponse, CACHING_TTL))
        .isEqualTo(cachedResponse);
    assertThat(savedCachedResponses.size()).isEqualTo(1);
  }

  @Test
  public void findById_whenNotFound_returnNull() throws Exception {
    when(cachedResponseDao.findById(anyString())).thenReturn(Optional.empty());

    assertThat(cachedResponseService.findById("ID").isPresent()).isFalse();
  }

  @Test
  public void findById_whenFound_returnOptionalOfCachedResponse() throws Exception {
    final CachedResponse cachedResponse = new CachedResponse();
    final CachedResponseEntity cachedResponseEntity = new CachedResponseEntity();

    when(cachedResponseEntityToCachedResponse.transform(cachedResponseEntity))
        .thenReturn(cachedResponse);
    when(cachedResponseDao.findById("ID")).thenReturn(Optional.of(cachedResponseEntity));

    Assertions.assertThat(cachedResponseService.findById("ID").get()).isEqualTo(cachedResponse);
  }
}
