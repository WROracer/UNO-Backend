package de.wroracer.uno.backend.util;

import de.wroracer.uno.backend.GameManager;
import de.wroracer.uno.backend.data.UnoGame;
import de.wroracer.uno.engine.Game;

public class GameConverter {

    public static UnoGame convert(Game game) {
        UnoGame g = new UnoGame();
        g.setGameId(game.getId());
        g.getPlayers().addAll(PlayerConverter.convertAll(game.getPlayers(), game, GameManager.getInstance().getPlayerNames(game)));
        g.setCurrentCard(CardConverter.convert(game.getCurrentCard()));
        return g;
    }
}
