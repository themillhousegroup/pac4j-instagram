package org.scribe.oauth

import org.scribe.model._
import org.scribe.builder.api.DefaultApi20
import org.scribe.extractors.AccessTokenExtractor
import org.specs2.mutable.Specification
import org.specs2.mock._

class ProxyAuth20WithHeadersServiceSpec extends Specification with Mockito {
  val mockApi = mock[DefaultApi20]
  mockApi.getAccessTokenExtractor returns mock[AccessTokenExtractor]

  def createTestInstance(fakeRequest: ProxyOAuthRequest)(getParameter: Boolean = false, addGrantType: Boolean = false, scope: Option[String] = None) = {
    val mockCfg = mock[OAuthConfig]
    mockCfg.hasScope returns scope.isDefined
    if (mockCfg.hasScope) {
      mockCfg.getScope returns scope.get
    }
    new ProxyAuth20WithHeadersServiceImpl(
      mockApi, mockCfg, 1, 2, null, 0, getParameter, addGrantType) {
      override private[oauth] def createRequest: ProxyOAuthRequest = fakeRequest
      override def addHeaders(requestToken: Token, api: DefaultApi20, config: OAuthConfig): List[(String, String)] = {
        List("foo" -> "bar")
      }
    }
  }
  "The OAuth20-With-Headers service" should {
    "by default, add no headers" in {
      val r = mock[ProxyOAuthRequest]
      r.send returns mock[Response]
      val i = new ProxyAuth20WithHeadersServiceImpl(
        mockApi, mock[OAuthConfig], 1, 2, null, 0, false, false) {
        override private[oauth] def createRequest: ProxyOAuthRequest = r
      }

      i.getAccessToken(mock[Token], mock[Verifier])
      there was no(r).addHeader(anyString, anyString)
    }
  }

  "The OAuth20-With-Headers service - POST body mode" should {
    "support adding the grant_type to a request" in {
      val r = mock[ProxyOAuthRequest]
      r.send returns mock[Response]
      val i = createTestInstance(r)(false, true)
      i.getAccessToken(mock[Token], mock[Verifier])
      there was one(r).addBodyParameter("grant_type", "authorization_code")
    }

    "support adding the scope to a request" in {
      val r = mock[ProxyOAuthRequest]
      r.send returns mock[Response]
      val i = createTestInstance(r)(false, false, Some("test-scope-variable"))
      i.getAccessToken(mock[Token], mock[Verifier])
      there was one(r).addBodyParameter("scope", "test-scope-variable")
    }

    "support adding headers to a request" in {
      val r = mock[ProxyOAuthRequest]
      r.send returns mock[Response]
      val i = createTestInstance(r)()
      i.getAccessToken(mock[Token], mock[Verifier])
      there was one(r).addHeader("foo", "bar")
    }
  }

  "The OAuth20-With-Headers service - query-string mode" should {
    "support adding the grant_type to a request" in {
      val r = mock[ProxyOAuthRequest]
      r.send returns mock[Response]
      val i = createTestInstance(r)(true, true)
      i.getAccessToken(mock[Token], mock[Verifier])
      there was one(r).addQuerystringParameter("grant_type", "authorization_code")
    }

    "support adding the scope to a request" in {
      val r = mock[ProxyOAuthRequest]
      r.send returns mock[Response]
      val i = createTestInstance(r)(true, false, Some("test-scope-variable"))
      i.getAccessToken(mock[Token], mock[Verifier])
      there was one(r).addQuerystringParameter("scope", "test-scope-variable")
    }

    "support adding headers to a request" in {
      val r = mock[ProxyOAuthRequest]
      r.send returns mock[Response]
      val i = createTestInstance(r)(true)
      i.getAccessToken(mock[Token], mock[Verifier])
      there was one(r).addHeader("foo", "bar")
    }
  }
}
