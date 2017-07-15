package com.themillhousegroup.pac4jinstagram

import org.pac4j.core.profile.converter.Converters
import org.pac4j.oauth.profile.OAuthAttributesDefinition
import org.pac4j.oauth.profile.JsonHelper
import org.pac4j.oauth.profile.JsonObject
import com.fasterxml.jackson.databind.JsonNode

object InstagramAttributesDefinition extends OAuthAttributesDefinition {

  val DATA = "data"
  val ID = "id"
  val USERNAME = "username"
  val FULL_NAME = "full_name"
  val PROFILE_PIC = "profile_picture"

  addAttribute(USERNAME, Converters.stringConverter)
  addAttribute(FULL_NAME, Converters.stringConverter)

  addAttribute(PROFILE_PIC, Converters.stringConverter)
}