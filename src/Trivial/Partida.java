package Trivial;

import net.objecthunter.exp4j.ExpressionBuilder;
import net.objecthunter.exp4j.Expression;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

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

    public void actualizarRanking(String contenido) {

    }

    public void mostrarRanking() {
        Path archivo = Paths.get("src/archivos/Ranking.txt");
        try {

            List<String> lineas = Files.readAllLines(archivo);

            for (String linea : lineas) {
                System.out.println(linea);
            }

        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        }

    }

    public void actualizarHistorial() {

    }

    public void mostrarHistorial() {

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
    public void preguntaLetras(){

    }
    public void preguntaIngles(){

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