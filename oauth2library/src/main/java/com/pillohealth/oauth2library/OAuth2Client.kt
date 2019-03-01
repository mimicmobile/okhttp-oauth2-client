package com.pillohealth.oauth2library

@Suppress("RedundantVisibilityModifier")
public interface OAuth2Client {

    fun requestAccessToken(): OAuthResponse
    fun requestAccessToken(callback: OAuthResponseCallback)

    fun refreshAccessToken(refreshToken: String): OAuthResponse
    fun refreshAccessToken(refreshToken: String, callback: OAuthResponseCallback)

}