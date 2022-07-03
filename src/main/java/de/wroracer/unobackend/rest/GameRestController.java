package de.wroracer.unobackend.rest;

import de.wroracer.unobackend.data.UNOGame;
import de.wroracer.unobackend.services.GameService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/game")
public class GameRestController {
    private GameService gameService;

    public GameRestController(GameService service){
        this.gameService = service;
    }

    @GetMapping("/new")
    public UNOGame createNewGame(int players){
        return gameService.createGame(players);
    }

    @GetMapping("/all")
    private Collection<UNOGame> getGames(){
        return gameService.getActiveGames();
    }
}
