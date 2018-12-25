package pl.edu.ukw.game.game.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import pl.edu.ukw.game.game.Assets;

public class PlayerTank extends AbstractGameObject {

    private TextureRegion tankX;
    private TextureRegion tankY;
    private TankDirection direction;
    private PlayerNumber playerNumber;

    public PlayerTank(PlayerNumber playerNumber) {
        this.playerNumber = playerNumber;
        init();
    }

    @Override
    public void render(SpriteBatch batch) {
        TextureRegion region;

        if (direction.equals(TankDirection.RIGHT) || direction.equals(TankDirection.LEFT)) {
            region = tankX;
        } else {
            region = tankY;
        }

        boolean flipY = direction.equals(TankDirection.DOWN);
        boolean flipX = direction.equals(TankDirection.RIGHT);
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

    public void setDirection(TankDirection direction) {
        this.direction = direction;
    }

    public void setPlayerNumber(PlayerNumber playerNumber) {
        this.playerNumber = playerNumber;
    }

    private void init() {
        dimension.set(0.5f, 0.5f);

        if (playerNumber.equals(PlayerNumber.ONE)) {
            tankX = Assets.instance.playerOneTank.tankX;
            tankY = Assets.instance.playerOneTank.tankY;
        } else {
            tankX = Assets.instance.playerTwoTank.tankX;
            tankY = Assets.instance.playerTwoTank.tankY;
        }

        direction = TankDirection.UP;
        bounds.set(0, 0, dimension.x, dimension.y);
    }

    public enum PlayerNumber {
        ONE, TWO
    }

    public enum TankDirection {
        UP, DOWN, LEFT, RIGHT
    }
}
