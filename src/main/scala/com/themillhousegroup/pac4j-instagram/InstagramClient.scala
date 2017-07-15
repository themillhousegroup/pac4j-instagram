package com.themillhousegroup.pac4jinstagram

import org.pac4j.oauth.client._
import org.pac4j.oauth.credentials.OAuthCredentials
import org.pac4j.core.client.BaseClient
import org.scribe.model.Token
import org.pac4j.core.context.WebContext
import org.scribe.model.ProxyOAuthRequest
import org.scribe.model.OAuthConfig
import org.scribe.model.SignatureType
import org.scribe.builder.api.{ DefaultApi20, UnderArmourApi }
import java.net.URL

/**
 * Get the key and secret values by registering your app at https://www.instagram.com/developer/clients/manage/
 */
class InstagramClient(clientKey: String, clientSecret: String, clientCallbackUrl: String = "/InstagramClient/callback") extends BaseOAuth20Client[InstagramProfile] {

  /**
   * comma delimited string of ‘view_private’ and/or ‘write’, leave blank for read-only permissions. FIXME
   */
  protected val scope: String = null
  setKey(clientKey)
  setSecret(clientSecret)
  setTokenAsHeader(true)

  protected def newClient(): BaseClient[OAuthCredentials, InstagramProfile] = {
    new InstagramClient(key, secret)
  }

  protected def requiresStateParameter(): Boolean = false

  protected def getProfileUrl(accessToken: Token): String = s"https://api.instagram.com/v1/users/self/?access_token=${accessToken.getToken}"

  protected def hasBeenCancelled(context: WebContext): Boolean = false

  protected def extractUserProfile(body: String): InstagramProfile = {
    InstagramProfileBuilder.createFromString(body)
  }
}

object InstagramProfileBuilder {
  def createFromString(body: String): InstagramProfile = {
    import org.pac4j.oauth.profile.JsonHelper
    import scala.collection.JavaConverters._

    val profile = new InstagramProfile()
    val json = JsonHelper.getFirstNode(body)
    println(s"body: $body")
    println(s"json: $json")

    if (json != null) {
      val data = json.get(InstagramAttributesDefinition.DATA)
      println(s"data: $data")
      if (data != null) {
        profile.setId(JsonHelper.get(data, InstagramAttributesDefinition.ID))

        InstagramAttributesDefinition.getAllAttributes.asScala.foreach { attribute =>
          profile.addAttribute(attribute, JsonHelper.get(data, attribute))
        }
      }

    }
    profile

  }
}
