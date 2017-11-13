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
 * Created by jerry2157 on 11/09/17.
 */

public class ScreenTwoAnim extends Objeto{
    //Atlases
    private Texture FondoAtlas;
    private Texture StevenJump;

    private int SceneTam = 0;
    private final float VELOCIDAD_X = 4;      // Velocidad horizontal

    private Animation spriteAnimadoCam;         // Animación caminando
    private float timerAnimacion;               // Tiempo para cambiar frames de la animación

    protected EstadoMovimiento estadoMovimiento = EstadoMovimiento.QUIETO;

    // Salto
    protected EstadoSalto estadoSalto = EstadoSalto.EN_PISO;
    private float alturaSalto;  // altura actual, inicia en cero
    private float yOriginal;

    // Recibe una imagen con varios frames (ver marioSprite.png)
    public ScreenTwoAnim(float x, float y, int SceneWidth) {
        FondoAtlas = new Texture("ESCENA2.png");

        SceneTam = SceneWidth;
        // Lee la textura como región
        TextureRegion texturaCompleta = new TextureRegion(FondoAtlas);
        // La divide en 4 frames de 32x64 (ver marioSprite.png)
        TextureRegion[][] texturaPersonaje = texturaCompleta.split(1280,720);
        Object[] temp = texturaPersonaje[0];
        System.out.println();
        // Crea la animación con tiempo de 0.25 segundos entre frames.

        spriteAnimadoCam = new Animation(0.1f
                , texturaPersonaje[0][0], texturaPersonaje[0][1], texturaPersonaje[0][2]
                , texturaPersonaje[1][0], texturaPersonaje[1][1], texturaPersonaje[1][2]);

        // Animación infinita
        spriteAnimadoCam.setPlayMode(Animation.PlayMode.LOOP);
        // Inicia el timer que contará tiempo para saber qué frame se dibuja
        timerAnimacion = 0;
        // Crea el sprite con el personaje quieto (idle)
        sprite = new Sprite(texturaPersonaje[2][0]);    // QUIETO
        sprite.setPosition(x,y);    // Posición inicial

        // Salto
        alturaSalto = 0;
    }

    // Dibuja el personaje
    public void dibujar(SpriteBatch batch) {
        // Dibuja el personaje dependiendo del estadoMovimiento

        switch (estadoMovimiento) {
            case MOV_DERECHA:
            case Cutting:
                timerAnimacion += Gdx.graphics.getDeltaTime();
                // Frame que se dibujará
                TextureRegion region = (TextureRegion)spriteAnimadoCam.getKeyFrame(timerAnimacion);
                if (estadoMovimiento==EstadoMovimiento.Cutting) {
                    /*if (!region.isFlipX()) {
                        region.flip(true,false);
                    }*/
                } else {
                    /*if (region.isFlipX()) {
                        region.flip(true,false);
                    }*/
                }
                batch.draw(region,sprite.getX(),sprite.getY());
                break;
            case QUIETO:
            case INICIANDO:
                sprite.draw(batch); // Dibuja el sprite estático
                break;
        }
    }

    // Actualiza el sprite, de acuerdo al estadoMovimiento y estadoSalto
    public void actualizar() {
        switch (estadoMovimiento) {
            case MOV_DERECHA:
            case Cutting:
                moverHorizontal();
                break;
        }
        switch (estadoSalto) {
            case SUBIENDO:
            case BAJANDO:
                moverVertical();
                break;
        }
    }

    // Realiza el salto
    private void moverVertical() {
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
    }


    // Mueve el personaje a la derecha/izquierda, prueba choques con paredes
    private void moverHorizontal() {
        // Obtiene la primer capa del mapa (en este caso es la única)
        //TiledMapTileLayer capa = (TiledMapTileLayer) mapa.getLayers().get(0);
        // Ejecutar movimiento horizontal
        float nuevaX = sprite.getX();
        // ¿Quiere ir a la Derecha?
        if ( estadoMovimiento==EstadoMovimiento.MOV_DERECHA) {
            /* Obtiene el bloque del lado derecho. Asigna null si puede pasar.
            int x = (int) ((sprite.getX() + 32) / 32);   // Convierte coordenadas del mundo en coordenadas del mapa
            int y = (int) (sprite.getY() / 32);
            TiledMapTileLayer.Cell celdaDerecha = capa.getCell(x, y);
            if (celdaDerecha != null) {
                Object tipo = celdaDerecha.getTile().getProperties().get("tipo");
                if (!"ladrillo".equals(tipo)) {
                    celdaDerecha = null;  // Puede pasar
                }
            }
            if ( celdaDerecha==null) {
                // Ejecutar movimiento horizontal

            }*/
            //nuevaX += VELOCIDAD_X;
            // Prueba que no salga del mundo por la derecha
            /*if (nuevaX <= SceneTam - sprite.getWidth()) {
                sprite.setX(nuevaX);
            }*/
        }
        // ¿Quiere ir a la izquierda?
        if ( estadoMovimiento==EstadoMovimiento.Cutting) {
            /*int xIzq = (int) ((sprite.getX()) / 32);
            int y = (int) (sprite.getY() / 32);
            /* Obtiene el bloque del lado izquierdo. Asigna null si puede pasar.
            TiledMapTileLayer.Cell celdaIzquierda = capa.getCell(xIzq, y);
            if (celdaIzquierda != null) {
                Object tipo = celdaIzquierda.getTile().getProperties().get("tipo");
                if (!"ladrillo".equals(tipo)) {
                    celdaIzquierda = null;  // Puede pasar
                }
            }
            if ( celdaIzquierda==null) {

            }
            // Prueba que no salga del mundo por la izquierda
            nuevaX -= VELOCIDAD_X;
            if (nuevaX >= 0) {
                sprite.setX(nuevaX);
            }*/
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

        if (estadoSalto!=EstadoSalto.SUBIENDO && estadoSalto!=EstadoSalto.BAJANDO) {
            // inicia
            estadoSalto = EstadoSalto.SUBIENDO;
            yOriginal = sprite.getY();
            alturaSalto = 0;
        }
    }

    public enum EstadoMovimiento {
        INICIANDO,
        QUIETO,
        Cutting,
        MOV_DERECHA,
    }

    public enum EstadoSalto {
        SUBIENDO,
        BAJANDO,
        EN_PISO,
        SALTANDO    // General, puede estar subiendo o bajando
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
