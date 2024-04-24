package Trivial;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class Persona {

    int puntuacion;
    String nombre;

    public Persona(String nombre) {
        this.puntuacion = 0;
        this.nombre = nombre;
    }

    public void añadirJugador(Persona jugador){

        jugador.nombre = jugador.nombre + " " + jugador.puntuacion + '\n';
        Path archivo = Paths.get("src/archivos/Ranking.txt");

        try {
            Files.write(archivo, jugador.nombre.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo: " + e.getMessage());
        }

    }

    public static void eliminarJugador(String jugador){
        Path archivo = Paths.get("src/archivos/Ranking.txt");
        Constantes.comprobarArchivo(archivo);
        ArrayList<String> lineasRanking = null;
        try {
            lineasRanking = (ArrayList<String>) Files.readAllLines(archivo);
            for (int i=0; i < lineasRanking.size(); i++){

                lineasRanking.set(i, lineasRanking.get(i).substring(0, ' '));

            }
            for (int i=0; i < lineasRanking.size(); i++){

                if (jugador.equalsIgnoreCase(lineasRanking.get(i))){
                    lineasRanking.remove(i);
                }
            }

        }catch(IOException e){
            System.err.println("Ha habido un error al intetntar leer el ranking");
        }
        try{
            Files.write(archivo, lineasRanking);
        }catch (IOException e){
            System.err.println("Ha habido un error al escribir en el archivo Ranking.txt");
        }

    }

    public static void mostrarJugadores() {
        Path archivo = Paths.get("src/archivos/Ranking.txt");
        Constantes.comprobarArchivo(archivo);
        try {

            List<String> lineas = Files.readAllLines(archivo);
            for (int i=0; i< lineas.size(); i++) {
                if (lineas.get(i).length() > 2){
                    lineas.set(i, lineas.get(i).substring(0, ' '));
                }
            }

        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        }

        Log.escribirEnLog("Se ha mostrado el Ranking");
    }
}
