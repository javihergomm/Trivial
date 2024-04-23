package Trivial;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Log {

    public static void escribirEnLog(String texto) {

        LocalDate fecha = LocalDate.now();
        LocalTime Hora = LocalTime.now();

        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("HH:mm:ss");

        texto ="[" + fecha.format(formatoFecha) + "]" + "[" + Hora.format(formatoHora) + "]" + " " + texto + '\n';

        if (ultimaLinea().equals("vacio")){
           apuntarAccion(texto);

        } else if (!logAntiguo(ultimaLinea())) {

            apuntarAccion(texto);

        } else {

            File archivoActual = new File("src/archivos/salida.log");
            File archivoAntiguo = new File("src/archivos/salida.log" + "." + fechaUltimoLog(ultimaLinea()));

            try{
                archivoActual.renameTo(archivoAntiguo);
                Files.createFile(archivoActual.toPath());
            }catch (IOException e) {
                System.err.println("Error al renombrar archivo salida.log o crear archivo salida.log");
            }

                apuntarAccion(texto);

        }
    }

    public static String ultimaLinea() {


        String ultimaLinea = "";
        String estaLogVacio = null;
        try {
            estaLogVacio = Files.readAllLines(Paths.get("src/archivos/salida.log")).toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (estaLogVacio.equals("[]")) {
            ultimaLinea = "vacio";
            return ultimaLinea;
        } else {

            try {

                ArrayList<String> lineas = (ArrayList<String>) Files.readAllLines(Paths.get("src/archivos/salida.log"));

                ultimaLinea = lineas.getLast();


            } catch (IOException e) {
                e.printStackTrace();
            }

            return ultimaLinea;
        }
    }

    private static boolean logAntiguo(String fecha){
        int dia;
        int mes;
        int año;

        dia = Integer.parseInt(fecha.substring(1, 3));
        mes = Integer.parseInt(fecha.substring(4, 6));
        año = Integer.parseInt(fecha.substring(7, 11));

        if(año < LocalDate.now().getYear()){
            return true;
        } else if (mes < LocalDate.now().getMonthValue()) {
            return true;
        } else if (dia < LocalDate.now().getDayOfMonth()) {
            return true;
        }else {
            return false;
        }
    }

    private static String fechaUltimoLog(String fecha){
        String dia;
        String mes;
        String año;
        String todoJunto;

        dia = fecha.substring(1, 3);
        mes = fecha.substring(4, 6);
        año = fecha.substring(7, 11);

        todoJunto = año + mes + dia;

        return todoJunto;
    }

    private static void apuntarAccion(String texto){

        Path archivo = Paths.get("src/archivos/salida.log");

        try {
            Files.write(archivo, texto.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo: " + e.getMessage());
        }

    }
}