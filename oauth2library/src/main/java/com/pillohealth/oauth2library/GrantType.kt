package com.pillohealth.oauth2library

@Suppress("RedundantVisibilityModifier")
public enum class GrantType {

    Refresh {
        override fun toString(): String {
            return Constants.GRANT_TYPE_REFRESH
        }
    },
    Password {
        override fun toString(): String {
            return Constants.GRANT_TYPE_PASSWORD
        }
    };

    abstract override fun toString(): String

}