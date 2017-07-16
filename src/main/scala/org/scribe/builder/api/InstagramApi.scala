package org.scribe.builder.api

import org.scribe.extractors.AccessTokenExtractor
import org.scribe.extractors.InstagramJsonExtractor
import org.scribe.model.OAuthConfig
import org.scribe.model.Verb
import org.scribe.utils.OAuthEncoder
import org.scribe.utils.Preconditions

object InstagramApi {
  /**
   * Instagram authorization URL
   */
  private val AUTHORIZE_URL = "https://api.instagram.com/oauth/authorize/?client_id=%s&redirect_uri=%s&response_type=code"

  private val ACCESS_TOKEN_URL = "https://api.instagram.com/oauth/access_token"

  /**
   * Need to redefine the token extractor, because the token comes from Instagram in json format.
   */
  private val ACCESS_TOKEN_EXTRACTOR: AccessTokenExtractor = new InstagramJsonExtractor()
}

/**
 * This class represents the OAuth API implementation for UnderArmour.
 */
class InstagramApi extends DefaultApi20 {
  import InstagramApi._

  override val getAccessTokenEndpoint: String = ACCESS_TOKEN_URL

  override val getAccessTokenVerb: Verb = Verb.POST

  override val getAccessTokenExtractor: AccessTokenExtractor = ACCESS_TOKEN_EXTRACTOR

  override def getAuthorizationUrl(config: OAuthConfig): String = {
    Preconditions.checkValidUrl(config.getCallback, "Must provide a valid callback url.")

    String.format(AUTHORIZE_URL,
      config.getApiKey,
      OAuthEncoder.encode(config.getCallback)
    )
  }
}
