package pl.edu.ukw.server;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import pl.edu.ukw.networkingentities.PlayerInfo;
import pl.edu.ukw.networkingentities.Response;
import pl.edu.ukw.networkingentities.Request.*;

public class GameRoom {

    private Connection playerOne;
    private Connection playerTwo;
    private static long gameRoomID;

    public GameRoom() {
        gameRoomID++;
    }

    public boolean isFull() {
        return getPlayers().size() == 2;
    }

    public void addPlayer(Connection player) {
        if (playerOne == null) {
            addPlayerOne(player);
        } else {
            addPlayerTwo(player);
        }
    }

    public void removePlayer(Connection player) {
        if (player.equals(playerOne)) {
            if (playerTwo != null) {
                playerTwo.sendTCP(new Response.AllyLeftRoom());
            }
            playerOne = null;
        } else {
            if (playerOne != null) {
                playerOne.sendTCP(new Response.AllyLeftRoom());
            }
            playerTwo = null;
        }
    }

    private void addPlayerOne(Connection playerOne) {
        this.playerOne = playerOne;

        playerOne.sendTCP(new Response.JoinToGameRoom(PlayerInfo.createPlayerOne()));

        playerOne.addListener(new Listener() {

            @Override
            public void received(Connection connection, Object object) {
                if (object instanceof AllyJoinedToRoom) {
                    AllyJoinedToRoom request = (AllyJoinedToRoom) object;
                    if (playerTwo != null) {
                        playerTwo.sendTCP(new Response.AllyJoinedToRoom(request.getPlayerInfo()));
                    }
                }

                if (object instanceof PlayerInfo) {
                    if (playerTwo != null) {
                        playerTwo.sendTCP(object);
                    }
                }
            }

            @Override
            public void disconnected(Connection connection) {
                removePlayer(connection);
            }
        });
    }

    private void addPlayerTwo(Connection playerTwo) {
        this.playerTwo = playerTwo;

        playerTwo.sendTCP(new Response.JoinToGameRoom(PlayerInfo.createPlayerTwo()));

        playerTwo.addListener(new Listener() {

            @Override
            public void received(Connection connection, Object object) {
                if (object instanceof AllyJoinedToRoom) {
                    AllyJoinedToRoom request = (AllyJoinedToRoom) object;
                    if (playerOne != null) {
                        playerOne.sendTCP(new Response.AllyJoinedToRoom(request.getPlayerInfo()));
                    }
                }

                if (object instanceof PlayerInfo) {
                    if (playerOne != null) {
                        playerOne.sendTCP(object);
                    }
                }
            }

            @Override
            public void disconnected(Connection connection) {
                removePlayer(connection);
            }
        });
    }

    public List<Connection> getPlayers() {
        return Lists.newArrayList(playerOne, playerTwo).stream().filter(Objects::nonNull).collect(Collectors.toList());
    }

    public static Long getNextGameRoomID() {
        return gameRoomID + 1;
    }

    @Override
    public String toString() {
        return "GameRoom{" +
                "playerOne=" + playerOne +
                ", playerTwo=" + playerTwo +
                '}';
    }
}
