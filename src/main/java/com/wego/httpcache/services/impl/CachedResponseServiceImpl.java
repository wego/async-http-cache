package com.wego.httpcache.services.impl;

import com.google.inject.Inject;
import com.wego.httpcache.dao.CachedResponseDao;
import com.wego.httpcache.dao.mappers.CachedResponseEntityToCachedResponse;
import com.wego.httpcache.dao.mappers.CachedResponseToCachedResponseEntity;
import com.wego.httpcache.dao.models.CachedResponse;
import com.wego.httpcache.services.CachedResponseService;
import java.util.Optional;

public class CachedResponseServiceImpl implements CachedResponseService {

  @Inject private CachedResponseDao cachedResponseDao;

  @Inject private CachedResponseEntityToCachedResponse cachedResponseEntityToCachedResponse;

  @Inject private CachedResponseToCachedResponseEntity responseToHttpReponseEntity;

  @Override
  public CachedResponse save(CachedResponse cachedResponse, long ttl) {
    if (cachedResponse == null) {
      return null;
    }

    cachedResponseDao.save(responseToHttpReponseEntity.transform(cachedResponse), ttl);

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
