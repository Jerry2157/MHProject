package com.mh.itesm;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Timer;

/**
 * Created by jerry2157 on 10/09/17.
 */

public class ScreenTwo extends Pantalla {
    private int tamMundoWidth = 1280;
    private boolean played = false;

    //ScreenTwoBNG
    private ScreenTwoAnim ScreenTwoBNG;


    private int TamEscena = 0;
    private MHMain juego;

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


    public ScreenTwo(MHMain juego) {
        //Crear a ScreenTwoBNG
        ScreenTwoBNG = new ScreenTwoAnim(0,0,tamMundoWidth);
        ScreenTwoBNG.setEstadoMovimiento(ScreenTwoAnim.EstadoMovimiento.Cutting);

        Gdx.input.setInputProcessor(escenaMenu);
        this.juego = juego;
        //world = new World(new Vector2(0,-9.81f),true);
        //manipular objeto world para manipular o cambiar con lo que hemos estado usando
        //b2dr = new Box2DDebugRenderer();

        //Inicializamos variables
        pinturas=new Texture[16];
        nImage=0;

        //createGround();
        //createPlayer();
        controller = new Controller();
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
    public void handleInput(){
        if (0==1) {//USELESSSSSSSSSSS
            if (controller.isRightPressed()) {
                //player.setLinearVelocity(new Vector2(100, player.getLinearVelocity().y));
                ScreenTwoBNG.setEstadoMovimiento(ScreenTwoAnim.EstadoMovimiento.MOV_DERECHA);
            } else if (controller.isLeftPressed() || played == true) {
                //player.setLinearVelocity(new Vector2(-100, player.getLinearVelocity().y));
                ScreenTwoBNG.setEstadoMovimiento(ScreenTwoAnim.EstadoMovimiento.QUIETO);
            } else {
                //player.setLinearVelocity(new Vector2(0, player.getLinearVelocity().y));
                ScreenTwoBNG.setEstadoMovimiento(ScreenTwoAnim.EstadoMovimiento.QUIETO);
            }
            if (controller.isUpPressed() && player.getLinearVelocity().y == 0) {
                //player.applyLinearImpulse(new Vector2(0, 20f), player.getWorldCenter(), true);
                ScreenTwoBNG.setEstadoMovimiento(ScreenTwoAnim.EstadoMovimiento.QUIETO);
            }
        }
    }

    @Override
    public void show() {
        cargarTexturas();
        //Gdx.input.setInputProcessor(new ProcesadorEntrada());

    }

    //es importante que se indique que parte debe tocar e ir pintando restringuiendo que parte toco, si es posible


    private void cargarTexturas() {
        BackgroundLayerOne = new Texture("ScreenThree/ScreenThreeBNG.png");
        //Imagenes de la pinturas
        paint1 =new Texture("Puzzle1/P1.png");
        pinturas[0]=paint1;
        paint2 =new Texture("Puzzle1/P2.png");
        pinturas[1]=paint2;
        paint3=new Texture("Puzzle1/P3.png");
        pinturas[2]=paint3;
        paint4 =new Texture("Puzzle1/P4.png");
        pinturas[3]=paint4;
        paint5 =new Texture("Puzzle1/P5.png");
        pinturas[4]=paint5;
        paint6 =new Texture("Puzzle1/P6.png");
        pinturas[5]=paint6;
        paint7 =new Texture("Puzzle1/P7.png");
        pinturas[6]=paint7;
        paint8 =new Texture("Puzzle1/P8.png");
        pinturas[7]=paint8;
        paint9 =new Texture("Puzzle1/P9.png");
        pinturas[8]=paint9;
        paint10 =new Texture("Puzzle1/P10.png");
        pinturas[9]=paint10;
        paint11 =new Texture("Puzzle1/P11.png");
        pinturas[10]=paint11;
        paint12 =new Texture("Puzzle1/P12.png");
        pinturas[11]=paint12;
        paint13 =new Texture("Puzzle1/P13.png");
        pinturas[12]=paint13;
        paint14 =new Texture("Puzzle1/P14.png");
        pinturas[13]=paint14;
        paint15 =new Texture("Puzzle1/P15.png");
        pinturas[14]=paint15;
        paint16 =new Texture("Puzzle1/P16.png");
        pinturas[15]=paint16;

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

        batch.draw(BackgroundLayerOne, Pantalla.ANCHO/2 -BackgroundLayerOne.getWidth()/2,Pantalla.ALTO/2-BackgroundLayerOne.getHeight()/2);
        ScreenTwoBNG.dibujar(batch);
        //dibujar imagen pintura, al clickear el metodo recibira una imagen dependiendo de la que mande
        //boton
        if(nImage>0 && nImage<16){
            batch.draw(pinturas[nImage-1],50,100);
        }

        //batch.draw(puzzlePintura(),50,100);
        batch.end();
        //b2dr.render(world,camara.combined);
        //batch.setProjectionMatrix(camara.combined);
        if(Gdx.app.getType() == Application.ApplicationType.Android)
            controller.draw();


    }





    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }
    /*public void createGround(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(vista.getWorldWidth()/2,0);
        bdef.type = BodyDef.BodyType.StaticBody;
        Body b2body = world.createBody(bdef);
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(vista.getWorldWidth()/2,20);
        fdef.shape = shape;
        b2body.createFixture(fdef);
    }
    public void createPlayer(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(vista.getWorldWidth()/2,80);
        bdef.type = BodyDef.BodyType.DynamicBody;
        player = world.createBody(bdef);
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(40,40);
        fdef.shape = shape;
        player.createFixture(fdef);
    }*/


    @Override
    public void dispose() {

    }

    public void update(float dt){
        handleInput();
        //world.step(1/60f,6,2);
        //camara.position.set(vista.getWorldWidth()/2,vista.getWorldHeight()/2,0);

        //camara.update();

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