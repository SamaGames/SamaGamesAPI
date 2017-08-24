package net.samagames.api.resourcepacks;

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
