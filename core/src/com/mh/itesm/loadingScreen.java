package com.mh.itesm;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

/**
 * Created by jerry2157 on 10/09/17.
 */

public class loadingScreen extends Pantalla {
    private MHMain juego;
    private float tiempo;   // Tiempo transcurrido
    private Texture loadingBngTex;   // Imagen que se muestra
    private Texture loadingTextTex;
    private Texture texturaReloj;   // Imagen que se muestra

    public loadingScreen(MHMain juego) {
        this.juego = juego;
    }

    // Se ejecuta cuando esta pantalla es la principal del juego
    @Override
    public void show() {
        tiempo = 0;
        loadingBngTex = new Texture("background_loading_1920.png");
        loadingTextTex = new Texture("text_loading.png");
        texturaReloj = new Texture("reloj.png");
    }

    @Override
    public void render(float delta) {
        borrarPantalla(0.6f, 0.7f, 0.3f);
        // Dibuja
        batch.setProjectionMatrix(camara.combined);
        batch.begin();
        batch.draw(loadingBngTex, Pantalla.ANCHO/2 -loadingBngTex.getWidth()/2,Pantalla.ALTO/2-loadingBngTex.getHeight()/2);
        //batch.draw(loadingBngTex, 0,0);

        batch.draw(loadingTextTex, Pantalla.ANCHO/6*5-loadingTextTex.getWidth(),Pantalla.ALTO/5-loadingTextTex.getHeight());
        //batch.draw(loadingTextTex, 0,0);
        //batch.draw(texturaReloj, Pantalla.ANCHO/2-texturaReloj.getWidth()/2, Pantalla.ALTO/2-texturaReloj.getHeight()/2);
        batch.end();
        // Actualiza
        tiempo += Gdx.graphics.getDeltaTime();  // Acumula tiempo
        if (tiempo>=0.1f) {
            juego.setScreen(new mainMenu(juego));
        }

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}
