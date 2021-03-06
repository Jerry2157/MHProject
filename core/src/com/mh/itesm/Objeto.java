package com.mh.itesm;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

/**
 * Representa un elemento gráfico del juego
 */

public class Objeto
{
    protected Sprite sprite;    // Imagen

    public Objeto() {

    }

    public Objeto(Texture textura, float x, float y) {
        sprite = new Sprite(textura);
        sprite.setPosition(x, y);
    }

    public boolean contiene(Vector3 v) {
        float x = v.x;
        float y = v.y;

        return x>=sprite.getX() && x<=sprite.getX()+sprite.getWidth()
                && y>=sprite.getY() && y<=sprite.getY()+sprite.getHeight();
    }

    public void dibujar(SpriteBatch batch) {
        sprite.draw(batch);
    }
    public void setPosition(float x,float y){
        sprite.setPosition(x,y);
    }


    public void dispose() {
        this.dispose();
    }

    public float getX(){
        float x=sprite.getX();
        return x;
    }
    public float getY(){
        float y=sprite.getY();
        return y;
    }
}
