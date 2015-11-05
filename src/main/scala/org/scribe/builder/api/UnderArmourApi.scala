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
  private val AUTHORIZE_URL = "https://www.strava.com/oauth/authorize?approval_prompt=%s&response_type=code&client_id=%s&redirect_uri=%s"

  private val SCOPED_AUTHORIZE_URL = AUTHORIZE_URL + "&scope=%s"

  private val ACCESS_TOKEN_URL = "https://www.strava.com/oauth/token"

  /**
   * Need to redefine the token extractor, because the token comes from Strava in json format.
   */
  private val ACCESS_TOKEN_EXTRACTOR: AccessTokenExtractor = new StravaJsonExtractor();
}

/**
 * This class represents the OAuth API implementation for UnderArmour.
 */
/**
 * possible 'approvalPrompt' values: auto or force. If force, the authorisation dialog is always displayed by UnderArmour.
 */
class UnderArmourApi(approvalPrompt: String) extends DefaultApi20 {
  import UnderArmourApi._

  override val getAccessTokenEndpoint: String = ACCESS_TOKEN_URL

  override val getAccessTokenVerb: Verb = Verb.POST

  override val getAccessTokenExtractor: AccessTokenExtractor = ACCESS_TOKEN_EXTRACTOR

  override def getAuthorizationUrl(config: OAuthConfig): String = {
    Preconditions.checkValidUrl(config.getCallback(), "Must provide a valid callback url.");

    // Append scope if present
    if (config.hasScope()) {
      String.format(SCOPED_AUTHORIZE_URL, this.approvalPrompt, config.getApiKey(), OAuthEncoder.encode(config.getCallback()),
        OAuthEncoder.encode(config.getScope()))
    } else {
      String.format(AUTHORIZE_URL, this.approvalPrompt, config.getApiKey(), OAuthEncoder.encode(config.getCallback()));
    }
  }

}
