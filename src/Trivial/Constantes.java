package Trivial;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Constantes {

    public static void comprobarArchivo(Path archivo){
        if (!Files.exists(archivo)){
            try {
                Files.createFile(archivo);
            }catch (IOException e){
                System.err.println("Error al crear el archivo" + archivo);
            }
        }
    }
}
