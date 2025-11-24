//Diego Rocabado - Número de estudiante: 305310
//Santiago Dirón - Número de estudiante: 359644

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Pattern;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

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

    // Cambiamos a boolean para compatibilidad con tests y demos
    public boolean eliminarArea(Area area) {
        return bajaArea(area);
    }

    // Métodos para gestionar Managers
    // Cambiamos a boolean para compatibilidad con tests y GUI
    public boolean agregarManager(Manager manager) {
        return altaManager(manager);
    }

    // Cambiamos a boolean y delegamos en bajaManager
    public boolean eliminarManager(Manager manager) {
        return bajaManager(manager);
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

    public boolean existeAreaConNombre(String nombre) {
        for (Area area : areas) {
            if (area.getNombre().equalsIgnoreCase(nombre)) {
                return true;
            }
        }
        return false;
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

        // 1. Create Areas with EXACT budgets from requirements
        Area areaPersonal = new Area(nextAreaId++, "Personal", "Reclutamiento de personal, promociones, gestión de cargos", 100000, new Empleado[0]);
        Area areaRRHH = new Area(nextAreaId++, "RRHH", "Relacionamiento en la empresa, organigrama, gestión de equipos", 80000, new Empleado[0]);
        Area areaSeguridad = new Area(nextAreaId++, "Seguridad", "Seguridad física, vigilancia, seguridad informática, protocolos y políticas de seguridad", 120000, new Empleado[0]);
        Area areaComunicaciones = new Area(nextAreaId++, "Comunicaciones", "Comunicaciones internas, reglas y protocolos, comunicaciones con proveedores y clientes", 20000, new Empleado[0]);
        Area areaMarketing = new Area(nextAreaId++, "Marketing", "Acciones planificadas, publicidad en medios masivos, publicidad en redes, gestión de redes", 95000, new Empleado[0]);

        agregarArea(areaPersonal);
        agregarArea(areaRRHH);
        agregarArea(areaSeguridad);
        agregarArea(areaComunicaciones);
        agregarArea(areaMarketing);

        // 2. Create Managers with EXACT data from requirements
        Manager managerAnaMartinez = new Manager("Ana Martínez", "4.568.369-1", "099123456", 10, areaPersonal, new Empleado[0]);
        Manager managerRicardoMorales = new Manager("Ricardo Morales", "3.214.589-3", "094121212", 4, areaRRHH, new Empleado[0]);
        Manager managerLauraTorales = new Manager("Laura Torales", "3.589.257-5", "099654321", 1, areaSeguridad, new Empleado[0]);
        Manager managerJuanPabloZapata = new Manager("Juan Pablo Zapata", "4.555.197-7", "099202020", 5, areaMarketing, new Empleado[0]);

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

    // ==== API de Áreas requerida por tests y GUI ====

    // Sobrecarga: crear y agregar área devolviendo el objeto
    public Area agregarArea(String nombre, String descripcion, int presupuestoAnual) {
        if (nombre == null || nombre.trim().isEmpty()) return null;
        if (presupuestoAnual <= 0) return null;
        // nombre único
        for (Area a : areas) {
            if (a.getNombre().equalsIgnoreCase(nombre.trim())) {
                return null;
            }
        }
        Area area = new Area(nextAreaId++, nombre.trim(), descripcion, presupuestoAnual, new Empleado[0]);
        areas.add(area);
        return area;
    }

    // Modificar descripción de un área existente
    public void modificarDescripcionArea(Area area, String nuevaDescripcion) {
        if (area == null || nuevaDescripcion == null) return;
        area.setDescripcion(nuevaDescripcion);
    }

    public void modificarArea(Area area, String nuevaDescripcion) {
        modificarDescripcionArea(area, nuevaDescripcion);
    }


    // Áreas ordenadas alfabéticamente por nombre
    public List<Area> getAreasSortedByName() {
        List<Area> copia = new ArrayList<>(areas);
        copia.sort(Comparator.comparing(a -> a.getNombre().toLowerCase()));
        return copia;
    }

    // Buscar área por id
    public Area buscarAreaPorId(int id) {
        for (Area a : areas) {
            if (a.getId() == id) return a;
        }
        return null;
    }

    // ==== API de validaciones usada en tests de empleados ====

    public boolean validarCedulaUnica(String cedula) {
        return esCedulaUnica(cedula);
    }

    public boolean validarPresupuestoArea(Area area, int salarioMensual) {
        if (area == null) return false;
        double totalActual = 0;
        for (Empleado e : empleados) {
            if (area.equals(e.getArea())) {
                totalActual += e.getSalarioMensual();
            }
        }
        double disponible = area.getPresupuesto() - totalActual * 12; // presupuestos anuales
        return salarioMensual * 12 <= disponible;
    }

    public boolean validarFormatoCelular(String celular) {
        if (celular == null) return false;
        String c = celular.replace(" ", "");
        return c.matches("^09\\d{7}$");
    }

    // ==== API de empleados ====

    public int generarLegajo() {
        return nextLegajo++;
    }

    public Empleado crearEmpleado(String nombre, String apellido, String cedula, String celular,
                                  String pathCV, int antiguedad, double salarioMensual,
                                  Manager manager, Area area) {
        if (!validarFormatoCedula(cedula)) {
            throw new IllegalArgumentException("Cédula inválida");
        }
        if (!esCedulaUnica(cedula)) {
            throw new IllegalArgumentException("Cédula duplicada");
        }
        if (!validarFormatoCelular(celular)) {
            throw new IllegalArgumentException("Celular inválido");
        }
        if (area != null && !validarPresupuestoArea(area, (int) salarioMensual)) {
            throw new IllegalArgumentException("Salario excede presupuesto del área");
        }
        int legajo = generarLegajo();
        Empleado empleado = new Empleado(legajo, nombre, apellido, cedula, celular, pathCV,
                                         antiguedad, salarioMensual, manager, area);
        empleados.add(empleado);
        return empleado;
    }

    public String leerCVEmpleado(Empleado empleado) {
        if (empleado == null || empleado.getPathCV() == null) return null;
        try {
            return Files.readString(Paths.get(empleado.getPathCV()));
        } catch (IOException e) {
            System.err.println("Error leyendo CV de empleado: " + e.getMessage());
            return null;
        }
    }

    public Empleado buscarEmpleadoPorLegajo(int legajo) {
        for (Empleado e : empleados) {
            if (e.getLegajo() == legajo) return e;
        }
        return null;
    }

    public void actualizarEmpleado(Empleado empleado, String nuevoCelular, double nuevoSalario) {
        if (empleado == null) return;
        if (!validarFormatoCelular(nuevoCelular)) {
            throw new IllegalArgumentException("Celular inválido");
        }
        empleado.setCelular(nuevoCelular);
        empleado.setSalarioMensual(nuevoSalario);
    }

    public List<Empleado> obtenerEmpleadosOrdenados() {
        List<Empleado> copia = new ArrayList<>(empleados);
        copia.sort(Comparator.comparing(Empleado::getNombre)
                              .thenComparing(Empleado::getApellido));
        return copia;
    }

    public void registrarEmpleado(Empleado empleado) {
        if (empleado != null && !empleados.contains(empleado)) {
            empleados.add(empleado);
        }
    }

    // ==== Movimientos ====

    public void registrarMovimiento(Movimiento movimiento) {
        if (movimiento != null) {
            movimientos.add(movimiento);
        }
    }

    public boolean moverEmpleado(Empleado empleado, Area nuevaArea) {
        if (empleado == null || nuevaArea == null) return false;
        // validar presupuesto destino
        if (!validarPresupuestoArea(nuevaArea, (int) empleado.getSalarioMensual())) {
            return false;
        }
        Area origen = empleado.getArea();
        empleado.setArea(nuevaArea);
        // Registrar movimiento
        Movimiento movimiento = new Movimiento(0, java.time.LocalDate.now().toString(),
                                               empleado, origen, nuevaArea);
        movimientos.add(movimiento);
        // Ajustar presupuestos (simplificado: ajustar atributos en Area)
        if (origen != null) {
            int nuevoPresOrigen = origen.getPresupuestoAnual() + (int) (empleado.getSalarioMensual() * 12);
            origen.setPresupuestoAnual(nuevoPresOrigen);
        }
        int nuevoPresDestino = nuevaArea.getPresupuestoAnual() - (int) (empleado.getSalarioMensual() * 12);
        nuevaArea.setPresupuestoAnual(nuevoPresDestino);
        return true;
    }

    // ==== API para managers usada por GUI y tests ====

    public int getCantidadEmpleadosACargo(Manager manager) {
        int count = 0;
        for (Empleado e : empleados) {
            if (manager.equals(e.getManager())) {
                count++;
            }
        }
        return count;
    }

    public Manager buscarManagerPorCedula(String cedula) {
        for (Manager m : managers) {
            if (m.getCedula().equals(cedula)) return m;
        }
        return null;
    }

    public boolean actualizarManager(Manager manager) {
        // En esta implementación, el Manager ya se actualizó desde la GUI
        // Solo verificamos formato del celular y devolvemos true si es válido
        if (manager == null) return false;
        if (!validarFormatoCelular(manager.getCelular())) {
            return false;
        }
        return true;
    }
}
