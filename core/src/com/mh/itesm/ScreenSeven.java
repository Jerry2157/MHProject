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

public class ScreenSeven extends Pantalla {//elevador 2do piso
    private int tamMundoWidth = 1280;
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
    private Sprite momNdaughter;


    //World world;
    //private Box2DDebugRenderer b2dr;
    Body player;
    Controller controller;
    private Texture BackgroundLayerOne;   // Imagen que se muestra
    // Contenedor de los botones
    private Stage escenaMenu;
    private Texture texturaBtnPintura;
    private Preferences prefs;

    private EscenaPausa escenaPausa;
    private EstadoJuego estadoJuego = EstadoJuego.JUGANDO; //Estado del juego

    public ScreenSeven(MHMain juego, int xS, int yS) {
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
            if (controller.isUpPressed()) {
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
        BackgroundLayerOne = new Texture("ScreenSeven/ElevadorSegundoPiso.png");

    }

    @Override
    public void render(float delta) {
        cambiarEscena();
        Steven.actualizar();
        reaction();
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
        if(Steven.getX()>=1100 && !passed  && prefs.getBoolean("playedTalkDirCat") && !prefs.getBoolean("cuartosPassed")) { // && cuartosActivados == true
            passed = true;
            trabar();
            nextScreenRight();
        }
        if(Steven.getX()<=10 && !passed && prefs.getBoolean("cuartosPassed")) {
            passed = true;
            trabar();
            nextScreenLeft();
        }
    }
    public void reaction(){//elevador
        if(Steven.getX()>=600 && Steven.getX()<=900 && (controller.isButtonPressed() || controller.isSpacePressed()) && !passed && !prefs.getBoolean("finalunlocked") && !prefs.getBoolean("playedTalkDirCat")) {
            passed = true;
            trabar();

            //Se espera un segundo
            float delay = 0.1f; // seconds
            Timer.schedule(new Timer.Task(){
                @Override
                public void run() {
                    // Do your work
                    juego.setScreen(new ScreenEight(juego,640,20));
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
                juego.setScreen(new ScreenSix(juego,3700,64));
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
                juego.setScreen(new ScreenFourteen(juego,10,64));
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
}
