package com.actionteam.geometryadventures.mapcreator.view;

import com.actionteam.geometryadventures.mapcreator.Resources;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * Created by theartful on 4/1/18.
 */

public class SideBar extends Table {

    private CategoryBar categoryBar;
    private Resources resources;
    private Tab floorsTab;
    private Tab wallsTab;
    private Tab selectedTab;

    public SideBar(Resources resources) {
        this.resources = resources;
        setBackground(new BackgroundColor(Color.rgba8888(1,1,1,0.3f)));
        initCategoryBar();
        floorsTab = new Tab(resources.floors, resources);
        wallsTab = new Tab(resources.walls, resources);
        categoryChosen(floorsTab);
    }

    private void initCategoryBar() {
        categoryBar = new CategoryBar(resources.skin);
        top();
        add(categoryBar).width(resources.getSideBarWidth()).height(resources.getUiHeight() * 0.08f);
        categoryBar.getFloorsBtn().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                categoryChosen(floorsTab);
            }
        });
        categoryBar.getWallsBtn().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                categoryChosen(wallsTab);
            }
        });
    }

    public void categoryChosen(Tab tab) {
        if (selectedTab != null) {
            Cell cell = getCell(selectedTab);
            cell.setActor(tab);
        } else {
            row();
            add(tab);
        }
        selectedTab = tab;
    }
}