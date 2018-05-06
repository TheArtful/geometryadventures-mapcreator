package com.actionteam.geometryadventures.mapcreator.view;

import com.actionteam.geometryadventures.mapcreator.model.TileType;

/**
 * Created by theartful on 4/3/18.
 */

public interface TextureBoxListener {
    void textureChosen(TextureBox textureBox, TileType type, int textureIndex);
}
