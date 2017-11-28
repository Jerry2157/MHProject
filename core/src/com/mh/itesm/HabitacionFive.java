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

public class HabitacionFive extends Pantalla { //cocina
    private int tamMundoWidth = 1280;
    private boolean passed = false;
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
    private Texture texturaB4tnPintura;
    Preferences prefs;

    private EscenaPausa escenaPausa;
    private EstadoJuego estadoJuego = EstadoJuego.JUGANDO; //Estado del juego

    public HabitacionFive(MHMain juego, int xS, int yS) {
        prefs = Gdx.app.getPreferences("My Preferences");
        //Crear a Steven
        Steven = new PlayerSteven(xS,yS,tamMundoWidth);
        Steven.setEstadoMovimiento(PlayerSteven.EstadoMovimiento.MOV_DERECHA);

        Gdx.input.setInputProcessor(escenaMenu);
        this.juego = juego;

        controller = new Controller();
        cargarTexturas();
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
            if (controller.isUpPressed()) {
                //player.applyLinearImpulse(new Vector2(0, 20f), player.getWorldCenter(), true);
                Steven.setEstadoMovimiento(PlayerSteven.EstadoMovimiento.QUIETO);
            }
        }
    }
    public void pausaInput(){
        if(controller.isPausePressed() || controller.isBackPressed()){
            estadoJuego = estadoJuego== EstadoJuego.PAUSADO? EstadoJuego.JUGANDO: EstadoJuego.PAUSADO; // Se pausa el juego
            controller.setBackPressed(false);
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
        //Gdx.input.setInputProcessor(new ProcesadorEntrada());
        Gdx.input.setCatchBackKey(true);

    }

    private void cargarTexturas() {
        BackgroundLayerOne = new Texture("ScreenFourteen/Cuarto5.png");
    }

    @Override
    public void render(float delta) {
        cambiarEscena();
        reaction();
        Steven.actualizar();

        update(Gdx.graphics.getDeltaTime());
        borrarPantalla(0.8f,0.45f,0.2f);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camara.combined);
        batch.begin();

        batch.draw(BackgroundLayerOne, Pantalla.ANCHO/2 -BackgroundLayerOne.getWidth()/2, Pantalla.ALTO/2-BackgroundLayerOne.getHeight()/2);

        Steven.dibujar(batch);

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
        if(Steven.getX()>=1270 && passed == false) {//derecha
            passed = true;
            trabar();
            nextScreenRight();

        }
        if(Steven.getX()<=10 && passed == false) {//izquierda
            passed = true;
            trabar();
            nextScreenLeft();
        }
    }
    public void reaction(){//interaccion con la comida
        if(Steven.getX()>=520 && Steven.getX()<=620 && played == false && prefs.getBoolean("cuartosPassed") == false) {
            played = true;
            //launchDiaologue(dialogue.speech(6));
        }
    }
    public void trabar(){//bloquea a steven
        passed = true;
        Steven.setEstadoMovimiento(PlayerSteven.EstadoMovimiento.QUIETO);
    }

    public void launchDiaologue(boolean proceed){ //acciona el diaologo y regresa el control al final de este
        passed = false;
        Steven.setEstadoMovimiento(PlayerSteven.EstadoMovimiento.QUIETO);
    }


    private void nextScreenLeft() {//izquiera
        //Se espera un segundo
        float delay = 0.1f; // seconds

        Timer.schedule(new Timer.Task(){
            @Override
            public void run() {
                // Do your work
                juego.setScreen(new ScreenFourteen(juego,30,64));
            }
        }, delay);
    }
    private void nextScreenRight() {//derecha
        //Se espera un segundo
        float delay = 0.1f; // seconds

        Timer.schedule(new Timer.Task(){
            @Override
            public void run() {
                // Do your work
                juego.setScreen(new ScreenFourteen(juego,30,64));
            }
        }, delay);
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
