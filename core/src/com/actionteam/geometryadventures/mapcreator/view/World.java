package com.actionteam.geometryadventures.mapcreator.view;

import com.actionteam.geometryadventures.mapcreator.model.Map;
import com.actionteam.geometryadventures.mapcreator.model.Tile;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

/**
 * Created by theartful on 4/1/18.
 */

public class World {

    private ScreenViewport viewport;
    private ShapeRenderer shapeRenderer;
    private TextureAtlas textureAtlas;
    private SpriteBatch batch;
    private int x;
    private int y;
    private int width;
    private int height;
    private Color gridColor;
    private boolean drawGrid;
    private Map map;

    public World(ShapeRenderer shapeRenderer, float unitsPerPixel, TextureAtlas textureAtlas, Map map) {
        this.shapeRenderer = shapeRenderer;
        this.textureAtlas = textureAtlas;
        this.map = map;

        viewport = new ScreenViewport();
        viewport.setUnitsPerPixel(unitsPerPixel);

        gridColor = Color.DARK_GRAY;
        drawGrid = true;
        batch = new SpriteBatch();
    }

    public World(TextureAtlas textureAtlas) {
        this(new ShapeRenderer(), 0.02f, textureAtlas, new Map());
    }

    public ScreenViewport getViewport() {
        return viewport;
    }

    public void translateCamera(float deltaX, float deltaY) {
        viewport.getCamera().translate(deltaX, deltaY, 0);
        viewport.update(width, height);
    }

    public void setViewport(ScreenViewport viewport) {
        this.viewport = viewport;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
        viewport.setScreenBounds(x, y, width, height);
    }

    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
        viewport.setScreenBounds(x, y, width, height);
        viewport.update(width, height);
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public Map getMap() {
        return map;
    }

    public void render(float dt) {
        viewport.apply();
        drawGrid();
        batch.begin();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        for (Tile tile : map.getTiles()) {
            batch.draw(textureAtlas.findRegion(tile.textureName, tile.textureIndex), tile.x,
                    tile.y, 1, 1);
        }
        batch.end();
    }

    private void drawGrid() {
        Vector2 topLeft = viewport.unproject(new Vector2(x, y));
        Vector2 bottomRight = viewport.unproject(new Vector2(x + width, y + height));
        int startX = (int) topLeft.x;
        int endX = (int) bottomRight.x;
        int startY = (int) bottomRight.y;
        int endY = (int) topLeft.y;

        shapeRenderer.setProjectionMatrix(viewport.getCamera().combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(gridColor);
        Gdx.gl20.glLineWidth(1);

        for (int x = startX; x <= endX; x++) {
            shapeRenderer.line(x, topLeft.y, x, bottomRight.y);
        }

        for (int y = startY; y <= endY; y++) {
            shapeRenderer.line(topLeft.x, y, bottomRight.x, y);
        }

        shapeRenderer.circle(0, 0, 0.25f, 10);
        shapeRenderer.end();
    }

    public void setUnitsPerPixel(float unitsPerPixel) {
        viewport.setUnitsPerPixel(unitsPerPixel);
        viewport.update(viewport.getScreenWidth(), viewport.getScreenHeight());
    }

    public float getUnitsPerPixel() {
        return viewport.getUnitsPerPixel();
    }
}
