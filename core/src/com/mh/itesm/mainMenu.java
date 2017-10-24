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
 * Created by jerry2157 on 10/09/17.
 */

public class mainMenu extends Pantalla{
    private MHMain juego;

    // Contenedor de los botones
    private Stage escenaMenu;

    // Texturas de los botones
    private Texture texturaBtnJugar;
    private Texture texturaBtnAyuda;
    private Texture texturaBtnCredits;
    private Texture texturaBackground;
    private Texture texturaBtnAjustes;


    public mainMenu(MHMain juego) {
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
        //--------------Inicia botones--------------
        //boton jugar
        TextureRegionDrawable trdPlay = new TextureRegionDrawable(new TextureRegion(texturaBtnJugar));
        ImageButton btnPlay = new ImageButton(trdPlay);
        btnPlay.setPosition(ANCHO/2 - btnPlay.getWidth()/2,0.7f*ALTO);
        escenaMenu.addActor(btnPlay);
        btnPlay.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                Gdx.app.log("clicked" , "***** TOUCH!!!!");
                juego.setScreen(new PantallaCargando(juego,Pantallas.PRIMER_NIVEL)); //Primer Nivel!!!!
            }
        });
        //boton
        TextureRegionDrawable trdAyuda = new TextureRegionDrawable(new TextureRegion(texturaBtnAyuda));
        ImageButton btnAyuda = new ImageButton(trdAyuda);
        btnAyuda.setPosition(ANCHO/2 - btnAyuda.getWidth()/2, 0.5f*ALTO);
        escenaMenu.addActor(btnAyuda);
        btnAyuda.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                Gdx.app.log("clicked" , "***** TOUCH!!!!");
                //BUEEEEEENAAAAAAAAAAAAASSSSS
                //juego.setScreen(new PantallaCargando(juego,Pantallas.NIVEL_WHACK_A_MOLE)); //Primer Nivel!!!!
                //BUUUUUEEEEENNNNNNNAAAAAAAASSS
                juego.setScreen(new ScreenFour(juego));
            }
        });
        //botonCreditos
        TextureRegionDrawable trdCreditos = new TextureRegionDrawable(new TextureRegion(texturaBtnCredits));
        ImageButton btnCredits = new ImageButton(trdCreditos);
        btnAyuda.setPosition(ANCHO/2 - btnCredits.getWidth()/2, 0.3f*ALTO);
        escenaMenu.addActor(btnCredits);
        btnCredits.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                Gdx.app.log("clicked" , "***** TOUCH!!!!");
                juego.setScreen(new Credits(juego)); //Primer Nivel!!!!
            }
        });

        //botonAjustes
        /*TextureRegionDrawable trdAjustes=new TextureRegionDrawable(new TextureRegion(texturaBtnAjustes));
        ImageButton btnAjustes= new ImageButton(trdAjustes);
        btnAjustes.setPosition(ANCHO/2-btnAjustes.getWidth()/2,0.3f*ALTO);
        escenaMenu.addActor(btnAjustes);
        btnAjustes.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                Gdx.app.log("clicked" , "***** TOUCH!!!!");
                juego.setScreen(new Credits(juego)); //Primer Nivel!!!!
            }
        });*/


        //-----------------fin botones---------------

    }

    private void cargarTexturas() {
        texturaBtnJugar = new Texture("NUEVOJUEGO.png");
        texturaBtnAyuda = new Texture("fondoNew.png");
        texturaBtnCredits = new Texture("CREDITOS.png");
        texturaBackground = new Texture("Menu/MenuBNG1920.png");
    }


    @Override
    public void render(float delta) {
        borrarPantalla(0.8f,0.45f,0.2f);
        batch.setProjectionMatrix(camara.combined);

        escenaMenu.draw();
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
