//Diego Rocabado
//Santiago Dirón

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Sistema {
    private ArrayList<Area> areas;
    private ArrayList<Manager> managers;
    private ArrayList<Empleado> empleados;
    
    public Sistema() {
        this.areas = new ArrayList<>();
        this.managers = new ArrayList<>();
        this.empleados = new ArrayList<>();
    }
    
    // ========== ALTA (CREATE) METHODS ==========
    
    /**
     * Da de alta un área en el sistema
     * @param area El área a agregar
     * @return true si se agregó correctamente, false si ya existe un área con ese nombre
     */
    public boolean altaArea(Area area) {
        if (area == null) {
            return false;
        }
        // Verificar que no exista un área con el mismo nombre (nombre único)
        for (Area a : areas) {
            if (a.getNombre().equals(area.getNombre())) {
                return false;
            }
        }
        areas.add(area);
        return true;
    }
    
    /**
     * Da de alta un manager en el sistema
     * @param manager El manager a agregar
     * @return true si se agregó correctamente, false si la cédula ya existe
     */
    public boolean altaManager(Manager manager) {
        if (manager == null) {
            return false;
        }
        // Validar cédula única en todo el sistema (managers y empleados)
        if (!esCedulaUnica(manager.getCedula())) {
            return false;
        }
        managers.add(manager);
        return true;
    }
    
    /**
     * Da de alta un empleado en el sistema
     * @param empleado El empleado a agregar
     * @return true si se agregó correctamente, false si falla alguna validación
     */
    public boolean altaEmpleado(Empleado empleado) {
        if (empleado == null) {
            return false;
        }
        
        // Validar cédula única en todo el sistema (managers y empleados)
        if (!esCedulaUnica(empleado.getCedula())) {
            return false;
        }
        
        // Validar que el manager exista si está asignado
        if (empleado.getManager() != null && !existeManager(empleado.getManager())) {
            return false;
        }
        
        // Validar que el área exista si está asignada
        if (empleado.getArea() != null && !existeArea(empleado.getArea())) {
            return false;
        }
        
        // Validar presupuesto disponible en el área
        if (empleado.getArea() != null && !hayPresupuestoDisponible(empleado.getArea(), empleado.getSalarioMensual())) {
            return false;
        }
        
        empleados.add(empleado);
        return true;
    }
    
    // ========== BAJA (DELETE) METHODS ==========
    
    /**
     * Da de baja un área del sistema
     * @param area El área a eliminar
     * @return true si se eliminó correctamente, false si tiene empleados o no existe
     */
    public boolean bajaArea(Area area) {
        if (area == null || !areas.contains(area)) {
            return false;
        }
        
        // Solo permitir baja de áreas sin empleados
        if (areaContieneEmpleados(area)) {
            return false;
        }
        
        areas.remove(area);
        return true;
    }
    
    /**
     * Da de baja un manager del sistema
     * @param manager El manager a eliminar
     * @return true si se eliminó correctamente, false si tiene empleados a cargo o no existe
     */
    public boolean bajaManager(Manager manager) {
        if (manager == null || !managers.contains(manager)) {
            return false;
        }
        
        // Solo permitir baja de managers sin empleados a cargo
        if (managerTieneEmpleados(manager)) {
            return false;
        }
        
        managers.remove(manager);
        return true;
    }
    
    /**
     * Da de baja un empleado del sistema
     * @param empleado El empleado a eliminar
     * @return true si se eliminó correctamente, false si no existe
     */
    public boolean bajaEmpleado(Empleado empleado) {
        if (empleado == null || !empleados.contains(empleado)) {
            return false;
        }
        
        empleados.remove(empleado);
        return true;
    }
    
    // ========== MODIFICACIÓN (UPDATE) METHODS ==========
    
    /**
     * Modifica los datos de un área
     * @param areaVieja El área a modificar
     * @param areaNueva Los nuevos datos del área
     * @return true si se modificó correctamente, false si falla alguna validación
     */
    public boolean modificarArea(Area areaVieja, Area areaNueva) {
        if (areaVieja == null || areaNueva == null || !areas.contains(areaVieja)) {
            return false;
        }
        
        // Si se cambia el nombre, verificar que el nuevo nombre sea único
        if (!areaVieja.getNombre().equals(areaNueva.getNombre())) {
            for (Area a : areas) {
                if (a != areaVieja && a.getNombre().equals(areaNueva.getNombre())) {
                    return false;
                }
            }
        }
        
        int index = areas.indexOf(areaVieja);
        areas.set(index, areaNueva);
        return true;
    }
    
    /**
     * Modifica los datos de un manager
     * @param managerViejo El manager a modificar
     * @param managerNuevo Los nuevos datos del manager
     * @return true si se modificó correctamente, false si falla alguna validación
     */
    public boolean modificarManager(Manager managerViejo, Manager managerNuevo) {
        if (managerViejo == null || managerNuevo == null || !managers.contains(managerViejo)) {
            return false;
        }
        
        // Si se cambia la cédula, verificar que la nueva cédula sea única
        if (!managerViejo.getCedula().equals(managerNuevo.getCedula())) {
            if (!esCedulaUnica(managerNuevo.getCedula())) {
                return false;
            }
        }
        
        int index = managers.indexOf(managerViejo);
        managers.set(index, managerNuevo);
        return true;
    }
    
    /**
     * Modifica los datos de un empleado
     * @param empleadoViejo El empleado a modificar
     * @param empleadoNuevo Los nuevos datos del empleado
     * @return true si se modificó correctamente, false si falla alguna validación
     */
    public boolean modificarEmpleado(Empleado empleadoViejo, Empleado empleadoNuevo) {
        if (empleadoViejo == null || empleadoNuevo == null || !empleados.contains(empleadoViejo)) {
            return false;
        }
        
        // Si se cambia la cédula, verificar que la nueva cédula sea única
        if (!empleadoViejo.getCedula().equals(empleadoNuevo.getCedula())) {
            if (!esCedulaUnica(empleadoNuevo.getCedula())) {
                return false;
            }
        }
        
        // Validar que el nuevo manager exista si está asignado
        if (empleadoNuevo.getManager() != null && !existeManager(empleadoNuevo.getManager())) {
            return false;
        }
        
        // Validar que el nueva área exista si está asignada
        if (empleadoNuevo.getArea() != null && !existeArea(empleadoNuevo.getArea())) {
            return false;
        }
        
        int index = empleados.indexOf(empleadoViejo);
        empleados.set(index, empleadoNuevo);
        return true;
    }
    
    // ========== MOVIMIENTO DE EMPLEADOS ==========
    
    /**
     * Realiza un movimiento de empleado entre áreas
     * @param empleado El empleado a mover
     * @param areaDestino El área de destino
     * @return true si el movimiento se realizó correctamente, false si falla alguna validación
     */
    public boolean moverEmpleado(Empleado empleado, Area areaDestino) {
        if (empleado == null || areaDestino == null) {
            return false;
        }
        
        // Verificar que el empleado existe en el sistema
        if (!empleados.contains(empleado)) {
            return false;
        }
        
        // Verificar que el área de destino existe en el sistema
        if (!areas.contains(areaDestino)) {
            return false;
        }
        
        // Si el empleado ya está en el área de destino, no hay nada que hacer
        if (empleado.getArea() != null && empleado.getArea().equals(areaDestino)) {
            return true;
        }
        
        // Verificar presupuesto disponible en el área de destino
        if (!hayPresupuestoDisponible(areaDestino, empleado.getSalarioMensual())) {
            return false;
        }
        
        // Actualizar el área del empleado
        empleado.setArea(areaDestino);
        return true;
    }
    
    // ========== VALIDACIONES Y MÉTODOS AUXILIARES ==========
    
    /**
     * Verifica si una cédula es única en el sistema (no existe en managers ni empleados)
     * @param cedula La cédula a verificar
     * @return true si la cédula es única, false si ya existe
     */
    private boolean esCedulaUnica(String cedula) {
        if (cedula == null) {
            return false;
        }
        
        // Verificar en managers
        for (Manager m : managers) {
            if (m.getCedula().equals(cedula)) {
                return false;
            }
        }
        
        // Verificar en empleados
        for (Empleado e : empleados) {
            if (e.getCedula().equals(cedula)) {
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * Verifica si un manager existe en el sistema
     * @param manager El manager a verificar
     * @return true si el manager existe, false en caso contrario
     */
    private boolean existeManager(Manager manager) {
        return managers.contains(manager);
    }
    
    /**
     * Verifica si un área existe en el sistema
     * @param area El área a verificar
     * @return true si el área existe, false en caso contrario
     */
    private boolean existeArea(Area area) {
        return areas.contains(area);
    }
    
    /**
     * Verifica si hay presupuesto disponible en un área para agregar un empleado
     * @param area El área a verificar
     * @param salarioMensual El salario mensual del empleado a agregar
     * @return true si hay presupuesto disponible, false en caso contrario
     */
    private boolean hayPresupuestoDisponible(Area area, double salarioMensual) {
        double salarioAnual = salarioMensual * 12;
        double presupuestoUsado = 0;
        
        // Calcular el presupuesto ya usado por empleados en el área
        for (Empleado e : empleados) {
            if (e.getArea() != null && e.getArea().equals(area)) {
                presupuestoUsado += e.getSalarioMensual() * 12;
            }
        }
        
        // Verificar si hay suficiente presupuesto disponible
        return (presupuestoUsado + salarioAnual) <= area.getPresupuestoAnual();
    }
    
    /**
     * Verifica si un manager tiene empleados a cargo
     * @param manager El manager a verificar
     * @return true si tiene empleados a cargo, false en caso contrario
     */
    public boolean managerTieneEmpleados(Manager manager) {
        if (manager == null) {
            return false;
        }
        
        for (Empleado e : empleados) {
            if (e.getManager() != null && e.getManager().equals(manager)) {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * Verifica si un área contiene empleados
     * @param area El área a verificar
     * @return true si contiene empleados, false en caso contrario
     */
    public boolean areaContieneEmpleados(Area area) {
        if (area == null) {
            return false;
        }
        
        for (Empleado e : empleados) {
            if (e.getArea() != null && e.getArea().equals(area)) {
                return true;
            }
        }
        
        return false;
    }
    
    // ========== ORDENAMIENTO ==========
    
    /**
     * Ordena las áreas por nombre (orden alfabético)
     * @return Una lista de áreas ordenadas por nombre
     */
    public ArrayList<Area> ordenarAreasPorNombre() {
        ArrayList<Area> areasOrdenadas = new ArrayList<>(areas);
        Collections.sort(areasOrdenadas, new Comparator<Area>() {
            @Override
            public int compare(Area a1, Area a2) {
                return a1.getNombre().compareTo(a2.getNombre());
            }
        });
        return areasOrdenadas;
    }
    
    /**
     * Ordena los managers por antigüedad (de mayor a menor)
     * @return Una lista de managers ordenados por antigüedad
     */
    public ArrayList<Manager> ordenarManagersPorAntiguedad() {
        ArrayList<Manager> managersOrdenados = new ArrayList<>(managers);
        Collections.sort(managersOrdenados, new Comparator<Manager>() {
            @Override
            public int compare(Manager m1, Manager m2) {
                // Orden descendente (mayor antigüedad primero)
                return Integer.compare(m2.getAntiguedad(), m1.getAntiguedad());
            }
        });
        return managersOrdenados;
    }
    
    // ========== GETTERS ==========
    
    public ArrayList<Area> getAreas() {
        return areas;
    }
    
    public ArrayList<Manager> getManagers() {
        return managers;
    }
    
    public ArrayList<Empleado> getEmpleados() {
        return empleados;
    }
}
