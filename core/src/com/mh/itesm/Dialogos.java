package com.mh.itesm;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

/**
 * Created by gerardomagdaleno on 14/11/17.
 */

public class Dialogos extends Objeto {
    //Atlases
    private Texture stevenCam;
    private Texture StevenJump;

    private int SceneTam = 0;
    private final float VELOCIDAD_X = 2;      // Velocidad horizontal
    private Animation spriteAnimadoCam;         // Animación caminando
    private float timerAnimacion;               // Tiempo para cambiar frames de la animación

    protected PlayerSteven.EstadoMovimiento estadoMovimiento = PlayerSteven.EstadoMovimiento.QUIETO;

    // Salto
    protected PlayerSteven.EstadoSalto estadoSalto = PlayerSteven.EstadoSalto.EN_PISO;
    private float alturaSalto;  // altura actual, inicia en cero
    private float yOriginal;

    BitmapFont font;

    // Recibe una imagen con varios frames (ver marioSprite.png)
    public Dialogos() {

        font = new BitmapFont(Gdx.files.internal("data/rayanfont.fnt"), false);
    }

    // Dibuja el personaje
    public void dibujar(SpriteBatch batch) {
        sprite.draw(batch); // Dibuja el sprite estático

    }

    // Accesores para la posición
    public float getX() {
        return sprite.getX();
    }

    public float getY() {
        return sprite.getY();
    }

    public void delete(){
        this.delete();
    }
}
