//Test class for ABM functionality
//Diego Rocabado
//Santiago Dirón

public class TestABM {
    public static void main(String[] args) {
        Sistema sistema = new Sistema();
        boolean allTestsPassed = true;
        
        System.out.println("=== Testing ABM Áreas Functionality ===\n");
        
        // Test 1: Add area with valid data
        System.out.print("Test 1 - Add valid area: ");
        Area area1 = sistema.agregarArea("Ventas", "Departamento de ventas", 500000);
        if (area1 != null && area1.getId() == 1) {
            System.out.println("✓ PASS");
        } else {
            System.out.println("✗ FAIL");
            allTestsPassed = false;
        }
        
        // Test 2: Validate unique area name
        System.out.print("Test 2 - Reject duplicate area name: ");
        Area area2 = sistema.agregarArea("Ventas", "Otro departamento", 300000);
        if (area2 == null) {
            System.out.println("✓ PASS");
        } else {
            System.out.println("✗ FAIL");
            allTestsPassed = false;
        }
        
        // Test 3: Validate budget > 0
        System.out.print("Test 3 - Reject budget <= 0: ");
        Area area3 = sistema.agregarArea("Marketing", "Marketing dept", 0);
        if (area3 == null) {
            System.out.println("✓ PASS");
        } else {
            System.out.println("✗ FAIL");
            allTestsPassed = false;
        }
        
        // Test 4: Add area with valid budget
        System.out.print("Test 4 - Add area with valid budget: ");
        Area area4 = sistema.agregarArea("Marketing", "Marketing dept", 400000);
        if (area4 != null && area4.getId() == 2) {
            System.out.println("✓ PASS");
        } else {
            System.out.println("✗ FAIL");
            allTestsPassed = false;
        }
        
        // Test 5: Modify area description
        System.out.print("Test 5 - Modify area description: ");
        String originalDesc = area1.getDescripcion();
        sistema.modificarArea(area1, "Nueva descripción");
        if (!area1.getDescripcion().equals(originalDesc) && 
            area1.getDescripcion().equals("Nueva descripción")) {
            System.out.println("✓ PASS");
        } else {
            System.out.println("✗ FAIL");
            allTestsPassed = false;
        }
        
        // Test 6: Delete area without employees
        System.out.print("Test 6 - Delete area without employees: ");
        Area areaToDelete = sistema.agregarArea("Temporal", "Area temporal", 100000);
        boolean deleted = sistema.eliminarArea(areaToDelete);
        if (deleted) {
            System.out.println("✓ PASS");
        } else {
            System.out.println("✗ FAIL");
            allTestsPassed = false;
        }
        
        // Test 7: Cannot delete area with employees
        System.out.print("Test 7 - Reject deletion of area with employees: ");
        Empleado emp1 = new Empleado(1001, "Juan", "Pérez", "12345678", "099123456", 
                                     "/cv/juan.pdf", 5, 30000, null, area1);
        sistema.agregarEmpleado(emp1);
        boolean deletedWithEmployees = sistema.eliminarArea(area1);
        if (!deletedWithEmployees) {
            System.out.println("✓ PASS");
        } else {
            System.out.println("✗ FAIL");
            allTestsPassed = false;
        }
        
        // Test 8: Get areas sorted by name
        System.out.print("Test 8 - Get areas sorted by name: ");
        sistema.agregarArea("ABC", "First alphabetically", 200000);
        var sortedAreas = sistema.getAreasSortedByName();
        if (sortedAreas.size() >= 2 && 
            sortedAreas.get(0).getNombre().compareToIgnoreCase(sortedAreas.get(1).getNombre()) <= 0) {
            System.out.println("✓ PASS");
        } else {
            System.out.println("✗ FAIL");
            allTestsPassed = false;
        }
        
        System.out.println("\n=== Testing Employee Movement Functionality ===\n");
        
        // Test 9: Move employee with sufficient budget
        System.out.print("Test 9 - Move employee with sufficient budget: ");
        Area areaDestino = sistema.agregarArea("TI", "Tecnología de información", 500000);
        int budgetOrigenBefore = area1.getPresupuestoAnual();
        int budgetDestinoBefore = areaDestino.getPresupuestoAnual();
        double salarioAnual = emp1.getSalarioMensual() * 12;
        
        boolean moved = sistema.moverEmpleado(emp1, areaDestino);
        
        int budgetOrigenAfter = area1.getPresupuestoAnual();
        int budgetDestinoAfter = areaDestino.getPresupuestoAnual();
        
        if (moved && 
            emp1.getArea().getId() == areaDestino.getId() &&
            budgetOrigenAfter == (int)(budgetOrigenBefore + salarioAnual) &&
            budgetDestinoAfter == (int)(budgetDestinoBefore - salarioAnual)) {
            System.out.println("✓ PASS");
        } else {
            System.out.println("✗ FAIL - moved:" + moved + ", newArea:" + emp1.getArea().getId() + 
                             ", budget changes: " + budgetOrigenAfter + "/" + budgetDestinoAfter);
            allTestsPassed = false;
        }
        
        // Test 10: Cannot move employee with insufficient budget
        System.out.print("Test 10 - Reject movement with insufficient budget: ");
        Area areaSmallBudget = sistema.agregarArea("Small", "Area with small budget", 10000);
        Empleado emp2 = new Empleado(1002, "María", "González", "87654321", "099654321", 
                                     "/cv/maria.pdf", 3, 50000, null, area4);
        sistema.agregarEmpleado(emp2);
        
        boolean movedWithoutBudget = sistema.moverEmpleado(emp2, areaSmallBudget);
        if (!movedWithoutBudget) {
            System.out.println("✓ PASS");
        } else {
            System.out.println("✗ FAIL");
            allTestsPassed = false;
        }
        
        // Test 11: Movement is registered
        System.out.print("Test 11 - Movement is registered: ");
        int movimientosBefore = sistema.getMovimientos().size();
        Area anotherArea = sistema.agregarArea("RRHH", "Recursos Humanos", 800000);
        sistema.moverEmpleado(emp2, anotherArea);
        int movimientosAfter = sistema.getMovimientos().size();
        if (movimientosAfter > movimientosBefore) {
            System.out.println("✓ PASS");
        } else {
            System.out.println("✗ FAIL");
            allTestsPassed = false;
        }
        
        System.out.println("\n=== Test Summary ===");
        if (allTestsPassed) {
            System.out.println("✓ All tests PASSED!");
        } else {
            System.out.println("✗ Some tests FAILED!");
        }
    }
}
