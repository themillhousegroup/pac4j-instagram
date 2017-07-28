package org.scribe.builder.api

import com.github.scribejava.core.builder.api.DefaultApi20
import com.github.scribejava.core.model.{ OAuthConstants, OAuthConfig, Verb }
import com.github.scribejava.core.utils.{ OAuthEncoder, Preconditions }

object InstagramApi {

  val INSTAGRAM_BASE_URL = "https://api.instagram.com"

  val INSTAGRAM_API_URL = s"${INSTAGRAM_BASE_URL}/v1"

  val INSTAGRAM_OAUTH_URL = s"${INSTAGRAM_BASE_URL}/oauth"

  private val AUTHORIZE_URL = s"${INSTAGRAM_OAUTH_URL}/authorize/?client_id=%s&redirect_uri=%s&response_type=code"

  private val ACCESS_TOKEN_URL = s"${INSTAGRAM_OAUTH_URL}/access_token"
}

/**
 * This class represents the OAuth API implementation for Instagram.
 */
class InstagramApi extends DefaultApi20 {
  import InstagramApi._

  override val getAccessTokenEndpoint: String = ACCESS_TOKEN_URL

  override def getAuthorizationUrl(config: OAuthConfig): String = {
    Preconditions.checkValidUrl(config.getCallback, "Must provide a valid callback url.")

    val basicUrl = String.format(AUTHORIZE_URL,
      config.getApiKey,
      OAuthEncoder.encode(config.getCallback)
    )

    if (config.hasScope) {
      s"${basicUrl}&${OAuthConstants.SCOPE}=${OAuthEncoder.encode(config.getScope)}"
    } else {
      basicUrl
    }
  }
}
