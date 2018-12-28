package pl.edu.ukw.networkingentities;

public class Request {

    public static class Login {

    }

    public static class CreateGameRoom {

    }

    public static class JoinToGameRoom {
        private Long gameRoomID;


        public JoinToGameRoom() {

        }

        public JoinToGameRoom(Long gameRoomID) {
           this.gameRoomID = gameRoomID;
        }

        public Long getGameRoomID() {
            return gameRoomID;
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
}
