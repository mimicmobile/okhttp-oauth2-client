package com.pillohealth.oauth2library

import okhttp3.Authenticator
import okhttp3.Credentials
import okhttp3.FormBody
import okhttp3.Response

internal object Utils {

    fun isJsonResponse(response: Response) = response.body() != null && response.body()!!.contentType()!!.subtype() == "json"

    fun getAuthenticator(oAuth2PasswordClient: OAuth2Password2Client, authState: AuthState): Authenticator =
            Authenticator { _, response ->
                var credential = ""

                authState.nextState()

                when {
                    authState.isBasicAuth -> credential = Credentials.basic(oAuth2PasswordClient.username!!, oAuth2PasswordClient.password!!)
                    authState.isAuthorizationAuth -> credential = Credentials.basic(oAuth2PasswordClient.clientId, oAuth2PasswordClient.clientSecret)
                    authState.isFinalAuth -> return@Authenticator null
                }


                println("Authenticating for response: $response")
                println("Challenges: " + response.challenges())
                response.request().newBuilder()
                        .header(Constants.HEADER_AUTHORIZATION, credential)
                        .build()
            }

    fun postAddIfValid(formBodyBuilder: FormBody.Builder, params: Map<String, String>?) {
        if (params == null) return

        for ((key, value) in params) {
            if (isValid(value)) {
                formBodyBuilder.add(key, value)
            }
        }
    }

    private fun isValid(s: String?) = s != null && s.trim { it <= ' ' }.isNotEmpty()

}
