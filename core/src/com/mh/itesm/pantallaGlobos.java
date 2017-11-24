package com.mh.itesm;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by Usuario on 19-Nov-17.
 */

//clase que es el juego globos
public class pantallaGlobos extends Pantalla {
    private MHMain juego;
    private final ProcesadorEntrada procesadorEntrada = new ProcesadorEntrada();
    private Texture BackgroundLayerOne;
    private Fondo fondo;
    private Sound efecto;
    //efecto confeti
    private ParticleEffect efectoParticulas;
    private ParticleEmitter emisorParticulas;

    //igual y solo teniendo el arreglo es suficiente
    private Objeto per1,per2,per3,per4,per5,per6,per7,per8,per9,per10,per11,per12,per13,per14,per15,per16,per17,per18,per19,per20,per21,per22,per23,per24,per25,per26,per27,
            per28,per29,per30, per31,per32, per33,per34,per35,per36,per37,per38,per39,per40,per41,per42,per43,per44,ene9,glob1,glob2,glob3,glob4,glob5,glob6,glob7,glob8,
            glob9,glob10,glob11,glob12,glob13,glob14,glob15,glob16,glob17,glob18,glob19,glob20,glob21,glob22,glob23,glob24,glob25,glob26,glob27,glob28,glob29,glob30,glob31,
            glob32,glob33,glob34,glob35,glob36,glob37,glob38,glob39,glob40,glob41;
    private Objeto[] personas,globos;
    private Texture confeti;
    private boolean finishilera1=false;
    //int que nos dira cuando se hayan borrado las 15 personas
    private int deletedilera1=0;
    private boolean finishilera2=false;
    //nos dice si debe dibujar el confeti
    private boolean animacion=false;
    //coordenadas para decirle donde dibujar el confeti
    private float coordenadX,coordenadY;
    //para mostrar tiempo y marcador
    private Texto texto;
    private int marcador=0;
    private float tiempo=0;
    private EscenaPierde escenaPierde;
    private EstadoJuego estadoJuego= EstadoJuego.JUGANDO;
    //variable para dejar de sumar un tiempo cuando pierde
    private boolean stoptime;
    //variable que limita escena pierde
    private boolean perdio=false;
    //variable para llevar el registro del tiempo para perder
    private float tiempoPer=0;

    Preferences prefs;

    public pantallaGlobos(MHMain juego){
        prefs = Gdx.app.getPreferences("My Preferences");
        this.juego=juego;
        personas=new Objeto[44];
        globos=new Objeto[44];
        Gdx.input.setInputProcessor(procesadorEntrada);
        texto=new Texto();

    }
    @Override
    public void show() {
        crearObjetos();
    }

