//Diego Rocabado
//Santiago Dirón

import java.util.ArrayList;
import java.util.List;

/**
 * Clase principal del sistema que gestiona todas las entidades.
 */
public class Sistema {
    private List<Area> areas;
    private List<Manager> managers;
    private List<Empleado> empleados;
    
    public Sistema() {
        this.areas = new ArrayList<>();
        this.managers = new ArrayList<>();
        this.empleados = new ArrayList<>();
    }
    
    // Métodos para gestionar Áreas
    public void agregarArea(Area area) {
        areas.add(area);
    }
    
    public List<Area> getAreas() {
        return areas;
    }
    
    public void eliminarArea(Area area) {
        areas.remove(area);
    }
    
    // Métodos para gestionar Managers
    public void agregarManager(Manager manager) {
        managers.add(manager);
    }
    
    public List<Manager> getManagers() {
        return managers;
    }
    
    public void eliminarManager(Manager manager) {
        managers.remove(manager);
    }
    
    // Métodos para gestionar Empleados
    public void agregarEmpleado(Empleado empleado) {
        empleados.add(empleado);
    }
    
    public List<Empleado> getEmpleados() {
        return empleados;
    }
    
    public void eliminarEmpleado(Empleado empleado) {
        empleados.remove(empleado);
    }
    
    // Método para cargar datos precargados
    public void cargarDatosPrecargados() {
        // Implementación futura para cargar datos de prueba
    }
}
