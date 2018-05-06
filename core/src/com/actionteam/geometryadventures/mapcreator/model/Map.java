package com.actionteam.geometryadventures.mapcreator.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by theartful on 4/1/18.
 */

public class Map {

    private List<Tile> tiles;
    private Tile playerTile;
    private MapConfig config;

    public boolean newLight = true;

    public class MapConfig{
        public Vector3 ambientLight = new Vector3(1,1,1);
        public float ambientIntensity = 0.5f;
    }

    public Map() {
        tiles = new ArrayList<Tile>();
        config = new MapConfig();
    }

    public void addTile(Tile tile) {
        Gdx.app.log("Adding Tile", "x: " + tile.x + ", y: " + tile.y + ", textureName: " +
                tile.textureName + ", index:" + tile.textureIndex);

        newLight = true;

        for (Tile t : tiles) {
            if (t.x == tile.x && t.y == tile.y && t.z == tile.z) {
                removeTile(t);
                break;
            }
        }

        if (tile.tileType.equals(TileType.PLAYER)) {
            if(playerTile != null) removeTile(playerTile);
            playerTile = tile;
        }

        tiles.add(tile);
        Collections.sort(tiles, new Comparator<Tile>() {
            @Override
            public int compare(Tile tile, Tile t1) {
                return (tile.z - t1.z < 0) ? 1 : -1;
            }
        });
        config = new MapConfig();
    }

    public Tile searchTiles(float x, float y) {
        for (Tile tile : tiles) {
            if (tile.x == x && tile.y == y)
                return tile;
        }
        return null;
    }

    public List<Tile> getTiles() {
        return tiles;
    }

    public void removeTile(Tile tile) {
        if (tile == null) return;
        tiles.remove(tile);
        Gdx.app.log("Removing tile", "x: " + tile.x + ", y : " + tile.y);
    }

    public void setTiles(List<Tile> tiles) {
        this.tiles = tiles;
    }

    public MapConfig getConfig() {
        return config;
    }
}
