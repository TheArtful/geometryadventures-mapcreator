package com.actionteam.geometryadventures.mapcreator.controller;

import com.actionteam.geometryadventures.mapcreator.Resources;
import com.actionteam.geometryadventures.mapcreator.RuntimeTypeAdapterFactory;
import com.actionteam.geometryadventures.mapcreator.model.CollectibleTile;
import com.actionteam.geometryadventures.mapcreator.model.EnemyTile;
import com.actionteam.geometryadventures.mapcreator.model.LightTile;
import com.actionteam.geometryadventures.mapcreator.model.Map;
import com.actionteam.geometryadventures.mapcreator.model.PlayerTile;
import com.actionteam.geometryadventures.mapcreator.model.PortalTile;
import com.actionteam.geometryadventures.mapcreator.model.Tile;
import com.actionteam.geometryadventures.mapcreator.model.TileType;
import com.actionteam.geometryadventures.mapcreator.view.TextureBox;
import com.actionteam.geometryadventures.mapcreator.view.World;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;

/**
 * Created by theartful on 4/1/18.
 */

public class WorldController implements GestureDetector.GestureListener {

    private static final int FREE_MODE = MyEvents.FREE_MODE;
    private static final int DRAWING_MODE = MyEvents.DRAW_MODE;
    private static final int DELETE_MODE = MyEvents.REMOVE_MODE;
    private static final int SELECT_MODE = MyEvents.SELECT_MODE;

    private static final int NO_PREVIEW = 0;
    private static final int PATTERN_PREVIEW = 1;
    private static final int DELETE_PREVIEW = 2;

    private World world;
    private Controller controller;
    private float zoomTmp;
    private boolean startPan;

    private int mode;
    private TileType selectedTile;
    private int selectedIndex;
    private float px1, px2, py1, py2;
    private int previewMode;
    private Map map;
    private Resources resources;
    private Tile selectModeTile;

    public WorldController(Controller controller, Resources resources) {
        map = new Map();
        resources.map = map;
        this.resources = resources;
        world = new World(resources.textureAtlas, map);
        this.controller = controller;
        mode = DRAWING_MODE;
        startPan = true;
        previewMode = NO_PREVIEW;
        this.controller = controller;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        if (zoomTmp == 0) zoomTmp = world.getUnitsPerPixel();
        world.setUnitsPerPixel((initialDistance / distance) * zoomTmp);
        return true;
    }

