package Trivial;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Persona {

    int puntuacion;
    String nombre;

    public Persona(String nombre, int puntuacion) {
        this.puntuacion = puntuacion;
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

    public int getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
