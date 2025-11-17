//Diego Rocabado
//Santiago Dirón

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Pattern;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Clase principal del sistema que gestiona todas las entidades.
 */
public class Sistema {
    private ArrayList<Area> areas;
    private ArrayList<Manager> managers;
    private ArrayList<Empleado> empleados;
    private ArrayList<Movimiento> movimientos;
    private int nextAreaId;
    private int nextLegajo;

    public Sistema() {
        this.areas = new ArrayList<>();
        this.managers = new ArrayList<>();
        this.empleados = new ArrayList<>();
        this.movimientos = new ArrayList<>();
        this.nextAreaId = 1;
        this.nextLegajo = 1000;
    }

    // Getters como List (para compatibilidad con código que use List)
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

    // Métodos para gestionar Áreas (API simple)
    public void agregarArea(Area area) {
        areas.add(area);
    }

    public void eliminarArea(Area area) {
        areas.remove(area);
    }

    // Métodos para gestionar Managers
    public void agregarManager(Manager manager) {
        managers.add(manager);
    }

    public void eliminarManager(Manager manager) {
        managers.remove(manager);
    }

    // Métodos para gestionar Empleados
    public void agregarEmpleado(Empleado empleado) {
        empleados.add(empleado);
    }

    public void eliminarEmpleado(Empleado empleado) {
        empleados.remove(empleado);
    }

    // Check if system has data
    public boolean tieneDatos() {
        return !areas.isEmpty() || !managers.isEmpty() || !empleados.isEmpty() || !movimientos.isEmpty();
    }

    // ==== Lógica de validación y reglas de negocio (resumida) ====

    // Validar formato de cédula (formato uruguayo aproximado)
    public boolean validarFormatoCedula(String cedula) {
        if (cedula == null || cedula.trim().isEmpty()) {
            return false;
        }
        Pattern pattern = Pattern.compile("^\\d{1}\\.\\d{3}\\.\\d{3}-\\d{1}$|^\\d{7,8}$");
        return pattern.matcher(cedula.trim()).matches();
    }

    // Validar que la cédula sea única en el sistema
    public boolean esCedulaUnica(String cedula) {
        if (cedula == null) {
            return false;
        }
        for (Manager m : managers) {
            if (cedula.equals(m.getCedula())) {
                return false;
            }
        }
        for (Empleado e : empleados) {
            if (cedula.equals(e.getCedula())) {
                return false;
            }
        }
        return true;
    }

    // Métodos de ayuda que se intuyen desde el archivo original (presupuestos, existencia, etc.)
    private boolean existeManager(Manager manager) {
        return managers.contains(manager);
    }

    private boolean existeArea(Area area) {
        return areas.contains(area);
    }

    private boolean areaContieneEmpleados(Area area) {
        for (Empleado e : empleados) {
            if (area.equals(e.getArea())) {
                return true;
            }
        }
        return false;
    }

    private boolean managerTieneEmpleados(Manager manager) {
        for (Empleado e : empleados) {
            if (manager.equals(e.getManager())) {
                return true;
            }
        }
        return false;
    }

    private boolean hayPresupuestoDisponible(Area area, double salarioMensual) {
        if (area == null) {
            return false;
        }
        double totalActual = 0;
        for (Empleado e : empleados) {
            if (area.equals(e.getArea())) {
                totalActual += e.getSalarioMensual();
            }
        }
        return totalActual + salarioMensual <= area.getPresupuesto();
    }

    // ==== ALTAS (CREATE) SIMPLIFICADAS, usando las validaciones ====

    public boolean altaArea(Area area) {
        if (area == null) return false;
        for (Area a : areas) {
            if (a.getNombre().equals(area.getNombre())) {
                return false;
            }
        }
        areas.add(area);
        return true;
    }

    public boolean altaManager(Manager manager) {
        if (manager == null) return false;
        if (!esCedulaUnica(manager.getCedula())) return false;
        managers.add(manager);
        return true;
    }

