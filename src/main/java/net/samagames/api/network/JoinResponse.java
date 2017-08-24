package net.samagames.api.network;

/*
 * This file is part of SamaGamesAPI.
 *
 * SamaGamesAPI is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * SamaGamesAPI is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with SamaGamesAPI.  If not, see <http://www.gnu.org/licenses/>.
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
