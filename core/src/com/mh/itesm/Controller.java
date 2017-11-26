package com.mh.itesm;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Autor: Jesús Heriberto Vásquez Sánchez A01377358
 * Autor: Gerardo Ezequiel Magdaleno Hernández
 */

public class Controller extends Pantalla {
    Viewport viewport;
    Stage stage;
    boolean upPressed, downPressed, leftPressed, rightPressed,pausePressed, spacePressed,buttonPressed;
    OrthographicCamera cam;

    public Controller(){
        cam = new OrthographicCamera();
        //viewport = new FitViewport(800, 480, cam);
        viewport = new FitViewport(1280, 720, cam);
        stage = new Stage(viewport, batch);

        stage.addListener(new InputListener(){

            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                switch(keycode){
                    case Input.Keys.UP:
                        upPressed = true;
                        break;
                    case Input.Keys.DOWN:
                        downPressed = true;
                        break;
                    case Input.Keys.LEFT:
                        leftPressed = true;
                        break;
                    case Input.Keys.RIGHT:
                        rightPressed = true;
                        break;
                    case Input.Keys.SPACE:
                        spacePressed=true;
                        break;
                }
                return true;
            }

            @Override
            public boolean keyUp(InputEvent event, int keycode) {
                switch(keycode){
                    case Input.Keys.UP:
                        upPressed = false;
                        break;
                    case Input.Keys.DOWN:
                        downPressed = false;
                        break;
                    case Input.Keys.LEFT:
                        leftPressed = false;
                        break;
                    case Input.Keys.RIGHT:
                        rightPressed = false;
                        break;
                    case Input.Keys.SPACE:
                        spacePressed = false;
                        break;
                }
                return true;
            }
        });

        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.left().bottom();

        Image upImg = new Image(new Texture("controllers/flatLight22up.png"));
        upImg.setSize(50, 50);
        upImg.addListener(new InputListener() {

            //vector para manejar objetos
            private Vector3 v = new Vector3();
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                upPressed = true;
                camara.unproject(v);

                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                upPressed = false;
            }
        });

        Image downImg = new Image(new Texture("controllers/flatLight22down.png"));
        downImg.setSize(50, 50);
        downImg.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                downPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                downPressed = false;
            }
        });
        //Texture tex=new Texture("comun/btnPausa.png");
        Texture tex=new Texture("comun/pausa.png");
        Image pausa= new Image(tex);
        //pausa.setSize(60,60);
        pausa.setSize(70,70);
        //pausa.setPosition(ANCHO/2+100,ALTO/2+60);
        pausa.setPosition(ANCHO/2+95,ALTO/2+55);
                //ANCHO-3*(new Texture("comun/btnPausa.png").getWidth()/2)+35,ALTO-(new Texture("comun/btnPausa.png")).getHeight()-10);
        pausa.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                pausePressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                pausePressed = false;
            }
        });

        Image rightImg = new Image(new Texture("controllers/flatLight22right.png"));
        rightImg.setSize(50, 50);
        rightImg.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                rightPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                rightPressed = false;
            }
        });

        Image leftImg = new Image(new Texture("controllers/flatLight22left.png"));
        leftImg.setSize(50, 50);
        leftImg.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                leftPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                leftPressed = false;
            }
        });

        Image botonI=new Image(new Texture("Botones/boton.png"));
        botonI.setSize(50,50);
        botonI.setPosition(ANCHO/2+100,ALTO/2-350);
        botonI.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                buttonPressed= true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                buttonPressed = false;
            }
        });


        table.add();
        table.add(upImg).size(upImg.getWidth(), upImg.getHeight());
        table.add();
        table.row().pad(5, 5, 5, 5);
        table.add(leftImg).size(leftImg.getWidth(), leftImg.getHeight());
        table.add();
        table.add(rightImg).size(rightImg.getWidth(), rightImg.getHeight());
        table.row().padBottom(5);
        table.add();
        table.add(downImg).size(downImg.getWidth(), downImg.getHeight());
        table.add();

        if (Gdx.app.getType() == Application.ApplicationType.Android  || Gdx.app.getType() == Application.ApplicationType.iOS) {
            stage.addActor(table);
            stage.addActor(botonI);
        }

        stage.addActor(pausa);
    }

    public void draw(){
        stage.draw();
    }

    public boolean isUpPressed() {
        return upPressed;
    }

    public boolean isDownPressed() {
        return downPressed;
    }

    public boolean isLeftPressed() {
        return leftPressed;
    }

    public boolean isRightPressed() {
        return rightPressed;
    }

    public boolean isPausePressed(){return pausePressed;}

    public boolean isSpacePressed(){
        return spacePressed;
    }

    public boolean isButtonPressed(){
        return buttonPressed;
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

    }

    public void resize(int width, int height){
        viewport.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        this.dispose();
    }

    public Stage getStage(){
        return stage;
    }

}
