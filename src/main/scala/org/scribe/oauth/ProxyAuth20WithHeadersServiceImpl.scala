package org.scribe.oauth

import org.scribe.model._
import org.scribe.builder.api.DefaultApi20

/**
 * For Oauth2 implementations that need to also set HTTP headers
 * in their requests (e.g UnderArmour)
 */
class ProxyAuth20WithHeadersServiceImpl(
  api: DefaultApi20,
  config: OAuthConfig,
  connectTimeout: Int,
  readTimeout: Int,
  proxyHost: String,
  proxyPort: Int,
  getParameter: Boolean,
  addGrantType: Boolean)
    extends ProxyOAuth20ServiceImpl(api, config, connectTimeout, readTimeout, proxyHost, proxyPort, getParameter, addGrantType) {

  // For testability
  private[oauth] def createRequest: ProxyOAuthRequest = {
    new ProxyOAuthRequest(this.api.getAccessTokenVerb,
      this.api.getAccessTokenEndpoint, this.connectTimeout,
      this.readTimeout, this.proxyHost, this.proxyPort)
  }

  def getConfig = config

  override def getAccessToken(requestToken: Token, verifier: Verifier): Token = {
    val request = createRequest
    if (this.getParameter) {
      request.addQuerystringParameter(OAuthConstants.CLIENT_ID, this.config.getApiKey)
      request.addQuerystringParameter(OAuthConstants.CLIENT_SECRET, this.config.getApiSecret)
      request.addQuerystringParameter(OAuthConstants.CODE, verifier.getValue)
      request.addQuerystringParameter(OAuthConstants.REDIRECT_URI, this.config.getCallback)
      if (this.config.hasScope) {
        request.addQuerystringParameter(OAuthConstants.SCOPE, this.config.getScope)
      }
      if (this.addGrantType) {
        request.addQuerystringParameter("grant_type", "authorization_code")
      }
    } else {
      if (this.addGrantType) {
        request.addBodyParameter("grant_type", "authorization_code")
      }
      request.addBodyParameter(OAuthConstants.CLIENT_ID, this.config.getApiKey)
      request.addBodyParameter(OAuthConstants.CLIENT_SECRET, this.config.getApiSecret)
      request.addBodyParameter(OAuthConstants.CODE, verifier.getValue)
      //request.addBodyParameter(OAuthConstants.REDIRECT_URI, this.config.getCallback)
      if (this.config.hasScope) {
        request.addBodyParameter(OAuthConstants.SCOPE, this.config.getScope)
      }
    }
    val headers = addHeaders(requestToken, api, config)
    headers.foreach {
      case (k, v) =>
        request.addHeader(k, v)
    }
    val response = request.send
    api.getAccessTokenExtractor.extract(response.getBody)
  }

  def addHeaders(requestToken: Token, api: DefaultApi20, config: OAuthConfig): List[(String, String)] = {
    Nil
  }
}
