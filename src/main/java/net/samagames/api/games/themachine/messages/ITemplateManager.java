package net.samagames.api.games.themachine.messages;

import net.samagames.api.games.themachine.messages.templates.*;

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
public interface ITemplateManager
{
    /**
     * Get a new instance of a basic message template
     * {@link BasicMessageTemplate}
     *
     * Basically, you don't have to use this template, it's
     * empty and isn't doing anything useful for you.
     *
     * @return Instance
     */
    BasicMessageTemplate getBasicMessageTemplate();

    /**
     * Get a new instance of a win message template
     * {@link WinMessageTemplate}
     *
     * Basically, you don't have to use this template, it's
     * empty and isn't doing anything useful for you.
     *
     * @return Instance
     */
    WinMessageTemplate getWinMessageTemplate();

    /**
     * Get a new instance of a player win template
     * {@link PlayerWinTemplate}
     *
     * With this template, you'll be able to show a win message
     * for one player only.
     *
     * Optionally, you can give a score and a commentary.
     *
     * @return Instance
     */
    PlayerWinTemplate getPlayerWinTemplate();

    /**
     * Get a new instance of a player leaderboard win template
     * {@link PlayerLeaderboardWinTemplate}
     *
     * With this template, you'll be able to show a win message
     * for three players only.
     *
     * Optionally, you can give a score and a commentary for
     * each players.
     *
     * @return Instance
     */
    PlayerLeaderboardWinTemplate getPlayerLeaderboardWinTemplate();

    /**
     * Get a new instance of a earning message template
     * {@link EarningMessageTemplate}
     *
     * Basically, you don't have to use this template, it's
     * empty and isn't doing anything useful for you.
     *
     * @return Instance
     */
    EarningMessageTemplate getEarningMessageTemplate();
}
