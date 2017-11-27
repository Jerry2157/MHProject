package com.mh.itesm;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by jerry2157 on 03/10/17.
 */

public class ScreenFinal extends Pantalla {//jardin
    private int tamMundoWidth = 3840;
    private boolean played = false;



    //Cop
    private FirstCop cop;
    private int TamEscena = 0;
    private MHMain juego;

    //Mom and daughter
    private Texture mom;
    private Sprite momNdaughter;

    //badpeople
    private Texture evilTex;
    private Sprite evil;

    //World world;
    //private Box2DDebugRenderer b2dr;
    Body player;
    Controller controller;
    private Texture BackgroundLayerOne;   // Imagen que se muestra

    // Contenedor de los botones
    private Stage escenaMenu;
    private Texture texturaBtnPintura;
    private Preferences prefs;

    //Dialogos
    private Dialogos dialogos;
    private boolean playedDialogo;
    private boolean runningDialogo;
    //------


    //cats functions
    private final float DELTA_X = 10;    // Desplazamiento del personaje
    private final float DELTA_Y = 10;
    private final float UMBRAL = 50; // Para asegurar que hay movimiento

    private final AssetManager manager;
    private float tiempoMaximo = 4.0f;

    // Punteros (dedo para paneo horizontal, vertical)
    private final int INACTIVO = -1;
    private int punteroHorizontal = INACTIVO;
    private int punteroVertical = INACTIVO;

    // Para salir
    private EscenaPausa escenaPausa;

    // Coordenadas
    private float xHorizontal = 0;
    private float yVertical = 0;

    // Objeto de prueba
    private PlayerStevenFinal steven;
    private float dx = 10;
    private float dy = 10;

    // Enemigos (hongos :)
    private Texture texturaHongo;
    private Array<Hongo> enemigos;
    private float tiempoEnemigo;    // Tiempo aleatorio para generar un enemigo

    // Proyectiles que lanza steven
    private Texture texturaBala;
    private Array<Bala> balas;

    //tmx gatos
    private TiledMap mapa;
    private OrthogonalTiledMapRenderer rendererMapa;

    //cats functions end

    //armas gatos
    private Sprite gatoUno;
    private Sprite gatoUnoFire;
    private Sprite gatoDos;
    private Sprite gatoDosFire;
    private Sprite gatoTres;
    private Sprite gatoTresFire;

    private Sprite gatoUnoState;
    private Sprite gatoDosState;
    private Sprite gatoTresState;

    private static Fondo fondo; //Imagen de fondo

    private boolean firerepeater;

    private boolean playingpuzzle;

    private Sprite Camioneta;
    private PatrullaPoliciaFinal patrullaOne, patrullaTwo;
    private Sprite Policia;

    private PlayerPoliciaFinal policia;


    private boolean playedGoal;
    private boolean playedGoalCops;
    private boolean playedCops;

    private Sprite camion;
    private Sprite malo;


    public ScreenFinal(MHMain juego, int xS, int yS) {

        playedGoal = false;
        playedCops = false;

        playingpuzzle = true;
        firerepeater = true;

        camion = new Sprite(new Texture("UltimoNivel/Camion.png"));
        camion.setPosition(3050,300);
        malo = new Sprite(new Texture("UltimoNivel/villanosentado.png"));
        malo.setPosition(3120,365);

        //gatos armas
        gatoUno = new Sprite(new Texture("PuzzleGatos/GatoMalo160x120.png"));
        gatoUnoFire = new Sprite(new Texture("PuzzleGatos/GatoMaloAbierto160x120.png"));
        gatoDos = new Sprite(new Texture("PuzzleGatos/GatoMalo2160x120.png"));
        gatoDosFire = new Sprite(new Texture("PuzzleGatos/GatoMalo2Abierto160x120.png"));
        gatoTres = new Sprite(new Texture("PuzzleGatos/GatoMalo3160x120.png"));
        gatoTresFire = new Sprite(new Texture("PuzzleGatos/GatoMalo3Abierto160x120.png"));

        gatoUnoState = gatoUno;
        gatoDosState = gatoDos;
        gatoTresState = gatoTres;

        //Dialogo
        playedDialogo = false;
        runningDialogo = false;
        dialogos = new Dialogos();
        //-------

        //cargar mapa xd
        cargarMapa();

        prefs = Gdx.app.getPreferences("My Preferences");

        //evilTex = new Texture("evilanim");
        //Crear a Steven
        //Steven = new PlayerSteven(xS,yS,tamMundoWidth);
        //Steven.setEstadoMovimiento(PlayerSteven.EstadoMovimiento.MOV_DERECHA);

        Gdx.input.setInputProcessor(escenaMenu);
        this.juego = juego;

        controller = new Controller();

        // evil
        if(prefs.getBoolean("finalunlocked")==true){
            evilTex = new Texture("evil.png");
            evil = new Sprite(evilTex);
            //cop
            cop = new FirstCop(3950,64,tamMundoWidth);
        }

        //cats function
        manager = juego.getAssetManager();
        facecathelper();
    }
    public void Confrontation(){
        if(played == false){
            played = true;
            //inicia animacion de confrontacion
            prefs.putBoolean("finalscape",true);
        }
    }
    private void cargarMapa() {
        mapa = new TmxMapLoader().load("UltimoNivel/Fondo.tmx");
        rendererMapa = new OrthogonalTiledMapRenderer(mapa);
    }

