//Diego Rocabado
//Santiago Dirón

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class GestorCV {
    private static final String CARPETA_CVS = "cvs";
    
    public GestorCV() {
        crearCarpetaCVs();
    }

    /**
     * Crea la carpeta cvs si no existe
     */
    private void crearCarpetaCVs() {
        File carpeta = new File(CARPETA_CVS);
        if (!carpeta.exists()) {
            carpeta.mkdir();
        }
    }

    /**
     * Procesa un archivo CV y extrae información básica
     * @param archivoCV El archivo CV a procesar
     * @return Un mapa con los datos extraídos
     * @throws IOException Si hay error al leer el archivo
     */
    public Map<String, String> procesarCV(File archivoCV) throws IOException {
        if (archivoCV == null) {
            throw new IllegalArgumentException("El archivo CV no puede ser nulo");
        }
        if (!archivoCV.exists()) {
            throw new FileNotFoundException("El archivo CV no existe: " + archivoCV.getPath());
        }
        if (!validarArchivo(archivoCV)) {
            throw new IllegalArgumentException("El archivo CV no es válido");
        }
        
        return extraerDatos(leerArchivo(archivoCV));
    }

    /**
     * Lee el contenido de un archivo CV
     * @param archivo El archivo a leer
     * @return El contenido del archivo como String
     * @throws IOException Si hay error al leer
     */
    private String leerArchivo(File archivo) throws IOException {
        return new String(Files.readAllBytes(archivo.toPath()));
    }

    /**
     * Extrae datos básicos del contenido del CV
     * @param contenido El contenido del CV
     * @return Un mapa con los datos extraídos
     */
    public Map<String, String> extraerDatos(String contenido) {
        Map<String, String> datos = new HashMap<>();
        
        if (contenido == null || contenido.trim().isEmpty()) {
            return datos;
        }
        
        // Extracción básica de datos (puede ser mejorada con regex o procesamiento más avanzado)
        String[] lineas = contenido.split("\n");
        
        for (String linea : lineas) {
            linea = linea.trim();
            if (linea.toLowerCase().startsWith("nombre:")) {
                datos.put("nombre", linea.substring(7).trim());
            } else if (linea.toLowerCase().startsWith("apellido:")) {
                datos.put("apellido", linea.substring(9).trim());
            } else if (linea.toLowerCase().startsWith("cedula:") || linea.toLowerCase().startsWith("cédula:")) {
                datos.put("cedula", linea.substring(linea.indexOf(":") + 1).trim());
            } else if (linea.toLowerCase().startsWith("celular:")) {
                datos.put("celular", linea.substring(8).trim());
            } else if (linea.toLowerCase().startsWith("email:") || linea.toLowerCase().startsWith("e-mail:")) {
                datos.put("email", linea.substring(linea.indexOf(":") + 1).trim());
            }
        }
        
        return datos;
    }

    /**
     * Escribe un archivo CV en la carpeta cvs
     * @param nombreArchivo Nombre del archivo (sin ruta)
     * @param contenido Contenido del CV
     * @throws IOException Si hay error al escribir
     */
    public void escribirCV(String nombreArchivo, String contenido) throws IOException {
        if (nombreArchivo == null || nombreArchivo.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del archivo no puede ser nulo o vacío");
        }
        if (contenido == null) {
            throw new IllegalArgumentException("El contenido no puede ser nulo");
        }
        
        crearCarpetaCVs();
        
        File archivo = new File(CARPETA_CVS, nombreArchivo);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo))) {
            writer.write(contenido);
        }
    }

    /**
     * Lee un archivo CV de la carpeta cvs
     * @param nombreArchivo Nombre del archivo (sin ruta)
     * @return El contenido del archivo
     * @throws IOException Si hay error al leer
     */
    public String leerCV(String nombreArchivo) throws IOException {
        if (nombreArchivo == null || nombreArchivo.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del archivo no puede ser nulo o vacío");
        }
        
        File archivo = new File(CARPETA_CVS, nombreArchivo);
        if (!archivo.exists()) {
            throw new FileNotFoundException("El archivo CV no existe: " + nombreArchivo);
        }
        
        return leerArchivo(archivo);
    }

    /**
     * Valida que un archivo CV tenga el formato correcto
     * @param archivo El archivo a validar
     * @return true si el archivo es válido, false en caso contrario
     */
    public boolean validarArchivo(File archivo) {
        if (archivo == null || !archivo.exists()) {
            return false;
        }
        
        // Validar que sea un archivo de texto
        String nombre = archivo.getName().toLowerCase();
        return nombre.endsWith(".txt") || nombre.endsWith(".pdf") || nombre.endsWith(".doc") || nombre.endsWith(".docx");
    }

    /**
     * Lista todos los archivos CV en la carpeta cvs
     * @return Lista de nombres de archivos
     */
    public List<String> listarCVs() {
        List<String> cvs = new ArrayList<>();
        File carpeta = new File(CARPETA_CVS);
        
        if (carpeta.exists() && carpeta.isDirectory()) {
            File[] archivos = carpeta.listFiles();
            if (archivos != null) {
                for (File archivo : archivos) {
                    if (archivo.isFile()) {
                        cvs.add(archivo.getName());
                    }
                }
            }
        }
        
        return cvs;
    }

    /**
     * Elimina un archivo CV de la carpeta cvs
     * @param nombreArchivo Nombre del archivo a eliminar
     * @return true si se eliminó correctamente, false en caso contrario
     */
    public boolean eliminarCV(String nombreArchivo) {
        if (nombreArchivo == null || nombreArchivo.trim().isEmpty()) {
            return false;
        }
        
        File archivo = new File(CARPETA_CVS, nombreArchivo);
        return archivo.exists() && archivo.delete();
    }

    /**
     * Verifica si existe un archivo CV en la carpeta cvs
     * @param nombreArchivo Nombre del archivo
     * @return true si existe, false en caso contrario
     */
    public boolean existeCV(String nombreArchivo) {
        if (nombreArchivo == null || nombreArchivo.trim().isEmpty()) {
            return false;
        }
        
        File archivo = new File(CARPETA_CVS, nombreArchivo);
        return archivo.exists() && archivo.isFile();
    }

    /**
     * Obtiene la ruta completa de la carpeta cvs
     * @return La ruta de la carpeta cvs
     */
    public String getCarpetaCVs() {
        return CARPETA_CVS;
    }
}
