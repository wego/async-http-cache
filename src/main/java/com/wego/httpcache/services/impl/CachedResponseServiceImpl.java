package com.wego.httpcache.services.impl;

import com.google.inject.Inject;
import com.wego.httpcache.dao.CachedResponseDao;
import com.wego.httpcache.dao.mappers.CachedResponseEntityToCachedResponse;
import com.wego.httpcache.dao.mappers.CachedResponseToCachedResponseEntity;
import com.wego.httpcache.dao.models.CachedResponse;
import com.wego.httpcache.services.CachedResponseService;
import java.io.IOException;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CachedResponseServiceImpl implements CachedResponseService {
  private static final Logger LOGGER = LoggerFactory.getLogger(CachedResponseServiceImpl.class);

  @Inject private CachedResponseDao cachedResponseDao;

  @Inject private CachedResponseEntityToCachedResponse cachedResponseEntityToCachedResponse;

  @Inject private CachedResponseToCachedResponseEntity responseToHttpReponseEntity;

  @Override
  public CachedResponse save(CachedResponse cachedResponse, long ttl) {
    if (cachedResponse == null) {
      return null;
    }

    try {
      cachedResponseDao.save(responseToHttpReponseEntity.transform(cachedResponse), ttl);
    } catch (IOException e) {
      LOGGER.error("Error saving cachedResponse with id {}", cachedResponse.getId(), e);
    }

    return cachedResponse;
  }

  @Override
  public Optional<CachedResponse> findById(String id) {
    return Optional.ofNullable(
        cachedResponseDao
            .findById(id)
            .map(cachedResponseEntityToCachedResponse::transform)
            .orElse(null));
  }
}
