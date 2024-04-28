package Trivial;


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


            if (eleccion == 1){
                Partida p = new Partida();
                ArrayList<Jugador> jugadoresQueJuegan = Partida.crearJugadores(p.numJugadores, p.numPersonas);
                jugadoresQueJuegan = (p.jugarPartida(p, jugadoresQueJuegan));
                for (int i=jugadoresQueJuegan.size()-1; i > 0; i--) {
                    if (jugadoresQueJuegan.get(i) instanceof CPU){
                        jugadoresQueJuegan.remove(i);
                    }
                }
                for (Jugador jugador : jugadoresQueJuegan) {
                    for (int j = 0; j < jugadores.size(); j++) {
                        if (jugador.nombre.equalsIgnoreCase(jugadores.get(j).nombre)) {
                            jugadores.set(j, (Persona) jugador);
                        }
                    }
                }
                Constantes.ordenarRanking(Constantes.actualizarRanking(jugadores));

            } else if (eleccion == 2) {
                for (Jugador jugadore : jugadores) {
                    System.out.println(jugadore.nombre + " " +jugadore.puntuacion);
                }
                Log.escribirEnLog("Se ha mostrado el Ranking");
                System.out.print("Presiona enter para continuar...");
                Constantes.teclado.nextLine();
                Constantes.teclado.nextLine();

            } else if (eleccion == 3) {

                for (String hist : Constantes.lineasHistorial){
                    System.out.println(hist);
                }
                Log.escribirEnLog("Se ha mostrado el Ranking");
                System.out.print("Presiona enter para continuar...");
                Constantes.teclado.nextLine();
                Constantes.teclado.nextLine();

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
        do {
            System.out.println("Este es el menú de jugadores, ¿Que quieres Hacer?");
            System.out.println("1. Ver Jugadores");
            System.out.println("2. Añadir Jugador");
            System.out.println("3. Eliminar Jugador");
            System.out.println("4. Volver al menú principal");
            eleccion = Constantes.teclado.nextInt();

            if (eleccion == 1){
                for (Jugador jugadore : jugadores) {
                    System.out.println(jugadore.nombre);
                }
                Log.escribirEnLog("Se han mostrado los jugadores");
                System.out.print("Presiona enter para continuar...");
                Constantes.teclado.nextLine();
                Constantes.teclado.nextLine();
            } else if (eleccion == 2) {

                boolean todoOK;

                    System.out.println("¿Cuál es el nombre del jugador que quieres añadir? (Solo el nombre y sin espacios)");
                    System.out.println("O escribe volver para no añadir ningun jugador");
                    nombre = Constantes.teclado.next();
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
                        jugadores.getLast().añadirJugador(jugadores.getLast());
                        Log.escribirEnLog("Se ha añadido un nuevo jugador llamado " + nombre);
                    }



            } else if (eleccion == 3) {
                System.out.println("¿Cuál es el nombre del jugador que quieres Eliminar? (Solo el nombre y sin espacios)");
                nombre= Constantes.teclado.next();
                Persona.eliminarJugador(nombre);
                Log.escribirEnLog("Se ha eliminado el jugador " + nombre);
            } else if (eleccion != 4){

                System.out.println("Error! No has escogido un número válido");
            }

        }while (eleccion != 4);

    }




}
