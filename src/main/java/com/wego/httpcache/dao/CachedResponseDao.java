package com.wego.httpcache.dao;

import com.wego.httpcache.dao.entities.CachedResponseEntity;
import java.util.Optional;

public interface CachedResponseDao {

  CachedResponseEntity save(CachedResponseEntity cachedResponseEntity, long ttl);

  Optional<CachedResponseEntity> findById(String id);
}
