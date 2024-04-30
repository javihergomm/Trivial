package Trivial;


import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<Persona> jugadores = Constantes.TodosLosJugadores();

        menu(jugadores);

    }

    public static void menu (ArrayList<Persona> jugadores){
        int eleccion;

        System.out.println("Bienvenido a ¿Quien quiere ser aprobado?");

        do {
            boolean error = false;
            System.out.println("¿Que quieres Hacer?");
            System.out.println("1. Iniciar una nueva partida");
            System.out.println("2. Ranking");
            System.out.println("3. Historial");
            System.out.println("4. Jugadores");
            System.out.println("5. Salir");
            try{
                eleccion = Constantes.teclado.nextInt();
            }catch (Exception e){
                System.err.println("Debes introducir un numero");
                Constantes.teclado.nextLine();
                eleccion = 7;
                error = true;
            }

            //INICIAR PARTIDA
            if (eleccion == 1){
                boolean encontrado = false;
                Partida p = new Partida();
                ArrayList<Jugador> jugadoresQueJuegan = Partida.crearJugadores(p.numJugadores, p.numPersonas);
                jugadoresQueJuegan = (p.jugarPartida(p, jugadoresQueJuegan));
                for (int i = jugadoresQueJuegan.size()-1; i >= 0; i--) {
                    if (jugadoresQueJuegan.get(i) instanceof CPU){
                        jugadoresQueJuegan.remove(i);
                    }
                }
                jugadores = Constantes.actualizarRanking(jugadores, jugadoresQueJuegan);
                Constantes.ordenarRanking(jugadores);

                //MOSTRAR RANKING
            } else if (eleccion == 2) {
                Constantes.ordenarRanking(jugadores);
                for (Persona jugadore : jugadores) {
                    System.out.println(jugadore.nombre + " " +jugadore.puntosEnElRanking);
                }
                Log.escribirEnLog("Se ha mostrado el Ranking");
                System.out.print("Presiona enter para continuar...");
                Constantes.teclado.nextLine();
                Constantes.teclado.nextLine();

                //MOSTRAR HISTORIAL
            } else if (eleccion == 3) {

                try {
                    Constantes.comprobarArchivo(Constantes.archivoHistorial);
                    Constantes.lineasHistorial = (ArrayList<String>) Files.readAllLines(Constantes.archivoHistorial);
                } catch (IOException e) {
                    System.err.println("No se ha podido leer el archivo diccionario.txt");
                }

                for (String lineas : Constantes.lineasHistorial){
                    System.out.println(lineas);
                }
                Log.escribirEnLog("Se ha mostrado el Ranking");
                System.out.print("Presiona enter para continuar...");
                Constantes.teclado.nextLine();
                Constantes.teclado.nextLine();

                //SUBMENU DE JUGADORES
            } else if (eleccion == 4) {

                menuJugadores(jugadores);
            } else if (eleccion != 5 && !error){

                System.err.println("Error! No has escogido un número válido");

            }

        } while (eleccion != 5);


    }


    public static void menuJugadores(ArrayList<Persona> jugadores){
        String nombre;
        int eleccion;
        boolean error=false;
        do {
            System.out.println("Este es el menú de jugadores, ¿Que quieres Hacer?");
            System.out.println("1. Ver Jugadores");
            System.out.println("2. Añadir Jugador");
            System.out.println("3. Eliminar Jugador");
            System.out.println("4. Volver al menú principal");
            try{
                eleccion = Constantes.teclado.nextInt();
            }catch (Exception e){
                System.err.println("Debes introducir un numero");
                Constantes.teclado.nextLine();
                eleccion = 7;
                error = true;
            }

            //VER JUGADORES
            if (eleccion == 1){
                for (Jugador jugadore : jugadores) {
                    System.out.println(jugadore.nombre);
                }
                Log.escribirEnLog("Se han mostrado los jugadores");
                System.out.print("Presiona enter para continuar...");
                Constantes.teclado.nextLine();
                Constantes.teclado.nextLine();

                //AÑADIR JUGADOR
            } else if (eleccion == 2) {
                boolean todoOK = true;
                String primeros3;
                    do {
                        primeros3="";
                        todoOK=true;
                        System.out.println("¿Cuál es el nombre del jugador que quieres añadir? (Solo el nombre y sin espacios)");
                        System.out.println("O escribe volver para no añadir ningun jugador");
                        nombre = Constantes.teclado.next();
                        if(nombre.length() >= 3){
                            primeros3 = nombre.substring(0, 3);
                        }
                        if (primeros3.equalsIgnoreCase("cpu")) {
                            System.err.println("El nombre no puede empezar por 'CPU'");
                            todoOK = false;
                        }
                    }while (!todoOK);
                    if(nombre.equalsIgnoreCase("volver")){

                    }else {
                        do{

                            todoOK=true;
                            for (Persona jugadore : jugadores) {
                                if (nombre.equalsIgnoreCase(jugadore.nombre)) {
                                    todoOK = false;
                                }
                            }
                            if (!todoOK) {
                                System.err.println("Ese jugador ya existe, por favor introduce otro");
                                System.out.println("¿Cuál es el nombre del jugador que quieres añadir? (Solo el nombre y sin espacios)");
                                System.out.println("O escribe volver para no añadir ningun jugador");
                                nombre = Constantes.teclado.next();
                            }
                        }while (!todoOK);
                        jugadores.add(new Persona(nombre, 0));
                        Persona.añadirJugador(jugadores.getLast());
                        Log.escribirEnLog("Se ha añadido un nuevo jugador llamado " + nombre);
                    }

                //ELIMINAR JUGADOR
            } else if (eleccion == 3) {
                System.out.println("¿Cuál es el nombre del jugador que quieres Eliminar? (Solo el nombre y sin espacios)");
                nombre= Constantes.teclado.next();
                Persona.eliminarJugador(nombre);
                Log.escribirEnLog("Se ha eliminado el jugador " + nombre);
            } else if (eleccion != 4 && !error){

                System.out.println("Error! No has escogido un número válido");
            }

        }while (eleccion != 4);
    }
}
