package pl.edu.ukw.game.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;

import pl.edu.ukw.game.game.util.Constants;

public class WorldRenderer implements Disposable {

    private OrthographicCamera camera;
    private SpriteBatch batch;
    private WorldController worldController;

    public WorldRenderer(WorldController worldController) {
        this.worldController = worldController;
        init();
    }

    public void render() {
        renderObjects();
    }

    public void resize(int width, int height) {
        camera.viewportWidth = (Constants.VIEWPORT_WIDTH / height) * width;
        camera.update();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    private void init() {
        batch = new SpriteBatch();
        camera = new OrthographicCamera(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);
        camera.position.set(0, 0, 0);
        camera.update();
    }

    private void renderObjects() {
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        if (worldController.player.isConnected()) {
            worldController.player.render(batch);
        }
        if (worldController.ally.isConnected()) {
            worldController.ally.render(batch);
        }
        batch.end();
    }
}
