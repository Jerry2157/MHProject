package com.mh.itesm;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by jerry2157 on 11/09/17.
 */

public class PlayerSteven extends Sprite {

    //Variables:
    private float x;
    private float y;
    private Estado estado; //abajo, arriba, explota, muerto
    Animation animation;
    float elapsedTime;
    //TextureRegion[]
    private Texture[] WalkingTextureDebugOne = new Texture[5];
    private float timerEstadoWalking = 0;
    //private Texture WalkingTextureDebugTwo;
    //End variabales

    //------------------------------------------------------------------
    public PlayerSteven(String name,float x,float y, SpriteBatch batch){
        super(new Texture(name));
        setPosition(x-getWidth()/2f,y-getHeight()/2f);
        this.x = x;
        this.y = y;
        WalkingTextureDebugOne[0] = new Texture("WalkAngie/walk_1.png");
        WalkingTextureDebugOne[1] = new Texture("WalkAngie/walk_2.png");
        WalkingTextureDebugOne[2] = new Texture("WalkAngie/walk_3.png");
        WalkingTextureDebugOne[3] = new Texture("WalkAngie/walk_4.png");
        WalkingTextureDebugOne[4] = new Texture("WalkAngie/walk_5.png");
        estado = Estado.Walking;
    }
    //----------------------------------------
    public void render(SpriteBatch batch) {
        //batch.draw(texturaArriba,x,y);
        switch (estado){
            case Idle:

                //batch.draw(texturaArriba,x,y);
                break;
            case Walking:
                //batch.draw(texturaAbajo,x,y);
                if(timerEstadoWalking<=0.2f){batch.draw(WalkingTextureDebugOne[0],x,y);}
                else if(timerEstadoWalking>0.2f && timerEstadoWalking<=0.4f){batch.draw(WalkingTextureDebugOne[1],x,y); }
                else if(timerEstadoWalking>0.4f && timerEstadoWalking<=0.6f){batch.draw(WalkingTextureDebugOne[2],x,y);}
                else if(timerEstadoWalking>0.6f && timerEstadoWalking<=0.8f){batch.draw(WalkingTextureDebugOne[3],x,y);}
                else if(timerEstadoWalking>=0.8f){batch.draw(WalkingTextureDebugOne[4],x,y); timerEstadoWalking = 0f;}
                break;
            case Jumping:
                //dibujar explotar
                break;
            case Crounching:
                //
                break;
        }
        timerEstadoWalking+= Gdx.graphics.getDeltaTime();
        //tiempo Animación caminata
        /*if(timerEstadoWalking<=0.2f){ }
        else if(timerEstadoWalking>0.2f && timerEstadoWalking<=0.4f){ }
        else if(timerEstadoWalking>0.4f && timerEstadoWalking<=0.6f){ }
        else if(timerEstadoWalking>0.6f && timerEstadoWalking<=0.8f){ }
        else if(timerEstadoWalking>=0.8f){ timerEstadoWalking = 0f;}*/
    }
    //----------------------------------------
    public enum Estado{
        Idle,
        Walking,
        Jumping,
        Crounching
    }
}
