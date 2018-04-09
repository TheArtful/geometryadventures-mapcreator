package com.actionteam.geometryadventures.mapcreator.view;

import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

/**
 * Created by theartful on 4/1/18.
 */

public class CategoryBar extends ScrollPane {

    private TextButton wallsBtn;
    private TextButton floorsBtn;
    private TextButton enemiesBtn;
    private TextButton miscBtn;
    private ButtonGroup buttonGroup;

    public CategoryBar(Skin skin) {
        super(new Table());
        Table table = (Table) getActor();
        wallsBtn = new TextButton("Walls", skin, "toggle");
        floorsBtn = new TextButton("Floors", skin, "toggle");
        enemiesBtn = new TextButton("Enemies", skin, "toggle");
        miscBtn = new TextButton("Misc", skin, "toggle");
        table.setFillParent(true);
        table.add(new Label("", skin)).width(200);
        table.add(floorsBtn).pad(2);
        table.add(wallsBtn).pad(2);
        table.add(enemiesBtn).pad(2);
        table.add(miscBtn).pad(2);

        buttonGroup = new ButtonGroup(floorsBtn, wallsBtn, enemiesBtn, miscBtn);
        buttonGroup.setMaxCheckCount(1);
        buttonGroup.setMinCheckCount(1);
        buttonGroup.setChecked("Floors");
    }

    public TextButton getWallsBtn() {
        return wallsBtn;
    }

    public TextButton getFloorsBtn() {
        return floorsBtn;
    }

    public TextButton getEnemiesBtn() {
        return enemiesBtn;
    }

    public TextButton getMiscBtn() {
        return miscBtn;
    }
}
