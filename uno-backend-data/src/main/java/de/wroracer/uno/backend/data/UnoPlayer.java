package de.wroracer.uno.backend.data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UnoPlayer {

    private final List<UnoCard> cards = new ArrayList<>();
    private UUID playerId;
    private String name;
    private UUID gameId;

    public UUID getPlayerId() {
        return playerId;
    }

    public void setPlayerId(UUID playerId) {
        this.playerId = playerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getGameId() {
        return gameId;
    }

    public void setGameId(UUID gameId) {
        this.gameId = gameId;
    }

    public List<UnoCard> getCards() {
        return cards;
    }
}
