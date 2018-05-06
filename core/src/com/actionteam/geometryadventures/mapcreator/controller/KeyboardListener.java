package com.actionteam.geometryadventures.mapcreator.controller;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;

/**
 * Created by theartful on 5/5/18.
 */

public class KeyboardListener extends InputAdapter {

    private WorldController worldController;

    public KeyboardListener(WorldController worldController) {
        this.worldController = worldController;
    }

    @Override
    public boolean keyDown(int keycode) {
        if(keycode == Input.Keys.I) {
            worldController.zoom(1, 1.5f);
            worldController.pinchStop();
            return true;
        }
        if(keycode == Input.Keys.O) {
            worldController.zoom(1.5f, 1f);
            worldController.pinchStop();
            return true;
        }
        return false;
    }

}
