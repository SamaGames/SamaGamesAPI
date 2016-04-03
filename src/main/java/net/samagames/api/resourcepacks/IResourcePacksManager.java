package net.samagames.api.resourcepacks;

/**
 * Resource packs manager
 *
 * Copyright (c) for SamaGames
 * All right reserved
 */
public interface IResourcePacksManager
{
	/**
	 * Forces the use of the pack given in argument
	 *
	 * @param name The name of the pack you want to force the users to use
	 */
	void forcePack(String name);

	/**
	 * Forces the use of the pack given in argument
	 *
	 * @param name The name of the pack you want to force the users to use
	 * @param callback a callback called when the download status changes
	 */
	void forcePack(String name, IResourceCallback callback);

	/**
	 * Forces the use of the pack given in argument
	 *
	 * @param url The url of the pack you want to force the users to use
	 * @param callback a callback called when the download status changes
	 */
	void forceUrlPack(String url, String hash, IResourceCallback callback);

	/**
	 * Kicks all the player who didn't totally downloaded the resource pack
	 */
	void kickAllUndownloaded();
}
