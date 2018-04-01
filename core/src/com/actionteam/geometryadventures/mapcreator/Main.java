package com.actionteam.geometryadventures.mapcreator;

import com.actionteam.geometryadventures.mapcreator.view.World;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class Main extends ApplicationAdapter {

    private World grid;

    @Override
    public void create() {
        grid = new World(new TextureAtlas(Gdx.files.internal("textureatlas/textures.atlas")));
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        grid.render(0);
    }

    @Override
    public void dispose() {
    }

    @Override
    public void resize(int width, int height) {
        grid.setSize(width, height);
    }
}
