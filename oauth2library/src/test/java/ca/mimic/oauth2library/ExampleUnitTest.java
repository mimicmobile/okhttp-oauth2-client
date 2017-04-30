package ca.mimic.oauth2library;

import com.squareup.moshi.Moshi;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void additionIsCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    public Token createTokenFromJson() throws IOException {
        Moshi moshi = new Moshi.Builder().build();
        String s = "{\"expires_in\": 2000, \"token_type\": \"bearer\", \"access_token\": \"access_token\", \"refresh_token\": \"refresh_token\", \"scope\": \"scope\"}";

        return moshi.adapter(Token.class).fromJson(s);
    }

    public OAuthError createErrorFromJson() throws IOException {
        Moshi moshi = new Moshi.Builder().build();
        String s = "{\"error\":\"unauthorized_client\",\"error_description\":\"Invalid client or doesn't have the permission to make this request.\"}";

        return moshi.adapter(OAuthError.class).fromJson(s);
    }

    @Test
    public void tokenCheck() throws Exception {
        Token token = createTokenFromJson();
        assertEquals("access_token", token.access_token);
        assertEquals("refresh_token", token.refresh_token);
        assertEquals("bearer", token.token_type);
        assertEquals("scope", token.scope);
        assertEquals(2000, (long) token.expires_in);
    }

    @Test
    public void errorCheck() throws Exception {
        OAuthError error = createErrorFromJson();
        assertEquals("unauthorized_client", error.getError());
        assertEquals("Invalid client or doesn't have the permission to make this request.", error.getErrorDescription());
        assertEquals(null, error.getErrorUri());
        assertEquals(null, error.getErrorException());
    }
}