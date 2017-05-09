package com.wego.httpcache.services.impl;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.ning.http.client.AsyncCompletionHandlerBase;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.ListenableFuture;
import com.ning.http.client.Request;
import com.ning.http.client.RequestBuilder;
import com.ning.http.client.Response;
import com.wego.httpcache.dao.models.CachedResponse;
import com.wego.httpcache.fixtures.RequestFixture;
import com.wego.httpcache.services.AsyncHttpCacheService;
import com.wego.httpcache.services.CachedResponseService;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TestAsyncHttpCachedServiceImpl {
  private static long CACHING_TTL = 60;
  @Rule public WireMockRule wireMockRule = new WireMockRule(8089);

  @Spy private AsyncHttpClient asyncHttpClient = new AsyncHttpClient();

  @InjectMocks
  private AsyncHttpCacheService asyncHttpCacheService =
      new AsyncHttpCacheServiceImpl(asyncHttpClient, CACHING_TTL);

  @Mock private CachedResponseService cachedResponseService;
  @Mock private Request request;

  @Test
  public void executeRequest_whenWasCached_getResponseFromCacheAndCallOnComplete()
      throws Exception {
    final CachedResponse cachedResponse = new CachedResponse();
    when(cachedResponseService.findById(anyString())).thenReturn(Optional.of(cachedResponse));

    AsyncCompletionHandlerBase asyncCompletionHandlerBaseHandler =
        new AsyncCompletionHandlerBase() {

          @Override
          public Response onCompleted(Response response) throws Exception {
            assertThat(response).isEqualTo(cachedResponse);
            return response;
          }
        };

    Optional<ListenableFuture<Response>> responseListenableFuture =
        asyncHttpCacheService.executeRequest(request, asyncCompletionHandlerBaseHandler);

    verifyZeroInteractions(asyncHttpClient);
    assertThat(responseListenableFuture.isPresent()).isFalse();
  }

  @Test
  public void executeRequest_whenWasNotCached_executeHttpRequestAndCacheNewResponse()
      throws Exception {
    final Request request =
        new RequestBuilder().setMethod("GET").setUrl("http://localhost:8089/resources/").build();
    final List<CachedResponse> savedCachedResponses = Lists.newArrayList();

    stubFor(
        get(urlEqualTo("/resources/"))
            .willReturn(
                aResponse()
                    .withStatus(200)
                    .withHeader("Content-Type", "text/json")
                    .withBody("This is body")));

    when(cachedResponseService.findById(anyString())).thenReturn(Optional.empty());
    when(cachedResponseService.save(any(), eq(CACHING_TTL)))
        .thenAnswer(
            invocation -> {
              CachedResponse cr = invocation.getArgumentAt(0, CachedResponse.class);
              savedCachedResponses.add(cr);
              return Optional.of(cr);
            });

    Optional<ListenableFuture<Response>> responseListenableFuture =
        asyncHttpCacheService.executeRequest(request, new AsyncCompletionHandlerBase());

    responseListenableFuture.get().get();

    verify(asyncHttpClient).executeRequest(any(), any());
    assertThat(savedCachedResponses.size()).isEqualTo(1);
    assertThat(savedCachedResponses.get(0).getId()).isNotEmpty();
  }

  @Test
  public void buildResponseId_returnsDifferentIdsForDifferentRequest() throws Exception {

    Request request = RequestFixture.create("GET", "http://localhost:8089/resources/");

    Request requestWithDifferentUrl =
        RequestFixture.create("GET", "http://localhost:8089/resources/2");

    Request requestWithParams =
        RequestFixture.createWithQueryParam(
            "GET", "http://localhost:8089/resources/2", "queryParam", "test1");

    Request requestWithDifferentParams =
        RequestFixture.createWithQueryParam(
            "GET", "http://localhost:8089/resources/2", "queryParam", "test2");

    Request requestWithHeader =
        RequestFixture.createWithHeader(
            "GET", "http://localhost:8089/resources/2", "header", "test1");

    Request requestWithDifferentHeader =
        RequestFixture.createWithHeader(
            "GET", "http://localhost:8089/resources/2", "header", "test2");

    Request requestWithCookie =
        RequestFixture.createWithCookie(
            "GET", "http://localhost:8089/resources/2", "cookie", "test1");

    Request requestWithDifferentCookie =
        RequestFixture.createWithCookie(
            "GET", "http://localhost:8089/resources/2", "cookie", "test2");

    Request requestWithDifferentMethod =
        RequestFixture.create("POST", "http://localhost:8089/resources/");

    Request requestWithBody =
        RequestFixture.createWithParams(
            "POST", "http://localhost:8089/resources/", "param", "test1");

    Request requestWithDifferentBody =
        RequestFixture.createWithParams(
            "POST", "http://localhost:8089/resources/", "param", "test2");

    Method method =
        AsyncHttpCacheServiceImpl.class.getDeclaredMethod("buildResponseId", Request.class);
    method.setAccessible(true);

    final List<String> responseIds =
        Stream.of(
                request,
                requestWithDifferentUrl,
                requestWithDifferentMethod,
                requestWithBody,
                requestWithDifferentBody,
                requestWithCookie,
                requestWithDifferentCookie,
                requestWithHeader,
                requestWithDifferentHeader,
                requestWithParams,
                requestWithDifferentParams)
            .map(
                rq -> {
                  try {
                    return (String) method.invoke(asyncHttpCacheService, rq);
                  } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                    return null;
                  }
                })
            .filter(Objects::nonNull)
            .collect(Collectors.toList());

    assertThat(Sets.newHashSet(responseIds).size()).isEqualTo(11);
  }

  @Test
  public void buildResponseId_returnsSameIdsForSameRequest() throws Exception {

    Request request1 = RequestFixture.createWithFullParams();

    Request request2 = RequestFixture.createWithFullParams();

    Method method =
        AsyncHttpCacheServiceImpl.class.getDeclaredMethod("buildResponseId", Request.class);
    method.setAccessible(true);

    final Set<String> responseIds =
        Stream.of(request1, request2)
            .map(
                rq -> {
                  try {
                    return (String) method.invoke(asyncHttpCacheService, rq);
                  } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                    return null;
                  }
                })
            .filter(Objects::nonNull)
            .collect(Collectors.toSet());

    assertThat(responseIds.size()).isEqualTo(1);
  }
}
