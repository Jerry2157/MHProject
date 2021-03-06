package com.mh.itesm;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

/**
 * Created by gerardomagdaleno on 20/11/17.
 *
 */

public class PersonajeStevenFinal extends Objeto
{
    private final float VELOCIDAD_X = 2;      // Velocidad horizontal

    private Animation spriteAnimado;         // Animación caminando
    private float timerAnimacion;                           // Tiempo para cambiar frames de la animación

    protected EstadoMovimiento estadoMovimiento = EstadoMovimiento.QUIETO;

    // Salto
    protected EstadoSalto estadoSalto = EstadoSalto.EN_PISO;
    private float alturaSalto;  // altura actual, inicia en cero
    private float yOriginal;

    // Recibe una imagen con varios frames (ver marioSprite.png)
    public PersonajeStevenFinal(Texture textura, float x, float y) {
        // Lee la textura como región
        TextureRegion texturaCompleta = new TextureRegion(textura);
        // La divide en 4 frames de 32x64 (ver marioSprite.png)
        TextureRegion[][] texturaPersonaje = texturaCompleta.split(32,27);
        // Crea la animación con tiempo de 0.25 segundos entre frames.

        spriteAnimado = new Animation(0.1f
                , texturaPersonaje[0][0], texturaPersonaje[0][1], texturaPersonaje[0][2], texturaPersonaje[0][3]);
        // Animación infinita
        spriteAnimado.setPlayMode(Animation.PlayMode.LOOP);
        // Inicia el timer que contará tiempo para saber qué frame se dibuja
        timerAnimacion = 0;
        // Crea el sprite con el personaje quieto (idle)
        sprite = new Sprite(texturaPersonaje[0][0]);    // QUIETO
        sprite.setPosition(x,y);    // Posición inicial

        // Salto
        alturaSalto = 0;
    }

    // Dibuja el personaje
    public void dibujar(SpriteBatch batch) {
        // Dibuja el personaje dependiendo del estadoMovimiento

        switch (estadoMovimiento) {
            case MOV_DERECHA:
                timerAnimacion += Gdx.graphics.getDeltaTime();
                // Frame que se dibujará
                TextureRegion region = spriteAnimado.getKeyFrame(timerAnimacion);
                if (estadoMovimiento== EstadoMovimiento.MOV_IZQUIERDA) {
                    if (!region.isFlipX()) {
                        region.flip(true,false);
                    }
                } else {
                    if (region.isFlipX()) {
                        region.flip(true,false);
                    }
                }
                batch.draw(region,sprite.getX(),sprite.getY());
                break;
            case MOV_IZQUIERDA:
                timerAnimacion += Gdx.graphics.getDeltaTime();
                // Frame que se dibujará
                TextureRegion regiontwo = spriteAnimado.getKeyFrame(timerAnimacion);
                if (estadoMovimiento== EstadoMovimiento.MOV_IZQUIERDA) {
                    if (!regiontwo.isFlipX()) {
                        regiontwo.flip(true,false);
                    }
                } else {
                    if (regiontwo.isFlipX()) {
                        regiontwo.flip(true,false);
                    }
                }
                batch.draw(regiontwo,sprite.getX(),sprite.getY());
                break;
            case QUIETO:
                sprite.draw(batch);
            case INICIANDO:
                sprite.draw(batch);
                break;
            case MOV_ABAJO:
                timerAnimacion += Gdx.graphics.getDeltaTime();
                // Frame que se dibujará
                TextureRegion regionfour = spriteAnimado.getKeyFrame(timerAnimacion);
                if (estadoMovimiento== EstadoMovimiento.MOV_IZQUIERDA) {
                    if (!regionfour.isFlipX()) {
                        regionfour.flip(true,false);
                    }
                } else {
                    if (regionfour.isFlipX()) {
                        regionfour.flip(true,false);
                    }
                }
                batch.draw(regionfour,sprite.getX(),sprite.getY());
                break;
            case MOV_ARRIBA:
                timerAnimacion += Gdx.graphics.getDeltaTime();
                // Frame que se dibujará
                TextureRegion regionthree = spriteAnimado.getKeyFrame(timerAnimacion);
                if (estadoMovimiento== EstadoMovimiento.MOV_IZQUIERDA) {
                    if (!regionthree.isFlipX()) {
                        regionthree.flip(true,false);
                    }
                } else {
                    if (regionthree.isFlipX()) {
                        regionthree.flip(true,false);
                    }
                }
                batch.draw(regionthree,sprite.getX(),sprite.getY());
                break;
        }
    }