    public void handleInput(){
        if(played == false) {
            if (controller.isRightPressed()) {
                //player.setLinearVelocity(new Vector2(100, player.getLinearVelocity().y));
                //Steven.setEstadoMovimiento(PlayerSteven.EstadoMovimiento.MOV_DERECHA);
                steven.setEstadoMovimiento(PlayerStevenFinal.EstadoMovimiento.MOV_DERECHA);
            } else if (controller.isLeftPressed() || played == true) {
                //player.setLinearVelocity(new Vector2(-100, player.getLinearVelocity().y));
                //Steven.setEstadoMovimiento(PlayerSteven.EstadoMovimiento.MOV_IZQUIERDA);
                steven.setEstadoMovimiento(PlayerStevenFinal.EstadoMovimiento.MOV_IZQUIERDA);
            }
            else if (controller.isUpPressed()) {
                //player.applyLinearImpulse(new Vector2(0, 20f), player.getWorldCenter(), true);
                //steven.saltar();
                //Steven.setEstadoMovimiento(PlayerSteven.EstadoMovimiento.QUIETO);
                steven.setEstadoMovimiento(PlayerStevenFinal.EstadoMovimiento.MOV_ARRIBA);
            }
            else if (controller.isDownPressed()) {
                //player.applyLinearImpulse(new Vector2(0, 20f), player.getWorldCenter(), true);
                //steven.saltar();
                //Steven.setEstadoMovimiento(PlayerSteven.EstadoMovimiento.QUIETO);
                steven.setEstadoMovimiento(PlayerStevenFinal.EstadoMovimiento.MOV_ABAJO);
            }
            else {
                //player.setLinearVelocity(new Vector2(0, player.getLinearVelocity().y));
                //Steven.setEstadoMovimiento(PlayerSteven.EstadoMovimiento.QUIETO);
                steven.setEstadoMovimiento(PlayerStevenFinal.EstadoMovimiento.QUIETO);
            }

            if((controller.isButtonPressed() || controller.isSpacePressed()) && firerepeater){
                disparar();
                firerepeater = false;
                //Se espera un segundo
                float delay = 0.1f; // seconds

                Timer.schedule(new Timer.Task(){
                    @Override
                    public void run() {
                        // Do your work
                        firerepeater = true;
                    }
                }, delay);
            }

        }
    }

    @Override
    public void show() {
        cargarTexturas();

        enemigos = new Array<Hongo>();   // Arreglo de hongos
        balas = new Array<Bala>();
        tiempoEnemigo = MathUtils.random(1.5f,5.0f);
        //texturaFondo = manager.get("runner/fondoRunnerD.jpg");
        //texturaHongo = manager.get("runner/enemigo.png");
        texturaHongo = manager.get("UltimoNivel/barrilito.png");

        texturaBala = manager.get("PuzzleGatos/tuna.png");
        //fondoB = new FondoB(texturaFondo);
        steven = new PlayerStevenFinal((Texture)(manager.get("UltimoNivel/Stevenvistaarriba.png")), 32, 360);
        policia = new PlayerPoliciaFinal((Texture)(manager.get("UltimoNivel/PoliciaVistaArriba.png")),2000,360-16);
        patrullaOne = new PatrullaPoliciaFinal((Texture)(manager.get("UltimoNivel/AutoPolicia.png")), 2000, 430);
        patrullaTwo = new PatrullaPoliciaFinal((Texture)(manager.get("UltimoNivel/AutoPolicia.png")), 2000, 130);
    }

    private void cargarTexturas() {
        BackgroundLayerOne = new Texture("Patio.png");
        fondo = new Fondo(BackgroundLayerOne);
        fondo.setPosicion(0,0);

    }

    @Override
    public void render(float delta) {

        cambiarEscena();
        //steven.actualizar();

        // Actualizar
        //actualizarMario();
        //actualizarSaltoMario(delta);
        actualizarEnemigos(delta);
        //actualizarBalas(delta);

        steven.actualizar(delta,mapa);



        if(prefs.getBoolean("finalunlocked")) {
            cop.actualizar();
        }
        update(Gdx.graphics.getDeltaTime());
        actualizarCamara();
        borrarPantalla(0.8f,0.45f,0.2f);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camara.combined);


