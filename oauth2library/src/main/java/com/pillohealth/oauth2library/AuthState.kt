package com.pillohealth.oauth2library

internal class AuthState(tokenType: Int) {

    private var state: IntArray? = null

    private var position: Int = 0

    val isFinalAuth: Boolean
        get() = state!![position] == FINAL_AUTH

    val isBasicAuth: Boolean
        get() = state!![position] == BASIC_AUTH

    val isAuthorizationAuth: Boolean
        get() = state!![position] == AUTHORIZATION_AUTH

    init {
        state = when (tokenType) {
            ACCESS_TOKEN -> ACCESS_STATES
            REFRESH_TOKEN -> REFRESH_STATES
            else -> ACCESS_STATES
        }
    }

    fun nextState() {
        position++
    }

    companion object {
        internal const val ACCESS_TOKEN = 0
        internal const val REFRESH_TOKEN = 1

        private const val NO_AUTH = 0
        private const val BASIC_AUTH = 1
        private const val AUTHORIZATION_AUTH = 2
        private const val FINAL_AUTH = 3

        private val ACCESS_STATES = intArrayOf(NO_AUTH, BASIC_AUTH, AUTHORIZATION_AUTH, FINAL_AUTH)

        private val REFRESH_STATES = intArrayOf(NO_AUTH, AUTHORIZATION_AUTH, FINAL_AUTH)
    }
}
