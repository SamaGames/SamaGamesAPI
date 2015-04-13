package net.samagames.api.protocol;

import java.util.HashMap;

/**
 * This file is a part of the SamaGames project
 * This code is absolutely confidential.
 * Created by zyuiop
 * (C) Copyright Elydra Network 2015
 * All rights reserved.
 */
public class ProtocolManager {

	private HashMap<String, TinyProtocol> handlers = new HashMap<>();

	/**
	 * Register a protocol handler. The protocol handler *MUST* have been already created.
	 * @param protocol Your protocol handler, extends TinyProtocol
	 */
	public void registerHandler(TinyProtocol protocol) {
		this.handlers.put(protocol.getClass().getName(), protocol);
	}

	/**
	 * Get a protocol handler
	 * @param clazz the protocol handler class
	 * @return the protocol handler instance
	 */
	public TinyProtocol getHandler(Class<? extends TinyProtocol> clazz) {
		return getHandler(clazz.getName());
	}

	/**
	 * Get a protocol handler
	 * @param handlerClassName the protocol handler class path
	 * @return the protocol handler instance
	 */
	public TinyProtocol getHandler(String handlerClassName) {
		return handlers.get(handlerClassName);
	}

}
