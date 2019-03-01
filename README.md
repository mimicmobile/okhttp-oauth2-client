# OAuth2PasswordClient

This library provides an Android oAuth2 library with resource owner password grant types. This fork includes a set of improvements
and converts to Kotlin the original library, which can be found [here](https://github.com/mimicmobile/okhttp-oauth2-client).

## Gradle

This fork isn't available on any public Gradle repository.

## Usage

```kotlin
val client = OAuth2Client.Builder("client-id", "client-secret", "site").withUsername("username").withPassword("password").withScope("scope").withGrantType(GrantType.Password).build()
val response = client.requestAccessToken()

if (response.isSuccessful()) {
    val accessToken = response.getAccessToken()
    val refreshToken = response.getRefreshToken()
} else {
    val error = response.getOAuthError()
    val errorMsg = error.getError()
}

response.getCode()   // HTTP Status code
```

To refresh a token (defaults to `refresh_token` grant type)

```kotlin
val client = OAuth2Client.Builder("client-id", "client-secret", "site").withScope("scope").build()
val response = client.refreshAccessToken("refresh-token")
val accessToken = response.getAccessToken()
```

### Builder options and parameters

Provide your own OkHttpClient to the builder

```kotlin
val client = OkHttpClient()

val builder = OAuth2Client.Builder("client-id", "client-secret", "site")
        .usingOkHttpClient(client)
```

### Acknowledgments

This library is merely a conversion to Kotlin, with some small improvements, of the original fork.

### Issues
Please, report any issue to the original fork. This fork, even if it's being used in a Production environment,
is not officially supported by either me or the author of the original fork.

