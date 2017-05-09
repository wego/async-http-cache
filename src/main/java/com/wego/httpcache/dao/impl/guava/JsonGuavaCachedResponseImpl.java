package com.wego.httpcache.dao.impl.guava;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.wego.httpcache.dao.CachedResponseDao;
import com.wego.httpcache.dao.entities.CachedResponseEntity;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonGuavaCachedResponseImpl implements CachedResponseDao {
  private static final Logger LOGGER = LoggerFactory.getLogger(JsonGuavaCachedResponseImpl.class);
  private static final long CACHE_TTL = 30;
  private static final Cache<String, String> CACHE =
      CacheBuilder.newBuilder().expireAfterWrite(CACHE_TTL, TimeUnit.MINUTES).build();

  private static final ObjectMapper OBJECT_MAPPER =
      new ObjectMapper()
          .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
          .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

  @Override
  public CachedResponseEntity save(CachedResponseEntity cachedResponseEntity, long ttl) {
    if (cachedResponseEntity != null) {
      try {
        String jsonEntity = OBJECT_MAPPER.writeValueAsString(cachedResponseEntity);
        CACHE.put(cachedResponseEntity.getId(), jsonEntity);
      } catch (JsonProcessingException e) {
        LOGGER.error(
            "Error when saving CachedResponse with id {}", cachedResponseEntity.getId(), e);
      }
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
      } catch (Exception e) {
        LOGGER.error("Error when finding CachedResponse with id {}", id, e);
      }
    }

    return Optional.ofNullable(cachedResponseEntity);
  }
}
