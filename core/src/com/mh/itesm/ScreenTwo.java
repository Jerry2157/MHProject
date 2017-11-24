package com.mh.itesm;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Timer;

/**
 * Created by jerry2157 on 10/09/17.
 */

public class ScreenTwo extends Pantalla {
    private int tamMundoWidth = 1280;
    private boolean played = false;

    //ScreenTwoBNG
    private ScreenTwoAnim ScreenTwoBNG;
    private MHMain juego;
    private Texture BackgroundLayerOne;   // Imagen que se muestra

    //Para dialogos
    private Texture line1; //Dialogo
    private float tiempo=0;


    public ScreenTwo(MHMain juego) {
        //Crear a ScreenTwoBNG
        ScreenTwoBNG = new ScreenTwoAnim(0,0,tamMundoWidth);
        ScreenTwoBNG.setEstadoMovimiento(ScreenTwoAnim.EstadoMovimiento.Cutting);
        this.juego = juego;

        float delay = 4.0f; // seconds
        Timer.schedule(new Timer.Task(){
            @Override
            public void run() {
                // Do your work
                //LLAMA AL DIALOGO BETO
                //
                animStart();
            }
        }, delay);
    }

    public void animStart(){
        float delay = 0.5f; // seconds
        Timer.schedule(new Timer.Task(){
            @Override
            public void run() {
                // Do your worK
                ScreenTwoBNG.setEstadoMovimiento(ScreenTwoAnim.EstadoMovimiento.QUIETO);
                float delay = 1.0f; // seconds

                Timer.schedule(new Timer.Task(){
                    @Override
                    public void run() {
                        // Do your work
                        juego.setScreen(new ScreenThree(juego));
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



        batch.draw(BackgroundLayerOne, Pantalla.ANCHO/2 -BackgroundLayerOne.getWidth()/2, Pantalla.ALTO/2-BackgroundLayerOne.getHeight()/2);
        ScreenTwoBNG.dibujar(batch);
       if((int)tiempo>=3){
           batch.draw(line1, 1010, 500);
       }

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
        if(1100 == ScreenTwoBNG.getX() &&  64 <= ScreenTwoBNG.getY()){ //258  y 512 es la posicion del templo, lo identifique con el system.out.println
            // Para verificar si el usuario ya tomo los 3 pergaminos y liberar el boton de galeria de arte...
            /*liberarArte();
            this.efectoPuertaTemplo.play(PantallaMenu.volumen);
            PantallaCargando.partidaGuardada.putBoolean("nivelAgua", true); //se guarda el progreso y se desbloquea el nivel de agua...
            PantallaCargando.partidaGuardada.flush(); //se guardan los cambios*/
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

        }
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
                ScreenTwoBNG.setEstadoMovimiento(ScreenTwoAnim.EstadoMovimiento.QUIETO);
            }
        }, delay);
    }
}