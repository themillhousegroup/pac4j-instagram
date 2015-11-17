package org.scribe.builder.api

import org.scribe.extractors.AccessTokenExtractor
import org.scribe.extractors.StravaJsonExtractor
import org.scribe.model.OAuthConfig
import org.scribe.model.Verb
import org.scribe.utils.OAuthEncoder
import org.scribe.utils.Preconditions

object UnderArmourApi {
  /**
   * UnderArmour authorization URL
   */
  private val AUTHORIZE_URL = "https://www.mapmyfitness.com/v7.1/oauth2/uacf/authorize/?response_type=code&client_id=%s&redirect_uri=%s"

  private val ACCESS_TOKEN_URL = "https://oauth2-api.mapmyapi.com/v7.1/oauth2/uacf/access_token/"

  /**
   * Need to redefine the token extractor, because the token comes from Strava in json format.
   */
  private val ACCESS_TOKEN_EXTRACTOR: AccessTokenExtractor = new StravaJsonExtractor();
}

/**
 * This class represents the OAuth API implementation for UnderArmour.
 */
class UnderArmourApi extends DefaultApi20 {
  import UnderArmourApi._

  override val getAccessTokenEndpoint: String = ACCESS_TOKEN_URL

  override val getAccessTokenVerb: Verb = Verb.POST

  override val getAccessTokenExtractor: AccessTokenExtractor = ACCESS_TOKEN_EXTRACTOR

  override def getAuthorizationUrl(config: OAuthConfig): String = {
    Preconditions.checkValidUrl(config.getCallback(), "Must provide a valid callback url.")

    String.format(AUTHORIZE_URL,
      config.getApiKey,
      OAuthEncoder.encode(config.getCallback)
    )
  }
}
