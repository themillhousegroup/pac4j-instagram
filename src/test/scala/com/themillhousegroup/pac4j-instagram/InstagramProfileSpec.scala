package com.themillhousegroup.pac4jinstagram

import org.specs2.mutable.Specification
import com.themillhousegroup.pac4jinstagram.test.ProfileFixtures._

class InstagramProfileSpec extends Specification {
  "Instagram profile builder" should {
    "be able to create a non-null InstagramProfile from a String" in {
      val p = InstagramProfileBuilder.createFromString(fullProfile)
      p must not beNull
    }

    "be able to populate the basic CommonProfile fields of an InstagramProfile from a String" in {
      val p = InstagramProfileBuilder.createFromString(fullProfile)

      p.getId must beEqualTo("1574083")
      p.getUsername must beEqualTo("snoopdogg")
      p.getDisplayName must beEqualTo("Snoop Dogg")
      p.getGender must beEqualTo(org.pac4j.core.profile.Gender.UNSPECIFIED)
      p.getPictureUrl must beEqualTo("http://distillery.s3.amazonaws.com/profiles/profile_1574083_75sq_1295469061.jpg")
      p.getProfileUrl must beEqualTo("https://api.instagram.com/v1/users/1574083")
    }
  }
}
