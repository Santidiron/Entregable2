//Diego Rocabado
//Santiago Dirón

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class GestorCV {
    private static final String CV_DIRECTORY = "cvs";

    // Constructor
    public GestorCV() {
        try {
            Files.createDirectories(Paths.get(CV_DIRECTORY));
        } catch (IOException e) {
            System.err.println("Error creating CV directory: " + e.getMessage());
        }
    }

    public String getCarpetaCVs() {
        return CV_DIRECTORY;
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
        Path origen = Paths.get(archivoOrigen);
        Path destino = Paths.get(CV_DIRECTORY, nombreArchivo);

        try {
            // Copiar archivo al directorio cvs/
            if (!Files.exists(origen)) {
                System.err.println("Error: El archivo origen no existe: " + archivoOrigen);
                return null;
            }

            Files.copy(origen, destino, StandardCopyOption.REPLACE_EXISTING);
            return destino.toString();
        } catch (IOException e) {
            System.err.println("Error guardando CV: " + e.getMessage());
            return null;
        }
    }

    // Nuevo: escribirCV(nombreArchivo, contenido) para tests
    public void escribirCV(String nombreArchivo, String contenido) throws IOException {
        Path destino = Paths.get(CV_DIRECTORY, nombreArchivo);
        Files.createDirectories(destino.getParent());
        Files.writeString(destino, contenido);
    }

    // Nuevo: leerCV por nombre de archivo (dentro de cvs/)
    public String leerCV(String nombreArchivo) throws IOException {
        Path path = Paths.get(CV_DIRECTORY, nombreArchivo);
        return Files.readString(path);
    }

    // Nuevo: versión original de leerCV por path completo (mantener compatibilidad)
    public String leerCVPorPath(String pathCV) {
        if (pathCV == null || pathCV.isEmpty()) {
            return null;
        }

        try {
            return Files.readString(Paths.get(pathCV));
        } catch (IOException e) {
            System.err.println("Error leyendo CV: " + e.getMessage());
            return null;
        }
    }

    // Verificar existencia de CV por nombre
    public boolean existeCV(String nombreArchivo) {
        Path path = Paths.get(CV_DIRECTORY, nombreArchivo);
        return Files.exists(path);
    }

    // Extrae datos clave del contenido de un CV simple formateado línea por línea
    public Map<String, String> extraerDatos(String contenido) {
        Map<String, String> datos = new HashMap<>();
        if (contenido == null) return datos;
        String[] lineas = contenido.split("\n");
        for (String linea : lineas) {
            String lower = linea.toLowerCase();
            if (lower.startsWith("nombre:")) {
                datos.put("nombre", linea.substring(linea.indexOf(':') + 1).trim());
            } else if (lower.startsWith("apellido:")) {
                datos.put("apellido", linea.substring(linea.indexOf(':') + 1).trim());
            } else if (lower.startsWith("cedula:")) {
                datos.put("cedula", linea.substring(linea.indexOf(':') + 1).trim());
            } else if (lower.startsWith("celular:")) {
                datos.put("celular", linea.substring(linea.indexOf(':') + 1).trim());
            } else if (lower.startsWith("email:")) {
                datos.put("email", linea.substring(linea.indexOf(':') + 1).trim());
            }
        }
        return datos;
    }

    // Listar nombres de archivos CV en la carpeta cvs
    public List<String> listarCVs() {
        List<String> lista = new ArrayList<>();
        File carpeta = new File(CV_DIRECTORY);
        File[] archivos = carpeta.listFiles((dir, name) -> name.toLowerCase().endsWith(".txt"));
        if (archivos != null) {
            for (File f : archivos) {
                lista.add(f.getName());
            }
        }
        return lista;
    }

    // Procesar CV a partir de un archivo
    public Map<String, String> procesarCV(File archivoCV) throws IOException {
        String contenido = Files.readString(archivoCV.toPath());
        return extraerDatos(contenido);
    }

    // Validar archivo como en los tests (File, no String)
    public boolean validarArchivo(File archivo) {
        if (archivo == null) return false;
        if (!archivo.getName().toLowerCase().endsWith(".txt")) return false;
        return archivo.exists() && archivo.isFile();
    }

    /**
     * Elimina un archivo CV
     * @param pathCV Path del archivo CV
     * @return true si se eliminó correctamente, false en caso contrario
     */
    public boolean eliminarCV(String nombreArchivo) {
        Path path = Paths.get(CV_DIRECTORY, nombreArchivo);
        try {
            return Files.deleteIfExists(path);
        } catch (IOException e) {
            System.err.println("Error eliminando CV: " + e.getMessage());
            return false;
        }
    }
}
