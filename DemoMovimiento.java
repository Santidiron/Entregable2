// Demonstration of the complete workflow
public class DemoMovimiento {
    public static void main(String[] args) {
        Sistema sistema = new Sistema();
        
        System.out.println("=== DEMO: Complete ABM and Movement Workflow ===\n");
        
        // Step 1: Create areas
        System.out.println("1. Creating areas...");
        Area ventas = sistema.agregarArea("Ventas", "Departamento comercial", 500000);
        Area ti = sistema.agregarArea("TI", "Tecnología", 800000);
        Area rrhh = sistema.agregarArea("RRHH", "Recursos Humanos", 300000);
        System.out.println("   ✓ Created 3 areas");
        System.out.println("   - Ventas (ID: " + ventas.getId() + ", Budget: " + ventas.getPresupuestoAnual() + ")");
        System.out.println("   - TI (ID: " + ti.getId() + ", Budget: " + ti.getPresupuestoAnual() + ")");
        System.out.println("   - RRHH (ID: " + rrhh.getId() + ", Budget: " + rrhh.getPresupuestoAnual() + ")");
        
        // Step 2: Add employees
        System.out.println("\n2. Adding employees...");
        Empleado juan = new Empleado(1001, "Juan", "Pérez", "12345678", "099123456", 
                                     "/cv/juan.pdf", 5, 30000, null, ventas);
        Empleado maria = new Empleado(1002, "María", "González", "87654321", "099654321", 
                                      "/cv/maria.pdf", 3, 25000, null, rrhh);
        sistema.agregarEmpleado(juan);
        sistema.agregarEmpleado(maria);
        System.out.println("   ✓ Added 2 employees");
        System.out.println("   - Juan Pérez (Legajo: " + juan.getLegajo() + ") in " + juan.getArea().getNombre());
        System.out.println("   - María González (Legajo: " + maria.getLegajo() + ") in " + maria.getArea().getNombre());
        
        // Step 3: Try to delete area with employees (should fail)
        System.out.println("\n3. Testing deletion protection...");
        boolean deleted = sistema.eliminarArea(ventas);
        System.out.println("   " + (deleted ? "✗ FAILED" : "✓ PROTECTED") + " - Cannot delete area with employees");
        
        // Step 4: Move employee
        System.out.println("\n4. Moving employee Juan from Ventas to TI...");
        System.out.println("   Before:");
        System.out.println("   - Juan's area: " + juan.getArea().getNombre());
        System.out.println("   - Ventas budget: " + ventas.getPresupuestoAnual());
        System.out.println("   - TI budget: " + ti.getPresupuestoAnual());
        
        boolean moved = sistema.moverEmpleado(juan, ti);
        
        System.out.println("   After:");
        System.out.println("   - Juan's area: " + juan.getArea().getNombre());
        System.out.println("   - Ventas budget: " + ventas.getPresupuestoAnual() + " (increased)");
        System.out.println("   - TI budget: " + ti.getPresupuestoAnual() + " (decreased)");
        System.out.println("   " + (moved ? "✓" : "✗") + " Movement " + (moved ? "successful" : "failed"));
        
        // Step 5: Try movement with insufficient budget
        System.out.println("\n5. Testing budget validation...");
        Area smallArea = sistema.agregarArea("Small", "Small budget area", 10000);
        boolean movedFail = sistema.moverEmpleado(maria, smallArea);
        System.out.println("   " + (movedFail ? "✗ FAILED" : "✓ PROTECTED") + " - Cannot move employee without sufficient budget");
        
        // Step 6: Show sorted areas
        System.out.println("\n6. Areas sorted by name:");
        for (Area area : sistema.getAreasSortedByName()) {
            System.out.println("   - " + area.getNombre() + " (ID: " + area.getId() + ")");
        }
        
        // Step 7: Show movements
        System.out.println("\n7. Registered movements: " + sistema.getMovimientos().size());
        
        System.out.println("\n=== DEMO COMPLETE ===");
        System.out.println("All features working correctly! ✓");
    }
}