        //renderizar mapTiled
        rendererMapa.setView(camara);
        rendererMapa.render();  // DIBUJA el mapa
        batch.begin();

        //batch.draw(BackgroundLayerOne, Pantalla.ANCHO/2 -BackgroundLayerOne.getWidth()/2,Pantalla.ALTO/2-BackgroundLayerOne.getHeight()/2);
        //fondo.render(batch);
        steven.dibujar(batch);
        //fondo.setPosicion(0,0);
        // Dibujar enemigo
        for (Hongo hongo : enemigos) {
            hongo.dibujar(batch);
        }
        for (Bala bala : balas) {
            bala.dibujar(batch);
        }

        camion.draw(batch);
        malo.draw(batch);

        if(playedGoalCops && !playedCops){
            policia.actualizar(delta,mapa);
            policia.dibujar(batch);
            policia.setEstadoMovimiento(PlayerPoliciaFinal.EstadoMovimiento.MOV_DERECHA);

            patrullaOne.actualizar(delta,mapa);
            patrullaOne.dibujar(batch);
            patrullaOne.setEstadoMovimiento(PatrullaPoliciaFinal.EstadoMovimiento.MOV_DERECHA);

            patrullaTwo.actualizar(delta,mapa);
            patrullaTwo.dibujar(batch);
            patrullaTwo.setEstadoMovimiento(PatrullaPoliciaFinal.EstadoMovimiento.MOV_DERECHA);
            if(policia.sprite.getX()>=2500){
                playedCops = true;
            }
        }else if(playedGoalCops && playedCops){
            policia.dibujar(batch);
            policia.setEstadoMovimiento(PlayerPoliciaFinal.EstadoMovimiento.QUIETO);
            patrullaOne.dibujar(batch);
            patrullaOne.setEstadoMovimiento(PatrullaPoliciaFinal.EstadoMovimiento.QUIETO);
            patrullaTwo.dibujar(batch);
            patrullaTwo.setEstadoMovimiento(PatrullaPoliciaFinal.EstadoMovimiento.QUIETO);

        }

        //Dialogo
        if((playedGoal) && !playedDialogo){
            runningDialogo = true;
            playedDialogo = dialogos.dibujar(batch,9);
            played = !playedDialogo;
        }
        //-------

        //Steven.dibujar(batch);
        if(prefs.getBoolean("finalunlocked")) {
            cop.dibujar(batch);
        }

        batch.end();



