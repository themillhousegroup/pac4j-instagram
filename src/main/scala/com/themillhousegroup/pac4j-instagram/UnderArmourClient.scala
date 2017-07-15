package com.themillhousegroup.pac4junderarmour

import org.pac4j.oauth.client._
import org.pac4j.oauth.credentials.OAuthCredentials
import org.pac4j.core.client.BaseClient
import org.scribe.model.Token
import org.pac4j.core.context.WebContext
import org.scribe.oauth.{ ProxyAuth20WithHeadersServiceImpl, ProxyOAuth20ServiceImpl }
import org.scribe.model.ProxyOAuthRequest
import org.scribe.model.OAuthConfig
import org.scribe.model.SignatureType
import org.scribe.builder.api.{ DefaultApi20, UnderArmourApi }
import java.net.URL

/**
 * Get the key and secret values by registering your app at https://developer.underarmour.com/apps/register
 */
class UnderArmourClient(underArmourKey: String, clientSecret: String, clientCallbackUrl: String = "/UnderArmourClient/callback") extends BaseOAuth20Client[UnderArmourProfile] {

  /**
   * comma delimited string of ‘view_private’ and/or ‘write’, leave blank for read-only permissions. FIXME
   */
  protected val scope: String = null
  setKey(underArmourKey)
  setSecret(clientSecret)
  setTokenAsHeader(true)

  protected def newClient(): BaseClient[OAuthCredentials, UnderArmourProfile] = {
    new UnderArmourClient(key, secret)
  }

  protected override def internalInit(): Unit = {
    super.internalInit()
    // FIXME: Filthy hack - UA seems unable to support having extra params in the callback URL
    // (like client_name=UnderArmourClient  - which is how pac4j routes it back to us...)
    // so we rewrite the client name INTO the callback URL rather than just as a param:
    // Before:
    // http://my-app:9000/callback?client_name=UnderArmourClient
    // After:
    // http://my-app:9000/UnderArmourClient/callback
    // This will need support in your client app's routes mapping

    val u = new URL(callbackUrl)
    val modifiedCallbackUrl = s"${u.getProtocol}://${u.getAuthority}${clientCallbackUrl}"
    service =
      new ProxyAuth20WithHeadersServiceImpl(
        new UnderArmourApi(),
        new OAuthConfig(key, secret, modifiedCallbackUrl, SignatureType.Header, scope, null),
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
          List("Api-Key" -> config.getApiKey)
        }
      }
  }

  protected def requiresStateParameter(): Boolean = false

  protected def getProfileUrl(accessToken: Token): String = "https://oauth2-api.mapmyapi.com/v7.1/user/self/"

  // All UA requests have to have the "Api-Key" HTTP header...
  protected override def createProxyRequest(url: String): ProxyOAuthRequest = {
    val r = super.createProxyRequest(url)
    r.addHeader("Api-Key", underArmourKey)
    r
  }

  protected def hasBeenCancelled(context: WebContext): Boolean = false

  protected def extractUserProfile(body: String): UnderArmourProfile = {
    UnderArmourProfileBuilder.createFromString(body)
  }

  private[pac4junderarmour] def getService = service
}

object UnderArmourProfileBuilder {
  def createFromString(body: String): UnderArmourProfile = {
    import org.pac4j.oauth.profile.JsonHelper
    import scala.collection.JavaConverters._

    val profile = new UnderArmourProfile()
    val json = JsonHelper.getFirstNode(body)
    if (json != null) {
      profile.setId(JsonHelper.get(json, UnderArmourAttributesDefinition.ID))

      UnderArmourAttributesDefinition.getAllAttributes.asScala.foreach { attribute =>
        profile.addAttribute(attribute, JsonHelper.get(json, attribute))
      }
    }
    profile

  }
}
