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

public class ScreenFiesta extends Pantalla {//screenGlobos
    private int tamMundoWidth = 5120;
    private boolean passed = false; //se cambiara de nivel
    private boolean played = false; //se acciono el elevador

    private static Fondo fondo; //Imagen de fondo
//
    //Steven
    private PlayerSteven Steven;

    //Cop
    private FirstCop cop;
    private int TamEscena = 0;
    private MHMain juego;

    //Mom and daughter
    private Texture mom;
    private Sprite momNdaughter;

    //fondos
    private Texture fondoNegro;
    private Texture spotNegro;
    private boolean SwitchLight;

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

    private Sprite blackPanel;
    private Sprite Light;

    private Sprite radio;

    public ScreenFiesta(MHMain juego) {
        cop = new FirstCop(3500,64,tamMundoWidth);
        cop.setEstadoMovimiento(FirstCop.EstadoMovimiento.QUIETO);
        radio = new Sprite(new Texture("Radio.png"));
        radio.setX(3450);
        radio.setY(10);
        Light = new Sprite(new Texture("Luz.png"));

        Steven = new PlayerSteven(10,64,tamMundoWidth);
        Steven.setEstadoMovimiento(PlayerSteven.EstadoMovimiento.INICIANDO);

        this.juego = juego;

        controller = new Controller();

        blackPanel = new Sprite(new Texture("blackpanel.png"));
        SwitchLight = false;
        prefs = Gdx.app.getPreferences("My Preferences");
        //Crear a Steven
        //Steven = new PlayerSteven(xS,yS,tamMundoWidth);
        //Steven.setEstadoMovimiento(PlayerSteven.EstadoMovimiento.MOV_DERECHA);

        //Light.setX(Steven.getX());
        //Light.setY(Steven.getY());

        Gdx.input.setInputProcessor(escenaMenu);


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
            if(controller.isSpacePressed() || controller.isButtonPressed() && SwitchLight == false){
                SwitchLight = true;

                //Se espera un segundo
                float delay = 0.3f; // seconds
                Timer.schedule(new Timer.Task(){
                    @Override
                    public void run() {
                        // Do your work
                        SwitchLight = false;
                    }
                }, delay);
            }
        }
    }

    @Override
    public void show() {
        cargarTexturas();
    }

    private void cargarTexturas() {
        //Fondo de la escena
        BackgroundLayerOne = new Texture("PuzzleGlobos/patioFiesta.png");
        fondo = new Fondo(BackgroundLayerOne);
        fondo.setPosicion(0,0);
    }
    @Override
    public void render(float delta) {
        //cambiarEscena();
        Steven.actualizar();
        cop.actualizar();
        actualizarCamara();
        update(Gdx.graphics.getDeltaTime());
        borrarPantalla(0.8f,0.45f,0.2f);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camara.combined);
        batch.begin();

        //En esta parte se inicia el puzzle
        if(Steven.getX()>=3250){
            float delay = 4; // seconds
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    // Do your work
                    //sonidoF.stop();
                    juego.setScreen(new pantallaGlobos(juego));
                }
            }, delay);
        }

        //batch.draw(BackgroundLayerOne, Pantalla.ANCHO/2 -BackgroundLayerOne.getWidth()/2,Pantalla.ALTO/2-BackgroundLayerOne.getHeight()/2);
        fondo.render(batch);
        fondo.setPosicion(0,0);
        //usemos el radio para iniciar nuestro puzzle
        System.out.println("posicion: "+Steven.getX());
        batch.draw(radio,radio.getX(),radio.getY());
        Steven.dibujar(batch);
        /*cop.dibujar(batch);
        if(SwitchLight == true){
            batch.draw(Light,Steven.getX()-Light.getX()*60,Steven.getY()-Light.getY()*5);
        }else{
            batch.draw(blackPanel,camara.position.x-640,camara.position.y-360);
        }*/
        //dibujar imagen pintura, al clickear el metodo recibira una imagen dependiendo de la que mande
        //boton
        if(nImage>0 && nImage<16){
            //batch.draw(pinturas[nImage-1],50,100);
        }
        batch.end();
        //b2dr.render(world,camara.combined);
        //batch.setProjectionMatrix(camara.combined);
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
        /*if(Steven.getX()>=1270 && passed == false) {
            passed = true;
            trabar();
            nextScreenRight();
        }
        if(Steven.getX()<=10 && passed == false) {
            passed = true;
            trabar();
            nextScreenLeft();
        }*/
    }
    public void reaction(){//puertacerrada
        if(Steven.getX()>=4800 && Steven.getX()<=4900 &&  passed == false && prefs.getBoolean("playedSotano") == false) {
            prefs.putBoolean("playedSotano", true);
            prefs.flush();
            //llamar al dialogo
            passed = true;
            trabar();
            //Se espera un segundo
            float delay = 0.1f; // seconds
            Timer.schedule(new Timer.Task(){
                @Override
                public void run() {
                    // Do your work
                    juego.setScreen(new ScreenEleven(juego,10,64));
                }
            }, delay);
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
                juego.setScreen(new ScreenNine(juego,10,64));
            }
        }, delay);
    }
    private void nextScreenRight() {//derecha
        //Se espera un segundo
        float delay = 0.1f; // seconds

        Timer.schedule(new Timer.Task(){
            @Override
            public void run() {
                juego.setScreen(new ScreenTen(juego,10,64));
                // Do your work
                //juego.setScreen(new ScreenTen(juego,10,64));
                //llevar a cuartos
            }
        }, delay);
    }
    private void actualizarCamara() {
        float posX = Steven.sprite.getX();
        // Si está en la parte 'media'
        if(posX>=3200){
            camara.position.set(3200, camara.position.y, 0);
        }
        else if (posX>=ANCHO/2 && posX<=tamMundoWidth-ANCHO/2) {
            // El personaje define el centro de la cámara
            camara.position.set((int)posX, camara.position.y, 0);

        }
        camara.update();
    }
}
