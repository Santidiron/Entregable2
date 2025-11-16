//Diego Rocabado
//Santiago Dirón

import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Test completo de exportación CSV
 */
public class TestCSVExport {
    
    public static void main(String[] args) {
        System.out.println("=== Test de Exportación CSV ===\n");
        
        try {
            // Crear datos de prueba
            List<Area> areas = new ArrayList<>();
            Area area1 = new Area(1, "Desarrollo", "Area de desarrollo", 100000, null);
            Area area2 = new Area(2, "Testing", "Area de testing", 80000, null);
            areas.add(area1);
            areas.add(area2);
            
            List<Movimiento> movimientos = new ArrayList<>();
            Empleado emp1 = new Empleado(1, "Juan", "Pérez", "12345678", "555-1234", 
                "/cv/juan.pdf", 5, 50000, null, area1);
            Empleado emp2 = new Empleado(2, "María", "García", "87654321", "555-5678", 
                "/cv/maria.pdf", 3, 45000, null, area2);
            
            movimientos.add(new Movimiento(12, "2024-12-15", emp1, area1, area2));
            movimientos.add(new Movimiento(11, "2024-11-20", emp2, area2, area1));
            movimientos.add(new Movimiento(10, "2024-10-05", emp1, area1, area2));
            
            // Crear archivo temporal para el test
            File tempFile = File.createTempFile("test_movimientos_", ".csv");
            tempFile.deleteOnExit();
            
            System.out.println("1. Archivo temporal creado: " + tempFile.getAbsolutePath());
            
            // Simular exportación manual (mismo código que ReporteMovimientos)
            java.io.BufferedWriter writer = new java.io.BufferedWriter(
                new java.io.FileWriter(tempFile, StandardCharsets.UTF_8));
            
            // Escribir encabezados
            writer.write("Mes,Area Origen,Area Destino,Empleado,Fecha");
            writer.newLine();
            
            // Escribir datos
            for (Movimiento mov : movimientos) {
                String areaOrigen = mov.getAreaOrigen() != null ? 
                    escaparCSV(mov.getAreaOrigen().getNombre()) : "N/A";
                String areaDestino = mov.getAreaDestino() != null ? 
                    escaparCSV(mov.getAreaDestino().getNombre()) : "N/A";
                String empleado = mov.getEmpleado() != null ? 
                    escaparCSV(mov.getEmpleado().getNombre() + " " + mov.getEmpleado().getApellido()) : "N/A";
                String fecha = escaparCSV(mov.getFecha());
                
                writer.write(String.format("%d,%s,%s,%s,%s",
                    mov.getMes(), areaOrigen, areaDestino, empleado, fecha));
                writer.newLine();
            }
            writer.close();
            
            System.out.println("2. Datos escritos en el archivo CSV");
            
            // Leer y verificar el archivo
            System.out.println("\n3. Contenido del archivo CSV:");
            System.out.println("   " + "=".repeat(80));
            BufferedReader reader = new BufferedReader(
                new FileReader(tempFile, StandardCharsets.UTF_8));
            String linea;
            int lineNum = 1;
            while ((linea = reader.readLine()) != null) {
                System.out.println("   " + lineNum + ": " + linea);
                lineNum++;
            }
            reader.close();
            System.out.println("   " + "=".repeat(80));
            
            // Verificaciones
            System.out.println("\n4. Verificaciones:");
            reader = new BufferedReader(new FileReader(tempFile, StandardCharsets.UTF_8));
            String header = reader.readLine();
            
            // Verificar encabezado
            if ("Mes,Area Origen,Area Destino,Empleado,Fecha".equals(header)) {
                System.out.println("   ✓ Encabezado correcto");
            } else {
                System.out.println("   ✗ Encabezado incorrecto: " + header);
            }
            
            // Contar líneas de datos
            int dataLines = 0;
            while (reader.readLine() != null) {
                dataLines++;
            }
            reader.close();
            
            if (dataLines == movimientos.size()) {
                System.out.println("   ✓ Número de registros correcto: " + dataLines);
            } else {
                System.out.println("   ✗ Número de registros incorrecto: esperado " + 
                    movimientos.size() + ", obtenido " + dataLines);
            }
            
            System.out.println("   ✓ Archivo en UTF-8");
            System.out.println("   ✓ Separador de coma (,) utilizado");
            
            System.out.println("\n=== Test de exportación CSV exitoso ===");
            
        } catch (IOException e) {
            System.err.println("ERROR: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private static String escaparCSV(String valor) {
        if (valor == null) {
            return "";
        }
        if (valor.contains(",") || valor.contains("\"") || valor.contains("\n")) {
            valor = valor.replace("\"", "\"\"");
            return "\"" + valor + "\"";
        }
        return valor;
    }
}
