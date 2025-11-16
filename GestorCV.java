//Diego Rocabado
//Santiago Dirón

import java.io.*;
import java.nio.file.*;

public class GestorCV {
    private static final String CV_DIRECTORY = "cvs/";

    // Constructor
    public GestorCV() {
        // Create cvs directory if it doesn't exist
        try {
            Files.createDirectories(Paths.get(CV_DIRECTORY));
        } catch (IOException e) {
            System.err.println("Error creating CV directory: " + e.getMessage());
        }
    }

    /**
     * Guarda un archivo CV en el directorio cvs/
     * @param archivoOrigen Path del archivo CV original
     * @param cedula Cédula del empleado
     * @param nombre Nombre del empleado
     * @param apellido Apellido del empleado
     * @return Path del archivo guardado, o null si hubo error
     */
    public String guardarCV(String archivoOrigen, String cedula, String nombre, String apellido) {
        if (archivoOrigen == null || archivoOrigen.isEmpty()) {
            return null;
        }

        // Validar que el archivo sea .txt
        if (!archivoOrigen.toLowerCase().endsWith(".txt")) {
            System.err.println("Error: El archivo CV debe ser .txt");
            return null;
        }

        // Crear nombre del archivo destino
        String nombreArchivo = cedula + "_" + nombre + "_" + apellido + ".txt";
        String pathDestino = CV_DIRECTORY + nombreArchivo;

        try {
            // Copiar archivo al directorio cvs/
            Path origen = Paths.get(archivoOrigen);
            Path destino = Paths.get(pathDestino);

            if (!Files.exists(origen)) {
                System.err.println("Error: El archivo origen no existe: " + archivoOrigen);
                return null;
            }

            Files.copy(origen, destino, StandardCopyOption.REPLACE_EXISTING);
            return pathDestino;
        } catch (IOException e) {
            System.err.println("Error guardando CV: " + e.getMessage());
            return null;
        }
    }

    /**
     * Lee el contenido de un archivo CV
     * @param pathCV Path del archivo CV
     * @return Contenido del archivo, o null si hubo error
     */
    public String leerCV(String pathCV) {
        if (pathCV == null || pathCV.isEmpty()) {
            return null;
        }

        try {
            return new String(Files.readAllBytes(Paths.get(pathCV)));
        } catch (IOException e) {
            System.err.println("Error leyendo CV: " + e.getMessage());
            return null;
        }
    }

    /**
     * Valida que un archivo existe y es .txt
     * @param pathArchivo Path del archivo
     * @return true si el archivo es válido, false en caso contrario
     */
    public boolean validarArchivoCV(String pathArchivo) {
        if (pathArchivo == null || pathArchivo.isEmpty()) {
            return false;
        }

        if (!pathArchivo.toLowerCase().endsWith(".txt")) {
            return false;
        }

        return Files.exists(Paths.get(pathArchivo));
    }

    /**
     * Elimina un archivo CV
     * @param pathCV Path del archivo CV
     * @return true si se eliminó correctamente, false en caso contrario
     */
    public boolean eliminarCV(String pathCV) {
        if (pathCV == null || pathCV.isEmpty()) {
            return false;
        }

        try {
            return Files.deleteIfExists(Paths.get(pathCV));
        } catch (IOException e) {
            System.err.println("Error eliminando CV: " + e.getMessage());
            return false;
        }
    }
}
