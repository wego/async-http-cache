package com.wego.services.impl;

import com.google.inject.Inject;
import com.wego.dao.CachedResponseDao;
import com.wego.dao.mappers.CachedResponseEntityToCachedResponse;
import com.wego.dao.mappers.CachedResponseToCachedResponseEntity;
import com.wego.dao.models.CachedResponse;
import com.wego.services.CachedResponseService;
import java.util.Optional;

public class CachedResponseServiceImpl implements CachedResponseService {

  @Inject private CachedResponseDao cachedResponseDao;

  @Inject private CachedResponseEntityToCachedResponse cachedResponseEntityToCachedResponse;

  @Inject private CachedResponseToCachedResponseEntity responseToHttpReponseEntity;

  @Override
  public CachedResponse save(CachedResponse cachedResponse) {
    if (cachedResponse == null) {
      return null;
    }

    cachedResponseDao.save(responseToHttpReponseEntity.transform(cachedResponse));

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