    //Creamos a las personas y globos como objetos
    private void crearObjetos(){
        BackgroundLayerOne = new Texture("PuzzleGlobos/fondoGlobos.png");
        fondo = new Fondo(BackgroundLayerOne);
        fondo.setPosicion(0,0);

        Texture pe1=new Texture("PuzzleGlobos/Hombre1.png");
        Texture pe2=new Texture("PuzzleGlobos/Mujer1.png");
        Texture pe3=new Texture("PuzzleGlobos/Hombre2.png");
        Texture pe4=new Texture("PuzzleGlobos/Hombre4.png");
        Texture pe5=new Texture("PuzzleGlobos/Mujer2.png");
        Texture pe6=new Texture("PuzzleGlobos/Hombre3.png");
        Texture pe7=new Texture("PuzzleGlobos/Mujer3.png");
        Texture pe8=new Texture("PuzzleGlobos/Mujer4.png");
        Texture en9=new Texture("PuzzleGlobos/CuerpoMalo.png");

        Texture gloA=new Texture("PuzzleGlobos/Amarillo.png");
        Texture gloAz=new Texture("PuzzleGlobos/Azul.png");
        Texture gloR=new Texture("PuzzleGlobos/Rojo.png");
        Texture gloV=new Texture("PuzzleGlobos/Verde.png");
        confeti=new Texture("PuzzleGlobos/confeti.png");

        efecto= Gdx.audio.newSound(Gdx.files.internal("PuzzleGlobos/sound.wav"));

        //OBJETOS PERSONAS
        per1=new Objeto(pe1,0,0);per2=new Objeto(pe2,90,0);per3=new Objeto(pe3,180,0);per4=new Objeto(pe4,270,0);per5=new Objeto(pe5,360,0);per6=new Objeto(pe6,450,0);
        per7=new Objeto(pe7,540,0);per8=new Objeto(pe8,630,0);per9=new Objeto(pe1,720,0);per10=new Objeto(pe2,810,0);per11=new Objeto(pe3,900,0);per12=new Objeto(pe4,990,0);
        per13=new Objeto(pe6,1080,0);per14=new Objeto(pe7,1170,0);

        per15=new Objeto(pe8,55,40);per16=new Objeto(pe1,145,40);per17=new Objeto(pe2,235,40);
        per18=new Objeto(pe3,325,40);per19=new Objeto(pe4,415,40);per20=new Objeto(pe7,505,40);per21=new Objeto(pe6,595,40);per22=new Objeto(pe7,685,40);
        per23=new Objeto(pe8,775,40);per24=new Objeto(pe3,865,40);per25=new Objeto(pe4,955,40);per26=new Objeto(pe5,1045,40);per27=new Objeto(pe6,1135,40);

        per28=new Objeto(pe7,0,80);per29=new Objeto(pe8,90,80);per30=new Objeto(pe1,180,80);per31=new Objeto(pe2,270,80); per32=new Objeto(pe7,360,80);
        per33=new Objeto(pe2,450,80);per34=new Objeto(pe5,540,80); per35=new Objeto(pe1,630,80);per36=new Objeto(pe8,720,80);per37=new Objeto(pe2,810,80);
        per38=new Objeto(pe5,900,80);per39=new Objeto(en9,990,80);per40=new Objeto(pe8,1080,80);per41=new Objeto(pe6,1170,80);

        personas[0]=per1; personas[1]=per2; personas[2]=per3; personas[3]=per4;personas[4]=per5;personas[5]=per6; personas[6]=per7; personas[7]=per8; personas[8]=per9;
        personas[9]=per10; personas[10]=per11; personas[11]=per12; personas[12]=per13; personas[13]=per14; personas[14]=per15; personas[15]=per16; personas[16]=per17;
        personas[17]=per18;personas[18]=per19; personas[19]=per20; personas[20]=per21; personas[21]=per22; personas[22]=per23; personas[23]=per24; personas[24]=per25;
        personas[25]=per26; personas[26]=per27;personas[27]=per28; personas[28]=per29; personas[29]=per30; personas[30]=per31; personas[31]=per32; personas[32]=per33;
        personas[33]=per34; personas[34]=per35; personas[35]=per36; personas[36]=per37;personas[37]=per38; personas[38]=per39; personas[39]=per40; personas[40]=per41;



        //Globos
        glob1=new Objeto(gloA,0,210); glob2=new Objeto(gloAz,95,210); glob3=new Objeto(gloR,180,210); glob4=new Objeto(gloV,270,210); glob5=new Objeto(gloA,365,210);
        glob6=new Objeto(gloAz,450,210);glob7=new Objeto(gloR,545,210); glob8=new Objeto(gloV,635,210); glob9=new Objeto(gloA,720,210); glob10=new Objeto(gloAz,815,210);
        glob11=new Objeto(gloR,900,210); glob12=new Objeto(gloV,990,210);glob13=new Objeto(gloA,1080,210);glob14=new Objeto(gloAz,1175,210);

        glob15=new Objeto(gloR,55,250);glob16=new Objeto(gloV,145,250); glob17=new Objeto(gloA,240,250);glob18=new Objeto(gloAz,325,250);glob19=new Objeto(gloR,415,250);
        glob20=new Objeto(gloV,510,250);glob21=new Objeto(gloA,595,250);glob22=new Objeto(gloAz,690,250);glob23=new Objeto(gloR,780,250);glob24=new Objeto(gloV,870,250);
        glob25=new Objeto(gloA,960,250);glob26=new Objeto(gloAz,1045,250);glob27=new Objeto(gloR,1135,250);

        glob28=new Objeto(gloV,0,290);glob29=new Objeto(gloA,95,290); glob30=new Objeto(gloAz,180,290);glob31=new Objeto(gloR,270,290);glob32=new Objeto(gloV,365,290);
        glob33=new Objeto(gloA,450,290);glob34=new Objeto(gloAz,545,290);glob35=new Objeto(gloA,635,290);glob36=new Objeto(gloAz,720,290);glob37=new Objeto(gloR,815,290);
        glob38=new Objeto(gloV,900,290);glob39=new Objeto(gloA,985,295);glob40=new Objeto(gloAz,1080,290); glob41=new Objeto(gloR,1175,290);

        globos[0]=glob1; globos[1]=glob2; globos[2]=glob3; globos[3]=glob4;globos[4]=glob5;globos[5]=glob6; globos[6]= glob7; globos[7]=glob8; globos[8]=glob9;globos[9]=glob10;
        globos[10]=glob11; globos[11]=glob12; globos[12]=glob13; globos[13]=glob14;

        globos[14]=glob15; globos[15]=glob16; globos[16]=glob17;globos[17]=glob18;globos[18]=glob19; globos[19]=glob20; globos[20]=glob21; globos[21]=glob22; globos[22]=glob23;
        globos[23]=glob24; globos[24]=glob25;globos[25]=glob26; globos[26]=glob27;

        globos[27]=glob28; globos[28]=glob29; globos[29]=glob30; globos[30]=glob31; globos[31]=glob32;globos[32]=glob33;globos[33]=glob34; globos[34]=glob35; globos[35]=glob36;
        globos[36]=glob37;globos[37]=glob38; globos[38]=glob39; globos[39]=glob40; globos[40]=glob41;


    }
    //metodo que maneja si se perdio
    private void checaPierde(){
        if (estadoJuego== EstadoJuego.PIERDE ) {
            // Activar escenaPausa y pasarle el control
            if (escenaPierde==null) {
                escenaPierde = new EscenaPierde(vista, batch);
            }
            Gdx.input.setInputProcessor(escenaPierde);

        }

    }

