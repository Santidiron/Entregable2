//Diego Rocabado
//Santiago Dirón

import java.util.*;

public class Sistema {
    private List<Empleado> empleados;
    private List<Manager> managers;
    private List<Area> areas;
    private int siguienteLegajo;
    private GestorCV gestorCV;

    public Sistema() {
        this.empleados = new ArrayList<>();
        this.managers = new ArrayList<>();
        this.areas = new ArrayList<>();
        this.siguienteLegajo = 1000; // Iniciar legajos desde 1000
        this.gestorCV = new GestorCV();
    }

    /**
     * Genera el siguiente número de legajo automáticamente
     */
    public int generarLegajo() {
        return siguienteLegajo++;
    }

    /**
     * Valida que una cédula sea única en el sistema (no existe en empleados ni managers)
     */
    public boolean validarCedulaUnica(String cedula) {
        if (cedula == null || cedula.isEmpty()) {
            return false;
        }

        // Verificar en empleados
        for (Empleado emp : empleados) {
            if (emp.getCedula().equals(cedula)) {
                return false;
            }
        }

        // Verificar en managers
        for (Manager mgr : managers) {
            if (mgr.getCedula().equals(cedula)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Valida que el área tenga presupuesto suficiente para el salario del empleado
     */
    public boolean validarPresupuestoArea(Area area, double salarioMensual) {
        if (area == null) {
            return false;
        }

        // Calcular presupuesto anual del nuevo empleado
        double salarioAnual = salarioMensual * 12;

        // Calcular salarios actuales del área
        double salariosActuales = 0;
        for (Empleado emp : empleados) {
            if (emp.getArea() != null && emp.getArea().getId() == area.getId()) {
                salariosActuales += emp.getSalarioMensual() * 12;
            }
        }

        // Verificar si hay presupuesto suficiente
        return (salariosActuales + salarioAnual) <= area.getPresupuestoAnual();
    }

    /**
     * Crea un nuevo empleado y lo agrega al sistema
     */
    public Empleado crearEmpleado(String nombre, String apellido, String cedula, String celular,
                                   String archivoCV, int antiguedad, double salarioMensual,
                                   Manager manager, Area area) {
        // Validaciones
        if (nombre == null || nombre.isEmpty() || apellido == null || apellido.isEmpty() ||
            cedula == null || cedula.isEmpty()) {
            throw new IllegalArgumentException("Nombre, apellido y cédula son obligatorios");
        }

        if (!validarCedulaUnica(cedula)) {
            throw new IllegalArgumentException("La cédula ya existe en el sistema");
        }

        if (manager == null || area == null) {
            throw new IllegalArgumentException("Manager y área son obligatorios");
        }

        if (!validarPresupuestoArea(area, salarioMensual)) {
            throw new IllegalArgumentException("El área no tiene presupuesto suficiente para este salario");
        }

        // Guardar CV
        String pathCV = null;
        if (archivoCV != null && !archivoCV.isEmpty()) {
            pathCV = gestorCV.guardarCV(archivoCV, cedula, nombre, apellido);
            if (pathCV == null) {
                throw new IllegalArgumentException("Error al guardar el archivo CV");
            }
        }

        // Crear empleado
        int legajo = generarLegajo();
        Empleado empleado = new Empleado(legajo, nombre, apellido, cedula, celular, pathCV,
                                         antiguedad, salarioMensual, manager, area);

        empleados.add(empleado);
        return empleado;
    }

    /**
     * Actualiza los datos de un empleado (solo celular y salario)
     */
    public void actualizarEmpleado(Empleado empleado, String celular, double salarioMensual) {
        if (empleado == null) {
            throw new IllegalArgumentException("Empleado no puede ser null");
        }

        // Validar presupuesto si se cambia el salario
        if (salarioMensual != empleado.getSalarioMensual()) {
            // Calcular diferencia de presupuesto
            double diferenciaSalario = salarioMensual - empleado.getSalarioMensual();
            double diferenciaAnual = diferenciaSalario * 12;

            Area area = empleado.getArea();
            double salariosActuales = 0;
            for (Empleado emp : empleados) {
                if (emp.getArea() != null && emp.getArea().getId() == area.getId()) {
                    salariosActuales += emp.getSalarioMensual() * 12;
                }
            }

            if ((salariosActuales + diferenciaAnual) > area.getPresupuestoAnual()) {
                throw new IllegalArgumentException("El área no tiene presupuesto suficiente para este salario");
            }

            empleado.setSalarioMensual(salarioMensual);
        }

        if (celular != null) {
            empleado.setCelular(celular);
        }
    }

    /**
     * Obtiene todos los empleados ordenados por nombre
     */
    public List<Empleado> obtenerEmpleadosOrdenados() {
        List<Empleado> ordenados = new ArrayList<>(empleados);
        ordenados.sort((e1, e2) -> e1.getNombre().compareToIgnoreCase(e2.getNombre()));
        return ordenados;
    }

    /**
     * Busca un empleado por legajo
     */
    public Empleado buscarEmpleadoPorLegajo(int legajo) {
        for (Empleado emp : empleados) {
            if (emp.getLegajo() == legajo) {
                return emp;
            }
        }
        return null;
    }

    /**
     * Lee el CV de un empleado
     */
    public String leerCVEmpleado(Empleado empleado) {
        if (empleado == null || empleado.getPathCV() == null) {
            return null;
        }
        return gestorCV.leerCV(empleado.getPathCV());
    }

    // Getters y métodos para gestionar managers y áreas
    public List<Empleado> getEmpleados() {
        return empleados;
    }

    public List<Manager> getManagers() {
        return managers;
    }

    public List<Area> getAreas() {
        return areas;
    }

    public void agregarManager(Manager manager) {
        if (manager != null) {
            managers.add(manager);
        }
    }

    public void agregarArea(Area area) {
        if (area != null) {
            areas.add(area);
        }
    }

    public GestorCV getGestorCV() {
        return gestorCV;
    }
}
