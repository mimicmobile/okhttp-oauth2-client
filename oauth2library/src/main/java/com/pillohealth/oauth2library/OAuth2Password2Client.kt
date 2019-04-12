package com.pillohealth.oauth2library

import android.os.AsyncTask
import okhttp3.OkHttpClient
import java.util.*

@Suppress("MemberVisibilityCanBePrivate", "unused", "RedundantVisibilityModifier")
open class OAuth2Password2Client private constructor(builder: Builder) : OAuth2Client {

    public class Builder(internal val clientId: String, internal val site: String) {

        internal var scope: String? = null
        internal var clientSecret: String? = null
        internal var grantType: String? = null

        internal var username: String? = null
        internal var password: String? = null

        internal var okHttpClient: OkHttpClient? = null

        internal var parameters: Map<String, String>? = null

        public fun withClientSecret(clientSecret: String) = apply { this.clientSecret = clientSecret }

        public fun withGrantType(grantType: GrantType) = apply { this.grantType = grantType.toString() }

        public fun withScope(scope: String) = apply { this.scope = scope }

        public fun withUsername(username: String) = apply { this.username = username }

        public fun withPassword(password: String) = apply { this.password = password }

        public fun usingOkHttpClient(client: OkHttpClient) = apply { this.okHttpClient = client }

        public fun withParameters(parameters: Map<String, String>) = apply { this.parameters = parameters }

        public fun build() = OAuth2Password2Client(this)

    }

    val clientId: String
    val clientSecret: String?
    val site: String
    private var okHttpClient: OkHttpClient?

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
            clientSecret?.let {
                oAuthParams[Constants.POST_CLIENT_SECRET] = it
            }
            grantType?.let {
                oAuthParams[Constants.POST_GRANT_TYPE] = it
            }
            scope?.let {
                oAuthParams[Constants.POST_SCOPE] = it
            }
            username?.let {
                oAuthParams[Constants.POST_USERNAME] = it
            }
            password?.let {
                oAuthParams[Constants.POST_PASSWORD] = it
            }
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

    fun refreshAccessToken(refreshToken: String, callback: OAuthResponseCallback) =
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

    fun requestAccessToken(callback: OAuthResponseCallback) =
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

    fun getOkHttpClient(): OkHttpClient {
        if (this.okHttpClient == null) this.okHttpClient = OkHttpClient()
        return this.okHttpClient!!
    }

    fun getParameters(): Map<String, String> = parameters ?: HashMap()

}
