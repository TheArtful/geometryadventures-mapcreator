package com.actionteam.geometryadventures.mapcreator;

import com.actionteam.geometryadventures.mapcreator.model.TileType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.google.gson.Gson;

import java.io.FileReader;
import java.util.ArrayList;

/**
 * Created by theartful on 4/1/18.
 */

public class Resources {
    public int screenWidth;
    public int screenHeight;
    public float uiUnitsPerPixel;
    public Skin skin;
    private float uiWidth;
    private float uiHeight;
    private float sideBarWidth;
    public ArrayList<TileType> floors;
    public ArrayList<TileType> walls;
    public TextureAtlas textureAtlas;
    public ShapeRenderer shapeRenderer;

    public Resources() {
    }

    public void init(int screenWidth, int screenHeight){
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.skin = new Skin(Gdx.files.internal("holo/skin/dark-hdpi/Holo-dark-hdpi.json"));
        updateUiUnitsPerPixel(screenWidth, screenHeight);
        floors = new ArrayList<TileType>();
        walls = new ArrayList<TileType>();
        try {
            loadTileTypes();
        } catch (Exception e) {
            e.printStackTrace();
        }
        textureAtlas = new TextureAtlas(Gdx.files.internal("textureatlas/textureatlas.atlas"));
        shapeRenderer = new ShapeRenderer();
    }

    public float updateUiUnitsPerPixel(int width, int height) {
        uiUnitsPerPixel = 2;
        uiUnitsPerPixel = 1600f / width;
        uiWidth = width * uiUnitsPerPixel;
        uiHeight = height * uiUnitsPerPixel;
        sideBarWidth = uiWidth / 4;
        return uiUnitsPerPixel;
    }

    public float getUiUnitsPerPixel() {
        return uiUnitsPerPixel;
    }

    public float getSideBarWidth() {
        return sideBarWidth;
    }

    public float getUiWidth() {
        return uiWidth;
    }

    public float getUiHeight() {
        return uiHeight;
    }

    protected boolean loadTileTypes() throws  Exception {
        TileType[] tileTypes;
        Gson gson = new Gson();
        tileTypes = gson.fromJson(new FileReader("cat.json"),
                TileType[].class);
        for(TileType type : tileTypes){
            if(type.type.equals(TileType.FLOOR)) floors.add(type);
            else if(type.type.equals(TileType.WALL)) walls.add(type);
        }
        return true;
    }
}
