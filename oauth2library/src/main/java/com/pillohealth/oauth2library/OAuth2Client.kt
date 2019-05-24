package com.pillohealth.oauth2library

import java.io.IOException

@Suppress("RedundantVisibilityModifier")
public interface OAuth2Client {

    @Throws(IOException::class)
    fun requestAccessToken(): OAuthResponse

    @Throws(IOException::class)
    fun refreshAccessToken(refreshToken: String): OAuthResponse

}