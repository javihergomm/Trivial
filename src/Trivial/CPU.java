package Trivial;

public class CPU extends Jugador{

    int puntuacion;
    String nombre;

    public CPU(String nombre) {
        super(0, nombre);
    }

    public boolean contestarPregunta(int tipoPregunta, String respuesta){

        boolean correcto;
        String contestado;

        if (tipoPregunta == 0){
            contestado=respuesta;

            System.out.println("Enhorabuena, la respuesta es correcta!!");
            correcto=true;
        } else if (tipoPregunta == 1) {
            char[] arrayRespuesta = respuesta.toCharArray();
            if (arrayRespuesta[(arrayRespuesta.length - 1)] == 'a'){
                arrayRespuesta[(arrayRespuesta.length - 1)] = 'b';
            } else {
                arrayRespuesta[(arrayRespuesta.length - 1)] = 'a';
            }

            contestado = new String(arrayRespuesta);
            System.out.println(contestado);
            System.out.println("Mala suerte, a la proxima será. La respuesta correcta era: " + respuesta);
            correcto=false;

        }else{

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

            if (contestado.equalsIgnoreCase(respuesta)){
                System.out.println("Enhorabuena, la respuesta es correcta!!");
                correcto=true;
            } else{
                System.out.println("Mala suerte, a la proxima será. La respuesta correcta era: " + respuesta);
                correcto=false;
            }
        }

        return correcto;
    }
}
