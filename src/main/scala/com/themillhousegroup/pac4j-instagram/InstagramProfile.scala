package com.themillhousegroup.pac4jinstagram

import org.pac4j.oauth.profile.OAuth20Profile
import org.pac4j.core.profile._
import org.scribe.builder.api.InstagramApi

/**
 * Example JSON from GET /users/self:
 * {
 * "user": {
 * "id": "1574083",
 * "username": "snoopdogg",
 * "full_name": "Snoop Dogg",
 * "profile_picture": "http://distillery.s3.amazonaws.com/profiles/profile_1574083_75sq_1295469061.jpg",
 * "bio": "This is my bio",
 * "website": "http://snoopdogg.com",
 * "counts": {
 * "media": 1320,
 * "follows": 420,
 * "followed_by": 3410
 * }
 * }
 */
class InstagramProfile extends OAuth20Profile {
  override protected val getAttributesDefinition: AttributesDefinition = InstagramAttributesDefinition

  private def getString(name: String): String = {
    getAttribute(name).asInstanceOf[String]
  }

  private def get[T](name: String): T = {
    getAttribute(name).asInstanceOf[T]
  }

  override def getDisplayName: String = {
    getString(InstagramAttributesDefinition.FULL_NAME)
  }

  override def getPictureUrl: String = {
    getString(InstagramAttributesDefinition.PROFILE_PIC)
  }

  override def getProfileUrl: String = s"${InstagramApi.INSTAGRAM_API_URL}/users/${getId}"

}

object InstagramProfileBuilder {
  def createFromString(body: String): InstagramProfile = {
    import org.pac4j.oauth.profile.JsonHelper
    import scala.collection.JavaConverters._

    val profile = new InstagramProfile()
    val json = JsonHelper.getFirstNode(body)
    if (json != null) {
      val data = json.get(InstagramAttributesDefinition.DATA)
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