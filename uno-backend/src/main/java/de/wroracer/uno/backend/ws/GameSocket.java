package de.wroracer.uno.backend.ws;

import de.wroracer.uno.backend.GameManager;
import de.wroracer.uno.backend.data.UnoGame;
import de.wroracer.uno.backend.data.UnoPlayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.Objects;
import java.util.UUID;

@Controller
public class GameSocket {
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    public GameSocket() {
        GameManager.getInstance().setWebSocket(this);
    }

    @MessageMapping("/game/{gameId}/{playerId}")
    @SendTo("/topic/game/{gameId}/{playerId}")
    public String bsp(String msg, @DestinationVariable UUID gameId, @DestinationVariable UUID playerId) {
        return "";
    }

    public void broadcastToGame(UnoGame game, Objects msg) {
        for (UnoPlayer player : game.getPlayers()) {
            sendMessage(player, game, msg);
        }
    }

    //TODO Create Main message object
    public void sendMessage(UnoPlayer player, UnoGame game, Objects msg) {
        //simpMessagingTemplate.convertAndSend("/topic/game/" + game.getGameId() + "/" + player.getPlayerId(), msg);
    }
}
