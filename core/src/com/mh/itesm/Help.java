package com.mh.itesm;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Created by jerry2157 on 11/09/17.
 */

public class Help extends Pantalla{
    private MHMain juego;

    // Contenedor de los botones
    private Stage escenaMenu;

    // Texturas de los botones
    private Texture texturaBtnJugar;
    private Texture texturaBtnAyuda;
    private Texture texturaBackground;

    public Help(MHMain juego) {
        this.juego = juego;
    }

    @Override
    public void show() {
        cargarTexturas();   // Carga imágenes
        crearEscenaMenu();  // Crea la escena
        Gdx.input.setInputProcessor(escenaMenu);
    }

    private void crearEscenaMenu() {
        escenaMenu = new Stage(vista);
        //boton jugar
        TextureRegionDrawable trdPlay = new TextureRegionDrawable(new TextureRegion(texturaBtnJugar));
        ImageButton btnPlay = new ImageButton(trdPlay);
        btnPlay.setPosition(ANCHO/3*2 - btnPlay.getWidth()/2,0.2f*ALTO);
        escenaMenu.addActor(btnPlay);
        btnPlay.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                Gdx.app.log("clicked" , "***** TOUCH!!!!");
                juego.setScreen(new mainMenu(juego)); //Primer Nivel!!!!
            }
        });
        //boton
        /*TextureRegionDrawable trdAyuda = new TextureRegionDrawable(new TextureRegion(texturaBtnAyuda));
        ImageButton btnAyuda = new ImageButton(trdAyuda);
        btnAyuda.setPosition(ANCHO/2 - btnAyuda.getWidth()/2, 0.3f*ALTO);
        escenaMenu.addActor(btnAyuda);*/
    }

    private void cargarTexturas() {
        texturaBtnJugar = new Texture("MENU.png");
        texturaBtnAyuda = new Texture("MENU.png");
        texturaBackground = new Texture("Menu/info1920.png");
    }


    @Override
    public void render(float delta) {
        borrarPantalla(0.0f,0.0f,0.0f);
        batch.setProjectionMatrix(camara.combined);
        batch.begin();
        batch.draw(texturaBackground, Pantalla.ANCHO/2 -texturaBackground.getWidth()/2,Pantalla.ALTO/2-texturaBackground.getHeight()/2);
        batch.end();
        escenaMenu.draw();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    // Liberar los recursos asignados
    @Override
    public void dispose() {

    }
}
