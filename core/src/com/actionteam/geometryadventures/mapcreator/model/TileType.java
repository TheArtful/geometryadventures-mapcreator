package com.actionteam.geometryadventures.mapcreator.model;

/**
 * Created by theartful on 4/1/18.
 */

public class TileType {

    public static final int W_BOTTOM_RIGHT = 0;
    public static final int W_LEFT_RIGHT = 1;
    public static final int W_BOTTOM_LEFT = 2;
    public static final int W_TOP_BOTTOM = 3;
    public static final int W_TOP_LEFT = 4;
    public static final int W_TOP_RIGHT = 5;
    public static final int W_RIGHT = 6;
    public static final int W_BOTTOM = 7;
    public static final int W_TOP = 8;
    public static final int W_LEFT = 9;
    public static final int W_LEFT_TOP_TOPLEFT = 10;
    public static final int W_LEFT_BOTTOM_BOTTOMLEFT = 11;
    public static final int W_RIGHT_LEFT_BOTTOM_BOTTOMRIGHT_BOTTOMLEFT = 12;
    public static final int W_TOP_RIGHT_LEFT_TOPRIGHT_TOPLEFT = 13;
    public static final int W_RIGHT_BOTTOM_BOTTOMRIGHT = 14;
    public static final int W_RIGHT_LEFT_BOTTOM_TOP = 15;
    public static final int W_RIGHT_TOP_TOPRIGHT = 16;
    public static final int W_LEFT_TOP_BOTTOM_TOPLEFT_BOTTOMLEFT = 17;
    public static final int W_RIGHT_TOP_BOTTOM_TOPRIGHT_BOTTOMRIGHT = 18;
    public static final int W_TOP_BOTTOM_RIGHT = 19;
    public static final int W_TOP_BOTTOM_LEFT = 20;
    public static final int W_TOP_BOTTOM_RIGHT_RIGHTBOTTOM = 21;
    public static final int W_TOP_BOTTOM_LEFT_LEFTBOTTOM = 22;
    public static final int W_BOTTOM_RIGHT_LEFT = 23;
    public static final int W_TOP_RIGHT_LEFT = 24;
    public static final int W_NOTHING = 25;
    public static final int W_LEFT_RIGHT_BOTTOM_BOTTOMRIGHT = 26;
    public static final int W_LEFT_RIGHT_BOTTOM_BOTTOMLEFT = 27;
    public static final int W_TOP_BOTTOM_RIGHT_TOPRIGHT = 34;
    public static final int W_TOP_RIGHT_LEFT_TOPRIGHT = 35;
    public static final int W_TOP_RIGHT_LEFT_TOPLEFT = 36;
    public static final int W_TOP_BOTTOM_LEFT_LEFTTOP = 37;
    public static final int W_RIGHT_LEFT_BOTTOM_TOP_TOPRIGHT = 38;
    public static final int W_RIGHT_LEFT_BOTTOM_TOP_TOPLEFT = 39;
    public static final int W_RIGHT_LEFT_BOTTOM_TOP_BOTTOMRIGHT = 40;
    public static final int W_RIGHT_LEFT_BOTTOM_TOP_BOTTOMLEFT = 41;
    public static final int W_ALL_EXCEPT_TOPLEFT = 41;
    public static final int W_ALL_EXCEPT_BOTTOMRIGHT = 39;
    public static final int W_ALL_EXCEPT_BOTTOMLEFT = 40;
    public static final int W_ALL_EXCEPT_TOPLRIGHT = 42;
    public static final int W_ALL = 33;


    public static final String WALL = "wall";
    public static final String FLOOR = "floor";
    public static final String ENEMY = "enemy";
    public static final String MISC = "misc";

    public String type;
    public String textureName;
    public int xTiles;
    public int numberOfTiles;
    public boolean isPattern;
    public int z;
    public boolean collidable;

    public TileType(String type, String textureName, int xTiles, int numberOfTiles, boolean isPattern,
                    int z, boolean collidable) {
        this.type = type;
        this.textureName = textureName;
        this.xTiles = xTiles;
        this.numberOfTiles = numberOfTiles;
        this.isPattern = isPattern;
        this.z = z;
        this.collidable = collidable;
    }
}
