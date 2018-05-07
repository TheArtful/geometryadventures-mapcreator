package com.actionteam.geometryadventures.mapcreator.model;

/**
 * Created by theartful on 5/8/18.
 */

public class CollectibleTile extends Tile {
    public String subtype;

    public CollectibleTile(String subtype) {
        this.subtype = subtype;
    }
}
