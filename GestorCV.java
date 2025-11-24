//Diego Rocabado - Número de estudiante: 305310
//Santiago Dirón - Número de estudiante: 359644

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class GestorCV {
    private static final String CV_DIRECTORY = "cvs";

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

    public String guardarCV(String archivoOrigen, String cedula, String nombre, String apellido) {
        if (archivoOrigen == null || archivoOrigen.isEmpty()) {
            return null;
        }

        if (!archivoOrigen.toLowerCase().endsWith(".txt")) {
            System.err.println("Error: El archivo CV debe ser .txt");
            return null;
        }

        String nombreArchivo = cedula + "_" + nombre + "_" + apellido + ".txt";
        Path origen = Paths.get(archivoOrigen);
        Path destino = Paths.get(CV_DIRECTORY, nombreArchivo);

        try {
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

    public void escribirCV(String nombreArchivo, String contenido) throws IOException {
        Path destino = Paths.get(CV_DIRECTORY, nombreArchivo);
        Files.createDirectories(destino.getParent());
        Files.writeString(destino, contenido);
    }

    public String leerCV(String nombreArchivo) throws IOException {
        Path path = Paths.get(CV_DIRECTORY, nombreArchivo);
        return Files.readString(path);
    }

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

    public boolean existeCV(String nombreArchivo) {
        Path path = Paths.get(CV_DIRECTORY, nombreArchivo);
        return Files.exists(path);
    }

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

    public Map<String, String> procesarCV(File archivoCV) throws IOException {
        String contenido = Files.readString(archivoCV.toPath());
        return extraerDatos(contenido);
    }

    public boolean validarArchivo(File archivo) {
        if (archivo == null) return false;
        if (!archivo.getName().toLowerCase().endsWith(".txt")) return false;
        return archivo.exists() && archivo.isFile();
    }

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
