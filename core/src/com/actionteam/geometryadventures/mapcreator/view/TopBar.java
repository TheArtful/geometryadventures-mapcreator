package com.actionteam.geometryadventures.mapcreator.view;

import com.actionteam.geometryadventures.mapcreator.Resources;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;

/**
 * Created by theartful on 4/3/18.
 */

public class TopBar extends Table {

    private TextButton drawBtn;
    private TextButton moveBtn;
    private TextButton deleteBtn;
    private TextButton saveBtn;
    private TextButton loadBtn;
    private TextButton selectBtn;
    private TextButton mapConfigBtn;
    private ButtonGroup<TextButton> buttonGroup;

    public TopBar(Resources resources) {
        drawBtn = new TextButton("Draw", resources.skin, "toggle");
        moveBtn = new TextButton("Move", resources.skin, "toggle");
        deleteBtn = new TextButton("Delete", resources.skin, "toggle");
        selectBtn = new TextButton("Select", resources.skin, "toggle");
        saveBtn = new TextButton("Save", resources.skin);
        loadBtn = new TextButton("Load", resources.skin);
        mapConfigBtn = new TextButton("Map Config", resources.skin);
        buttonGroup = new ButtonGroup<TextButton>(drawBtn, moveBtn, deleteBtn, selectBtn);
        buttonGroup.setMinCheckCount(1);
        buttonGroup.setMaxCheckCount(1);
        buttonGroup.setChecked("Draw");
        setBackground(new BackgroundColor(Color.rgba8888(1, 1, 1, 0.3f)));
        left();
        add(drawBtn).pad(5);
        add(moveBtn).pad(5);
        add(deleteBtn).pad(5);
        add(selectBtn).pad(5);
        add(mapConfigBtn).pad(5);
        add(saveBtn).pad(5).align(Align.right);
        add(loadBtn).pad(5).align(Align.right);
    }

    public TextButton getDrawBtn() {
        return drawBtn;
    }

    public TextButton getMoveBtn() {
        return moveBtn;
    }

    public TextButton getDeleteBtn() {
        return deleteBtn;
    }

    public TextButton getSaveBtn() {
        return saveBtn;
    }

    public TextButton getSelectBtn() {
        return selectBtn;
    }

    public TextButton getLoadBtn() {
        return loadBtn;
    }

    public ButtonGroup getButtonGroup() {
        return buttonGroup;
    }

    public TextButton getMapConfigBtn() {
        return mapConfigBtn;
    }

}
