package com.themillhousegroup.pac4junderarmour

import org.pac4j.oauth.client._
import org.pac4j.oauth.profile.OAuth20Profile

class UnderArmourClient extends BaseOAuth20Client[UnderArmourProfile] {

  protected def newClient(): org.pac4j.core.client.BaseClient[org.pac4j.oauth.credentials.OAuthCredentials, com.themillhousegroup.pac4junderarmour.UnderArmourProfile] = ???

  protected def requiresStateParameter(): Boolean = ???

  protected def getProfileUrl(x$1: org.scribe.model.Token): String = ???

  protected def hasBeenCancelled(x$1: org.pac4j.core.context.WebContext): Boolean = ???

  protected def extractUserProfile(x$1: String): com.themillhousegroup.pac4junderarmour.UnderArmourProfile = ???

}
