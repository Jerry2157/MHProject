package com.mh.itesm.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mh.itesm.MHMain;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

        /*float height = 20;


        float ppu = Gdx.graphics.getHeight() / height;
        float width = Gdx.graphics.getWidth() / ppu;

        config.height = (int) height; //Altura
        config.width = (int) width;  //Anchura*/
        //config.width = 1280;
        //config.height = 720;
		new LwjglApplication(new MHMain(), config);
	}
}
