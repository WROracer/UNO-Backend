package de.wroracer.unobackend.services;

import de.wroracer.unobackend.data.UNOGame;
import de.wroracer.unobackend.websocket.GameSocketController;
import de.wroracer.unobackend.websocket.data.GameSocketResponse;
import de.wroracer.unobackend.websocket.data.PlayerJoinMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Service
public class GameService {

    private HashMap<Long, UNOGame> games = new HashMap<>();

    public GameService(){

    }

    public UNOGame createGame(int players){
        UNOGame game = new UNOGame();
        game.initGame(players);
        games.put(game.getId(),game);
        return game;
    }


    public Collection<UNOGame> getActiveGames() {
        return games.values();
    }

    public GameSocketResponse joinGame(String gameId, PlayerJoinMessage joinMessage) {
        return null;
    }
}
