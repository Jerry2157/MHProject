package com.mh.itesm;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.particles.influencers.ColorInfluencer;
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
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.sun.corba.se.impl.oa.poa.POAPolicyMediator;

import java.util.Random;

/**
 * Autor Principal: Jesús Heriberto Vásquez Sánchez A01377358
 */

public class ScreenOne extends Pantalla {

    private MHMain juego;
    private final AssetManager manager; //para manegar texturas y demas con manager

    World world;
    private Box2DDebugRenderer b2dr;
    Controller controller;
    private Texture BackgroundLayerOne;   // Imagen fondo
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
    private Texture paint1, paint2, paint3, paint4, paint5, paint6, paint7, paint8, paint9, paint10, paint11, paint12, paint13, paint14, paint15, paint16,paint17;
    private Texture[] pinturas;
    private Texture texturaBtnTocaA;
    //Texturas dialogos
    private Texture line1,line2,line3,line4,line5,line6,line7,line8,line9;
    private Texture[] dialogos;
    private int nDialog;

    //Variable nImage lleva el conteo de cuantos clicks en la pantalla se han hecho
    private int nImage;
    // Contenedor de los botones
    private Stage escenaMenu;

    //private Texture texturaBtnPintura;
    //Variable para llevar un cronometro para manejar texturas

    private float tiempo;
    private float tiempoParpadeo;
    private final float TIEMPO_PASO = 1.0f; //la usaremos para el parpadeo
    // Procesador de eventos pinturas
    private final ProcesadorEntrada procesadorEntrada = new ProcesadorEntrada();

    //Para el fondo de pausa
    private Texture texturaCuadro;
    //Steven
    private PlayerSteven Steven;
    private int tamMundoWidth = 3840;
    //Puzzle pintura
    private Objeto btnTocaAqui;
    private boolean condicionTerminoPintura=false;
    private boolean condicionTocaAqui=false;
    //condicion que permite que solo una vez le demos el control a controller cuando hablamos de el puzzle pintura
    private boolean condicionUnaVez=true;
    //Coordenadas para boton toca aquí
    private int cordeX;
    private int cordeY;
    //estado para saber si toco boton toca aqui
    private boolean tocoBtn;

    private Fondo fondo;
    //condicion para empezar con los dialogos
    private boolean pressEspaciadora=false;
    //condicion texto barra espaciadora
    private boolean condicionTemp=true;
    //variable que limita la pintura dialogo
    private int delayDialog;
    //boleano para empezar a pintar
    private boolean beginPaint=false;
    //booleano para dejar de dibujar text lienzo
    private boolean quitarTextLien=false;
    //textura que suplantara al object
    private Texture lienzoPuzzle;
    //variable que inicia el conteo
    private boolean tiempoCondicionPuzzle;
    private float tiempoPuzzle;

    private boolean played; //bandera se cambio de nivel

    public ScreenOne(MHMain juego) {
        played = false;
        Steven = new PlayerSteven(10,64,tamMundoWidth);
        Steven.setEstadoMovimiento(PlayerSteven.EstadoMovimiento.INICIANDO);

        this.juego = juego;
        world = new World(new Vector2(0, -9.81f), true);
        //manipular objeto world para manipular o cambiar con lo que hemos estado usando
        b2dr = new Box2DDebugRenderer();
        //Inicializamos variables
        pinturas = new Texture[16];
        dialogos=new Texture[9];
        //Contador para que pintura se colocara
        nImage = 0;
        nDialog=-1;
        cordeX=0;
        cordeY=0;
        delayDialog=0;

        manager = juego.getAssetManager(); //manager
        controller = new Controller();
        texto = new Texto();

        float delay = 4000.0f; // seconds

        Timer.schedule(new Timer.Task(){
            @Override
            public void run() {
                // Do your work
                //LLAMA AL DIALOGO BETO
                //
                test();
            }
        }, delay);

    }
    public void test(){
        juego.setScreen(new ScreenTwo(juego));
    }

    //Metodo para mover a steve
    public void handleInput() {
        if (controller.isRightPressed()) {
            Steven.setEstadoMovimiento(PlayerSteven.EstadoMovimiento.MOV_DERECHA);
        }
        else if (controller.isLeftPressed()) {
            Steven.setEstadoMovimiento(PlayerSteven.EstadoMovimiento.MOV_IZQUIERDA);
        }
        //AGREGAR TIEMPO, Y MENSAJE DE FELICIDADES
        else if((controller.isSpacePressed() || controller.isButtonPressed())&& Steven.getX()>=900){
            //parte del codigo que con booleando para dibujar dialogo.
            pressEspaciadora=true;
        }
        //si se pasa de la izquierda
        else {
            Steven.setEstadoMovimiento(PlayerSteven.EstadoMovimiento.QUIETO);
        }
        if (controller.isUpPressed()) {
            Steven.setEstadoMovimiento(PlayerSteven.EstadoMovimiento.QUIETO);
        }
        if(condicionTocaAqui==true ){
            Steven.setEstadoMovimiento(PlayerSteven.EstadoMovimiento.QUIETO);
        }
        //evitar que se salga del nivel.
        if(Steven.getX()>=1150){
            Steven.setEstadoMovimiento(PlayerSteven.EstadoMovimiento.MOV_IZQUIERDA);
        }
        //Debemos limitar que no se mueva por si solo ya que se mueve por si solo perdemos el control al regresar

    }

