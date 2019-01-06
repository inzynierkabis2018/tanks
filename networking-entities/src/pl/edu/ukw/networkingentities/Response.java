package pl.edu.ukw.networkingentities;

import java.util.Collections;
import java.util.List;

public class Response {

    public static class JoinToGameRoom {
        private PlayerInfo playerInfo;

        public JoinToGameRoom() {

        }

        public JoinToGameRoom(PlayerInfo playerInfo) {
            this.playerInfo = playerInfo;
        }

        public PlayerInfo getPlayerInfo() {
            return playerInfo;
        }
    }

    public static class AllyJoinedToRoom {
        private PlayerInfo playerInfo;

        public AllyJoinedToRoom() {

        }

        public AllyJoinedToRoom(PlayerInfo playerInfo) {
            this.playerInfo = playerInfo;
        }

        public PlayerInfo getPlayerInfo() {
            return playerInfo;
        }
    }

    public static class AllyLeftRoom {

    }

    public static  class GameRoomsList {
        private List<Long> gameRooms;

        public GameRoomsList() {
        }

        public GameRoomsList(List<Long> gameRooms) {
            this.gameRooms = gameRooms;
        }

        public List<Long> getGameRooms() {
            return Collections.unmodifiableList(gameRooms);
        }

        @Override
        public String toString() {
            return "GameRoomsListResponse{" +
                    "gameRooms=" + gameRooms +
                    '}';
        }
    }
}
