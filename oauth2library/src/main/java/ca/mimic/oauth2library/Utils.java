package ca.mimic.oauth2library;

import java.io.IOException;
import java.util.Map;

import okhttp3.Authenticator;
import okhttp3.Credentials;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;

import okhttp3.Route;

class Utils {
    private final static MediaType JSON_MEDIA_TYPE = MediaType.parse("application/json");

    static boolean isJsonResponse(Response response) {
        return response.body() != null && response.body().contentType().equals(JSON_MEDIA_TYPE);
    }

    static Authenticator getAuthenticator(final OAuth2Client oAuth2Client,
                                          final AuthState authState) {
        return new Authenticator() {
            @Override
            public Request authenticate(Route route, Response response) throws IOException {
                String credential = "";

                authState.nextState();

                if (authState.isBasicAuth()) {
                    credential = Credentials.basic(oAuth2Client.getUsername(),
                            oAuth2Client.getPassword());
                } else if (authState.isAuthorizationAuth()) {
                    credential = Credentials.basic(oAuth2Client.getClientId(),
                            oAuth2Client.getClientSecret());
                } else if (authState.isFinalAuth()) {
                    return null;
                }


                System.out.println("Authenticating for response: " + response);
                System.out.println("Challenges: " + response.challenges());
                return response.request().newBuilder()
                        .addHeader(Constants.HEADER_AUTHORIZATION, credential)
                        .build();
            }
        };
    }


    static void postAddIfValid(FormBody.Builder formBodyBuilder, Map<String, String> params) {
        if (params == null) return;

        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (isValid(entry.getValue())) {
                formBodyBuilder.add(entry.getKey(), entry.getValue());
            }
        }
    }

    private static boolean isValid(String s) {
        return (s != null && s.trim().length() > 0);
    }

}
