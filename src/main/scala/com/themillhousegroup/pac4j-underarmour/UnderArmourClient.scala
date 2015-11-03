package com.themillhousegroup.pac4junderarmour

import org.pac4j.oauth.client._
import org.pac4j.oauth.credentials.OAuthCredentials
import org.pac4j.core.client.BaseClient
import org.scribe.model.Token
import org.pac4j.core.context.WebContext

/**
 * Get the key and secret values by registering your app at https://developer.underarmour.com/apps/register
 */
class UnderArmourClient(underArmourKey: String, clientSecret: String) extends BaseOAuth20Client[UnderArmourProfile] {

  setKey(underArmourKey)
  setSecret(clientSecret)

  protected def newClient(): BaseClient[OAuthCredentials, UnderArmourProfile] = {
    new UnderArmourClient(key, secret)
  }

  protected def requiresStateParameter(): Boolean = false

  protected def getProfileUrl(accessToken: Token): String = "https://api.ua.com/v7.1/user/self/"

  protected def hasBeenCancelled(context: WebContext): Boolean = false

  protected def extractUserProfile(body: String): UnderArmourProfile = {
    null
  }

}
