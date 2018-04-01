package com.actionteam.geometryadventures.mapcreator.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

/**
 * Created by theartful on 4/1/18.
 */

public class Grid {

    private ScreenViewport viewport;
    private ShapeRenderer shapeRenderer;
    private int x;
    private int y;
    private int width;
    private int height;
    private Color gridColor;

    public Grid(ShapeRenderer shapeRenderer, float unitsPerPixel) {
        viewport = new ScreenViewport();
        this.shapeRenderer = shapeRenderer;
        viewport.setUnitsPerPixel(0.1f);
        gridColor = Color.DARK_GRAY;
        x = 0;
        y = 0;
    }

    public Grid() {
        this(new ShapeRenderer(), 0.1f);
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
        viewport.update(width, height);
    }

    public void render(float dt) {
        viewport.apply();
        Vector2 topLeft = viewport.unproject(new Vector2(x,y));
        Vector2 bottomRight = viewport.unproject(new Vector2(x + width, y + height));
        int startX = (int)topLeft.x;
        int endX = (int)bottomRight.x;
        int startY = (int)bottomRight.y;
        int endY = (int)topLeft.y;

        // draw grid
        shapeRenderer.setProjectionMatrix(viewport.getCamera().combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(gridColor);
        Gdx.gl20.glLineWidth(1);

        for(int x = startX; x <= endX; x++) {
            shapeRenderer.line(x, topLeft.y, x, bottomRight.y);
        }

        for(int y = startY; y <= endY; y++) {
            shapeRenderer.line(topLeft.x, y, bottomRight.x, y);
        }

        shapeRenderer.circle(0,0,0.25f, 10);
        shapeRenderer.end();
    }
}
