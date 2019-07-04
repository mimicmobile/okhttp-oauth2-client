package ca.mimic.oauth2library;

import java.io.IOException;

import okhttp3.Authenticator;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Access {
    protected static OAuthResponse refreshAccessToken(String token, OAuth2Client oAuth2Client) throws IOException {
        FormBody.Builder formBodyBuilder = new FormBody.Builder()
                .add(Constants.POST_REFRESH_TOKEN, token);

        Utils.postAddIfValid(formBodyBuilder, oAuth2Client.getFieldsAsMap());

        final Request request = new Request.Builder()
                .url(oAuth2Client.getSite())
                .post(formBodyBuilder.build())
                .build();

        return refreshTokenFromResponse(oAuth2Client, request);
    }

    private static OAuthResponse refreshTokenFromResponse(final OAuth2Client oAuth2Client,
                                                  final Request request) throws IOException {
        return getTokenFromResponse(oAuth2Client, oAuth2Client.getOkHttpClient(),
                request, new AuthState(AuthState.REFRESH_TOKEN));

    }

    protected static OAuthResponse getToken(OAuth2Client oAuth2Client) throws IOException {
        FormBody.Builder formBodyBuilder = new FormBody.Builder();
        Utils.postAddIfValid(formBodyBuilder, oAuth2Client.getFieldsAsMap());
        Utils.postAddIfValid(formBodyBuilder, oAuth2Client.getParameters());

        return getAccessToken(oAuth2Client, formBodyBuilder);
    }

    private static OAuthResponse getAccessToken(OAuth2Client oAuth2Client, FormBody.Builder formBodyBuilder)
            throws IOException {
        final Request request = new Request.Builder()
                .url(oAuth2Client.getSite())
                .post(formBodyBuilder.build())
                .build();

        return getTokenFromResponse(oAuth2Client, request);
    }

    private static OAuthResponse getTokenFromResponse(final OAuth2Client oAuth2Client,
                                              final Request request) throws IOException {
        return getTokenFromResponse(oAuth2Client, oAuth2Client.getOkHttpClient(),
                request, new AuthState(AuthState.ACCESS_TOKEN));

    }

    private static OAuthResponse getTokenFromResponse(final OAuth2Client oAuth2Client,
                                              final OkHttpClient okHttpClient,
                                              final Request request,
                                              final AuthState authState) throws IOException {

        Authenticator authenticator = oAuth2Client.getAuthenticator() == null ? Utils.getAuthenticator(oAuth2Client, authState) : oAuth2Client.getAuthenticator();
        Response response = okHttpClient.newBuilder().authenticator(authenticator)
                .build().newCall(request).execute();

        return new OAuthResponse(response);
    }

}
