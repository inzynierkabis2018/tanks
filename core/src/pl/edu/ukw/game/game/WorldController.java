package pl.edu.ukw.game.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import pl.edu.ukw.game.game.objects.PlayerTank;

public class WorldController {

    private static final String TAG = WorldController.class.getName();

    public PlayerTank playerOne;

    public WorldController() {
        init();
    }

    public void update(float deltaTime) {
        handleInputGame(deltaTime);
    }

    private void init() {
        playerOne = new PlayerTank(PlayerTank.PlayerNumber.ONE);
    }

    private void handleInputGame(float deltaTime) {
        handlePlayerInput(deltaTime);
    }

    private void handlePlayerInput(float deltaTime) {
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            playerOne.setDirection(PlayerTank.TankDirection.LEFT);
            playerOne.position.x -= 1 * deltaTime;
            showPlayerPosition(playerOne);
        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            playerOne.setDirection(PlayerTank.TankDirection.RIGHT);
            playerOne.position.x += 1 * deltaTime;
            showPlayerPosition(playerOne);
        } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            playerOne.setDirection(PlayerTank.TankDirection.DOWN);
            playerOne.position.y -= 1 * deltaTime;
            showPlayerPosition(playerOne);
        } else if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            playerOne.setDirection(PlayerTank.TankDirection.UP);
            playerOne.position.y += 1 * deltaTime;
            showPlayerPosition(playerOne);
        }
    }

    private void showPlayerPosition(PlayerTank player) {
        Gdx.app.debug(TAG, player.toString());
    }
}
