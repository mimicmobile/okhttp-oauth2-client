package com.pillohealth.oauth2library

import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request

internal object Access {

    internal fun refreshAccessToken(token: String, oAuth2PasswordClient: OAuth2Password2Client): OAuthResponse {
        val formBodyBuilder = FormBody.Builder()
                .add(Constants.POST_REFRESH_TOKEN, token)

        Utils.postAddIfValid(formBodyBuilder, oAuth2PasswordClient.fieldsAsMap)

        val request = Request.Builder()
                .url(oAuth2PasswordClient.site)
                .post(formBodyBuilder.build())
                .build()

        return refreshTokenFromResponse(oAuth2PasswordClient, request)
    }

    private fun refreshTokenFromResponse(oAuth2PasswordClient: OAuth2Password2Client,
                                         request: Request): OAuthResponse {
        return getTokenFromResponse(oAuth2PasswordClient, oAuth2PasswordClient.getOkHttpClient(),
                request, AuthState(AuthState.REFRESH_TOKEN))

    }

    internal fun getToken(oAuth2PasswordClient: OAuth2Password2Client): OAuthResponse {
        val formBodyBuilder = FormBody.Builder()
        Utils.postAddIfValid(formBodyBuilder, oAuth2PasswordClient.fieldsAsMap)
        Utils.postAddIfValid(formBodyBuilder, oAuth2PasswordClient.parameters)

        return getAccessToken(oAuth2PasswordClient, formBodyBuilder)
    }

    private fun getAccessToken(oAuth2PasswordClient: OAuth2Password2Client, formBodyBuilder: FormBody.Builder): OAuthResponse {
        val request = Request.Builder()
                .url(oAuth2PasswordClient.site)
                .post(formBodyBuilder.build())
                .build()

        return getTokenFromResponse(oAuth2PasswordClient, request)
    }

    private fun getTokenFromResponse(oAuth2PasswordClient: OAuth2Password2Client,
                                     request: Request): OAuthResponse {
        return getTokenFromResponse(oAuth2PasswordClient, oAuth2PasswordClient.getOkHttpClient(),
                request, AuthState(AuthState.ACCESS_TOKEN))

    }

    private fun getTokenFromResponse(oAuth2PasswordClient: OAuth2Password2Client,
                                     okHttpClient: OkHttpClient,
                                     request: Request,
                                     authState: AuthState): OAuthResponse {
        val response = okHttpClient.newBuilder().authenticator(Utils.getAuthenticator(oAuth2PasswordClient, authState))
                .build().newCall(request).execute()

        return OAuthResponse(response)
    }

}
