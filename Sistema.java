//Diego Rocabado
//Santiago Dirón

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Sistema {
    private List<Area> areas;
    private List<Empleado> empleados;
    private List<Movimiento> movimientos;
    private int nextAreaId;

    public Sistema() {
        this.areas = new ArrayList<>();
        this.empleados = new ArrayList<>();
        this.movimientos = new ArrayList<>();
        this.nextAreaId = 1;
    }

    // Area Management Methods

    /**
     * Adds a new area to the system
     * @param nombre Area name (must be unique)
     * @param descripcion Area description
     * @param presupuestoAnual Annual budget (must be > 0)
     * @return The created Area or null if validation fails
     */
    public Area agregarArea(String nombre, String descripcion, int presupuestoAnual) {
        // Validate unique name
        if (existeAreaConNombre(nombre)) {
            return null;
        }

        // Validate budget > 0
        if (presupuestoAnual <= 0) {
            return null;
        }

        Area nuevaArea = new Area(nextAreaId++, nombre, descripcion, presupuestoAnual, new Empleado[0]);
        areas.add(nuevaArea);
        return nuevaArea;
    }

    /**
     * Checks if an area with the given name already exists
     */
    public boolean existeAreaConNombre(String nombre) {
        for (Area area : areas) {
            if (area.getNombre().equalsIgnoreCase(nombre)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Removes an area from the system
     * @param area The area to remove
     * @return true if removed successfully, false if the area has employees
     */
    public boolean eliminarArea(Area area) {
        // Check if area has employees
        if (tieneEmpleados(area)) {
            return false;
        }

        return areas.remove(area);
    }

    /**
     * Checks if an area has any employees
     */
    public boolean tieneEmpleados(Area area) {
        for (Empleado empleado : empleados) {
            if (empleado.getArea() != null && empleado.getArea().getId() == area.getId()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Modifies an area (only description can be changed)
     * @param area The area to modify
     * @param nuevaDescripcion The new description
     */
    public void modificarArea(Area area, String nuevaDescripcion) {
        area.setDescripcion(nuevaDescripcion);
    }

    /**
     * Gets all areas sorted by name
     */
    public List<Area> getAreasSortedByName() {
        List<Area> sortedAreas = new ArrayList<>(areas);
        sortedAreas.sort((a1, a2) -> a1.getNombre().compareToIgnoreCase(a2.getNombre()));
        return sortedAreas;
    }

    /**
     * Finds an area by ID
     */
    public Area buscarAreaPorId(int id) {
        for (Area area : areas) {
            if (area.getId() == id) {
                return area;
            }
        }
        return null;
    }

    // Employee Movement Methods

    /**
     * Moves an employee from one area to another
     * @param empleado The employee to move
     * @param areaDestino The destination area
     * @return true if movement was successful, false if budget validation fails
     */
    public boolean moverEmpleado(Empleado empleado, Area areaDestino) {
        Area areaOrigen = empleado.getArea();

        // Validate budget in destination area
        double salarioAnual = empleado.getSalarioMensual() * 12;
        if (areaDestino.getPresupuestoAnual() < salarioAnual) {
            return false;
        }

        // Update budgets
        if (areaOrigen != null) {
            areaOrigen.setPresupuestoAnual((int)(areaOrigen.getPresupuestoAnual() + salarioAnual));
        }
        areaDestino.setPresupuestoAnual((int)(areaDestino.getPresupuestoAnual() - salarioAnual));

        // Update employee's area
        empleado.setArea(areaDestino);

        // Register movement with current month
        int mesActual = Calendar.getInstance().get(Calendar.MONTH) + 1; // Calendar.MONTH is 0-based
        Movimiento movimiento = new Movimiento(mesActual, "", empleado, areaOrigen, areaDestino);
        movimientos.add(movimiento);

        return true;
    }

    // Getters

    public List<Area> getAreas() {
        return areas;
    }

    public List<Empleado> getEmpleados() {
        return empleados;
    }

    public List<Movimiento> getMovimientos() {
        return movimientos;
    }

    /**
     * Adds an employee to the system
     */
    public void agregarEmpleado(Empleado empleado) {
        empleados.add(empleado);
    }
}
