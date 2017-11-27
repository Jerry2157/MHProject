package com.mh.itesm;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Timer;

/**
 * Created by jerry2157 on 10/09/17.
 */

public class ScreenFinalEnding extends Pantalla {
    private int tamMundoWidth = 1280;
    private boolean played = false;

    //ScreenTwoBNG
    private ScreenAnimFinal ScreenTwoBNG;
    private MHMain juego;
    private Texture BackgroundLayerOne;   // Imagen que se muestra

    //Para dialogos
    private Texture line1; //Dialogo
    private float tiempo=0;

    //Dialogos
    private Dialogos dialogos;
    private boolean playedDialogo;
    private boolean runningDialogo;
    //------

    private AssetManager manager;


    public ScreenFinalEnding(MHMain juego) {


        this.juego = juego;
        manager = juego.getAssetManager();

        //Crear a ScreenTwoBNG
        ScreenTwoBNG = new ScreenAnimFinal(0,0,tamMundoWidth,manager);
        ScreenTwoBNG.setEstadoMovimiento(ScreenAnimFinal.EstadoMovimiento.Cutting);

        float delay = 50.0f; // seconds
        Timer.schedule(new Timer.Task(){
            @Override
            public void run() {
                // Do your work
                //LLAMA AL DIALOGO BETO
                //
                animStart();
            }
        }, delay);

        float delayTwo = 40.0f;
        Timer.schedule(new Timer.Task(){
            @Override
            public void run() {
                // Do your work
                //LLAMA AL DIALOGO BETO
                //
                ScreenTwoBNG.setEstadoMovimiento(ScreenAnimFinal.EstadoMovimiento.QUIETO);
            }
        }, delayTwo);

        //Dialogo
        playedDialogo = false;
        runningDialogo = false;
        dialogos = new Dialogos();
        //-------


    }

    public void animStart(){
        float delay = 0.5f; // seconds
        Timer.schedule(new Timer.Task(){
            @Override
            public void run() {
                // Do your worK
                //ScreenTwoBNG.setEstadoMovimiento(ScreenAnimFinal.EstadoMovimiento.QUIETO);
                float delay = 1.0f; // seconds

                Timer.schedule(new Timer.Task(){
                    @Override
                    public void run() {
                        // Do your work
                        juego.setScreen(new mainMenu(juego));
                    }
                }, delay);
            }
        }, delay);
    }

    @Override
    public void show() {
        cargarTexturas();
    }
    private void cargarTexturas() {
        BackgroundLayerOne = new Texture("ScreenThree/ScreenThreeBNG.png");
        //Texturas dialogo
        line1=new Texture("Dialogos/Nivel1/di10.png");
    }

    @Override
    public void render(float delta) {
        cambiarEscena();
        ScreenTwoBNG.actualizar();
        update(Gdx.graphics.getDeltaTime());
        batch.setProjectionMatrix(camara.combined);
        borrarPantalla(0.8f,0.45f,0.2f);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();



        //batch.draw(BackgroundLayerOne, Pantalla.ANCHO/2 -BackgroundLayerOne.getWidth()/2, Pantalla.ALTO/2-BackgroundLayerOne.getHeight()/2);
        ScreenTwoBNG.dibujar(batch);
        if((int)tiempo>=3){
            //batch.draw(line1, 1010, 500);
        }
        //Dialogo
        if((int)tiempo>=10 && !playedDialogo){
            runningDialogo = true;
            playedDialogo = dialogos.dibujar(batch,11);
            played = !playedDialogo;
        }
        //-------

        batch.end();
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
        camara.update();

    }
    public void cambiarEscena(){
        /*if(1100 == ScreenTwoBNG.getX() &&  64 <= ScreenTwoBNG.getY()){ //258  y 512 es la posicion del templo, lo identifique con el system.out.println
            // Para verificar si el usuario ya tomo los 3 pergaminos y liberar el boton de galeria de arte...
            /*liberarArte();
            this.efectoPuertaTemplo.play(PantallaMenu.volumen);
            PantallaCargando.partidaGuardada.putBoolean("nivelAgua", true); //se guarda el progreso y se desbloquea el nivel de agua...
            PantallaCargando.partidaGuardada.flush(); //se guardan los cambios
            //juego.setScreen(new ScreenOne(juego));//Debug

            //Se espera un segundo
            float delay = 0.5f; // seconds
            System.out.println("paso uno");
            Timer.schedule(new Timer.Task(){
                @Override
                public void run() {
                    // Do your work
                    System.out.println("paso 2");
                    showPolice();
                }
            }, delay);
        }
        else if (64 == ScreenTwoBNG.getX() &&  64 <= ScreenTwoBNG.getY() && played == true){
            juego.setScreen(new ScreenFour(juego));
        }
        else{

        }*/
    }

    private void showPolice() {
        //Se espera un segundo
        float delay = 0.5f; // seconds

        Timer.schedule(new Timer.Task(){
            @Override
            public void run() {
                // Do your work
                played = true;
                System.out.println("paso 3");
                ScreenTwoBNG.setEstadoMovimiento(ScreenAnimFinal.EstadoMovimiento.QUIETO);
            }
        }, delay);
    }
}