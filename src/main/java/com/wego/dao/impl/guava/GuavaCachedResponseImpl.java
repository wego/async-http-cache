package com.wego.dao.impl.guava;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.wego.dao.CachedResponseDao;
import com.wego.dao.entities.CachedResponseEntity;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class GuavaCachedResponseImpl implements CachedResponseDao {
  private static long CACHE_TTL = 30;
  private static Cache<String, String> CACHE =
      CacheBuilder.newBuilder().expireAfterWrite(CACHE_TTL, TimeUnit.MINUTES).build();

  public static final ObjectMapper OBJECT_MAPPER =
      new ObjectMapper()
          .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
          .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

  @Override
  public CachedResponseEntity save(CachedResponseEntity cachedResponseEntity, long ttl) {
    String jsonEntity = null;
    try {
      jsonEntity = OBJECT_MAPPER.writeValueAsString(cachedResponseEntity);
    } catch (JsonProcessingException ex) {
      ex.printStackTrace();
    }

    if (jsonEntity != null) {
      CACHE.put(cachedResponseEntity.getId(), jsonEntity);
    }

    return cachedResponseEntity;
  }

  @Override
  public Optional<CachedResponseEntity> findById(String id) {
    CachedResponseEntity cachedResponseEntity = null;
    String jsonEntity = CACHE.getIfPresent(id);

    if (jsonEntity != null) {
      try {
        cachedResponseEntity = OBJECT_MAPPER.readValue(jsonEntity, CachedResponseEntity.class);
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    }

    return Optional.ofNullable(cachedResponseEntity);
  }
}
