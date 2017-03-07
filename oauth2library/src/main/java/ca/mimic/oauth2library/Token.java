package ca.mimic.oauth2library;

public class Token {
    private final Long expiresIn;
    private final Long expiresAt;
    private final String tokenType;
    private final String refreshToken;
    private final String accessToken;

    private final String scope;

    public Token(Long expiresIn, String tokenType, String refreshToken, String accessToken,
                 String scope) {
        this.expiresIn = expiresIn;
        this.tokenType = tokenType;
        this.refreshToken = refreshToken;
        this.accessToken = accessToken;
        this.expiresAt = (expiresIn * 1000) + System.currentTimeMillis();
        this.scope = scope;
    }

    public Long getExpiresIn() {
        return expiresIn;
    }

    public Long getExpiresAt() {
        return expiresAt;
    }

    public String getTokenType() {
        return tokenType;
    }


    public String getRefreshToken() {
        return refreshToken;
    }


    public String getAccessToken() {
        return accessToken;
    }

    public boolean isExpired() {
        return (System.currentTimeMillis() >= this.getExpiresAt());
    }

    public String getScope() {
        return scope;
    }

    public String toString() {
        return "expiresIn: " + expiresIn + " | expiresAt: " + expiresAt + " | tokenType: " + tokenType + " | refreshToken: " + refreshToken + " | accessToken: " + accessToken + " | scope: " + scope;
    }
}
