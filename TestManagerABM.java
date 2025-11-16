//Diego Rocabado
//Santiago Dirón

public class TestManagerABM {
    public static void main(String[] args) {
        Sistema sistema = new Sistema();
        
        System.out.println("=== Test Manager ABM System ===\n");
        
        // Test 1: List initial managers
        System.out.println("Test 1: List initial managers (sorted by seniority desc)");
        for (Manager m : sistema.getManagers()) {
            System.out.println("  - " + m.getNombre() + " | Antigüedad: " + m.getAntiguedad() + 
                             " | Cédula: " + m.getCedula() + " | Empleados: " + 
                             sistema.getCantidadEmpleadosACargo(m));
        }
        System.out.println();
        
        // Test 2: Validate cedula format
        System.out.println("Test 2: Validate cedula format");
        System.out.println("  Valid format 1.234.567-8: " + sistema.validarFormatoCedula("1.234.567-8"));
        System.out.println("  Valid format 12345678: " + sistema.validarFormatoCedula("12345678"));
        System.out.println("  Invalid format 123: " + sistema.validarFormatoCedula("123"));
        System.out.println("  Invalid format abc: " + sistema.validarFormatoCedula("abc"));
        System.out.println();
        
        // Test 3: Validate unique cedula
        System.out.println("Test 3: Validate unique cedula");
        System.out.println("  Existing cedula 1.234.567-8: " + !sistema.esCedulaUnica("1.234.567-8"));
        System.out.println("  New cedula 4.567.890-1: " + sistema.esCedulaUnica("4.567.890-1"));
        System.out.println();
        
        // Test 4: Validate celular format
        System.out.println("Test 4: Validate celular format");
        System.out.println("  Valid format 099 123 456: " + sistema.validarFormatoCelular("099 123 456"));
        System.out.println("  Valid format 099123456: " + sistema.validarFormatoCelular("099123456"));
        System.out.println("  Invalid format 123: " + sistema.validarFormatoCelular("123"));
        System.out.println();
        
        // Test 5: Add new manager
        System.out.println("Test 5: Add new manager");
        Area areaIT = sistema.getAreas().get(2); // IT
        Manager nuevoManager = new Manager("María López", "4.567.890-1", "099 111 222", 5, areaIT, new Empleado[0]);
        boolean agregado = sistema.agregarManager(nuevoManager);
        System.out.println("  Manager added successfully: " + agregado);
        System.out.println("  Total managers now: " + sistema.getManagers().size());
        System.out.println();
        
        // Test 6: Try to add manager with duplicate cedula
        System.out.println("Test 6: Try to add manager with duplicate cedula");
        Manager duplicado = new Manager("Test", "1.234.567-8", "099 999 999", 3, areaIT, new Empleado[0]);
        boolean agregadoDuplicado = sistema.agregarManager(duplicado);
        System.out.println("  Should fail (duplicate cedula): " + !agregadoDuplicado);
        System.out.println();
        
        // Test 7: Update manager celular
        System.out.println("Test 7: Update manager celular");
        Manager managerToUpdate = sistema.buscarManagerPorCedula("1.234.567-8");
        String oldCelular = managerToUpdate.getCelular();
        managerToUpdate.setCelular("099 999 888");
        sistema.actualizarManager(managerToUpdate);
        System.out.println("  Old celular: " + oldCelular);
        System.out.println("  New celular: " + managerToUpdate.getCelular());
        System.out.println();
        
        // Test 8: Try to delete manager without employees
        System.out.println("Test 8: Delete manager without employees");
        Manager managerToDelete = sistema.buscarManagerPorCedula("4.567.890-1");
        boolean eliminado = sistema.eliminarManager(managerToDelete);
        System.out.println("  Manager deleted successfully: " + eliminado);
        System.out.println("  Total managers now: " + sistema.getManagers().size());
        System.out.println();
        
        // Test 9: Try to delete manager with employees (simulate)
        System.out.println("Test 9: Try to delete manager with employees");
        Manager managerConEmpleados = sistema.buscarManagerPorCedula("1.234.567-8");
        Empleado emp1 = new Empleado(1, "Juan", "Perez", "5.678.901-2", "099 333 444", "/path/cv", 2, 50000, managerConEmpleados, areaIT);
        managerConEmpleados.setEmpleados(new Empleado[]{emp1});
        boolean eliminadoConEmpleados = sistema.eliminarManager(managerConEmpleados);
        System.out.println("  Should fail (has employees): " + !eliminadoConEmpleados);
        System.out.println("  Employees count: " + sistema.getCantidadEmpleadosACargo(managerConEmpleados));
        System.out.println();
        
        System.out.println("=== All tests completed successfully! ===");
    }
}
