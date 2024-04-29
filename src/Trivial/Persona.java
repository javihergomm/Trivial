package Trivial;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;


public class Persona extends Jugador{

    int puntosEnElRanking;

    public Persona(String nombre, int puntosEnElRanking) {
        super(0, nombre);
        this.puntosEnElRanking = puntosEnElRanking;
    }


    public static void añadirJugador(Persona jugador){
        try {
            Files.write(Constantes.archivoRanking, (jugador.getNombre() + ' ' + jugador.getPuntuacion() + '\n').getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo Ranking.txt");
        }

    }

    public static void eliminarJugador(String jugador){
        Constantes.comprobarArchivo(Constantes.archivoRanking);
        ArrayList<String> lineasRanking = Constantes.lineasRanking;
        boolean encontrado = false;

        for (int i=0; i < lineasRanking.size(); i++){

            if (jugador.equalsIgnoreCase(lineasRanking.get(i).split(" ")[0])){
                lineasRanking.remove(i);
                encontrado = true;
            }
        }

        if (encontrado) {
            try {
                Files.write(Constantes.archivoRanking, lineasRanking);
            } catch (IOException e) {
                System.err.println("Ha habido un error al escribir en el archivo Ranking.txt");
            }
        }else{
            System.err.println("Ese jugador no existe");
        }
    }
    public boolean contestarPregunta(String respuesta){
        boolean correcto;
        String contestado;
        contestado = Constantes.teclado.next();

        if (contestado.equalsIgnoreCase(respuesta)){
                System.out.println("Enhorabuena, la respuesta es correcta!!");
                correcto=true;
        } else{
                System.out.println("Mala suerte, a la proxima será. La respuesta correcta era: " + respuesta);
                correcto=false;
        }
        return correcto;

    }

    public int getPuntosEnElRanking() {
        return puntosEnElRanking;
    }

    public void setPuntosEnElRanking(int puntosEnElRanking) {
        this.puntosEnElRanking = puntosEnElRanking;
    }
}
