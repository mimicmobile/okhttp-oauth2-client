package ca.mimic.oauth2library;

public class OAuthError {
    protected String error;
    protected String error_description;
    protected String error_uri;

    protected transient Exception exception;

    public OAuthError(Exception e) {
        exception = e;
    }

    public String getError() {
        return error;
    }

    public String getErrorDescription() {
        return error_description;
    }

    public String getErrorUri() {
        return error_uri;
    }

    public Exception getErrorException() {
        return exception;
    }
}
