package com.mh.itesm;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Timer;

/**
 * Created by jerry2157 on 03/10/17.
 */

public class ScreenFive extends Pantalla {//cuarto steven
    private int tamMundoWidth = 1280;
    private boolean played = false;

    //Steven
    private PlayerSteven Steven;

    //Cop
    private FirstCop cop;
    private int TamEscena = 0;
    private MHMain juego;

    //Mom and daughter
    private Texture mom;
    private Sprite momNdaughter;

    //badpeople
    private Texture evilTex;
    private Sprite evil;

    //World world;
    //private Box2DDebugRenderer b2dr;
    Body player;
    Controller controller;
    private Texture BackgroundLayerOne;   // Imagen que se muestra
    //Pinturas interactuables
    //Imagen(Pintura) interactuable
    private Texture paint1,paint2, paint3, paint4, paint5, paint6, paint7, paint8, paint9, paint10, paint11, paint12, paint13, paint14, paint15, paint16;
    private Texture[] pinturas;
    //Variable nImage lleva el conteo de cuantos clicks en la pantalla se han hecho
    private int nImage;
    // Contenedor de los botones
    private Stage escenaMenu;
    private Texture texturaBtnPintura;
    private Preferences prefs;

    public ScreenFive(MHMain juego,int xS,int yS) {


        prefs = Gdx.app.getPreferences("My Preferences");

        //evilTex = new Texture("evilanim");
        //Crear a Steven
        Steven = new PlayerSteven(xS,yS,tamMundoWidth);
        Steven.setEstadoMovimiento(PlayerSteven.EstadoMovimiento.MOV_DERECHA);

        Gdx.input.setInputProcessor(escenaMenu);
        this.juego = juego;

        controller = new Controller();

        // evil
        if(prefs.getBoolean("finalunlocked")==true){
            evilTex = new Texture("evil.png");
            evil = new Sprite(evilTex);
            //cop
            cop = new FirstCop(3950,64,tamMundoWidth);
        }
    }
    public void Confrontation(){
        if(played == false){
            played = true;
            //inicia animacion de confrontacion
            prefs.putBoolean("finalscape",true);
        }
    }

    public void handleInput(){
        if(played == false) {
            if (controller.isRightPressed()) {
                //player.setLinearVelocity(new Vector2(100, player.getLinearVelocity().y));
                Steven.setEstadoMovimiento(PlayerSteven.EstadoMovimiento.MOV_DERECHA);
            } else if (controller.isLeftPressed() || played == true) {
                //player.setLinearVelocity(new Vector2(-100, player.getLinearVelocity().y));
                Steven.setEstadoMovimiento(PlayerSteven.EstadoMovimiento.MOV_IZQUIERDA);
            } else {
                //player.setLinearVelocity(new Vector2(0, player.getLinearVelocity().y));
                Steven.setEstadoMovimiento(PlayerSteven.EstadoMovimiento.QUIETO);
            }
            if (controller.isUpPressed() && player.getLinearVelocity().y == 0) {
                //player.applyLinearImpulse(new Vector2(0, 20f), player.getWorldCenter(), true);
                Steven.setEstadoMovimiento(PlayerSteven.EstadoMovimiento.QUIETO);
            }
        }
    }

    @Override
    public void show() {
        cargarTexturas();
        //Gdx.input.setInputProcessor(new ProcesadorEntrada());

    }

    private void cargarTexturas() {
        BackgroundLayerOne = new Texture("ScreenFive/ScreenFiveBNG.png");


    }

    @Override
    public void render(float delta) {
        cambiarEscena();
        Steven.actualizar();
        if(prefs.getBoolean("finalunlocked")==true) {
            cop.actualizar();
        }
        update(Gdx.graphics.getDeltaTime());
        borrarPantalla(0.8f,0.45f,0.2f);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camara.combined);
        batch.begin();

        batch.draw(BackgroundLayerOne, Pantalla.ANCHO/2 -BackgroundLayerOne.getWidth()/2,Pantalla.ALTO/2-BackgroundLayerOne.getHeight()/2);

        Steven.dibujar(batch);
        if(prefs.getBoolean("finalunlocked")==true) {
            cop.dibujar(batch);
        }
        //dibujar imagen pintura, al clickear el metodo recibira una imagen dependiendo de la que mande
        //boton
        if(nImage>0 && nImage<16){
            batch.draw(pinturas[nImage-1],50,100);
        }
        //batch.draw(puzzlePintura(),50,100);
        batch.end();
        //b2dr.render(world,camara.combined);
        //batch.setProjectionMatrix(camara.combined);
        if(Gdx.app.getType() == Application.ApplicationType.Android)
            controller.draw();
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

    public void update(float dt){
        handleInput();


        camara.update();

    }
    public void cambiarEscena(){
        if(Steven.getX()>=1100 && played == false) {
            played= true;
            Steven.setEstadoMovimiento(PlayerSteven.EstadoMovimiento.QUIETO);
            nextScreenRight();
        }
    }

    private void nextScreenRight() {
        //Se espera un segundo
        float delay = 0.5f; // seconds

        Timer.schedule(new Timer.Task(){
            @Override
            public void run() {
                // Do your work
                juego.setScreen(new ScreenSix(juego,20,64));
            }
        }, delay);
    }
}
