package com.actionteam.geometryadventures.mapcreator.controller;

import com.actionteam.geometryadventures.mapcreator.Resources;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

/**
 * Created by theartful on 4/3/18.
 */

public class Controller extends Stage {

    private Resources resources;
    private UIController uiController;
    private WorldController worldController;

    public Controller(Resources resources) {
        this.resources = resources;
        resources.init(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        uiController = new UIController(resources);
        worldController = new WorldController();
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(uiController);
        inputMultiplexer.addProcessor(new GestureDetector(worldController));
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    public void update() {
        worldController.update();
        uiController.getViewport().apply();
        uiController.act();
        uiController.draw();
    }

    public void resize(int width, int height) {
        ((ScreenViewport) uiController.getViewport()).
                setUnitsPerPixel(resources.updateUiUnitsPerPixel(width, height));
        uiController.resize(width, height);
        worldController.resize(width, height);
    }
}
