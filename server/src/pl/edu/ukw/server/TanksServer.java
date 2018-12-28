package pl.edu.ukw.server;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pl.edu.ukw.networkingentities.PlayerInfo;
import pl.edu.ukw.networkingentities.Request;
import pl.edu.ukw.networkingentities.Response;

public class TanksServer {

    private Server server;
    private Map<Long, GameRoom> gameRooms = new HashMap<>();

    public TanksServer() throws IOException {
        server = new Server();
        server.start();
        server.bind(54555, 54777);
        registerObjects();
        registerListener();
    }

    private void registerListener() {
        server.addListener(new Listener() {

            @Override
            public void received(Connection connection, Object object) {
                if (object instanceof Request.Login) {
                    List<Long> gameRoomsIds = new ArrayList<>(gameRooms.keySet());
                    connection.sendTCP(new Response.GameRoomsList(gameRoomsIds));
                }

                if (object instanceof Request.CreateGameRoom) {
                    gameRooms.put(GameRoom.getNextGameRoomID(), new GameRoom());
                }

                if (object instanceof Request.JoinToGameRoom) {
                    Request.JoinToGameRoom request = (Request.JoinToGameRoom) object;
                    GameRoom gameRoom = gameRooms.get(request.getGameRoomID());
                    if (gameRoom != null && !gameRoom.isFull()) {
                        gameRoom.addPlayer(connection);
                    }
                }
            }
        });
    }


    private void registerObjects() {
        Kryo kryo = server.getKryo();
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

    public static void main(String... args) throws IOException {
        new TanksServer();
    }
}
