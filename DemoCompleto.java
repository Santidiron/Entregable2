//Diego Rocabado
//Santiago Dirón
//Demo completo de Persistencia y GestorCV

import java.io.*;
import java.util.*;

public class DemoCompleto {
    
    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════════════╗");
        System.out.println("║   DEMO COMPLETO: PERSISTENCIA Y GESTORCV           ║");
        System.out.println("╚════════════════════════════════════════════════════╝\n");
        
        demoCompleto();
    }
    
    private static void demoCompleto() {
        try {
            // 1. Crear GestorCV y preparar CVs
            System.out.println("1️⃣  INICIALIZANDO GESTORCV");
            System.out.println("─────────────────────────────────────────────────");
            GestorCV gestorCV = new GestorCV();
            System.out.println("✓ GestorCV creado");
            System.out.println("✓ Carpeta cvs/ disponible en: " + gestorCV.getCarpetaCVs());
            
            // 2. Crear y guardar CVs de ejemplo
            System.out.println("\n2️⃣  CREANDO CVS DE EMPLEADOS");
            System.out.println("─────────────────────────────────────────────────");
            
            String cv1 = "Nombre: Ana\n" +
                        "Apellido: Rodriguez\n" +
                        "Cedula: 33333333\n" +
                        "Celular: 099333333\n" +
                        "Email: ana.rodriguez@example.com\n" +
                        "Experiencia: Desarrolladora Senior con 8 años de experiencia\n" +
                        "Habilidades: Java, Python, Spring Boot, Microservicios";
            
            String cv2 = "Nombre: Luis\n" +
                        "Apellido: Fernandez\n" +
                        "Cedula: 44444444\n" +
                        "Celular: 099444444\n" +
                        "Email: luis.fernandez@example.com\n" +
                        "Experiencia: Analista de RRHH con 5 años de experiencia\n" +
                        "Habilidades: Gestión de personal, Reclutamiento";
            
            gestorCV.escribirCV("ana_rodriguez.txt", cv1);
            gestorCV.escribirCV("luis_fernandez.txt", cv2);
            System.out.println("✓ CV guardado: ana_rodriguez.txt");
            System.out.println("✓ CV guardado: luis_fernandez.txt");
            
            // 3. Crear Sistema con datos
            System.out.println("\n3️⃣  CONSTRUYENDO SISTEMA");
            System.out.println("─────────────────────────────────────────────────");
            Sistema sistema = new Sistema();
            
            // Crear áreas
            Area areaIT = new Area(1, "Tecnología", "Desarrollo y soporte IT", 150000, null);
            Area areaRRHH = new Area(2, "RRHH", "Recursos Humanos", 90000, null);
            Area areaVentas = new Area(3, "Ventas", "Comercial y ventas", 120000, null);
            
            sistema.agregarArea(areaIT);
            sistema.agregarArea(areaRRHH);
            sistema.agregarArea(areaVentas);
            System.out.println("✓ Agregadas 3 áreas");
            
            // Crear managers
            Manager manager1 = new Manager("Maria", "55555555", "099555555", 10, areaIT, null);
            Manager manager2 = new Manager("Roberto", "66666666", "099666666", 7, areaRRHH, null);
            
            sistema.agregarManager(manager1);
            sistema.agregarManager(manager2);
            System.out.println("✓ Agregados 2 managers");
            
            // Crear empleados con referencias a CVs
            Empleado emp1 = new Empleado(1001, "Ana", "Rodriguez", "33333333", "099333333", 
                                        "cvs/ana_rodriguez.txt", 8, 75000, manager1, areaIT);
            Empleado emp2 = new Empleado(1002, "Luis", "Fernandez", "44444444", "099444444",
                                        "cvs/luis_fernandez.txt", 5, 55000, manager2, areaRRHH);
            
            sistema.registrarEmpleado(emp1);
            sistema.registrarEmpleado(emp2);
            System.out.println("✓ Registrados 2 empleados");
            
            // Crear movimientos
            Movimiento mov1 = new Movimiento(1, "2024-01-15", emp1, null, areaIT);
            sistema.registrarMovimiento(mov1);
            System.out.println("✓ Registrado 1 movimiento");
            
            // 4. Persistir el sistema
            System.out.println("\n4️⃣  PERSISTIENDO SISTEMA");
            System.out.println("─────────────────────────────────────────────────");
            Persistencia persistencia = new Persistencia();
            String archivoSistema = "sistema_completo.dat";
            
            persistencia.guardarDatos(archivoSistema, sistema);
            System.out.println("✓ Sistema serializado y guardado en: " + archivoSistema);
            
            File archivo = new File(archivoSistema);
            System.out.println("✓ Tamaño del archivo: " + archivo.length() + " bytes");
            
            // 5. Cargar el sistema
            System.out.println("\n5️⃣  RECUPERANDO SISTEMA");
            System.out.println("─────────────────────────────────────────────────");
            Sistema sistemaRecuperado = (Sistema) persistencia.cargarDatos(archivoSistema);
            System.out.println("✓ Sistema deserializado correctamente");
            
            // 6. Verificar datos recuperados
            System.out.println("\n6️⃣  VERIFICANDO DATOS RECUPERADOS");
            System.out.println("─────────────────────────────────────────────────");
            System.out.println("📊 Estadísticas del Sistema:");
            System.out.println("   • Áreas: " + sistemaRecuperado.getAreas().size());
            System.out.println("   • Managers: " + sistemaRecuperado.getManagers().size());
            System.out.println("   • Empleados: " + sistemaRecuperado.getEmpleados().size());
            System.out.println("   • Movimientos: " + sistemaRecuperado.getMovimientos().size());
            
            System.out.println("\n📋 Detalles de Áreas:");
            for (Area area : sistemaRecuperado.getAreas()) {
                System.out.println("   • " + area.getNombre() + " (Presupuesto: $" + area.getPresupuestoAnual() + ")");
            }
            
            System.out.println("\n👥 Detalles de Empleados:");
            for (Empleado emp : sistemaRecuperado.getEmpleados()) {
                System.out.println("   • " + emp.getNombre() + " " + emp.getApellido());
                System.out.println("     - Legajo: " + emp.getLegajo());
                System.out.println("     - Área: " + emp.getArea().getNombre());
                System.out.println("     - Manager: " + emp.getManager().getNombre());
                System.out.println("     - CV: " + emp.getPathCV());
            }
            
            // 7. Leer CVs desde el sistema recuperado
            System.out.println("\n7️⃣  PROCESANDO CVS DE EMPLEADOS");
            System.out.println("─────────────────────────────────────────────────");
            List<String> cvsDisponibles = gestorCV.listarCVs();
            System.out.println("✓ CVs disponibles: " + cvsDisponibles.size());
            
            for (String cvNombre : cvsDisponibles) {
                System.out.println("\n📄 Procesando: " + cvNombre);
                String contenido = gestorCV.leerCV(cvNombre);
                Map<String, String> datos = gestorCV.extraerDatos(contenido);
                System.out.println("   Nombre completo: " + datos.get("nombre") + " " + datos.get("apellido"));
                System.out.println("   Cédula: " + datos.get("cedula"));
                System.out.println("   Email: " + datos.get("email"));
            }
            
            // 8. Resumen final
            System.out.println("\n╔════════════════════════════════════════════════════╗");
            System.out.println("║              ✅ DEMO COMPLETADO                     ║");
            System.out.println("╚════════════════════════════════════════════════════╝");
            System.out.println("\n✓ Persistencia: Serialización/Deserialización funcional");
            System.out.println("✓ GestorCV: Gestión de archivos CV funcional");
            System.out.println("✓ Sistema: Gestión de entidades funcional");
            System.out.println("✓ Carpeta cvs/: Creación automática funcional");
            System.out.println("✓ Validación: Manejo de errores implementado");
            
            // Limpieza opcional (comentada para que puedas ver los archivos)
            // archivo.delete();
            // gestorCV.eliminarCV("ana_rodriguez.txt");
            // gestorCV.eliminarCV("luis_fernandez.txt");
            
        } catch (Exception e) {
            System.err.println("\n❌ Error durante la demo: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
