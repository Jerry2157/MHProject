package com.mh.itesm;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
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

/**
 * Created by jerry2157 on 10/09/17.
 */

public class ScreenOne extends Pantalla {

    private MHMain juego;

    World world;
    private Box2DDebugRenderer b2dr;
    Body player;
    Controller controller;
    private Texture BackgroundLayerOne;   // Imagen que se muestra
    //Peronajes no tan interactuables
    private Texture esposaParada;
    private Texture hijaSentada;
    private Texture lienzo;

    //Estado del juego
    private EstadoJuego estadoJuego;

    //Elementos para pausa
    private Boton btnPausa;
    private Fondo fondoPausa;

    //Texto para poner en pantalla
    private Texto texto;
    //Pinturas interactuables
    //Imagen(Pintura) interactuable
    private Texture paint1,paint2, paint3, paint4, paint5, paint6, paint7, paint8, paint9, paint10, paint11, paint12, paint13, paint14, paint15, paint16;
    private Texture[] pinturas;
    //Variable nImage lleva el conteo de cuantos clicks en la pantalla se han hecho
    private int nImage;
    // Contenedor de los botones
    private Stage escenaMenu;
    private Texture texturaBtnPintura;
    //Variable para llevar un cronometro para manejar texturas
    private float tiempo;

    //Transparencia por int para ir reduciendo
    private float textoTransparencia;
    private Texture texturaFondoPausa,texturaPausa;

    //Objeto animation para parpadeo y mov.


    public ScreenOne(MHMain juego) {
        Gdx.input.setInputProcessor(escenaMenu);
        this.juego = juego;
        world = new World(new Vector2(0,-9.81f),true);
        //manipular objeto world para manipular o cambiar con lo que hemos estado usando
        b2dr = new Box2DDebugRenderer();

        //Inicializamos variables
        pinturas=new Texture[16];
        //Contador para que pintura se colocara
        nImage=0;

        createGround();
        createPlayer();
        controller = new Controller();
        texto=new Texto();
        estadoJuego=EstadoJuego.JUGANDO;


    }

    public void handleInput(){
        if(controller.isRightPressed())
            //estado=EstadoJuego.JUGANDO;
            player.setLinearVelocity(new Vector2(100, player.getLinearVelocity().y));
            //estado=EstadoJuego.JUGANDO;
        else if (controller.isLeftPressed())
            //estado=EstadoJuego.JUGANDO;
            player.setLinearVelocity(new Vector2(-100, player.getLinearVelocity().y));
        else
            //estado=EstadoJuego.JUGANDO;
            player.setLinearVelocity(new Vector2(0, player.getLinearVelocity().y));

        if (controller.isUpPressed() && player.getLinearVelocity().y == 0)
            player.applyLinearImpulse(new Vector2(0, 20f), player.getWorldCenter(), true);
    }

    @Override
    public void show() {
        cargarTexturas();
        //manejoElementos();
        tiempo=0;
        textoTransparencia=1;
        Gdx.input.setInputProcessor(new ProcesadorEntrada());

    }

    private void manejoElementos() {
        AssetManager assetManager = juego.getAssetManager();   // Referencia al assetManager
        //Botones
        texturaPausa=assetManager.get("Botones/pausa.png");
        btnPausa=new Boton(texturaPausa);
        btnPausa.setAlfa(0.7f);
        btnPausa.setPosicion(80,80);
        btnPausa.setTamanio(btnPausa.getAncho() + 10, btnPausa.getAlto() + 10);
        //fondoPausa
        texturaFondoPausa = assetManager.get("Background_loading.png");

        //menu pausa
        fondoPausa =  new Fondo(texturaFondoPausa);

        //Texturas de pausa que se utilizan en todos los niveles.
        //texturaFondoPausa = assetManager.get("seleccionNivel/recursosPausa/fondoPausa.png");
        //texturaContinue = assetManager.get("seleccionNivel/recursosPerdiste/continue.png");
        //texturaMenu = assetManager.get("seleccionNivel/recursosPausa/menu.png");
    }

