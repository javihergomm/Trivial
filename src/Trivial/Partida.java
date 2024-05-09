package Trivial;

import constantes.Constantes;
import constantes.Log;
import net.objecthunter.exp4j.ExpressionBuilder;
import net.objecthunter.exp4j.Expression;
import static constantes.Constantes.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Partida {
    int numRondas;
    int numJugadores;
    int numPersonas;

    /**
     * El constructor de la clase partida: fija el numero de jugadores, de humanos y de rondas, a partir de preguntarselo al usuario.
     */
    public Partida() {
        int numRondas;
        int numJugadores;
        int numPersonas;

        numJugadores = pedirNumJugadores();
        numPersonas = pedirNumPersonas(numJugadores);
        numRondas = pedirNumRondas();

        Constantes.teclado.nextLine();

        this.numRondas = numRondas;
        this.numJugadores = Math.max(numJugadores, 0);
        this.numPersonas = Math.max(numPersonas, 0);

        Log.escribirEnLog("Se ha creado una nueva partida con " + numJugadores + " jugadores, " + numPersonas + " personas y " + numRondas + " Rondas");
    }

    /**
     * Rellena un arraylist con todos los jugadores que participan en la partida ya sean humanos o sean CPU.
     * @param numJugadores (int) es el numero de jugadores que van a añadirse al arraylist
     * @param numPersonas (int) es el numero de jugadores humanos que participan en la partida, numJugadores - numPersonas = al numero de bots que van a jugar.
     * @return devuelve el arraylist de los jugadores.
     */
    public static ArrayList<Jugador> crearJugadores(int numJugadores, int numPersonas){

        ArrayList<Jugador> jugadoresEnPartida = new ArrayList<>();
        String añadir;
        String nombre;
        boolean correcto;

        for (int i=0; i < numPersonas; i++){
            correcto = false;
            do {
                System.out.println("¿Como se llama el jugador " + (i + 1) + "?");
                nombre = Constantes.teclado.nextLine();
                String primeros3 = "";
                if (nombre.length() >=3){
                    primeros3 = nombre.substring(0, 3);
                }
                if (primeros3.equalsIgnoreCase("cpu")) {
                    System.err.println("El nombre no puede empezar por 'CPU'");
                } else if (nombre.contains(" ")){
                    System.err.println("El nombre no puede contener espacios");
                } else {
                    for (int j = 0; j < Constantes.lineasRanking.size(); j++) {
                        if (nombre.equalsIgnoreCase(Constantes.lineasRanking.get(j).split(" ")[0])) {
                            jugadoresEnPartida.add(new Persona(nombre, 0));
                            correcto = true;
                        }
                    }
                    if (!correcto) {
                        System.err.println("Ese jugador no esta añadido en el sistema");
                        System.out.println("¿Quieres añadirlo?");
                        añadir = Constantes.teclado.nextLine();

                        if (añadir.equalsIgnoreCase("si") || añadir.equalsIgnoreCase("s")) {
                            jugadoresEnPartida.add(new Persona(nombre, 0));
                            Persona.añadirJugador((Persona) jugadoresEnPartida.getLast());
                            Log.escribirEnLog("Se ha añadido un nuevo jugador llamado " + nombre);
                            System.out.println("Jugador " + nombre + " añadido correctamente");
                            correcto = true;

                        } else {
                            System.out.println(ANSI_GREEN + "Jugador no añadido" + ANSI_RESET);
                        }
                    }
                }

            }while (!correcto);
        }
        for (int i = 0; i < (numJugadores-numPersonas); i++){
            jugadoresEnPartida.add(new CPU("CPU" + i));
        }


        return jugadoresEnPartida;
    }

    /**
     * Es donde se maneja toda la partida, donde se hacen las preguntas y se añaden los puntos.
     * @param p (Partida) Es el objeto Partida con el numero de rondas, de jugadores, y de personas.
     * @param jugadores (ArrayList<Jugador>) Es el arraylist con los jugadores que van a participar en la partida
     * @return devuelve un arraylist de los jugadores con los puntos actualizados.
     */
    public ArrayList<Jugador> jugarPartida(Partida p, ArrayList<Jugador> jugadores){

        for (int i = 0; i < p.numRondas; i++){
            for (int j = 0; j < p.numJugadores; j++){
                int pregunta = Constantes.aleatorio.nextInt(3);
                System.out.println(ANSI_CYAN + "ES EL TURNO DE " + jugadores.get(j).nombre + ANSI_RESET);
                if(pregunta==0 && jugadores.get(j) instanceof CPU){
                    System.out.println(ANSI_RED + "Ha salido una pregunta de matemáticas!" + ANSI_RESET);
                    if(((CPU) jugadores.get(j)).contestarPregunta(0, preguntaMates())){
                        jugadores.get(j).puntuacion += 1;
                    }

                }else if (pregunta==0 && jugadores.get(j) instanceof Persona){
                    System.out.println(ANSI_RED + "Ha salido una pregunta de matemáticas!" + ANSI_RESET);
                    if (((Persona) jugadores.get(j)).contestarPregunta(preguntaMates(), 0)){
                        jugadores.get(j).puntuacion += 1;
                    }

                } else if (pregunta==1 && jugadores.get(j) instanceof CPU) {
                    System.out.println(ANSI_BLUE + "Ha salido una pregunta de Letras!" + ANSI_RESET);
                    if (((CPU) jugadores.get(j)).contestarPregunta(1, preguntaLetras())){
                        jugadores.get(j).puntuacion += 1;
                    }

                }else if (pregunta==1 && jugadores.get(j) instanceof Persona){
                    System.out.println(ANSI_BLUE + "Ha salido una pregunta de Letras!" + ANSI_RESET);
                    if (((Persona) jugadores.get(j)).contestarPregunta(preguntaLetras(), 1)){
                        jugadores.get(j).puntuacion += 1;
                    }

                } else if (pregunta==2 && jugadores.get(j) instanceof CPU) {
                    System.out.println(ANSI_PURPLE + "Ha salido una pregunta de ingles!" + ANSI_RESET);
                    if (((CPU) jugadores.get(j)).contestarPregunta(2, preguntaIngles())){
                        jugadores.get(j).puntuacion += 1;
                    }

                } else if (pregunta==2 && jugadores.get(j) instanceof Persona) {
                    System.out.println(ANSI_PURPLE + "Ha salido una pregunta de ingles" + ANSI_RESET);
                    if (((Persona) jugadores.get(j)).contestarPregunta(preguntaIngles(), 2)){
                        jugadores.get(j).puntuacion += 1;
                    }
                }

            }
        }
        jugadores.sort((o1, o2) -> Integer.compare(o2.getPuntuacion(), o1.getPuntuacion()));
        System.out.println("Y el ganador es...");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            System.err.println("Error al esperar");
        }
        System.out.println(jugadores.getFirst().nombre + "!!! " + "Enhorabuenaaa!!!");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            System.err.println("Error al esperar");
        }
        System.out.println("Así ha quedado la partida: ");
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            System.err.println("Error al esperar");
        }
        for (int i = 0; i < p.numJugadores; i++){
            System.out.println(jugadores.get(i).nombre + " " + jugadores.get(i).puntuacion);
            try {
                Files.write(Constantes.archivoHistorial, (jugadores.get(i).nombre + ' ' + (jugadores.get(i).puntuacion) + " ").getBytes(), StandardOpenOption.APPEND);
            }catch (IOException e){
                System.err.println("Error al escribir en el Historial");
            }
        }
        try{
            Files.write(Constantes.archivoHistorial, "\n".getBytes(), StandardOpenOption.APPEND);
        }catch (IOException e){
            System.err.println("Error al escribir en el Historial");
        }
        System.out.println("Pulsa enter para continuar...");
        teclado.nextLine();
        return jugadores;
    }


    /**
     * Hace la pregunta sobre matemáticas.
     * @return devuelve la respuesta correcta.
     */
    public String preguntaMates(){
        String operacion = "";
        int numero2;
        int numero3;
        int numero1;
        int respuesta;


        //Genera el número de operaciones (numero al azar entre 4 y 8)
        numero1 = Constantes.aleatorio.nextInt(5) + 4;

        //Genera los números de la operacion (entre 2 y 12)
        for (int i = 0; i < numero1; i++) {
            numero2 = Constantes.aleatorio.nextInt(11) + 2;
            operacion = operacion + numero2;

            //Genera los operadores/Simbolos matematicos
            if (i < (numero1 - 1)) {
                numero3 = Constantes.aleatorio.nextInt(3) + 1;
                if (numero3 == 1) {
                    operacion = operacion + " + ";
                } else if (numero3 == 2) {
                    operacion = operacion + " - ";
                } else {
                    operacion = operacion + " * ";
                }
            }
        }

        Expression expresion = new ExpressionBuilder(operacion).build();
        int resultado = (int) expresion.evaluate();


        System.out.println("¿Cual es el resultado de la siguiente opeación?");
        System.out.println(operacion);

        return String.valueOf(resultado);

    }

    /**
     * Hace la pregunta de letras.
     * @return devuelve la respuesta correcta.
     */
    public String preguntaLetras(){

        String palabra;
        String respuesta;
        int numeroDeLinea;

        do {
            numeroDeLinea = Constantes.aleatorio.nextInt(Constantes.lineasDiccionario.size());
            palabra = Constantes.lineasDiccionario.get(numeroDeLinea);
        }while(palabra.length() <= 3);

        char[] letras = palabra.toCharArray();
        ArrayList<Integer> posicionesDeLetras = new ArrayList<>();

        for (int i = 0; i < letras.length; i++){
            posicionesDeLetras.add(i);
        }

        Collections.shuffle(posicionesDeLetras);
        List<Integer> posicionesAleatorias = posicionesDeLetras.subList(0, (letras.length / 3));

        for (Integer posicionesAleatoria : posicionesAleatorias) {
            letras[posicionesAleatoria] = '*';
        }

        String palabraCensurada = new String(letras);

        System.out.println("¿Cual es esta palabra?");
        System.out.println(palabraCensurada);

        return palabra;
    }

    /**
     * Hace la pregunta de ingles.
     * @return devuelve la respuesta correcta.
     */
    public String preguntaIngles(){

        int numeroDeLinea;
        int dondeEstaLaRespuesta = Constantes.aleatorio.nextInt(4) + 1;
        String respuesta;
        String correcto;

        do{
            numeroDeLinea = Constantes.aleatorio.nextInt(Constantes.lineasIngles.size());
        }while (numeroDeLinea % 5 != 0 || numeroDeLinea == 0);


        System.out.println(Constantes.lineasIngles.get(numeroDeLinea));
        if (dondeEstaLaRespuesta == 1){

            System.out.println("a)" + Constantes.lineasIngles.get(numeroDeLinea + 1));
            System.out.println("b)" + Constantes.lineasIngles.get(numeroDeLinea + 2));
            System.out.println("c)" + Constantes.lineasIngles.get(numeroDeLinea + 3));
            System.out.println("d)" + Constantes.lineasIngles.get(numeroDeLinea + 4));

            correcto = "a";

        } else if (dondeEstaLaRespuesta == 2) {
            System.out.println("a)" + Constantes.lineasIngles.get(numeroDeLinea + 2));
            System.out.println("b)" + Constantes.lineasIngles.get(numeroDeLinea + 1));
            System.out.println("c)" + Constantes.lineasIngles.get(numeroDeLinea + 3));
            System.out.println("d)" + Constantes.lineasIngles.get(numeroDeLinea + 4));

            correcto = "b";

        } else if (dondeEstaLaRespuesta == 3) {
            System.out.println("a)" + Constantes.lineasIngles.get(numeroDeLinea + 2));
            System.out.println("b)" + Constantes.lineasIngles.get(numeroDeLinea + 3));
            System.out.println("c)" + Constantes.lineasIngles.get(numeroDeLinea + 1));
            System.out.println("d)" + Constantes.lineasIngles.get(numeroDeLinea + 4));

            correcto = "c";

        } else {
            System.out.println("a)" + Constantes.lineasIngles.get(numeroDeLinea + 2));
            System.out.println("b)" + Constantes.lineasIngles.get(numeroDeLinea + 3));
            System.out.println("c)" + Constantes.lineasIngles.get(numeroDeLinea + 4));
            System.out.println("d)" + Constantes.lineasIngles.get(numeroDeLinea + 1));


            correcto = "d";
        }

        return correcto;
    }

    /**
     * Pide el número de jugadores para iniciar la partida.
     * @return devuelve el número de jugadores
     */
    private static int pedirNumJugadores(){
        System.out.println("¿Cuántos jugadores va a haber?");
        int numJugadores=0;
        boolean correcto;
        do{
            try{
                numJugadores = Constantes.teclado.nextInt();
                correcto = true;
            }catch (Exception e){
                Constantes.teclado.nextLine();
                System.err.println("Debes introducir un número");
                correcto = false;
            }
        }while(!correcto);

        while (numJugadores > 4 || numJugadores<=0){
            System.err.println("Error, el número de jugadores no puede ser superior a 4 ni inferior a 1");
            System.out.println("¿Cuántos jugadores va a haber?");
            do{
                try{
                    numJugadores = Constantes.teclado.nextInt();
                    correcto = true;
                }catch (Exception e){
                    Constantes.teclado.nextLine();
                    System.err.println("Debes introducir un número");
                    correcto = false;
                }
            }while(!correcto);

        }
        return numJugadores;
    }

    /**
     * Pide el número de jugadores humanos para iniciar la partida.
     * @param numJugadores (int) es el numero de jugadores introducido anteriormente, el numero de jugadores humanos no puede ser superior al numero de jugadores
     * @return devuelve el numero de jugadores humanos.
     */
    private static int pedirNumPersonas(int numJugadores){
        System.out.println("¿Y cuántas personas?");
        int numPersonas=0;
        boolean correcto;
        do{
            if (numPersonas>numJugadores){
                System.err.println("No puede haber más personas que jugadores!");
            }
            try{
                numPersonas = Constantes.teclado.nextInt();
                correcto = true;
            }catch (Exception e){
                Constantes.teclado.nextLine();
                System.err.println("Debes introducir un número");
                correcto = false;
            }
        }while(!correcto || numPersonas>numJugadores);
        return numPersonas;
    }

    /**
     * Pide el numero de rondas para iniciar la partida.
     * @return devuelve el numero de rondas.
     */
    private static int pedirNumRondas(){
        System.out.println("¿Cuántas rondas vais a querer jugar? (escoge una de las siguientes opciones)");
        System.out.println("3 (partida rápida)");
        System.out.println("5 (partida corta)");
        System.out.println("10 (partida normal)");
        System.out.println("20 (partida larga)");
        int numRondas=0;
        boolean correcto;
        do{
            try{
                numRondas = Constantes.teclado.nextInt();
                correcto = true;
            }catch (Exception e){
                Constantes.teclado.nextLine();
                System.err.println("Debes introducir un número");
                correcto = false;
            }
        }while(!correcto);

        while (numRondas != 3 && numRondas != 5 && numRondas != 10 && numRondas != 20){

            System.out.println("Error, no has escogido una de las opciones.");

            System.out.println("¿Cuántas rondas vais a querer jugar? (escoge una de las siguientes opciones)");
            System.out.println("3 (partida rápida)");
            System.out.println("5 (partida corta)");
            System.out.println("10 (partida normal)");
            System.out.println("20 (partida larga)");
            do{
                try{
                    numRondas = Constantes.teclado.nextInt();
                    correcto = true;
                }catch (Exception e){
                    Constantes.teclado.nextLine();
                    System.err.println("Debes introducir un número");
                    correcto = false;
                }
            }while(!correcto);
        }
        return numRondas;
    }

    public int getNumRondas() {
        return numRondas;
    }

    public void setNumRondas(int numRondas) {
        this.numRondas = numRondas;
    }

    public int getNumJugadores() {
        return numJugadores;
    }

    public void setNumJugadores(int numJugadores) {
        this.numJugadores = numJugadores;
    }
}