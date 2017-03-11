package ca.mimic.oauth2library;

class OAuthError {
    protected String error;
    protected String error_description;
    protected String error_uri;

    public String getError() {
        return error;
    }

    public String getErrorDescription() {
        return error_description;
    }

    public String getErrorUri() {
        return error_uri;
    }
}
