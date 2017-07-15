package com.themillhousegroup.pac4jinstagram

import org.specs2.mutable.Specification
import org.scribe.oauth._

class InstagramClientSpec extends Specification {
  "Instagram client" should {
    "be instantiable" in {
      val ic = new InstagramClient("key", "secret")
      ic must not beNull
    }

    "be initializable by pac4j calling init()" in {
      val ic = new InstagramClient("key", "secret")
      ic.setCallbackUrl("http://callbackUrl")
      ic.init // will throw if things are not right
      ic must not beNull
    }
  }
}
