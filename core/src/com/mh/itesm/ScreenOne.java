package com.mh.itesm;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;

import javax.swing.Box;

/**
 * Created by jerry2157 on 10/09/17.
 */

public class ScreenOne extends Pantalla {

    private MHMain juego;

    World world;
    private Box2DDebugRenderer b2dr;
    Body player;
    Controller controller;
    private Texture BackgroundLayerOne;   // Imagen que se muestra

    // Contenedor de los botones
    private Stage escenaMenu;

    public ScreenOne(MHMain juego) {
        Gdx.input.setInputProcessor(escenaMenu);
        this.juego = juego;
        world = new World(new Vector2(0,-9.81f),true);
        b2dr = new Box2DDebugRenderer();


        createGround();
        createPlayer();
        controller = new Controller();
    }

    public void handleInput(){
        if(controller.isRightPressed())
            player.setLinearVelocity(new Vector2(100, player.getLinearVelocity().y));
        else if (controller.isLeftPressed())
            player.setLinearVelocity(new Vector2(-100, player.getLinearVelocity().y));
        else
            player.setLinearVelocity(new Vector2(0, player.getLinearVelocity().y));
        if (controller.isUpPressed() && player.getLinearVelocity().y == 0)
            player.applyLinearImpulse(new Vector2(0, 20f), player.getWorldCenter(), true);
    }

    @Override
    public void show() {
        BackgroundLayerOne = new Texture("ScreenOne/ScreenOneFondo1920.png");
    }

    @Override
    public void render(float delta) {
        update(Gdx.graphics.getDeltaTime());
        borrarPantalla(0.8f,0.45f,0.2f);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(BackgroundLayerOne, Pantalla.ANCHO/2 -BackgroundLayerOne.getWidth()/2,Pantalla.ALTO/2-BackgroundLayerOne.getHeight()/2);
        batch.end();
        b2dr.render(world,camara.combined);
        //batch.setProjectionMatrix(camara.combined);
        if(Gdx.app.getType() == Application.ApplicationType.Android)
        controller.draw();


    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }
    public void createGround(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(vista.getWorldWidth()/2,0);
        bdef.type = BodyDef.BodyType.StaticBody;
        Body b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(vista.getWorldWidth()/2,20);

        fdef.shape = shape;
        b2body.createFixture(fdef);
    }
    public void createPlayer(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(vista.getWorldWidth()/2,80);
        bdef.type = BodyDef.BodyType.DynamicBody;
        player = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(40,40);

        fdef.shape = shape;
        player.createFixture(fdef);
    }

    @Override
    public void dispose() {

    }
    public void HandleInput(){

    }
    public void update(float dt){
        handleInput();
        world.step(1/60f,6,2);
        //camara.position.set(vista.getWorldWidth()/2,vista.getWorldHeight()/2,0);
        camara.update();
    }
}
