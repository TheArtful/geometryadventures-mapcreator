package com.actionteam.geometryadventures.mapcreator;

import com.actionteam.geometryadventures.mapcreator.controller.WorldGestureListener;
import com.actionteam.geometryadventures.mapcreator.view.UI;
import com.actionteam.geometryadventures.mapcreator.view.World;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class Main extends ApplicationAdapter {

    private World world;
    private Stage stage;
    private Resources resources;
    private UI ui;

    public Main(Resources resources){
        this.resources = resources;
    }

    @Override
    public void create() {
        resources.init(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        world = new World(new TextureAtlas(Gdx.files.internal("textureatlas/textures.atlas")));
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        ScreenViewport uiViewport = new ScreenViewport();
        uiViewport.setUnitsPerPixel(resources.uiUnitsPerPixel);
        uiViewport.update(resources.screenWidth, resources.screenHeight);
        ui = new UI(resources);
        stage = new Stage(uiViewport);
        stage.addActor(ui);

        inputMultiplexer.addProcessor(stage);
        inputMultiplexer.addProcessor(new GestureDetector(new WorldGestureListener(world)));
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        world.render(0);
        stage.getViewport().apply();
        stage.act();
        stage.draw();
    }

    @Override
    public void dispose() {
    }

    @Override
    public void resize(int width, int height) {
        world.setSize(width, height);
        ((ScreenViewport) stage.getViewport()).
                setUnitsPerPixel(resources.updateUiUnitsPerPixel(width, height));
        stage.getViewport().update(width, height, true);
        ui.update(width, height);
    }
}
