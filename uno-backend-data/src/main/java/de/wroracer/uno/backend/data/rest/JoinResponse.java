package de.wroracer.uno.backend.data.rest;

import de.wroracer.uno.backend.data.UnoGame;
import de.wroracer.uno.backend.data.UnoPlayer;

public class JoinResponse {
    private final UnoGame game;
    private final UnoPlayer player;

    public JoinResponse(UnoGame game, UnoPlayer player) {
        this.game = game;
        this.player = player;
    }

    public UnoGame getGame() {
        return game;
    }

    public UnoPlayer getPlayer() {
        return player;
    }
}
