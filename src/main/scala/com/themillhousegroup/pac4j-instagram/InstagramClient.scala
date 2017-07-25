package com.themillhousegroup.pac4jinstagram

import org.pac4j.oauth.client._
import org.pac4j.oauth.credentials.OAuthCredentials
import org.pac4j.core.client.BaseClient
import org.pac4j.core.context.WebContext
import org.scribe.builder.api.InstagramApi
import java.net.URL

import com.github.scribejava.core.builder.api.BaseApi
import com.github.scribejava.core.model.OAuth2AccessToken
import com.github.scribejava.core.oauth.OAuth20Service

/**
 * Get the key and secret values by registering your app at https://www.instagram.com/developer/clients/manage/
 */
class InstagramClient(clientKey: String, clientSecret: String) extends BaseOAuth20Client[InstagramProfile] {

  /**
   * comma delimited string of ‘view_private’ and/or ‘write’, leave blank for read-only permissions. FIXME
   */
  protected val scope: String = null
  setKey(clientKey)
  setSecret(clientSecret)
  setTokenAsHeader(true)

  private val approvalPrompt = "auto"

  protected def newClient(): BaseClient[OAuthCredentials, InstagramProfile] = {
    new InstagramClient(clientKey, clientSecret)
  }

  // As per https://www.instagram.com/developer/authentication/ - we must use grant_type: authorization_code in the access token request
  override protected val hasOAuthGrantType:Boolean = true

  protected def getProfileUrl(accessToken: OAuth2AccessToken): String = s"https://api.instagram.com/v1/users/self/?access_token=${accessToken.getAccessToken}"

  protected def extractUserProfile(body: String): InstagramProfile = {
    InstagramProfileBuilder.createFromString(body)
  }

  protected def getApi: BaseApi[OAuth20Service] = {
    new InstagramApi()
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
        profile.setId(JsonHelper.getElement(data, InstagramAttributesDefinition.ID))

        InstagramAttributesDefinition.getPrimaryAttributes.asScala.foreach { attribute =>
          profile.addAttribute(attribute, JsonHelper.getElement(data, attribute))
        }
      }

    }
    profile

  }
}
