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
import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;

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
        world = new World(new TextureAtlas(Gdx.files.internal("textureatlas/textureatlas.atlas")),
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
        switch (mode) {
            case FREE_MODE:
                float dX = -deltaX * world.getUnitsPerPixel();
                float dY = deltaY * world.getUnitsPerPixel();
                world.translateCamera(dX, dY);
                break;
            case DRAWING_MODE:
                if (selectedTile != null) {
                    Vector2 pos = world.getViewport().unproject(new Vector2(x, y));
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
                if (selectedTile == null || selectedIndex < TextureBox.NOTHING) return true;
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
                Tile tile = new Tile();
                tile.type = selectedTile.type;
                tile.x = x;
                tile.y = y;
                tile.z = selectedTile.z;
                tile.textureName = selectedTile.textureName;
                tile.textureIndex = index;
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
        tile.type = selectedTile.type;
        tile.textureName = selectedTile.textureName;
        tile.x = x;
        tile.y = y;
        tile.z = selectedTile.z;
        map.addTile(tile);
        setAppropriateWall(tile, true);
    }

    private void setAppropriateWall(Tile tile, boolean recursive) {
        if (tile == null) {
            return;
        }
        Tile left = map.searchTilesFiltered(tile.x - 1, tile.y, TileType.WALL);
        Tile right = map.searchTilesFiltered(tile.x + 1, tile.y, TileType.WALL);
        Tile top = map.searchTilesFiltered(tile.x, tile.y + 1, TileType.WALL);
        Tile bottom = map.searchTilesFiltered(tile.x, tile.y - 1, TileType.WALL);
        Tile topLeft = map.searchTilesFiltered(tile.x - 1, tile.y + 1, TileType.WALL);
        Tile topRight = map.searchTilesFiltered(tile.x + 1, tile.y + 1, TileType.WALL);
        Tile bottomLeft = map.searchTilesFiltered(tile.x - 1, tile.y - 1, TileType.WALL);
        Tile bottomRight = map.searchTilesFiltered(tile.x + 1, tile.y - 1, TileType.WALL);

        if (bottom == null && left == null && right == null && top == null && topLeft == null &&
                topRight == null && bottomLeft == null && bottomRight == null) {
            tile.textureIndex = TileType.W_NOTHING;
        } else if (bottom != null && left == null && right != null && top == null &&
                bottomRight == null) {
            tile.textureIndex = TileType.W_BOTTOM_RIGHT;
        } else if (bottom != null && left == null && right != null && top == null &&
                bottomRight != null) {
            tile.textureIndex = TileType.W_RIGHT_BOTTOM_BOTTOMRIGHT;
        } else if (bottom == null && left != null && right != null && top == null) {
            tile.textureIndex = TileType.W_LEFT_RIGHT;
        } else if (bottom != null && left != null && right == null && top == null &&
                bottomLeft == null) {
            tile.textureIndex = TileType.W_BOTTOM_LEFT;
        } else if (bottom != null && left != null && right == null && top == null &&
                bottomLeft != null) {
            tile.textureIndex = TileType.W_LEFT_BOTTOM_BOTTOMLEFT;
        } else if (top != null && bottom != null && right == null && left == null) {
            tile.textureIndex = TileType.W_TOP_BOTTOM;
        } else if (top != null && left != null && right == null && bottom == null
                && topLeft == null) {
            tile.textureIndex = TileType.W_TOP_LEFT;
        } else if (top != null && left != null && right == null && bottom == null
                && topLeft != null) {
            tile.textureIndex = TileType.W_LEFT_TOP_TOPLEFT;
        } else if (top != null && right != null && left == null && bottom == null
                && topRight == null) {
            tile.textureIndex = TileType.W_TOP_RIGHT;
        } else if (top != null && right != null && left == null && bottom == null
                && topRight != null) {
            tile.textureIndex = TileType.W_RIGHT_TOP_TOPRIGHT;
        } else if (right != null && left == null && top == null && bottom == null) {
            tile.textureIndex = TileType.W_RIGHT;
        } else if (bottom != null && top == null && right == null && left == null) {
            tile.textureIndex = TileType.W_BOTTOM;
        } else if (top != null && bottom == null && right == null && left == null) {
            tile.textureIndex = TileType.W_TOP;
        } else if (left != null && right == null && top == null && right == null) {
            tile.textureIndex = TileType.W_LEFT;
        } else if (right != null && left != null && bottom != null && bottomRight != null &&
                bottomLeft != null && top == null) {
            tile.textureIndex = TileType.W_RIGHT_LEFT_BOTTOM_BOTTOMRIGHT_BOTTOMLEFT;
        } else if (right != null && left != null && bottom != null && bottomRight != null &&
                bottomLeft == null && top == null) {
            tile.textureIndex = TileType.W_LEFT_RIGHT_BOTTOM_BOTTOMRIGHT;
        } else if (right != null && left != null && bottom != null && bottomRight == null &&
                bottomLeft == null && top == null) {
            tile.textureIndex = TileType.W_BOTTOM_RIGHT_LEFT;
        } else if (top != null && bottom != null && right != null && topRight == null &&
                bottomRight == null) {
            tile.textureIndex = TileType.W_TOP_BOTTOM_RIGHT;
        } else if (top != null && bottom != null && right != null && topRight != null &&
                bottomRight == null && left == null) {
            tile.textureIndex = TileType.W_TOP_BOTTOM_RIGHT_TOPRIGHT;
        } else if (top != null && bottom != null && right != null && left == null &&
                topRight == null && bottomRight != null) {
            tile.textureIndex = TileType.W_TOP_BOTTOM_RIGHT_RIGHTBOTTOM;
        } else if (top != null && bottom != null && right != null && left == null &&
                topRight != null && bottomRight != null) {
            tile.textureIndex = TileType.W_RIGHT_TOP_BOTTOM_TOPRIGHT_BOTTOMRIGHT;
        } else if (top != null && right != null && bottom == null && left != null &&
                topLeft == null && topRight == null) {
            tile.textureIndex = TileType.W_TOP_RIGHT_LEFT;
        } else if (top != null && right != null && left != null && bottom == null &&
                topLeft != null && topRight == null) {
            tile.textureIndex = TileType.W_TOP_RIGHT_LEFT_TOPLEFT;
        } else if (top != null && right != null && left != null && topLeft == null &&
                topRight != null && bottom == null) {
            tile.textureIndex = TileType.W_TOP_RIGHT_LEFT_TOPRIGHT;
        } else if (top != null && bottom != null && left != null && topLeft == null &&
                bottomLeft == null && right == null) {
            tile.textureIndex = TileType.W_TOP_BOTTOM_LEFT;
        } else if (top != null && bottom != null && left != null && topLeft == null &&
                bottomLeft != null && right == null) {
            tile.textureIndex = TileType.W_TOP_BOTTOM_LEFT_LEFTBOTTOM;
        } else if (top != null && bottom != null && left != null && topLeft != null &&
                bottomLeft == null && right == null) {
            tile.textureIndex = TileType.W_TOP_BOTTOM_LEFT_LEFTTOP;
        } else if (top != null && right != null && left != null && bottom == null && topLeft != null
                && topRight != null) {
            tile.textureIndex = TileType.W_TOP_RIGHT_LEFT_TOPRIGHT_TOPLEFT;
        } else if (top != null && left != null && bottom != null && right != null &&
                topLeft != null && topRight != null && bottomLeft != null && bottomRight != null) {
            tile.textureIndex = TileType.W_ALL;
        } else if (top != null && left != null && bottom != null && topLeft != null &&
                bottomLeft != null && right == null) {
            tile.textureIndex = TileType.W_LEFT_TOP_BOTTOM_TOPLEFT_BOTTOMLEFT;
        } else if (top != null && left != null && right != null && bottom != null &&
                topLeft != null && topRight != null && bottomLeft == null && bottomRight != null) {
            tile.textureIndex = TileType.W_ALL_EXCEPT_BOTTOMLEFT;
        } else if (top != null && left != null && right != null && bottom != null &&
                topLeft != null && topRight != null && bottomLeft != null && bottomRight == null) {
            tile.textureIndex = TileType.W_ALL_EXCEPT_BOTTOMRIGHT;
        } else if (top != null && left != null && right != null && bottom != null &&
                topLeft == null && topRight != null && bottomLeft != null && bottomRight != null) {
            tile.textureIndex = TileType.W_ALL_EXCEPT_TOPLEFT;
        } else if (top != null && left != null && right != null && bottom != null &&
                topLeft != null && topRight == null && bottomLeft != null && bottomRight != null) {
            tile.textureIndex = TileType.W_ALL_EXCEPT_TOPLRIGHT;
        }

        if (recursive) {
            setAppropriateWall(bottom, false);
            setAppropriateWall(left, false);
            setAppropriateWall(top, false);
            setAppropriateWall(right, false);
            setAppropriateWall(topLeft, false);
            setAppropriateWall(topRight, false);
            setAppropriateWall(bottomLeft, false);
            setAppropriateWall(bottomRight, false);
        }
    }

    private void saveMap() {
        Gson gson = new Gson();
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
            Gson gson = new Gson();
            map = gson.fromJson(new FileReader(file), Map.class);
            world.setMap(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
