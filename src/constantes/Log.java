package constantes;

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

    /**
     * Escribe en el archivo salida.log y, si el archivo salida.log es de mas antiguo que 1 día
     * crea un archivo nuevo y el anterior lo guarda con la fecha como extensión.
     *
     * @param texto (String) Es el texto que se escribirá en el archivo salida.log justo despues de la fecha y hora
     */
    public static void escribirEnLog(String texto) {

        LocalDate fecha = LocalDate.now();
        LocalTime Hora = LocalTime.now();

        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("HH:mm:ss");

        texto ="[" + fecha.format(formatoFecha) + "]" + "[" + Hora.format(formatoHora) + "]" + " " + texto + '\n';

        Path archivo = Path.of("src/archivos/salida.log");

        Constantes.comprobarArchivo(archivo);

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

    /**
     * Extrae la última línea del archivo salida.log para su posterior uso.
     * @return Devuelve un String, es la última línea del archivo salida.log
     */
    public static String ultimaLinea() {


        String ultimaLinea = null;
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

    /**
     * Comprueba si la fecha del archivo salida.log es la de hoy o no
     * @param fecha (String) es la última linea del archivo salida.log
     * @return devuelve un booleano (true: si la fecha actual es mayor a la fecha del archivo salida.log. false: si no)
     */
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

    /**
     * Extrae la fecha del ultimo Log
     * @param fecha (String) es la última linea del archivo salida.log
     * @return devuelve un String que es la fecha en formato yyyymmdd
     */
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

    /**
     * Escribe en el archivo salida.log el texto
     * @param texto (String) es el texto de la acción juntada con la fecha
     */
    private static void apuntarAccion(String texto){

        Path archivo = Paths.get("src/archivos/salida.log");

        try {
            Files.write(archivo, texto.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo: " + e.getMessage());
        }

    }
}