package Trivial;

import net.objecthunter.exp4j.ExpressionBuilder;
import net.objecthunter.exp4j.Expression;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

public class Partida {
    int numRondas;
    int numJugadores;
    int numPersonas;

    public Partida(Scanner teclado) {
        int numRondas;
        int numJugadores;
        int numPersonas;

        System.out.println("¿Cuántos jugadores va a haber?");
        numJugadores = teclado.nextInt();
        while (numJugadores > 4){
            System.out.println("Error, el número de jugadores no puede ser superior a 4");
            System.out.println("¿Cuántos jugadores va a haber?");
            numJugadores = teclado.nextInt();
        }
        System.out.println("¿Y cuántas personas?");
        numPersonas = teclado.nextInt();

        System.out.println("¿Cuántas rondas vais a querer jugar? (escoge una de las siguientes opciones)");
        System.out.println("3 (partida rápida)");
        System.out.println("5 (partida corta)");
        System.out.println("10 (partida normal)");
        System.out.println("20 (partida larga)");
        numRondas = teclado.nextInt();

        while (numRondas != 3 && numRondas != 5 && numRondas != 10 && numRondas != 20){

            System.out.println("Error, no has escogido una de las opciones.");

            System.out.println("¿Cuántas rondas vais a querer jugar? (escoge una de las siguientes opciones)");
            System.out.println("3 (partida rápida)");
            System.out.println("5 (partida corta)");
            System.out.println("10 (partida normal)");
            System.out.println("20 (partida larga)");
            numRondas = teclado.nextInt();
        }


        this.numRondas = numRondas;
        this.numJugadores = Math.max(numJugadores, 0);
        this.numPersonas = Math.max(numPersonas, 0);

    }

    public void meterJugadoresEnPartida(int numJugadores, int numPersonas){


    }

    public void jugarPartida(Partida p){

    }

    public void actualizarRanking(String contenido) {

    }


    public void actualizarHistorial() {

    }



