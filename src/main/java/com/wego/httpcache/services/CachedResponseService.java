package com.wego.httpcache.services;

import com.wego.httpcache.dao.models.CachedResponse;
import java.util.Optional;

public interface CachedResponseService {
  CachedResponse save(CachedResponse response, long ttl);

  Optional<CachedResponse> findById(String id);
}
