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

public class Log {

    public static void escribirEnLog(String texto){

        LocalDate fecha = LocalDate.now();
        LocalTime Hora = LocalTime.now();

        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("HH:mm:ss");

        texto ="[" + fecha.format(formatoFecha) + "]" + "[" + Hora.format(formatoHora) + "]" + " " + texto;

        if (!logAntiguo(ultimaLinea())){


            Path archivo = Paths.get("src/archivos/salida.log");

            try {
                Files.write(archivo, texto.getBytes(), StandardOpenOption.APPEND);
            } catch (IOException e) {
                System.err.println("Error al escribir en el archivo: " + e.getMessage());
            }
        }else {
            
        }


    }

    private static String ultimaLinea() {


        String ultimaLinea = null;
        try {

            ArrayList<String> lineas = (ArrayList<String>) Files.readAllLines(Paths.get("src/archivos/salida.log"));

            ultimaLinea = lineas.getLast();


        } catch (IOException e) {
            e.printStackTrace();
        }

        return ultimaLinea;
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
}
