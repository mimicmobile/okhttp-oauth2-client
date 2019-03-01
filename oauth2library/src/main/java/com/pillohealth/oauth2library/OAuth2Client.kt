package com.pillohealth.oauth2library

@Suppress("RedundantVisibilityModifier")
public interface OAuth2Client {

    fun requestAccessToken(): OAuthResponse

    fun refreshAccessToken(refreshToken: String): OAuthResponse

}