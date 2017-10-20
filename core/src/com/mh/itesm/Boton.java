package com.mh.itesm;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Usuario on 19-Oct-17.
 */

//Esta clase representa un boton

public class Boton {
    //Se manejar por sprites (Img)
    private Sprite sprite;



    public Boton(Texture textura) {
        sprite = new Sprite(textura);
    }
    //render dibujara el boton en automarico
    public void render(SpriteBatch batch){
        sprite.draw(batch);
    }

    public float getY() {

        return sprite.getY();
    }

    public float getX() {

        return sprite.getX();
    }

    public float getAncho(){
        return sprite.getWidth();
    }

    public float getAlto(){
        return sprite.getHeight();
    }


    public void setPosicion(float x, float y) {
        sprite.setPosition(x, y);

    }

    public void setTamanio(float ancho,float alto){
        sprite.setSize(ancho, alto);
    }
    //Set alfa define el color? IMPORTANTE PARA MANEJAR TRASPARENCIA
    public void setAlfa(float alfa) {
        sprite.setAlpha(alfa);
    }

    public Sprite getSprite() {
        return sprite;
    }








}
