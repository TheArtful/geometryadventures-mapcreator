package com.actionteam.geometryadventures.mapcreator.controller;

import com.actionteam.geometryadventures.mapcreator.Resources;
import com.actionteam.geometryadventures.mapcreator.model.Tile;
import com.actionteam.geometryadventures.mapcreator.view.Properties;
import com.actionteam.geometryadventures.mapcreator.view.SideBar;
import com.actionteam.geometryadventures.mapcreator.view.TopBar;
import com.actionteam.geometryadventures.mapcreator.view.UI;
import com.badlogic.gdx.Gdx;
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
    private TopBar topBar;
    private SideBar sideBar;
    private Properties properties;

    public UIController(Resources resources, final Controller controller, TextureBoxController textureBoxController) {
        super(new ScreenViewport());
        this.resources = resources;
        uiViewport = (ScreenViewport) getViewport();
        uiViewport.setUnitsPerPixel(resources.getUiUnitsPerPixel());
        uiViewport.update(resources.screenWidth, resources.screenHeight);
        ui = new UI(resources);
        addActor(ui);
        topBar = ui.getTopBar();
        sideBar = ui.getSideBar();
        properties = ui.getProperties();
        ui.setFillParent(true);
        sideBar.setTextureBoxListener(textureBoxController);
        this.controller = controller;
        topBar.getDrawBtn().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.fireEvent(MyEvents.DRAW_MODE, null);
            }
        });
        topBar.getDeleteBtn().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.fireEvent(MyEvents.REMOVE_MODE, null);
            }
        });
        topBar.getMoveBtn().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.fireEvent(MyEvents.FREE_MODE, null);
            }
        });
        topBar.getSaveBtn().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.fireEvent(MyEvents.SAVE, null);
            }
        });
        topBar.getSelectBtn().addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.fireEvent(MyEvents.SELECT_MODE, null);
            }
        });

        topBar.getLoadBtn().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.fireEvent(MyEvents.LOAD, null);
            }
        });

        topBar.getMapConfigBtn().addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.fireEvent(MyEvents.SHOW_PROPERTIES, new Object[]{null, 0, 0});
            }
        });

    }

    void resize(int width, int height) {
        getViewport().update(width, height, true);
        ui.update(width, height);
    }

    public void handle(int eventCode, Object message) {
        switch (eventCode) {
            case MyEvents.SET_FREE_MODE:
                ui.getTopBar().getButtonGroup().setChecked(ui.getTopBar().getMoveBtn().getText().
                        toString());
                ui.hideProperties();
                break;
            case MyEvents.SET_DRAW_MODE:
                ui.getTopBar().getButtonGroup().setChecked(ui.getTopBar().getDrawBtn().getText().
                        toString());
                ui.hideProperties();
                break;
            case MyEvents.SHOW_PROPERTIES:
                Gdx.app.log("UIController", "showing properties");
                Object[] mes = (Object[]) message;
                ui.showProperties((Tile) mes[0], (Integer) mes[1], (Integer) mes[2]);
                break;
            case MyEvents.DRAW_MODE:
                ui.hideProperties();
                break;
            case MyEvents.REMOVE_MODE:
                ui.hideProperties();
                break;
            case MyEvents.FREE_MODE:
                ui.hideProperties();
                break;
        }
    }
}
