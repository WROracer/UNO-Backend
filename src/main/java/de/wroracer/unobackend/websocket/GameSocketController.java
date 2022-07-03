package de.wroracer.unobackend.websocket;

import de.wroracer.unobackend.services.GameService;
import de.wroracer.unobackend.websocket.data.GameSocketResponse;
import de.wroracer.unobackend.websocket.data.PlayerJoinMessage;
import de.wroracer.unoengine.Game;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

@Controller
public class GameSocketController {

    private GameService service;

    public GameSocketController(GameService service){
        this.service = service;
    }

    @MessageMapping("/game/{gameID}/join")
    @SendTo("/topic/game/{gameID}")
    public GameSocketResponse joinGame(@DestinationVariable String gameID, PlayerJoinMessage joinMessage){
        return service.joinGame(gameID,joinMessage);
    }

    @MessageMapping("/game/{gameId}/{userId}")
    @SendTo("/topic/game/{gameId}")
    public String simple(@DestinationVariable String gameId, @DestinationVariable String userId) {
        return gameId + userId;
    }
}
