package com.pillohealth.oauth2library

class OAuthError(e: Exception) {
    var error: String? = null
        private set
    var errorDescription: String? = null
        private set
    var errorUri: String? = null
        private set

    @Transient
    var errorException: Exception
        private set

    init {
        errorException = e
    }
}
