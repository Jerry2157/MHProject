package com.mh.itesm;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.Viewport;


/**
 * Autor: Jesus Heriberto Vasquez Sanchez A01377358.
 */
//Agregar botones de musica

public class EscenaPausa extends Stage {
    // La escena que se muestra cuando está pausado

    private AssetManager manager; //para manegar texturas y demas con manager
    private MHMain juego;
    private EstadoJuego estadoJuego;
    //botones para screen six y mayores
    private ImageButton btnContinuarD;
    private ImageButton btnSalirD;

    //Varible que lleva la cuenta del boton de musica para acivar y desactiva la musica
    private int tocado=0;



    public EscenaPausa(final Pantalla pantalla, final Controller controller, Viewport vista, SpriteBatch batch) {
        super(vista, batch);
        //Debemos cambiar para que pueda recibir cualquier screen
        //Se requiere aqui ya que si no entra no se crea
        if(pantalla instanceof ScreenOne){
            final ScreenOne currentS=((ScreenOne) pantalla);
            //Accedemos al estado juego de screenOne
            estadoJuego=currentS.getEstadoJuego();

            // Crear rectángulo transparente
            Pixmap pixmap = new Pixmap((int) (currentS.ANCHO/*currentS.ANCHO * 0.7f*/), (int) (currentS.ALTO /* 0.8f*/), Pixmap.Format.RGBA8888);
            pixmap.setColor(1f, 1f, 1f, 0.40f/*0.65f*/);
            pixmap.fillRectangle(0, 0, pixmap.getWidth(), pixmap.getHeight());
            Texture texturaRectangulo = new Texture( pixmap );
            pixmap.dispose();
            Image imgRectangulo = new Image(texturaRectangulo);
            imgRectangulo.setPosition(0,0/*0.15f*currentS.ANCHO, 0.1f*currentS.ALTO*/);
            this.addActor(imgRectangulo);

            // Salir
            Texture texturaBtnSalir =new Texture("Botones/SALIR.png");
            TextureRegionDrawable trdSalir = new TextureRegionDrawable(
                    new TextureRegion(texturaBtnSalir));
            ImageButton btnSalir = new ImageButton(trdSalir);
            btnSalir.setPosition(currentS.ANCHO/2-btnSalir.getWidth()/2, currentS.ALTO*0.2f);
            btnSalir.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    // Regresa al menú
                    //deten la musica que estaba de fondo
                    currentS.getSonidoF().stop();
                    currentS.getJuego().setScreen(new mainMenu(currentS.getJuego()));

                }
            });
            this.addActor(btnSalir);

            // Continuar
            Texture texturaBtnReintentar = new Texture("Botones/CONTINUAR.png");
            TextureRegionDrawable trdReintentar = new TextureRegionDrawable(
                    new TextureRegion(texturaBtnReintentar));
            ImageButton btnReintentar = new ImageButton(trdReintentar);
            btnReintentar.setPosition(currentS.ANCHO/2-btnReintentar.getWidth()/2, currentS.ALTO*0.5f);
            btnReintentar.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    // Regresa al juego
                    currentS.setEstadoJuego(EstadoJuego.JUGANDO);
                    Gdx.input.setInputProcessor(currentS.getController().getStage());
                }
            });
            this.addActor(btnReintentar);
            //falta entonces que se mantenga la referencia porque estamos editando en una variable lcoal

            // Musica
            Texture temp=new Texture("Botones/MusicaApagada.png");
            Texture texturaBtnMusica = new Texture("Botones/MusicaP.png");
            TextureRegionDrawable trdMusica = new TextureRegionDrawable(
                    new TextureRegion(texturaBtnMusica));
            ImageButton btnMusica = new ImageButton(trdMusica);
            btnMusica.setPosition(currentS.ANCHO/2-btnMusica.getWidth()/2, currentS.ALTO*0.5f-110);
            btnMusica.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    //AQUI DETENEMOS LA MUSICA
                    tocado ++;
                    if(tocado%2!=0){
                        currentS.getSonidoF().stop();
                    }
                    else{
                        currentS.getSonidoF().play();
                        currentS.getSonidoF().setLooping(true);
                    }
                }
            });
            this.addActor(btnMusica);
        }
        if(pantalla instanceof ScreenThree){
            final ScreenThree currentS=((ScreenThree) pantalla);
            //Accedemos al estado juego de screenOne
            estadoJuego=currentS.getEstadoJuego();

            // Crear rectángulo transparente
            Pixmap pixmap = new Pixmap((int) (currentS.ANCHO/*currentS.ANCHO * 0.7f*/), (int) (currentS.ALTO /* 0.8f*/), Pixmap.Format.RGBA8888);
            pixmap.setColor(1f, 1f, 1f, 0.40f/*0.65f*/);
            pixmap.fillRectangle(0, 0, pixmap.getWidth(), pixmap.getHeight());
            Texture texturaRectangulo = new Texture( pixmap );
            pixmap.dispose();
            Image imgRectangulo = new Image(texturaRectangulo);
            imgRectangulo.setPosition(0,0/*0.15f*currentS.ANCHO, 0.1f*currentS.ALTO*/);
            this.addActor(imgRectangulo);

            // Salir
            Texture texturaBtnSalir =new Texture("Botones/SALIR.png");
            TextureRegionDrawable trdSalir = new TextureRegionDrawable(
                    new TextureRegion(texturaBtnSalir));
            ImageButton btnSalir = new ImageButton(trdSalir);
            btnSalir.setPosition(currentS.ANCHO/2-btnSalir.getWidth()/2, currentS.ALTO*0.2f);
            btnSalir.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    // Regresa al menú
                    //deten la musica que estaba de fondo
                    //currentS.getSonidoF().stop();
                    currentS.getJuego().setScreen(new mainMenu(currentS.getJuego()));

                }
            });
            this.addActor(btnSalir);

            // Continuar
            Texture texturaBtnReintentar = new Texture("Botones/CONTINUAR.png");
            TextureRegionDrawable trdReintentar = new TextureRegionDrawable(
                    new TextureRegion(texturaBtnReintentar));
            ImageButton btnReintentar = new ImageButton(trdReintentar);
            btnReintentar.setPosition(currentS.ANCHO/2-btnReintentar.getWidth()/2, currentS.ALTO*0.5f);
            btnReintentar.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    // Regresa al juego
                    currentS.setEstadoJuego(EstadoJuego.JUGANDO);
                    Gdx.input.setInputProcessor(currentS.getController().getStage());
                }
            });
            this.addActor(btnReintentar);
            //falta entonces que se mantenga la referencia porque estamos editando en una variable lcoal

            // Musica
            /*Texture temp=new Texture("Botones/MusicaApagada.png");
            Texture texturaBtnMusica = new Texture("Botones/Musica.png");
            TextureRegionDrawable trdMusica = new TextureRegionDrawable(
                    new TextureRegion(texturaBtnMusica));
            ImageButton btnMusica = new ImageButton(trdMusica);
            btnMusica.setPosition(currentS.ANCHO/2-btnMusica.getWidth()/2, currentS.ALTO*0.5f-120);
            btnMusica.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    //AQUI DETENEMOS LA MUSICA
                    tocado ++;
                    if(tocado%2!=0){
                        currentS.getSonidoF().stop();
                    }
                    else{
                        currentS.getSonidoF().play();
                        currentS.getSonidoF().setLooping(true);
                    }
                }
            });
            this.addActor(btnMusica);*/

        }

        if(pantalla instanceof ScreenFour){
            final ScreenFour currentS=((ScreenFour) pantalla);
            //Accedemos al estado juego de screenOne
            estadoJuego=currentS.getEstadoJuego();

            // Crear rectángulo transparente
            Pixmap pixmap = new Pixmap((int) (currentS.ANCHO/*currentS.ANCHO * 0.7f*/), (int) (currentS.ALTO /* 0.8f*/), Pixmap.Format.RGBA8888);
            pixmap.setColor(1f, 1f, 1f, 0.40f/*0.65f*/);
            pixmap.fillRectangle(0, 0, pixmap.getWidth(), pixmap.getHeight());
            Texture texturaRectangulo = new Texture( pixmap );
            pixmap.dispose();
            Image imgRectangulo = new Image(texturaRectangulo);
            imgRectangulo.setPosition(0,0/*0.15f*currentS.ANCHO, 0.1f*currentS.ALTO*/);
            this.addActor(imgRectangulo);

            // Salir
            Texture texturaBtnSalir =new Texture("Botones/SALIR.png");
            TextureRegionDrawable trdSalir = new TextureRegionDrawable(
                    new TextureRegion(texturaBtnSalir));
            ImageButton btnSalir = new ImageButton(trdSalir);
            btnSalir.setPosition(currentS.ANCHO/2-btnSalir.getWidth()/2, currentS.ALTO*0.2f);
            btnSalir.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    // Regresa al menú
                    //deten la musica que estaba de fondo
                    //currentS.getSonidoF().stop();
                    currentS.getJuego().setScreen(new mainMenu(currentS.getJuego()));

                }
            });
            this.addActor(btnSalir);

            // Continuar
            Texture texturaBtnReintentar = new Texture("Botones/CONTINUAR.png");
            TextureRegionDrawable trdReintentar = new TextureRegionDrawable(
                    new TextureRegion(texturaBtnReintentar));
            ImageButton btnReintentar = new ImageButton(trdReintentar);
            btnReintentar.setPosition(currentS.ANCHO/2-btnReintentar.getWidth()/2, currentS.ALTO*0.5f);
            btnReintentar.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    // Regresa al juego
                    currentS.setEstadoJuego(EstadoJuego.JUGANDO);
                    Gdx.input.setInputProcessor(currentS.getController().getStage());
                }
            });
            this.addActor(btnReintentar);
            //falta entonces que se mantenga la referencia porque estamos editando en una variable lcoal

            // Musica
            /*Texture temp=new Texture("Botones/MusicaApagada.png");
            Texture texturaBtnMusica = new Texture("Botones/Musica.png");
            TextureRegionDrawable trdMusica = new TextureRegionDrawable(
                    new TextureRegion(texturaBtnMusica));
            ImageButton btnMusica = new ImageButton(trdMusica);
            btnMusica.setPosition(currentS.ANCHO/2-btnMusica.getWidth()/2, currentS.ALTO*0.5f-120);
            btnMusica.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    //AQUI DETENEMOS LA MUSICA
                    tocado ++;
                    if(tocado%2!=0){
                        currentS.getSonidoF().stop();
                    }
                    else{
                        currentS.getSonidoF().play();
                        currentS.getSonidoF().setLooping(true);
                    }
                }
            });
            this.addActor(btnMusica);*/
        }
        if(pantalla instanceof ScreenFive){
            final ScreenFive currentS=((ScreenFive) pantalla);
            //Accedemos al estado juego de screenOne
            estadoJuego=currentS.getEstadoJuego();

            // Crear rectángulo transparente
            Pixmap pixmap = new Pixmap((int) (currentS.ANCHO/*currentS.ANCHO * 0.7f*/), (int) (currentS.ALTO /* 0.8f*/), Pixmap.Format.RGBA8888);
            pixmap.setColor(1f, 1f, 1f, 0.40f/*0.65f*/);
            pixmap.fillRectangle(0, 0, pixmap.getWidth(), pixmap.getHeight());
            Texture texturaRectangulo = new Texture( pixmap );
            pixmap.dispose();
            Image imgRectangulo = new Image(texturaRectangulo);
            imgRectangulo.setPosition(0,0/*0.15f*currentS.ANCHO, 0.1f*currentS.ALTO*/);
            this.addActor(imgRectangulo);

            // Salir
            Texture texturaBtnSalir =new Texture("Botones/SALIR.png");
            TextureRegionDrawable trdSalir = new TextureRegionDrawable(
                    new TextureRegion(texturaBtnSalir));
            ImageButton btnSalir = new ImageButton(trdSalir);
            btnSalir.setPosition(currentS.ANCHO/2-btnSalir.getWidth()/2, currentS.ALTO*0.2f);
            btnSalir.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    // Regresa al menú
                    //deten la musica que estaba de fondo
                    //currentS.getSonidoF().stop();
                    currentS.getJuego().setScreen(new mainMenu(currentS.getJuego()));

                }
            });
            this.addActor(btnSalir);

            // Continuar
            Texture texturaBtnReintentar = new Texture("Botones/CONTINUAR.png");
            TextureRegionDrawable trdReintentar = new TextureRegionDrawable(
                    new TextureRegion(texturaBtnReintentar));
            ImageButton btnReintentar = new ImageButton(trdReintentar);
            btnReintentar.setPosition(currentS.ANCHO/2-btnReintentar.getWidth()/2, currentS.ALTO*0.5f);
            btnReintentar.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    // Regresa al juego
                    currentS.setEstadoJuego(EstadoJuego.JUGANDO);
                    Gdx.input.setInputProcessor(currentS.getController().getStage());
                }
            });
            this.addActor(btnReintentar);
            //falta entonces que se mantenga la referencia porque estamos editando en una variable lcoal

            // Musica
            /*Texture temp=new Texture("Botones/MusicaApagada.png");
            Texture texturaBtnMusica = new Texture("Botones/Musica.png");
            TextureRegionDrawable trdMusica = new TextureRegionDrawable(
                    new TextureRegion(texturaBtnMusica));
            ImageButton btnMusica = new ImageButton(trdMusica);
            btnMusica.setPosition(currentS.ANCHO/2-btnMusica.getWidth()/2, currentS.ALTO*0.5f-120);
            btnMusica.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    //AQUI DETENEMOS LA MUSICA
                    tocado ++;
                    if(tocado%2!=0){
                        currentS.getSonidoF().stop();
                    }
                    else{
                        currentS.getSonidoF().play();
                        currentS.getSonidoF().setLooping(true);
                    }
                }
            });
            this.addActor(btnMusica);*/
        }
        if(pantalla instanceof ScreenFiveTwo){
            final ScreenFiveTwo currentS=((ScreenFiveTwo) pantalla);
            //Accedemos al estado juego de screenOne
            estadoJuego=currentS.getEstadoJuego();

            // Crear rectángulo transparente
            Pixmap pixmap = new Pixmap((int) (currentS.ANCHO/*currentS.ANCHO * 0.7f*/), (int) (currentS.ALTO /* 0.8f*/), Pixmap.Format.RGBA8888);
            pixmap.setColor(1f, 1f, 1f, 0.40f/*0.65f*/);
            pixmap.fillRectangle(0, 0, pixmap.getWidth(), pixmap.getHeight());
            Texture texturaRectangulo = new Texture( pixmap );
            pixmap.dispose();
            Image imgRectangulo = new Image(texturaRectangulo);
            imgRectangulo.setPosition(0,0/*0.15f*currentS.ANCHO, 0.1f*currentS.ALTO*/);
            this.addActor(imgRectangulo);

            // Salir
            Texture texturaBtnSalir =new Texture("Botones/SALIR.png");
            TextureRegionDrawable trdSalir = new TextureRegionDrawable(
                    new TextureRegion(texturaBtnSalir));
            ImageButton btnSalir = new ImageButton(trdSalir);
            btnSalir.setPosition(currentS.ANCHO/2-btnSalir.getWidth()/2, currentS.ALTO*0.2f);
            btnSalir.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    // Regresa al menú
                    //deten la musica que estaba de fondo
                    //currentS.getSonidoF().stop();
                    currentS.getJuego().setScreen(new mainMenu(currentS.getJuego()));

                }
            });
            this.addActor(btnSalir);

            // Continuar
            Texture texturaBtnReintentar = new Texture("Botones/CONTINUAR.png");
            TextureRegionDrawable trdReintentar = new TextureRegionDrawable(
                    new TextureRegion(texturaBtnReintentar));
            ImageButton btnReintentar = new ImageButton(trdReintentar);
            btnReintentar.setPosition(currentS.ANCHO/2-btnReintentar.getWidth()/2, currentS.ALTO*0.5f);
            btnReintentar.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    // Regresa al juego
                    currentS.setEstadoJuego(EstadoJuego.JUGANDO);
                    Gdx.input.setInputProcessor(currentS.getController().getStage());
                }
            });
            this.addActor(btnReintentar);
            //falta entonces que se mantenga la referencia porque estamos editando en una variable lcoal

            // Musica
            /*Texture temp=new Texture("Botones/MusicaApagada.png");
            Texture texturaBtnMusica = new Texture("Botones/Musica.png");
            TextureRegionDrawable trdMusica = new TextureRegionDrawable(
                    new TextureRegion(texturaBtnMusica));
            ImageButton btnMusica = new ImageButton(trdMusica);
            btnMusica.setPosition(currentS.ANCHO/2-btnMusica.getWidth()/2, currentS.ALTO*0.5f-120);
            btnMusica.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    //AQUI DETENEMOS LA MUSICA
                    tocado ++;
                    if(tocado%2!=0){
                        currentS.getSonidoF().stop();
                    }
                    else{
                        currentS.getSonidoF().play();
                        currentS.getSonidoF().setLooping(true);
                    }
                }
            });
            this.addActor(btnMusica);*/
        }
        if(pantalla instanceof ScreenSix){
            final ScreenSix currentS=((ScreenSix) pantalla);
            //Accedemos al estado juego de screenOne
            estadoJuego=currentS.getEstadoJuego();

            // Crear rectángulo transparente
            Pixmap pixmap = new Pixmap((int) (3840/*currentS.ANCHO * 0.7f*/), (int) (720/* 0.8f*/), Pixmap.Format.RGBA8888);
            pixmap.setColor(1f, 1f, 1f, 0.40f/*0.65f*/);
            pixmap.fillRectangle(0, 0, pixmap.getWidth(), pixmap.getHeight());
            Texture texturaRectangulo = new Texture( pixmap );
            pixmap.dispose();
            Image imgRectangulo = new Image(texturaRectangulo);
            imgRectangulo.setPosition(0,0/*0.15f*currentS.ANCHO, 0.1f*currentS.ALTO*/);
            this.addActor(imgRectangulo);

            // Salir
            Texture texturaBtnSalir =new Texture("Botones/SALIR.png");
            TextureRegionDrawable trdSalir = new TextureRegionDrawable(
                    new TextureRegion(texturaBtnSalir));
            btnSalirD = new ImageButton(trdSalir);
            btnSalirD.setPosition(currentS.ANCHO/2-btnSalirD.getWidth()/2, currentS.ALTO*0.2f);
            btnSalirD.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    // Regresa al menú
                    //deten la musica que estaba de fondo
                    //currentS.getSonidoF().stop();
                    currentS.getJuego().setScreen(new mainMenu(currentS.getJuego()));

                }
            });
            this.addActor(btnSalirD);

            // Continuar
            Texture texturaBtnReintentar = new Texture("Botones/CONTINUAR.png");
            TextureRegionDrawable trdReintentar = new TextureRegionDrawable(
                    new TextureRegion(texturaBtnReintentar));
            btnContinuarD = new ImageButton(trdReintentar);
            btnContinuarD.setPosition(currentS.ANCHO/2-btnContinuarD.getWidth()/2, currentS.ALTO*0.5f);
            btnContinuarD.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    // Regresa al juego
                    currentS.setEstadoJuego(EstadoJuego.JUGANDO);
                    Gdx.input.setInputProcessor(currentS.getController().getStage());
                }
            });
            this.addActor(btnContinuarD);
            //falta entonces que se mantenga la referencia porque estamos editando en una variable lcoal

            // Musica
            /*Texture temp=new Texture("Botones/MusicaApagada.png");
            Texture texturaBtnMusica = new Texture("Botones/Musica.png");
            TextureRegionDrawable trdMusica = new TextureRegionDrawable(
                    new TextureRegion(texturaBtnMusica));
            ImageButton btnMusica = new ImageButton(trdMusica);
            btnMusica.setPosition(currentS.ANCHO/2-btnMusica.getWidth()/2, currentS.ALTO*0.5f-120);
            btnMusica.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    //AQUI DETENEMOS LA MUSICA
                    tocado ++;
                    if(tocado%2!=0){
                        currentS.getSonidoF().stop();
                    }
                    else{
                        currentS.getSonidoF().play();
                        currentS.getSonidoF().setLooping(true);
                    }
                }
            });
            this.addActor(btnMusica);*/
        }
        if(pantalla instanceof ScreenSeven){
            final ScreenSeven currentS=((ScreenSeven) pantalla);
            //Accedemos al estado juego de screenOne
            estadoJuego=currentS.getEstadoJuego();

            // Crear rectángulo transparente
            Pixmap pixmap = new Pixmap((int) (currentS.ANCHO/*currentS.ANCHO * 0.7f*/), (int) (currentS.ALTO /* 0.8f*/), Pixmap.Format.RGBA8888);
            pixmap.setColor(1f, 1f, 1f, 0.40f/*0.65f*/);
            pixmap.fillRectangle(0, 0, pixmap.getWidth(), pixmap.getHeight());
            Texture texturaRectangulo = new Texture( pixmap );
            pixmap.dispose();
            Image imgRectangulo = new Image(texturaRectangulo);
            imgRectangulo.setPosition(0,0/*0.15f*currentS.ANCHO, 0.1f*currentS.ALTO*/);
            this.addActor(imgRectangulo);

            // Salir
            Texture texturaBtnSalir =new Texture("Botones/SALIR.png");
            TextureRegionDrawable trdSalir = new TextureRegionDrawable(
                    new TextureRegion(texturaBtnSalir));
            ImageButton btnSalir = new ImageButton(trdSalir);
            btnSalir.setPosition(currentS.ANCHO/2-btnSalir.getWidth()/2, currentS.ALTO*0.2f);
            btnSalir.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    // Regresa al menú
                    //deten la musica que estaba de fondo
                    //currentS.getSonidoF().stop();
                    currentS.getJuego().setScreen(new mainMenu(currentS.getJuego()));

                }
            });
            this.addActor(btnSalir);

            // Continuar
            Texture texturaBtnReintentar = new Texture("Botones/CONTINUAR.png");
            TextureRegionDrawable trdReintentar = new TextureRegionDrawable(
                    new TextureRegion(texturaBtnReintentar));
            ImageButton btnReintentar = new ImageButton(trdReintentar);
            btnReintentar.setPosition(currentS.ANCHO/2-btnReintentar.getWidth()/2, currentS.ALTO*0.5f);
            btnReintentar.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    // Regresa al juego
                    currentS.setEstadoJuego(EstadoJuego.JUGANDO);
                    Gdx.input.setInputProcessor(currentS.getController().getStage());
                }
            });
            this.addActor(btnReintentar);
            //falta entonces que se mantenga la referencia porque estamos editando en una variable lcoal

            // Musica
            /*Texture temp=new Texture("Botones/MusicaApagada.png");
            Texture texturaBtnMusica = new Texture("Botones/Musica.png");
            TextureRegionDrawable trdMusica = new TextureRegionDrawable(
                    new TextureRegion(texturaBtnMusica));
            ImageButton btnMusica = new ImageButton(trdMusica);
            btnMusica.setPosition(currentS.ANCHO/2-btnMusica.getWidth()/2, currentS.ALTO*0.5f-120);
            btnMusica.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    //AQUI DETENEMOS LA MUSICA
                    tocado ++;
                    if(tocado%2!=0){
                        currentS.getSonidoF().stop();
                    }
                    else{
                        currentS.getSonidoF().play();
                        currentS.getSonidoF().setLooping(true);
                    }
                }
            });
            this.addActor(btnMusica);*/

        }
        if(pantalla instanceof ScreenEight){
            final ScreenEight currentS=((ScreenEight) pantalla);
            //Accedemos al estado juego de screenOne
            estadoJuego=currentS.getEstadoJuego();

            // Crear rectángulo transparente
            Pixmap pixmap = new Pixmap((int) (currentS.ANCHO/*currentS.ANCHO * 0.7f*/), (int) (currentS.ALTO /* 0.8f*/), Pixmap.Format.RGBA8888);
            pixmap.setColor(1f, 1f, 1f, 0.40f/*0.65f*/);
            pixmap.fillRectangle(0, 0, pixmap.getWidth(), pixmap.getHeight());
            Texture texturaRectangulo = new Texture( pixmap );
            pixmap.dispose();
            Image imgRectangulo = new Image(texturaRectangulo);
            imgRectangulo.setPosition(0,0/*0.15f*currentS.ANCHO, 0.1f*currentS.ALTO*/);
            this.addActor(imgRectangulo);

            // Salir
            Texture texturaBtnSalir =new Texture("Botones/SALIR.png");
            TextureRegionDrawable trdSalir = new TextureRegionDrawable(
                    new TextureRegion(texturaBtnSalir));
            ImageButton btnSalir = new ImageButton(trdSalir);
            btnSalir.setPosition(currentS.ANCHO/2-btnSalir.getWidth()/2, currentS.ALTO*0.2f);
            btnSalir.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    // Regresa al menú
                    //deten la musica que estaba de fondo
                    //currentS.getSonidoF().stop();
                    currentS.getJuego().setScreen(new mainMenu(currentS.getJuego()));

                }
            });
            this.addActor(btnSalir);

            // Continuar
            Texture texturaBtnReintentar = new Texture("Botones/CONTINUAR.png");
            TextureRegionDrawable trdReintentar = new TextureRegionDrawable(
                    new TextureRegion(texturaBtnReintentar));
            ImageButton btnReintentar = new ImageButton(trdReintentar);
            btnReintentar.setPosition(currentS.ANCHO/2-btnReintentar.getWidth()/2, currentS.ALTO*0.5f);
            btnReintentar.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    // Regresa al juego
                    currentS.setEstadoJuego(EstadoJuego.JUGANDO);
                    Gdx.input.setInputProcessor(currentS.getController().getStage());
                }
            });
            this.addActor(btnReintentar);
            //falta entonces que se mantenga la referencia porque estamos editando en una variable lcoal

            // Musica
            /*Texture temp=new Texture("Botones/MusicaApagada.png");
            Texture texturaBtnMusica = new Texture("Botones/Musica.png");
            TextureRegionDrawable trdMusica = new TextureRegionDrawable(
                    new TextureRegion(texturaBtnMusica));
            ImageButton btnMusica = new ImageButton(trdMusica);
            btnMusica.setPosition(currentS.ANCHO/2-btnMusica.getWidth()/2, currentS.ALTO*0.5f-120);
            btnMusica.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    //AQUI DETENEMOS LA MUSICA
                    tocado ++;
                    if(tocado%2!=0){
                        currentS.getSonidoF().stop();
                    }
                    else{
                        currentS.getSonidoF().play();
                        currentS.getSonidoF().setLooping(true);
                    }
                }
            });
            this.addActor(btnMusica);*/
        }
        if(pantalla instanceof ScreenNine){
            final ScreenNine currentS=((ScreenNine) pantalla);
            //Accedemos al estado juego de screenOne
            estadoJuego=currentS.getEstadoJuego();

            // Crear rectángulo transparente
            Pixmap pixmap = new Pixmap((int) (2560/*currentS.ANCHO * 0.7f*/), (int) (720 /* 0.8f*/), Pixmap.Format.RGBA8888);
            pixmap.setColor(1f, 1f, 1f, 0.40f/*0.65f*/);
            pixmap.fillRectangle(0, 0, pixmap.getWidth(), pixmap.getHeight());
            Texture texturaRectangulo = new Texture( pixmap );
            pixmap.dispose();
            Image imgRectangulo = new Image(texturaRectangulo);
            imgRectangulo.setPosition(0,0/*0.15f*currentS.ANCHO, 0.1f*currentS.ALTO*/);
            this.addActor(imgRectangulo);

            // Salir
            Texture texturaBtnSalir =new Texture("Botones/SALIR.png");
            TextureRegionDrawable trdSalir = new TextureRegionDrawable(
                    new TextureRegion(texturaBtnSalir));
            btnSalirD = new ImageButton(trdSalir);
            btnSalirD.setPosition(1870, currentS.ALTO*0.2f);
            btnSalirD.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    // Regresa al menú
                    //deten la musica que estaba de fondo
                    //currentS.getSonidoF().stop();
                    currentS.getJuego().setScreen(new mainMenu(currentS.getJuego()));

                }
            });
            this.addActor(btnSalirD);

            // Continuar
            Texture texturaBtnReintentar = new Texture("Botones/CONTINUAR.png");
            TextureRegionDrawable trdReintentar = new TextureRegionDrawable(
                    new TextureRegion(texturaBtnReintentar));
            btnContinuarD = new ImageButton(trdReintentar);
            btnContinuarD.setPosition(1800, currentS.ALTO*0.5f);
            btnContinuarD.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    // Regresa al juego
                    currentS.setEstadoJuego(EstadoJuego.JUGANDO);
                    Gdx.input.setInputProcessor(currentS.getController().getStage());
                }
            });
            this.addActor(btnContinuarD);
            //falta entonces que se mantenga la referencia porque estamos editando en una variable lcoal

            // Musica
            /*Texture temp=new Texture("Botones/MusicaApagada.png");
            Texture texturaBtnMusica = new Texture("Botones/Musica.png");
            TextureRegionDrawable trdMusica = new TextureRegionDrawable(
                    new TextureRegion(texturaBtnMusica));
            ImageButton btnMusica = new ImageButton(trdMusica);
            btnMusica.setPosition(currentS.ANCHO/2-btnMusica.getWidth()/2, currentS.ALTO*0.5f-120);
            btnMusica.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    //AQUI DETENEMOS LA MUSICA
                    tocado ++;
                    if(tocado%2!=0){
                        currentS.getSonidoF().stop();
                    }
                    else{
                        currentS.getSonidoF().play();
                        currentS.getSonidoF().setLooping(true);
                    }
                }
            });
            this.addActor(btnMusica);*/
        }
        if(pantalla instanceof ScreenTen){
            final ScreenTen currentS=((ScreenTen) pantalla);
            //Accedemos al estado juego de screenOne
            estadoJuego=currentS.getEstadoJuego();

            // Crear rectángulo transparente
            Pixmap pixmap = new Pixmap((int) (3840/*currentS.ANCHO * 0.7f*/), (int) (720 /* 0.8f*/), Pixmap.Format.RGBA8888);
            pixmap.setColor(1f, 1f, 1f, 0.40f/*0.65f*/);
            pixmap.fillRectangle(0, 0, pixmap.getWidth(), pixmap.getHeight());
            Texture texturaRectangulo = new Texture( pixmap );
            pixmap.dispose();
            Image imgRectangulo = new Image(texturaRectangulo);
            imgRectangulo.setPosition(0,0/*0.15f*currentS.ANCHO, 0.1f*currentS.ALTO*/);
            this.addActor(imgRectangulo);

            // Salir
            Texture texturaBtnSalir =new Texture("Botones/SALIR.png");
            TextureRegionDrawable trdSalir = new TextureRegionDrawable(
                    new TextureRegion(texturaBtnSalir));
            btnSalirD = new ImageButton(trdSalir);
            btnSalirD.setPosition(currentS.ANCHO/2-btnSalirD.getWidth()/2, currentS.ALTO*0.2f);
            btnSalirD.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    // Regresa al menú
                    //deten la musica que estaba de fondo
                    //currentS.getSonidoF().stop();
                    currentS.getJuego().setScreen(new mainMenu(currentS.getJuego()));

                }
            });
            this.addActor(btnSalirD);

            // Continuar
            Texture texturaBtnReintentar = new Texture("Botones/CONTINUAR.png");
            TextureRegionDrawable trdReintentar = new TextureRegionDrawable(
                    new TextureRegion(texturaBtnReintentar));
            btnContinuarD = new ImageButton(trdReintentar);
            btnContinuarD.setPosition(currentS.ANCHO/2-btnContinuarD.getWidth()/2, currentS.ALTO*0.5f);
            btnContinuarD.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    // Regresa al juego
                    currentS.setEstadoJuego(EstadoJuego.JUGANDO);
                    Gdx.input.setInputProcessor(currentS.getController().getStage());
                }
            });
            this.addActor(btnContinuarD);
            //falta entonces que se mantenga la referencia porque estamos editando en una variable lcoal

            // Musica
            /*Texture temp=new Texture("Botones/MusicaApagada.png");
            Texture texturaBtnMusica = new Texture("Botones/Musica.png");
            TextureRegionDrawable trdMusica = new TextureRegionDrawable(
                    new TextureRegion(texturaBtnMusica));
            ImageButton btnMusica = new ImageButton(trdMusica);
            btnMusica.setPosition(currentS.ANCHO/2-btnMusica.getWidth()/2, currentS.ALTO*0.5f-120);
            btnMusica.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    //AQUI DETENEMOS LA MUSICA
                    tocado ++;
                    if(tocado%2!=0){
                        currentS.getSonidoF().stop();
                    }
                    else{
                        currentS.getSonidoF().play();
                        currentS.getSonidoF().setLooping(true);
                    }
                }
            });
            this.addActor(btnMusica);*/
        }
        if(pantalla instanceof ScreenEleven){
            final ScreenEleven currentS=((ScreenEleven) pantalla);
            //Accedemos al estado juego de screenOne
            estadoJuego=currentS.getEstadoJuego();

            // Crear rectángulo transparente
            Pixmap pixmap = new Pixmap((int) (3840/*currentS.ANCHO * 0.7f*/), (int) (720 /* 0.8f*/), Pixmap.Format.RGBA8888);
            pixmap.setColor(1f, 1f, 1f, 0.40f/*0.65f*/);
            pixmap.fillRectangle(0, 0, pixmap.getWidth(), pixmap.getHeight());
            Texture texturaRectangulo = new Texture( pixmap );
            pixmap.dispose();
            Image imgRectangulo = new Image(texturaRectangulo);
            imgRectangulo.setPosition(0,0/*0.15f*currentS.ANCHO, 0.1f*currentS.ALTO*/);
            this.addActor(imgRectangulo);

            // Salir
            Texture texturaBtnSalir =new Texture("Botones/SALIR.png");
            TextureRegionDrawable trdSalir = new TextureRegionDrawable(
                    new TextureRegion(texturaBtnSalir));
            btnSalirD = new ImageButton(trdSalir);
            btnSalirD.setPosition(currentS.ANCHO/2-btnSalirD.getWidth()/2, currentS.ALTO*0.2f);
            btnSalirD.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    // Regresa al menú
                    //deten la musica que estaba de fondo
                    //currentS.getSonidoF().stop();
                    currentS.getJuego().setScreen(new mainMenu(currentS.getJuego()));

                }
            });
            this.addActor(btnSalirD);

            // Continuar
            Texture texturaBtnReintentar = new Texture("Botones/CONTINUAR.png");
            TextureRegionDrawable trdReintentar = new TextureRegionDrawable(
                    new TextureRegion(texturaBtnReintentar));
            btnContinuarD = new ImageButton(trdReintentar);
            btnContinuarD.setPosition(currentS.ANCHO/2-btnContinuarD.getWidth()/2, currentS.ALTO*0.5f);
            btnContinuarD.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    // Regresa al juego
                    currentS.setEstadoJuego(EstadoJuego.JUGANDO);
                    Gdx.input.setInputProcessor(currentS.getController().getStage());
                }
            });
            this.addActor(btnContinuarD);
            //falta entonces que se mantenga la referencia porque estamos editando en una variable lcoal

            // Musica
            /*Texture temp=new Texture("Botones/MusicaApagada.png");
            Texture texturaBtnMusica = new Texture("Botones/Musica.png");
            TextureRegionDrawable trdMusica = new TextureRegionDrawable(
                    new TextureRegion(texturaBtnMusica));
            ImageButton btnMusica = new ImageButton(trdMusica);
            btnMusica.setPosition(currentS.ANCHO/2-btnMusica.getWidth()/2, currentS.ALTO*0.5f-120);
            btnMusica.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    //AQUI DETENEMOS LA MUSICA
                    tocado ++;
                    if(tocado%2!=0){
                        currentS.getSonidoF().stop();
                    }
                    else{
                        currentS.getSonidoF().play();
                        currentS.getSonidoF().setLooping(true);
                    }
                }
            });
            this.addActor(btnMusica);*/
        }
        if(pantalla instanceof ScreenTwelve){
            final ScreenTwelve currentS=((ScreenTwelve) pantalla);
            //Accedemos al estado juego de screenOne
            estadoJuego=currentS.getEstadoJuego();

            // Crear rectángulo transparente
            Pixmap pixmap = new Pixmap((int) (3840/*currentS.ANCHO * 0.7f*/), (int) (720 /* 0.8f*/), Pixmap.Format.RGBA8888);
            pixmap.setColor(1f, 1f, 1f, 0.40f/*0.65f*/);
            pixmap.fillRectangle(0, 0, pixmap.getWidth(), pixmap.getHeight());
            Texture texturaRectangulo = new Texture( pixmap );
            pixmap.dispose();
            Image imgRectangulo = new Image(texturaRectangulo);
            imgRectangulo.setPosition(0,0/*0.15f*currentS.ANCHO, 0.1f*currentS.ALTO*/);
            this.addActor(imgRectangulo);

            // Salir
            Texture texturaBtnSalir =new Texture("Botones/SALIR.png");
            TextureRegionDrawable trdSalir = new TextureRegionDrawable(
                    new TextureRegion(texturaBtnSalir));
            btnSalirD = new ImageButton(trdSalir);
            btnSalirD.setPosition(1870, currentS.ALTO*0.2f);
            btnSalirD.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    // Regresa al menú
                    //deten la musica que estaba de fondo
                    //currentS.getSonidoF().stop();
                    currentS.getJuego().setScreen(new mainMenu(currentS.getJuego()));

                }
            });
            this.addActor(btnSalirD);

            // Continuar
            Texture texturaBtnReintentar = new Texture("Botones/CONTINUAR.png");
            TextureRegionDrawable trdReintentar = new TextureRegionDrawable(
                    new TextureRegion(texturaBtnReintentar));
            btnContinuarD = new ImageButton(trdReintentar);
            btnContinuarD.setPosition(1800, currentS.ALTO*0.5f);
            btnContinuarD.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    // Regresa al juego
                    currentS.setEstadoJuego(EstadoJuego.JUGANDO);
                    Gdx.input.setInputProcessor(currentS.getController().getStage());
                }
            });
            this.addActor(btnContinuarD);
            //falta entonces que se mantenga la referencia porque estamos editando en una variable lcoal

            // Musica
            /*Texture temp=new Texture("Botones/MusicaApagada.png");
            Texture texturaBtnMusica = new Texture("Botones/Musica.png");
            TextureRegionDrawable trdMusica = new TextureRegionDrawable(
                    new TextureRegion(texturaBtnMusica));
            ImageButton btnMusica = new ImageButton(trdMusica);
            btnMusica.setPosition(currentS.ANCHO/2-btnMusica.getWidth()/2, currentS.ALTO*0.5f-120);
            btnMusica.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    //AQUI DETENEMOS LA MUSICA
                    tocado ++;
                    if(tocado%2!=0){
                        currentS.getSonidoF().stop();
                    }
                    else{
                        currentS.getSonidoF().play();
                        currentS.getSonidoF().setLooping(true);
                    }
                }
            });
            this.addActor(btnMusica);*/
        }
        if(pantalla instanceof ScreenThirteen){
            final ScreenThirteen currentS=((ScreenThirteen) pantalla);
            //Accedemos al estado juego de screenOne
            estadoJuego=currentS.getEstadoJuego();

            // Crear rectángulo transparente
            Pixmap pixmap = new Pixmap((int) (currentS.ANCHO/*currentS.ANCHO * 0.7f*/), (int) (currentS.ALTO /* 0.8f*/), Pixmap.Format.RGBA8888);
            pixmap.setColor(1f, 1f, 1f, 0.40f/*0.65f*/);
            pixmap.fillRectangle(0, 0, pixmap.getWidth(), pixmap.getHeight());
            Texture texturaRectangulo = new Texture( pixmap );
            pixmap.dispose();
            Image imgRectangulo = new Image(texturaRectangulo);
            imgRectangulo.setPosition(0,0/*0.15f*currentS.ANCHO, 0.1f*currentS.ALTO*/);
            this.addActor(imgRectangulo);

            // Salir
            Texture texturaBtnSalir =new Texture("Botones/SALIR.png");
            TextureRegionDrawable trdSalir = new TextureRegionDrawable(
                    new TextureRegion(texturaBtnSalir));
            ImageButton btnSalir = new ImageButton(trdSalir);
            btnSalir.setPosition(currentS.ANCHO/2-btnSalir.getWidth()/2, currentS.ALTO*0.2f);
            btnSalir.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    // Regresa al menú
                    //deten la musica que estaba de fondo
                    //currentS.getSonidoF().stop();
                    currentS.getJuego().setScreen(new mainMenu(currentS.getJuego()));

                }
            });
            this.addActor(btnSalir);

            // Continuar
            Texture texturaBtnReintentar = new Texture("Botones/CONTINUAR.png");
            TextureRegionDrawable trdReintentar = new TextureRegionDrawable(
                    new TextureRegion(texturaBtnReintentar));
            ImageButton btnReintentar = new ImageButton(trdReintentar);
            btnReintentar.setPosition(currentS.ANCHO/2-btnReintentar.getWidth()/2, currentS.ALTO*0.5f);
            btnReintentar.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    // Regresa al juego
                    currentS.setEstadoJuego(EstadoJuego.JUGANDO);
                    Gdx.input.setInputProcessor(currentS.getController().getStage());
                }
            });
            this.addActor(btnReintentar);
            //falta entonces que se mantenga la referencia porque estamos editando en una variable lcoal

            // Musica
            /*Texture temp=new Texture("Botones/MusicaApagada.png");
            Texture texturaBtnMusica = new Texture("Botones/Musica.png");
            TextureRegionDrawable trdMusica = new TextureRegionDrawable(
                    new TextureRegion(texturaBtnMusica));
            ImageButton btnMusica = new ImageButton(trdMusica);
            btnMusica.setPosition(currentS.ANCHO/2-btnMusica.getWidth()/2, currentS.ALTO*0.5f-120);
            btnMusica.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    //AQUI DETENEMOS LA MUSICA
                    tocado ++;
                    if(tocado%2!=0){
                        currentS.getSonidoF().stop();
                    }
                    else{
                        currentS.getSonidoF().play();
                        currentS.getSonidoF().setLooping(true);
                    }
                }
            });
            this.addActor(btnMusica);*/
        }
        if(pantalla instanceof ScreenFourteen){
            final ScreenFourteen currentS=((ScreenFourteen) pantalla);
            //Accedemos al estado juego de screenOne
            estadoJuego=currentS.getEstadoJuego();

            // Crear rectángulo transparente
            Pixmap pixmap = new Pixmap((int) (3840/*currentS.ANCHO * 0.7f*/), (int) (720/* 0.8f*/), Pixmap.Format.RGBA8888);
            pixmap.setColor(1f, 1f, 1f, 0.40f/*0.65f*/);
            pixmap.fillRectangle(0, 0, pixmap.getWidth(), pixmap.getHeight());
            Texture texturaRectangulo = new Texture( pixmap );
            pixmap.dispose();
            Image imgRectangulo = new Image(texturaRectangulo);
            imgRectangulo.setPosition(0,0/*0.15f*currentS.ANCHO, 0.1f*currentS.ALTO*/);
            this.addActor(imgRectangulo);

            // Salir
            Texture texturaBtnSalir =new Texture("Botones/SALIR.png");
            TextureRegionDrawable trdSalir = new TextureRegionDrawable(
                    new TextureRegion(texturaBtnSalir));
            btnSalirD = new ImageButton(trdSalir);
            btnSalirD.setPosition(currentS.ANCHO/2-btnSalirD.getWidth()/2, currentS.ALTO*0.2f);
            btnSalirD.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    // Regresa al menú
                    //deten la musica que estaba de fondo
                    //currentS.getSonidoF().stop();
                    currentS.getJuego().setScreen(new mainMenu(currentS.getJuego()));

                }
            });
            this.addActor(btnSalirD);

            // Continuar
            Texture texturaBtnReintentar = new Texture("Botones/CONTINUAR.png");
            TextureRegionDrawable trdReintentar = new TextureRegionDrawable(
                    new TextureRegion(texturaBtnReintentar));
            btnContinuarD = new ImageButton(trdReintentar);
            btnContinuarD.setPosition(currentS.ANCHO/2-btnContinuarD.getWidth()/2, currentS.ALTO*0.5f);
            btnContinuarD.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    // Regresa al juego
                    currentS.setEstadoJuego(EstadoJuego.JUGANDO);
                    Gdx.input.setInputProcessor(currentS.getController().getStage());
                }
            });
            this.addActor(btnContinuarD);
            //falta entonces que se mantenga la referencia porque estamos editando en una variable lcoal

            // Musica
            /*Texture temp=new Texture("Botones/MusicaApagada.png");
            Texture texturaBtnMusica = new Texture("Botones/Musica.png");
            TextureRegionDrawable trdMusica = new TextureRegionDrawable(
                    new TextureRegion(texturaBtnMusica));
            ImageButton btnMusica = new ImageButton(trdMusica);
            btnMusica.setPosition(currentS.ANCHO/2-btnMusica.getWidth()/2, currentS.ALTO*0.5f-120);
            btnMusica.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    //AQUI DETENEMOS LA MUSICA
                    tocado ++;
                    if(tocado%2!=0){
                        currentS.getSonidoF().stop();
                    }
                    else{
                        currentS.getSonidoF().play();
                        currentS.getSonidoF().setLooping(true);
                    }
                }
            });
            this.addActor(btnMusica);*/
        }
        if(pantalla instanceof HabitacionOne){
            final HabitacionOne currentS=((HabitacionOne) pantalla);
            //Accedemos al estado juego de screenOne
            estadoJuego=currentS.getEstadoJuego();

            // Crear rectángulo transparente
            Pixmap pixmap = new Pixmap((int) (currentS.ANCHO/*currentS.ANCHO * 0.7f*/), (int) (currentS.ALTO /* 0.8f*/), Pixmap.Format.RGBA8888);
            pixmap.setColor(1f, 1f, 1f, 0.40f/*0.65f*/);
            pixmap.fillRectangle(0, 0, pixmap.getWidth(), pixmap.getHeight());
            Texture texturaRectangulo = new Texture( pixmap );
            pixmap.dispose();
            Image imgRectangulo = new Image(texturaRectangulo);
            imgRectangulo.setPosition(0,0/*0.15f*currentS.ANCHO, 0.1f*currentS.ALTO*/);
            this.addActor(imgRectangulo);

            // Salir
            Texture texturaBtnSalir =new Texture("Botones/SALIR.png");
            TextureRegionDrawable trdSalir = new TextureRegionDrawable(
                    new TextureRegion(texturaBtnSalir));
            ImageButton btnSalir = new ImageButton(trdSalir);
            btnSalir.setPosition(currentS.ANCHO/2-btnSalir.getWidth()/2, currentS.ALTO*0.2f);
            btnSalir.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    // Regresa al menú
                    //deten la musica que estaba de fondo
                    //currentS.getSonidoF().stop();
                    currentS.getJuego().setScreen(new mainMenu(currentS.getJuego()));

                }
            });
            this.addActor(btnSalir);

            // Continuar
            Texture texturaBtnReintentar = new Texture("Botones/CONTINUAR.png");
            TextureRegionDrawable trdReintentar = new TextureRegionDrawable(
                    new TextureRegion(texturaBtnReintentar));
            ImageButton btnReintentar = new ImageButton(trdReintentar);
            btnReintentar.setPosition(currentS.ANCHO/2-btnReintentar.getWidth()/2, currentS.ALTO*0.5f);
            btnReintentar.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    // Regresa al juego
                    currentS.setEstadoJuego(EstadoJuego.JUGANDO);
                    Gdx.input.setInputProcessor(currentS.getController().getStage());
                }
            });
            this.addActor(btnReintentar);
            //falta entonces que se mantenga la referencia porque estamos editando en una variable lcoal

            // Musica
            /*Texture temp=new Texture("Botones/MusicaApagada.png");
            Texture texturaBtnMusica = new Texture("Botones/Musica.png");
            TextureRegionDrawable trdMusica = new TextureRegionDrawable(
                    new TextureRegion(texturaBtnMusica));
            ImageButton btnMusica = new ImageButton(trdMusica);
            btnMusica.setPosition(currentS.ANCHO/2-btnMusica.getWidth()/2, currentS.ALTO*0.5f-120);
            btnMusica.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    //AQUI DETENEMOS LA MUSICA
                    tocado ++;
                    if(tocado%2!=0){
                        currentS.getSonidoF().stop();
                    }
                    else{
                        currentS.getSonidoF().play();
                        currentS.getSonidoF().setLooping(true);
                    }
                }
            });
            this.addActor(btnMusica);*/

        }
        if(pantalla instanceof HabitacionTwo){
            final HabitacionTwo currentS=((HabitacionTwo) pantalla);
            //Accedemos al estado juego de screenOne
            estadoJuego=currentS.getEstadoJuego();

            // Crear rectángulo transparente
            Pixmap pixmap = new Pixmap((int) (currentS.ANCHO/*currentS.ANCHO * 0.7f*/), (int) (currentS.ALTO /* 0.8f*/), Pixmap.Format.RGBA8888);
            pixmap.setColor(1f, 1f, 1f, 0.40f/*0.65f*/);
            pixmap.fillRectangle(0, 0, pixmap.getWidth(), pixmap.getHeight());
            Texture texturaRectangulo = new Texture( pixmap );
            pixmap.dispose();
            Image imgRectangulo = new Image(texturaRectangulo);
            imgRectangulo.setPosition(0,0/*0.15f*currentS.ANCHO, 0.1f*currentS.ALTO*/);
            this.addActor(imgRectangulo);

            // Salir
            Texture texturaBtnSalir =new Texture("Botones/SALIR.png");
            TextureRegionDrawable trdSalir = new TextureRegionDrawable(
                    new TextureRegion(texturaBtnSalir));
            ImageButton btnSalir = new ImageButton(trdSalir);
            btnSalir.setPosition(currentS.ANCHO/2-btnSalir.getWidth()/2, currentS.ALTO*0.2f);
            btnSalir.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    // Regresa al menú
                    //deten la musica que estaba de fondo
                    //currentS.getSonidoF().stop();
                    currentS.getJuego().setScreen(new mainMenu(currentS.getJuego()));

                }
            });
            this.addActor(btnSalir);

            // Continuar
            Texture texturaBtnReintentar = new Texture("Botones/CONTINUAR.png");
            TextureRegionDrawable trdReintentar = new TextureRegionDrawable(
                    new TextureRegion(texturaBtnReintentar));
            ImageButton btnReintentar = new ImageButton(trdReintentar);
            btnReintentar.setPosition(currentS.ANCHO/2-btnReintentar.getWidth()/2, currentS.ALTO*0.5f);
            btnReintentar.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    // Regresa al juego
                    currentS.setEstadoJuego(EstadoJuego.JUGANDO);
                    Gdx.input.setInputProcessor(currentS.getController().getStage());
                }
            });
            this.addActor(btnReintentar);
            //falta entonces que se mantenga la referencia porque estamos editando en una variable lcoal

            // Musica
            /*Texture temp=new Texture("Botones/MusicaApagada.png");
            Texture texturaBtnMusica = new Texture("Botones/Musica.png");
            TextureRegionDrawable trdMusica = new TextureRegionDrawable(
                    new TextureRegion(texturaBtnMusica));
            ImageButton btnMusica = new ImageButton(trdMusica);
            btnMusica.setPosition(currentS.ANCHO/2-btnMusica.getWidth()/2, currentS.ALTO*0.5f-120);
            btnMusica.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    //AQUI DETENEMOS LA MUSICA
                    tocado ++;
                    if(tocado%2!=0){
                        currentS.getSonidoF().stop();
                    }
                    else{
                        currentS.getSonidoF().play();
                        currentS.getSonidoF().setLooping(true);
                    }
                }
            });
            this.addActor(btnMusica);*/

        }
        if(pantalla instanceof HabitacionThree){
            final HabitacionThree currentS=((HabitacionThree) pantalla);
            //Accedemos al estado juego de screenOne
            estadoJuego=currentS.getEstadoJuego();

            // Crear rectángulo transparente
            Pixmap pixmap = new Pixmap((int) (currentS.ANCHO/*currentS.ANCHO * 0.7f*/), (int) (currentS.ALTO /* 0.8f*/), Pixmap.Format.RGBA8888);
            pixmap.setColor(1f, 1f, 1f, 0.40f/*0.65f*/);
            pixmap.fillRectangle(0, 0, pixmap.getWidth(), pixmap.getHeight());
            Texture texturaRectangulo = new Texture( pixmap );
            pixmap.dispose();
            Image imgRectangulo = new Image(texturaRectangulo);
            imgRectangulo.setPosition(0,0/*0.15f*currentS.ANCHO, 0.1f*currentS.ALTO*/);
            this.addActor(imgRectangulo);

            // Salir
            Texture texturaBtnSalir =new Texture("Botones/SALIR.png");
            TextureRegionDrawable trdSalir = new TextureRegionDrawable(
                    new TextureRegion(texturaBtnSalir));
            ImageButton btnSalir = new ImageButton(trdSalir);
            btnSalir.setPosition(currentS.ANCHO/2-btnSalir.getWidth()/2, currentS.ALTO*0.2f);
            btnSalir.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    // Regresa al menú
                    //deten la musica que estaba de fondo
                    //currentS.getSonidoF().stop();
                    currentS.getJuego().setScreen(new mainMenu(currentS.getJuego()));

                }
            });
            this.addActor(btnSalir);

            // Continuar
            Texture texturaBtnReintentar = new Texture("Botones/CONTINUAR.png");
            TextureRegionDrawable trdReintentar = new TextureRegionDrawable(
                    new TextureRegion(texturaBtnReintentar));
            ImageButton btnReintentar = new ImageButton(trdReintentar);
            btnReintentar.setPosition(currentS.ANCHO/2-btnReintentar.getWidth()/2, currentS.ALTO*0.5f);
            btnReintentar.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    // Regresa al juego
                    currentS.setEstadoJuego(EstadoJuego.JUGANDO);
                    Gdx.input.setInputProcessor(currentS.getController().getStage());
                }
            });
            this.addActor(btnReintentar);
            //falta entonces que se mantenga la referencia porque estamos editando en una variable lcoal

            // Musica
            /*Texture temp=new Texture("Botones/MusicaApagada.png");
            Texture texturaBtnMusica = new Texture("Botones/Musica.png");
            TextureRegionDrawable trdMusica = new TextureRegionDrawable(
                    new TextureRegion(texturaBtnMusica));
            ImageButton btnMusica = new ImageButton(trdMusica);
            btnMusica.setPosition(currentS.ANCHO/2-btnMusica.getWidth()/2, currentS.ALTO*0.5f-120);
            btnMusica.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    //AQUI DETENEMOS LA MUSICA
                    tocado ++;
                    if(tocado%2!=0){
                        currentS.getSonidoF().stop();
                    }
                    else{
                        currentS.getSonidoF().play();
                        currentS.getSonidoF().setLooping(true);
                    }
                }
            });
            this.addActor(btnMusica);*/

        }
        if(pantalla instanceof HabitacionFour){
            final HabitacionFour currentS=((HabitacionFour) pantalla);
            //Accedemos al estado juego de screenOne
            estadoJuego=currentS.getEstadoJuego();

            // Crear rectángulo transparente
            Pixmap pixmap = new Pixmap((int) (currentS.ANCHO/*currentS.ANCHO * 0.7f*/), (int) (currentS.ALTO /* 0.8f*/), Pixmap.Format.RGBA8888);
            pixmap.setColor(1f, 1f, 1f, 0.40f/*0.65f*/);
            pixmap.fillRectangle(0, 0, pixmap.getWidth(), pixmap.getHeight());
            Texture texturaRectangulo = new Texture( pixmap );
            pixmap.dispose();
            Image imgRectangulo = new Image(texturaRectangulo);
            imgRectangulo.setPosition(0,0/*0.15f*currentS.ANCHO, 0.1f*currentS.ALTO*/);
            this.addActor(imgRectangulo);

            // Salir
            Texture texturaBtnSalir =new Texture("Botones/SALIR.png");
            TextureRegionDrawable trdSalir = new TextureRegionDrawable(
                    new TextureRegion(texturaBtnSalir));
            ImageButton btnSalir = new ImageButton(trdSalir);
            btnSalir.setPosition(currentS.ANCHO/2-btnSalir.getWidth()/2, currentS.ALTO*0.2f);
            btnSalir.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    // Regresa al menú
                    //deten la musica que estaba de fondo
                    //currentS.getSonidoF().stop();
                    currentS.getJuego().setScreen(new mainMenu(currentS.getJuego()));

                }
            });
            this.addActor(btnSalir);

            // Continuar
            Texture texturaBtnReintentar = new Texture("Botones/CONTINUAR.png");
            TextureRegionDrawable trdReintentar = new TextureRegionDrawable(
                    new TextureRegion(texturaBtnReintentar));
            ImageButton btnReintentar = new ImageButton(trdReintentar);
            btnReintentar.setPosition(currentS.ANCHO/2-btnReintentar.getWidth()/2, currentS.ALTO*0.5f);
            btnReintentar.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    // Regresa al juego
                    currentS.setEstadoJuego(EstadoJuego.JUGANDO);
                    Gdx.input.setInputProcessor(currentS.getController().getStage());
                }
            });
            this.addActor(btnReintentar);
            //falta entonces que se mantenga la referencia porque estamos editando en una variable lcoal

            // Musica
            /*Texture temp=new Texture("Botones/MusicaApagada.png");
            Texture texturaBtnMusica = new Texture("Botones/Musica.png");
            TextureRegionDrawable trdMusica = new TextureRegionDrawable(
                    new TextureRegion(texturaBtnMusica));
            ImageButton btnMusica = new ImageButton(trdMusica);
            btnMusica.setPosition(currentS.ANCHO/2-btnMusica.getWidth()/2, currentS.ALTO*0.5f-120);
            btnMusica.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    //AQUI DETENEMOS LA MUSICA
                    tocado ++;
                    if(tocado%2!=0){
                        currentS.getSonidoF().stop();
                    }
                    else{
                        currentS.getSonidoF().play();
                        currentS.getSonidoF().setLooping(true);
                    }
                }
            });
            this.addActor(btnMusica);*/

        }
        if(pantalla instanceof HabitacionFive){
            final HabitacionFive currentS=((HabitacionFive) pantalla);
            //Accedemos al estado juego de screenOne
            estadoJuego=currentS.getEstadoJuego();

            // Crear rectángulo transparente
            Pixmap pixmap = new Pixmap((int) (currentS.ANCHO/*currentS.ANCHO * 0.7f*/), (int) (currentS.ALTO /* 0.8f*/), Pixmap.Format.RGBA8888);
            pixmap.setColor(1f, 1f, 1f, 0.40f/*0.65f*/);
            pixmap.fillRectangle(0, 0, pixmap.getWidth(), pixmap.getHeight());
            Texture texturaRectangulo = new Texture( pixmap );
            pixmap.dispose();
            Image imgRectangulo = new Image(texturaRectangulo);
            imgRectangulo.setPosition(0,0/*0.15f*currentS.ANCHO, 0.1f*currentS.ALTO*/);
            this.addActor(imgRectangulo);

            // Salir
            Texture texturaBtnSalir =new Texture("Botones/SALIR.png");
            TextureRegionDrawable trdSalir = new TextureRegionDrawable(
                    new TextureRegion(texturaBtnSalir));
            ImageButton btnSalir = new ImageButton(trdSalir);
            btnSalir.setPosition(currentS.ANCHO/2-btnSalir.getWidth()/2, currentS.ALTO*0.2f);
            btnSalir.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    // Regresa al menú
                    //deten la musica que estaba de fondo
                    //currentS.getSonidoF().stop();
                    currentS.getJuego().setScreen(new mainMenu(currentS.getJuego()));

                }
            });
            this.addActor(btnSalir);

            // Continuar
            Texture texturaBtnReintentar = new Texture("Botones/CONTINUAR.png");
            TextureRegionDrawable trdReintentar = new TextureRegionDrawable(
                    new TextureRegion(texturaBtnReintentar));
            ImageButton btnReintentar = new ImageButton(trdReintentar);
            btnReintentar.setPosition(currentS.ANCHO/2-btnReintentar.getWidth()/2, currentS.ALTO*0.5f);
            btnReintentar.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    // Regresa al juego
                    currentS.setEstadoJuego(EstadoJuego.JUGANDO);
                    Gdx.input.setInputProcessor(currentS.getController().getStage());
                }
            });
            this.addActor(btnReintentar);
            //falta entonces que se mantenga la referencia porque estamos editando en una variable lcoal

            // Musica
            /*Texture temp=new Texture("Botones/MusicaApagada.png");
            Texture texturaBtnMusica = new Texture("Botones/Musica.png");
            TextureRegionDrawable trdMusica = new TextureRegionDrawable(
                    new TextureRegion(texturaBtnMusica));
            ImageButton btnMusica = new ImageButton(trdMusica);
            btnMusica.setPosition(currentS.ANCHO/2-btnMusica.getWidth()/2, currentS.ALTO*0.5f-120);
            btnMusica.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    //AQUI DETENEMOS LA MUSICA
                    tocado ++;
                    if(tocado%2!=0){
                        currentS.getSonidoF().stop();
                    }
                    else{
                        currentS.getSonidoF().play();
                        currentS.getSonidoF().setLooping(true);
                    }
                }
            });
            this.addActor(btnMusica);*/

        }

    }
    //metodo que actualiza la posicion
    public void updateBtnPos(Pantalla p){
        if(p instanceof ScreenSix){
          ScreenSix ps=(ScreenSix)p;
            btnContinuarD.setPosition(ps.getPlayerSteven().getX()-120,p.ALTO*0.5f);
            btnSalirD.setPosition(ps.getPlayerSteven().getX()-50,p.ALTO*0.2f);
        }
        if(p instanceof ScreenNine){
            ScreenNine ps=(ScreenNine)p;
            btnContinuarD.setPosition(ps.getPlayerSteven().getX()-120,p.ALTO*0.5f);
            btnSalirD.setPosition(ps.getPlayerSteven().getX()-50,p.ALTO*0.2f);
        }
        if(p instanceof ScreenTen){
            ScreenTen ps=(ScreenTen)p;
            btnContinuarD.setPosition(ps.getPlayerSteven().getX()-120,p.ALTO*0.5f);
            btnSalirD.setPosition(ps.getPlayerSteven().getX()-50,p.ALTO*0.2f);
        }
        if(p instanceof ScreenEleven){
            ScreenEleven ps=(ScreenEleven)p;
            btnContinuarD.setPosition(ps.getPlayerSteven().getX()-120,p.ALTO*0.5f);
            btnSalirD.setPosition(ps.getPlayerSteven().getX()-50,p.ALTO*0.2f);
        }
        if(p instanceof ScreenTwelve){
            ScreenTwelve ps=(ScreenTwelve)p;
            btnContinuarD.setPosition(ps.getPlayerSteven().getX()-120,p.ALTO*0.5f);
            btnSalirD.setPosition(ps.getPlayerSteven().getX()-50,p.ALTO*0.2f);
        }
        if(p instanceof ScreenFourteen){
            ScreenFourteen ps=(ScreenFourteen) p;
            btnContinuarD.setPosition(ps.getPlayerSteven().getX()-120,p.ALTO*0.5f);
            btnSalirD.setPosition(ps.getPlayerSteven().getX()-50,p.ALTO*0.2f);
        }

    }
}
