package Trivial;


import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        ArrayList <Persona> jugadores = new ArrayList<>();

        ArrayList<String> hoho = new ArrayList<>();
        hoho.add("Javier 0");
        hoho.add("Pedro 0");
        hoho.add("Luis 0");

        for (String s : hoho) {
            System.out.println(s.substring(0));
        }


        //Partida p = new Partida(teclado);

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
                Ranking.mostrarRanking();
                System.out.print("Presiona enter para continuar...");
                teclado.nextLine();
                teclado.nextLine();

            } else if (eleccion == 3) {
                Historial.mostrarHistorial();

            } else if (eleccion == 4) {

                menuJugadores(teclado, jugadores);
            } else if (eleccion != 5){

                System.out.println("Error! No has escogido un número válido");

            }

        } while (eleccion != 5);


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
                Persona.mostrarJugadores();

            } else if (eleccion == 2) {
                System.out.println("¿Cuál es el nombre del jugador que quieres añadir? (Solo el nombre y sin espacios)");
                nombre = teclado.next();
                jugadores.add(new Persona(nombre));
                jugadores.getLast().añadirJugador(jugadores.getLast());
                Log.escribirEnLog("Se ha añadido un nuevo jugador llamado " + nombre);

            } else if (eleccion == 3) {
                System.out.println("¿Cuál es el nombre del jugador que quieres Eliminar? (Solo el nombre y sin espacios)");
                nombre= teclado.next();
                Persona.eliminarJugador(nombre);
                Log.escribirEnLog("Se ha eliminado el jugador " + nombre);
            } else if (eleccion != 4){

                System.out.println("Error! No has escogido un número válido");
            }

        }while (eleccion != 4);

    }




}
