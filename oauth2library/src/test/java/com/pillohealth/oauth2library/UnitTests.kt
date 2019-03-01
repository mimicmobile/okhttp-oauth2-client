package com.pillohealth.oauth2library

import com.squareup.moshi.Moshi

import org.junit.Test

import java.io.IOException

import org.junit.Assert.*

class UnitTests {

    @Test
    @Throws(Exception::class)
    fun additionIsCorrect() {
        assertEquals(4, (2 + 2).toLong())
    }

    @Throws(IOException::class)
    fun createTokenFromJson(): Token? {
        val moshi = Moshi.Builder().build()
        val s = "{\"expires_in\": 2000, \"token_type\": \"bearer\", \"access_token\": \"access_token\", \"refresh_token\": \"refresh_token\", \"scope\": \"scope\"}"

        return moshi.adapter(Token::class.java).fromJson(s)
    }

    @Throws(IOException::class)
    fun createErrorFromJson(): OAuthError? {
        val moshi = Moshi.Builder().build()
        val s = "{\"error\":\"unauthorized_client\",\"error_description\":\"Invalid client or doesn't have the permission to make this request.\"}"

        return moshi.adapter(OAuthError::class.java).fromJson(s)
    }

    @Test
    @Throws(Exception::class)
    fun tokenCheck() {
        val token = createTokenFromJson()
        assertEquals("access_token", token!!.access_token)
        assertEquals("refresh_token", token.refresh_token)
        assertEquals("bearer", token.token_type)
        assertEquals("scope", token.scope)
        assertEquals(2000, token.expires_in as Long)
    }

    @Test
    @Throws(Exception::class)
    fun errorCheck() {
        val error = createErrorFromJson()
        assertEquals("unauthorized_client", error!!.error)
        assertEquals("Invalid client or doesn't have the permission to make this request.", error.errorDescription)
        assertEquals(null, error.errorUri)
        assertEquals(null, error.errorException)
    }
}