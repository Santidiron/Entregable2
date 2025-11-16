import javax.swing.*;
import java.awt.*;

public class UITest {
    public static void main(String[] args) throws Exception {
        Sistema sistema = new Sistema();
        
        // Initialize with sample data
        sistema.agregarArea("Ventas", "Departamento de ventas", 500000);
        sistema.agregarArea("Recursos Humanos", "Gestión de personal", 300000);
        sistema.agregarArea("Tecnología", "Desarrollo de sistemas", 800000);
        
        Area ventas = sistema.buscarAreaPorId(1);
        Area rrhh = sistema.buscarAreaPorId(2);
        
        Empleado emp1 = new Empleado(1001, "Juan", "Pérez", "12345678", "099123456", 
                                     "/cv/juan.pdf", 5, 30000, null, ventas);
        Empleado emp2 = new Empleado(1002, "María", "González", "87654321", "099654321", 
                                     "/cv/maria.pdf", 3, 25000, null, rrhh);
        
        sistema.agregarEmpleado(emp1);
        sistema.agregarEmpleado(emp2);
        
        // Create and show window
        SwingUtilities.invokeAndWait(() -> {
            ABMAreasWindow window = new ABMAreasWindow(sistema);
            window.setVisible(true);
            
            // Auto-close after 2 seconds
            Timer timer = new Timer(2000, e -> {
                window.dispose();
                System.exit(0);
            });
            timer.setRepeats(false);
            timer.start();
        });
    }
}
