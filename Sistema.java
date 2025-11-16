//Diego Rocabado
//Santiago Dirón

import java.util.ArrayList;
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
    }
}
