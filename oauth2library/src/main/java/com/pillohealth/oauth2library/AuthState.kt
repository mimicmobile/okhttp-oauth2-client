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
        when (tokenType) {
            ACCESS_TOKEN -> state = ACCESS_STATES
            REFRESH_TOKEN -> state = REFRESH_STATES
            else -> state = ACCESS_STATES
        }
    }

    fun nextState() {
        position++
    }

    companion object {
        internal val ACCESS_TOKEN = 0
        internal val REFRESH_TOKEN = 1

        private val NO_AUTH = 0
        private val BASIC_AUTH = 1
        private val AUTHORIZATION_AUTH = 2
        private val FINAL_AUTH = 3

        private val ACCESS_STATES = intArrayOf(NO_AUTH, BASIC_AUTH, AUTHORIZATION_AUTH, FINAL_AUTH)

        private val REFRESH_STATES = intArrayOf(NO_AUTH, AUTHORIZATION_AUTH, FINAL_AUTH)
    }
}
