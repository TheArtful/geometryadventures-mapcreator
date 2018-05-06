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
    private Tab miscTab;
    private Tab enemyTab;
    private Tab selectedTab;
    private Tab portalsTab;
    private Tab doorsTab;
    private Tab playerTab;
    private Tab lightTab;

    public SideBar(Resources resources) {
        this.resources = resources;
        lightTab = new Tab(resources.lights, resources);
        setBackground(new BackgroundColor(Color.rgba8888(1,1,1,0.3f)));
        initCategoryBar();
        floorsTab = new Tab(resources.floors, resources);
        wallsTab = new Tab(resources.walls, resources);
        miscTab = new Tab(resources.misc, resources);
        enemyTab = new Tab(resources.enemies, resources);
        portalsTab = new Tab(resources.portals, resources);
        doorsTab = new Tab(resources.doors, resources);
        playerTab = new Tab(resources.players, resources);

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
        categoryBar.getMiscBtn().addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                categoryChosen(miscTab);
            }
        });
        categoryBar.getEnemiesBtn().addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                categoryChosen(enemyTab);
            }
        });
        categoryBar.getPortalsBtn().addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                categoryChosen(portalsTab);
            }
        });
        categoryBar.getDoorsBtn().addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                categoryChosen(doorsTab);
            }
        });
        categoryBar.getPlayerBtn().addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                categoryChosen(playerTab);
            }
        });
        categoryBar.getLightBtn().addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                categoryChosen(lightTab);
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

    public void setTextureBoxListener(TextureBoxListener textureBoxListener) {
        floorsTab.setTextureBoxListener(textureBoxListener);
        wallsTab.setTextureBoxListener(textureBoxListener);
        miscTab.setTextureBoxListener(textureBoxListener);
        enemyTab.setTextureBoxListener(textureBoxListener);
        doorsTab.setTextureBoxListener(textureBoxListener);
        portalsTab.setTextureBoxListener(textureBoxListener);
        playerTab.setTextureBoxListener(textureBoxListener);
        lightTab.setTextureBoxListener(textureBoxListener);
    }
}