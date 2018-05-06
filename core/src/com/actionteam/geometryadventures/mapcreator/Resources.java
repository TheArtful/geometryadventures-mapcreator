package com.actionteam.geometryadventures.mapcreator;

import com.actionteam.geometryadventures.mapcreator.model.Map;
import com.actionteam.geometryadventures.mapcreator.model.TileType;
import com.actionteam.geometryadventures.mapcreator.model.WeaponTypes;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.google.gson.Gson;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    private float propertiesHeight;
    public List<TileType> floors;
    public List<TileType> walls;
    public List<TileType> enemies;
    public List<TileType> misc;
    public List<TileType> portals;
    public List<TileType> doors;
    public List<TileType> lights;
    public TextureAtlas textureAtlas;
    public ShapeRenderer shapeRenderer;
    private boolean onlyFirstTime = true;
    public List<TileType> players;
    public Map map;

    public Resources() {
    }

    public void init(int screenWidth, int screenHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.skin = new Skin(Gdx.files.internal("holo/skin/dark-hdpi/Holo-dark-hdpi.json"));
        updateUiUnitsPerPixel(screenWidth, screenHeight);
        floors = new ArrayList<TileType>();
        walls = new ArrayList<TileType>();
        misc = new ArrayList<TileType>();
        enemies = new ArrayList<TileType>();
        portals = new ArrayList<TileType>();
        doors = new ArrayList<TileType>();
        players = new ArrayList<TileType>();
        lights = new ArrayList<TileType>();
        try {
            loadTileTypes();
        } catch (Exception e) {
            e.printStackTrace();
        }
        textureAtlas = new TextureAtlas(Gdx.files.internal("env_packed/envTextureAtlas.atlas"));
        shapeRenderer = new ShapeRenderer();
    }

    public float updateUiUnitsPerPixel(int width, int height) {
        if(onlyFirstTime) {
            uiUnitsPerPixel = 2;
            uiUnitsPerPixel = 1600f / width;
            sideBarWidth = width * uiUnitsPerPixel / 4;
            propertiesHeight = height * uiUnitsPerPixel / 8;
            onlyFirstTime = false;
        }
        uiWidth = width * uiUnitsPerPixel;
        uiHeight = height * uiUnitsPerPixel;

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

    protected boolean loadTileTypes() throws Exception {
        TileType[] tileTypes;
        Gson gson = new Gson();
        tileTypes = gson.fromJson(new FileReader("cat.json"),
                TileType[].class);
        for (TileType type : tileTypes) {
            if (type.type.equals(TileType.FLOOR)) floors.add(type);
            else if (type.type.equals(TileType.WALL)) walls.add(type);
            else if (type.type.equals(TileType.MISC)) misc.add(type);
            else if (type.type.equals(TileType.ENEMY)) enemies.add(type);
            else if (type.type.equals(TileType.DOOR)) doors.add(type);
            else if (type.type.equals(TileType.PORTAL)) portals.add(type);
            else if (type.type.equals(TileType.PLAYER)) players.add(type);
            else if (type.type.equals(TileType.LIGHT)) lights.add(type);
        }
        WeaponTypes.types =
                Arrays.asList((String[])gson.fromJson(new FileReader("weapontypes.json"), String[].class));
        return true;
    }

    public float getPropertiesHeight() {
        return propertiesHeight;
    }
}
