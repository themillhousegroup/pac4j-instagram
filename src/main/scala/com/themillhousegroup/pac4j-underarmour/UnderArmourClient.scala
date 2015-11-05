package com.themillhousegroup.pac4junderarmour

import org.pac4j.oauth.client._
import org.pac4j.oauth.credentials.OAuthCredentials
import org.pac4j.core.client.BaseClient
import org.scribe.model.Token
import org.pac4j.core.context.WebContext
import org.scribe.oauth.ProxyOAuth20ServiceImpl
import org.scribe.model.OAuthConfig
import org.scribe.model.SignatureType
import org.scribe.builder.api.UnderArmourApi

/**
 * Get the key and secret values by registering your app at https://developer.underarmour.com/apps/register
 */
class UnderArmourClient(underArmourKey: String, clientSecret: String) extends BaseOAuth20Client[UnderArmourProfile] {

  val approvalPrompt = "force" // FIXME

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
    service =
      new ProxyOAuth20ServiceImpl(
        new UnderArmourApi(approvalPrompt),
        new OAuthConfig(key, secret, callbackUrl, SignatureType.Header, scope, null),
        connectTimeout,
        readTimeout,
        proxyHost,
        proxyPort,
        false,
        false)
  }

  protected def requiresStateParameter(): Boolean = false

  protected def getProfileUrl(accessToken: Token): String = "https://api.ua.com/v7.1/user/self/"

  protected def hasBeenCancelled(context: WebContext): Boolean = false

  protected def extractUserProfile(body: String): UnderArmourProfile = {
    null
  }

}
