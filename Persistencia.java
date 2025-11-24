//Diego Rocabado - Número de estudiante: 305310
//Santiago Dirón - Número de estudiante: 359644

import java.io.*;

public class Persistencia {
    
    public Persistencia() {
    }

    public void guardarDatos(String nombreArchivo, Object objeto) throws IOException {
        if (nombreArchivo == null || nombreArchivo.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del archivo no puede ser nulo o vacío");
        }
        if (objeto == null) {
            throw new IllegalArgumentException("El objeto a guardar no puede ser nulo");
        }
        
        File archivo = new File(nombreArchivo);
        File directorio = archivo.getParentFile();
        if (directorio != null && !directorio.exists()) {
            directorio.mkdirs();
        }
        
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(archivo))) {
            oos.writeObject(objeto);
        }
    }

    public Sistema cargarDatos(String nombreArchivo) throws IOException, ClassNotFoundException {
        if (nombreArchivo == null || nombreArchivo.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del archivo no puede ser nulo o vacío");
        }
        if (!existeArchivo(nombreArchivo)) {
            throw new FileNotFoundException("El archivo no existe: " + nombreArchivo);
        }
        
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(nombreArchivo))) {
            return (Sistema) ois.readObject();
        }
    }

    public boolean existeArchivo(String nombreArchivo) {
        if (nombreArchivo == null || nombreArchivo.trim().isEmpty()) {
            return false;
        }
        File archivo = new File(nombreArchivo);
        return archivo.exists() && archivo.isFile();
    }
}
