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

public class ScreenFiveTwo extends Pantalla {//sotano
    private int tamMundoWidth = 1280;
    private boolean passed = false; //se cambiara de nivel
    private boolean played = true; //se acciono el elevador

    private static Fondo fondo; //Imagen de fondo

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
    private boolean SwitchLightTwo = true;

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

    private Sprite malo;


    //Dialogos
    private Dialogos dialogos;
    private boolean playedDialogo;
    private boolean runningDialogo;
    //------

    private EscenaPausa escenaPausa;
    private EstadoJuego estadoJuego = EstadoJuego.JUGANDO; //Estado del juego

    public ScreenFiveTwo(MHMain juego, int xS, int yS) {

        //Dialogo
        playedDialogo = false;
        runningDialogo = false;
        dialogos = new Dialogos();
        //-------

        //cop = new FirstCop(3550,64,tamMundoWidth);
        //cop.setEstadoMovimiento(FirstCop.EstadoMovimiento.QUIETO);
        radio = new Sprite(new Texture("Radio.png"));
        radio.setX(3500);
        radio.setY(5);
        Light = new Sprite(new Texture("Luz.png"));

        malo = new Sprite(new Texture("Characters/VillanoTwo.png"));
        malo.setPosition(64,64);

        blackPanel = new Sprite(new Texture("blackpanel.png"));
        SwitchLight = false;
        prefs = Gdx.app.getPreferences("My Preferences");
        //Crear a Steven
        Steven = new PlayerSteven(xS,yS,tamMundoWidth);
        Steven.setEstadoMovimiento(PlayerSteven.EstadoMovimiento.MOV_DERECHA);

        Light.setX(Steven.getX());
        Light.setY(Steven.getY());

        Gdx.input.setInputProcessor(escenaMenu);
        this.juego = juego;

        controller = new Controller();

        //Se espera un segundo
        float delay = 12.0f; // seconds

        Timer.schedule(new Timer.Task(){
            @Override
            public void run() {
                // Do your work
                SwitchLightTwo = false;

                //Se espera un segundo
                float delay = 2.0f; // seconds

                Timer.schedule(new Timer.Task(){
                    @Override
                    public void run() {
                        SceneHelper();

                        // Do your work
                        //juego.setScreen(new ScreenTen(juego,10,64));
                        //llevar a cuartos
                    }
                }, delay);

            }
        }, delay);
    }
    public void SceneHelper(){
        juego.setScreen(new PantallaCargando(juego,Pantallas.FINAL));
    }

    public void handleInput(){
        if(passed) {
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
                float delay = 0.1f; // seconds
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

    public void pausaInput(){
        if(controller.isPausePressed()){
            estadoJuego = estadoJuego== EstadoJuego.PAUSADO? EstadoJuego.JUGANDO: EstadoJuego.PAUSADO; // Se pausa el juego
        }
        if (estadoJuego== EstadoJuego.PAUSADO ) {
            // Activar escenaPausa y pasarle el control
            if (escenaPausa==null) {
                escenaPausa = new EscenaPausa(this,controller,vista, batch);
            }
            Gdx.input.setInputProcessor(escenaPausa);
            controller.pausePressed=false; //Evita que cree la escena varias veces
        }
    }

    @Override
    public void show() {
        cargarTexturas();
    }

    private void cargarTexturas() {
        BackgroundLayerOne = new Texture("ScreenFive/ScreenFiveBNG.png");
        fondo = new Fondo(BackgroundLayerOne);
        fondo.setPosicion(0,0);
    }
    @Override
    public void render(float delta) {
        //reaction();
        //cambiarEscena();
        Steven.actualizar();
        //cop.actualizar();
        actualizarCamara();
        update(Gdx.graphics.getDeltaTime());
        borrarPantalla(0.8f,0.45f,0.2f);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camara.combined);
        batch.begin();

        //batch.draw(BackgroundLayerOne, Pantalla.ANCHO/2 -BackgroundLayerOne.getWidth()/2,Pantalla.ALTO/2-BackgroundLayerOne.getHeight()/2);
        fondo.render(batch);
        fondo.setPosicion(0,0);
        batch.draw(radio,radio.getX(),radio.getY());
        batch.draw(malo,malo.getX(),malo.getY());
        Steven.dibujar(batch);
        //cop.dibujar(batch);
        if(SwitchLight && SwitchLightTwo){
            //batch.draw(Light,Steven.getX()-Light.getX()*60,Steven.getY()-Light.getY()*5);
            batch.draw(Light,camara.position.x-1280,Steven.getY()-Light.getY()*5-128);
        }else{
            batch.draw(blackPanel,camara.position.x-640,camara.position.y-360);
        }



        //Dialogo
        if((played || runningDialogo) && !playedDialogo){
            played = playedDialogo;
            runningDialogo = true;
            playedDialogo = dialogos.dibujar(batch,6);
        }
        //-------

        //batch.draw(puzzlePintura(),50,100);
        batch.end();
        if (estadoJuego == EstadoJuego.PAUSADO && escenaPausa!=null ) {
            escenaPausa.draw(); //DIBUJAMOS escenaPausa si esta pausado
        }
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
        pausaInput();
        camara.update();
    }

    public void cambiarEscena(){
        if(Steven.getX()>=3700 && passed == false) {
            passed = true;
            trabar();
            nextScreenRight();
        }
        /*if(Steven.getX()<=10 && passed == false) {
            passed = true;
            trabar();
            nextScreenLeft();
        }*/
    }
    public void reaction(){//puertacerrada
        if(Steven.getX()>=3500 && Steven.getX()<=3550 &&  passed == false && prefs.getBoolean("playedSotano") == false) {
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
                    juego.setScreen(new ScreenTen(juego,10,64));
                }
            }, delay);
        }
        if(Steven.getX()>=3400 && Steven.getX()<=3450 &&  passed == false && prefs.getBoolean("playedSotano") == false) {

            //llamar al dialogo
            passed = true;
            trabar();
            //Se espera un segundo
            float delay = 0.5f; // seconds
            Timer.schedule(new Timer.Task(){
                @Override
                public void run() {
                    // Do your work
                    SwitchLight = true;
                    malo.setPosition(5000.0f,5000.0f);
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
        if (posX>=ANCHO/2 && posX<=tamMundoWidth-ANCHO/2) {
            // El personaje define el centro de la cámara
            camara.position.set((int)posX, camara.position.y, 0);
            //fondo.setPosicion(posX,camara.position.y);
        } else if (posX>tamMundoWidth-ANCHO/2) {    // Si está en la última mitad
            // La cámara se queda a media pantalla antes del fin del mundo  :)
            camara.position.set(tamMundoWidth-ANCHO/2, camara.position.y, 0);
        } else if ( posX<ANCHO/2 ) { // La primera mitad
            camara.position.set(ANCHO/2, ALTO/2,0);
        }
        camara.update();
    }

    //Metodos get que nos permiten modificar en escena pausa
    public Pantalla getScreenFour(){
        return this;
    }
    public Controller getController(){
        return controller;
    }
    public MHMain getJuego(){
        return this.juego;
    }
    //public Music getSonidoF(){ return sonidoF;}
    public EstadoJuego getEstadoJuego(){
        return estadoJuego;
    }
    public void setEstadoJuego(EstadoJuego estado){
        estadoJuego=estado;
    }
    public PlayerSteven getPlayerSteven(){
        return Steven;
    }
}
