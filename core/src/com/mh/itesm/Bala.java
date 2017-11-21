package com.mh.itesm;

import com.badlogic.gdx.graphics.Texture;

/**
 * Proyectiles que lanza mario
 */

public class Bala extends Objeto
{
    private final float VELOCIDAD_X = 400;      // Velocidad horizontal (a la derecha)

    // Recibe la imagen
    public Bala(Texture textura, float x, float y) {
        super(textura, x, y);
    }

    // Mueve el personaje a la derecha
    public void mover(float delta) {
        float distancia = VELOCIDAD_X*delta;
        sprite.setX(sprite.getX()+distancia);
    }

    public boolean chocaCon(Hongo hongo) {
        return sprite.getBoundingRectangle().overlaps(hongo.sprite.getBoundingRectangle());
    }
}
