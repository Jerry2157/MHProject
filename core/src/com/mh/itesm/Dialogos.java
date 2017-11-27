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
    private Sprite fadeObject2;
    private Sprite fadeObject3;
    private Sprite fadeObject4;
    private Sprite fadeObject5;
    private Sprite fadeObject6;
    private Sprite fadeObject7;
    private Sprite fadeObject8;
    private Sprite fadeObject9;
    private Sprite fadeObject10;

    private Sprite dote;

    private int posAvatarX;
    private int posAvatarY;

    private SpriteBatch sbatch;
    private int linePoint;

    private String[][] dialogueLines;

    private boolean lineStarted;
    private float counterDialogue;
    private float counterDialogueStart;

    // Recibe una imagen con varios frames (ver marioSprite.png)
    public Dialogos() {
        dialogueLines = new String[][]{
                //0
                {"Hola, soy tu enfermera, \n estas en el Manicomio Colonia, ",
                "Vimos tus antecedentes, \n parece que padeces esquizofrenia",
                        "Eres el principal sospechoso \n en el acuchillamiento de tu familia",
                "Ve un piso abajo y platica con el policia, \n el te dira que hacer"},
                //1
                {"Hola Steven, \n Soy el oficial acargo de esta zona del manicomio",
                "Lamentablemente no podemos dejarte ir, \n para eso tendras que probar tu inocencia",
                        "Tu esposa e hija se encuentran hospitalizadas, \n aun no nos confirman que tan graves estan",
                        "Ve a la cocina que esta a tu izquierda, \n la cocinera es una de tus vecinas quiere decirte algo"},
                //2
                {"Hola, soy la cocinera de este lugar \n asi como tu vecina",
                "Antes de que ocurriera el ataque \n yo estaba caminando por la acera \n frente a tu casa y logre ver a un hombre \n que miraba fijamente el interior de tu casa ",
                        "El hombre \n era de una edad parecida a la tuya, \n vestia unas botas color cafe, su pelo era claro ",
                        "Es todo lo que recuerdo. \n Deberias comer un poco. \n Sientate en la segunda mesa de la izquierda \n te prepare un plato",
                        "Despues dirijete a tu habitacion y espera"
                },
                //3
                {"Disculpa jovencito \n puedes ayudarme a encontrar mi radio \n esta en el sotano",
                        "Se me perdio ayer \n AYUDA"},
                //4
                {"Steven!!\n Pensaba que estabas en tu cuarto",
                "Ya que vi a alguien ahí, \n deberias regresar"
                },
                //5
                {"Tu no mereces lo que tienes. \n Deberias estar aqui internado \n Para siempre",
                        "Me alegro de haber herido \n a tu esposa e hija\n espero no se recuperen"
                },
                //6
                {"Hola Steven \n Yo puedo ayudarte dandote informacion",
                        "Pero a cambio me tienes que ayudar \n a buscar a mi gato \n regresa cuando lo encuentres "},
                //7
                {"Volviste\n Y encontraste a mi gato\n bien hecho",
                "Ve y busca al chico nuevo\n en la cuarta habitacion\n el te ayudara"},
                //8
                {"Todo fue tan sencillo\n Solo le dije a tu familia \n que era un amigo tuyo\n y no preguntaron",
                "Me dejaron pasar a tu casa.\n Seguro escuchaste sus gritos",
                        "Despues fue sencillo involucrarte \n Gracias a tu historial medico",
                        "Que haras\n quien le creeria a un esquizofrenico"
                },
                //9
                {}
        };

        lineStarted = false;
        linePoint=0;

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
        fadeObject2 = new Sprite(new Texture("Dialoguefade.png"));
        fadeObject3 = new Sprite(new Texture("Dialoguefade.png"));
        fadeObject4=new Sprite(new Texture("Dialoguefade.png"));
        fadeObject5=new Sprite(new Texture("Dialoguefade.png"));
        fadeObject6=new Sprite(new Texture("Dialoguefade.png"));
        fadeObject7=new Sprite(new Texture("Dialoguefade.png"));
        fadeObject8=new Sprite(new Texture("Dialoguefade.png"));
        fadeObject9=new Sprite(new Texture("Dialoguefade.png"));
        fadeObject10=new Sprite(new Texture("Dialoguefade.png"));

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
                        font.mostrarMensaje(batch,dialogueLines[0][linePoint],600,450);
                        fadeObject.draw(batch);
                        fadeObject.setX(240);
                        fadeObject.setY(250);

                        enfermera.draw(batch);
                        enfermera.setX(300);
                        enfermera.setY(400);
                        break;
                    case 1: //linea 1
                        font.mostrarMensaje(batch,dialogueLines[0][linePoint],600,450);
                        fadeObject.draw(batch);
                        fadeObject.setX(240);
                        fadeObject.setY(250);

                        enfermera.draw(batch);
                        enfermera.setX(300);
                        enfermera.setY(400);
                        break;
                    case 2: //linea 2
                        font.mostrarMensaje(batch,dialogueLines[0][linePoint],600,450);
                        fadeObject.draw(batch);
                        fadeObject.setX(240);
                        fadeObject.setY(250);

                        enfermera.draw(batch);
                        enfermera.setX(300);
                        enfermera.setY(400);

                        break;
                    case 3: // linea 3 ULTIMA
                        font.mostrarMensaje(batch,dialogueLines[0][linePoint],600,450);
                        fadeObject.draw(batch);
                        fadeObject.setX(240);
                        fadeObject.setY(250);

                        enfermera.draw(batch);
                        enfermera.setX(300);
                        enfermera.setY(400);

                        break;
                    case 4:

                        terminado = true; //en la ultima linea se debe regresar true
                }
                break;
            case 2: //Dialogo 2 POLICIA 1
                switch (linePoint){
                    case 0:
                        font.mostrarMensaje(batch,dialogueLines[1][linePoint],660,450);
                        fadeObject2.draw(batch);
                        fadeObject2.setX(240);
                        fadeObject2.setY(250);

                        policia.draw(batch);
                        policia.setX(300);
                        policia.setY(400);
                        break;
                    case 1:
                        font.mostrarMensaje(batch,dialogueLines[1][linePoint],660,450);
                        fadeObject2.draw(batch);
                        fadeObject2.setX(240);
                        fadeObject2.setY(250);

                        policia.draw(batch);
                        policia.setX(300);
                        policia.setY(400);
                        break;
                    case 2:
                        font.mostrarMensaje(batch,dialogueLines[1][linePoint],660,450);
                        fadeObject2.draw(batch);
                        fadeObject2.setX(240);
                        fadeObject2.setY(250);

                        policia.draw(batch);
                        policia.setX(300);
                        policia.setY(400);
                        break;
                    case 3:
                        font.mostrarMensaje(batch,dialogueLines[1][linePoint],660,450);
                        fadeObject2.draw(batch);
                        fadeObject2.setX(240);
                        fadeObject2.setY(250);

                        policia.draw(batch);
                        policia.setX(300);
                        policia.setY(400);
                        break;
                    case 4:

                        terminado = true;
                        break;
                }
                break;
            case 3: //Dialogo 3 cocina
                switch (linePoint){
                    case 0:
                        font.mostrarMensaje(batch,dialogueLines[2][linePoint],660,450);
                        fadeObject3.draw(batch);
                        fadeObject3.setX(240);
                        fadeObject3.setY(250);

                        cocinera.draw(batch);
                        cocinera.setX(300);
                        cocinera.setY(400);
                        break;
                    case 1:
                        font.mostrarMensaje(batch,dialogueLines[2][linePoint],660,450);
                        fadeObject3.draw(batch);
                        fadeObject3.setX(240);
                        fadeObject3.setY(250);

                        cocinera.draw(batch);
                        cocinera.setX(300);
                        cocinera.setY(400);
                        break;
                    case 2:
                        font.mostrarMensaje(batch,dialogueLines[2][linePoint],660,450);
                        fadeObject3.draw(batch);
                        fadeObject3.setX(240);
                        fadeObject3.setY(250);

                        cocinera.draw(batch);
                        cocinera.setX(300);
                        cocinera.setY(400);
                        break;
                    case 3:
                        font.mostrarMensaje(batch,dialogueLines[2][linePoint],660,450);
                        fadeObject3.draw(batch);
                        fadeObject3.setX(240);
                        fadeObject3.setY(250);

                        cocinera.draw(batch);
                        cocinera.setX(300);
                        cocinera.setY(400);
                        break;
                    case 4:
                        font.mostrarMensaje(batch,dialogueLines[2][linePoint],660,450);
                        fadeObject3.draw(batch);
                        fadeObject3.setX(240);
                        fadeObject3.setY(250);

                        cocinera.draw(batch);
                        cocinera.setX(300);
                        cocinera.setY(400);
                        break;
                    case 5:
                        terminado = true;
                        break;
                }
                break;
            //screen
            case 4: //dialogos viejita nivel sotano
                switch(linePoint){
                    case 0://dialogo 1
                        font.mostrarMensaje(batch,dialogueLines[3][linePoint],660,450);
                        fadeObject4.draw(batch);
                        fadeObject4.setX(240);
                        fadeObject4.setY(250);

                        viejita.draw(batch);
                        viejita.setX(300);
                        viejita.setY(400);
                        break;
                    case 1:
                        font.mostrarMensaje(batch,dialogueLines[3][linePoint],660,450);
                        fadeObject4.draw(batch);
                        fadeObject4.setX(240);
                        fadeObject4.setY(250);

                        viejita.draw(batch);
                        viejita.setX(300);
                        viejita.setY(400);
                        break;
                    case 2:
                        terminado = true;
                        break;
                    case 3:

                        break;
                    case 4:


                        break;
                }

                break;
            case 5: //Screen fourteen enfermera
                switch(linePoint) {
                    case 0://dialogo 1
                        font.mostrarMensaje(batch, dialogueLines[4][linePoint], 660, 450);
                        fadeObject5.draw(batch);
                        fadeObject5.setX(240);
                        fadeObject5.setY(250);

                        enfermera.draw(batch);
                        enfermera.setX(300);
                        enfermera.setY(400);
                        break;
                    case 1:
                        font.mostrarMensaje(batch, dialogueLines[4][linePoint], 660, 450);
                        fadeObject5.draw(batch);
                        fadeObject5.setX(240);
                        fadeObject5.setY(250);

                        enfermera.draw(batch);
                        enfermera.setX(300);
                        enfermera.setY(400);
                        break;
                    case 2:
                        terminado = true;
                        break;
                }

                break;
            case 6: //Dialogos villano cuando admite que el es el malo
                switch(linePoint) {
                    case 0://dialogo 1
                        font.mostrarMensaje(batch, dialogueLines[5][linePoint], 660, 450);
                        fadeObject6.draw(batch);
                        fadeObject6.setX(240);
                        fadeObject6.setY(250);

                        villano.draw(batch);
                        villano.setX(300);
                        villano.setY(400);
                        break;
                    case 1:
                        font.mostrarMensaje(batch, dialogueLines[5][linePoint], 660, 450);
                        fadeObject6.draw(batch);
                        fadeObject6.setX(240);
                        fadeObject6.setY(250);

                        villano.draw(batch);
                        villano.setX(300);
                        villano.setY(400);
                        break;
                    case 2:
                        terminado = true;
                        break;
                }

                break;
            case 7:// Director parte 1 recuperar gato
                switch(linePoint) {
                    case 0://dialogo 1
                        font.mostrarMensaje(batch, dialogueLines[6][linePoint], 660, 450);
                        fadeObject7.draw(batch);
                        fadeObject7.setX(240);
                        fadeObject7.setY(250);

                        director.draw(batch);
                        director.setX(300);
                        director.setY(400);
                        break;
                    case 1:
                        font.mostrarMensaje(batch, dialogueLines[6][linePoint], 660, 450);
                        fadeObject7.draw(batch);
                        fadeObject7.setX(240);
                        fadeObject7.setY(250);

                        director.draw(batch);
                        director.setX(300);
                        director.setY(400);
                        break;
                    case 2:
                        terminado = true;
                        break;
                }

                break;
            case 8: //Director 2 ve con el chico nuevo
                switch(linePoint) {
                    case 0://dialogo 1
                        font.mostrarMensaje(batch, dialogueLines[7][linePoint], 660, 450);
                        fadeObject8.draw(batch);
                        fadeObject8.setX(240);
                        fadeObject8.setY(250);

                        director.draw(batch);
                        director.setX(300);
                        director.setY(400);
                        break;
                    case 1:
                        font.mostrarMensaje(batch, dialogueLines[7][linePoint], 660, 450);
                        fadeObject8.draw(batch);
                        fadeObject8.setX(240);
                        fadeObject8.setY(250);

                        director.draw(batch);
                        director.setX(300);
                        director.setY(400);
                        break;
                    case 2:
                        terminado = true;
                        break;
                }
                break;
            case 9: //el villano revela su crimen
                switch (linePoint){
                    case 0://linea 0
                        font.mostrarMensaje(batch,dialogueLines[8][linePoint],660,450);
                        fadeObject9.draw(batch);
                        fadeObject9.setX(240);
                        fadeObject9.setY(250);

                        villano.draw(batch);
                        villano.setX(300);
                        villano.setY(400);
                        break;
                    case 1: //linea 1
                        font.mostrarMensaje(batch,dialogueLines[8][linePoint],660,450);
                        fadeObject9.draw(batch);
                        fadeObject9.setX(240);
                        fadeObject9.setY(250);

                        villano.draw(batch);
                        villano.setX(300);
                        villano.setY(400);
                        break;
                    case 2: //linea 2
                        font.mostrarMensaje(batch,dialogueLines[8][linePoint],660,450);
                        fadeObject9.draw(batch);
                        fadeObject9.setX(240);
                        fadeObject9.setY(250);

                        villano.draw(batch);
                        villano.setX(300);
                        villano.setY(400);

                        break;
                    case 3: // linea 3 ULTIMA
                        font.mostrarMensaje(batch,dialogueLines[8][linePoint],660,450);
                        fadeObject9.draw(batch);
                        fadeObject9.setX(240);
                        fadeObject9.setY(250);

                        villano.draw(batch);
                        villano.setX(300);
                        villano.setY(400);

                        break;
                    case 4:

                        terminado = true; //en la ultima linea se debe regresar true
                }
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
        //System.out.printf("line pointer called");
        //counterDialogue = dialogueLines[Dialogue].length * 4.0f;
        //counterDialogueStart = 0.0f;
        linePoint = 0;
        linePointerHelperA();
    }
    private void linePointerHelperA(){
        float delay = 6.0f;
        Timer.schedule(new Timer.Task(){
            @Override
            public void run() {
                // Do your work
                //counterDialogueStart += 5.0f;
                //if(counterDialogueStart>=counterDialogue){

                //}else{
                    linePoint+=1;
                    //System.out.printf("linepoint:" + linePoint);
                    linePointerHelperA();
                //}
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
