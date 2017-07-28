package org.scribe.builder.api

import com.github.scribejava.core.builder.api.DefaultApi20
import com.github.scribejava.core.model.{ OAuthConfig, Verb }
import com.github.scribejava.core.utils.{ OAuthEncoder, Preconditions }

object InstagramApi {

  val INSTAGRAM_BASE_URL = "https://api.instagram.com"

  val INSTAGRAM_API_URL = "https://api.instagram.com/v1"

  /**
   * Instagram authorization URL
   */
  private val AUTHORIZE_URL = s"${INSTAGRAM_BASE_URL}/oauth/authorize/?client_id=%s&redirect_uri=%s&response_type=code"

  private val ACCESS_TOKEN_URL = s"${INSTAGRAM_BASE_URL}/oauth/access_token"
}

/**
 * This class represents the OAuth API implementation for Instagram.
 */
class InstagramApi extends DefaultApi20 {
  import InstagramApi._

  override val getAccessTokenEndpoint: String = ACCESS_TOKEN_URL

  override val getAccessTokenVerb: Verb = Verb.POST

  override def getAuthorizationUrl(config: OAuthConfig): String = {
    Preconditions.checkValidUrl(config.getCallback, "Must provide a valid callback url.")

    String.format(AUTHORIZE_URL,
      config.getApiKey,
      OAuthEncoder.encode(config.getCallback)
    )
  }
}
