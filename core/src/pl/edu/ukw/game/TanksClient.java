package pl.edu.ukw.game;

import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;

import java.io.IOException;
import java.util.ArrayList;

import pl.edu.ukw.game.game.objects.PlayerTank;
import pl.edu.ukw.game.game.util.Constants;
import pl.edu.ukw.networkingentities.PlayerInfo;
import pl.edu.ukw.networkingentities.Request;
import pl.edu.ukw.networkingentities.Response;

public class TanksClient extends Client{

    private static final String TAG = TanksClient.class.getName();

    public static TanksClient instance = new TanksClient();

    public void login() {
        if (isConnected()) {
            sendTCP(new Request.Login());
        }
    }

    public void createGameRoom() {
        if (isConnected()) {
            sendTCP(new Request.CreateGameRoom());
        }
    }

    public void joinToGameRoom(Long gameRoomID) {
        if (isConnected()) {
            sendTCP(new Request.JoinToGameRoom(gameRoomID));
        }
    }

    public void sendPosition(PlayerTank tank) {
        if (isConnected()) {
            sendTCP(new PlayerInfo(tank.position.x, tank.position.y, tank.getPlayerNumber(), tank.getDirection()));
        }
    }

    public void allyJoined(PlayerInfo playerInfo) {
        if (isConnected()) {
            sendTCP(new Request.AllyJoinedToRoom(playerInfo));
        }
    }

    private TanksClient() {
        start();

        try {
            connect(Constants.clientConnectionTimeout, Constants.serverAddress, Constants.serverTCPPort, Constants.serverUDPPort);
        } catch (IOException e) {
            Gdx.app.error(TAG, "Network error ", e);
        }
        registerObjects();
    }

    private void registerObjects() {
        Kryo kryo = getKryo();
        kryo.register(Request.CreateGameRoom.class);
        kryo.register(Request.JoinToGameRoom.class);
        kryo.register(Request.Login.class);
        kryo.register(Request.AllyJoinedToRoom.class);
        kryo.register(Response.GameRoomsList.class);
        kryo.register(Response.JoinToGameRoom.class);
        kryo.register(Response.AllyJoinedToRoom.class);
        kryo.register(Response.AllyLeftRoom.class);
        kryo.register(PlayerInfo.PlayerNumber.class);
        kryo.register(PlayerInfo.TankDirection.class);
        kryo.register(ArrayList.class);
        kryo.register(PlayerInfo.class);
    }
}
