package pl.edu.ukw.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;

import pl.edu.ukw.game.game.Assets;
import pl.edu.ukw.game.game.WorldController;
import pl.edu.ukw.game.game.WorldRenderer;

public class Tanks extends ApplicationAdapter {
    private static final String TAG = Tanks.class.getName();

    private WorldController worldController;
    private WorldRenderer worldRenderer;
    private boolean paused;

    @Override
    public void create() {
        Gdx.app.setLogLevel(Application.LOG_DEBUG);
        Assets.instance.init(new AssetManager());
        worldController = new WorldController();
        worldRenderer = new WorldRenderer(worldController);
        paused = false;
    }

    @Override
    public void resize(int width, int height) {
        worldRenderer.resize(width, height);
    }

    @Override
    public void render() {
        if (!paused) {
            worldController.update(Gdx.graphics.getDeltaTime());
        }
        Gdx.gl.glClearColor(0x64 / 255.0f, 0x95 / 255.0f, 0xed / 255.0f, 0xff / 255.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        worldRenderer.render();
    }

    @Override
    public void pause() {
        paused = true;
    }

    @Override
    public void resume() {
        Assets.instance.init(new AssetManager());
        paused = false;
    }

    @Override
    public void dispose() {
        TanksClient.instance.close();
        worldRenderer.dispose();
        Assets.instance.dispose();
    }
}
