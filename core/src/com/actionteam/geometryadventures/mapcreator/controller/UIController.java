package com.actionteam.geometryadventures.mapcreator.controller;

import com.actionteam.geometryadventures.mapcreator.Resources;
import com.actionteam.geometryadventures.mapcreator.view.UI;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

/**
 * Created by theartful on 4/3/18.
 */

public class UIController extends Stage {

    private UI ui;
    private Resources resources;
    private ScreenViewport uiViewport;
    private Controller controller;

    public UIController(Resources resources, final Controller controller, TextureBoxController textureBoxController) {
        super(new ScreenViewport());
        this.resources = resources;
        uiViewport = (ScreenViewport) getViewport();
        uiViewport.setUnitsPerPixel(resources.getUiUnitsPerPixel());
        uiViewport.update(resources.screenWidth, resources.screenHeight);
        ui = new UI(resources);
        addActor(ui);
        ui.setFillParent(true);
        ui.getSideBar().setTextureBoxListener(textureBoxController);
        this.controller = controller;
        ui.getTopBar().getDrawBtn().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.fireEvent(MyEvents.DRAW_MODE, null);
            }
        });
        ui.getTopBar().getDeleteBtn().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.fireEvent(MyEvents.REMOVE_MODE, null);
            }
        });
        ui.getTopBar().getMoveBtn().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.fireEvent(MyEvents.FREE_MODE, null);
            }
        });
        ui.getTopBar().getSaveBtn().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.fireEvent(MyEvents.SAVE, null);
            }
        });
        ui.getTopBar().getLoadBtn().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.fireEvent(MyEvents.LOAD, null);
            }
        });
    }

    public void resize(int width, int height) {
        getViewport().update(width, height, true);
        ui.update(width, height);
    }

    public void handle(int eventCode, Object message) {
        switch (eventCode) {
            case MyEvents.SET_FREE_MODE:
                ui.getTopBar().getButtonGroup().setChecked(ui.getTopBar().getMoveBtn().getText().
                        toString());
                break;
            case MyEvents.SET_DRAW_MODE:
                ui.getTopBar().getButtonGroup().setChecked(ui.getTopBar().getDrawBtn().getText().
                        toString());
                break;
        }
    }
}
