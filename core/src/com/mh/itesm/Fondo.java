package com.mh.itesm;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Usuario on 20-Oct-17.
 */
//Clase que representa un objeto de tipo fondo

public class Fondo {
    private Sprite sprite;

    public Fondo(Texture textura){
        sprite=new Sprite(textura);
    }

    //Metodo automatico maneja un batch
    public void render(SpriteBatch batch){

        sprite.draw(batch);
    }

    public float getAncho(){
        return sprite.getWidth();
    }

    public float getAlto(){
        return sprite.getHeight();
    }

    public void setPosition(float x, float y) {
        sprite.setPosition(x, y);
    }




    public void setSize(float ancho,float alto){
        sprite.setSize(ancho, alto);
    }

    public void cambiarFondo(Texture textura){
        sprite.setTexture(textura);
    }

    public Sprite getSprite() {
        return sprite;
    }


}
