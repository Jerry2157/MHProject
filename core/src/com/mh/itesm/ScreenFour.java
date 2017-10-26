package com.mh.itesm;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Timer;

/**
 * Created by jerry2157 on 10/09/17.
 */

public class ScreenFour extends Pantalla {
    private int tamMundoWidth = 3840;
    private boolean played = false;
    private static Fondo fondo; //Imagen de fondo

    //Steven
    private PlayerSteven Steven;


    private int TamEscena = 0;
    private MHMain juego;

    World world;
    private Box2DDebugRenderer b2dr;
    Body player;
    Controller controller;
    private Texture BackgroundLayerOne;   // Imagen que se muestra

    // Contenedor de los botones
    private Stage escenaMenu;
    private Texture texturaBtnPintura;


    public ScreenFour(MHMain juego) {
        //Crear a Steven
        Steven = new PlayerSteven(10,64,tamMundoWidth);
        Steven.setEstadoMovimiento(PlayerSteven.EstadoMovimiento.MOV_DERECHA);

        Gdx.input.setInputProcessor(escenaMenu);
        this.juego = juego;
        //world = new World(new Vector2(0,-9.81f),true);
        //manipular objeto world para manipular o cambiar con lo que hemos estado usando
        b2dr = new Box2DDebugRenderer();

        //createGround();
        //createPlayer();
        controller = new Controller();
    }

    public void handleInput(){
        if(controller.isRightPressed()) {
            //player.setLinearVelocity(new Vector2(100, player.getLinearVelocity().y));
            Steven.setEstadoMovimiento(PlayerSteven.EstadoMovimiento.MOV_DERECHA);
        }
        else if (controller.isLeftPressed()) {
            //player.setLinearVelocity(new Vector2(-100, player.getLinearVelocity().y));
            Steven.setEstadoMovimiento(PlayerSteven.EstadoMovimiento.MOV_IZQUIERDA);
        }
        else {
            //player.setLinearVelocity(new Vector2(0, player.getLinearVelocity().y));
            Steven.setEstadoMovimiento(PlayerSteven.EstadoMovimiento.QUIETO);
        }
        if (controller.isUpPressed()) {
            //player.applyLinearImpulse(new Vector2(0, 20f), player.getWorldCenter(), true);
            Steven.setEstadoMovimiento(PlayerSteven.EstadoMovimiento.QUIETO);
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

    }

    @Override
    public void render(float delta) {
        Steven.actualizar();
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
        //bdef.position.set(vista.getWorldWidth()/2,0);
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
    public void HandleInput(){

    }
    public void update(){
        handleInput();
        //world.step(1/60f,6,2);
        //camara.position.set(vista.getWorldWidth()/2,vista.getWorldHeight()/2,0);

        //camara.update();

        //camara.update();

    }
    public void cambiarEscena(){
        if(1100 == Steven.getX() &&  64 <= Steven.getY()){ //258  y 512 es la posicion del templo, lo identifique con el system.out.println
            // Para verificar si el usuario ya tomo los 3 pergaminos y liberar el boton de galeria de arte...
            /*liberarArte();
            this.efectoPuertaTemplo.play(PantallaMenu.volumen);
            PantallaCargando.partidaGuardada.putBoolean("nivelAgua", true); //se guarda el progreso y se desbloquea el nivel de agua...
            PantallaCargando.partidaGuardada.flush(); //se guardan los cambios*/
            juego.setScreen(new ScreenOne(juego));//Debug

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
            juego.setScreen(new ScreenFour(juego));
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
}
