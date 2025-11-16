//Diego Rocabado
//Santiago Dirón

import java.io.*;

public class Persistencia {
    
    public Persistencia() {
    }

    /**
     * Guarda un objeto en un archivo usando serialización
     * @param nombreArchivo Ruta del archivo donde guardar
     * @param objeto Objeto a serializar y guardar
     * @throws IOException Si hay error al escribir el archivo
     */
    public void guardarDatos(String nombreArchivo, Object objeto) throws IOException {
        if (nombreArchivo == null || nombreArchivo.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del archivo no puede ser nulo o vacío");
        }
        if (objeto == null) {
            throw new IllegalArgumentException("El objeto a guardar no puede ser nulo");
        }
        
        // Crear directorio si no existe
        File archivo = new File(nombreArchivo);
        File directorio = archivo.getParentFile();
        if (directorio != null && !directorio.exists()) {
            directorio.mkdirs();
        }
        
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(archivo))) {
            oos.writeObject(objeto);
        }
    }

    /**
     * Carga un objeto desde un archivo usando deserialización
     * @param nombreArchivo Ruta del archivo a leer
     * @return El objeto deserializado
     * @throws IOException Si hay error al leer el archivo
     * @throws ClassNotFoundException Si no se encuentra la clase del objeto
     */
    public Object cargarDatos(String nombreArchivo) throws IOException, ClassNotFoundException {
        if (nombreArchivo == null || nombreArchivo.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del archivo no puede ser nulo o vacío");
        }
        if (!existeArchivo(nombreArchivo)) {
            throw new FileNotFoundException("El archivo no existe: " + nombreArchivo);
        }
        
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(nombreArchivo))) {
            return ois.readObject();
        }
    }

    /**
     * Verifica si un archivo existe
     * @param nombreArchivo Ruta del archivo a verificar
     * @return true si el archivo existe, false en caso contrario
     */
    public boolean existeArchivo(String nombreArchivo) {
        if (nombreArchivo == null || nombreArchivo.trim().isEmpty()) {
            return false;
        }
        File archivo = new File(nombreArchivo);
        return archivo.exists() && archivo.isFile();
    }
}
