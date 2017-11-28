package com.mh.itesm;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;

/**
 * Autores: Jesús Heriberto Vásquez Sánchez A01377358
 * Autores: Gerardo Ezequiel Magdaleno Hernández
 */

class PantallaCargando extends Pantalla
{
    // Animación cargando
    private static final float TIEMPO_ENTRE_FRAMES = 0.05f;
    private Sprite spriteCargando;
    private float timerAnimacion = TIEMPO_ENTRE_FRAMES;

    // AssetManager
    private AssetManager manager;

    private MHMain juego;
    private Pantallas siguientePantalla;
    private int avance; // % de carga
    private Texto texto;

    private Texture texturaCargando;

    public PantallaCargando(MHMain juego, Pantallas siguientePantalla) {
        this.juego = juego;
        this.siguientePantalla = siguientePantalla;
    }

    @Override
    public void show() {
        texturaCargando = new Texture(Gdx.files.internal("cargando/loading.png"));
        spriteCargando = new Sprite(texturaCargando);
        spriteCargando.setPosition(ANCHO/2-spriteCargando.getWidth()/2,ALTO/2-spriteCargando.getHeight()/2);
        cargarRecursosSigPantalla();
        texto = new Texto("fuentes/fuente.fnt");
    }

    // Carga los recursos de la siguiente pantalla
    private void cargarRecursosSigPantalla() {
        manager = juego.getAssetManager();
        avance = 0;
        switch (siguientePantalla) {
            case MENU:
                cargarRecursosMenu();
                break;
            case NIVEL_WHACK_A_MOLE:
                cargarRecursosWhackAMole();
                break;
            case PRIMER_NIVEL:
                cargarRecursosPrimerNivel();
                break;
            case RUNNER:
                cargarRecursosRunner();
                break;
            case FINAL:
                cargarRecursosFinal();
                break;
            case ENDING:
                cargarRecursosENDING();
                break;

        }
    }

    private void cargarRecursosPrimerNivel() {
        manager.load("whackamole/btnSalir.png", Texture.class);
        manager.load("whackamole/btnContinuar.png", Texture.class);

        manager.load("Botones/CONTINUAR.png", Texture.class);
        manager.load("Botones/SALIR.png", Texture.class);
        manager.load("Botones/Musica.png",Texture.class);

        manager.load("Lienzo.png",Texture.class);
        manager.load("ScreenOne/fondotest.png",Texture.class);
    }

    private void cargarRecursosRunner() {
        manager.load("PuzzleGatos/ScreenGtos.tmx", TiledMap.class);
        //manager.load("runner/fondoRunnerD.jpg", Texture.class);
        manager.load("Characters/Steven/Atlas-StevenCaminandoFinal512.png", Texture.class);
        manager.load("PuzzleGatos/BoladePelo40x40.png", Texture.class);
        manager.load("PuzzleGatos/tuna.png", Texture.class);
        manager.load("comun/btnSalir.png", Texture.class);

    }
    private void cargarRecursosFinal() {
        manager.load("PuzzleFinal/persecucion.tmx", TiledMap.class);
        //manager.load("runner/fondoRunnerD.jpg", Texture.class);
        manager.load("UltimoNivel/Stevenvistaarriba.png", Texture.class);
        manager.load("UltimoNivel/PoliciaVistaArriba.png", Texture.class);
        manager.load("UltimoNivel/barrilito.png", Texture.class);
        manager.load("PuzzleGatos/tuna.png", Texture.class);
        manager.load("comun/btnSalir.png", Texture.class);
        manager.load("UltimoNivel/AutoPolicia.png", Texture.class);

    }