    public void pausaInput(){
        if(controller.isPausePressed()){
            // Se pausa el juego
            estadoJuego = estadoJuego==EstadoJuego.PAUSADO?EstadoJuego.JUGANDO:EstadoJuego.PAUSADO;
        }
        if (estadoJuego==EstadoJuego.PAUSADO ) {
            // Activar escenaPausa y pasarle el control
            if (escenaPausa==null) {
                escenaPausa = new EscenaPausa(vista, batch);
            }
            Gdx.input.setInputProcessor(escenaPausa);
            //Evita que cree la escena varias veces
            controller.pausePressed=false;
        }

    }

    @Override
    public void show() {
        generarNumRandom();
        cargarTexturas();
        crearObjetos();
        // Definir quién atiende los eventos de touch * CORROBORAR CARGANDO EN CELULAR
        //Proceso entrada para mover a steven y pausa
        Gdx.input.setInputProcessor(controller.getStage());
        tiempo = 0;
        tiempoParpadeo=0;


    }

    private void generarNumRandom() {
        //manejo numeros random
        Random tempRand1 = new Random();
        Random tempRand2=new Random();
        int i=0;
        int k=0;

        Random randomCordeX = new Random();
        Random randomCordeY=new Random();
        while(i<200 && k<200) {
            i = tempRand1.nextInt(1000);
            k = tempRand2.nextInt(600);
            if (i > 200) {
                cordeX = i;
                if (k > 200) {
                    cordeY = k;
                    break;
                }
            }

        }
    }


    private void crearObjetos() {
        // Botón pausa CMBIAR TEXTURA MOVIENDO PANTALLA CARGA Y CARGANDOLE EN CARGAR TEXTURAS
        btnPausa =new Objeto(texturaBtnPausa,(ANCHO-3*texturaBtnPausa.getWidth()/2)+35,ALTO-texturaBtnPausa.getHeight()-10);
        btnTocaAqui=new Objeto(texturaBtnTocaA,cordeX,cordeY);
        lienzo=new Objeto(texturaLienzo,450,60 );
    }
    //es importante que se indique que parte debe tocar e ir pintando restringuiendo que parte toco, si es posible

    private void cargarTexturas() {
        BackgroundLayerOne = new Texture("ScreenOne/fondo.png");
        fondo = new Fondo(BackgroundLayerOne);
        fondo.setPosicion(0,0);
        //Textura personajes estaticos
        esposaParada = new Texture("Characters/EsposaNORMAL.png");
        esposaParadaPar=new Texture("Characters/EsposaParpadeo.png");
        hijaSentada = new Texture("Characters/HijaNORMAL.png");
        lienzoPuzzle=new Texture("Lienzo.png");
        hijaSentadaPar=new Texture("Characters/HijaParpadeo.png");
        texturaLienzo = manager.get("Lienzo.png");
        //pausa con el manager ponerlo en otro metodo si uso manager???
        texturaBtnPausa = manager.get("comun/btnPausa.png");
        //texturaBtnPausa= manager.get("Botones/pausa.png");
        //para el texture toca aquí
        texturaBtnTocaA=manager.get("ScreenOne/fondotest.png");

        //Texturas dialogo

        line1=new Texture("Dialogos/Nivel1/Esposa/line1.png");
        dialogos[0]=line1;
        line2=new Texture("Dialogos/Nivel1/Hija/line2.png");
        dialogos[1]=line2;
        line3=new Texture("Dialogos/Nivel1/Esposa/line3.png");
        dialogos[2]=line3;
        line4=new Texture("Dialogos/Nivel1/Hija/line4.png");
        dialogos[3]=line4;
        line5=new Texture("Dialogos/Nivel1/Esposa/line5.png");
        dialogos[4]=line5;
        line6=new Texture("Dialogos/Nivel1/Esposa/line6.png");
        dialogos[5]=line6;
        line7=new Texture("Dialogos/Nivel1/Hija/line7.png");
        dialogos[6]=line7;
        line8=new Texture("Dialogos/Nivel1/Hija/line8.png");
        dialogos[7]=line8;
        line9=new Texture("Dialogos/Nivel1/Esposa/line9.png");
        dialogos[8]=line9;



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

        paint17=new Texture("Puzzle1/Pfinal.png");
    }

