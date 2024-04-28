package Trivial;

import net.objecthunter.exp4j.ExpressionBuilder;
import net.objecthunter.exp4j.Expression;

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

    public Partida() {
        int numRondas;
        int numJugadores;
        int numPersonas;

        System.out.println("¿Cuántos jugadores va a haber?");
        numJugadores = Constantes.teclado.nextInt();
        while (numJugadores > 4){
            System.out.println("Error, el número de jugadores no puede ser superior a 4");
            System.out.println("¿Cuántos jugadores va a haber?");
            numJugadores = Constantes.teclado.nextInt();
        }
        System.out.println("¿Y cuántas personas?");
        numPersonas = Constantes.teclado.nextInt();

        System.out.println("¿Cuántas rondas vais a querer jugar? (escoge una de las siguientes opciones)");
        System.out.println("3 (partida rápida)");
        System.out.println("5 (partida corta)");
        System.out.println("10 (partida normal)");
        System.out.println("20 (partida larga)");
        numRondas = Constantes.teclado.nextInt();

        while (numRondas != 3 && numRondas != 5 && numRondas != 10 && numRondas != 20){

            System.out.println("Error, no has escogido una de las opciones.");

            System.out.println("¿Cuántas rondas vais a querer jugar? (escoge una de las siguientes opciones)");
            System.out.println("3 (partida rápida)");
            System.out.println("5 (partida corta)");
            System.out.println("10 (partida normal)");
            System.out.println("20 (partida larga)");
            numRondas = Constantes.teclado.nextInt();
        }
        Constantes.teclado.nextLine();

        this.numRondas = numRondas;
        this.numJugadores = Math.max(numJugadores, 0);
        this.numPersonas = Math.max(numPersonas, 0);

        Log.escribirEnLog("Se ha creado una nueva partida con " + numJugadores + " jugadores, " + numPersonas + " personas y " + numRondas + " Rondas");
    }

    public static ArrayList<Jugador> crearJugadores(int numJugadores, int numPersonas){

        ArrayList<Jugador> jugadoresEnPartida = new ArrayList<>();
        String nombre;
        boolean correcto;

        for (int i=0; i < numPersonas; i++){
            correcto = false;
            do {
                System.out.println("¿Como se llama el jugador " + (i+1) + "?");
                nombre = Constantes.teclado.nextLine();
                for (int j = 0; j < Constantes.lineasRanking.size(); j++) {
                    if (nombre.equalsIgnoreCase(Constantes.lineasRanking.get(j).split(" ")[0])) {
                        jugadoresEnPartida.add(new Persona(nombre, 0));
                        correcto = true;
                    }
                }
                if (!correcto) {
                    System.err.println("Ese no es un jugador válido");
                    System.err.println("Por favor introduce un jugador añadido al sistema");
                }
            }while (!correcto);
        }
        for (int i = 0; i < (numJugadores-numPersonas); i++){
            jugadoresEnPartida.add(new CPU("CPU" + i));
        }


        return jugadoresEnPartida;
    }

    public ArrayList<Jugador> jugarPartida(Partida p, ArrayList<Jugador> jugadores){

        for (int i = 0; i < p.numRondas; i++){
            for (int j = 0; j < p.numJugadores; j++){
                int pregunta = Constantes.aleatorio.nextInt(3);
                System.out.println("ES EL TURNO DE " + jugadores.get(j).nombre);
                if(pregunta==0 && jugadores.get(j) instanceof CPU){
                    System.out.println("Ha salido una pregunta de matemáticas!");
                    if(((CPU) jugadores.get(j)).contestarPregunta(0, preguntaMates())){
                        jugadores.get(j).puntuacion += 1;
                    }

                }else if (pregunta==0 && jugadores.get(j) instanceof Persona){
                    System.out.println("Ha salido una pregunta de matemáticas!");
                    if (((Persona) jugadores.get(j)).contestarPregunta(preguntaMates())){
                        jugadores.get(j).puntuacion += 1;
                    }

                } else if (pregunta==1 && jugadores.get(j) instanceof CPU) {
                    System.out.println("Ha salido una pregunta de Letras!");
                    if (((CPU) jugadores.get(j)).contestarPregunta(1, preguntaLetras())){
                        jugadores.get(j).puntuacion += 1;
                    }

                }else if (pregunta==1 && jugadores.get(j) instanceof Persona){
                    System.out.println("Ha salido una pregunta de Letras!");
                    if (((Persona) jugadores.get(j)).contestarPregunta(preguntaLetras())){
                        jugadores.get(j).puntuacion += 1;
                    }

                } else if (pregunta==2 && jugadores.get(j) instanceof CPU) {
                    System.out.println("Ha salido una pregunta de ingles!");
                    if (((CPU) jugadores.get(j)).contestarPregunta(2, preguntaIngles())){
                        jugadores.get(j).puntuacion += 1;
                    }

                } else if (pregunta==2 && jugadores.get(j) instanceof Persona) {
                    System.out.println("Ha salido una pregunta de ingles");
                    if (((Persona) jugadores.get(j)).contestarPregunta(preguntaIngles())){
                        jugadores.get(j).puntuacion += 1;
                    }
                }

            }
        }
        jugadores.sort((o1, o2) -> Integer.compare(o2.getPuntuacion(), o1.getPuntuacion()));
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
        return jugadores;
    }


    //Hace la pregunta sobre matemáticas. Primero genera un número aleatorio entre 4 y 8 que será el número de operaciones,
    //despues añade un numero entre 2 y 12 y luego el símbolo de la operación, eso el número de veces que haya salido en el primer numero aleatorio
    //por último con la librería exp4j usando las clases Expression y ExpressionBuilder
    //paso el string a un double, pido una respuesta y la comparo con el resultado
    //Si la respuesta es correcta lo cuenta como acierto, y si es incorrecta como fallo.
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

    //Hace la pregunta de letras. Primero lee entero el diccionario, y lo almacena en un arraylist, despues genera
    //una linea (palabra del diccionario) aleatoria y la almacena en un String. De esa palabra se sacan todos los
    //caracteres a un array de chars y se sacan tantos numeros aleatorios del 0 al numero de lertas de la palabra
    // como letras tenga la palabra/3 en el array de chars se cambian esos caracteres por '*' y se muestra.
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

    //Hace la pregunta de ingles. Primero almacena todas las lineas del archivo ingles.txt en un arraylist, después
    //genera un numero aleatorio del 0 al numero de lineas del archivo que sea multiplo de 5 (porque es donde estan
    // las preguntas) despues con un numero aleatorio del 1 al 4 se coloca la respuesta correcta en la posicion a, b, c o d
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