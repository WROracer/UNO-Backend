package de.wroracer.uno.backend;

import de.wroracer.uno.backend.data.UnoGame;
import de.wroracer.uno.backend.data.UnoPlayer;
import de.wroracer.uno.backend.util.GameConverter;
import de.wroracer.uno.backend.util.PlayerConverter;
import de.wroracer.uno.backend.ws.GameSocket;
import de.wroracer.uno.engine.Game;
import de.wroracer.uno.engine.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class GameManager {
    private static final GameManager INSTANCE = new GameManager();
    private final List<Game> games;

    private HashMap<UUID, HashMap<UUID, String>> playerNames = new HashMap<>();
    private GameSocket gameSocket;

    /*
    Init of GameManager
     */
    public GameManager() {
        games = new ArrayList<>();
        //TODO create Persistent games after restart
    }

    public static GameManager getInstance() {
        return INSTANCE;
    }

    public HashMap<UUID, String> getPlayerNames(Game game) {
        return playerNames.get(game.getId());
    }

    public UnoGame addGame() {
        Game game = new Game();
        games.add(game);

        return GameConverter.convert(game);
    }

    public UnoPlayer addPlayer(UUID gameId, String playerName) {
        Game game = getGame(gameId);
        Player player = game.addPlayer();
        if (playerNames == null) {
            playerNames = new HashMap<>();
        }
        if (!playerNames.containsKey(gameId)) {
            playerNames.put(gameId, new HashMap<>());
        }
        playerNames.get(gameId).put(player.getId(), playerName);
        UnoPlayer up = PlayerConverter.convert(player, game, playerName);
        UnoGame ug = GameConverter.convert(game);

        if (gameSocket != null) {
            //TODO
            gameSocket.broadcastToGame(ug, null);
        }
        return up;
    }

    private Game getGame(UUID gameId) {
        return games.stream().filter(g -> g.getId().equals(gameId)).findFirst().orElseThrow();
    }

    public UnoGame getUnoGame(UUID gameId) {
        return GameConverter.convert(getGame(gameId));
    }

    public List<UnoGame> getGames() {
        return games.stream().map(GameConverter::convert).toList();
    }

    private void gameFinished(Game game) {
        //TODO
    }

    public void setWebSocket(GameSocket gameSocket) {
        this.gameSocket = gameSocket;
    }
}
