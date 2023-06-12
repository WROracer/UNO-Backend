package de.wroracer.uno.backend.util;

import de.wroracer.uno.backend.data.UnoPlayer;
import de.wroracer.uno.engine.Game;
import de.wroracer.uno.engine.Player;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class PlayerConverter {

    public static List<UnoPlayer> convertAll(List<Player> players, Game game, HashMap<UUID, String> playerNames) {
        return players.stream().map(p -> convert(p, game, playerNames.get(p.getId()))).toList();
    }

    public static final UnoPlayer convert(Player player, Game game, String playerName) {
        UnoPlayer p = new UnoPlayer();
        p.setPlayerId(player.getId());
        p.setName(playerName);
        p.setGameId(game.getId());
        p.getCards().addAll(CardConverter.convertAll(player.getHand()));
        return p;
    }

}
