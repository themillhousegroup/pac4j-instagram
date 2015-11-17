package com.themillhousegroup.pac4junderarmour

import org.pac4j.core.profile.converter.Converters
import org.pac4j.oauth.profile.OAuthAttributesDefinition
import org.pac4j.oauth.profile.JsonHelper
import org.pac4j.oauth.profile.JsonObject

object UnderArmourAttributesDefinition extends OAuthAttributesDefinition {

  val ID = "id"
  val RESOURCE_STATE = "resource_state"
  val FIRST_NAME = "first_name"
  val LAST_NAME = "last_name"
  val DISPLAY_NAME = "display_name"
  val EMAIL = "email"

  val LOCATION = "location"
  val LOCALITY = "locality"
  val REGION = "region"
  val COUNTRY = "country"
  val GENDER = "gender"

  addAttribute(FIRST_NAME, Converters.stringConverter)
  addAttribute(LAST_NAME, Converters.stringConverter)
  addAttribute(DISPLAY_NAME, Converters.stringConverter)
  addAttribute(EMAIL, Converters.stringConverter)

  addAttribute(LOCATION, UnderArmourConverters.locationConverter)
  addAttribute(GENDER, Converters.stringConverter)
}

class UnderArmourLocation extends JsonObject {
  var country: String = ""
  var region: String = ""
  var locality: String = ""
  var address: String = ""

  import com.fasterxml.jackson.databind.JsonNode
  override def buildFromJson(json: JsonNode): Unit = {
    System.err.println(s"ual:buildFromJson on ${json}")
    this.country = JsonHelper.convert(Converters.stringConverter, json, "country").asInstanceOf[String]
    this.region = JsonHelper.convert(Converters.stringConverter, json, "region").asInstanceOf[String]
    this.locality = JsonHelper.convert(Converters.stringConverter, json, "locality").asInstanceOf[String]
    this.address = JsonHelper.convert(Converters.stringConverter, json, "address").asInstanceOf[String]
  }
}

object UnderArmourConverters {
  import org.pac4j.oauth.profile.converter.JsonObjectConverter
  val locationConverter: JsonObjectConverter = new JsonObjectConverter(classOf[UnderArmourLocation])
}
