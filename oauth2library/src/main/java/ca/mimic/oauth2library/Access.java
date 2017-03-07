package ca.mimic.oauth2library;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Access {
    static Token refreshAccessToken(String token, OAuth2Client oAuth2Client) throws IOException, OAuthException {
        FormBody.Builder formBodyBuilder = new FormBody.Builder()
                .add(Constants.POST_REFRESH_TOKEN, token);

        Utils.postAddIfValid(formBodyBuilder, oAuth2Client.getFieldsAsMap());

        final Request request = new Request.Builder()
                .url(oAuth2Client.getSite())
                .post(formBodyBuilder.build())
                .build();

        return refreshTokenFromResponse(oAuth2Client, request);
    }

    private static Token refreshTokenFromResponse(final OAuth2Client oAuth2Client,
                                                  final Request request) throws IOException, OAuthException {
        return getTokenFromResponse(oAuth2Client, oAuth2Client.getOkHttpClient(),
                request, new AuthState(AuthState.REFRESH_TOKEN));

    }

    static Token getToken(OAuth2Client oAuth2Client) throws IOException, OAuthException {
        FormBody.Builder formBodyBuilder = new FormBody.Builder();
        Utils.postAddIfValid(formBodyBuilder, oAuth2Client.getFieldsAsMap());
        Utils.postAddIfValid(formBodyBuilder, oAuth2Client.getParameters());

        return getAccessToken(oAuth2Client, formBodyBuilder);
    }

    private static Token getAccessToken(OAuth2Client oAuth2Client, FormBody.Builder formBodyBuilder)
            throws IOException, OAuthException {
        final Request request = new Request.Builder()
                .url(oAuth2Client.getSite())
                .post(formBodyBuilder.build())
                .build();

        return getTokenFromResponse(oAuth2Client, request);
    }

    private static Token getTokenFromResponse(final OAuth2Client oAuth2Client,
                                              final Request request) throws IOException, OAuthException {
        return getTokenFromResponse(oAuth2Client, oAuth2Client.getOkHttpClient(),
                request, new AuthState(AuthState.ACCESS_TOKEN));

    }

    private static Token getTokenFromResponse(final OAuth2Client oAuth2Client,
                                              final OkHttpClient okHttpClient,
                                              final Request request,
                                              final AuthState authState) throws IOException, OAuthException {
        Response response;
        response = okHttpClient.newBuilder().authenticator(Utils.getAuthenticator(oAuth2Client, authState))
                .build().newCall(request).execute();

        if (response.code() < 400 && Utils.isJsonResponse(response)) {
            return Utils.getSuccessTokenFromJsonResponse(response);
        }

        throw new OAuthException(response);
    }

}
