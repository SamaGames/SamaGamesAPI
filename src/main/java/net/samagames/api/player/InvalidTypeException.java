package net.samagames.api.player;

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
public class InvalidTypeException extends IllegalArgumentException
{
	/**
	 * Constructor
	 */
	public InvalidTypeException() {}

	/**
	 * Constructor
	 *
	 * @param message Exception message
     */
	public InvalidTypeException(String message) {
		super(message);
	}

	/**
	 * Constructor
	 *
	 * @param message Exception message
	 * @param cause Error message
	 */
	public InvalidTypeException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructor
	 *
	 * @param cause Error message
	 */
	public InvalidTypeException(Throwable cause) {
		super(cause);
	}
}
