package com.actionteam.geometryadventures.mapcreator.view;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * Created by theartful on 4/3/18.
 */

public class MyScrollPane extends ScrollPane {

    public MyScrollPane(Actor widget, Skin skin) {
        super(widget, skin);
    }

    @Override
    public void setScrollX(float x){
    }
    @Override
    public void scrollX(float x){
    }
    @Override
    public void visualScrollX(float x){
    }
}
