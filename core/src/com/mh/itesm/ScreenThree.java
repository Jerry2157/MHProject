package com.mh.itesm;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
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

public class ScreenThree extends Pantalla {//patio trasero
    private int tamMundoWidth = 1280;
    private boolean played = false;

    //Steven
    private PlayerSteven Steven;

    private EscenaPausa escenaPausa;
    private EstadoJuego estadoJuego = EstadoJuego.JUGANDO; //Estado del juego

    //Cop
    private FirstCop cop;
    private int TamEscena = 0;
    private MHMain juego;

    //Mom and daughter
    private Texture mom;
    private Sprite momNdaughter;

    Body player;
    Controller controller;
    private Texture BackgroundLayerOne;   // Imagen que se muestra
    private Stage escenaMenu;
    //Dialogo 1
    private Texture dialog1;
    private float tiempo=0;
    //condicional que maneja el mov en pausa

    public ScreenThree(MHMain juego) {
        //Crear a Steven
        Steven = new PlayerSteven(10,64,tamMundoWidth);
        Steven.setEstadoMovimiento(PlayerSteven.EstadoMovimiento.MOV_DERECHA);

        //crear al policia
        cop = new FirstCop(1281,64,tamMundoWidth);
        cop.setEstadoMovimiento(FirstCop.EstadoMovimiento.QUIETO);

        //mama e hija
        mom = new Texture("Characters/EsposaHija.png");
        momNdaughter = new Sprite(mom);
        momNdaughter.setX(750);
        momNdaughter.setY(-2);

        Gdx.input.setInputProcessor(escenaMenu);
        this.juego = juego;

        controller = new Controller();
    }

    public void handleInput(){
        if(played == false) {
            if (controller.isRightPressed()) {
                //player.setLinearVelocity(new Vector2(100, player.getLinearVelocity().y));
                Steven.setEstadoMovimiento(PlayerSteven.EstadoMovimiento.MOV_DERECHA);
            } else if (controller.isLeftPressed() || played == true) {
                //player.setLinearVelocity(new Vector2(-100, player.getLinearVelocity().y));
                Steven.setEstadoMovimiento(PlayerSteven.EstadoMovimiento.MOV_IZQUIERDA);
            } else {
                //player.setLinearVelocity(new Vector2(0, player.getLinearVelocity().y));
                Steven.setEstadoMovimiento(PlayerSteven.EstadoMovimiento.QUIETO);
            }
            if (controller.isUpPressed() && player.getLinearVelocity().y == 0) {
                //player.applyLinearImpulse(new Vector2(0, 20f), player.getWorldCenter(), true);
                Steven.setEstadoMovimiento(PlayerSteven.EstadoMovimiento.QUIETO);
            }
        }
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
        cargarTexturas();
        //Gdx.input.setInputProcessor(new ProcesadorEntrada());

    }


    private void cargarTexturas() {
        BackgroundLayerOne = new Texture("ScreenThree/ScreenThreeBNG.png");
        dialog1=new Texture("Dialogos/Nivel1/11.png");

    }

    @Override
    public void render(float delta) {
        cambiarEscena();
        Steven.actualizar();
        cop.actualizar();
        update(Gdx.graphics.getDeltaTime());
        borrarPantalla(0.8f,0.45f,0.2f);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camara.combined);
        batch.begin();

        batch.draw(BackgroundLayerOne, Pantalla.ANCHO/2 -BackgroundLayerOne.getWidth()/2,Pantalla.ALTO/2-BackgroundLayerOne.getHeight()/2);
        batch.draw(momNdaughter,momNdaughter.getX(),momNdaughter.getY());
        Steven.dibujar(batch);
        cop.dibujar(batch);

        //aqui va el unico dialogo
        if(((int)tiempo>=7 && (int)tiempo<=13)&&Steven.getX()>=560){
            batch.draw(dialog1,900,260);
        }

        batch.end();
        //b2dr.render(world,camara.combined);
        //batch.setProjectionMatrix(camara.combined);

        if (estadoJuego == EstadoJuego.PAUSADO && escenaPausa!=null ) {
            //Steven.setEstadoMovimiento(PlayerSteven.EstadoMovimiento.QUIETO);
            //cop.setEstadoMovimiento(FirstCop.EstadoMovimiento.QUIETO);
            escenaPausa.draw(); //DIBUJAMOS escenaPausa si esta
           
        }
        tiempo += Gdx.graphics.getDeltaTime();

        controller.draw();


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
        handleInput();
        //world.step(1/60f,6,2);
        //camara.position.set(vista.getWorldWidth()/2,vista.getWorldHeight()/2,0);
        pausaInput();
        //camara.update();

        camara.update();

    }
    public void cambiarEscena(){
        if(Steven.getX()>=560 && played == false){ //258  y 512 es la posicion del templo, lo identifique con el system.out.println
            // Para verificar si el usuario ya tomo los 3 pergaminos y liberar el boton de galeria de arte...
            /*liberarArte();
            this.efectoPuertaTemplo.play(PantallaMenu.volumen);
            PantallaCargando.partidaGuardada.putBoolean("nivelAgua", true); //se guarda el progreso y se desbloquea el nivel de agua...
            PantallaCargando.partidaGuardada.flush(); //se guardan los cambios*/
            //juego.setScreen(new ScreenOne(juego));//Debug

            //Se espera un segundo
            float delay = 1f; // seconds
            Steven.setEstadoMovimiento(PlayerSteven.EstadoMovimiento.QUIETO);
            played = true;
            cop.setEstadoMovimiento(FirstCop.EstadoMovimiento.MOV_IZQUIERDA);
            cop.VELOCIDAD_X = 4.0f;
            System.out.println("paso uno");
            Timer.schedule(new Timer.Task(){
                @Override
                public void run() {
                    // Do your work
                    System.out.println("paso 2");
                    cop.setEstadoMovimiento(FirstCop.EstadoMovimiento.QUIETO);
                    if(estadoJuego==EstadoJuego.JUGANDO){
                        showPolice();
                    }

                }
            }, delay);
        }
        else if (64 == Steven.getX() &&  64 <= Steven.getY() && played == true){
            juego.setScreen(new ScreenFour(juego));
        }
        else{

        }
    }

    private void showPolice() {
        //Se espera un segundo
        float delay = 2.0f; // seconds

        Timer.schedule(new Timer.Task(){
            @Override
            public void run() {
                // Do your work
                cop.VELOCIDAD_X = 2.0f;
                cop.setEstadoMovimiento(FirstCop.EstadoMovimiento.MOV_IZQUIERDA);

                System.out.println("paso 3");
                Steven.setEstadoMovimiento(PlayerSteven.EstadoMovimiento.MOV_IZQUIERDA);
            }
        }, delay);
    }


    //Metodos get que nos permiten modificar en escena pausa
    public Pantalla getScreenThree(){
        return this;
    }
    public Controller getController(){
        return controller;
    }
    public MHMain getJuego(){
        return this.juego;
    }
    //public Music getSonidoF(){ return sonidoF;}
    public EstadoJuego getEstadoJuego(){
        return estadoJuego;
    }
    public void setEstadoJuego(EstadoJuego estado){
        estadoJuego=estado;
    }
}
