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
 * Get the key and secret values by registering your app at
 * https://www.instagram.com/developer/clients/manage/
 *
 * Default permission scope is "basic";
 * but you could also try (for example):
 * "public_content+follower_list"
 *
 * @see https://www.instagram.com/developer/authorization/
 */
class InstagramClient(clientKey: String, clientSecret: String, scope: String = "basic") extends BaseOAuth20Client[InstagramProfile] {

  /**
   *
   *
   */
  setKey(clientKey)
  setSecret(clientSecret)
  setTokenAsHeader(true)

  private val approvalPrompt = "auto"

  protected def newClient(): BaseClient[OAuthCredentials, InstagramProfile] = {
    new InstagramClient(clientKey, clientSecret)
  }

  // As per https://www.instagram.com/developer/authentication/ - we must use grant_type: authorization_code in the access token request
  override protected val hasOAuthGrantType: Boolean = true

  protected def getProfileUrl(accessToken: OAuth2AccessToken): String = s"${InstagramApi.INSTAGRAM_API_URL}/users/self/"

  protected def extractUserProfile(body: String): InstagramProfile = {
    InstagramProfileBuilder.createFromString(body)
  }

  protected def getApi: BaseApi[OAuth20Service] = {
    new InstagramApi()
  }

  override protected val getOAuthScope = scope
}
