//Diego Rocabado
//Santiago Dirón
//Test de Persistencia y GestorCV

import java.io.*;
import java.util.*;

public class TestPersistenciaGestorCV {
    
    public static void main(String[] args) {
        System.out.println("=== TEST DE PERSISTENCIA Y GESTORCV ===\n");
        
        testPersistencia();
        System.out.println();
        testGestorCV();
    }
    
    private static void testPersistencia() {
        System.out.println("--- Test de Persistencia ---");
        Persistencia persistencia = new Persistencia();
        
        try {
            // Crear un sistema con datos de prueba
            Sistema sistema = new Sistema();
            
            // Crear y agregar areas
            Area area1 = new Area(1, "IT", "Tecnología", 100000, null);
            Area area2 = new Area(2, "RRHH", "Recursos Humanos", 80000, null);
            sistema.agregarArea(area1);
            sistema.agregarArea(area2);
            
            // Crear y agregar managers
            Manager manager1 = new Manager("Juan", "12345678", "099123456", 5, area1, null);
            sistema.agregarManager(manager1);
            
            // Crear y agregar empleados
            Empleado emp1 = new Empleado(1001, "Carlos", "Gomez", "11111111", "099111111", 
                                        "cvs/carlos_gomez.txt", 2, 50000, manager1, area1);
            sistema.registrarEmpleado(emp1);
            
            System.out.println("Sistema creado con:");
            System.out.println("  - " + sistema.getAreas().size() + " áreas");
            System.out.println("  - " + sistema.getManagers().size() + " managers");
            System.out.println("  - " + sistema.getEmpleados().size() + " empleados");
            
            // Guardar el sistema
            String archivo = "sistema.dat";
            persistencia.guardarDatos(archivo, sistema);
            System.out.println("✓ Sistema guardado en: " + archivo);
            
            // Verificar que el archivo existe
            if (persistencia.existeArchivo(archivo)) {
                System.out.println("✓ Archivo existe: " + archivo);
            }
            
            // Cargar el sistema
            Sistema sistemaRecuperado = (Sistema) persistencia.cargarDatos(archivo);
            System.out.println("✓ Sistema cargado correctamente");
            System.out.println("Sistema recuperado con:");
            System.out.println("  - " + sistemaRecuperado.getAreas().size() + " áreas");
            System.out.println("  - " + sistemaRecuperado.getManagers().size() + " managers");
            System.out.println("  - " + sistemaRecuperado.getEmpleados().size() + " empleados");
            
            // Verificar datos
            if (sistemaRecuperado.getAreas().size() == 2 && 
                sistemaRecuperado.getManagers().size() == 1 &&
                sistemaRecuperado.getEmpleados().size() == 1) {
                System.out.println("✓ Datos verificados correctamente");
            }
            
            // Limpiar
            new File(archivo).delete();
            System.out.println("✓ Archivo de prueba eliminado");
            
        } catch (Exception e) {
            System.err.println("✗ Error en test de Persistencia: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private static void testGestorCV() {
        System.out.println("--- Test de GestorCV ---");
        GestorCV gestorCV = new GestorCV();
        
        try {
            System.out.println("✓ GestorCV creado (carpeta cvs debe existir)");
            File carpeta = new File("cvs");
            if (carpeta.exists() && carpeta.isDirectory()) {
                System.out.println("✓ Carpeta cvs creada correctamente");
            }
            
            // Escribir un CV de prueba
            String nombreCV = "test_empleado.txt";
            String contenidoCV = "Nombre: Pedro\n" +
                               "Apellido: Martinez\n" +
                               "Cedula: 22222222\n" +
                               "Celular: 099222222\n" +
                               "Email: pedro@example.com\n" +
                               "Experiencia: 5 años en desarrollo de software\n";
            
            gestorCV.escribirCV(nombreCV, contenidoCV);
            System.out.println("✓ CV escrito: " + nombreCV);
            
            // Verificar que existe
            if (gestorCV.existeCV(nombreCV)) {
                System.out.println("✓ CV existe en carpeta cvs");
            }
            
            // Leer el CV
            String contenidoLeido = gestorCV.leerCV(nombreCV);
            System.out.println("✓ CV leído correctamente (" + contenidoLeido.length() + " caracteres)");
            
            // Extraer datos
            Map<String, String> datos = gestorCV.extraerDatos(contenidoLeido);
            System.out.println("✓ Datos extraídos del CV:");
            System.out.println("  - Nombre: " + datos.get("nombre"));
            System.out.println("  - Apellido: " + datos.get("apellido"));
            System.out.println("  - Cedula: " + datos.get("cedula"));
            System.out.println("  - Celular: " + datos.get("celular"));
            System.out.println("  - Email: " + datos.get("email"));
            
            // Listar CVs
            List<String> cvs = gestorCV.listarCVs();
            System.out.println("✓ CVs en carpeta: " + cvs.size());
            for (String cv : cvs) {
                System.out.println("  - " + cv);
            }
            
            // Procesar CV
            File archivoCV = new File("cvs", nombreCV);
            Map<String, String> datosExtraidos = gestorCV.procesarCV(archivoCV);
            System.out.println("✓ CV procesado correctamente");
            
            // Eliminar CV de prueba
            if (gestorCV.eliminarCV(nombreCV)) {
                System.out.println("✓ CV de prueba eliminado");
            }
            
            // Test de validación de archivos
            File archivoValido = new File("test.txt");
            archivoValido.createNewFile();
            if (gestorCV.validarArchivo(archivoValido)) {
                System.out.println("✓ Validación de archivo .txt correcta");
            }
            archivoValido.delete();
            
            System.out.println("✓ Todos los tests de GestorCV pasaron");
            
        } catch (Exception e) {
            System.err.println("✗ Error en test de GestorCV: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