    // Actualiza el sprite, de acuerdo al estadoMovimiento y estadoSalto
    public void actualizar(TiledMap mapa) {
        switch (estadoMovimiento) {
            case MOV_DERECHA:
            case MOV_IZQUIERDA:
                moverHorizontal(mapa);
                break;
        }
        switch (estadoSalto) {
            case SUBIENDO:
            case BAJANDO:
                moverVertical(mapa);
                break;
        }
    }

    // Realiza el salto
    public void moverVertical(TiledMap mapa) {
        float delta = Gdx.graphics.getDeltaTime()*200;
        switch (estadoSalto) {
            case SUBIENDO:
                sprite.setY(sprite.getY()+delta);
                alturaSalto += delta;
                if (alturaSalto>=2*sprite.getHeight()) {
                    estadoSalto = EstadoSalto.BAJANDO;
                }
                break;
            case BAJANDO:
                sprite.setY(sprite.getY()-delta);
                alturaSalto -= delta;
                if (alturaSalto<=0) {
                    estadoSalto = EstadoSalto.EN_PISO;
                    alturaSalto = 0;
                    sprite.setY(yOriginal);
                }
                break;
        }



        // Obtiene la primer capa del mapa (en este caso es la única)
        TiledMapTileLayer capa = (TiledMapTileLayer) mapa.getLayers().get(0);
        // Ejecutar movimiento horizontal
        float nuevaY = sprite.getY();
        // ¿Quiere ir a la Derecha?
        if ( estadoMovimiento== EstadoMovimiento.MOV_ARRIBA) {
            // Obtiene el bloque del lado derecho. Asigna null si puede pasar.
            int y = (int) ((sprite.getY() + 32) / 32);   // Convierte coordenadas del mundo en coordenadas del mapa
            int x = (int) (sprite.getX() / 32);
            TiledMapTileLayer.Cell celdaDerecha = capa.getCell(x, y);
            if (celdaDerecha != null) {
                Object tipo = celdaDerecha.getTile().getProperties().get("tipo");
                if (!"pasto".equals(tipo)) {
                    celdaDerecha = null;  // Puede pasar
                }
            }
            if ( celdaDerecha==null) {
                // Ejecutar movimiento horizontal
                nuevaY += VELOCIDAD_X;
                // Prueba que no salga del mundo por la derecha
                if (nuevaY <= 3840 - sprite.getHeight()) {
                    sprite.setY(nuevaY);
                }
            }
        }
        // ¿Quiere ir a la izquierda?
        if ( estadoMovimiento== EstadoMovimiento.MOV_ABAJO) {
            int yAba = (int) ((sprite.getY()) / 32);
            int x = (int) (sprite.getX() / 32);
            // Obtiene el bloque del lado izquierdo. Asigna null si puede pasar.
            TiledMapTileLayer.Cell celdaIzquierda = capa.getCell(x, yAba);
            if (celdaIzquierda != null) {
                Object tipo = celdaIzquierda.getTile().getProperties().get("tipo");
                if (!"pasto".equals(tipo)) {
                    celdaIzquierda = null;  // Puede pasar
                }
            }
            if ( celdaIzquierda==null) {
                // Prueba que no salga del mundo por la izquierda
                nuevaY -= VELOCIDAD_X;
                if (nuevaY >= 0) {
                    sprite.setY(nuevaY);
                }
            }
        }

    }


