@file:Suppress("RedundantVisibilityModifier")

package com.pillohealth.oauth2library

internal object Constants {
    const val GRANT_TYPE_REFRESH = "refresh_token"
    const val GRANT_TYPE_PASSWORD = "password"

    const val POST_GRANT_TYPE = "grant_type"
    const val POST_USERNAME = "username"
    const val POST_PASSWORD = "password"
    const val POST_CLIENT_ID = "client_id"
    const val POST_CLIENT_SECRET = "client_secret"
    const val POST_SCOPE = "scope"
    const val POST_REFRESH_TOKEN = "refresh_token"

    const val HEADER_AUTHORIZATION = "Authorization"
}
