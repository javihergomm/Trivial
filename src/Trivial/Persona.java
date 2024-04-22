package Trivial;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class Persona {

    int puntuacion;
    String nombre;

    public Persona(int puntuacion, String nombre) {
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


}
