package com.actionteam.geometryadventures.mapcreator.controller;

import com.actionteam.geometryadventures.mapcreator.view.World;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by theartful on 4/1/18.
 */

public class WorldController implements GestureDetector.GestureListener {

    private World world;
    private float zoomTmp;

    public WorldController() {
        world = new World(new TextureAtlas(Gdx.files.internal("textureatlas/textures.atlas")));
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        if (zoomTmp == 0) zoomTmp = world.getUnitsPerPixel();
        world.setUnitsPerPixel((initialDistance / distance) * zoomTmp);
        return true;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        float dX = -deltaX * world.getUnitsPerPixel();
        float dY = deltaY * world.getUnitsPerPixel();
        world.translateCamera(dX, dY);
        return false;
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }

    @Override
    public void pinchStop() {
        zoomTmp = 0;
    }

    public void update() {
        world.render(0);
    }

    public void resize(int width, int height) {
        world.setSize(width, height);
    }
}
