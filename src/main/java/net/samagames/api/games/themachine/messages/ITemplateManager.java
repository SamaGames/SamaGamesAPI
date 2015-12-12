package net.samagames.api.games.themachine.messages;

import net.samagames.api.games.themachine.messages.templates.*;

/**
 * Template manager class
 *
 * Copyright (c) for SamaGames
 * All right reserved
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
