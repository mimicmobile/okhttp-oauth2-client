package com.pillohealth.oauth2library

interface OAuthResponseCallback {
    fun onResponse(response: OAuthResponse)
}