    public boolean altaEmpleado(Empleado empleado) {
        if (empleado == null) return false;
        if (!esCedulaUnica(empleado.getCedula())) return false;
        if (empleado.getManager() != null && !existeManager(empleado.getManager())) return false;
        if (empleado.getArea() != null && !existeArea(empleado.getArea())) return false;
        if (empleado.getArea() != null && !hayPresupuestoDisponible(empleado.getArea(), empleado.getSalarioMensual())) return false;
        empleados.add(empleado);
        return true;
    }

    // ==== BAJAS (DELETE) SIMPLIFICADAS ====

    public boolean bajaArea(Area area) {
        if (area == null || !areas.contains(area)) return false;
        if (areaContieneEmpleados(area)) return false;
        areas.remove(area);
        return true;
    }

    public boolean bajaManager(Manager manager) {
        if (manager == null || !managers.contains(manager)) return false;
        if (managerTieneEmpleados(manager)) return false;
        managers.remove(manager);
        return true;
    }

    public boolean bajaEmpleado(Empleado empleado) {
        if (empleado == null || !empleados.contains(empleado)) return false;
        empleados.remove(empleado);
        return true;
    }

    // ==== Datos precargados y CVs (de la implementación anterior) ====

    public void cargarDatosPrecargados() {
        // Create cvs directory if it doesn't exist
        File cvsDir = new File("cvs");
        if (!cvsDir.exists()) {
            cvsDir.mkdir();
        }

        // 1. Create Areas
        Area areaPersonal = new Area(nextAreaId++, "Personal", "Gestión de recursos humanos", 500000, new Empleado[0]);
        Area areaRRHH = new Area(nextAreaId++, "RRHH", "Reclutamiento y desarrollo", 450000, new Empleado[0]);
        Area areaSeguridad = new Area(nextAreaId++, "Seguridad", "Seguridad física y digital", 350000, new Empleado[0]);
        Area areaComunicaciones = new Area(nextAreaId++, "Comunicaciones", "Comunicación interna y externa", 400000, new Empleado[0]);
        Area areaMarketing = new Area(nextAreaId++, "Marketing", "Estrategias de mercado", 550000, new Empleado[0]);

        agregarArea(areaPersonal);
        agregarArea(areaRRHH);
        agregarArea(areaSeguridad);
        agregarArea(areaComunicaciones);
        agregarArea(areaMarketing);

        // 2. Create Managers
        Manager managerAnaMartinez = new Manager("Ana Martínez", "12345678", "099123456", 10, areaPersonal, new Empleado[0]);
        Manager managerRicardoMorales = new Manager("Ricardo Morales", "23456789", "099234567", 8, areaRRHH, new Empleado[0]);
        Manager managerLauraTorales = new Manager("Laura Torales", "34567890", "099345678", 12, areaSeguridad, new Empleado[0]);
        Manager managerJuanPabloZapata = new Manager("Juan Pablo Zapata", "45678901", "099456789", 6, areaMarketing, new Empleado[0]);

        agregarManager(managerAnaMartinez);
        agregarManager(managerRicardoMorales);
        agregarManager(managerLauraTorales);
        agregarManager(managerJuanPabloZapata);

        // 3. Create Employees for each area
        // Area Personal - 2 employees
        crearEmpleadoConCV("Carlos", "González", "11111111", "099111111", 5, 18000,
                          managerAnaMartinez, areaPersonal);
        crearEmpleadoConCV("María", "Rodríguez", "11111112", "099111112", 3, 16000,
                          managerAnaMartinez, areaPersonal);

        // Area RRHH - 3 employees
        crearEmpleadoConCV("Pedro", "Sánchez", "22222221", "099222221", 7, 11000,
                          managerRicardoMorales, areaRRHH);
        crearEmpleadoConCV("Lucía", "Fernández", "22222222", "099222222", 4, 10500,
                          managerRicardoMorales, areaRRHH);
        crearEmpleadoConCV("Diego", "Martín", "22222223", "099222223", 2, 9500,
                          managerRicardoMorales, areaRRHH);

        // Area Seguridad - 2 employees
        crearEmpleadoConCV("Roberto", "López", "33333331", "099333331", 9, 13500,
                          managerLauraTorales, areaSeguridad);
        crearEmpleadoConCV("Andrea", "García", "33333332", "099333332", 6, 12000,
                          managerLauraTorales, areaSeguridad);

        // Area Comunicaciones - 2 employees
        crearEmpleadoConCV("Sofía", "Pérez", "44444441", "099444441", 4, 15000,
                          managerAnaMartinez, areaComunicaciones);
        crearEmpleadoConCV("Javier", "Ramírez", "44444442", "099444442", 5, 14500,
                          managerAnaMartinez, areaComunicaciones);

        // Area Marketing - 3 employees
        crearEmpleadoConCV("Valentina", "Torres", "55555551", "099555551", 8, 14000,
                          managerJuanPabloZapata, areaMarketing);
        crearEmpleadoConCV("Mateo", "Flores", "55555552", "099555552", 3, 13000,
                          managerJuanPabloZapata, areaMarketing);
        crearEmpleadoConCV("Isabella", "Vega", "55555553", "099555553", 5, 13500,
                          managerJuanPabloZapata, areaMarketing);

        // 4. Create historical movements
        if (empleados.size() >= 5) {
            Empleado emp1 = empleados.get(0);
            Empleado emp2 = empleados.get(2);
            Empleado emp3 = empleados.get(4);
            Empleado emp4 = empleados.get(6);
            Empleado emp5 = empleados.get(8);

            Movimiento mov1 = new Movimiento(1, "2024-01-15", emp1, areaPersonal, areaRRHH);
            Movimiento mov2 = new Movimiento(3, "2024-03-20", emp2, areaRRHH, areaSeguridad);
            Movimiento mov3 = new Movimiento(5, "2024-05-10", emp3, areaSeguridad, areaMarketing);
            Movimiento mov4 = new Movimiento(7, "2024-07-25", emp4, areaComunicaciones, areaPersonal);
            Movimiento mov5 = new Movimiento(9, "2024-09-18", emp5, areaMarketing, areaComunicaciones);

            movimientos.add(mov1);
            movimientos.add(mov2);
            movimientos.add(mov3);
            movimientos.add(mov4);
            movimientos.add(mov5);
        }
    }

