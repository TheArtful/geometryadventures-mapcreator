package com.actionteam.geometryadventures.mapcreator;

import com.actionteam.geometryadventures.mapcreator.view.Grid;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Main extends ApplicationAdapter {

	Grid grid;

	@Override
	public void create() {
		grid = new Grid();
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		grid.render(0);
	}
	
	@Override
	public void dispose () {
	}

	@Override
	public void resize(int width, int height){
		grid.setSize(width, height);
	}
}
