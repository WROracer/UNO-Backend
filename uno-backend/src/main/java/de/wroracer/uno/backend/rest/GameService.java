package de.wroracer.uno.backend.rest;

import de.wroracer.uno.backend.GameManager;
import de.wroracer.uno.backend.data.UnoGame;
import de.wroracer.uno.backend.data.UnoPlayer;
import de.wroracer.uno.backend.data.rest.GameList;
import de.wroracer.uno.backend.data.rest.JoinResponse;
import de.wroracer.uno.backend.data.rest.UserInformation;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class GameService {

    @GetMapping("/api/game/list")
    public GameList getGames() {
        return new GameList(GameManager.getInstance().getGames());
    }

    @PostMapping("/api/game/create")
    public JoinResponse createGame(@RequestBody UserInformation information) {
        UnoGame game = GameManager.getInstance().addGame();
        UnoPlayer player = GameManager.getInstance().addPlayer(game.getGameId(), information.getUsername());
        game = GameManager.getInstance().getUnoGame(game.getGameId());
        return new JoinResponse(game, player);
    }

    @PostMapping("/api/game/join/{gameId}")
    public JoinResponse joinGame(@RequestBody UserInformation information, @PathVariable(value = "gameId") UUID gameId) {
        UnoGame game = GameManager.getInstance().getUnoGame(gameId);
        if (game != null) {
            UnoPlayer player = GameManager.getInstance().addPlayer(gameId, information.getUsername());
            game = GameManager.getInstance().getUnoGame(game.getGameId());
            return new JoinResponse(game, player);
        }
        return null;
    }
}
