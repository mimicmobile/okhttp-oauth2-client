# OAuth2 client

## Overview

A modern oAuth2 library using OkHttp with resource owner password grant types and easy token refreshing.
This library aims to provide a solution for the less commonly used resource owner password grant type as well as providing dynamic parameter support that can be used with frameworks that allow for more flexible and dynamic oAuth2 parameters (such as the [Django REST framework social oAuth2 library](https://github.com/PhilipGarnero/django-rest-framework-social-oauth2))

## Usage

Grab a resource owner password grant type token

```java
import ca.mimic.oauth2library.OAuth2Client;
import ca.mimic.oauth2library.Token;

OAuth2Client client = new OAuth2Client.Builder("username", "password", "client-id", "client-secret", "site").build();
Token token = client.getAccessToken();

```

To refresh a token (defaults to "refresh_token" grant type)

```java
OAuth2Client client = new OAuth2Client.Builder("client-id", "client-secret", "site").build();
Token token = client.refreshAccessToken("refresh-token");
```

### Builder options and parameters

You can provide custom parameters to the builder

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
client = builder.build();

Observable<Token> observable = Observable.fromCallable(new Callable<Token>() {
    @Override
    public Token call() throws Exception {
        return client.refreshAccessToken("refresh-token");
    }
});

observable.subscribe(new Subscriber<Token>() {
    @Override
    public void onCompleted() {
    }

    @Override
    public void onError(Throwable e) {
    }

    @Override
    public void onNext(Token token) {
        token.getAccessToken();
    }
});
```

### **OAuthException**
This library wraps JSON Exceptions and other errors in its own OAuthException
```java
try {
    Token token = client.refreshAccessToken("");
} catch (OAuthException e) {
    Response okHttpResponse = e.getResponse();
    okHttpResponse.code();                // Http status code
    okHttpResponse.isSuccessful();
    
    String body = e.getResponseBody();    // Response body
    
    JSONException jsonException = e.getJsonException();
}
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

