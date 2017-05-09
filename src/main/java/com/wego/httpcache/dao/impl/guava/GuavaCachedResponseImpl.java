package com.wego.httpcache.dao.impl.guava;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.wego.httpcache.dao.CachedResponseDao;
import com.wego.httpcache.dao.entities.CachedResponseEntity;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class GuavaCachedResponseImpl implements CachedResponseDao {
  private static final long CACHE_TTL = 30;
  private static final Cache<String, CachedResponseEntity> CACHE =
      CacheBuilder.newBuilder().expireAfterWrite(CACHE_TTL, TimeUnit.MINUTES).build();

  @Override
  public CachedResponseEntity save(CachedResponseEntity cachedResponseEntity, long ttl) {
    if (cachedResponseEntity != null) {
      CACHE.put(cachedResponseEntity.getId(), cachedResponseEntity);
    }

    return cachedResponseEntity;
  }

  @Override
  public Optional<CachedResponseEntity> findById(String id) {
    return Optional.ofNullable(CACHE.getIfPresent(id));
  }
}
