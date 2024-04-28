package Trivial;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Constantes {

    public static Scanner teclado = new Scanner(System.in);
    public static Random aleatorio = new Random();
    public static ArrayList<String> lineasRanking;
    public static ArrayList<String> lineasIngles;
    public static ArrayList<String> lineasDiccionario;
    public static ArrayList<String> lineasHistorial;

    public static Path archivoRanking = Paths.get("src/archivos/Ranking.txt");
    public static Path archivoIngles = Paths.get("src/archivos/ingles.txt");
    public static Path archivoDiccionario = Paths.get("src/archivos/diccionario.txt");
    public static Path archivoHistorial = Paths.get("src/archivos/Historial.txt");

    //Rellena archivoRanking
    static {
        try {
            comprobarArchivo(archivoRanking);
            lineasRanking = (ArrayList<String>) Files.readAllLines(archivoRanking);
        } catch (IOException e) {
            System.err.println("No se ha podido leer el archivo Ranking.txt");
        }
    }
    //Rellena archivoIngles
    static {
        try {
            comprobarArchivo(archivoIngles);
            lineasIngles = (ArrayList<String>) Files.readAllLines(archivoIngles);
        } catch (IOException e) {
            System.err.println("No se ha podido leer el archivo ingles.txt");
        }
    }
    //Rellena archivoDiccionario
    static {
        try {
            comprobarArchivo(archivoDiccionario);
            lineasDiccionario = (ArrayList<String>) Files.readAllLines(archivoDiccionario);
        } catch (IOException e) {
            System.err.println("No se ha podido leer el archivo diccionario.txt");
        }
    }
    static {
        try {
            comprobarArchivo(archivoHistorial);
            lineasHistorial = (ArrayList<String>) Files.readAllLines(archivoHistorial);
        } catch (IOException e) {
            System.err.println("No se ha podido leer el archivo Historial.txt");
        }
    }

    //Comprueba si el archivo introducido existe, si no es así lo crea.
    public static void comprobarArchivo(Path archivo){
        if (!Files.exists(archivo)){
            try {
                Files.createFile(archivo);
            }catch (IOException e){
                System.err.println("Error al crear el archivo" + archivo);
            }
        }
    }

    //Pasa el Arraylist archivoRanking a un Arraylist de la clase Persona
    public static ArrayList<Persona> TodosLosJugadores(){
        ArrayList<Persona> TodosLosJugadores = new ArrayList<>();
        comprobarArchivo(archivoRanking);
        for (String s : lineasRanking) {
            TodosLosJugadores.add(new Persona(((s.split(" "))[0]), Integer.parseInt((s.split(" "))[1])));
        }
        return TodosLosJugadores;
    }


    public static ArrayList<Persona> actualizarRanking(ArrayList<Persona> jugadores) {

        for (int i=0; i < jugadores.size(); i++) {
            for (String linea : lineasRanking) {
                String[] partes = linea.split(" ");
                if (partes[0].equalsIgnoreCase(jugadores.get(i).getNombre())) {
                    int puntosActuales = Integer.parseInt(partes[1]);
                    jugadores.get(i).setPuntuacion(jugadores.get(i).getPuntuacion() + puntosActuales);
                }
            }
        }
        return jugadores;

    }

    public static void ordenarRanking(ArrayList<Persona> jugadores){

        jugadores.sort((o1, o2) -> Integer.compare(o2.getPuntuacion(), o1.getPuntuacion()));
        try {
            Files.delete(archivoRanking);
            Files.createFile(archivoRanking);
            for (Jugador jugadore : jugadores) {
                Files.write(archivoRanking, (jugadore.nombre + ' ' + (jugadore.puntuacion) + '\n').getBytes(), StandardOpenOption.APPEND);
            }
        } catch (IOException e) {
            System.err.println("Error al actualizar el Ranking");
        }

    }
}
