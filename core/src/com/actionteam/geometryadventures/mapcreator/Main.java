package com.actionteam.geometryadventures.mapcreator;

import com.actionteam.geometryadventures.mapcreator.controller.WorldGestureListener;
import com.actionteam.geometryadventures.mapcreator.view.World;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.input.GestureDetector;

public class Main extends ApplicationAdapter {

    private World world;

    @Override
    public void create() {
        world = new World(new TextureAtlas(Gdx.files.internal("textureatlas/textures.atlas")));
        Gdx.input.setInputProcessor(new GestureDetector(new WorldGestureListener(world)));
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        world.render(0);
    }

    @Override
    public void dispose() {
    }

    @Override
    public void resize(int width, int height) {
        world.setSize(width, height);
    }
}
