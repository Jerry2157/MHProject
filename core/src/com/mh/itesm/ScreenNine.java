package com.mh.itesm;

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

public class ScreenNine extends Pantalla { //cocina
    private int tamMundoWidth = 2560;
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
    private Sprite coocker;


    //World world;
    //private Box2DDebugRenderer b2dr;
    Body player;
    Controller controller;
    private Texture BackgroundLayerOne;   // Imagen que se muestra
    // Contenedor de los botones
    private Stage escenaMenu;
    private Texture texturaBtnPintura;
    Preferences prefs;

    //Dialogos
    private Dialogos dialogos;
    private boolean playedDialogo;
    private boolean runningDialogo;
    //------

    private static Fondo fondo; //Imagen de fondo

    private EscenaPausa escenaPausa;
    private EstadoJuego estadoJuego = EstadoJuego.JUGANDO; //Estado del juego

    public ScreenNine(MHMain juego, int xS, int yS) {

        //Dialogo
        playedDialogo = false;
        runningDialogo = false;
        dialogos = new Dialogos();
        //-------

        coocker = new Sprite(new Texture("Characters/Cocinera.png"));
        coocker.setPosition(32,64);
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
            if (controller.isUpPressed() /*&& player.getLinearVelocity().y == 0*/) {
                //player.applyLinearImpulse(new Vector2(0, 20f), player.getWorldCenter(), true);
                Steven.setEstadoMovimiento(PlayerSteven.EstadoMovimiento.QUIETO);
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
        //Gdx.input.setInputProcessor(new ProcesadorEntrada());

    }

    private void cargarTexturas() {
        BackgroundLayerOne = new Texture("ScreenNine/Cocina.png");
        fondo = new Fondo(BackgroundLayerOne);
        fondo.setPosicion(0,0);

    }

    @Override
    public void render(float delta) {
        reaction();
        cambiarEscena();
        Steven.actualizar();
        actualizarCamara();
        //cop.actualizar();
        update(Gdx.graphics.getDeltaTime());
        borrarPantalla(0.8f,0.45f,0.2f);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camara.combined);
        batch.begin();

        fondo.render(batch);
        fondo.setPosicion(0,0);

        //Dialogo
        if((Steven.getX()>=32 && Steven.getX()<=128 || runningDialogo) && !playedDialogo){
            played = playedDialogo;
            runningDialogo = true;
            playedDialogo = dialogos.dibujar(batch,1);
        }
        //-------

        //batch.draw(BackgroundLayerOne, Pantalla.ANCHO/2 -BackgroundLayerOne.getWidth()/2,Pantalla.ALTO/2-BackgroundLayerOne.getHeight()/2);
        batch.draw(coocker,coocker.getX(),coocker.getY());
        Steven.dibujar(batch);
        //cop.dibujar(batch);
        //Dialogo
        if((Steven.getX()<=200 || runningDialogo) && !playedDialogo  ){
            played = playedDialogo;
            runningDialogo = true;

            playedDialogo = dialogos.dibujar(batch,3);
        }
        //-------

        batch.end();

        if (estadoJuego == EstadoJuego.PAUSADO && escenaPausa!=null ) {
            if(Steven.getX()<=1800 /*&& Steven.getX()>=this.ANCHO/2*/){
                escenaPausa.updateBtnPos(this);
            }
            escenaPausa.draw(); //DIBUJAMOS escenaPausa si esta pausado
        }
        //si esta ocurriendo el sistema de dialogos steven no se puede mover ni pausar el juego
        if(playedDialogo==true || runningDialogo==false){
            controller.draw();
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

    public void update(float dt){
        handleInput();
        pausaInput();

        camara.update();

    }
    public void cambiarEscena(){
        if(Steven.getX()>=2460 && passed == false) {//derecha
            trabar();
            nextScreenRight();
        }
        if(Steven.getX()<=10 && passed == false) {//izquierda
            trabar();
            nextScreenLeft();
            prefs.putBoolean("cocinaPassed",true);
            prefs.flush();
        }
    }
    public void reaction(){//interaccion con la comida
        //if(Steven.getX()>=520 && Steven.getX()<=620 && played == false && !prefs.getBoolean("areaverdelocked") && !prefs.getBoolean("cocinaPassed")) {
        if(Steven.getX()>=520 && Steven.getX()<=620 && played == false && !prefs.getBoolean("cocinaPassed") && playedDialogo==true) {
            played = true;
            //launchDiaologue(dialogue.speech(6));
            juego.setScreen(new PantallaCargando(juego, Pantallas.NIVEL_WHACK_A_MOLE));
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
                //juego.setScreen(new ScreenFive(juego,10,64));
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
                juego.setScreen(new ScreenEight(juego,10,64));
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