    //es importante que se indique que parte debe tocar e ir pintando restringuiendo que parte toco, si es posible


    private void cargarTexturas() {
        BackgroundLayerOne = new Texture("ScreenOne/Fondo.png");
        //Textura personajes estaticos
        esposaParada=new Texture("Characters/EsposaNormal.png");
        hijaSentada=new Texture("Characters/HijaNORMAL.png");
        lienzo=new Texture("Lienzo.png");

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
        update(Gdx.graphics.getDeltaTime());
        borrarPantalla(0.8f,0.45f,0.2f);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(BackgroundLayerOne, Pantalla.ANCHO/2 -BackgroundLayerOne.getWidth()/2,Pantalla.ALTO/2-BackgroundLayerOne.getHeight()/2);
        //dibujando personajes y elementosIMPLEMENTAR PARPADEO
        //batch.draw(esposaParada,0,0);
        if(estadoJuego==EstadoJuego.PAUSADO){
            btnPausa.render(batch);
        }

        batch.draw(lienzo,600,170);
        batch.draw(esposaParada,720,200);
        batch.draw(hijaSentada,750,200);
        //dibujar imagen pintura, al clickear el metodo recibira una imagen dependiendo de la que mande
        //boton
        //AGREGAR QUE SI COLISIONO APAREZCA EL LIENZO
        //Para dejar de pintar debe presionar tal boton que agregaremos en la condicion
        if(nImage>0 && nImage<16){
            batch.draw(pinturas[nImage-1],50,100);
        }

        //Mostrando texto al inicio del nivel
        //buscar alfa para ir desapareciendo el texto

        if(tiempo<=2) {

            texto.mostrarMensaje(batch, "Primer Nivel \n Una tarde agradable en el parque", ANCHO / 2, ALTO / 2);
            texto.setColor(0,0,0,1);
        }

        //batch.draw(puzzlePintura(),50,100);
        batch.end();
        tiempo +=Gdx.graphics.getDeltaTime();
        textoTransparencia -=0.5;
        b2dr.render(world,camara.combined);
        //batch.setProjectionMatrix(camara.combined);
        if(Gdx.app.getType() == Application.ApplicationType.Android)
        controller.draw();


    }

    class ProcesadorEntrada implements InputProcessor {

        @Override
        public boolean keyDown(int keycode) {
            return false;
        }

        @Override
        public boolean keyUp(int keycode) {
            return false;
        }

        @Override
        public boolean keyTyped(char character) {
            return false;
        }

        @Override
        //coordenadas pintura con testeo podemos saber exactamente donde toca
        public boolean touchDown(int screenX, int screenY, int pointer, int button) {
            if(screenX>=Pantalla.ANCHO/2 && screenY>=Pantalla.ALTO/2){
                System.out.println("TOCO en X: "+screenX+" y: "+screenY);
                nImage++;
                return  true;
            }
            return false;

        }

        @Override
        public boolean touchUp(int screenX, int screenY, int pointer, int button) {
            return false;
        }

        @Override
        public boolean touchDragged(int screenX, int screenY, int pointer) {
            return false;
        }

        @Override
        public boolean mouseMoved(int screenX, int screenY) {
            return false;
        }

        @Override
        public boolean scrolled(int amount) {
            return false;
        }
    }



    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }
    public void createGround(){
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
        bdef.position.set(vista.getWorldWidth()/4,60);
        bdef.type = BodyDef.BodyType.DynamicBody;
        player = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(40,40);

        fdef.shape = shape;
        player.createFixture(fdef);


    }


    @Override
    public void dispose() {

    }
    public void HandleInput(){

    }
    public void update(float dt){
        handleInput();
        world.step(1/60f,6,2);
        //camara.position.set(vista.getWorldWidth()/2,vista.getWorldHeight()/2,0);

        //camara.update();

        camara.update();

    }
}
