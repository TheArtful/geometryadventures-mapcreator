package com.actionteam.geometryadventures.mapcreator.view;

import com.actionteam.geometryadventures.mapcreator.Resources;
import com.actionteam.geometryadventures.mapcreator.model.TileType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Created by theartful on 4/1/18.
 */

public class TextureBox extends Table {

    public static final int NOTHING = -1;
    public static final int ALL = -2;

    private Resources resources;
    private Skin skin;
    private TextureAtlas textureAtlas;
    private final TileType tileType;
    private float width;
    private TextureBoxListener listener;
    private int selectedTile;
    private Image[] tiles;
    private ShapeRenderer shapeRenderer;

    public TextureBox(final TileType tileType, float width, Resources resources) {
        this.resources = resources;
        this.shapeRenderer = resources.shapeRenderer;
        this.tileType = tileType;
        this.skin = resources.skin;
        this.textureAtlas = resources.textureAtlas;
        this.width = width;
        this.selectedTile = -1;
        center();
        final float tileLength = (width / tileType.xTiles > 160)? 160 : width / tileType.xTiles;
        tiles = new Image[tileType.numberOfTiles];
        final TextureBox t = this;
        for (int i = 0; i < tileType.numberOfTiles; i++) {
            tiles[i] = new Image();
            TextureAtlas.AtlasRegion region = textureAtlas.findRegion(tileType.textureName, i);
            tiles[i].setDrawable(new TextureRegionDrawable(region));
            add(tiles[i]).width(tileLength).height(tileLength);
            if ((i + 1) % tileType.xTiles == 0) row();
            final int index = i;
            tiles[i].addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (listener != null) listener.textureChosen(t, tileType, index);
                }
            });
        }
    }

    public void setListener(TextureBoxListener listener) {
        this.listener = listener;
    }

    public void setSelectedTile(int selectedTile) {
        this.selectedTile = selectedTile;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        Vector2 pos = null;
        float width = 0;
        float height = 0;
        if (selectedTile >= 0) {
            Image selectedImage = tiles[selectedTile];
            pos = selectedImage.localToStageCoordinates(new Vector2(0, 0));
            width = selectedImage.getWidth();
            height = selectedImage.getHeight();
        } else if (selectedTile == -2) {
            pos = localToStageCoordinates(new Vector2(0, 0));
            width = getWidth();
            height = getHeight();
        }
        if (pos != null) {
            batch.end();
            Gdx.gl20.glLineWidth(3);
            shapeRenderer.setProjectionMatrix(getStage().getViewport().getCamera().combined);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            shapeRenderer.setColor(Color.LIGHT_GRAY);

            shapeRenderer.rect(pos.x,
                    pos.y, width, height);
            shapeRenderer.end();
            batch.begin();
        }
    }
}
