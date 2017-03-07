package ca.mimic.oauth2library;

class AuthState {
    static final int ACCESS_TOKEN = 0;
    static final int REFRESH_TOKEN = 1;

    private static final int NO_AUTH = 0;
    private static final int BASIC_AUTH = 1;
    private static final int AUTHORIZATION_AUTH = 2;
    private static final int FINAL_AUTH = 3;

    private static final int[] ACCESS_STATES = new int[]{
            NO_AUTH,
            BASIC_AUTH,
            AUTHORIZATION_AUTH,
            FINAL_AUTH
    };

    private static final int[] REFRESH_STATES = new int[]{
            NO_AUTH,
            AUTHORIZATION_AUTH,
            FINAL_AUTH
    };

    private int[] state;

    private int tokenType;
    private int position;
    private boolean initialAuth;

    AuthState(int tokenType) {
        this.tokenType = tokenType;

        switch (tokenType) {
            case ACCESS_TOKEN:
                state = ACCESS_STATES;
                break;
            case REFRESH_TOKEN:
                state = REFRESH_STATES;
                break;
        }
    }

    void nextState() {
        position++;
    }

    boolean isFinalAuth() {
        return state[position] == FINAL_AUTH;
    }

    boolean isBasicAuth() {
        return state[position] == BASIC_AUTH;
    }

    boolean isAuthorizationAuth() {
        return state[position] == AUTHORIZATION_AUTH;
    }

    public int getTokenType() {
        return tokenType;
    }
}
