package Trivial;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Ranking {

    public static void mostrarRanking() {
        Path archivo = Paths.get("src/archivos/Ranking.txt");
        Constantes.comprobarArchivo(archivo);
        try {

            List<String> lineas = Files.readAllLines(archivo);

            for (String linea : lineas) {
                System.out.println(linea);
            }

        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        }

        Log.escribirEnLog("Se ha mostrado el Ranking");
    }

    public void actualizarRanking() {
        Path archivo = Paths.get("src/archivos/Ranking.txt");
        Constantes.comprobarArchivo(archivo);

    }


}
