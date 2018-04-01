package com.actionteam.geometryadventures.mapcreator.model;

import com.badlogic.gdx.Gdx;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by theartful on 4/1/18.
 */

public class Map {

    private ArrayList<Tile> tiles;

    public Map() {
        tiles = new ArrayList<Tile>();
    }

    public void addTile(Tile tile) {
        Gdx.app.log("Adding Tile", "x: " + tile.x + ", y: " + tile.y + ", textureName: " +
                tile.textureName + ", index:" + tile.textureIndex);
        for (Tile t : tiles) {
            if (t.x == tile.x && t.y == tile.y && tile.type.equals(t.type)) {
                tiles.remove(t);
                break;
            }
        }
        tiles.add(tile);
        Collections.sort(tiles, new Comparator<Tile>() {
            @Override
            public int compare(Tile tile, Tile t1) {
                return (tile.z - t1.z < 0)? -1 : 1;
            }
        });
    }

    public Tile searchTiles(float x, float y) {
        for (Tile tile : tiles) {
            if (tile.x == x && tile.y == y)
                return tile;
        }
        return null;
    }

    public Tile searchTilesFiltered(float x, float y, String type) {
        for (Tile tile : tiles) {
            if (tile.x == x && tile.y == y && tile.type.equals(type))
                return tile;
        }
        return null;
    }

    public ArrayList<Tile> getTiles() {
        return tiles;
    }

    public void removeTile(Tile tile) {
        tiles.remove(tile);
        Gdx.app.log("Removing tile", "x: " + tile.x + ", y : " + tile.y);
    }
}
