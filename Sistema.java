//Diego Rocabado
//Santiago Dirón

import java.util.ArrayList;
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

    // Getters
    public ArrayList<Area> getAreas() {
        return areas;
    }

    public ArrayList<Manager> getManagers() {
        return managers;
    }

    public ArrayList<Empleado> getEmpleados() {
        return empleados;
    }

    public ArrayList<Movimiento> getMovimientos() {
        return movimientos;
    }

    // Métodos para gestionar Áreas
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

    // Main method to load preloaded data
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

        // Area Comunicaciones - 2 employees (no manager assigned, so using first manager)
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
