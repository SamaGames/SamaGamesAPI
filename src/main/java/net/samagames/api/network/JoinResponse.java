package net.samagames.api.network;

public class JoinResponse
{
    private String reason;
	private ResponseType responseType;

    public JoinResponse()
    {
        this.reason = null;
        this.responseType = ResponseType.ALLOW;
    }

    public void disallow(String reason)
    {
        this.responseType = ResponseType.DENY_OTHER;
        this.reason = reason;
    }

	public void disallow(ResponseType responseType)
    {
		this.responseType = responseType;
		this.reason = responseType.getMessage();
	}

	public ResponseType getResponseType()
    {
		return this.responseType;
	}

    public boolean isAllowed()
    {
        return this.responseType == ResponseType.ALLOW;
    }

    public String getReason()
    {
        return this.reason;
    }

    public void allow()
    {
        this.responseType = ResponseType.ALLOW;
    }
}
