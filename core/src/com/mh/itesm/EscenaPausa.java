package com.mh.itesm;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.Viewport;


/**
 * Created by Usuario on 24-Oct-17.
 */

/*
public class EscenaPausa1 extends Stage {
    // La escena que se muestra cuando está pausado

    private  AssetManager manager; //para manegar texturas y demas con manager
    private MHMain juego;
    private EstadoJuego estadoJuego;

    public EscenaPausa1(Pantalla pantalla, Viewport vista, SpriteBatch batch) {
        super(vista, batch);
        iniciaVar();
        // Crear rectángulo transparente
        Pixmap pixmap = new Pixmap((int) (pantalla.ANCHO * 0.7f), (int) (pantalla.ALTO * 0.8f), Pixmap.Format.RGBA8888);
        pixmap.setColor(1f, 1f, 1f, 0.65f);
        pixmap.fillTriangle(0,pixmap.getHeight(),pixmap.getWidth(),pixmap.getHeight(),pixmap.getWidth()/2,0);
        Texture texturaTriangulo = new Texture(pixmap);
        pixmap.dispose();
        Image imgTriangulo = new Image(texturaTriangulo);
        imgTriangulo.setPosition(0.15f * pantalla.ANCHO, 0.1f * pantalla.ALTO);
        this.addActor(imgTriangulo);

        // Salir
        Texture texturaBtnSalir = manager.get("whackamole/btnSalir.png");
        TextureRegionDrawable trdSalir = new TextureRegionDrawable(
                new TextureRegion(texturaBtnSalir));
        ImageButton btnSalir = new ImageButton(trdSalir);
        btnSalir.setPosition(pantalla.ANCHO/2-btnSalir.getWidth()/2, pantalla.ALTO*0.2f);
        btnSalir.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Regresa al menú
                juego.setScreen(new mainMenu(juego));
            }
        });
        this.addActor(btnSalir);

        // Continuar
        Texture texturaBtnReintentar = manager.get("whackamole/btnContinuar.png");
        TextureRegionDrawable trdReintentar = new TextureRegionDrawable(
                new TextureRegion(texturaBtnReintentar));
        ImageButton btnReintentar = new ImageButton(trdReintentar);
        btnReintentar.setPosition(pantalla.ANCHO/2-btnReintentar.getWidth()/2, pantalla.ALTO*0.5f);
        btnReintentar.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Regresa al juego
                estadoJuego = EstadoJuego.JUGANDO;
                Gdx.input.setInputProcessor(ScreenOne.get.getStage());
            }
        });
        this.addActor(btnReintentar);
    }

    private void iniciaVar(){
        manager=juego.getAssetManager();
    }
}*/
