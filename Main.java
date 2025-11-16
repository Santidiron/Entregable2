//Diego Rocabado
//Santiago Dirón

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Create the system
        Sistema sistema = new Sistema();
        
        // Add some sample data for testing
        inicializarDatos(sistema);
        
        // Create and show the main window
        SwingUtilities.invokeLater(() -> {
            ABMAreasWindow window = new ABMAreasWindow(sistema);
            window.setVisible(true);
        });
    }
    
    private static void inicializarDatos(Sistema sistema) {
        // Add some sample areas
        sistema.agregarArea("Ventas", "Departamento de ventas y comercialización", 500000);
        sistema.agregarArea("Recursos Humanos", "Gestión de personal y talento", 300000);
        sistema.agregarArea("Tecnología", "Desarrollo y mantenimiento de sistemas", 800000);
        sistema.agregarArea("Marketing", "Publicidad y comunicación", 400000);
        
        // Add some sample employees
        Area ventas = sistema.buscarAreaPorId(1);
        Area rrhh = sistema.buscarAreaPorId(2);
        Area tecnologia = sistema.buscarAreaPorId(3);
        
        Empleado emp1 = new Empleado(1001, "Juan", "Pérez", "12345678", "099123456", 
                                     "/cv/juan.pdf", 5, 35000, null, ventas);
        Empleado emp2 = new Empleado(1002, "María", "González", "87654321", "099654321", 
                                     "/cv/maria.pdf", 3, 28000, null, rrhh);
        Empleado emp3 = new Empleado(1003, "Carlos", "Rodríguez", "11223344", "099111222", 
                                     "/cv/carlos.pdf", 7, 45000, null, tecnologia);
        
        sistema.agregarEmpleado(emp1);
        sistema.agregarEmpleado(emp2);
        sistema.agregarEmpleado(emp3);
    }
}
