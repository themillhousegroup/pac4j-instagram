package com.themillhousegroup.pac4junderarmour

import org.specs2.mutable.Specification

class UnderArmourClientSpec extends Specification {
  "UnderArmour client" should {
    "be instantiable" in {
      val uac = new UnderArmourClient("key", "secret")
      uac must not beNull
    }

    "be instantiable" in {
      val uac = new UnderArmourClient("key", "secret")
      uac must not beNull
    }
  }
}