    // Mueve el personaje a la derecha/izquierda, prueba choques con paredes
    public void moverHorizontal(TiledMap mapa) {
        // Obtiene la primer capa del mapa (en este caso es la única)
        TiledMapTileLayer capa = (TiledMapTileLayer) mapa.getLayers().get(0);
        // Ejecutar movimiento horizontal
        float nuevaX = sprite.getX();
        // ¿Quiere ir a la Derecha?
        if ( estadoMovimiento== EstadoMovimiento.MOV_DERECHA) {
            // Obtiene el bloque del lado derecho. Asigna null si puede pasar.
            int x = (int) ((sprite.getX() + 32) / 32);   // Convierte coordenadas del mundo en coordenadas del mapa
            int y = (int) (sprite.getY() / 32);
            TiledMapTileLayer.Cell celdaDerecha = capa.getCell(x, y);
            if (celdaDerecha != null) {
                Object tipo = celdaDerecha.getTile().getProperties().get("tipo");
                if (!"pasto".equals(tipo)) {
                    celdaDerecha = null;  // Puede pasar
                }
            }
            if ( celdaDerecha==null) {
                // Ejecutar movimiento horizontal
                nuevaX += VELOCIDAD_X;
                // Prueba que no salga del mundo por la derecha
                if (nuevaX <= 3840 - sprite.getWidth()) {
                    sprite.setX(nuevaX);
                }
            }
        }
        // ¿Quiere ir a la izquierda?
        if ( estadoMovimiento== EstadoMovimiento.MOV_IZQUIERDA) {
            int xIzq = (int) ((sprite.getX()) / 32);
            int y = (int) (sprite.getY() / 32);
            // Obtiene el bloque del lado izquierdo. Asigna null si puede pasar.
            TiledMapTileLayer.Cell celdaIzquierda = capa.getCell(xIzq, y);
            if (celdaIzquierda != null) {
                Object tipo = celdaIzquierda.getTile().getProperties().get("tipo");
                if (!"pasto".equals(tipo)) {
                    celdaIzquierda = null;  // Puede pasar
                }
            }
            if ( celdaIzquierda==null) {
                // Prueba que no salga del mundo por la izquierda
                nuevaX -= VELOCIDAD_X;
                if (nuevaX >= 0) {
                    sprite.setX(nuevaX);
                }
            }
        }
    }

    // Revisa si toca una moneda
    public boolean recolectarMonedas(TiledMap mapa) {
        // Revisar si toca una moneda (pies)
        TiledMapTileLayer capa = (TiledMapTileLayer)mapa.getLayers().get(0);
        int x = (int)(sprite.getX()/32);
        int y = (int)(sprite.getY()/32)+1;
        TiledMapTileLayer.Cell celda = capa.getCell(x,y);
        if (celda!=null ) {
            Object tipo = celda.getTile().getProperties().get("tipo");
            if ( "moneda".equals(tipo) ) {
                //capa.setCell(x,y,null);    // Borra la moneda del mapa
                capa.setCell(x,y,capa.getCell(0,4)); // Cuadro azul en lugar de la moneda
                return true;
            }
        }
        return false;
    }

    // Accesor de estadoMovimiento
    public EstadoMovimiento getEstadoMovimiento() {
        return estadoMovimiento;
    }

    // Modificador de estadoMovimiento
    public void setEstadoMovimiento(EstadoMovimiento estadoMovimiento) {
        this.estadoMovimiento = estadoMovimiento;
    }

    // Inicia el salto
    public void saltar() {

        if (estadoSalto!= EstadoSalto.SUBIENDO && estadoSalto!= EstadoSalto.BAJANDO) {
            // inicia
            estadoSalto = EstadoSalto.SUBIENDO;
            yOriginal = sprite.getY();
            alturaSalto = 0;
        }
    }

    public enum EstadoMovimiento {
        INICIANDO,
        QUIETO,
        MOV_IZQUIERDA,
        MOV_DERECHA,
        MOV_ARRIBA,
        MOV_ABAJO
    }

    public enum EstadoSalto {
        SUBIENDO,
        BAJANDO,
        EN_PISO,
        SALTANDO    // General, puede estar subiendo o bajando
    }
}
