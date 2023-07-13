package de.wroracer.uno.backend.data.rest;

import de.wroracer.uno.backend.data.UnoGame;

import java.util.List;

public class GameList {
    private final List<UnoGame> games;

    public GameList(List<UnoGame> games) {
        this.games = games;
    }

    public List<UnoGame> getGames() {
        return games;
    }
}
