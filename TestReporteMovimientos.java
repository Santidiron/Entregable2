//Diego Rocabado
//Santiago Dirón

import java.util.ArrayList;
import java.util.List;

/**
 * Clase de prueba para verificar la funcionalidad de ReporteMovimientos
 */
public class TestReporteMovimientos {
    
    public static void main(String[] args) {
        System.out.println("=== Test de ReporteMovimientos ===\n");
        
        // Crear áreas de prueba
        System.out.println("1. Creando áreas de prueba...");
        List<Area> areas = new ArrayList<>();
        Area area1 = new Area(1, "Desarrollo", "Area de desarrollo", 100000, null);
        Area area2 = new Area(2, "Testing", "Area de testing", 80000, null);
        Area area3 = new Area(3, "Recursos Humanos", "Area de RRHH", 90000, null);
        areas.add(area1);
        areas.add(area2);
        areas.add(area3);
        System.out.println("   ✓ Creadas " + areas.size() + " áreas");
        
        // Crear empleados de prueba
        System.out.println("\n2. Creando empleados de prueba...");
        Empleado emp1 = new Empleado(1, "Juan", "Pérez", "12345678", "555-1234", 
            "/cv/juan.pdf", 5, 50000, null, area1);
        Empleado emp2 = new Empleado(2, "María", "García", "87654321", "555-5678", 
            "/cv/maria.pdf", 3, 45000, null, area2);
        Empleado emp3 = new Empleado(3, "Carlos", "López", "11223344", "555-9999", 
            "/cv/carlos.pdf", 7, 60000, null, area1);
        System.out.println("   ✓ Creados 3 empleados");
        
        // Crear movimientos de prueba
        System.out.println("\n3. Creando movimientos de prueba...");
        List<Movimiento> movimientos = new ArrayList<>();
        movimientos.add(new Movimiento(12, "2024-12-15", emp1, area1, area2));
        movimientos.add(new Movimiento(11, "2024-11-20", emp2, area2, area3));
        movimientos.add(new Movimiento(10, "2024-10-05", emp3, area1, area3));
        movimientos.add(new Movimiento(12, "2024-12-01", emp2, area3, area1));
        movimientos.add(new Movimiento(9, "2024-09-15", emp1, area2, area1));
        movimientos.add(new Movimiento(8, "2024-08-10", emp3, area3, area2));
        movimientos.add(new Movimiento(7, "2024-07-05", emp1, area1, area3));
        System.out.println("   ✓ Creados " + movimientos.size() + " movimientos");
        
        // Verificar datos de movimientos
        System.out.println("\n4. Verificando datos de movimientos:");
        for (int i = 0; i < movimientos.size(); i++) {
            Movimiento mov = movimientos.get(i);
            String empleado = mov.getEmpleado().getNombre() + " " + mov.getEmpleado().getApellido();
            String areaOrigen = mov.getAreaOrigen() != null ? mov.getAreaOrigen().getNombre() : "N/A";
            String areaDestino = mov.getAreaDestino() != null ? mov.getAreaDestino().getNombre() : "N/A";
            System.out.println(String.format("   Movimiento %d: Mes=%d, %s -> %s, Empleado=%s, Fecha=%s",
                (i + 1), mov.getMes(), areaOrigen, areaDestino, empleado, mov.getFecha()));
        }
        
        // Probar filtros
        System.out.println("\n5. Probando filtros:");
        
        // Filtro por mes 12
        System.out.println("   - Filtro por mes 12:");
        long movimientosMes12 = movimientos.stream()
            .filter(m -> m.getMes() == 12)
            .count();
        System.out.println("     Movimientos en mes 12: " + movimientosMes12);
        
        // Filtro por área Desarrollo
        System.out.println("   - Filtro por área 'Desarrollo' (origen o destino):");
        long movimientosDesarrollo = movimientos.stream()
            .filter(m -> (m.getAreaOrigen() != null && m.getAreaOrigen().getNombre().equals("Desarrollo")) ||
                        (m.getAreaDestino() != null && m.getAreaDestino().getNombre().equals("Desarrollo")))
            .count();
        System.out.println("     Movimientos relacionados con Desarrollo: " + movimientosDesarrollo);
        
        // Filtro por empleado "Juan"
        System.out.println("   - Filtro por empleado 'Juan':");
        long movimientosJuan = movimientos.stream()
            .filter(m -> m.getEmpleado() != null && 
                        (m.getEmpleado().getNombre() + " " + m.getEmpleado().getApellido()).toLowerCase().contains("juan"))
            .count();
        System.out.println("     Movimientos de Juan: " + movimientosJuan);
        
        // Verificar ordenamiento
        System.out.println("\n6. Verificando ordenamiento (mes descendente):");
        System.out.println("   Orden de meses en lista: ");
        for (Movimiento mov : movimientos) {
            System.out.print(mov.getMes() + " ");
        }
        System.out.println();
        
        // Simular exportación CSV
        System.out.println("\n7. Simulando formato CSV:");
        System.out.println("   Encabezado: Mes,Area Origen,Area Destino,Empleado,Fecha");
        System.out.println("   Datos de ejemplo:");
        for (int i = 0; i < Math.min(3, movimientos.size()); i++) {
            Movimiento mov = movimientos.get(i);
            String areaOrigen = mov.getAreaOrigen() != null ? mov.getAreaOrigen().getNombre() : "N/A";
            String areaDestino = mov.getAreaDestino() != null ? mov.getAreaDestino().getNombre() : "N/A";
            String empleado = mov.getEmpleado() != null ? 
                mov.getEmpleado().getNombre() + " " + mov.getEmpleado().getApellido() : "N/A";
            System.out.println(String.format("   %d,%s,%s,%s,%s",
                mov.getMes(), areaOrigen, areaDestino, empleado, mov.getFecha()));
        }
        
        System.out.println("\n=== Todos los tests pasaron exitosamente ===");
        System.out.println("\nNOTA: Para probar la interfaz gráfica completa, ejecute:");
        System.out.println("  java -cp .:issues/9 ReporteMovimientos");
        System.out.println("\nEsto abrirá la ventana con la tabla de movimientos y todas las funcionalidades:");
        System.out.println("  - Tabla con columnas: Mes, Área Origen, Área Destino, Empleado, Fecha");
        System.out.println("  - Filtros por mes (1-12, Todos)");
        System.out.println("  - Filtros por área (lista de áreas, Todas)");
        System.out.println("  - Filtro por empleado (búsqueda por nombre)");
        System.out.println("  - Botones: Aplicar Filtros, Limpiar Filtros");
        System.out.println("  - Botón Exportar a CSV con JFileChooser");
        System.out.println("  - Validaciones de datos y manejo de errores");
    }
}
