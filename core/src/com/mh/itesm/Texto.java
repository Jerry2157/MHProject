package com.mh.itesm;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Muestra un texto en la pantalla.
 */

public class Texto
{
    private BitmapFont font;

    //Cargamos la fuente que queremos utilizar para nuestro texto
    public Texto() {
        font = new BitmapFont(Gdx.files.internal("fuentes/TextoNivel/fuenteTexto.fnt"));
    }
    //Metodo whackamole
    public Texto(String archivo) {
        font = new BitmapFont(Gdx.files.internal(archivo));
    }

    public void mostrarMensaje(SpriteBatch batch, String mensaje, float x, float y) {
        GlyphLayout glyp = new GlyphLayout();
        glyp.setText(font, mensaje);
        float anchoTexto = glyp.width;
        font.draw(batch, glyp, x-anchoTexto/2, y);
        font.setColor(com.badlogic.gdx.graphics.Color.BLACK);
    }
    //Mueve color por escala RGB
    public void setColor(float a,float b, float c,float d){
        font.setColor(a,b,c,d);
    }


}
