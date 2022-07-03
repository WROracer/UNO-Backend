package de.wroracer.unobackend.retrofit;

import de.wroracer.unobackend.data.UNOGame;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import java.util.Collection;

public interface RestEndpoint {
    @GET("game/all")
    public Call<Collection<UNOGame>> getAllGames();

    @GET("game/new")
    public Call<UNOGame> createNewGame(@Query("players")int players);

}
