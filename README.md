# OkHttp OAuth2 client

A modern Android oAuth2 library using OkHttp with resource owner password grant types and easy token refreshing.
This library aims to provide a solution for the less commonly used resource owner password grant type as well as providing dynamic parameter support that can be used with frameworks that allow for more flexible and dynamic oAuth2 parameters (such as the [Django REST framework social oAuth2 library](https://github.com/PhilipGarnero/django-rest-framework-social-oauth2))

[![License](https://img.shields.io/badge/License-Apache%202.0-yellow.svg)](https://opensource.org/licenses/Apache-2.0)
[![Release](https://jitpack.io/v/corcoran/okhttp-oauth2-client.svg)] (https://jitpack.io/#corcoran/okhttp-oauth2-client)
[![Download](https://api.bintray.com/packages/corcoran/maven/ca.mimic%3Aoauth2library/images/download.svg) ](https://bintray.com/corcoran/maven/ca.mimic%3Aoauth2library/_latestVersion)

## Gradle

The Gradle dependency is available via jCenter. jCenter is the default Maven repository used by Android Studio.

```gradle
dependencies {
    // ... other dependencies here
    compile 'ca.mimic:oauth2library:2.1.0'
}
```

## Usage

```java
OAuth2Client client = new OAuth2Client.Builder("username", "password", "client-id", "client-secret", "site").build();
OAuthResponse response = client.requestAccessToken();

if (response.isSuccessful()) {
    String accessToken = response.getAccessToken();
    String refreshToken = response.getRefreshToken();
} else {
    OAuthError error = response.getOAuthError();
    String errorMsg = error.getError();
}

response.getCode();   // HTTP Status code
```

To refresh a token (defaults to "refresh_token" grant type)

```java
OAuth2Client client = new OAuth2Client.Builder("client-id", "client-secret", "site").build();
OAuthResponse response = client.refreshAccessToken("refresh-token");
String accessToken = response.getAccessToken();
```

### Callbacks

```java
client.requestAccessToken(new OAuthResponseCallback() {
    @Override
    public void onResponse(OAuthResponse response) {
        if (response.isSuccessful()) {
            // response.getAccessToken();
        } else {
            // response.getOAuthError();
        }
    }
});
```

### Builder options and parameters

Parameters for the builder

```java
OAuth2Client.Builder builder = new OAuth2Client.Builder("client-id", "client-secret", "site")
        .grantType("grant-type")
        .scope("scope")
        .username("username")
        .password("password");
```

Provide your own OkHttpClient to the builder

```java
OkHttpClient client = new OkHttpClient();

OAuth2Client.Builder builder = new OAuth2Client.Builder("client-id", "client-secret", "site")
        .okHttpClient(client);
```

Provide additional name / value string parameters

```java
Map<String, String> map = new HashMap<>();
map.put("backend", "example-backend");

OAuth2Client.Builder builder = new OAuth2Client.Builder("client-id", "client-secret", "site")
        .parameters(map)
```

Wrap with RxJava!

```java
OAuth2Client.Builder builder = new OAuth2Client.Builder("client-id", "client-secret", "http://localhost:8000/auth/token");
final OAuth2Client client = builder.build();

Observable<OAuthResponse> observable = Observable.fromCallable(new Callable<OAuthResponse>() {
    @Override
    public OAuthResponse call() throws Exception {
        return client.refreshAccessToken("refresh-token");
    }
});

observable.subscribe(new Action1<OAuthResponse>() {
    @Override
    public void call(OAuthResponse oAuthResponse) {
        oAuthResponse.getAccessToken();
    }
});
```

### Extra response data
OAuthResponse contains other potentially helpful data
```java
OAuthResponse response = client.requestAccessToken();
response.getHttpResponse();   // OkHttp response
response.getBody();           // Response body string
response.isJsonResponse();    // Was JSON parsed?
```

### Acknowledgments

This library was inspired by the [android-oauth2-client](https://github.com/danielsz/android-oauth2-client) library by [Daniel Szmulewicz](https://github.com/danielsz)

## License

```
Copyright 2017 Jeff Corcoran

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

