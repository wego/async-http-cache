package com.wego.services;

import com.wego.dao.models.CachedResponse;
import java.util.Optional;

public interface CachedResponseService {
  CachedResponse save(CachedResponse response, long ttl);

  Optional<CachedResponse> findById(String id);
}
