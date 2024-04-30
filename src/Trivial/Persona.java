package Trivial;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

import static Trivial.Constantes.ANSI_LIGHTGREEN;
import static Trivial.Constantes.ANSI_RESET;


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

    public static void eliminarJugador(String jugador, ArrayList<Persona> jugadores){
        Constantes.comprobarArchivo(Constantes.archivoRanking);
        boolean encontrado = false;

        for (int i=0; i < jugadores.size(); i++){

            if (jugador.equalsIgnoreCase(jugadores.get(i).nombre)){
                jugadores.remove(i);
                encontrado = true;
            }
        }


        if (encontrado) {
            try{
                Files.delete(Constantes.archivoRanking);
                Files.createFile(Constantes.archivoRanking);
            }catch (IOException e){
                System.err.println("Error al borrar y crear el archivo Ranking.txt");
            }
            for (int i = 0; i < jugadores.size(); i++){
                try {
                    Files.writeString(Constantes.archivoRanking, (jugadores.get(i).getNombre() + " " + jugadores.get(i).getPuntuacion()), StandardOpenOption.APPEND);
                } catch (IOException e) {
                    System.err.println("Ha habido un error al escribir en el archivo Ranking.txt");
                }
            }

        }else{
            System.err.println("Ese jugador no existe");
        }
    }
    public boolean contestarPregunta(String respuesta){
        boolean correcto;
        String contestado;
        contestado = Constantes.teclado.nextLine();

        if (contestado.equalsIgnoreCase(respuesta)){
                System.out.println("Enhorabuena, la respuesta es correcta!!");
                correcto=true;
        } else{
                System.out.println("Mala suerte, a la proxima será. La respuesta correcta era: " + ANSI_LIGHTGREEN + respuesta + ANSI_RESET);
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
