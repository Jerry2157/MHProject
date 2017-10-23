package com.mh.itesm;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.sun.corba.se.impl.oa.poa.POAPolicyMediator;

/**
 * Created by jerry2157 on 10/09/17.
 */

public class ScreenOne extends Pantalla {

    private MHMain juego;
    private final AssetManager manager; //para manegar texturas y demas con manager

    World world;
    private Box2DDebugRenderer b2dr;
    Body player;
    Controller controller;
    private Texture BackgroundLayerOne;   // Imagen que se muestra
    //Peronajes no tan interactuables
    private Texture esposaParada;
    private Texture esposaParadaPar;
    private Texture hijaSentada;
    private Texture hijaSentadaPar;
    private Objeto lienzo;
    private Texture texturaLienzo;

    //Estado del juego
    private EstadoJuego estadoJuego = EstadoJuego.JUGANDO;

    //Escala necesaria para llamar a steven
    private final float SCALE=2.0f;

    //Elementos para pausa
    private Texture texturaBtnPausa;
    private EscenaPausa escenaPausa;
    private Objeto btnPausa;

    //Boolean para pintura
    private boolean estadoPintura=false;

    //Texto para poner en pantalla
    private Texto texto;
    //Pinturas interactuables
    //Imagen(Pintura) interactuable
    private Texture paint1, paint2, paint3, paint4, paint5, paint6, paint7, paint8, paint9, paint10, paint11, paint12, paint13, paint14, paint15, paint16;
    private Texture[] pinturas;
    //Variable nImage lleva el conteo de cuantos clicks en la pantalla se han hecho
    private int nImage;
    // Contenedor de los botones
    private Stage escenaMenu;
    private Texture texturaBtnPintura;
    //Variable para llevar un cronometro para manejar texturas
    private float tiempo;
    private float tiempoParpadeo;
    private final float TIEMPO_PASO = 0.5f; //la usaremos para el parpadeo

    //Transparencia por int para ir reduciendo
    private float textoTransparencia;
    //Esta condicion le otorga el procesor a quien lo necesite.
    private boolean condicionProcesor=false;

    // Procesador de eventos
    // Procesador de eventos
    private final ProcesadorEntrada procesadorEntrada = new ProcesadorEntrada();
    //Para el fondo de pausa
    private Texture texturaCuadro;
    private Texture stevenParado;


    public ScreenOne(MHMain juego) {
        //Talvez incesesario
        //Gdx.input.setInputProcessor(escenaMenu);
        this.juego = juego;
        world = new World(new Vector2(0, -9.81f), true);
        //manipular objeto world para manipular o cambiar con lo que hemos estado usando
        b2dr = new Box2DDebugRenderer();

        //Inicializamos variables
        pinturas = new Texture[16];
        //Contador para que pintura se colocara
        nImage = 0;

        manager = juego.getAssetManager(); //manager
        createGround();
        createPlayer();
        controller = new Controller();
        texto = new Texto();


    }

    public void handleInput() {
        if (controller.isRightPressed())
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

        //Si no esta siendo utilizado pasa el control el boton pausa y el lienzo
        //condicionProcesor=false;
    }

    @Override
    public void show() {
        cargarTexturas();
        crearObjetos();

        // Definir quién atiende los eventos de touch
        Gdx.input.setInputProcessor(procesadorEntrada);
        //Proceso entrada para mover a steven
        condicionProcesor=false;
        if(condicionProcesor==true){
            Gdx.input.setInputProcessor(controller.getStage());
        }
        tiempo = 0;
        tiempoParpadeo=0;
        //textoTransparencia=1;
        //Gdx.input.setInputProcessor(new ProcesadorEntrada());

    }

    private void crearObjetos() {
        // Botón pausa
        // ACOMODARLO MEJOR :)
        btnPausa =new Objeto(texturaBtnPausa,(ANCHO-3*texturaBtnPausa.getWidth()/2)+35,ALTO-texturaBtnPausa.getHeight()-10);
        lienzo=new Objeto(texturaLienzo,450,60 );
    }



    //es importante que se indique que parte debe tocar e ir pintando restringuiendo que parte toco, si es posible


