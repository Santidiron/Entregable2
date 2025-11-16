//Diego Rocabado
//Santiago Dirón

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class TestGUI {
    public static void main(String[] args) throws Exception {
        System.out.println("=== Test de GUI del Sistema de Empleados ===\n");

        // Crear sistema con datos de prueba
        Sistema sistema = new Sistema();
        
        // Crear áreas
        Area area1 = new Area(1, "Desarrollo", "Área de desarrollo de software", 500000, new Empleado[0]);
        Area area2 = new Area(2, "Recursos Humanos", "Área de gestión de personal", 300000, new Empleado[0]);
        sistema.agregarArea(area1);
        sistema.agregarArea(area2);

        // Crear managers
        Manager manager1 = new Manager("Juan Pérez", "12345678", "099123456", 5, area1, new Empleado[0]);
        Manager manager2 = new Manager("María López", "87654321", "099654321", 3, area2, new Empleado[0]);
        sistema.agregarManager(manager1);
        sistema.agregarManager(manager2);

        // Crear algunos empleados de prueba
        sistema.crearEmpleado("Pedro", "González", "11223344", "099111222", null, 2, 15000.0, manager1, area1);
        sistema.crearEmpleado("Ana", "Martínez", "22334455", "099222333", null, 3, 18000.0, manager1, area1);
        sistema.crearEmpleado("Carlos", "Rodríguez", "33445566", "099333444", null, 1, 12000.0, manager2, area2);

        System.out.println("1. Creando ventana EmpleadoList...");
        EmpleadoList frame = new EmpleadoList(sistema);
        frame.setVisible(true);

        // Esperar a que la ventana se renderice
        Thread.sleep(1000);

        // Tomar captura de pantalla
        System.out.println("2. Capturando pantalla de EmpleadoList...");
        BufferedImage image = new Robot().createScreenCapture(frame.getBounds());
        ImageIO.write(image, "png", new File("/tmp/empleado_list_screenshot.png"));
        System.out.println("   - Captura guardada en: /tmp/empleado_list_screenshot.png");

        // Probar ventana de formulario
        System.out.println("\n3. Creando ventana EmpleadoForm...");
        EmpleadoForm formFrame = new EmpleadoForm(sistema);
        formFrame.setVisible(true);

        // Esperar a que la ventana se renderice
        Thread.sleep(1000);

        // Tomar captura de pantalla
        System.out.println("4. Capturando pantalla de EmpleadoForm...");
        BufferedImage formImage = new Robot().createScreenCapture(formFrame.getBounds());
        ImageIO.write(formImage, "png", new File("/tmp/empleado_form_screenshot.png"));
        System.out.println("   - Captura guardada en: /tmp/empleado_form_screenshot.png");

        System.out.println("\n=== Tests de GUI completados ===");
        System.out.println("Las ventanas seguirán abiertas. Presione Ctrl+C para salir.");
    }
}
