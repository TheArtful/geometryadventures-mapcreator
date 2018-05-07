package com.actionteam.geometryadventures.mapcreator;

import com.actionteam.geometryadventures.mapcreator.controller.Controller;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.badlogic.gdx.utils.TimeUtils;

public class Main extends ApplicationAdapter {

    private Controller controller;
    private Resources resources;
    private long time;

    public Main(Resources resources) {
        this.resources = resources;
    }

    @Override
    public void create() {
        controller = new Controller(resources);
        time = TimeUtils.millis();
        TexturePacker.process(Gdx.files.internal("sprites/").path(),
                Gdx.files.internal("env_packed/").path(),
                "envTextureAtlas.atlas");
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        controller.update();
        Clock.clock = (int) TimeUtils.millis();
    }

    @Override
    public void dispose() {
    }

    @Override
    public void resize(int width, int height) {
        controller.resize(width, height);
    }
}
