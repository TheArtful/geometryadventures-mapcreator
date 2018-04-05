package com.actionteam.geometryadventures.mapcreator.model;

/**
 * Created by theartful on 4/1/18.
 */

public class TileType {

    public static final String WALL = "wall";
    public static final String FLOOR = "floor";
    public static final String ENEMY = "enemy";

    public String type;
    public String textureName;
    public int xTiles;
    public int numberOfTiles;
    public boolean isPattern;
    public int z;

    public TileType(String type, String textureName, int xTiles, int numberOfTiles, boolean isPattern,
                    int z) {
        this.type = type;
        this.textureName = textureName;
        this.xTiles = xTiles;
        this.numberOfTiles = numberOfTiles;
        this.isPattern = isPattern;
        this.z = z;
    }
}
