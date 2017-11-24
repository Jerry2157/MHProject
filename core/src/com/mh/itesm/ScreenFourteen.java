package com.mh.itesm;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Timer;

/**
 * Created by jerry2157 on 03/10/17.
 */

public class ScreenFourteen extends Pantalla {//Habitaciones
    private int tamMundoWidth = 3840;
    private boolean passed = false; //se cambiara de nivel
    private boolean played = false; //se acciono el elevador

    //Steven
    private PlayerSteven Steven;

    //Cop
    private FirstCop cop;
    private int TamEscena = 0;
    private MHMain juego;

    //Enfermera
    private Texture enfermeraTex;
    private Sprite enfermera;


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
    Preferences prefs;


    public ScreenFourteen(MHMain juego, int xS, int yS) {
        enfermeraTex = new Texture("enfermera.png");
        enfermera = new Sprite(enfermeraTex);
        prefs = Gdx.app.getPreferences("My Preferences");
        //Crear a Steven
        Steven = new PlayerSteven(xS,yS,tamMundoWidth);
        Steven.setEstadoMovimiento(PlayerSteven.EstadoMovimiento.MOV_DERECHA);

        Gdx.input.setInputProcessor(escenaMenu);
        this.juego = juego;

        controller = new Controller();
    }

    public void handleInput(){
        if(passed == false) {
            if (controller.isRightPressed()) {
                //player.setLinearVelocity(new Vector2(100, player.getLinearVelocity().y));
                Steven.setEstadoMovimiento(PlayerSteven.EstadoMovimiento.MOV_DERECHA);
            } else if (controller.isLeftPressed() || passed == true) {
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
        cop.actualizar();
        update(Gdx.graphics.getDeltaTime());
        borrarPantalla(0.8f,0.45f,0.2f);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camara.combined);
        batch.begin();

        batch.draw(BackgroundLayerOne, Pantalla.ANCHO/2 -BackgroundLayerOne.getWidth()/2, Pantalla.ALTO/2-BackgroundLayerOne.getHeight()/2);
        if(prefs.getBoolean("cuartosPassed")==true) {
            batch.draw(enfermera, enfermera.getX(), enfermera.getY());
        }
        Steven.dibujar(batch);
        cop.dibujar(batch);
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
        if(Steven.getX()>=1270 && passed == false && prefs.getBoolean("playedDir") == false) {
            passed = true;
            trabar();
            nextScreenRight();
        }
        if(Steven.getX()<=10 && passed == false  && prefs.getBoolean("playedTalkDir") == true) {
            //passed = true;
            //trabar();
            //nextScreenLeft();
            //prefs.putBoolean("playedTalkDir", false);
        }
    }
    public void reaction(){//habitaciones
        if(Steven.getX()>=520 && Steven.getX()<=570 && passed == false && controller.isButtonPressed()) {//primera habitacion
            //llamar al dialogo
            passed = true;
            trabar();
            //Se espera un segundo
            float delay = 0.1f; // seconds
            Timer.schedule(new Timer.Task(){
                @Override
                public void run() {
                    // Do your work
                    juego.setScreen(new HabitacionOne(juego,10,64));
                }
            }, delay);
        }
        else if(Steven.getX()>=720 && Steven.getX()<=770 && passed == false && controller.isButtonPressed()) {//segunda habitacion
            //llamar al dialogo
            passed = true;
            trabar();
            //Se espera un segundo
            float delay = 0.1f; // seconds
            Timer.schedule(new Timer.Task(){
                @Override
                public void run() {
                    // Do your work
                    juego.setScreen(new HabitacionTwo(juego,10,64));
                }
            }, delay);
        }
        else if(Steven.getX()>=920 && Steven.getX()<=970 && passed == false && controller.isButtonPressed()) {//tercera habitacion
            //llamar al dialogo
            passed = true;
            trabar();
            //Se espera un segundo
            float delay = 0.1f; // seconds
            Timer.schedule(new Timer.Task(){
                @Override
                public void run() {
                    // Do your work
                    juego.setScreen(new HabitacionThree(juego,10,64));
                }
            }, delay);
        }
        else if(Steven.getX()>=1120 && Steven.getX()<=1170 && passed == false && controller.isButtonPressed()) {//cuarta habitacion
            //llamar al dialogo
            passed = true;
            trabar();
            //Se espera un segundo
            float delay = 0.1f; // seconds
            Timer.schedule(new Timer.Task(){
                @Override
                public void run() {
                    // Do your work
                    juego.setScreen(new HabitacionFour(juego,10,64));
                }
            }, delay);
        }
        else if(Steven.getX()>=1320 && Steven.getX()<=1370 &&  passed == false && controller.isButtonPressed()) {//quinta habitacion
            //llamar al dialogo
            passed = true;
            trabar();
            //Se espera un segundo
            float delay = 0.1f; // seconds
            Timer.schedule(new Timer.Task(){
                @Override
                public void run() {
                    // Do your work
                    juego.setScreen(new HabitacionFive(juego,10,64));
                }
            }, delay);
        }
        if(Steven.getX()>=620 && Steven.getX()<=670 &&  passed == false && prefs.getBoolean("cuartosPassed") == true) {//enfermera
            //llamar al dialogo
            passed = true;
            trabar();
            //llamar dialogo enfermera
            prefs.putBoolean("finalunlocked",true);
        }

    }
    public void trabar(){//bloquea a steven
        passed = true;
        Steven.setEstadoMovimiento(PlayerSteven.EstadoMovimiento.QUIETO);
    }


    private void nextScreenLeft() {//izquiera
        //Se espera un segundo
        float delay = 0.1f; // seconds

        Timer.schedule(new Timer.Task(){
            @Override
            public void run() {
                // Do your work
                juego.setScreen(new ScreenTwelve(juego,10,64));
                prefs.putBoolean("lockedDir", true);
            }
        }, delay);
    }
    private void nextScreenRight() {//derecha
        //Se espera un segundo
        float delay = 0.1f; // seconds

        Timer.schedule(new Timer.Task(){
            @Override
            public void run() {
                juego.setScreen(new ScreenTwelve(juego,10,64));
                // Do your work
                //juego.setScreen(new ScreenTen(juego,10,64));
                //llevar a cuartos
            }
        }, delay);
    }
}
