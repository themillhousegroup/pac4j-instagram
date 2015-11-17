package com.themillhousegroup.pac4junderarmour

import org.pac4j.oauth.profile.OAuth20Profile
import org.pac4j.core.profile._

/**
 * Example JSON from GET /v7.1/user/self:
 * {
 * "last_name": "Last",
 * "weight": null,
 * "communication": {
 * "promotions": true,
 * "newsletter": true,
 * "system_messages": true
 * },
 * "height": null,
 * "hobbies": "",
 * "id": {user ID},
 * "date_joined": "2014-08-28T00:33:20+00:00",
 * "first_name": "First",
 * "display_name": "First Last",
 * "introduction": "",
 * "display_measurement_system": "imperial",
 * "last_login": "2014-08-28T00:33:20+00:00",
 * "goal_statement": null,
 * "_links": {
 * "stats": [{
 * "href": "\/v7.1\/user_stats\/{user ID}\/?aggregate_by_period=month",
 * "id": "{user ID}",
 * "name": "month"
 * }, {
 * "href": "\/v7.1\/user_stats\/{user ID}\/?aggregate_by_period=year",
 * "id": "{user ID}",
 * "name": "year"
 * }, {
 * "href": "\/v7.1\/user_stats\/{user ID}\/?aggregate_by_period=day",
 * "id": "{user ID}",
 * "name": "day"
 * }, {
 * "href": "\/v7.1\/user_stats\/{user ID}\/?aggregate_by_period=week",
 * "id": "{user ID}",
 * "name": "week"
 * }, {
 * "href": "\/v7.1\/user_stats\/{user ID}\/?aggregate_by_period=lifetime",
 * "id": "{user ID}",
 * "name": "lifetime"
 * }],
 * "privacy": [{
 * "href": "\/v7.1\/privacy_option\/1\/",
 * "id": "1",
 * "name": "profile"
 * }, {
 * "href": "\/v7.1\/privacy_option\/1\/",
 * "id": "1",
 * "name": "workout"
 * }, {
 * "href": "\/v7.1\/privacy_option\/3\/",
 * "id": "3",
 * "name": "activity_feed"
 * }, {
 * "href": "\/v7.1\/privacy_option\/0\/",
 * "id": "0",
 * "name": "bodymass"
 * }, {
 * "href": "\/v7.1\/privacy_option\/1\/",
 * "id": "1",
 * "name": "food_log"
 * }, {
 * "href": "\/v7.1\/privacy_option\/3\/",
 * "id": "3",
 * "name": "email_search"
 * }, {
 * "href": "\/v7.1\/privacy_option\/1\/",
 * "id": "1",
 * "name": "route"
 * }, {
 * "href": "\/v7.1\/privacy_option\/0\/",
 * "id": "0",
 * "name": "sleep"
 * }, {
 * "href": "\/v7.1\/privacy_option\/0\/",
 * "id": "0",
 * "name": "workout_music"
 * }],
 * "image": [{
 * "href": "\/v7.1\/user_profile_photo\/{user ID}\/",
 * "id": "{user ID}",
 * "name": "user_profile_photo"
 * }],
 * "documentation": [{
 * "href": "https:\/\/developer.underarmour.com\/docs\/v71_User"
 * }],
 * "deactivation": [{
 * "href": "\/v7.1\/user_deactivation\/"
 * }],
 * "user_achievements": [{
 * "href": "\/v7.1\/user_achievement\/?user={user ID}"
 * }],
 * "friendships": [{
 * "href": "\/v7.1\/friendship\/?from_user={user ID}"
 * }],
 * "workouts": [{
 * "href": "\/v7.1\/workout\/?user={user ID}&order_by=-start_datetime"
 * }],
 * "self": [{
 * "href": "\/v7.1\/user\/`{user ID}\/",
 * "id": "{user ID}"
 * }]
 * },
 * "email": "email@emaildomain.com",
 * "location": {
 * "country": "US",
 * "region": "CO",
 * "locality": "Austin",
 * "address": ""
 * },
 * "username": "First{user ID}",
 * "sharing": {
 * "twitter": false,
 * "facebook": false
 * },
 * "last_initial": "A.",
 * "gender": "M",
 * "time_zone": "America\/Austin",
 * "birthdate": "1983-05-05",
 * "profile_statement": "",
 * "preferred_language": "en-US"
 * }
 */
class UnderArmourProfile extends OAuth20Profile {

  val UNDERARMOUR_BASE_URL = "https://api.ua.com/v7.1"
  val UNDERARMOUR_SELF_PROFILE_URL = s"${UNDERARMOUR_BASE_URL}/user/self/"

  override protected val getAttributesDefinition: AttributesDefinition = UnderArmourAttributesDefinition

  private def getString(name: String): String = {
    getAttribute(name).asInstanceOf[String]
  }

  private def get[T](name: String): T = {
    getAttribute(name).asInstanceOf[T]
  }

  override def getFirstName: String = {
    getString(UnderArmourAttributesDefinition.FIRST_NAME)
  }

  override def getFamilyName: String = {
    getString(UnderArmourAttributesDefinition.LAST_NAME)
  }

  override def getDisplayName: String = {
    getString(UnderArmourAttributesDefinition.DISPLAY_NAME)
  }

  override def getEmail: String = {
    getString(UnderArmourAttributesDefinition.EMAIL)
  }

  override def getPictureUrl: String = s"${UNDERARMOUR_BASE_URL}/user_profile_photo/${getId}"

  override def getProfileUrl: String = s"${UNDERARMOUR_BASE_URL}/user/${getId}"

  override def getGender: Gender = {
    val gender = getString(UnderArmourAttributesDefinition.GENDER)
    if ("M".equals(gender)) {
      Gender.MALE
    } else if ("F".equals(gender)) {
      Gender.FEMALE
    } else {
      Gender.UNSPECIFIED
    }
  }

  override def getLocation: String = {
    getFullLocation.locality
  }

  def getFullLocation: UnderArmourLocation = {
    get(UnderArmourAttributesDefinition.LOCATION)
  }
}
