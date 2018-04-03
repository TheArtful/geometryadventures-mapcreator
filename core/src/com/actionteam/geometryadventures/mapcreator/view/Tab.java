package com.actionteam.geometryadventures.mapcreator.view;

import com.actionteam.geometryadventures.mapcreator.Resources;
import com.actionteam.geometryadventures.mapcreator.model.TileType;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import java.util.List;

/**
 * Created by theartful on 4/1/18.
 */

public class Tab extends MyScrollPane {

    private Skin skin;
    private List<TileType> tileTypes;
    private Resources resources;
    private Table table;

    public Tab(List<TileType> tileTypes, Resources resources) {
        super(new Table(), resources.skin);
        table = (Table) getActor();
        this.tileTypes = tileTypes;
        this.skin = resources.skin;
        this.resources = resources;

        for (TileType type : tileTypes) {
            drawType(type);
        }
    }

    private void drawType(TileType type) {
        Label label = new Label(type.textureName, skin);
        table.add(label).pad(5);
        table.row();
        TextureBox textureBox = new TextureBox(type, resources.getSideBarWidth(),
                resources);
        table.add(textureBox).width(resources.getSideBarWidth());
        table.row();
    }


}
