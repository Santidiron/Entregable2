//Diego Rocabado
//Santiago Dirón

import java.util.ArrayList;
import java.util.List;

/**
 * Test class for GeminiService and ReporteInteligenteWindow
 */
public class TestGeminiService {
    
    public static void main(String[] args) {
        System.out.println("=== Test de GeminiService ===\n");
        
        // Create test data
        List<Area> areas = new ArrayList<>();
        Area area1 = new Area(1, "Desarrollo", "Área de desarrollo de software y sistemas", 500000, new Empleado[0]);
        Area area2 = new Area(2, "Marketing", "Área de marketing digital y ventas", 300000, new Empleado[0]);
        Area area3 = new Area(3, "Recursos Humanos", "Área de gestión de personal y desarrollo organizacional", 250000, new Empleado[0]);
        areas.add(area1);
        areas.add(area2);
        areas.add(area3);
        
        // Create test employee
        Empleado empleado = new Empleado(
            1001, 
            "Juan", 
            "Pérez", 
            "12345678", 
            "555-1234", 
            "/home/runner/work/Entregable2/Entregable2/test_data/cv1.txt", 
            5, 
            50000.0, 
            null, 
            area1
        );
        
        System.out.println("Empleado: " + empleado.getNombre() + " " + empleado.getApellido());
        System.out.println("Área actual: " + empleado.getArea().getNombre());
        System.out.println("CV path: " + empleado.getPathCV());
        System.out.println("\nÁreas a analizar:");
        for (Area area : areas) {
            System.out.println("  - " + area.getNombre() + ": " + area.getDescripcion());
        }
        
        // Test GeminiService
        System.out.println("\n--- Prueba 1: Inicialización de GeminiService ---");
        GeminiService service = new GeminiService();
        System.out.println("API Key configurada: " + service.isAPIKeyConfigured());
        
        // Test report generation (will use fallback since no API key)
        System.out.println("\n--- Prueba 2: Generación de Reporte (modo fallback) ---");
        String reporte = service.generarReporteInteligente(empleado, areas);
        System.out.println("\nReporte generado:");
        System.out.println("================");
        System.out.println(reporte);
        System.out.println("================");
        
        // Test with employee without CV
        System.out.println("\n--- Prueba 3: Empleado sin CV ---");
        Empleado empleado2 = new Empleado(
            1002, 
            "María", 
            "González", 
            "87654321", 
            "555-5678", 
            null, // No CV
            3, 
            45000.0, 
            null, 
            area2
        );
        
        List<Area> areasSeleccionadas = new ArrayList<>();
        areasSeleccionadas.add(area1);
        areasSeleccionadas.add(area3);
        
        String reporte2 = service.generarReporteInteligente(empleado2, areasSeleccionadas);
        System.out.println("\nReporte para empleado sin CV:");
        System.out.println("================");
        System.out.println(reporte2.substring(0, Math.min(500, reporte2.length())) + "...");
        System.out.println("================");
        
        System.out.println("\n=== Todas las pruebas completadas ===");
    }
}
