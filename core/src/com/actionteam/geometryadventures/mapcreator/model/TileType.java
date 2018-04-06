package com.actionteam.geometryadventures.mapcreator.model;

/**
 * Created by theartful on 4/1/18.
 */

public class TileType {

    public static final int W_BOTTOM_RIGHT = 2;
    public static final int W_LEFT_RIGHT = 1;
    public static final int W_BOTTOM_LEFT = 0;
    public static final int W_TOP_BOTTOM = 3;
    public static final int W_TOP_LEFT = 5;
    public static final int W_TOP_RIGHT = 4;
    public static final int W_RIGHT = 9;
    public static final int W_BOTTOM = 7;
    public static final int W_TOP = 8;
    public static final int W_LEFT = 6;

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
