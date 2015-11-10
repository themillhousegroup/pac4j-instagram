package com.themillhousegroup.pac4junderarmour

import org.pac4j.oauth.client._
import org.pac4j.oauth.credentials.OAuthCredentials
import org.pac4j.core.client.BaseClient
import org.scribe.model.Token
import org.pac4j.core.context.WebContext
import org.scribe.oauth.{ ProxyAuth20WithHeadersServiceImpl, ProxyOAuth20ServiceImpl }
import org.scribe.model.OAuthConfig
import org.scribe.model.SignatureType
import org.scribe.builder.api.{ DefaultApi20, UnderArmourApi }

/**
 * Get the key and secret values by registering your app at https://developer.underarmour.com/apps/register
 */
class UnderArmourClient(underArmourKey: String, clientSecret: String) extends BaseOAuth20Client[UnderArmourProfile] {

  /**
   * comma delimited string of ‘view_private’ and/or ‘write’, leave blank for read-only permissions. FIXME
   */
  protected val scope: String = null
  setKey(underArmourKey)
  setSecret(clientSecret)

  protected def newClient(): BaseClient[OAuthCredentials, UnderArmourProfile] = {
    new UnderArmourClient(key, secret)
  }

  protected override def internalInit(): Unit = {
    super.internalInit()
    println(s"UnderArmourClient::internalInit: Using callbackUrl: '${callbackUrl}'")
    service =
      new ProxyAuth20WithHeadersServiceImpl(
        new UnderArmourApi(),
        new OAuthConfig(key, secret, callbackUrl, SignatureType.Header, scope, null),
        connectTimeout,
        readTimeout,
        proxyHost,
        proxyPort,
        false,
        true) {
        override def addHeaders(requestToken: Token, api: DefaultApi20, config: OAuthConfig): List[(String, String)] = {
          // As per:
          // https://developer.underarmour.com/docs/v71_OAuth_2_Intro
          // we need to add "Api-Key" with the client ID
          println(s"UnderArmourClient::addHeaders: Setting 'Api-Key' HTTP header to '${config.getApiKey}'")
          List("Api-Key" -> config.getApiKey)
        }
      }
  }

  protected def requiresStateParameter(): Boolean = false

  protected def getProfileUrl(accessToken: Token): String = "https://api.ua.com/v7.1/user/self/"

  protected def hasBeenCancelled(context: WebContext): Boolean = false

  protected def extractUserProfile(body: String): UnderArmourProfile = {
    null
  }

}
