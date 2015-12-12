package net.samagames.api.player;

/**
 * Invalid type exception
 *
 * Copyright (c) for SamaGames
 * All right reserved
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