        //b2dr.render(world,camara.combined);
        //batch.setProjectionMatrix(camara.combined);
        if(Gdx.app.getType() == Application.ApplicationType.Android)
            controller.draw();
    }

    public void finalAnimation(){

    }



    public void facecathelper(){
        if(playingpuzzle){
            //Se espera un segundo
            float delay = 0.1f; // seconds

            Timer.schedule(new Timer.Task(){
                @Override
                public void run() {
                    // Do your work
                    gatoUnoState = gatoUno;
                    gatoDosState = gatoDos;
                    gatoTresState = gatoTres;
                    facecathelper();
                }
            }, delay);
        }
    }
    private void actualizarBalas(float delta) {
        // ¿POR QUÉ CREES QUE SE RECORRE AL REVÉS EL ARREGLO?
        for(int i=balas.size-1; i>=0; i--) {
            Bala bala = balas.get(i);
            bala.mover(delta);
            if (bala.sprite.getX()>ANCHO) {
                // Se salió de la pantalla
                balas.removeIndex(i);
                break;
            }
            // Prueba choque contra todos los enemigos
            for (int j=enemigos.size-1; j>=0; j--) {
                Hongo hongo = enemigos.get(j);
                if (bala.chocaCon(hongo)) {
                    // Borrar hongo, bala, aumentar puntos, etc.
                    enemigos.removeIndex(j);
                    balas.removeIndex(i);
                    break;  // Siguiente bala, ésta ya no existe
                }
            }
        }
    }

    private void actualizarEnemigos(float delta) {
        if(steven.getX() <2500) {
            // Generar nuevo enemigo
            tiempoEnemigo -= delta;
            if (tiempoEnemigo <= 0) {
                tiempoEnemigo = MathUtils.random(0.5f, tiempoMaximo);
                tiempoMaximo -= tiempoMaximo > 0.5f ? 40 * delta : 0;
                Hongo hongo = new Hongo(texturaHongo, camara.position.x + 700, 64 * MathUtils.random(1, 8) + 36, 32, 64);
                gatoUnoState = gatoUnoFire;
                gatoDosState = gatoDosFire;
                gatoTresState = gatoTresFire;

                enemigos.add(hongo);
            }
        }
            // Actualizar enemigos
            for (Hongo hongo : enemigos) {
                hongo.mover(delta);
            }
            // Verificar choque
            for (int k = enemigos.size - 1; k >= 0; k--) {
                Hongo hongo = enemigos.get(k);
                if (hongo.chocaCon(steven)) {
                    // Pierde!!!
                    steven.setEstadoMovimiento(PlayerStevenFinal.EstadoMovimiento.QUIETO);
                    played = true;
                    juego.setScreen(new PantallaCargando(juego,Pantallas.FINAL));
                    enemigos.removeIndex(k);
                    // Activar escenaPausa y pasarle el control
                    if (escenaPausa == null) {
                        escenaPausa = new EscenaPausa(vista, batch);

                    }
                    Gdx.input.setInputProcessor(escenaPausa);
                } else if (hongo.sprite.getX() < -hongo.sprite.getWidth()) {
                    enemigos.removeIndex(k);
                }
            }

    }
    private void terminar() {
        // Activar escenaPausa y pasarle el control
        if (escenaPausa==null) {
            escenaPausa = new EscenaPausa(vista, batch);
        }
        Gdx.input.setInputProcessor(escenaPausa);
    }

    private void actualizarSaltoMario(float delta) {
        steven.actualizar(delta);
    }
    // Ejercicio anterior, por ahora deshabilitado
    private void actualizarMario() {
        steven.sprite.setPosition(steven.sprite.getX()+dx, steven.sprite.getY()+dy);
    }
    private void disparar() {
        Bala bala = new Bala(texturaBala,
                steven.sprite.getX()+ steven.sprite.getWidth()/2,
                steven.sprite.getY()+ steven.sprite.getHeight()/2-texturaBala.getHeight()/2);
        balas.add(bala);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        //manager.unload("runner/fondoRunnerD.jpg");
        manager.unload("UltimoNivel/Stevenvistaarriba.png");
        //manager.unload("runner/enemigo.png");
        manager.unload("UltimoNivel/barrilito.png");
        //manager.unload("comun/btnSalir.png");

    }

    public void update(float dt){
        handleInput();


        camara.update();

    }
    public void cambiarEscena(){
        if(steven.sprite.getX()>=3000 && played == false) {
            played= true;
            playedGoal = true;
            steven.setEstadoMovimiento(PlayerStevenFinal.EstadoMovimiento.QUIETO);
            //nextScreenRight();

            //Se espera un segundo
            float delay = 5.0f; // seconds

            Timer.schedule(new Timer.Task(){
                @Override
                public void run() {
                    // Do your work
                    playedGoalCops = true;
                }
            }, delay);
            //Se espera un segundo
            float delayTwo = 120.0f; // seconds

            Timer.schedule(new Timer.Task(){
                @Override
                public void run() {
                    // Do your work
                    juego.setScreen(new PantallaCargando(juego,Pantallas.ENDING));
                }
            }, delayTwo);
        }
    }

    private void nextScreenRight() {
        //Se espera un segundo
        float delay = 0.5f; // seconds

        Timer.schedule(new Timer.Task(){
            @Override
            public void run() {
                // Do your work
                //juego.setScreen(new ScreenSix(juego,20,64));
            }
        }, delay);
    }

    // La escena que se muestra cuando el juego se pausa
    // (simplificado, ver la misma escena en PantallaWhackAMole)
    private class EscenaPausa extends Stage
    {
        public EscenaPausa(Viewport vista, SpriteBatch batch) {
            super(vista, batch);
            // Crear rectángulo transparente
            Pixmap pixmap = new Pixmap((int)(ANCHO*0.7f), (int)(ALTO*0.8f), Pixmap.Format.RGBA8888 );
            pixmap.setColor( 0.1f, 0.1f, 0.1f, 0.65f );
            pixmap.fillRectangle(0, 0, pixmap.getWidth(), pixmap.getHeight());
            Texture texturaRectangulo = new Texture( pixmap );
            pixmap.dispose();
            Image imgRectangulo = new Image(texturaRectangulo);
            imgRectangulo.setPosition(0.15f*ANCHO, 0.1f*ALTO);
            this.addActor(imgRectangulo);

            // Salir
            Texture texturaBtnSalir = manager.get("comun/btnSalir.png");
            TextureRegionDrawable trdSalir = new TextureRegionDrawable(
                    new TextureRegion(texturaBtnSalir));
            ImageButton btnSalir = new ImageButton(trdSalir);
            btnSalir.setPosition(ANCHO/2-btnSalir.getWidth()/2, ALTO*0.2f);
            btnSalir.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    // Regresa al menú
                    juego.setScreen((new ScreenSix(juego,64,10)));
                }
            });
            this.addActor(btnSalir);
        }
    }
    private void actualizarCamara() {
        float posX = steven.sprite.getX();
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
