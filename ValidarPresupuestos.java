//Diego Rocabado
//Santiago Dirón

import java.util.HashMap;
import java.util.Map;

public class ValidarPresupuestos {
    public static void main(String[] args) {
        Sistema sistema = new Sistema();
        sistema.cargarDatosPrecargados();
        
        System.out.println("=== VALIDACIÓN DE PRESUPUESTOS ===\n");
        
        // Calculate total salaries per area
        Map<String, Double> salariosPorArea = new HashMap<>();
        Map<String, Integer> empleadosPorArea = new HashMap<>();
        
        for (Empleado empleado : sistema.getEmpleados()) {
            String nombreArea = empleado.getArea().getNombre();
            double salarioAnual = empleado.getSalarioMensual() * 12;
            
            salariosPorArea.put(nombreArea, 
                salariosPorArea.getOrDefault(nombreArea, 0.0) + salarioAnual);
            empleadosPorArea.put(nombreArea,
                empleadosPorArea.getOrDefault(nombreArea, 0) + 1);
        }
        
        // Validate budgets
        boolean todosDentroPresupuesto = true;
        for (Area area : sistema.getAreas()) {
            String nombre = area.getNombre();
            int presupuesto = area.getPresupuestoAnual();
            double totalSalarios = salariosPorArea.getOrDefault(nombre, 0.0);
            int cantEmpleados = empleadosPorArea.getOrDefault(nombre, 0);
            double disponible = presupuesto - totalSalarios;
            double porcentajeUsado = (totalSalarios / presupuesto) * 100;
            
            System.out.println("Área: " + nombre);
            System.out.println("  Presupuesto anual: $" + presupuesto);
            System.out.println("  Empleados: " + cantEmpleados);
            System.out.println("  Salarios anuales totales: $" + totalSalarios);
            System.out.println("  Presupuesto disponible: $" + disponible);
            System.out.println("  Porcentaje usado: " + String.format("%.2f", porcentajeUsado) + "%");
            
            if (totalSalarios > presupuesto) {
                System.out.println("  ⚠️  EXCEDE PRESUPUESTO!");
                todosDentroPresupuesto = false;
            } else {
                System.out.println("  ✓ Dentro del presupuesto");
            }
            System.out.println();
        }
        
        if (todosDentroPresupuesto) {
            System.out.println("✓ VALIDACIÓN EXITOSA: Todos los salarios están dentro del presupuesto");
        } else {
            System.out.println("✗ ERROR: Algunas áreas exceden su presupuesto");
        }
        
        // Additional validations
        System.out.println("\n=== VALIDACIONES ADICIONALES ===\n");
        
        // Check all employees have CV files
        int empleadosConCV = 0;
        for (Empleado emp : sistema.getEmpleados()) {
            if (emp.getPathCV() != null && !emp.getPathCV().isEmpty()) {
                empleadosConCV++;
            }
        }
        System.out.println("Empleados con archivo CV: " + empleadosConCV + "/" + 
                          sistema.getEmpleados().size());
        
        // Check movements are in valid months
        boolean mesesValidos = true;
        for (Movimiento mov : sistema.getMovimientos()) {
            if (mov.getMes() < 1 || mov.getMes() > 12) {
                System.out.println("✗ Movimiento con mes inválido: " + mov.getMes());
                mesesValidos = false;
            }
        }
        if (mesesValidos) {
            System.out.println("✓ Todos los movimientos tienen meses válidos (1-12)");
        }
        
        System.out.println("\n=== VALIDACIÓN COMPLETADA ===");
    }
}