    @Override
    public void render(float delta) {
        Steven.actualizar();
        update(Gdx.graphics.getDeltaTime());
        borrarPantalla(0.8f, 0.45f, 0.2f);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        //batch.draw(BackgroundLayerOne, Pantalla.ANCHO / 2 - BackgroundLayerOne.getWidth() / 2, Pantalla.ALTO / 2 - BackgroundLayerOne.getHeight() / 2);
        fondo.render(batch);
        fondo.setPosicion(0,0);

        //dibujando personajes y elementosIMPLEMENTAR PARPADEO
        batch.draw(esposaParada, 750, 60);
        batch.draw(hijaSentada, 957, 110);
        //Manejo del parpadeo esposa E HIJA PROVISIONAL
        if (tiempoParpadeo>=TIEMPO_PASO) {
            tiempoParpadeo = 0;
            batch.draw(esposaParadaPar,750,60);
            batch.draw(hijaSentadaPar, 957, 110);
        }
        //AGREGAR QUE SI COLISIONO APAREZCA EL LIENZO
        //draw de las pintruas
        if (nImage > 0 && nImage < 16 ) {
            tiempoCondicionPuzzle=true;

            //mostrampos tiempo hecho
            texto.mostrarMensaje(batch, "Tiempo: "+(tiempoPuzzle), (ANCHO / 4)+10, (ALTO / 1)-5);
            texto.setColor(0, 0, 0, 1);

            //lo sacamos de la pantalla eliminar
            lienzo.setPosition(1280,800);
            batch.draw(lienzoPuzzle,450,60);
            batch.draw(pinturas[nImage - 1], 260, 180);
            if(tocoBtn==true){
                btnTocaAqui.setPosition(cordeX,cordeY);
                tocoBtn=false;
            }
        }
        //Comprueba si ternimo la pintura para mandar el true PENDIENTE
        if(nImage==16){
            condicionTerminoPintura=true;
            condicionTocaAqui=false;
            tiempoCondicionPuzzle=false;
            float tempTiempo=tiempoPuzzle;
            //mostrar pintura terminada tamanio mas grande y mensaje
            texto.mostrarMensaje(batch, "FELICIDADES, TERMINASTE EL PRIMER NIVEL \n Tu tiempo fue: "+tempTiempo, (ANCHO / 2), (ALTO/2));
            texto.setColor(0, 0, 0, 1);
            //Se espera un segundo
            float delay = 4; // seconds
            if(played == false) {
                played = true;
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        // Do your work
                        //played = true;
                        juego.setScreen(new ScreenTwo(juego));
                    }
                }, delay);
            }
            //SI QUEREMOS MOSTRAR EL RETRATO FINAL
            //batch.draw(paint17, ANCHO/2, ALTO/2);
            //CARGA NUEVA ESCENA FIJARNOS EN EL DELAY



        }
        if(beginPaint==true && condicionTerminoPintura==false){
            //activar mini uzzle pintura
            condicionTocaAqui=true;
        }

        //Mostrando texto al inicio del nivel
        if (tiempo <= 2) {
            texto.mostrarMensaje(batch, "Primer Nivel \n Una tarde agradable en el parque", (ANCHO / 4)+10, (ALTO / 1)-5);
            texto.setColor(0, 0, 0, 1);
        }
        //Set de intrucciones
        if(tiempo>2 && tiempo<=7){
            texto.mostrarMensaje(batch, "Camina hasta tu hija", (ANCHO / 4)-110, (ALTO / 1)-5);
            texto.setColor(1, 1, 1, 1);
        }
        //Agregar booleano para que desaparezca este texto
        if(nDialog>=9 && quitarTextLien==false){
            texto.mostrarMensaje(batch, "Ahora toca el lienzo", (ANCHO / 4)-150, (ALTO / 1)-5);
            texto.setColor(1, 1, 1, 1);
            //le damos el control al input de procesador entrada
            Gdx.input.setInputProcessor(procesadorEntrada);
        }
        //Desplegar controlador de dialogos al llegar a la coordenada de su esposa
        if(Steven.getX()>=900 && condicionTemp==true){
            texto.mostrarMensaje(batch, "Pulsa el boton para el dialogo", (ANCHO / 4)+80, (ALTO / 1)-5);
            texto.setColor(1, 1, 1, 1);
        }

        //Manejo de tiempo para los dialogos
        if ( delayDialog%160==0 && Steven.getX()>=900  && pressEspaciadora==true) {
            tiempoParpadeo = 0;
            nDialog++;

        }

        //empezamos con los dialogos
        if( Steven.getX()>=900  && pressEspaciadora==true){
            //No dejar que steven se mueva?
            if(estadoJuego==EstadoJuego.JUGANDO) {
                if (nDialog == 0 || nDialog == 2 || nDialog == 4 /*|| nDialog == 5 || nDialog == 8*/) {
                    condicionTemp = false;
                    batch.draw(dialogos[nDialog], 620, 275);
                }
                if (nDialog == 1 || nDialog == 3 || nDialog == 6 /*|| nDialog == 7*/) {
                    batch.draw(dialogos[nDialog], 820, 255);
                }
                if (nDialog == 9) {
                    pressEspaciadora = false;
                }
            }

        }

        Steven.dibujar(batch);

        dibujarElementos(batch);
        batch.end();
        manejoInputPintura();
        //Crear int que se actualize y ponder por modulo condicion para dialogo
        delayDialog ++;
        tiempo += Gdx.graphics.getDeltaTime();
        //Tiempo parpadeo
        tiempoParpadeo +=Gdx.graphics.getDeltaTime();
        //Timepo puzzle
        if(tiempoCondicionPuzzle){
            tiempoPuzzle += Gdx.graphics.getDeltaTime();
        }


        if (estadoJuego == EstadoJuego.PAUSADO && escenaPausa!=null ) {
            escenaPausa.draw();
        }

        b2dr.render(world, camara.combined);
        //batch.setProjectionMatrix(camara.combined);
        //if (Gdx.app.getType() == Application.ApplicationType.Android) //condicion manejada en controller
        controller.draw();


    }
    //tambien corrobora si debe pasar el input procesor a el puzzle o no
    private void dibujarElementos(SpriteBatch batch) {
        lienzo.dibujar(batch);
        if(condicionTocaAqui==true && condicionTerminoPintura==false ){
            btnTocaAqui.dibujar(batch);
            //corroborar el manejo de la escena pausa
        }
    }



    private void manejoInputPintura(){
        if(condicionTocaAqui){
            Gdx.input.setInputProcessor(procesadorEntrada);
        }
        if(condicionTocaAqui==false && condicionUnaVez==true){
            //Steven.setEstadoMovimiento(PlayerSteven.EstadoMovimiento.QUIETO);
            Steven.setEstadoMovimiento(PlayerSteven.EstadoMovimiento.INICIANDO);
            //guardamos un temp
            //y volvemos a crear
            //Se pierde la referencia guardar estado pasado y crear uno nuevo, esto debido al cambio de inputs
            //System.out.println("Llegamos aquí: "+Steven.getEstadoMovimiento());
            //Si le regresas el control se cicla y se mueve solo
            //Gdx.input.setInputProcessor(controller.getStage());

            condicionUnaVez=false;
        }
        //PORQUE NO FUNCIONA?
        /*if(condicionTerminoPintura){
            Gdx.input.setInputProcessor(controller.getStage());
        }*/
    }

    //Procesador entrada para minujuego pinturas
    class ProcesadorEntrada implements InputProcessor {
        //Objeto tipo vector para hacer match con objetos por coordenadas
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
        public boolean touchDown(int screenX, int screenY, int pointer, int button) {
            v.set(screenX, screenY, 0);
            camara.unproject(v);
            //DEBUGEO SI TODCA LA PINTURA PASA EL CONTROL AL OTRO ASSET
            //chcear porque no me permite mas de una pintura y si va a ser de pantalla completa
            if(lienzo.contiene(v)){
                beginPaint=true;
                quitarTextLien=true;
            }
            if(btnTocaAqui.contiene(v)){
                tocoBtn=true;
                estadoPintura=true;

                //Gdx.input.setInputProcessor(procesadorEntrada);
            }
            if(estadoPintura==true) {
                //System.out.println("TOCO en X: " + screenX + " y: " + screenY);
                nImage++;
            }
            estadoPintura=false;
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

    public EstadoJuego getEstadoJuego(){
       return estadoJuego;
    }

    public void setEstadoJuego(EstadoJuego estado){
        estadoJuego=estado;
    }

    //aqui descargamos todo lo utilizado para ahorrar memoria

    @Override
    public void dispose() {

    }

    public void update(float dt) {
        handleInput();
        pausaInput();
        generarNumRandom();


        world.step(1 / 60f, 6, 2);
        //camara.position.set(vista.getWorldWidth()/2,vista.getWorldHeight()/2,0);
        batch.setProjectionMatrix(camara.combined);
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
                    Gdx.input.setInputProcessor(controller.getStage()); // No debería crear uno nuevo
                }
            });
            this.addActor(btnReintentar);

        }
    }

    public Pantalla getScreenOne(){
        return this;
    }
    public Controller getController(){
        return controller;
    }
}






