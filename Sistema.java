//Diego Rocabado
//Santiago Dirón

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Sistema implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private List<Area> areas;
    private List<Manager> managers;
    private List<Empleado> empleados;
    private List<Movimiento> movimientos;

    public Sistema() {
        this.areas = new ArrayList<>();
        this.managers = new ArrayList<>();
        this.empleados = new ArrayList<>();
        this.movimientos = new ArrayList<>();
    }

    // Getters
    public List<Area> getAreas() {
        return areas;
    }

    public List<Manager> getManagers() {
        return managers;
    }

    public List<Empleado> getEmpleados() {
        return empleados;
    }

    public List<Movimiento> getMovimientos() {
        return movimientos;
    }

    // Setters
    public void setAreas(List<Area> areas) {
        this.areas = areas;
    }

    public void setManagers(List<Manager> managers) {
        this.managers = managers;
    }

    public void setEmpleados(List<Empleado> empleados) {
        this.empleados = empleados;
    }

    public void setMovimientos(List<Movimiento> movimientos) {
        this.movimientos = movimientos;
    }

    // Business methods
    public void agregarArea(Area area) {
        if (area != null) {
            this.areas.add(area);
        }
    }

    public void agregarManager(Manager manager) {
        if (manager != null) {
            this.managers.add(manager);
        }
    }

    public void registrarEmpleado(Empleado empleado) {
        if (empleado != null) {
            this.empleados.add(empleado);
        }
    }

    public void registrarMovimiento(Movimiento movimiento) {
        if (movimiento != null) {
            this.movimientos.add(movimiento);
        }
    }
}
