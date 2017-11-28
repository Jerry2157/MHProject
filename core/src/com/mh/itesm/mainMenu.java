package com.mh.itesm;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;

/**
 * Autor: Gerardo Ezequiel Magdaleno Hernandez
 * Autor: Jesus Heriberto Vasquez Sanchez A01377358.
 */


public class mainMenu extends Pantalla {
    private MHMain juego;

    // Contenedor de los botones
    private Stage escenaMenu;

    // Texturas de los botones
    private Texture texturaBtnJugar;
    private Texture texturaBtnAyuda;
    private Texture texturaBtnCredits;
    private Texture texturaBackground;
    private Texture texturaBtnMusica;
    private Texture texturaBtnMusicaX;

    private Texture texturaBtnTemp;

    private Texture backTexAnim;
    private Animation spriteAnimadoBNG;         // Animación caminando
    private float timerAnimacionBNG;               // Tiempo para cambiar frames de la animación
    protected Sprite sprite;    //

    private Music tonadaMenu;
    //Varible que lleva la cuenta del boton de musica para acivar y desactiva la musica
    private int tocado=0;

    Preferences prefs;

    private Stage stage;

    public mainMenu(MHMain juego) {
        prefs = Gdx.app.getPreferences("My Preferences");
        prefs.putBoolean("cocinaPassed",false);
        prefs.putBoolean("areaverdelocked",false);
        prefs.putBoolean("playedMother",false);
        prefs.putBoolean("continuehistory",false);
        prefs.putBoolean("PassedParty",false);
        prefs.putBoolean("playedDir",false);
        prefs.putBoolean("lockedDir",false);
        prefs.putBoolean("playedTalkDir", false);
        prefs.putBoolean("playedTalkDirCat",false);
        prefs.putBoolean("cuartosPassed",false);
        prefs.putBoolean("finalunlocked",false);
        prefs.putBoolean("playedSotano",false);

        prefs.flush();
        this.juego = juego;

        stage = new Stage(new FitViewport(1280, 720));

    }

    @Override
    public void show() {
        cargarTexturas();   // Carga imágenes
        crearEscenaMenu();  // Crea la escena
        Gdx.input.setInputProcessor(escenaMenu);

    }

