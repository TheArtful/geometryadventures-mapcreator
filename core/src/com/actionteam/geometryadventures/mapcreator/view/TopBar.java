package com.actionteam.geometryadventures.mapcreator.view;

import com.actionteam.geometryadventures.mapcreator.Resources;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

/**
 * Created by theartful on 4/3/18.
 */

public class TopBar extends Table {

    private TextButton drawBtn;
    private TextButton moveBtn;
    private TextButton deleteBtn;
    private ButtonGroup<TextButton> buttonGroup;

    public TopBar(Resources resources) {
        drawBtn = new TextButton("Draw", resources.skin, "toggle");
        moveBtn = new TextButton("Move", resources.skin, "toggle");
        deleteBtn = new TextButton("Delete", resources.skin, "toggle");
        buttonGroup = new ButtonGroup<TextButton>(drawBtn, moveBtn, deleteBtn);
        buttonGroup.setMinCheckCount(1);
        buttonGroup.setMaxCheckCount(1);
        buttonGroup.setChecked("Draw");
        setBackground(new BackgroundColor(Color.rgba8888(1, 1, 1, 0.3f)));
        left();
        add(drawBtn).pad(5);
        add(moveBtn).pad(5);
        add(deleteBtn).pad(5);
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

    public ButtonGroup getButtonGroup() {
        return buttonGroup;
    }
}