    private void cargarRecursosWhackAMole() {
        manager.load("whackamole/whackamoleFINAL/WhackAMoleFinalEscalados/fondoSopa.png", Texture.class);
        manager.load("whackamole/whackamoleFINAL/WhackAMoleFinalEscalados/holeTest.png", Texture.class);
        manager.load("whackamole/whackamoleFINAL/WhackAMoleFinalEscalados/mole.png", Texture.class);
        manager.load("whackamole/estrellasGolpe.png", Texture.class);
        manager.load("whackamole/mazo.png", Texture.class);
        manager.load("whackamole/golpe.mp3", Sound.class);
        manager.load("whackamole/risa.mp3", Sound.class);
        manager.load("comun/btnPausa.png", Texture.class);
        manager.load("whackamole/btnSalir.png", Texture.class);
        manager.load("whackamole/btnReintentar.png", Texture.class);
        manager.load("whackamole/btnContinuar.png", Texture.class);
    }

    private void cargarRecursosMario() {
        manager.load("mario/marioSprite.png", Texture.class);
        manager.load("mario/mapaMario.tmx", TiledMap.class);
        manager.load("mario/marioBros.mp3",Music.class);
        manager.load("mario/moneda.mp3",Sound.class);
        manager.load("mario/padBack.png", Texture.class);
        manager.load("mario/padKnob.png", Texture.class);
        manager.load("mario/jumpBtn.png", Texture.class);
        manager.load("mario/jumpBtnBajo.png", Texture.class);
        manager.load("comun/btnSalir.png", Texture.class);
        manager.load("comun/btnPausa.png", Texture.class);

    }


    private void cargarRecursosMenu() {
        manager.load("menu/btnJugarMario.png", Texture.class);
        manager.load("menu/btnJugarMarioP.png", Texture.class);
        manager.load("menu/btnJugarRunner.png", Texture.class);
        manager.load("menu/btnJugarRunnerP.png", Texture.class);
        manager.load("menu/btnJugarWhackAMole.png", Texture.class);
        manager.load("menu/btnJugarWhackAMoleP.png", Texture.class);
        manager.load("menu/fondoSinFin.jpg", Texture.class);
        manager.load("menu/fondoSinFin.jpg", Texture.class);
    }
    private  void cargarRecursosENDING(){
        manager.load("ScreenEnding/EscenaFinal1.png",Texture.class);
        manager.load("ScreenEnding/EscenaFinal2.png",Texture.class);
        manager.load("ScreenEnding/EscenaFinal3.png",Texture.class);
        manager.load("ScreenEnding/Credits.png",Texture.class);
        manager.load("ScreenEnding/EscenaFinal2One.png",Texture.class);
        manager.load("ScreenEnding/EscenaFinal2Two.png",Texture.class);
    }

    @Override
    public void render(float delta) {
        borrarPantalla(0.5f, 0.5f, 0.5f);
        batch.setProjectionMatrix(camara.combined);
        batch.begin();
        spriteCargando.draw(batch);
        texto.mostrarMensaje(batch,avance+" %",ANCHO/2,ALTO/2);
        batch.end();
        // Actualizar
        timerAnimacion -= delta;
        if (timerAnimacion<=0) {
            timerAnimacion = TIEMPO_ENTRE_FRAMES;
            spriteCargando.rotate(20);
        }
        // Actualizar carga
        actualizarCargaRecursos();
    }

    private void actualizarCargaRecursos() {
        if (manager.update()) { // Terminó?
            switch (siguientePantalla) {
                case MENU:
                    juego.setScreen(new mainMenu(juego));   // 100% de carga
                    break;
                case NIVEL_WHACK_A_MOLE:
                    juego.setScreen(new PantallaWhackAMole(juego));
                    break;
                case PRIMER_NIVEL:
                    juego.setScreen(new ScreenOne(juego));
                    break;
                case RUNNER:
                    juego.setScreen(new ScreenGatos(juego,0,0));
                    break;
                case FINAL:
                    juego.setScreen(new ScreenFinal(juego,100,100));
                    break;
                case ENDING:
                    juego.setScreen(new ScreenFinalEnding(juego));
            }
        }
        avance = (int)(manager.getProgress()*100);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        texturaCargando.dispose();
    }
}
