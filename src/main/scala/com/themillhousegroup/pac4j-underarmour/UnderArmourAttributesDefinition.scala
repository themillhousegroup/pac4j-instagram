package com.themillhousegroup.pac4junderarmour


import org.pac4j.core.profile.converter.Converters
import org.pac4j.oauth.profile.OAuthAttributesDefinition

object UnderArmourAttributesDefinition extends OAuthAttributesDefinition {

  val ID = "id"
	val RESOURCE_STATE = "resource_state"
	val FIRST_NAME = "firstname"
	val LAST_NAME = "lastname"
	val PROFILE_MEDIUM = "profile_medium"
	val PROFILE = "profile"
	val CITY = "city"
	val STATE = "state"
	val COUNTRY = "country"
	val GENDER = "gender"

	val EMAIL = "email"

}
