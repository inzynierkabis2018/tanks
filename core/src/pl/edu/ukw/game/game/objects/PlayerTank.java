package pl.edu.ukw.game.game.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import pl.edu.ukw.game.game.Assets;
import pl.edu.ukw.networkingentities.PlayerInfo;

public class PlayerTank extends AbstractGameObject {

    private TextureRegion tankX;
    private TextureRegion tankY;
    private PlayerInfo.TankDirection tankDirection;
    private PlayerInfo.PlayerNumber playerNumber;
    private boolean connected;

    public PlayerTank() {

    }

    @Override
    public void render(SpriteBatch batch) {
        TextureRegion region;

        if (tankDirection.equals(PlayerInfo.TankDirection.RIGHT) || tankDirection.equals(PlayerInfo.TankDirection.LEFT)) {
            region = tankX;
        } else {
            region = tankY;
        }

        boolean flipY = tankDirection.equals(PlayerInfo.TankDirection.DOWN);
        boolean flipX = tankDirection.equals(PlayerInfo.TankDirection.RIGHT);
        batch.draw(
                region.getTexture(), position.x, position.y, origin.x, origin.y,
                dimension.x, dimension.y, scale.x, scale.y, rotation,
                region.getRegionX(), region.getRegionY(), region.getRegionWidth(), region.getRegionHeight(),
                flipX, flipY);
    }

    @Override
    public String toString() {
        return "PlayerTank{" +
                "playerNumber=" + playerNumber +
                ", position=" + position +
                '}';
    }

    public void setDirection(PlayerInfo.TankDirection direction) {
        this.tankDirection = direction;
    }

    public PlayerInfo.TankDirection getDirection() {
        return tankDirection;
    }

    public void setPlayerNumber(PlayerInfo.PlayerNumber playerNumber) {
        this.playerNumber = playerNumber;
    }

    public PlayerInfo.PlayerNumber getPlayerNumber() {
        return playerNumber;
    }

    public void connect() {
        if (!isConnected()) {
            connected = true;
        }
    }

    public void disconnect() {
        if (isConnected()) {
            connected = false;
        }
    }

    public boolean isConnected() {
        return connected;
    }

    public void setPlayerInfo(PlayerInfo playerInfo) {
        position.x = playerInfo.getX();
        position.y = playerInfo.getY();
        playerNumber = playerInfo.getPlayerNumber();
        tankDirection = playerInfo.getTankDirection();
        init();
    }

    private void init() {
        dimension.set(0.5f, 0.5f);

        if (playerNumber.equals(PlayerInfo.PlayerNumber.ONE)) {
            tankX = Assets.instance.playerOneTank.tankX;
            tankY = Assets.instance.playerOneTank.tankY;
        } else {
            tankX = Assets.instance.playerTwoTank.tankX;
            tankY = Assets.instance.playerTwoTank.tankY;
        }

        bounds.set(0, 0, dimension.x, dimension.y);
    }
}
