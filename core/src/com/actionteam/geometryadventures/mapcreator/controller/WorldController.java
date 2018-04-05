package com.actionteam.geometryadventures.mapcreator.controller;

import com.actionteam.geometryadventures.mapcreator.model.Map;
import com.actionteam.geometryadventures.mapcreator.model.Tile;
import com.actionteam.geometryadventures.mapcreator.model.TileType;
import com.actionteam.geometryadventures.mapcreator.view.TextureBox;
import com.actionteam.geometryadventures.mapcreator.view.World;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by theartful on 4/1/18.
 */

public class WorldController implements GestureDetector.GestureListener {

    private static final int FREE_MODE = 0;
    private static final int DRAWING_MODE = 1;
    private static final int DELETE_MODE = 2;

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

    public WorldController(Controller controller) {
        map = new Map();
        world = new World(new TextureAtlas(Gdx.files.internal("textureatlas/textures.atlas")),
                map);
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
                    previewMode = PATTERN_PREVIEW;
                    px1 = (int) x;
                    py1 = (int) y;
                    px2 = px1;
                    py2 = py1;
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
        switch (mode) {
            case FREE_MODE:
                float dX = -deltaX * world.getUnitsPerPixel();
                float dY = deltaY * world.getUnitsPerPixel();
                world.translateCamera(dX, dY);
                break;
            case DRAWING_MODE:
                if (selectedTile != null) {
                    Vector2 pos = world.getViewport().unproject(new Vector2(x, y));
                    px2 = (int) Math.floor(pos.x);
                    py2 = (int) Math.floor(pos.y);
                }
                break;
            case DELETE_MODE:
                Vector2 pos = world.getViewport().unproject(new Vector2(x, y));
                px2 = pos.x;
                py2 = pos.y;
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
                pos = world.getViewport().unproject(new Vector2(x, y));
                x = (int) Math.floor(pos.x);
                y = (int) Math.floor(pos.y);
                px1 = (int) x;
                py1 = (int) y;
                px2 = px1;
                py2 = py1;
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
                previewMode = NO_PREVIEW;
                if (selectedTile != null && selectedIndex != TextureBox.NOTHING) {
                    applyPreviewed();
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
                if(selectedTile == null || selectedIndex == TextureBox.NOTHING){
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
                Tile tile = new Tile();
                tile.type = selectedTile.type;
                tile.x = x;
                tile.y = y;
                tile.textureName = selectedTile.textureName;
                tile.textureIndex = index;
                map.addTile(tile);
            }
        }
    }

    private void applyDelete() {
        Tile tile;
        for (float x = (px1 < px2) ? px1 : px2; x <= ((px1 < px2) ? px2 : px1); x++) {
            for (float y = (py1 < py2) ? py1 : py2; y <= ((py1 < py2) ? py2 : py1); y++) {
                while ((tile = map.searchTiles((float) Math.floor(x), (float) Math.floor(y))) != null) {
                    map.removeTile(tile);
                }
            }
        }
    }

}