    private void cargarTexturas() {
        BackgroundLayerOne = new Texture("ScreenOne/Fondo.png");
        //Textura personajes estaticos
        esposaParada = new Texture("Characters/EsposaNORMAL.png");
        esposaParadaPar=new Texture("Characters/EsposaParpadeo.png");
        hijaSentada = new Texture("Characters/HijaNORMAL.png");
        hijaSentadaPar=new Texture("Characters/HijaParpadeo.png");
        texturaLienzo = manager.get("Lienzo.png");
        //pausa con el manager ponerlo en otro metodo si uso manager???
        texturaBtnPausa = manager.get("comun/btnPausa.png");
        //Steven
        stevenParado=new Texture("Characters/StevenPie.png");

        //Imagenes de la pinturas
        paint1 = new Texture("Puzzle1/P1.png");
        pinturas[0] = paint1;
        paint2 = new Texture("Puzzle1/P2.png");
        pinturas[1] = paint2;
        paint3 = new Texture("Puzzle1/P3.png");
        pinturas[2] = paint3;
        paint4 = new Texture("Puzzle1/P4.png");
        pinturas[3] = paint4;
        paint5 = new Texture("Puzzle1/P5.png");
        pinturas[4] = paint5;
        paint6 = new Texture("Puzzle1/P6.png");
        pinturas[5] = paint6;
        paint7 = new Texture("Puzzle1/P7.png");
        pinturas[6] = paint7;
        paint8 = new Texture("Puzzle1/P8.png");
        pinturas[7] = paint8;
        paint9 = new Texture("Puzzle1/P9.png");
        pinturas[8] = paint9;
        paint10 = new Texture("Puzzle1/P10.png");
        pinturas[9] = paint10;
        paint11 = new Texture("Puzzle1/P11.png");
        pinturas[10] = paint11;
        paint12 = new Texture("Puzzle1/P12.png");
        pinturas[11] = paint12;
        paint13 = new Texture("Puzzle1/P13.png");
        pinturas[12] = paint13;
        paint14 = new Texture("Puzzle1/P14.png");
        pinturas[13] = paint14;
        paint15 = new Texture("Puzzle1/P15.png");
        pinturas[14] = paint15;
        paint16 = new Texture("Puzzle1/P16.png");
        pinturas[15] = paint16;

    }

    @Override
    public void render(float delta) {
        update(Gdx.graphics.getDeltaTime());
        borrarPantalla(0.8f, 0.45f, 0.2f);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(BackgroundLayerOne, Pantalla.ANCHO / 2 - BackgroundLayerOne.getWidth() / 2, Pantalla.ALTO / 2 - BackgroundLayerOne.getHeight() / 2);
        //dibujando personajes y elementosIMPLEMENTAR PARPADEO

        batch.draw(esposaParada, 750, 60);
        batch.draw(hijaSentada, 957, 110);
        batch.draw(stevenParado,player.getPosition().x,player.getPosition().y);
        //Manejo del parpadeo esposa
        if (tiempoParpadeo>=TIEMPO_PASO) {
            tiempoParpadeo = 0;
            batch.draw(esposaParadaPar,750,60);
            batch.draw(hijaSentadaPar, 957, 110);
        }


        //dibujar imagen pintura, al clickear el metodo recibira una imagen dependiendo de la que mande
        //boton
        //AGREGAR QUE SI COLISIONO APAREZCA EL LIENZO
        //Para dejar de pintar debe presionar tal boton que agregaremos en la condicion
        if (nImage > 0 && nImage < 16 ) {
            batch.draw(pinturas[nImage - 1], 50, 100);
        }

        //Mostrando texto al inicio del nivel
        //buscar alfa para ir desapareciendo el texto

        if (tiempo <= 2) {
            texto.mostrarMensaje(batch, "Primer Nivel \n Una tarde agradable en el parque", (ANCHO / 4)+10, (ALTO / 1)-5);
            texto.setColor(0, 0, 0, 1);
            //manejo de alfa
        }

        //batch.draw(puzzlePintura(),50,100);
        dibujarEstado(batch);
        batch.end();
        tiempo += Gdx.graphics.getDeltaTime();
        //Tiempo parpadeo
        tiempoParpadeo +=Gdx.graphics.getDeltaTime();
        if (estadoJuego == EstadoJuego.PAUSADO) {
            escenaPausa.draw();
        }


        b2dr.render(world, camara.combined);
        //batch.setProjectionMatrix(camara.combined);
        if (Gdx.app.getType() == Application.ApplicationType.Android)
            controller.draw();


    }

    private void dibujarEstado(SpriteBatch batch) {
        //Cuadrado?? pausa
        //batch.draw(texturaCuadro,0,ALTO-texturaCuadro.getHeight());
        //dibujamos el boton pausa
        btnPausa.dibujar(batch);
        lienzo.dibujar(batch);
    }

