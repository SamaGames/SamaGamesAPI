package net.samagames.api.network;

/**
 * Created by vialarl on 09/03/2015.
 */
public class JoinResponse {

    private boolean allow = true;
    private String reason = null;

    public JoinResponse() {

    }

    public void disallow(String reason) {
        this.allow = false;
        this.reason = reason;
    }

    public boolean isAllowed() {
        return allow;
    }

    public String getReason() {
        return reason;
    }

    public void allow() {
        this.allow = true;
    }

}
