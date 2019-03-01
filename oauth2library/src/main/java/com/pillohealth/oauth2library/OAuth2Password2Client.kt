package com.pillohealth.oauth2library

import android.os.AsyncTask
import okhttp3.OkHttpClient
import java.util.*

@Suppress("MemberVisibilityCanBePrivate", "unused", "RedundantVisibilityModifier")
open class OAuth2Password2Client private constructor(builder: Builder) : OAuth2Client {

    class Builder(internal val clientId: String, internal val clientSecret: String, internal val site: String) {

        internal var scope: String? = null
        internal var grantType: String? = null

        internal var username: String? = null
        internal var password: String? = null

        internal var okHttpClient: OkHttpClient? = null

        internal var parameters: Map<String, String>? = null

        fun withGrantType(grantType: GrantType) = apply { this.grantType = grantType.toString() }

        fun withScope(scope: String) = apply { this.scope = scope }

        fun withUsername(username: String) = apply { this.username = username }

        fun withPassword(password: String) = apply { this.password = password }

        fun usingOkHttpClient(client: OkHttpClient) = apply { this.okHttpClient = client }

        fun withParameters(parameters: Map<String, String>) = apply { this.parameters = parameters }

        fun build() = OAuth2Password2Client(this)

    }

    val clientId: String
    val clientSecret: String
    val site: String
    internal val okHttpClient: OkHttpClient?

    val scope: String?
    var grantType: String? = null
        protected set

    val username: String?
    val password: String?

    internal val parameters: Map<String, String>?

    internal val fieldsAsMap: Map<String, String>
        get() {
            val oAuthParams = HashMap<String, String>()
            oAuthParams[Constants.POST_CLIENT_ID] = clientId
            oAuthParams[Constants.POST_CLIENT_SECRET] = clientSecret
            oAuthParams[Constants.POST_GRANT_TYPE] = grantType!!
            oAuthParams[Constants.POST_SCOPE] = scope!!
            oAuthParams[Constants.POST_USERNAME] = username!!
            oAuthParams[Constants.POST_PASSWORD] = password!!
            return oAuthParams
        }

    init {
        this.username = builder.username
        this.password = builder.password
        this.clientId = builder.clientId
        this.clientSecret = builder.clientSecret
        this.site = builder.site
        this.scope = builder.scope
        this.grantType = builder.grantType
        this.okHttpClient = builder.okHttpClient
        this.parameters = builder.parameters
    }

    override fun refreshAccessToken(refreshToken: String): OAuthResponse {
        if (this.grantType == null) this.grantType = Constants.GRANT_TYPE_REFRESH
        return Access.refreshAccessToken(refreshToken, this)
    }

    override fun refreshAccessToken(refreshToken: String, callback: OAuthResponseCallback) =
            AsyncTask.execute {
                var response: OAuthResponse
                try {
                    response = refreshAccessToken(refreshToken)
                    callback.onResponse(response)
                } catch (e: Exception) {
                    response = OAuthResponse(e)
                    callback.onResponse(response)
                }
            }

    override fun requestAccessToken(): OAuthResponse {
        if (this.grantType == null) this.grantType = Constants.GRANT_TYPE_PASSWORD
        return Access.getToken(this)
    }

    override fun requestAccessToken(callback: OAuthResponseCallback) =
            AsyncTask.execute {
                var response: OAuthResponse
                try {
                    response = requestAccessToken()
                    callback.onResponse(response)
                } catch (e: Exception) {
                    response = OAuthResponse(e)
                    callback.onResponse(response)
                }
            }

    protected fun getOkHttpClient() = this.okHttpClient ?: OkHttpClient()

    fun getParameters(): Map<String, String> = parameters ?: HashMap()

}
