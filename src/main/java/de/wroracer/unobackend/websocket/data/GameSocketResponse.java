package de.wroracer.unobackend.websocket.data;

import de.wroracer.unobackend.data.UNOGame;
import de.wroracer.unobackend.data.UNOPlayer;
import de.wroracer.unobackend.websocket.GameSocketController;

public class GameSocketResponse {
    private UNOGame game;
    private UNOPlayer player;
    private String message;
    public GameSocketResponse(UNOGame game, UNOPlayer player,String message){
        this.game = game;
        this.player = player;
        this.message = message;
    }
}
