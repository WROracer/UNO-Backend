package de.wroracer.unobackend.websocket;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

@Controller
public class GameController {
    @MessageMapping("/welcome")
    @SendTo("/topic/greetings")
    public String greeting(String payload) {
        System.out.println("Generating new greeting message for " + payload);
        return "Hello, " + payload + "!";
    }

    @SubscribeMapping("/chat")
    public Message sendWelcomeMessageOnSubscription() {
        Message welcomeMessage = new Message();
        welcomeMessage.setMessage("Hello World!");
        return welcomeMessage;
    }

    @MessageMapping("/game/{gameId}/{userId}")
    @SendTo("/topic/game/{gameId}")
    public String simple(@DestinationVariable String gameId, @DestinationVariable String userId) {
        return gameId + userId;
    }
}
