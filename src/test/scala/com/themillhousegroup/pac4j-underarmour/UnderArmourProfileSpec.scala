package com.themillhousegroup.pac4junderarmour

import org.specs2.mutable.Specification
import com.themillhousegroup.pac4junderarmour.test.ProfileFixtures._

class UnderArmourProfileSpec extends Specification {
  "UnderArmour profile builder" should {
    "be able to create a non-null UnderArmourProfile from a String" in {
      val p = UnderArmourProfileBuilder.createFromString(fullProfile)
      p must not beNull
    }

    "be able to populate the basic CommonProfile fields of an UnderArmourProfile from a String" in {
      val p = UnderArmourProfileBuilder.createFromString(fullProfile)

      p.getId must beEqualTo("512262")
      p.getDisplayName must beEqualTo("FirstName 'Display' McLastName")
      p.getEmail must beEqualTo("me@myemail.com")
      p.getFamilyName must beEqualTo("McLastName")
      p.getFirstName must beEqualTo("FirstName")
      p.getGender must beEqualTo(org.pac4j.core.profile.Gender.MALE)
      p.getLocale must beNull // beEqualTo(new java.util.Locale("EN", "AU"))
      p.getLocation must beEqualTo("Locality")
      p.getPictureUrl must beEqualTo("https://api.ua.com/v7.1/user_profile_photo/512262")
      p.getProfileUrl must beEqualTo("https://api.ua.com/v7.1/user/512262")
      p.getUsername must beEqualTo("myusername")
    }

    "be able to populate the specific fields of an UnderArmourProfile from a String" in {
      val p = UnderArmourProfileBuilder.createFromString(fullProfile)

      val fullLocation = new UnderArmourLocation()
      fullLocation.country = "AU"
      fullLocation.region = "Vic"
      fullLocation.locality = "Locality"
      fullLocation.address = "3476 Address-Field Street"

      p.getFullLocation must beEqualTo(fullLocation)
    }
  }
}