    //Hace la pregunta sobre matemáticas, Primero genera un número aleatorio entre 4 y 8 que será el número de operaciones,
    //despues añade un numero entre 2 y 12 y luego el símbolo de la operación, eso el número de veces que haya salido en el primer numero aleatorio
    //por último con la librería exp4j usando las clases Expression y ExpressionBuilder
    //paso el string a un double, pido una respuesta y la comparo con el resultado
    //Si la respuesta es correcta lo cuenta como acierto, y si es incorrecta como fallo.
    public void preguntaMates(Scanner teclado){
        String operacion = "";
        int numero2;
        int numero3;
        int numero1;
        int respuesta;
        Random random = new Random();

        //Genera el número de operaciones (numero al azar entre 4 y 8)
        numero1 = random.nextInt(5) + 4;

        //Genera los números de la operacion (entre 2 y 12)
        for (int i = 0; i < numero1; i++) {
            numero2 = random.nextInt(11) + 2;
            operacion = operacion + numero2;

            //Genera los operadores/Simbolos matematicos
            if (i < (numero1 - 1)) {
                numero3 = random.nextInt(3) + 1;
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
        double resultado = expresion.evaluate();


        System.out.println("¿Cual es el resultado de la siguiente opeación?");
        System.out.println(operacion);

        respuesta = teclado.nextInt();
        if (respuesta == (int) resultado){
            System.out.println("Enhorabuena, la respuesta es correcta!!");
        } else{
            System.out.println("Mala suerte, a la proxima será. La respuesta correcta era: " + resultado);
        }


    }

    public void preguntaLetras(Scanner teclado){

        try{

            ArrayList<String> lineas = (ArrayList<String>) Files.readAllLines(Paths.get("src/archivos/diccionario.txt"));
            Random aleatorio = new Random();
            String palabra;
            String respuesta;
            int numeroDeLinea;

            do {
                numeroDeLinea = aleatorio.nextInt(lineas.size());
                palabra = lineas.get(numeroDeLinea);
            }while(palabra.length() > 3);

            char[] letras = palabra.toCharArray();
            ArrayList<Integer> posicionesDeLetras = new ArrayList<>();

            for (int i = 0; i < letras.length; i++){
                posicionesDeLetras.add(i);
            }

            Collections.shuffle(posicionesDeLetras);
            List<Integer> posicionesAleatorias = posicionesDeLetras.subList(0, (letras.length / 3));

            for (int i = 0; i < posicionesAleatorias.size(); i++){
                letras[posicionesAleatorias.get(i)] = '*';
            }

            String palabraCensurada = new String(letras);

            System.out.println("¿Cual es esta palabra?");
            System.out.println(palabraCensurada);
            respuesta = teclado.nextLine();

            if (respuesta.equalsIgnoreCase(palabra)){
                System.out.println("Enhorabuena!! Respuesta correcta");
            }else {
                System.out.println("Mala suerte. La respuesta correcta era: " + palabra);
            }
        }catch (IOException e){
            System.err.println("La pregunta de letras no ha podido leer el archivo diccionario.txt");
        }

    }


    public void preguntaIngles(Scanner teclado){

        try {
            ArrayList<String> lineas = (ArrayList<String>) Files.readAllLines(Paths.get("src/archivos/ingles.txt"));
            Random aleatorio = new Random();
            int numeroDeLinea;
            int dondeEstaLaRespuesta = aleatorio.nextInt(4) + 1;
            String respuesta;

            do{
                numeroDeLinea = aleatorio.nextInt(lineas.size());
            }while (numeroDeLinea % 5 != 0);

            teclado.nextLine();
            System.out.println(lineas.get(numeroDeLinea));
            if (dondeEstaLaRespuesta == 1){

                System.out.println("a)" + lineas.get(numeroDeLinea + 1));
                System.out.println("b)" + lineas.get(numeroDeLinea + 2));
                System.out.println("c)" + lineas.get(numeroDeLinea + 3));
                System.out.println("d)" + lineas.get(numeroDeLinea + 4));

                respuesta = teclado.nextLine();

                if(respuesta.equalsIgnoreCase("a") || respuesta.equalsIgnoreCase("a)")){
                    System.out.println("Enhorabuena!! La respuesta es correcta");
                } else{
                    System.out.println("Mala suerte, a la proxima será. La respuesta correcta era la A");
                }

            } else if (dondeEstaLaRespuesta == 2) {
                System.out.println("a)" + lineas.get(numeroDeLinea + 2));
                System.out.println("b)" + lineas.get(numeroDeLinea + 1));
                System.out.println("c)" + lineas.get(numeroDeLinea + 3));
                System.out.println("d)" + lineas.get(numeroDeLinea + 4));

                respuesta = teclado.nextLine();

                if(respuesta.equalsIgnoreCase("b") || respuesta.equalsIgnoreCase("b)")){
                    System.out.println("Enhorabuena!! La respuesta es correcta");
                } else{
                    System.out.println("Mala suerte, a la proxima será. La respuesta correcta era la B");
                }

            } else if (dondeEstaLaRespuesta == 3) {
                System.out.println("a)" + lineas.get(numeroDeLinea + 2));
                System.out.println("b)" + lineas.get(numeroDeLinea + 3));
                System.out.println("c)" + lineas.get(numeroDeLinea + 1));
                System.out.println("d)" + lineas.get(numeroDeLinea + 4));

                respuesta = teclado.nextLine();

                if(respuesta.equalsIgnoreCase("c") || respuesta.equalsIgnoreCase("c)")){
                    System.out.println("Enhorabuena!! La respuesta es correcta");
                } else{
                    System.out.println("Mala suerte, a la proxima será. La respuesta correcta era la C");
                }

            } else {
                System.out.println("a)" + lineas.get(numeroDeLinea + 2));
                System.out.println("b)" + lineas.get(numeroDeLinea + 3));
                System.out.println("c)" + lineas.get(numeroDeLinea + 4));
                System.out.println("d)" + lineas.get(numeroDeLinea + 1));

                respuesta = teclado.nextLine();

                if(respuesta.equalsIgnoreCase("d") || respuesta.equalsIgnoreCase("d)")){
                    System.out.println("Enhorabuena!! La respuesta es correcta");
                } else{
                    System.out.println("Mala suerte, a la proxima será. La respuesta correcta era la D");
                }

            }


        }catch(IOException e){
            System.err.println("La pregunta de ingles Tiene un fallo al leer el archivo ingles.txt");
        }

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