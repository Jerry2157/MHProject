package com.mh.itesm;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Timer;

import static java.lang.Math.abs;

/**
 * Created by jerry2157 on 10/09/17.
 */

public class ScreenFour extends Pantalla {//pasillo casa steven
    private int tamMundoWidth = 3840;
    private boolean played = false;
    private static Fondo fondo; //Imagen de fondo

    //Steven
    private PlayerSteven Steven;

    private EscenaPausa escenaPausa;
    private EstadoJuego estadoJuego = EstadoJuego.JUGANDO; //Estado del juego


    //FirstCop
    private FirstCop cop;

    //ContadorTiempo
    private float countTime;

    //ha sido golpeado Steven?
    private boolean kicked;
    //se ha dado la vuelta el policia?
    private boolean fliped;
    //se ha dado la vuelta y lo persigue?
    private  boolean flippedrunning;

    private boolean SceneActive;

    private int TamEscena = 0;
    private MHMain juego;

    World world;
    private Box2DDebugRenderer b2dr;
    Body player;
    Controller controller;
    private Texture BackgroundLayerOne;   // Imagen que se muestra
    private Stage escenaMenu;
    private Music sonidoF;


    public ScreenFour(MHMain juego) {
        SceneActive = false;
        kicked = false;
        fliped = false;
        flippedrunning = false;
        countTime = 0;
        //Crear a Steven
        Steven = new PlayerSteven(3500,64,tamMundoWidth);
        Steven.setEstadoMovimiento(PlayerSteven.EstadoMovimiento.MOV_DERECHA);
        //Crear a Cop
        cop = new FirstCop(3950,64,tamMundoWidth);
        cop.setEstadoMovimiento(FirstCop.EstadoMovimiento.MOV_IZQUIERDA);

        Gdx.input.setInputProcessor(escenaMenu);
        this.juego = juego;
        //world = new World(new Vector2(0,-9.81f),true);
        //manipular objeto world para manipular o cambiar con lo que hemos estado usando
        b2dr = new Box2DDebugRenderer();

        controller = new Controller();

        //proceduralActions
        runCop();
    }

    public void handleInput(){
        if(kicked == false) {
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
                Steven.setEstadoMovimiento(PlayerSteven.EstadoMovimiento.QUIETO);
            }
        }else{

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
    //es importante que se indique que parte debe tocar e ir pintando restringuiendo que parte toco, si es posible
    private void cargarTexturas() {
        BackgroundLayerOne = new Texture("ScreenFour/SceneFourBNG.png");
        fondo = new Fondo(BackgroundLayerOne);
        fondo.setPosicion(0,0);
        //Sonido
        sonidoF= Gdx.audio.newMusic(Gdx.files.internal("Sonidos/parque.mp3"));
        sonidoF.setVolume(0.5f);
        sonidoF.play();
        sonidoF.setLooping(true);
    }

    @Override
    public void render(float delta) {


        Steven.actualizar();
        cop.actualizar();
        actualizarCamara();
        update();
        borrarPantalla(0.8f,0.45f,0.2f);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camara.combined);

        batch.begin();
        fondo.render(batch);
        fondo.setPosicion(0,0);
        //batch.draw(BackgroundLayerOne, Pantalla.ANCHO/2 -BackgroundLayerOne.getWidth()/2,Pantalla.ALTO/2-BackgroundLayerOne.getHeight()/2);
        Steven.dibujar(batch);
        cop.dibujar(batch);



        //batch.draw(puzzlePintura(),50,100);
        batch.end();
        //b2dr.render(world,camara.combined);
        //batch.setProjectionMatrix(camara.combined);

        //verifica si el policia alcanzo a steven
        if(abs(cop.getX()-Steven.getX()) < 5){
            kicked = true;
            Steven.setEstadoMovimiento(PlayerSteven.EstadoMovimiento.QUIETO); //cambiar por modo herido
            cop.setEstadoMovimiento(FirstCop.EstadoMovimiento.QUIETO);//cambiar por modo golpe
            //Se espera un segundo
            float delay = 1; // seconds
            Timer.schedule(new Timer.Task(){
                @Override
                public void run() {
                    // Do your work

                    cop.setX(Steven.getX()-1000);
                    //cop.setEstadoMovimiento(FirstCop.EstadoMovimiento.Cutting);
                    if(SceneActive == false) {
                        SceneActive = false;
                        sonidoF.stop();
                        juego.setScreen(new ScreenFive(juego,20,64));
                    }
                }
            }, delay);
        }
        //si han pasado 10 segundos el policia se va y lo encuentra del otro lado
        countTime += Gdx.graphics.getDeltaTime();  // Acumula tiempo
        System.out.println(countTime);
        if (countTime>=5.0f && kicked == false && fliped == false) {
            fliped = true;
            cop.setEstadoMovimiento(FirstCop.EstadoMovimiento.MOV_DERECHA);
            System.out.println("se acabo tiempo");
            cop.VELOCIDAD_X = 5.0f;

            //Se espera un segundo
            float delay = 1; // seconds
            Timer.schedule(new Timer.Task(){
                @Override
                public void run() {
                    // Do your work

                    cop.setX(Steven.getX()-1000);
                    //cop.setEstadoMovimiento(FirstCop.EstadoMovimiento.Cutting);
                }
            }, delay);
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
    public void update(){
        handleInput();
        //world.step(1/60f,6,2);
        //camara.position.set(vista.getWorldWidth()/2,vista.getWorldHeight()/2,0);

        //camara.update();

        //camara.update();

    }

    private void runCop(){
        //Se espera un segundo
        float delay = 1; // seconds
        Timer.schedule(new Timer.Task(){
            @Override
            public void run() {
                // Do your work
                played = true;
                cop.setEstadoMovimiento(FirstCop.EstadoMovimiento.MOV_IZQUIERDA);
            }
        }, delay);
    }

    public void cambiarEscena(){
        if(1100 == Steven.getX() &&  64 <= Steven.getY()){ //258  y 512 es la posicion del templo, lo identifique con el system.out.println
            // Para verificar si el usuario ya tomo los 3 pergaminos y liberar el boton de galeria de arte...
            /*liberarArte();
            this.efectoPuertaTemplo.play(PantallaMenu.volumen);
            PantallaCargando.partidaGuardada.putBoolean("nivelAgua", true); //se guarda el progreso y se desbloquea el nivel de agua...
            PantallaCargando.partidaGuardada.flush(); //se guardan los cambios*/
            sonidoF.stop();
            juego.setScreen(new ScreenFive(juego,64,64));//Debug

            //Se espera un segundo
            float delay = 1; // seconds

            Timer.schedule(new Timer.Task(){
                @Override
                public void run() {
                    // Do your work
                    showPolice();
                }
            }, delay);
        }
        else if (64 == Steven.getX() &&  64 <= Steven.getY()){
            //juego.setScreen(new ScreenFour(juego));
        }
        else{

        }
    }



    private void showPolice() {
        //Se espera un segundo
        float delay = 1; // seconds
        Timer.schedule(new Timer.Task(){
            @Override
            public void run() {
                // Do your work
                played = true;
                Steven.setEstadoMovimiento(PlayerSteven.EstadoMovimiento.MOV_IZQUIERDA);
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
}