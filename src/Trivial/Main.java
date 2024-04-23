package Trivial;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        ArrayList <Persona> jugadores = new ArrayList<>();
        Partida p = new Partida(teclado);

        Random random = new Random();

        int numero;
        while (true){
            numero= random.nextInt(0,3);

            if (numero == 0){
                System.out.println("Ha tocado una pregunta de Letras");
                p.preguntaLetras(teclado);
            } else if (numero == 1) {
                System.out.println("Ha tocado una pregunta de Ingles");

                p.preguntaIngles(teclado);
            } else{
                System.out.println("Ha tocado una pregunta de matemáticas");

                p.preguntaMates(teclado);
            }

        }


        //menu(teclado, jugadores);

    }

    public static void menu(Scanner teclado, ArrayList<Persona> jugadores){
        int eleccion;

        System.out.println("Bienvenido a ¿Quien quiere ser aprobado?");

        do {

            System.out.println("¿Que quieres Hacer?");
            System.out.println("1. Iniciar una nueva partida");
            System.out.println("2. Ranking");
            System.out.println("3. Historial");
            System.out.println("4. Jugadores");
            System.out.println("5. Salir");
            eleccion = teclado.nextInt();

            if (eleccion == 1){
                Partida p = new Partida(teclado);
                p.jugarPartida(p);

            } else if (eleccion == 2) {
                mostrarRanking();
                System.out.print("Presiona enter para continuar...");
                teclado.nextLine();
                teclado.nextLine();

            } else if (eleccion == 3) {
                mostrarHistorial();

            } else if (eleccion == 4) {

                menuJugadores(teclado, jugadores);
            } else if (eleccion != 5){

                System.out.println("Error! No has escogido un número válido");

            }

        } while (eleccion != 5);


    }

    public static void mostrarRanking() {
        Path archivo = Paths.get("src/archivos/Ranking.txt");
        try {

            List<String> lineas = Files.readAllLines(archivo);

            for (String linea : lineas) {
                System.out.println(linea);
            }

        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        }

        Log.escribirEnLog("Se ha mostrado el Ranking");
    }

    public static void mostrarHistorial() {


    }

    public static void menuJugadores(Scanner teclado, ArrayList<Persona> jugadores){
        String nombre;
        int eleccion;
        do {
            System.out.println("Este es el menú de jugadores, ¿Que quieres Hacer?");
            System.out.println("1. Ver Jugadores");
            System.out.println("2. Añadir Jugador");
            System.out.println("3. Eliminar Jugador");
            System.out.println("4. Volver al menú principal");
            eleccion = teclado.nextInt();

            if (eleccion == 1){


            } else if (eleccion == 2) {
                System.out.println("¿Cuál es el nombre del jugador que quieres añadir? (Solo el nombre y sin espacios)");
                nombre = teclado.next();
                jugadores.add(new Persona(nombre));
                jugadores.getLast().añadirJugador(jugadores.getLast());
                Log.escribirEnLog("Se ha añadido un nuevo jugador llamado " + nombre);

            } else if (eleccion == 3) {


            } else if (eleccion != 4){

                System.out.println("Error! No has escogido un número válido");
            }

        }while (eleccion != 4);

    }




}
