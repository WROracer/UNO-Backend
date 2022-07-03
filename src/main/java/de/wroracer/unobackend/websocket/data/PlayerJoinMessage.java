package de.wroracer.unobackend.websocket.data;

public class PlayerJoinMessage {
    private String name;

    public PlayerJoinMessage(){

    }

    public PlayerJoinMessage(String name){
        this.name = name;

    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
