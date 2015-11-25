package org.scribe.extractors

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import org.scribe.exceptions.OAuthException
import org.scribe.model.Token
import org.scribe.utils.Preconditions

import java.io.IOException

/**
 * Json token extractor for UnderArmour using Jackson to parse the response.
 */
class UnderArmourJsonExtractor extends AccessTokenExtractor {

  private val OAUTH_EXCEPTION_INVALID_TOKEN_MESSAGE = "Response body is incorrect. Can't extract a token from this: '"

  /**
   * Object mapper needed to extract the token from the UA response.
   */
  val objectMapper = new ObjectMapper()
  objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

  def extract(response: String): Token = {
    Preconditions.checkEmptyString(response, "Response body is incorrect. Can't extract a token from an empty string")

    try {
      val accessToken = objectMapper.readValue(response, classOf[UnderArmourAccessToken])
      if (accessToken == null || accessToken.getAccessToken == null) {
        throw new OAuthException(OAUTH_EXCEPTION_INVALID_TOKEN_MESSAGE + response + "'", null)
      }
      val accessTokenValue = accessToken.getAccessToken
      new Token(accessTokenValue, "", response)
    } catch {
      case e: IOException => throw new OAuthException(OAUTH_EXCEPTION_INVALID_TOKEN_MESSAGE + response + "'", null)
    }
  }
}

private class UnderArmourAccessToken {
  /**
   * the access_token json property
   */
  @JsonProperty("access_token")
  private var accessToken: String = ""

  def getAccessToken: String = {
    accessToken
  }

  def setAccessToken(accessToken: String): Unit = {
    this.accessToken = accessToken
  }
}