    // Helper method to create employee with CV file
    private void crearEmpleadoConCV(String nombre, String apellido, String cedula, String celular,
                                   int antiguedad, double salario, Manager manager, Area area) {
        String pathCV = "cvs/cv_" + nombre.toLowerCase() + "_" + apellido.toLowerCase() + ".txt";

        // Create CV file
        crearArchivoCV(pathCV, nombre, apellido, cedula, celular, antiguedad);

        // Create employee
        Empleado empleado = new Empleado(nextLegajo++, nombre, apellido, cedula, celular,
                                        pathCV, antiguedad, salario, manager, area);
        agregarEmpleado(empleado);
    }

    // Helper method to create CV file
    private void crearArchivoCV(String pathCV, String nombre, String apellido,
                               String cedula, String celular, int antiguedad) {
        try {
            File cvFile = new File(pathCV);
            FileWriter writer = new FileWriter(cvFile);

            writer.write("CURRICULUM VITAE\n");
            writer.write("================\n\n");
            writer.write("Nombre: " + nombre + " " + apellido + "\n");
            writer.write("Cédula: " + cedula + "\n");
            writer.write("Celular: " + celular + "\n");
            writer.write("Antigüedad: " + antiguedad + " años\n\n");
            writer.write("EXPERIENCIA PROFESIONAL:\n");
            writer.write("- " + antiguedad + " años de experiencia en el sector\n");
            writer.write("- Habilidades de trabajo en equipo\n");
            writer.write("- Conocimientos avanzados de herramientas ofimáticas\n\n");
            writer.write("EDUCACIÓN:\n");
            writer.write("- Licenciatura en Administración de Empresas\n");
            writer.write("- Cursos de especialización profesional\n\n");
            writer.write("REFERENCIAS:\n");
            writer.write("Disponibles a solicitud\n");

            writer.close();
        } catch (IOException e) {
            System.err.println("Error al crear archivo CV: " + pathCV);
            e.printStackTrace();
        }
    }
}
