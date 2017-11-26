package com.mh.itesm;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Timer;

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

    DlgTexto font;
    private Sprite cocinera;
    private Sprite director;
    private Sprite enfermera;
    private Sprite jardinero;
    private Sprite persona;
    private Sprite policia;
    private Sprite viejita;
    private Sprite villano;
    private Sprite fadeObject;
    private Sprite dote;

    private int posAvatarX;
    private int posAvatarY;

    private SpriteBatch sbatch;
    private int linePoint;

    private String[][] dialogueLines;

    //Dialogos screenONE
    private String[][] dialogueOne;

    private boolean lineStarted;
    private float counterDialogue;
    private float counterDialogueStart;

    // Recibe una imagen con varios frames (ver marioSprite.png)
    public Dialogos() {
        dialogueLines = new String[][]{{"hola","perro","manzana"},{"Hola, soy tu enfermera, \n estas en el Manicomio Colonia, ",
                "Vimos tus antecedentes, \n parece que padeces esquizofrenia","Eres el principal sospechoso \n en el acuchillamiento de tu familia",
                "Ve y platica con la cocinera \n en la cafeteria, ella te dira que hacer"},{"hola","perro","manzana"}};
        //screenOne el primer elemento es esposa, el segundo hija
        dialogueOne = new String[][]{{"Hola Steven","Que lindo dia para pasarla en parque","Quedate quieta hija, tu padre te pintara"},{"Hola Padre", "Esta muy relajado","No te muevas madre"}};
        lineStarted = false;

        font = new DlgTexto("fonts/gamefont.fnt");
        cocinera = new Sprite(new Texture("CabezasDialogos/CabezaCocinera.png"));
        director = new Sprite(new Texture("CabezasDialogos/CabezaDirector.png"));
        enfermera = new Sprite(new Texture("CabezasDialogos/CabezaEnfermera.png"));
        jardinero = new Sprite(new Texture("CabezasDialogos/CabezaJardinero.png"));
        persona = new Sprite(new Texture("CabezasDialogos/CabezaPersona.png"));
        policia = new Sprite(new Texture("CabezasDialogos/CabezaPolicia.png"));
        viejita = new Sprite(new Texture("CabezasDialogos/CabezaViejita.png"));
        villano = new Sprite(new Texture("CabezasDialogos/CabezaVillano.png"));
        fadeObject = new Sprite(new Texture("Dialoguefade.png"));
        dote = new Sprite(new Texture("dote.png"));


    }

    // Dibuja el personaje
    public boolean dibujar(SpriteBatch batch, int DialogueNumber) { //recibe el numero de dialogo
        boolean terminado = false;
        if(!lineStarted){
            lineStarted = true;
            linePointer(DialogueNumber);
        }

        sbatch = batch;
        //sprite.draw(batch); // Dibuja el sprite estático


        //inician los diaologos
        switch (DialogueNumber){
            case 0: //Dialogo 0
                break;
            case 1: //Dialogo 1
                switch (linePoint){
                    case 0://linea 0
                        font.mostrarMensaje(batch,dialogueLines[1][linePoint],600,450);
                        fadeObject.draw(batch);
                        fadeObject.setX(240);
                        fadeObject.setY(250);

                        enfermera.draw(batch);
                        enfermera.setX(300);
                        enfermera.setY(400);
                        break;
                    case 1: //linea 1
                        font.mostrarMensaje(batch,dialogueLines[1][linePoint],600,450);
                        fadeObject.draw(batch);
                        fadeObject.setX(240);
                        fadeObject.setY(250);

                        enfermera.draw(batch);
                        enfermera.setX(300);
                        enfermera.setY(400);
                        break;
                    case 2: //linea 2
                        font.mostrarMensaje(batch,dialogueLines[1][linePoint],600,450);
                        fadeObject.draw(batch);
                        fadeObject.setX(240);
                        fadeObject.setY(250);

                        enfermera.draw(batch);
                        enfermera.setX(300);
                        enfermera.setY(400);
                        break;
                    case 3: // linea 3 ULTIMA
                        font.mostrarMensaje(batch,dialogueLines[1][linePoint],600,450);
                        fadeObject.draw(batch);
                        fadeObject.setX(240);
                        fadeObject.setY(250);

                        enfermera.draw(batch);
                        enfermera.setX(300);
                        enfermera.setY(400);
                        terminado = true; //en la ultima linea se debe regresar true
                        break;
                }
                break;
            case 2: //Dialogo 2
                switch (linePoint){
                    case 0:
                        font.mostrarMensaje(batch,dialogueLines[2][linePoint],2760,450);
                        fadeObject.draw(batch);
                        fadeObject.setX(2500);
                        fadeObject.setY(250);

                        cocinera.draw(batch);
                        cocinera.setX(2560);
                        cocinera.setY(400);
                        break;
                    case 1:
                        font.mostrarMensaje(batch,dialogueLines[2][linePoint],2760,450);
                        fadeObject.draw(batch);
                        fadeObject.setX(2500);
                        fadeObject.setY(250);

                        cocinera.draw(batch);
                        cocinera.setX(2560);
                        cocinera.setY(400);
                        break;
                    case 2:
                        font.mostrarMensaje(batch,dialogueLines[2][linePoint],2760,450);
                        fadeObject.draw(batch);
                        fadeObject.setX(2500);
                        fadeObject.setY(250);

                        cocinera.draw(batch);
                        cocinera.setX(2560);
                        cocinera.setY(400);
                        terminado = true;
                        break;
                    case 3:

                        break;
                    case 4:

                        terminado = true;
                        break;
                }
                break;
            case 3: //Dialogo 3
                switch (linePoint){
                    case 0:
                        font.mostrarMensaje(batch,dialogueLines[1][0],450,450);
                        fadeObject.draw(batch);
                        fadeObject.setX(240);
                        fadeObject.setY(250);

                        cocinera.draw(batch);
                        cocinera.setX(300);
                        cocinera.setY(400);
                        break;
                    case 1:

                        break;
                    case 2:

                        break;
                    case 3:

                        break;
                    case 4:

                        terminado = true;
                        break;
                }
                break;
            //screen
            case 4:
                switch(linePoint){
                    case 0://dialogo 1
                        font.mostrarMensaje(batch,dialogueOne[0][linePoint],450,450);
                        fadeObject.draw(batch);
                        fadeObject.setX(240);
                        fadeObject.setY(250);

                        cocinera.draw(batch);
                        cocinera.setX(300);
                        cocinera.setY(400);
                        break;
                    case 1:

                        break;
                    case 2:

                        break;
                    case 3:

                        break;
                    case 4:

                        terminado = true;
                        break;
                }

                break;
            case 5:

                break;
            case 6:

                break;
            case 7:

                break;
            case 8:

                break;
            case 9:

                break;
            case 10:

                break;
            case 11:

                break;
            case 12:

                break;
            case 13:

                break;
            case 14:

                break;
            case 15:

                break;
            case 16:

                break;
            case 17:

                break;
            case 18:

                break;
        }



        return terminado;
    }
    public void linePointer(int Dialogue){
        System.out.printf("line pointer called");
        counterDialogue = dialogueLines[Dialogue].length * 4.0f;
        counterDialogueStart = 0.0f;
        linePoint = 0;
        linePointerHelperA();
    }
    private void linePointerHelperA(){
        float delay = 4.0f;
        Timer.schedule(new Timer.Task(){
            @Override
            public void run() {
                // Do your work
                counterDialogueStart += 4.0f;
                if(counterDialogueStart>=counterDialogue){

                }else{
                    linePoint+=1;
                    System.out.printf("linepoint:" + linePoint);
                    linePointerHelperA();
                }
            }
        }, delay);
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
