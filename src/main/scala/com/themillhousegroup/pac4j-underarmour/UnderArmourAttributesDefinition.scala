package com.themillhousegroup.pac4junderarmour

import org.pac4j.core.profile.converter.Converters
import org.pac4j.oauth.profile.OAuthAttributesDefinition

object UnderArmourAttributesDefinition extends OAuthAttributesDefinition {

  val ID = "id"
  val RESOURCE_STATE = "resource_state"
  val FIRST_NAME = "first_name"
  val LAST_NAME = "last_name"
  val DISPLAY_NAME = "display_name"
  val LOCALITY = "locality"
  val REGION = "region"
  val COUNTRY = "country"
  val GENDER = "gender"

  val EMAIL = "email"

}
