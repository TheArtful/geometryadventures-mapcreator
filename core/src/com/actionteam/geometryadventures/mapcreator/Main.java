package com.actionteam.geometryadventures.mapcreator;

import com.actionteam.geometryadventures.mapcreator.controller.Controller;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.TimeUtils;

public class Main extends ApplicationAdapter {

    private Controller controller;
    private Resources resources;
    private long time;
    private int clockRate = 300;

    public Main(Resources resources) {
        this.resources = resources;
    }

    @Override
    public void create() {
        controller = new Controller(resources);
        time = TimeUtils.millis();
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        controller.update();
        long t = TimeUtils.millis();
        if(t - time > clockRate) {
            Clock.clock++;
            time = t;
        }
    }

    @Override
    public void dispose() {
    }

    @Override
    public void resize(int width, int height) {
        controller.resize(width, height);
    }
}