    @Override
    public void render(float delta) {
        //checa si perdio y asi
        update(Gdx.graphics.getDeltaTime());
        borrarPantalla(0.8f, 0.45f, 0.2f);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camara.combined);
        batch.begin();
        //dibujamos el fondo
        fondo.render(batch);
        fondo.setPosicion(0,0);

        //dibujamos a las personas y los globos tenemos que ir eliminando del arreglo si lo toca
        //dibujara si no es null
        for (int i=personas.length-1;i>=0;i--){
            if(personas[i]!=null && globos[i]!=null){
                personas[i].dibujar(batch);
                globos[i].dibujar(batch);
            }
            //si ya acabo
            if(deletedilera1==40 && personas[i]!=null){
                personas[i].dibujar(batch);
                //Cargar screen twelve
                float delay = 0.2f; // seconds
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        // Do your work
                        juego.setScreen(new ScreenTwelve(juego,600,32));
                    }
                }, delay);

            }

        }
        //aqui dibujamos el texto y tiempo que tardaste en dicho juego, es importante poner que si te equivocaste vuelva a cargar el juego, con enum
        texto.mostrarMensaje(batch, "Marcador: "+marcador, (ANCHO / 4)-177, (ALTO / 1)-5);
        texto.setColor(0, 0, 0, 1);

        texto.mostrarMensaje(batch, "Tiempo: "+(int)tiempo, (ANCHO / 4)+200, (ALTO / 1)-5);
        texto.setColor(0, 0, 0, 1);


        //Thread que comprueba el tiempo y si el jugador no ha hecho click le manda que perdio y que reintente
        if((int)tiempoPer%3==0 && perdio==false){
            if((int)tiempoPer>0){
                //aqui detenemos la escena y que
                //texto.mostrarMensaje(batch, "Perdiste, vuelve a intentarlo ", (ANCHO /2), (ALTO /2)+50);
                //texto.setColor(0, 0, 0, 1);
                stoptime=true;
                estadoJuego= EstadoJuego.PIERDE;
            }

        }

        //talvez se ve feo porque lo esta haciendo 60 veces por segundo tenemos que sacarlo
        if(animacion==true){
            batch.draw(confeti,coordenadX,coordenadY);
            float delay = 1f; // seconds
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    // Do your work
                    animacion=false;
                }
            }, delay);
        }
        //IR AGREGANDO MARCADOR CON PUNTOS Y TIEMPO
        batch.end();
        if(stoptime==false) {
            tiempo += Gdx.graphics.getDeltaTime();
            if(perdio==false){
                tiempoPer += Gdx.graphics.getDeltaTime();
            }

        }

        if (estadoJuego == EstadoJuego.PIERDE && escenaPierde!=null ) {
            escenaPierde.draw(); //DIBUJAMOS escenaPierde si perdio
        }



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
        checaPierde();

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
            perdio=true;
            tiempoPer=0;
            if(finishilera1==false) {
                //para la primera ilera
                for (int i = 0; i <= 13; i++) {
                    //si lo toco, deja de existir
                    if (personas[i] != null && globos[i] != null) {
                        if (personas[i].contiene(v) || globos[i].contiene(v)) {
                            animacion=true;
                            coordenadX=personas[i].getX()-10;
                            coordenadY=personas[i].getY()+180;
                            efecto.play(0.1f);
                            personas[i] = null;
                            globos[i] = null;
                            marcador=marcador+100;
                            //aqui ponemos el efecto y su posicion
                            deletedilera1 ++;
                        }
                    }

                }
                if(deletedilera1==14){
                    float delay = 0.2f; // seconds
                    Timer.schedule(new Timer.Task() {
                        @Override
                        public void run() {
                            // Do your work
                            finishilera1=true;
                        }
                    }, delay);
                }
            }
            //Segunda ilera
            if(finishilera1==true && finishilera2==false) {
                for (int i = 14; i <= 26; i++) {
                    //si lo toco, deja de existir
                    if (personas[i] != null && globos[i] != null) {
                        if (personas[i].contiene(v) || globos[i].contiene(v)) {
                            animacion=true;
                            coordenadX=personas[i].getX()-10;
                            coordenadY=personas[i].getY()+180;
                            personas[i] = null;
                            globos[i] = null;
                            marcador=marcador+100;
                            efecto.play(0.1f);
                            deletedilera1 ++;
                        }
                    }

                }
                if(deletedilera1==27){
                    float delay = 0.2f; // seconds
                    Timer.schedule(new Timer.Task() {
                        @Override
                        public void run() {
                            // Do your work
                            finishilera2=true;
                        }
                    }, delay);
                }
            }
            //Tercera ilera
            if(finishilera2==true){
                for (int i = 27; i <= 40; i++) {
                    //si lo toco, deja de existir
                    if (personas[i] != null && globos[i] != null ) {
                        if (personas[i].contiene(v) || globos[i].contiene(v)) {
                            if(personas[38].contiene(v)){
                                //do nothing maybe finish the puzzle
                            }
                            else {
                                animacion=true;
                                coordenadX=personas[i].getX()-10;
                                coordenadY=personas[i].getY()+180;
                                personas[i] = null;
                                globos[i] = null;
                                marcador=marcador+100;
                                efecto.play(0.1f);
                                deletedilera1++;
                                if(deletedilera1==40){
                                    globos[38]=null;
                                    efecto.play(0.1f);
                                    finishilera2=true;
                                }
                            }
                        }
                    }

                }
            }

            perdio=false;
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

    // Escena pierde
    class EscenaPierde extends Stage
    {
        public EscenaPierde(Viewport vista, SpriteBatch batch) {
            super(vista, batch);
            // Crear círculo transparente
            Pixmap pixmap = new Pixmap((int)ANCHO, (int)(ALTO), Pixmap.Format.RGBA8888 );
            pixmap.setColor( 0, 0, 0, 0.55f );
            pixmap.fillCircle((int)ANCHO/2, (int)ALTO/2, (int)ALTO/2);
            Texture texturaCirculo = new Texture( pixmap );
            pixmap.dispose();
            this.addActor(new Image(texturaCirculo));

            //texto
            Image tex=new Image(new Texture("PuzzleGlobos/perdiste.png"));
            tex.setPosition(ANCHO/2-220,ALTO-200);
            this.addActor(tex);

            //Agregar botón salir
            Texture texturabtnSalir =new Texture("Botones/SALIR.png");
            TextureRegionDrawable trdSalir = new TextureRegionDrawable(
                    new TextureRegion(texturabtnSalir));
            ImageButton btnSalir = new ImageButton(trdSalir);
            btnSalir.setPosition(ANCHO/2-btnSalir.getWidth()/2, ALTO*0.3f);
            btnSalir.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    // Regresa al menú
                    juego.setScreen(new mainMenu(juego));
                }
            });
            this.addActor(btnSalir);

            //regresar a screen twelev

            // Reintentar
            // Agregar botón reintentar
            Texture texturabtnReintentar = new Texture("Botones/CONTINUAR.png");
            TextureRegionDrawable trdReintentar = new TextureRegionDrawable(
                    new TextureRegion(texturabtnReintentar));
            ImageButton btnReintentar = new ImageButton(trdReintentar);
            btnReintentar.setPosition(ANCHO/2-btnReintentar.getWidth()/2, (ALTO*0.6f)-20);
            btnReintentar.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    // Reiniciar el juego

                    // Regresa el control a la pantalla
                    Gdx.input.setInputProcessor(procesadorEntrada);
                    //al volver a crear la pantalla se crean desde cero todo
                    juego.setScreen(new pantallaGlobos(juego));
                }
            });
            this.addActor(btnReintentar);
        }
    }

}


