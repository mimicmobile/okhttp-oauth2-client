package com.pillohealth.oauth2library

import com.squareup.moshi.Moshi

import java.io.IOException

import okhttp3.Response

@Suppress("unused", "RedundantVisibilityModifier")
open class OAuthResponse {

    var httpResponse: Response? = null
        private set
    var body: String? = null
        private set
    private var token: Token? = null
    var oAuthError: OAuthError? = null
        private set
    var isJsonResponse: Boolean = false
        private set
    var expiresAt: Long? = null
        private set

    public val isSuccessful: Boolean
        get() = httpResponse != null && httpResponse!!.isSuccessful && isJsonResponse

    public val code: Int?
        get() = if (httpResponse != null) httpResponse!!.code() else null

    public val expiresIn: Long?
        get() = if (token != null) token!!.expires_in else null

    public val tokenType: String?
        get() = if (token != null) token!!.token_type else null

    public val refreshToken: String?
        get() = if (token != null) token!!.refresh_token else null

    public val accessToken: String?
        get() = if (token != null) token!!.access_token else null

    public val scope: String?
        get() = if (token != null) token!!.scope else null

    internal constructor(response: Response?) {
        this.httpResponse = response
        response?.let {
            body = response.body()!!.string()

            if (Utils.isJsonResponse(response)) {
                val moshi = Moshi.Builder().build()
                if (response.isSuccessful) {
                    token = moshi.adapter(Token::class.java).fromJson(body!!)
                    isJsonResponse = true

                    if (token!!.expires_in != null) expiresAt = token!!.expires_in!! * 1000 + System.currentTimeMillis()
                } else {
                    try {
                        oAuthError = moshi.adapter(OAuthError::class.java).fromJson(body!!)
                        isJsonResponse = true
                    } catch (e: Exception) {
                        oAuthError = OAuthError(e)
                        isJsonResponse = false
                    }

                }
            }
        }
    }

    internal constructor(e: Exception) {
        this.httpResponse = null
        this.oAuthError = OAuthError(e)
    }

}
