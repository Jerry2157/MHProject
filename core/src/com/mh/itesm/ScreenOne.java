package com.mh.itesm;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
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

    private EstadoJuego estadoJuego = EstadoJuego.JUGANDO; //Estado del juego
    //Elementos para pausa
    private Texture texturaBtnPausa;
    private EscenaPausa escenaPausa;
    private Objeto btnPausa;

    private boolean estadoPintura=false; //Boolean para pintura

    private Texto texto; //Texto para poner en pantalla
    //Pinturas interactuables
    //Imagen(Pintura) interactuable
    private Texture paint1, paint2, paint3, paint4, paint5, paint6, paint7, paint8, paint9, paint10, paint11, paint12, paint13, paint14, paint15, paint16,paint17;
    private Texture[] pinturas;
    private Texture texturaBtnTocaA;

    private Texture line1,line2,line3,line4,line5,line6,line7,line8,line9; //Texturas dialogos
    private Texture[] dialogos;
    private int nDialog;

    private int nImage; //Variable nImage lleva el conteo de cuantos clicks en la pantalla se han hecho
    private Stage escenaMenu; // Contenedor de los botones

    private float tiempo; //Variable para llevar un cronometro para manejar texturas
    private float tiempoParpadeo;
    private final float TIEMPO_PASO = 1.0f; //la usaremos para el parpadeo

    private final ProcesadorEntrada procesadorEntrada = new ProcesadorEntrada(); // Procesador de eventos puzzle pinturas

    private Texture texturaCuadro; //Para el fondo de pausa *CHECAR*
    private PlayerSteven Steven; //Steven
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
    private boolean tocoBtn; //estado para saber si toco boton toca aqui

    private Fondo fondo;

    private boolean pressEspaciadora=false; //condicion para empezar con los dialogos
    private boolean condicionTemp=true; //condicion texto barra espaciadora
    private int delayDialog; //variable que limita la pintura dialogo
    private boolean beginPaint=false; //boleano para empezar a pintar
    private boolean quitarTextLien=false;  //booleano para dejar de dibujar text lienzo

    private Texture lienzoPuzzle; //textura que suplantara al object
    private boolean tiempoCondicionPuzzle;  //variable que inicia el conteo
    private float tiempoPuzzle;

    private boolean played; //bandera se cambio de nivel
    private Music sonidoF;
    private boolean startdialog=false;

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
    }


    //Metodo para mover a steve
    public void handleInput() {
        if (controller.isRightPressed()) {
            Steven.setEstadoMovimiento(PlayerSteven.EstadoMovimiento.MOV_DERECHA);
        }
        else if (controller.isLeftPressed()) {
            Steven.setEstadoMovimiento(PlayerSteven.EstadoMovimiento.MOV_IZQUIERDA);
        }
        else if((controller.isSpacePressed() || controller.isButtonPressed())&& Steven.getX()>=590){
            pressEspaciadora=true; //parte del codigo que con booleando para dibujar dialogo.
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
            estadoJuego = estadoJuego==EstadoJuego.PAUSADO?EstadoJuego.JUGANDO:EstadoJuego.PAUSADO; // Se pausa el juego
        }
        if (estadoJuego==EstadoJuego.PAUSADO ) {
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
        generarNumRandom();
        cargarTexturas();
        crearObjetos();
        //Proceso entrada para mover a steven y pausa
        Gdx.input.setInputProcessor(controller.getStage());
        //variables para manipular tiempo
        tiempo = 0;
        tiempoParpadeo=0;
    }

    private void generarNumRandom() {
        //manejo numeros random
        Random tempRand1 = new Random();
        Random tempRand2=new Random();
        int i=0;
        int k=0;
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
        btnTocaAqui=new Objeto(texturaBtnTocaA,cordeX,cordeY);
        lienzo=new Objeto(texturaLienzo,450,60 );
    }

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

        //Sonido
        sonidoF=Gdx.audio.newMusic(Gdx.files.internal("Sonidos/parque.mp3"));
        sonidoF.setVolume(0.7f);
        sonidoF.play();
        sonidoF.setLooping(true);

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
        //paintfinal implementada??? CHECAR
        paint17=new Texture("Puzzle1/Pfinal.png");
    }

    @Override
    public void render(float delta) {
        Steven.actualizar();
        update(Gdx.graphics.getDeltaTime());
        borrarPantalla(0.8f, 0.45f, 0.2f);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        fondo.render(batch);
        fondo.setPosicion(0,0);

        Steven.dibujar(batch);
        //dibujando personajes y elementos CORREGIR PARPADEO
        batch.draw(esposaParada, 730, 60);
        batch.draw(hijaSentada, 1015, 110);
        //Manejo del parpadeo esposa E HIJA PROVISIONAL
        /*if (tiempoParpadeo>=TIEMPO_PASO) {
            tiempoParpadeo = 0;
            batch.draw(esposaParadaPar,750,60);
            batch.draw(hijaSentadaPar, 957, 110);
        }*/
        //draw de las pintruas
        //AGREGAR ESTADOS SI NO TOCA PIERDE Y SE REINICIA
        //puzzle pinturas;
        juegoPuzzlePinturas();


        if(beginPaint==true && condicionTerminoPintura==false){
            condicionTocaAqui=true; //activar mini puzzle pintura
        }
        //TEXTOS DE LA PANTALLA, metodo que maneja textos de la pantalla que aparecen en la parte superior
        textosScreenOne();

        //Manejo de tiempo para los dialogos, usando el delay PAPPADEO CHECAR***
        tiempoDialogos();

        //CONDICIONAL QUE NOS INDICA SI STEVEN ALCANZO A SU ESPOSA PARA PONE LOS DIALOGOS
        if(Steven.getX()>=590){
            startdialog=true;
        }
        //manejo y impresion de dialogos
        sistemaDialogos();

        dibujarElementos(batch);

        batch.end();
        manejoInputPintura();
        delayDialog ++;
        tiempo += Gdx.graphics.getDeltaTime();
        tiempoParpadeo +=Gdx.graphics.getDeltaTime(); //Tiempo parpadeo
        //Timepo puzzle
        if(tiempoCondicionPuzzle){
            tiempoPuzzle += Gdx.graphics.getDeltaTime();
        }
        if (estadoJuego == EstadoJuego.PAUSADO && escenaPausa!=null ) {
            escenaPausa.draw(); //DIBUJAMOS escenaPausa si esta pausado
        }

        b2dr.render(world, camara.combined);
        //borrar el controller si llego a cierto punto solo borrar la cruzeta??
        if(beginPaint==false) {
            controller.draw();
        }
    }

    //metodo que va a manejar los textos con sus respectivos tiempos
    private void textosScreenOne() {
        //Mostrando texto al inicio del nivel
        if (tiempo <= 2) {
            texto.mostrarMensaje(batch, "Primer Nivel \n Una tarde agradable en el parque", (ANCHO / 4)+10, (ALTO / 1)-5);
            texto.setColor(0, 0, 0, 1);
        }
        //Set de intrucciones
        if(tiempo>2 && Steven.getX()<590 && startdialog==false){
            texto.mostrarMensaje(batch, "Camina hasta tu familia", (ANCHO / 4)-90, (ALTO / 1)-5);
            texto.setColor(0, 0, 0, 1);

        }
        //Muestra el texto para el puzzle de pintura
        if(nDialog>=9 && quitarTextLien==false){
            texto.mostrarMensaje(batch, "Ahora toca el lienzo", (ANCHO / 4)-120, (ALTO / 1)-5);
            texto.setColor(0, 0, 0, 1);
            Gdx.input.setInputProcessor(procesadorEntrada); //le damos el control al input de procesador entrada
            // YA NO PODEMOS REGRESARSELO AL PRINCIPAL PODRIAMOS GUARDAR LA RELACION CON UN TEMP
        }
        //Desplegar controlador de dialogos al llegar a la coordenada de su hija TENTATIVO A CAMBIAR
        if(startdialog==true && condicionTemp==true ){
            texto.mostrarMensaje(batch, "Pulsa el boton para el dialogo", (ANCHO / 4)-30, (ALTO / 1)-5);
            texto.setColor(0, 0, 0, 1);
        }
    }

    //metodo que dibuja y maneja el puzzle pinturas
    private void juegoPuzzlePinturas() {
        if (nImage > 0 && nImage < 16 ) {
            tiempoCondicionPuzzle=true;
            texto.mostrarMensaje(batch, "Tiempo: "+((int)tiempoPuzzle), (ANCHO / 4)-190, (ALTO / 1)-5); //mostrampos tiempo hecho
            texto.setColor(0, 0, 0, 1);
            lienzo.setPosition(1280,800); //lo sacamos de la pantalla eliminar
            batch.draw(lienzoPuzzle,450,60);
            batch.draw(pinturas[nImage - 1], 260, 180);
            if(tocoBtn==true){
                btnTocaAqui.setPosition(cordeX,cordeY);
                tocoBtn=false;
            }
        }
        //Comprueba si ternimo la pintura para mandar el true PENDIENTE, IMPORTANE QUE PUEDA PERDER SI NO TOCA EN EL TIEMPO ADECUADO
        if(nImage==16){
            condicionTerminoPintura=true;
            condicionTocaAqui=false;
            tiempoCondicionPuzzle=false;
            float tempTiempo=tiempoPuzzle;
            texto.mostrarMensaje(batch, "FELICIDADES, TERMINASTE EL PRIMER NIVEL \n Tu tiempo fue: "+(int)tempTiempo, (ANCHO / 2), (ALTO/2)+50);
            texto.setColor(0, 0, 0, 1);
            //CARGA NUEVA ESCENA FIJARNOS EN EL DELAY
            float delay = 4; // seconds
            if(played == false) {
                played = true;
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        // Do your work
                        sonidoF.stop();
                        juego.setScreen(new ScreenTwo(juego));
                    }
                }, delay);
            }
        }
    }

    //tiempo dialogos
    private void tiempoDialogos() {
        if ( delayDialog%160==0 && startdialog==true  && pressEspaciadora==true) {
            tiempoParpadeo = 0;
            nDialog++;
        }

    }
    //manejo dialogos
    private void sistemaDialogos() {
        if( startdialog==true  && pressEspaciadora==true){
            if(estadoJuego==EstadoJuego.JUGANDO) {
                if (nDialog == 0 || nDialog == 2 || nDialog == 4 /*|| nDialog == 5 || nDialog == 8*/) {
                    condicionTemp = false;
                    batch.draw(dialogos[nDialog], 485, 277);

                }
                if (nDialog == 1 || nDialog == 3 || nDialog == 6 /*|| nDialog == 7*/) {
                    batch.draw(dialogos[nDialog], 780, 245);
                }
                if (nDialog == 9) {
                    pressEspaciadora = false;
                }
            }
        }
    }

    //tambien corrobora si debe pasar el input procesor a el puzzle o no
    private void dibujarElementos(SpriteBatch batch) {
        lienzo.dibujar(batch);
        if(condicionTocaAqui==true && condicionTerminoPintura==false ){
            btnTocaAqui.dibujar(batch);
        }
    }

    private void manejoInputPintura(){
        if(condicionTocaAqui){
            Gdx.input.setInputProcessor(procesadorEntrada);
        }
        if(condicionTocaAqui==false && condicionUnaVez==true){
            //Steven.setEstadoMovimiento(PlayerSteven.EstadoMovimiento.QUIETO);
            //CHECAR ESTADO MOVIMIENTO
            Steven.setEstadoMovimiento(PlayerSteven.EstadoMovimiento.INICIANDO);
            //guardamos un temp
            //y volvemos a crear
            //Se pierde la referencia guardar estado pasado y crear uno nuevo, esto debido al cambio de inputs
            //System.out.println("Llegamos aquí: "+Steven.getEstadoMovimiento());
            Gdx.input.setInputProcessor(controller.getStage());

            condicionUnaVez=false;
        }
        //PORQUE NO FUNCIONA?
        /*if(condicionTerminoPintura){
            Gdx.input.setInputProcessor(controller.getStage());
        }*/
    }
    //Procesador entrada para minujuego pinturas
    class ProcesadorEntrada implements InputProcessor {
        private Vector3 v = new Vector3(); //Objeto tipo vector para hacer match con objetos por coordenadas

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
                //EVITAMOS QUE STEVEN SE MUEVA
                Steven.estadoMovimiento= PlayerSteven.EstadoMovimiento.QUIETO;
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

    @Override
    public void dispose() {
        //aqui descargamos todo lo utilizado para ahorrar memoria
    }

    public void update(float dt) {
        handleInput();
        pausaInput();
        generarNumRandom();
        world.step(1 / 60f, 6, 2);
        batch.setProjectionMatrix(camara.combined);
        camara.update();

    }
//Metodos get que nos permiten modificar en escena pausa
    public Pantalla getScreenOne(){
        return this;
    }
    public AssetManager getManager(){
        return manager;
    }
    public Controller getController(){
        return controller;
    }
    public MHMain getJuego(){
        return this.juego;
    }
    public Music getSonidoF(){ return sonidoF;}
}






