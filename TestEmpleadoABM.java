//Diego Rocabado
//Santiago Dirón

public class TestEmpleadoABM {
    public static void main(String[] args) {
        System.out.println("=== Test del Sistema de Gestión de Empleados ===\n");

        // Crear sistema
        Sistema sistema = new Sistema();
        
        // Crear áreas de prueba
        System.out.println("1. Creando áreas de prueba...");
        Area area1 = new Area(1, "Desarrollo", "Área de desarrollo de software", 500000, new Empleado[0]);
        Area area2 = new Area(2, "Recursos Humanos", "Área de gestión de personal", 300000, new Empleado[0]);
        sistema.agregarArea(area1);
        sistema.agregarArea(area2);
        System.out.println("   - Área 1: " + area1.getNombre() + " (Presupuesto: " + area1.getPresupuestoAnual() + ")");
        System.out.println("   - Área 2: " + area2.getNombre() + " (Presupuesto: " + area2.getPresupuestoAnual() + ")");

        // Crear managers de prueba
        System.out.println("\n2. Creando managers de prueba...");
        Manager manager1 = new Manager("Juan Pérez", "12345678", "099123456", 5, area1, new Empleado[0]);
        Manager manager2 = new Manager("María López", "87654321", "099654321", 3, area2, new Empleado[0]);
        sistema.agregarManager(manager1);
        sistema.agregarManager(manager2);
        System.out.println("   - Manager 1: " + manager1.getNombre() + " (Cédula: " + manager1.getCedula() + ")");
        System.out.println("   - Manager 2: " + manager2.getNombre() + " (Cédula: " + manager2.getCedula() + ")");

        // Test de validación de cédula única
        System.out.println("\n3. Test de validación de cédula única...");
        System.out.println("   - ¿Cédula 11223344 es única? " + sistema.validarCedulaUnica("11223344"));
        System.out.println("   - ¿Cédula 12345678 es única? " + sistema.validarCedulaUnica("12345678") + " (debe ser false, ya existe en manager)");

        // Test de validación de presupuesto
        System.out.println("\n4. Test de validación de presupuesto...");
        System.out.println("   - ¿Área Desarrollo tiene presupuesto para salario 10000? " + 
            sistema.validarPresupuestoArea(area1, 10000));
        System.out.println("   - ¿Área Desarrollo tiene presupuesto para salario 50000? " + 
            sistema.validarPresupuestoArea(area1, 50000) + " (debe ser false si excede)");

        // Crear empleado de prueba sin CV
        System.out.println("\n5. Creando empleado sin CV...");
        try {
            Empleado emp1 = sistema.crearEmpleado(
                "Pedro",
                "González",
                "11223344",
                "099111222",
                null, // Sin CV
                2,
                15000.0,
                manager1,
                area1
            );
            System.out.println("   - Empleado creado exitosamente:");
            System.out.println("     * Legajo: " + emp1.getLegajo());
            System.out.println("     * Nombre: " + emp1.getNombre() + " " + emp1.getApellido());
            System.out.println("     * Cédula: " + emp1.getCedula());
            System.out.println("     * Salario: " + emp1.getSalarioMensual());
            System.out.println("     * Manager: " + emp1.getManager().getNombre());
            System.out.println("     * Área: " + emp1.getArea().getNombre());
        } catch (Exception e) {
            System.out.println("   - Error al crear empleado: " + e.getMessage());
        }

        // Crear empleado con CV
        System.out.println("\n6. Creando empleado con CV...");
        try {
            String cvPath = "/tmp/test_cv/cv_pedro.txt";
            Empleado emp2 = sistema.crearEmpleado(
                "Ana",
                "Martínez",
                "22334455",
                "099222333",
                cvPath,
                3,
                18000.0,
                manager1,
                area1
            );
            System.out.println("   - Empleado creado exitosamente:");
            System.out.println("     * Legajo: " + emp2.getLegajo());
            System.out.println("     * Nombre: " + emp2.getNombre() + " " + emp2.getApellido());
            System.out.println("     * CV guardado en: " + emp2.getPathCV());
            
            // Leer CV
            String contenidoCV = sistema.leerCVEmpleado(emp2);
            if (contenidoCV != null) {
                System.out.println("     * CV leído correctamente (primeras 100 caracteres):");
                System.out.println("       " + contenidoCV.substring(0, Math.min(100, contenidoCV.length())) + "...");
            }
        } catch (Exception e) {
            System.out.println("   - Error al crear empleado: " + e.getMessage());
        }

        // Test de cédula duplicada
        System.out.println("\n7. Test de cédula duplicada...");
        try {
            sistema.crearEmpleado(
                "Carlos",
                "Rodríguez",
                "11223344", // Cédula duplicada
                "099333444",
                null,
                1,
                12000.0,
                manager1,
                area1
            );
            System.out.println("   - ERROR: No debería permitir crear empleado con cédula duplicada");
        } catch (IllegalArgumentException e) {
            System.out.println("   - Validación correcta: " + e.getMessage());
        }

        // Test de actualización de empleado
        System.out.println("\n8. Test de actualización de empleado...");
        try {
            Empleado emp = sistema.buscarEmpleadoPorLegajo(1000);
            if (emp != null) {
                System.out.println("   - Empleado antes de actualizar:");
                System.out.println("     * Celular: " + emp.getCelular());
                System.out.println("     * Salario: " + emp.getSalarioMensual());
                
                sistema.actualizarEmpleado(emp, "099999888", 16000.0);
                
                System.out.println("   - Empleado después de actualizar:");
                System.out.println("     * Celular: " + emp.getCelular());
                System.out.println("     * Salario: " + emp.getSalarioMensual());
            }
        } catch (Exception e) {
            System.out.println("   - Error al actualizar empleado: " + e.getMessage());
        }

        // Listar empleados ordenados
        System.out.println("\n9. Listado de empleados ordenados por nombre...");
        java.util.List<Empleado> empleados = sistema.obtenerEmpleadosOrdenados();
        for (Empleado emp : empleados) {
            System.out.println("   - " + emp.getNombre() + " " + emp.getApellido() + 
                " (Legajo: " + emp.getLegajo() + ", Salario: " + emp.getSalarioMensual() + ")");
        }

        System.out.println("\n=== Tests completados exitosamente ===");
    }
}
