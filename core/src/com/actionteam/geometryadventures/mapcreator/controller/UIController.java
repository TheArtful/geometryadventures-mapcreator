package com.actionteam.geometryadventures.mapcreator.controller;

import com.actionteam.geometryadventures.mapcreator.Resources;
import com.actionteam.geometryadventures.mapcreator.view.UI;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

/**
 * Created by theartful on 4/3/18.
 */

public class UIController extends Stage {

    private UI ui;
    private Resources resources;
    private ScreenViewport uiViewport;

    public UIController(Resources resources){
        super(new ScreenViewport());
        this.resources = resources;
        uiViewport = (ScreenViewport) getViewport();
        uiViewport.setUnitsPerPixel(resources.getUiUnitsPerPixel());
        uiViewport.update(resources.screenWidth, resources.screenHeight);
        ui = new UI(resources);
        addActor(ui);
        ui.setFillParent(true);
    }

    public void resize(int width, int height) {
        getViewport().update(width, height, true);
        ui.update(width, height);
    }
}
