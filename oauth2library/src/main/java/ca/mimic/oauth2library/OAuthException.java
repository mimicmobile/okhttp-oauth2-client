package ca.mimic.oauth2library;

import java.io.IOException;
import org.json.JSONException;

import okhttp3.Response;

public class OAuthException extends RuntimeException {
    private Response response;

    private JSONException jsonException;
    private String responseBody;

    public OAuthException(JSONException e, Response response) throws IOException {
        this.jsonException = e;
        this.response = response;
        if (response != null) {
            responseBody = response.body().string();
        }

    }

    public OAuthException(Response response) throws IOException {
        this.response = response;
        if (response != null) {
            responseBody = response.body().string();
        }
    }

    public Response getResponse() {
        return response;
    }

    public JSONException getJsonException() {
        return jsonException;
    }

    public String getResponseBody() {
        return responseBody;
    }

    public String toString() {
        return "code: " + response.code() + " | " + response.toString();
    }
}

