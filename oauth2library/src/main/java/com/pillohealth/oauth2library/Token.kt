package com.pillohealth.oauth2library

@Suppress("PropertyName")
class Token {
    var expires_in: Long? = null
    var token_type: String? = null
    var refresh_token: String? = null
    var access_token: String? = null
    var scope: String? = null
}
