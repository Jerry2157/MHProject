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

public class ScreenThirteen extends Pantalla {//oficina director
    private int tamMundoWidth = 3840;
    private boolean passed = false; //se cambiara de nivel
    private boolean played = false; //se acciono el elevador

    //Steven
    private PlayerSteven Steven;

    //Cop
    private FirstCop cop;
    private int TamEscena = 0;
    private MHMain juego;

    //Mom and daughter
    private Texture mom;
    private Sprite escritorio;


    //World world;
    //private Box2DDebugRenderer b2dr;
    Body player;
    Controller controller;
    private Texture BackgroundLayerOne;   // Imagen que se muestra
    private Stage escenaMenu;

    Preferences prefs;

    private Sprite director;

    //Dialogos
    private Dialogos dialogos;
    private boolean playedDialogo;
    private boolean runningDialogo;
    private boolean startDialogue;
    //------

    //Dialogos
    private Dialogos dialogoTwo;
    private boolean playedDialogoTwo;
    private boolean runningDialogoTwo;
    private boolean finalDialogue;
    //------

    private EscenaPausa escenaPausa;
    private EstadoJuego estadoJuego = EstadoJuego.JUGANDO; //Estado del juego

    private boolean playedfinal = false;

    public ScreenThirteen(MHMain juego, int xS, int yS) {

        prefs = Gdx.app.getPreferences("My Preferences");
        //Dialogo
        playedDialogo = prefs.getBoolean("playedTalkDir");
        runningDialogo = false;
        dialogos = new Dialogos();
        //-------

        //Dialogo
        playedDialogoTwo = false;
        runningDialogoTwo = false;
        dialogoTwo = new Dialogos();
        finalDialogue = false;
        //-------

        escritorio = new Sprite(new Texture("ScreenThirteen/Escritorio.png"));
        escritorio.setPosition(40,10);

        director  = new Sprite(new Texture("Characters/Director.png"));
        director.setPosition(32,32);

        //Crear a Steven
        Steven = new PlayerSteven(xS,yS,tamMundoWidth);
        Steven.setEstadoMovimiento(PlayerSteven.EstadoMovimiento.MOV_DERECHA);
        cop = new FirstCop(10,10,tamMundoWidth);

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
        BackgroundLayerOne = new Texture("ScreenThirteen/OficinaDirectorSinMuebles.png");
    }
    @Override
    public void render(float delta) {
        reaction();
        cambiarEscena();
        Steven.actualizar();
        //cop.actualizar();
        update(Gdx.graphics.getDeltaTime());
        borrarPantalla(0.8f,0.45f,0.2f);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camara.combined);
        batch.begin();



        batch.draw(BackgroundLayerOne, Pantalla.ANCHO/2 -BackgroundLayerOne.getWidth()/2, Pantalla.ALTO/2-BackgroundLayerOne.getHeight()/2);
        director.draw(batch);
        batch.draw(escritorio,escritorio.getX(),escritorio.getY());
        Steven.dibujar(batch);

        //cop.dibujar(batch);

        //Dialogo
        if((prefs.getBoolean("playedTalkDir") || runningDialogo) && !playedDialogo && !prefs.getBoolean("playedTalkDirCat")){
            //played = playedDialogo;
            runningDialogo = true;
            playedDialogo = dialogos.dibujar(batch,7);
        }
        //-------

        //DialogoTwo
        if(prefs.getBoolean("playedTalkDir") && !playedDialogoTwo && prefs.getBoolean("playedTalkDirCat")){
            finalDialogue = true;
            runningDialogoTwo = true;

            playedDialogoTwo = dialogoTwo.dibujar(batch,8);
            arege();
        }
        //----------
        batch.end();
        //modificar posicion steven
        if (estadoJuego == EstadoJuego.PAUSADO && escenaPausa!=null ) {
            escenaPausa.draw(); //DIBUJAMOS escenaPausa si esta pausado
        }

        //si esta ocurriendo el sistema de dialogos steven no se puede mover ni pausar el juego
        if((playedDialogo==true || runningDialogo==false) && (playedDialogoTwo==true || runningDialogoTwo==false)){
            controller.draw();
        }
    }

    public void arege(){
        if(playedfinal == false){
            playedfinal = true;
            aregehelper();
        }
    }
    public void aregehelper(){
        //Se espera un segundo
        float delay = 6f; // seconds
        Timer.schedule(new Timer.Task(){
            @Override
            public void run() {
                // Do your work
                juego.setScreen(new ScreenSeven(juego,11,64));
            }
        }, delay);
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
        if(Steven.getX()>=1270 && passed == false && prefs.getBoolean("playedDir") == false) {
            passed = true;
            trabar();
            nextScreenRight();
        }
        /*if(Steven.getX()<=100 && passed == false  && !prefs.getBoolean("playedTalkDir")) {
            //passed = true;
            trabar();
            //nextScreenLeft();
            //prefs.putBoolean("playedTalkDir", false);
        }*/
    }
    public void reaction(){//hablar con director
        if(Steven.getX()>=230 && Steven.getX()<=250 &&  !passed && !prefs.getBoolean("playedTalkDir")) {//hablo la primera vez
            System.out.printf("holaaaaaaaa");
            prefs.putBoolean("playedTalkDir", true);

            //prefs.putBoolean("playedTalkDir", true);

            prefs.flush();
            //llamar al dialogo
            passed = true;
            trabar();
            //Se espera un segundo
            float delay = 12f; // seconds
            Timer.schedule(new Timer.Task(){
                @Override
                public void run() {
                    // Do your work
                    juego.setScreen(new ScreenGatos(juego,11,64));
                }
            }, delay);
        }
        if(Steven.getX()>=230 && Steven.getX()<=240 &&  !passed && prefs.getBoolean("playedTalkDir") && prefs.getBoolean("playedTalkDirCat")) {//hablo la segunda vez
            prefs.putBoolean("playedDir", true);
            prefs.putBoolean("playedTalkDir", true);
            prefs.putBoolean("playedTalkDirCat",true);
            prefs.putBoolean("continuehistory",true);


            prefs.flush();
            //llamar al dialogo
            passed = true;
            trabar();
            //Se espera un segundo
            float delay = 6.0f; // seconds
            Timer.schedule(new Timer.Task(){
                @Override
                public void run() {
                    // Do your work
                    juego.setScreen(new ScreenSeven(juego,10,64));
                }
            }, delay);
        }
    }
    public void trabar(){//bloquea a steven
        passed = true;
        Steven.setEstadoMovimiento(PlayerSteven.EstadoMovimiento.QUIETOTWO);

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
