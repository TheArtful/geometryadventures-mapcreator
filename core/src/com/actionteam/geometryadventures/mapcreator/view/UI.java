package com.actionteam.geometryadventures.mapcreator.view;

import com.actionteam.geometryadventures.mapcreator.Resources;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;

/**
 * Created by theartful on 4/1/18.
 */

public class UI extends Table {

    private SideBar sideBar;
    private TopBar topBar;
    private Skin skin;
    private Resources resources;

    public UI(Resources resources) {
        setFillParent(true);
        this.resources = resources;
        this.skin = resources.skin;
        sideBar = new SideBar(resources);
        topBar = new TopBar(resources);
    }

    public void update(int width, int height) {
        clearChildren();
        top();
        add(topBar).width(resources.getUiWidth() - resources.getSideBarWidth()).align(Align.top);
        add(sideBar).
                width(resources.getSideBarWidth()).height(resources.getUiHeight()).
                align(Align.right);
    }

    public SideBar getSideBar() {
        return sideBar;
    }

    public TopBar getTopBar() {
        return topBar;
    }
}