    private boolean panStart(float x, float y, float deltaX, float deltaY) {
        startPan = false;
        switch (mode) {
            case DRAWING_MODE:
                if (selectedTile != null && selectedIndex != TextureBox.NOTHING) {
                    Vector2 pos = world.getViewport().unproject(new Vector2(x, y));
                    x = (int) Math.floor(pos.x);
                    y = (int) Math.floor(pos.y);
                    px1 = (int) x;
                    py1 = (int) y;
                    px2 = px1;
                    py2 = py1;
                    if (selectedIndex == TextureBox.ALL && selectedTile.type.equals(TileType.WALL))
                        previewMode = NO_PREVIEW;
                    else
                        previewMode = PATTERN_PREVIEW;
                }
                break;
            case DELETE_MODE:
                Vector2 pos = world.getViewport().unproject(new Vector2(x, y));
                px1 = pos.x;
                py1 = pos.y;
                px2 = px1;
                py2 = py1;
                previewMode = DELETE_PREVIEW;
                break;
        }
        return true;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        if (startPan) return panStart(x, y, deltaX, deltaY);
        Vector2 pos;
        switch (mode) {
            case FREE_MODE:
                float dX = -deltaX * world.getUnitsPerPixel();
                float dY = deltaY * world.getUnitsPerPixel();
                world.translateCamera(dX, dY);
                break;
            case DRAWING_MODE:
                if (selectedTile != null) {
                    pos = world.getViewport().unproject(new Vector2(x, y));
                    pos.x = (int) Math.floor(pos.x);
                    pos.y = (int) Math.floor(pos.y);
                    if (selectedIndex == TextureBox.ALL && selectedTile.type.equals(TileType.WALL)) {
                        if (px2 != pos.x || py2 != pos.y)
                            addWall(pos.x, pos.y);
                    }
                    px2 = pos.x;
                    py2 = pos.y;
                }
                break;
            case DELETE_MODE:
                pos = world.getViewport().unproject(new Vector2(x, y));
                px2 = pos.x;
                py2 = pos.y;
                break;
            case SELECT_MODE:
                if (selectModeTile != null) {
                    pos = world.getViewport().unproject(new Vector2(x, y));
                    pos.x = (int) Math.floor(pos.x);
                    pos.y = (int) Math.floor(pos.y);
                    Tile tmp = map.searchTiles(pos.x, pos.y);
                    if (tmp == null || tmp.z < selectModeTile.z) {
                        selectModeTile.x = pos.x;
                        selectModeTile.y = pos.y;
                        if (selectModeTile.tileType.equals(TileType.LIGHT)) {
                            map.newLight = true;
                        }
                    }
                }
                break;
        }
        return true;
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        return true;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        Vector2 pos;
        switch (mode) {
            case DRAWING_MODE:
                if (selectedTile == null || selectedIndex == TextureBox.NOTHING) return true;
                pos = world.getViewport().unproject(new Vector2(x, y));
                x = (int) Math.floor(pos.x);
                y = (int) Math.floor(pos.y);
                px1 = (int) x;
                py1 = (int) y;
                px2 = px1;
                py2 = py1;
                if (selectedTile.type.equals(TileType.WALL) && selectedIndex == TextureBox.ALL) {
                    addWall(px1, py1);
                } else
                    applyPreviewed();
                break;
            case DELETE_MODE:
                pos = world.getViewport().unproject(new Vector2(x, y));
                x = (int) Math.floor(pos.x);
                y = (int) Math.floor(pos.y);
                px1 = (int) x;
                py1 = (int) y;
                px2 = px1;
                py2 = py1;
                applyDelete();
                break;
            case SELECT_MODE:
                pos = world.getViewport().unproject(new Vector2(x, y));
                x = (int) Math.floor(pos.x);
                y = (int) Math.floor(pos.y);
                Tile tile = world.getMap().searchTiles(x, y);
                this.selectModeTile = tile;
                Object[] mes = {tile, (int) x, (int) y};
                controller.fireEvent(MyEvents.SHOW_PROPERTIES, mes);
                break;
        }
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        startPan = true;
        switch (mode) {
            case DRAWING_MODE:
                if (previewMode == PATTERN_PREVIEW) {
                    if (selectedTile != null && selectedIndex != TextureBox.NOTHING) {
                        applyPreviewed();
                    }
                    previewMode = NO_PREVIEW;
                }
                break;
            case DELETE_MODE:
                previewMode = NO_PREVIEW;
                applyDelete();
                break;
        }
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }

    @Override
    public void pinchStop() {
        zoomTmp = 0;
    }

    public void update() {
        world.render(0);
        if (previewMode == PATTERN_PREVIEW)
            world.renderPreviewPattern(px1 < px2 ? (int) px1 : (int) px2,
                    py1 < py2 ? (int) py1 : (int) py2, px1 > px2 ? (int) px1 : (int) px2,
                    py1 > py2 ? (int) py1 : (int) py2, selectedIndex, selectedTile);
        if (previewMode == DELETE_PREVIEW)
            world.renderDeletePreview(px1 < px2 ? px1 : px2,
                    py1 < py2 ? py1 : py2, px1 > px2 ? px1 : px2,
                    py1 > py2 ? py1 : py2);
    }

    void resize(int width, int height) {
        world.setSize(width, height);
    }

    public void handle(int eventCode, Object message) {
        switch (eventCode) {
            case MyEvents.TEXTURE_CHOSEN:
                selectedTile = (TileType) (((Object[]) message)[0]);
                selectedIndex = (Integer) (((Object[]) message)[1]);
                if (selectedTile == null || selectedIndex == TextureBox.NOTHING) {
                    mode = FREE_MODE;
                    controller.fireEvent(MyEvents.SET_FREE_MODE, null);
                } else {
                    mode = DRAWING_MODE;
                    controller.fireEvent(MyEvents.SET_DRAW_MODE, null);
                }
                break;
            case MyEvents.REMOVE_MODE:
                mode = DELETE_MODE;
                break;
            case MyEvents.DRAW_MODE:
                mode = DRAWING_MODE;
                break;
            case MyEvents.FREE_MODE:
                mode = FREE_MODE;
                break;
            case MyEvents.SELECT_MODE:
                mode = SELECT_MODE;
                break;
            case MyEvents.SAVE:
                saveMap();
                break;
            case MyEvents.LOAD:
                loadMap();
                break;
        }
    }

