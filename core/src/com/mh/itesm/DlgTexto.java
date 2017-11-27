package com.mh.itesm;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * Muestra un texto en la pantalla.
 */
public class DlgTexto extends Stage
{
    private BitmapFont font;
    //Cargamos la fuente que queremos utilizar para nuestro texto
    public DlgTexto() {
        font = new BitmapFont(Gdx.files.internal("fonts/gamefont.fnt"));
    }
    //Metodo whackamole
    public DlgTexto(String archivo) {
        font = new BitmapFont(Gdx.files.internal(archivo));
    }

    public void mostrarMensaje(SpriteBatch batch, String mensaje, float x, float y) {

        // Crear rectángulo transparente
        Pixmap pixmap = new Pixmap((int) (Pantalla.ANCHO/*currentS.ANCHO * 0.7f*/), (int) (Pantalla.ALTO /* 0.8f*/), Pixmap.Format.RGBA8888);
        pixmap.setColor(1f, 1f, 1f, 0.40f/*0.65f*/);
        pixmap.fillRectangle(0, 0, pixmap.getWidth(), pixmap.getHeight());
        Texture texturaRectangulo = new Texture( pixmap );
        pixmap.dispose();
        Image imgRectangulo = new Image(texturaRectangulo);
        imgRectangulo.setPosition(0,0/*0.15f*currentS.ANCHO, 0.1f*currentS.ALTO*/);
        this.addActor(imgRectangulo);

        GlyphLayout glyp = new GlyphLayout();
        glyp.setText(font, mensaje);
        float anchoTexto = glyp.width;
        font.draw(batch, glyp, x-anchoTexto/2, y);
        font.setColor(com.badlogic.gdx.graphics.Color.WHITE);
    }
    //Mueve color por escala RGB
    public void setColor(float a,float b, float c,float d){
        font.setColor(a,b,c,d);
    }
}
