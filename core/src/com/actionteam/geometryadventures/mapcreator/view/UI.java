package com.actionteam.geometryadventures.mapcreator.view;

import com.actionteam.geometryadventures.mapcreator.Resources;
import com.actionteam.geometryadventures.mapcreator.model.Tile;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Tree;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

/**
 * Created by theartful on 4/1/18.
 */

public class UI extends WidgetGroup {

    private SideBar sideBar;
    private TopBar topBar;
    private Properties properties;
    private Skin skin;
    private Resources resources;
    private Table table;

    private boolean showProperties = false;

    public UI(Resources resources) {
        setFillParent(true);
        this.resources = resources;
        this.skin = resources.skin;
        sideBar = new SideBar(resources);
        topBar = new TopBar(resources);
        properties = new Properties(resources);
        table = new Table();
        table.setFillParent(true);
        addActor(properties);
        addActor(table);
    }

    public void update(int width, int height) {
        table.clearChildren();
        if (showProperties) {
            properties.setVisible(true);
        }
        properties.update(width, height);
        table.add(topBar).align(Align.top).expand();
        table.add(sideBar).
                width(resources.getSideBarWidth()).height(resources.getUiHeight()).
                align(Align.right);
    }

    public SideBar getSideBar() {
        return sideBar;
    }

    public TopBar getTopBar() {
        return topBar;
    }

    public Properties getProperties() {
        return properties;
    }

    public void showProperties(Tile message, int x, int y) {
        showProperties = true;
        properties.setTile(message, x, y);
        update(0, 0);
    }

    public void hideProperties() {
        if (properties.pickTile) return;
        showProperties = false;
        update(0, 0);
    }
}
