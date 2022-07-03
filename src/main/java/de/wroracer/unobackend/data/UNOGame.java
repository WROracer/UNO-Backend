package de.wroracer.unobackend.data;

import de.wroracer.unoengine.Game;

import java.util.List;
import java.util.Random;
import java.util.UUID;

public class UNOGame {
    private long id;
    private transient List<UNOPlayer> players;
    private int amountPlayers = 0;
    private transient Game game;
    private boolean isFull = false;
    public UNOGame(){
        id = new Random().nextLong();
    }

    public void initGame(int players){
        game = new Game(players);
    }

    public long getId() {
        return id;
    }

    public void addPlayer(UNOPlayer player){
        int id = players.size();
        player.setPlayer(game.getPlayers()[id]);
        players.add(player);
        if (players.size() == game.getPlayers().length){
            isFull = true;
        }
        amountPlayers++;
    }

    public List<UNOPlayer> getPlayers() {
        return players;
    }
}
