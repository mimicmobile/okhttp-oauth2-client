package ca.mimic.oauth2library;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Response;

public class OAuthResponse {
    private Response response;
    private String responseBody;
    private Token token;
    private OAuthError error;
    private boolean jsonParsed;

    protected OAuthResponse(Response response) throws IOException {
        this.response = response;
        if (response != null) {
            responseBody = response.body().string();

            if (Utils.isJsonResponse(response)) {
                if (response.isSuccessful()) {
                    token = new Gson().fromJson(responseBody, Token.class);
                } else {
                    error = new Gson().fromJson(responseBody, OAuthError.class);
                }

                jsonParsed = true;
            }
        }
    }

    public boolean isSuccessful() {
        return response != null && response.isSuccessful() && jsonParsed;
    }

    public boolean isJsonResponse() {
        return jsonParsed;
    }

    public Integer getCode() {
        return response != null ? response.code(): null;
    }

    public Long getExpiresIn() {
        return token != null ? (token.expires_at * 1000) + System.currentTimeMillis() :
                System.currentTimeMillis();
    }

    public Long getExpiresAt() {
        return token != null ? token.expires_at : null;
    }

    public String getTokenType() {
        return token != null ? token.token_type : null;
    }

    public String getRefreshToken() {
        return token != null ? token.refresh_token : null;
    }

    public String getAccessToken() {
        return token != null ? token.access_token : null;
    }

    public String getScope() {
        return token != null ? token.scope : null;
    }

    public String getBody() {
        return responseBody;
    }

    public OAuthError getOAuthError() {
        return error;
    }

    public Response getHttpResponse() {
        return response;
    }

}
