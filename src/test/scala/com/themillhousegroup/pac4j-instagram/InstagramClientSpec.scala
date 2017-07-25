package com.themillhousegroup.pac4jinstagram

import org.specs2.mutable.Specification

class InstagramClientSpec extends Specification {
  "Instagram client" should {
    "be instantiable" in {
      val ic = new InstagramClient("key", "secret")
      ic must not beNull
    }

  }
}
