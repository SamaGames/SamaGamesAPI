package net.samagames.api.network;

/**
 * Join response object
 *
 * Copyright (c) for SamaGames
 * All right reserved
 */
public class JoinResponse
{
    private String reason;
	private ResponseType responseType;

    /**
     * Constructor
     */
    public JoinResponse()
    {
        this.reason = null;
        this.responseType = ResponseType.ALLOW;
    }

    /**
     * Allow the join request
     */
    public void allow()
    {
        this.responseType = ResponseType.ALLOW;
    }

    /**
     * Disallow the join request with a given reason
     *
     * @param reason Reason
     */
    public void disallow(String reason)
    {
        this.responseType = ResponseType.DENY_OTHER;
        this.reason = reason;
    }

    /**
     * Disallow the join request with a given reponse {@link ResponseType}
     *
     * @param responseType Reason
     */
	public void disallow(ResponseType responseType)
    {
		this.responseType = responseType;
		this.reason = responseType.getMessage();
	}

    /**
     * Get the response type used
     *
     * @return Response type
     */
	public ResponseType getResponseType()
    {
		return this.responseType;
	}

    /**
     * Return if the join is allowed
     *
     * @return {@code true} if allowed
     */
    public boolean isAllowed()
    {
        return this.responseType == ResponseType.ALLOW;
    }

    /**
     * Get the disallow reason of the join request
     *
     * @return Reason
     */
    public String getReason()
    {
        return this.reason;
    }
}
