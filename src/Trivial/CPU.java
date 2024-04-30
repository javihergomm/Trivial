package Trivial;
import static Trivial.Constantes.*;
public class CPU extends Jugador{

    public CPU(String nombre) {
        super(0, nombre);
    }

    public boolean contestarPregunta(int tipoPregunta, String respuesta){

        boolean correcto;
        String contestado;

        if (tipoPregunta == 0){
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                System.err.println("Error al esperar");
            }
            System.out.println(ANSI_LIGHTGREEN + respuesta + ANSI_RESET);
            System.out.println("Enhorabuena, la respuesta es correcta!!");
            correcto=true;
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                System.err.println("Error al esperar");
            }
        } else if (tipoPregunta == 1) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                System.err.println("Error al esperar");
            }
            char[] arrayRespuesta = respuesta.toCharArray();
            if (arrayRespuesta[(arrayRespuesta.length - 1)] == 'a'){
                arrayRespuesta[(arrayRespuesta.length - 1)] = 'b';
            } else {
                arrayRespuesta[(arrayRespuesta.length - 1)] = 'a';
            }

            contestado = new String(arrayRespuesta);
            System.out.println(ANSI_LIGHTGREEN + contestado + ANSI_RESET);
            System.out.println("Mala suerte, a la proxima será. La respuesta correcta era: " + ANSI_LIGHTGREEN + respuesta + ANSI_RESET);
            correcto=false;
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                System.err.println("Error al esperar");
            }

        }else{
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                System.err.println("Error al esperar");
            }
            int alAzar = Constantes.aleatorio.nextInt(4);

            if (alAzar == 0){

                contestado = "a";
            } else if (alAzar == 1) {

                contestado = "b";
            } else if (alAzar == 2) {

                contestado = "c";
            } else {

                contestado = "d";
            }

            System.out.println(ANSI_LIGHTGREEN + contestado + ANSI_RESET);

            if (contestado.equalsIgnoreCase(respuesta)){
                System.out.println("Enhorabuena, la respuesta es correcta!!");
                correcto=true;
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    System.err.println("Error al esperar");
                }
            } else{
                System.out.println("Mala suerte, a la proxima será. La respuesta correcta era: " + ANSI_LIGHTGREEN + respuesta + ANSI_RESET);
                correcto=false;
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    System.err.println("Error al esperar");
                }
            }
        }

        return correcto;
    }
}
