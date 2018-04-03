package com.actionteam.geometryadventures.mapcreator.desktop;

import com.actionteam.geometryadventures.mapcreator.Resources;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.actionteam.geometryadventures.mapcreator.Main;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 1024;
		config.height = 768;
		new LwjglApplication(new Main(new Resources()), config);
	}
}
