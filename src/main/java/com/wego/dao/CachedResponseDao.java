package com.wego.dao;

import com.wego.dao.entities.CachedResponseEntity;
import java.util.Optional;

public interface CachedResponseDao {

  CachedResponseEntity save(CachedResponseEntity cachedResponseEntity);

  Optional<CachedResponseEntity> findById(String id);
}