    private void applyPreviewed() {
        for (float x = (px1 < px2) ? px1 : px2; x <= ((px1 < px2) ? px2 : px1); x++) {
            for (float y = (py1 < py2) ? py1 : py2; y <= ((py1 < py2) ? py2 : py1); y++) {
                int index;
                if (selectedIndex == TextureBox.ALL) {
                    index = Math.abs(((int) x) % selectedTile.xTiles) + selectedTile.xTiles *
                            Math.abs(((int) y) % (selectedTile.numberOfTiles / selectedTile.xTiles));
                } else
                    index = selectedIndex;
                Tile tile;
                if (selectedTile.type.equals(TileType.PORTAL))
                    tile = new PortalTile();
                else if (selectedTile.type.equals(TileType.ENEMY))
                    tile = new EnemyTile();
                else if (selectedTile.type.equals(TileType.PLAYER))
                    tile = new PlayerTile();
                else if (selectedTile.type.equals(TileType.LIGHT))
                    tile = new LightTile();
                else if (selectedTile.type.equals(TileType.COLLECTIBLE))
                    tile = new CollectibleTile(selectedTile.subtype);
                else
                    tile = new Tile();
                tile.tileType = selectedTile.type;
                tile.x = x;
                tile.y = y;
                tile.z = selectedTile.z;
                tile.collidable = selectedTile.collidable;
                tile.textureName = selectedTile.textureName;
                tile.textureIndex = index;
                tile.isAnimated = selectedTile.isAnimated;
                tile.frames = selectedTile.frames;
                tile.speed = selectedTile.speed != 0 ? selectedTile.speed : 1;
                map.addTile(tile);
            }
        }
    }

    private void applyDelete() {
        for (float x = (px1 < px2) ? px1 : px2; x <= ((px1 < px2) ? px2 : px1); x++) {
            for (float y = (py1 < py2) ? py1 : py2; y <= ((py1 < py2) ? py2 : py1); y++) {
                map.removeTile(map.searchTiles((float) Math.floor(x), (float) Math.floor(y)));
            }
        }
    }

    private void addWall(float x, float y) {
        Tile tile = new Tile();
        tile.tileType = selectedTile.type;
        tile.textureName = selectedTile.textureName;
        tile.textureIndex = selectedIndex;
        tile.x = x;
        tile.y = y;
        tile.z = selectedTile.z;
        tile.collidable = selectedTile.collidable;
        map.addTile(tile);
        // setAppropriateWall(tile, true);
    }

    private void saveMap() {
        RuntimeTypeAdapterFactory<Tile> rtaf = RuntimeTypeAdapterFactory.of(Tile.class, "type").
                registerSubtype(Tile.class).registerSubtype(PortalTile.class).
                registerSubtype(LightTile.class).registerSubtype(PlayerTile.class).
                registerSubtype(EnemyTile.class).registerSubtype(CollectibleTile.class);

        Gson gson = new GsonBuilder().registerTypeAdapterFactory(rtaf).create();
        String json = gson.toJson(map);
        PrintWriter writer = null;
        try {
            writer = new PrintWriter("map");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        writer.write(json);
        writer.close();
    }

    private void loadMap() {
        try {
            File file = new File("map");
            if (!file.exists()) return;

            RuntimeTypeAdapterFactory<Tile> rtaf = RuntimeTypeAdapterFactory.of(Tile.class, "type").
                    registerSubtype(Tile.class).registerSubtype(PortalTile.class).
                    registerSubtype(LightTile.class).registerSubtype(PlayerTile.class).
                    registerSubtype(EnemyTile.class).registerSubtype(CollectibleTile.class);

            Gson gson = new GsonBuilder().registerTypeAdapterFactory(rtaf).create();
            map = gson.fromJson(new FileReader(file), Map.class);
            resources.map = map;
            map.newLight = true;
            world.setMap(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