    private void crearEscenaMenu() {
        escenaMenu = new Stage(vista);
        //--------------Inicia botones--------------

        //boton jugar
        TextureRegionDrawable trdPlay = new TextureRegionDrawable(new TextureRegion(texturaBtnJugar));
        ImageButton btnPlay = new ImageButton(trdPlay);
        btnPlay.setPosition(ANCHO/2 - btnPlay.getWidth()/2,0.25f*ALTO);
        escenaMenu.addActor(btnPlay);
        btnPlay.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                //paramos la musica
                tonadaMenu.stop();

                juego.setScreen(new PantallaCargando(juego, Pantallas.PRIMER_NIVEL)); //Primer Nivel!!!!
            }
        });
        //boton AYUDA
        TextureRegionDrawable trdAyuda = new TextureRegionDrawable(new TextureRegion(texturaBtnAyuda));
        ImageButton btnAyuda = new ImageButton(trdAyuda);
        btnAyuda.setPosition(ANCHO/4 - btnAyuda.getWidth()-180, 0.2f*ALTO+10);
        escenaMenu.addActor(btnAyuda);
        btnAyuda.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                //paramos la musica
                tonadaMenu.stop();
                juego.setScreen(new Help(juego));
            }
        });
        //botonCreditos
        TextureRegionDrawable trdCreditos = new TextureRegionDrawable(new TextureRegion(texturaBtnCredits));
        ImageButton btnCredits = new ImageButton(trdCreditos);
        btnCredits.setPosition(ANCHO - btnCredits.getWidth()-15/*ANCHO/2 - btnCredits.getWidth()/2*/, 0.2f*ALTO +10);
        escenaMenu.addActor(btnCredits);
        btnCredits.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                //paramos la musica
                tonadaMenu.stop();
                //juego.setScreen(new Credits(juego));
                // Iniciar juego Mario
                //juego.setScreen(new PantallaCargando(juego, Pantallas.NIVEL_WHACK_A_MOLE));
                //juego.setScreen((new ScreenFiveTwo(juego,1100,20)));

                //juego.setScreen(new ScreenEight(juego,640,64));
                //stage.addAction(Actions.fadeOut(10.0f));//pasa a negro fadeOut
                //juego.setScreen(new PantallaCargando(juego,Pantallas.FINAL));
                //juego.setScreen(new PantallaCargando(juego,Pantallas.FINAL));
                //escenaMenu.addAction(Actions.fadeIn(1));
                //juego.setScreen(new PantallaCargando(juego, Pantallas.RUNNER)); //screen gatos
                //juego.setScreen(new PantallaCargando(juego, Pantallas.ENDING)); //screen gatos
                juego.setScreen(new ScreenFourteen(juego,20,64));

                //juego.setScreen(new pantallaGlobos(juego));
            }
        });

        //botonMusica
        texturaBtnTemp=texturaBtnMusica;
        TextureRegionDrawable trdMusica=new TextureRegionDrawable(new TextureRegion(texturaBtnTemp));
        final ImageButton btnMusica= new ImageButton(trdMusica);
        btnMusica.setPosition(ANCHO/2-btnMusica.getWidth()/2,0.05f*ALTO);
        escenaMenu.addActor(btnMusica);
        btnMusica.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                tocado ++;
                texturaBtnTemp=texturaBtnMusicaX;
                //AQUI SE PARA LA MUSICA
                if(tocado%2!=0){
                    tonadaMenu.stop();
                }
                else{
                    tonadaMenu.play();
                    tonadaMenu.setLooping(true);
                }

            }
        });
    }

    private void cargarTexturas() {
        texturaBtnJugar = new Texture("NUEVOJUEGO.png");
        texturaBtnAyuda = new Texture("AyudaBtnMain.png");
        texturaBtnCredits = new Texture("CreditsBtnMain.png");
        texturaBackground = new Texture("Menu/MenuBNG1920.png");
        texturaBtnMusica=new Texture("Botones/Musica.png");
        texturaBtnMusicaX=new Texture("Botones/MusicaApagada.png");
        backTexAnim = new Texture("FondoMenu.png");
        //Sonido
        tonadaMenu= Gdx.audio.newMusic(Gdx.files.internal("Sonidos/lluvia.wav"));
        tonadaMenu.setVolume(1f);
        tonadaMenu.play();
        tonadaMenu.setLooping(true);

        TextureRegion texturaCompleta = new TextureRegion(backTexAnim);
        TextureRegion[][] texturaPersonaje = texturaCompleta.split(1280,720);
        spriteAnimadoBNG = new Animation(0.1f
                , texturaPersonaje[0][0], texturaPersonaje[0][1], texturaPersonaje[0][2]
                , texturaPersonaje[1][0], texturaPersonaje[1][1], texturaPersonaje[1][2]
                , texturaPersonaje[2][0], texturaPersonaje[2][1]);
        spriteAnimadoBNG.setPlayMode(Animation.PlayMode.LOOP);
        timerAnimacionBNG = 0;
        sprite = new Sprite(texturaPersonaje[0][0]);    // QUIETO
        sprite.setPosition(ANCHO/2,ALTO/2);    // Posición inicial
    }


    @Override
    public void render(float delta) {
        timerAnimacionBNG += Gdx.graphics.getDeltaTime();
        TextureRegion region = spriteAnimadoBNG.getKeyFrame(timerAnimacionBNG);
        borrarPantalla(0.8f,0.45f,0.2f);
        batch.setProjectionMatrix(camara.combined);

        escenaMenu.draw();
        batch.setProjectionMatrix(camara.combined);
        batch.begin();
        batch.draw(region,0,0);
        batch.end();
        escenaMenu.draw();
        stage.draw();

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    // Liberar los recursos asignados
    @Override
    public void dispose() {

    }

}