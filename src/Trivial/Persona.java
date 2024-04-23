package Trivial;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

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

    public void eliminarJugador(Persona jugador){
        Path archivo = Paths.get("src/archivos/Ranking.txt");
        ArrayList<String> lineasRanking = new ArrayList<>();
        String lineaABorrar = "";
        boolean u = false;
        int i=0;
        try {
            lineasRanking = (ArrayList<String>) Files.readAllLines(archivo);
            do{
                try {
                    lineaABorrar = lineasRanking.get(i);
                    i++;
                }catch (IndexOutOfBoundsException e){
                    System.err.println("Ese jugador no está registrado en el sistema");
                    u = true;
                }

            }while (lineaABorrar.equals(jugador.nombre) || u);

        }catch(IOException e){
            System.err.println("Ha habido un error al intetntar leer el ranking");
        }

    }


}
