package com.themillhousegroup.pac4junderarmour

import org.specs2.mutable.Specification
import org.scribe.oauth._

class UnderArmourClientSpec extends Specification {
  "UnderArmour client" should {
    "be instantiable" in {
      val uac = new UnderArmourClient("key", "secret")
      uac must not beNull
    }

    "be initializable by pac4j calling init()" in {
      val uac = new UnderArmourClient("key", "secret")
      uac.setCallbackUrl("http://callbackUrl")
      uac.init // will throw if things are not right
      uac must not beNull
    }

    "Support providing a custom callback URL" in {
      val uac = new UnderArmourClient("key", "secret", "/custom-callback-url")
      uac.setCallbackUrl("http://callbackUrl")
      uac.init // will throw if things are not right

      uac must not beNull

      val svc = uac.getService.asInstanceOf[ProxyAuth20WithHeadersServiceImpl]

      val cfg = svc.getConfig

      cfg must not beNull

      cfg.getCallback must beEqualTo("http://callbackUrl/custom-callback-url")
    }

    "Providing a reasonable callback URL by default" in {
      val uac = new UnderArmourClient("key", "secret")
      uac.setCallbackUrl("http://callbackUrl")
      uac.init // will throw if things are not right

      uac must not beNull

      val svc = uac.getService.asInstanceOf[ProxyAuth20WithHeadersServiceImpl]

      val cfg = svc.getConfig

      cfg must not beNull

      cfg.getCallback must beEqualTo("http://callbackUrl/UnderArmourClient/callback")
    }
  }
}
