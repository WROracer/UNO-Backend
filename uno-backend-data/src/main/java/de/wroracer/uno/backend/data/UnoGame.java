package de.wroracer.uno.backend.data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UnoGame {
    private final List<UnoPlayer> players = new ArrayList<>();
    private UUID gameId;
    private UnoCard currentCard;

    public UUID getGameId() {
        return gameId;
    }

    public void setGameId(UUID gameId) {
        this.gameId = gameId;
    }

    public List<UnoPlayer> getPlayers() {
        return players;
    }

    public UnoCard getCurrentCard() {
        return currentCard;
    }

    public void setCurrentCard(UnoCard currentCard) {
        this.currentCard = currentCard;
    }
}
