package pl.edu.ukw.game.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Disposable;

import pl.edu.ukw.game.game.util.Constants;

public class Assets implements Disposable, AssetErrorListener {

    public static final String TAG = Assets.class.getName();
    public static final Assets instance = new Assets();

    public AssetPlayerOneTank playerOneTank;
    public AssetPlayerTwoTank playerTwoTank;

    private AssetManager assetManager;

    private Assets() {
    }

    public void init(AssetManager assetManager) {
        this.assetManager = assetManager;
        assetManager.setErrorListener(this);
        assetManager.load(Constants.TEXTURE_ATLAS_OBJECTS, TextureAtlas.class);
        assetManager.finishLoading();

        Gdx.app.debug(TAG, "# of assets loaded: " + assetManager.getAssetNames().size);
        for (String asset : assetManager.getAssetNames()) {
            Gdx.app.debug(TAG, "Asset: " + asset);
        }

        TextureAtlas atlas = assetManager.get(Constants.TEXTURE_ATLAS_OBJECTS);

        for (Texture texture : atlas.getTextures()) {
            texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        }

        playerOneTank = new AssetPlayerOneTank(atlas);
        playerTwoTank = new AssetPlayerTwoTank(atlas);
    }

    @Override
    public void error(AssetDescriptor asset, Throwable throwable) {
        Gdx.app.error(TAG, "Couldn't load asset '" + asset.fileName + "'", throwable);
    }

    @Override
    public void dispose() {
        assetManager.dispose();
    }

    public class AssetPlayerOneTank {
        public final TextureAtlas.AtlasRegion tankX;
        public final TextureAtlas.AtlasRegion tankY;

        public AssetPlayerOneTank(TextureAtlas textureAtlas) {
            tankX = textureAtlas.findRegion("player_one_tank1_x");
            tankY = textureAtlas.findRegion("player_one_tank1_y");
        }
    }

    public class AssetPlayerTwoTank {
        public final TextureAtlas.AtlasRegion tankX;
        public final TextureAtlas.AtlasRegion tankY;

        public AssetPlayerTwoTank(TextureAtlas textureAtlas) {
            tankX = textureAtlas.findRegion("player_two_tank1_x");
            tankY = textureAtlas.findRegion("player_two_tank1_y");
        }
    }
}
