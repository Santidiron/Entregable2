//Diego Rocabado
//Santiago Dirón

import java.util.ArrayList;
<<<<<<< HEAD
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
=======
import java.util.List;
import java.util.regex.Pattern;

public class Sistema {
    private List<Manager> managers;
    private List<Empleado> empleados;
    private List<Area> areas;
    
    public Sistema() {
        this.managers = new ArrayList<>();
        this.empleados = new ArrayList<>();
        this.areas = new ArrayList<>();
        inicializarDatos();
    }
    
    private void inicializarDatos() {
        // Crear áreas de ejemplo
        Area area1 = new Area(1, "Ventas", "Departamento de Ventas", 100000, new Empleado[0]);
        Area area2 = new Area(2, "Marketing", "Departamento de Marketing", 80000, new Empleado[0]);
        Area area3 = new Area(3, "IT", "Departamento de Tecnología", 120000, new Empleado[0]);
        Area area4 = new Area(4, "RRHH", "Recursos Humanos", 90000, new Empleado[0]);
        
        areas.add(area1);
        areas.add(area2);
        areas.add(area3);
        areas.add(area4);
        
        // Crear managers de ejemplo
        Manager m1 = new Manager("Carlos Rodríguez", "1.234.567-8", "099 123 456", 15, area1, new Empleado[0]);
        Manager m2 = new Manager("Ana García", "2.345.678-9", "098 765 432", 12, area2, new Empleado[0]);
        Manager m3 = new Manager("Pedro Martínez", "3.456.789-0", "099 888 777", 8, area3, new Empleado[0]);
        
        managers.add(m1);
        managers.add(m2);
        managers.add(m3);
    }
    
    public List<Manager> getManagers() {
        return managers;
    }
    
    public List<Empleado> getEmpleados() {
        return empleados;
    }
    
    public List<Area> getAreas() {
        return areas;
    }
    
    // Validar formato de cédula (formato uruguayo: X.XXX.XXX-X)
    public boolean validarFormatoCedula(String cedula) {
        if (cedula == null || cedula.trim().isEmpty()) {
            return false;
        }
        // Patrón para cédula uruguaya: 1.234.567-8 o sin puntos 12345678
        Pattern pattern = Pattern.compile("^\\d{1}\\.\\d{3}\\.\\d{3}-\\d{1}$|^\\d{7,8}$");
        return pattern.matcher(cedula.trim()).matches();
    }
    
    // Validar que la cédula sea única en el sistema
    public boolean esCedulaUnica(String cedula) {
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
    
    // Validar que la cédula sea única excepto para un manager específico
    public boolean esCedulaUnicaExcepto(String cedula, String cedulaOriginal) {
        if (cedula.equals(cedulaOriginal)) {
            return true;
        }
        return esCedulaUnica(cedula);
    }
    
    // Validar formato de celular
    public boolean validarFormatoCelular(String celular) {
        if (celular == null || celular.trim().isEmpty()) {
            return false;
        }
        // Formato uruguayo: 09X XXX XXX o 09XXXXXXX
        Pattern pattern = Pattern.compile("^09\\d{1}\\s?\\d{3}\\s?\\d{3}$|^09\\d{7}$");
        return pattern.matcher(celular.trim()).matches();
    }
    
    // Agregar un manager
    public boolean agregarManager(Manager manager) {
        if (manager == null) {
            return false;
        }
        if (!esCedulaUnica(manager.getCedula())) {
            return false;
        }
        return managers.add(manager);
    }
    
    // Eliminar un manager (solo si no tiene empleados)
    public boolean eliminarManager(Manager manager) {
        if (manager == null) {
            return false;
        }
        // Verificar que no tenga empleados a cargo
        if (manager.getEmpleados() != null && manager.getEmpleados().length > 0) {
            return false;
        }
        return managers.remove(manager);
    }
    
    // Actualizar manager
    public boolean actualizarManager(Manager manager) {
        if (manager == null) {
            return false;
        }
        // El manager ya debe estar en la lista
        int index = managers.indexOf(manager);
        if (index >= 0) {
            managers.set(index, manager);
            return true;
        }
        return false;
    }
    
    // Obtener cantidad de empleados a cargo de un manager
    public int getCantidadEmpleadosACargo(Manager manager) {
        if (manager == null || manager.getEmpleados() == null) {
            return 0;
        }
        return manager.getEmpleados().length;
    }
    
    // Buscar manager por cédula
    public Manager buscarManagerPorCedula(String cedula) {
        for (Manager m : managers) {
            if (m.getCedula().equals(cedula)) {
                return m;
            }
        }
        return null;
>>>>>>> origin/copilot/develop-abm-for-managers
    }
}
