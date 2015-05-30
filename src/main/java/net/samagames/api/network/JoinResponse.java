package net.samagames.api.network;

/**
 * Created by Someone on 09/03/2015.
 */
public class JoinResponse {

    private String reason = null;
	private ResponseType responseType = ResponseType.ALLOW;

    public JoinResponse() {

    }

    public void disallow(String reason) {
        responseType = ResponseType.DENY_OTHER;
        this.reason = reason;
    }

	public void disallow(ResponseType responseType) {
		this.responseType = responseType;
		this.reason = responseType.getMessage();
	}

	public ResponseType getResponseType() {
		return responseType;
	}

    public boolean isAllowed() {
        return responseType == ResponseType.ALLOW;
    }

    public String getReason() {
        return reason;
    }

    public void allow() {
        this.responseType = ResponseType.ALLOW;
    }

}
