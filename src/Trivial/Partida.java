package Trivial;

import net.objecthunter.exp4j.ExpressionBuilder;
import net.objecthunter.exp4j.Expression;
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
        teclado.nextLine();

        this.numRondas = numRondas;
        this.numJugadores = Math.max(numJugadores, 0);
        this.numPersonas = Math.max(numPersonas, 0);

    }

    public static ArrayList<String> crearJugadores(int numJugadores, int numPersonas, Scanner teclado){

        ArrayList<String> jugadoresEnPartida = new ArrayList<>();
        String nombre;
        boolean correcto;

        for (int i=0; i < numPersonas; i++){
            correcto = false;
            do {
                System.out.println("¿Como se llama el jugador " + (i+1) + "?");
                nombre = teclado.nextLine();
                for (int j = 0; j < Constantes.lineasRanking.size(); j++) {
                    if (nombre.equalsIgnoreCase(Constantes.lineasRanking.get(j).split(" ")[0])) {
                        jugadoresEnPartida.add(nombre);
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
            jugadoresEnPartida.add("CPU" + i);
        }


        return jugadoresEnPartida;
    }

    public void jugarPartida(Partida p){


    }


    //Hace la pregunta sobre matemáticas. Primero genera un número aleatorio entre 4 y 8 que será el número de operaciones,
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

    //Hace la pregunta de letras. Primero lee entero el diccionario, y lo almacena en un arraylist, despues genera
    //una linea (palabra del diccionario) aleatoria y la almacena en un String. De esa palabra se sacan todos los
    //caracteres a un array de chars y se sacan tantos numeros aleatorios del 0 al numero de lertas de la palabra
    // como letras tenga la palabra/3 en el array de chars se cambian esos caracteres por '*' y se muestra.
    public void preguntaLetras(Scanner teclado){

        Random aleatorio = new Random();
        String palabra;
        String respuesta;
        int numeroDeLinea;

        do {
            numeroDeLinea = aleatorio.nextInt(Constantes.lineasDiccionario.size());
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
        respuesta = teclado.nextLine();

        if (respuesta.equalsIgnoreCase(palabra)){
            System.out.println("Enhorabuena!! Respuesta correcta");
        }else {
            System.out.println("Mala suerte. La respuesta correcta era: " + palabra);
        }

    }

    //Hace la pregunta de ingles. Primero almacena todas las lineas del archivo ingles.txt en un arraylist, después
    //genera un numero aleatorio del 0 al numero de lineas del archivo que sea multiplo de 5 (porque es donde estan
    // las preguntas) despues con un numero aleatorio del 1 al 4 se coloca la respuesta correcta en la posicion a, b, c o d
    public void preguntaIngles(Scanner teclado){

        Random aleatorio = new Random();
        int numeroDeLinea;
        int dondeEstaLaRespuesta = aleatorio.nextInt(4) + 1;
        String respuesta;

        do{
            numeroDeLinea = aleatorio.nextInt(Constantes.lineasIngles.size());
        }while (numeroDeLinea % 5 != 0 || numeroDeLinea == 0);


        System.out.println(Constantes.lineasIngles.get(numeroDeLinea));
        if (dondeEstaLaRespuesta == 1){

            System.out.println("a)" + Constantes.lineasIngles.get(numeroDeLinea + 1));
            System.out.println("b)" + Constantes.lineasIngles.get(numeroDeLinea + 2));
            System.out.println("c)" + Constantes.lineasIngles.get(numeroDeLinea + 3));
            System.out.println("d)" + Constantes.lineasIngles.get(numeroDeLinea + 4));

            respuesta = teclado.nextLine();

            if(respuesta.equalsIgnoreCase("a") || respuesta.equalsIgnoreCase("a)")){
                System.out.println("Enhorabuena!! La respuesta es correcta");
            } else{
                System.out.println("Mala suerte, a la proxima será. La respuesta correcta era la A");
            }

        } else if (dondeEstaLaRespuesta == 2) {
            System.out.println("a)" + Constantes.lineasIngles.get(numeroDeLinea + 2));
            System.out.println("b)" + Constantes.lineasIngles.get(numeroDeLinea + 1));
            System.out.println("c)" + Constantes.lineasIngles.get(numeroDeLinea + 3));
            System.out.println("d)" + Constantes.lineasIngles.get(numeroDeLinea + 4));

            respuesta = teclado.nextLine();

            if(respuesta.equalsIgnoreCase("b") || respuesta.equalsIgnoreCase("b)")){
                System.out.println("Enhorabuena!! La respuesta es correcta");
            } else{
                System.out.println("Mala suerte, a la proxima será. La respuesta correcta era la B");
            }

        } else if (dondeEstaLaRespuesta == 3) {
            System.out.println("a)" + Constantes.lineasIngles.get(numeroDeLinea + 2));
            System.out.println("b)" + Constantes.lineasIngles.get(numeroDeLinea + 3));
            System.out.println("c)" + Constantes.lineasIngles.get(numeroDeLinea + 1));
            System.out.println("d)" + Constantes.lineasIngles.get(numeroDeLinea + 4));

            respuesta = teclado.nextLine();

            if(respuesta.equalsIgnoreCase("c") || respuesta.equalsIgnoreCase("c)")){
                System.out.println("Enhorabuena!! La respuesta es correcta");
            } else{
                System.out.println("Mala suerte, a la proxima será. La respuesta correcta era la C");
            }

        } else {
            System.out.println("a)" + Constantes.lineasIngles.get(numeroDeLinea + 2));
            System.out.println("b)" + Constantes.lineasIngles.get(numeroDeLinea + 3));
            System.out.println("c)" + Constantes.lineasIngles.get(numeroDeLinea + 4));
            System.out.println("d)" + Constantes.lineasIngles.get(numeroDeLinea + 1));

            respuesta = teclado.nextLine();

            if(respuesta.equalsIgnoreCase("d") || respuesta.equalsIgnoreCase("d)")){
                System.out.println("Enhorabuena!! La respuesta es correcta");
            } else{
                System.out.println("Mala suerte, a la proxima será. La respuesta correcta era la D");
            }

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