package com.mh.itesm;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Autor: Gerardo Ezequiel Magdaleno Hernandez
 * Autor: Jesus Heriberto Vasquez Sanchez A01377358.
 */


public class mainMenu extends Pantalla{
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


    public mainMenu(MHMain juego) {
        this.juego = juego;
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
        btnPlay.setPosition(ANCHO/2 - btnPlay.getWidth()/2,0.5f*ALTO);
        escenaMenu.addActor(btnPlay);
        btnPlay.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                //paramos la musica
                tonadaMenu.stop();
                juego.setScreen(new PantallaCargando(juego,Pantallas.PRIMER_NIVEL)); //Primer Nivel!!!!
            }
        });
        //boton AYUDA
        TextureRegionDrawable trdAyuda = new TextureRegionDrawable(new TextureRegion(texturaBtnAyuda));
        ImageButton btnAyuda = new ImageButton(trdAyuda);
        btnAyuda.setPosition(ANCHO/4 - btnAyuda.getWidth()-60, 0.2f*ALTO+10);
        escenaMenu.addActor(btnAyuda);
        btnAyuda.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                juego.setScreen(new Help(juego));
            }
        });
        //botonCreditos
        TextureRegionDrawable trdCreditos = new TextureRegionDrawable(new TextureRegion(texturaBtnCredits));
        ImageButton btnCredits = new ImageButton(trdCreditos);
        btnCredits.setPosition(ANCHO - btnCredits.getWidth()/*ANCHO/2 - btnCredits.getWidth()/2*/, 0.2f*ALTO +10);
        escenaMenu.addActor(btnCredits);
        btnCredits.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                juego.setScreen(new Credits(juego));
            }
        });

        //botonMusica
        texturaBtnTemp=texturaBtnMusica;
        TextureRegionDrawable trdMusica=new TextureRegionDrawable(new TextureRegion(texturaBtnTemp));
        final ImageButton btnMusica= new ImageButton(trdMusica);
        btnMusica.setPosition(ANCHO/2-btnMusica.getWidth()/2,0.3f*ALTO-40);
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
        texturaBtnAyuda = new Texture("fondoNew.png");
        texturaBtnCredits = new Texture("CREDITOS.png");
        texturaBackground = new Texture("Menu/MenuBNG1920.png");
        texturaBtnMusica=new Texture("Botones/Musica.png");
        texturaBtnMusicaX=new Texture("Botones/MusicaApagada.png");
        backTexAnim = new Texture("FondoMenu.png");
        //Sonido
        tonadaMenu=Gdx.audio.newMusic(Gdx.files.internal("Sonidos/lluvia.mp3"));
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