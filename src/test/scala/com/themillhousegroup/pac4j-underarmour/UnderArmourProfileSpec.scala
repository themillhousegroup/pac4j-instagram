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

      p.getDisplayName must beEqualTo("FirstName 'Display' McLastName")
      p.getEmail must beEqualTo("me@myemail.com")
      p.getFamilyName must beEqualTo("McLastName")
      p.getFirstName must beEqualTo("FirstName")
      p.getGender must beEqualTo(org.pac4j.core.profile.Gender.MALE)
      p.getLocale must beEqualTo(new java.util.Locale("EN", "AU"))
      p.getLocation must beEqualTo("")
      p.getPictureUrl must beEqualTo("")
      p.getProfileUrl must beEqualTo("")
      p.getUsername must beEqualTo("myusername")
    }
  }
}