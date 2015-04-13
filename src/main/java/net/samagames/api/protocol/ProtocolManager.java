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

	public void registerProtocol(TinyProtocol protocol) {
		this.handlers.put(protocol.getClass().getName(), protocol);
	}

	public TinyProtocol getProtocol(Class<? extends TinyProtocol> clazz) {
		return getProtocol(clazz.getName());
	}

	public TinyProtocol getProtocol(String handlerClassName) {
		return handlers.get(handlerClassName);
	}

}
