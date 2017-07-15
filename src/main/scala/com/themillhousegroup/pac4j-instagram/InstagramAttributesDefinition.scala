package com.themillhousegroup.pac4junderarmour

import org.pac4j.core.profile.converter.Converters
import org.pac4j.oauth.profile.OAuthAttributesDefinition
import org.pac4j.oauth.profile.JsonHelper
import org.pac4j.oauth.profile.JsonObject
import com.fasterxml.jackson.databind.JsonNode

object InstagramAttributesDefinition extends OAuthAttributesDefinition {

  val ID = "id"
  val RESOURCE_STATE = "resource_state"
  val FIRST_NAME = "first_name"
  val LAST_NAME = "last_name"
  val DISPLAY_NAME = "display_name"
  val EMAIL = "email"
  val USERNAME = "username"

  val LOCATION = "location"
  val LOCALITY = "locality"
  val REGION = "region"
  val COUNTRY = "country"
  val GENDER = "gender"

  val LINKS = "_links"

  addAttribute(FIRST_NAME, Converters.stringConverter)
  addAttribute(LAST_NAME, Converters.stringConverter)
  addAttribute(DISPLAY_NAME, Converters.stringConverter)
  addAttribute(EMAIL, Converters.stringConverter)
  addAttribute(USERNAME, Converters.stringConverter)

  addAttribute(GENDER, Converters.stringConverter)
}