package de.wroracer.unobackend;

import com.google.gson.Gson;
import de.wroracer.unobackend.data.UNOGame;
import de.wroracer.unobackend.retrofit.RestEndpoint;
import de.wroracer.unobackend.websocket.data.GameSocketResponse;
import de.wroracer.unobackend.websocket.data.PlayerJoinMessage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.messaging.converter.GsonMessageConverter;
import org.springframework.messaging.converter.StringMessageConverter;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.*;

import static java.util.concurrent.TimeUnit.SECONDS;

class DemoApplicationTest {

    private RestEndpoint restEndpoint;
    private WebSocketStompClient webSocketStompClient;
    private StompSession session;


    @BeforeEach
    public void initTests() throws ExecutionException, InterruptedException, TimeoutException {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        restEndpoint = retrofit.create(RestEndpoint.class);

        this.webSocketStompClient = new WebSocketStompClient(new SockJsClient(
                Arrays.asList(new WebSocketTransport(new StandardWebSocketClient()))));

        webSocketStompClient.setMessageConverter(new GsonMessageConverter());

        session = webSocketStompClient
                .connect(String.format("ws://localhost:%d/ws-endpoint", 8080), new StompSessionHandlerAdapter() {
                })
                .get(1, SECONDS);
    }

    @Test
    public void testGameCreation() throws IOException, InterruptedException {

        /*Call<Collection<UNOGame>>  gameCall = restEndpoint.getAllGames();
        assertNotNull(gameCall);
        Response<Collection<UNOGame>> gameRes = gameCall.execute();
        assertEquals(gameRes.code(),200);
        Collection<UNOGame> games = gameRes.body();
        assertNotNull(games);
        assertEquals(games.size(),0);*/

        Call<UNOGame> gameCall = restEndpoint.createNewGame(2);
        Response<UNOGame> gameResponse = gameCall.execute();
        assertEquals(gameResponse.code(),200);
        UNOGame game = gameResponse.body();
        assertNotNull(game);
        long gameid = game.getId();

        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue(1);
        session.subscribe("/topic/game/"+gameid, new StompFrameHandler() {
            @Override
            public Type getPayloadType(StompHeaders headers) {
                return String.class;
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                System.out.println("Received message: " + payload);
                blockingQueue.add((String) payload);
            }
        });

        session.send("/app/game/"+gameid+"/5","null");

        PlayerJoinMessage joinMessage = new PlayerJoinMessage("P1");
        session.send("/app/game/"+gameid+"/join",joinMessage);

       // assertEquals("1235", blockingQueue.poll(1, SECONDS));

        LocalTime time = LocalTime.now();
        while (time.plusMinutes(5).isAfter(LocalTime.now())){

        }

        session.disconnect();

    }



}
