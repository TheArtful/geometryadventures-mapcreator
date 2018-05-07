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
    private TextButton playerBtn;
    private TextButton miscBtn;
    private TextButton portalsBtn;
    private TextButton doorsBtn;
    private TextButton lightBtn;
    private TextButton collectiblesBtn;
    private ButtonGroup buttonGroup;

    public CategoryBar(Skin skin) {
        super(new Table());
        Table table = (Table) getActor();
        wallsBtn = new TextButton("Walls", skin, "toggle");
        floorsBtn = new TextButton("Floors", skin, "toggle");
        enemiesBtn = new TextButton("Enemies", skin, "toggle");
        playerBtn = new TextButton("Player", skin, "toggle");
        miscBtn = new TextButton("Misc", skin, "toggle");
        portalsBtn = new TextButton("Portals", skin, "toggle");
        doorsBtn = new TextButton("Doors", skin, "toggle");
        lightBtn = new TextButton("Light", skin, "toggle");
        collectiblesBtn = new TextButton("Collectibles", skin, "toggle");

        table.add(floorsBtn).pad(2);
        table.add(wallsBtn).pad(2);
        table.add(playerBtn).pad(2);
        table.add(enemiesBtn).pad(2);
        table.add(miscBtn).pad(2);
        table.add(portalsBtn).pad(2);
        table.add(doorsBtn).pad(2);
        table.add(lightBtn).pad(2);
        table.add(collectiblesBtn).pad(2);

        buttonGroup = new ButtonGroup(floorsBtn, wallsBtn, playerBtn, enemiesBtn, miscBtn, portalsBtn,
                doorsBtn, lightBtn, collectiblesBtn);
        buttonGroup.setMaxCheckCount(1);
        buttonGroup.setMinCheckCount(1);
        buttonGroup.setChecked("Floors");
    }

    TextButton getWallsBtn() {
        return wallsBtn;
    }

    TextButton getFloorsBtn() {
        return floorsBtn;
    }

    TextButton getEnemiesBtn() {
        return enemiesBtn;
    }

    TextButton getMiscBtn() {
        return miscBtn;
    }

    TextButton getPortalsBtn() {
        return portalsBtn;
    }

    TextButton getDoorsBtn() {
        return doorsBtn;
    }

    TextButton getPlayerBtn() {
        return playerBtn;
    }

    TextButton getLightBtn() {
        return lightBtn;
    }

    TextButton getCollectiblesBtn() {
        return collectiblesBtn;
    }
}
