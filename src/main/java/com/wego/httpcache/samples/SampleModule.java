package com.wego.httpcache.samples;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import com.wego.httpcache.dao.CachedResponseDao;
import com.wego.httpcache.dao.impl.guava.GuavaCachedResponseImpl;
import com.wego.httpcache.services.AsyncHttpCacheService;
import com.wego.httpcache.services.CachedResponseService;
import com.wego.httpcache.services.factories.AsyncHttpCacheServiceFactory;
import com.wego.httpcache.services.impl.AsyncHttpCacheServiceImpl;
import com.wego.httpcache.services.impl.CachedResponseServiceImpl;

public class SampleModule extends AbstractModule {

  @Override
  protected void configure() {
    install(
        new FactoryModuleBuilder()
            .implement(AsyncHttpCacheService.class, AsyncHttpCacheServiceImpl.class)
            .build(AsyncHttpCacheServiceFactory.class));
    bind(CachedResponseService.class).to(CachedResponseServiceImpl.class);
    bind(CachedResponseDao.class).to(GuavaCachedResponseImpl.class);
  }
}
