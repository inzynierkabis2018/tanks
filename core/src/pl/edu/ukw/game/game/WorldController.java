package pl.edu.ukw.game.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import java.util.ArrayList;
import java.util.List;

import pl.edu.ukw.game.TanksClient;
import pl.edu.ukw.game.game.objects.PlayerTank;
import pl.edu.ukw.networkingentities.PlayerInfo;
import pl.edu.ukw.networkingentities.Response;

public class WorldController {

    private static final String TAG = WorldController.class.getName();

    public PlayerTank player = new PlayerTank();
    public PlayerTank ally = new PlayerTank();
    private List<Long> gameRooms = new ArrayList<Long>();

    public WorldController() {
        init();
    }

    public void update(float deltaTime) {
        handleInputGame(deltaTime);
    }

    private void init() {

        TanksClient.instance.addListener(new Listener() {

            @Override
            public void received(Connection connection, Object object) {
                if (object instanceof Response.GameRoomsList) {
                    Response.GameRoomsList gameRoomsList = (Response.GameRoomsList) object;
                    gameRooms = gameRoomsList.getGameRooms();
                }

                if (object instanceof Response.JoinToGameRoom) {
                    Response.JoinToGameRoom response = (Response.JoinToGameRoom) object;
                    PlayerInfo playerInfo = response.getPlayerInfo();
                    player.setPlayerInfo(playerInfo);
                    player.connect();
                    TanksClient.instance.allyJoined(playerInfo);
                }

                if (object instanceof Response.AllyJoinedToRoom) {
                    Response.AllyJoinedToRoom response = (Response.AllyJoinedToRoom) object;
                    PlayerInfo allyInfo = response.getPlayerInfo();
                    ally.setPlayerInfo(allyInfo);
                    ally.connect();
                    TanksClient.instance.sendPosition(player);
                }

                if (object instanceof PlayerInfo) {
                    PlayerInfo playerInfo = (PlayerInfo) object;
                    ally.setPlayerInfo(playerInfo);
                    ally.connect();
                }

                if (object instanceof Response.AllyLeftRoom) {
                    ally.disconnect();
                }
            }
        });

        TanksClient.instance.login();
        TanksClient.instance.createGameRoom();
        TanksClient.instance.joinToGameRoom(1L);
    }

    private void handleInputGame(float deltaTime) {
        handlePlayerInput(deltaTime);
    }

    private void handlePlayerInput(float deltaTime) {
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            player.setDirection(PlayerInfo.TankDirection.LEFT);
            player.position.x -= 1 * deltaTime;
            TanksClient.instance.sendPosition(player);
        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            player.setDirection(PlayerInfo.TankDirection.RIGHT);
            player.position.x += 1 * deltaTime;
            TanksClient.instance.sendPosition(player);
        } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            player.setDirection(PlayerInfo.TankDirection.DOWN);
            player.position.y -= 1 * deltaTime;
            TanksClient.instance.sendPosition(player);
        } else if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            player.setDirection(PlayerInfo.TankDirection.UP);
            player.position.y += 1 * deltaTime;
            TanksClient.instance.sendPosition(player);
        }
    }
}
