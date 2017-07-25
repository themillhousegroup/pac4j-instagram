package com.themillhousegroup.pac4jinstagram

import org.pac4j.core.profile.converter.Converters
import org.pac4j.core.profile.AttributesDefinition

object InstagramAttributesDefinition extends AttributesDefinition {

  val DATA = "user"
  val ID = "id"
  val USERNAME = "username"
  val FULL_NAME = "full_name"
  val PROFILE_PIC = "profile_picture"

  primary(USERNAME, Converters.STRING)
  primary(FULL_NAME, Converters.STRING)

  primary(PROFILE_PIC, Converters.STRING)
}