    class ProcesadorEntrada implements InputProcessor {
        private Vector3 v = new Vector3();
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
            v.set(screenX, screenY, 0);
            camara.unproject(v);
            //DEBUGEO SI TODCA LA PINTURA PASA EL CONTROL AL OTRO ASSET
            //chcear porque no me permite mas de una pintura y si va a ser de pantalla completa
            if(lienzo.contiene(v)){
                estadoPintura=true;
                Gdx.input.setInputProcessor(procesadorEntrada);
            }

            if(estadoPintura==true) {
                System.out.println("TOCO en X: " + screenX + " y: " + screenY);
                nImage++;
            }
            estadoPintura=false;
            //Gdx.input.setInputProcessor(escenaPausa);

            // Prueba botón pausa
            if (btnPausa.contiene(v)) {
                // Se pausa el juego
                estadoJuego = estadoJuego==EstadoJuego.PAUSADO?EstadoJuego.JUGANDO:EstadoJuego.PAUSADO;
                if (estadoJuego==EstadoJuego.PAUSADO) {
                    // Activar escenaPausa y pasarle el control
                    if (escenaPausa==null) {
                        escenaPausa = new EscenaPausa(vista, batch);
                    }
                    Gdx.input.setInputProcessor(escenaPausa);
                }
            }
            return true;
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

    public void createGround() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(vista.getWorldWidth() / 2, 0);
        bdef.type = BodyDef.BodyType.StaticBody;
        Body b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(vista.getWorldWidth() / 2, 20);

        fdef.shape = shape;
        b2body.createFixture(fdef);
    }

    public void createPlayer() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(vista.getWorldWidth() / 4, 60);
        bdef.type = BodyDef.BodyType.DynamicBody;
        player = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(40, 40);

        fdef.shape = shape;
        player.createFixture(fdef);


    }

    //aqui descargamos todo lo utilizado para ahorrar memoria

    @Override
    public void dispose() {

    }

    public void HandleInput() {

    }

    public void update(float dt) {
        handleInput();
        world.step(1 / 60f, 6, 2);
        //camara.position.set(vista.getWorldWidth()/2,vista.getWorldHeight()/2,0);
        batch.setProjectionMatrix(camara.combined);
        //camara.update();

        camara.update();

    }


    private class EscenaPausa extends Stage {
        // La escena que se muestra cuando está pausado
        public EscenaPausa(Viewport vista, SpriteBatch batch) {
            super(vista, batch);
            // Crear rectángulo transparente
            Pixmap pixmap = new Pixmap((int) (ScreenOne.ANCHO * 0.7f), (int) (ScreenOne.ALTO * 0.8f), Pixmap.Format.RGBA8888);
            pixmap.setColor(1f, 1f, 1f, 0.65f);
            pixmap.fillTriangle(0,pixmap.getHeight(),pixmap.getWidth(),pixmap.getHeight(),pixmap.getWidth()/2,0);
            Texture texturaTriangulo = new Texture(pixmap);
            pixmap.dispose();
            Image imgTriangulo = new Image(texturaTriangulo);
            imgTriangulo.setPosition(0.15f * ANCHO, 0.1f * ALTO);
            this.addActor(imgTriangulo);

            // Salir
            Texture texturaBtnSalir = manager.get("whackamole/btnSalir.png");
            TextureRegionDrawable trdSalir = new TextureRegionDrawable(
                    new TextureRegion(texturaBtnSalir));
            ImageButton btnSalir = new ImageButton(trdSalir);
            btnSalir.setPosition(ANCHO/2-btnSalir.getWidth()/2, ALTO*0.2f);
            btnSalir.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    // Regresa al menú
                    juego.setScreen(new mainMenu(juego));
                }
            });
            this.addActor(btnSalir);

            // Continuar
            Texture texturaBtnReintentar = manager.get("whackamole/btnContinuar.png");
            TextureRegionDrawable trdReintentar = new TextureRegionDrawable(
                    new TextureRegion(texturaBtnReintentar));
            ImageButton btnReintentar = new ImageButton(trdReintentar);
            btnReintentar.setPosition(ANCHO/2-btnReintentar.getWidth()/2, ALTO*0.5f);
            btnReintentar.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    // Regresa al juego
                    estadoJuego = EstadoJuego.JUGANDO;
                    Gdx.input.setInputProcessor(procesadorEntrada); // No debería crear uno nuevo
                }
            });
            this.addActor(btnReintentar);
        }
    }

}






