package com.mh.itesm;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Stage;
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

    private EscenaPausa escenaPausa;
    private EstadoJuego estadoJuego = EstadoJuego.JUGANDO; //Estado del juego

    //badpeople
    private Texture evilTex;
    private Sprite evil;

    //World world;
    //private Box2DDebugRenderer b2dr;
    Body player;
    Controller controller;
    private Texture BackgroundLayerOne;   // Imagen que se muestra
    private Stage escenaMenu;

    private Preferences prefs;

    //Dialogos
    private Dialogos dialogos;
    private boolean playedDialogo;
    private boolean runningDialogo;
    //------
    //dialogo unico steven
    private Texture dialog1;
    private float tiempo;
    private Music sonidoF;

    public ScreenFive(MHMain juego, int xS, int yS) {

        //Dialogo
        playedDialogo = false;
        runningDialogo = false;
        dialogos = new Dialogos();
        //-------

        prefs = Gdx.app.getPreferences("My Preferences");

        //evilTex = new Texture("evilanim");
        //Crear a Steven
        Steven = new PlayerSteven(300,yS,tamMundoWidth);
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
            } else if (controller.isLeftPressed()) {
                //player.setLinearVelocity(new Vector2(-100, player.getLinearVelocity().y));
                Steven.setEstadoMovimiento(PlayerSteven.EstadoMovimiento.MOV_IZQUIERDA);
            } else {
                //player.setLinearVelocity(new Vector2(0, player.getLinearVelocity().y));
                Steven.setEstadoMovimiento(PlayerSteven.EstadoMovimiento.QUIETO);
            }
            if (controller.isUpPressed()) {
                //player.applyLinearImpulse(new Vector2(0, 20f), player.getWorldCenter(), true);
                //Steven.setEstadoMovimiento(PlayerSteven.EstadoMovimiento.QUIETO);
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
        BackgroundLayerOne = new Texture("ScreenFive/ScreenFiveBNG.png");
        dialog1=new Texture("Dialogos/Nivel1/12.png");
        //Sonido
        sonidoF= Gdx.audio.newMusic(Gdx.files.internal("Sonidos/voices.mp3"));
        sonidoF.setVolume(0.5f);
        sonidoF.play();
        sonidoF.setLooping(true);
    }

    @Override
    public void render(float delta) {
        cambiarEscena();
        Steven.actualizar();
        if(prefs.getBoolean("finalunlocked")) {
            cop.actualizar();
        }
        update(Gdx.graphics.getDeltaTime());
        borrarPantalla(0.8f,0.45f,0.2f);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camara.combined);
        batch.begin();

        batch.draw(BackgroundLayerOne, Pantalla.ANCHO/2 -BackgroundLayerOne.getWidth()/2, Pantalla.ALTO/2-BackgroundLayerOne.getHeight()/2);

        //Dialogo
        if((controller.isSpacePressed() || runningDialogo) && !playedDialogo){

            runningDialogo = true;
            playedDialogo = dialogos.dibujar(batch,1);
            played = !playedDialogo;
        }
        //-------

        Steven.dibujar(batch);
        if(prefs.getBoolean("finalunlocked")) {
            cop.dibujar(batch);
        }

        //aqui va el unico dialogo
        if((int)tiempo>=0 && (int)tiempo<=3){
            batch.draw(dialog1,110,285);
        }
        batch.end();
        //b2dr.render(world,camara.combined);
        //batch.setProjectionMatrix(camara.combined);
        if (estadoJuego == EstadoJuego.PAUSADO && escenaPausa!=null ) {
            escenaPausa.draw(); //DIBUJAMOS escenaPausa si esta pausado
        }
         controller.draw();
        tiempo += Gdx.graphics.getDeltaTime();
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
                sonidoF.stop();
                juego.setScreen(new ScreenSix(juego,20,64));
            }
        }, delay);
    }
    private void nextScreenLeftt() {
        //Se espera un segundo
        float delay = 0.5f; // seconds

        Timer.schedule(new Timer.Task(){
            @Override
            public void run() {
                // Do your work
                sonidoF.stop();
                juego.setScreen(new ScreenFive(juego,20,64));
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
}
