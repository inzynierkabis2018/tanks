package pl.edu.ukw.networkingentities;

public class PlayerInfo {

    private float x;
    private float y;
    private PlayerNumber playerNumber;
    private TankDirection tankDirection;

    private PlayerInfo() {

    }

    public PlayerInfo(float x, float y, PlayerNumber playerNumber, TankDirection tankDirection) {
        this.x = x;
        this.y = y;
        this.playerNumber = playerNumber;
        this.tankDirection = tankDirection;
    }

    public static PlayerInfo createPlayerOne() {
        return new PlayerInfo(0, 0, PlayerNumber.ONE, TankDirection.UP);
    }

    public static PlayerInfo createPlayerTwo() {
        return new PlayerInfo(1, 1, PlayerNumber.TWO, TankDirection.UP);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public PlayerNumber getPlayerNumber() {
        return playerNumber;
    }

    public TankDirection getTankDirection() {
        return tankDirection;
    }

    public enum PlayerNumber {
        ONE, TWO;

        public static PlayerNumber reflect(PlayerNumber playerNumber) {
            return playerNumber.equals(ONE) ? TWO : ONE;
        }
    }

    public enum TankDirection {
        UP, DOWN, LEFT, RIGHT
    }
}
