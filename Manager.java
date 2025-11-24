//Diego Rocabado - Número de estudiante: 305310
//Santiago Dirón - Número de estudiante: 359644

import java.io.Serializable;

public class Manager implements Serializable {
    private static final long serialVersionUID = 1L;

    private String nombre;
    private String cedula;
    private String celular;
    private int antiguedad;
    private Area area;
    private Empleado empleados[];

    public Manager(String nombre, String cedula, String celular, int antiguedad, Area area, Empleado[] empleados)
    {
        this.nombre = nombre;
        this.cedula = cedula;
        this.celular = celular;
        this.antiguedad = antiguedad;
        this.area = area;
        this.empleados = empleados;
    }
    // Getters and Setters
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getCedula() {
        return cedula;
    }
    public void setCedula(String cedula) {
        this.cedula = cedula;
    }
    public String getCelular() {
        return celular;
    }
    public void setCelular(String celular) {
        this.celular = celular;
    }
    public int getAntiguedad() {
        return antiguedad;
    }
    public void setAntiguedad(int antiguedad) {
        this.antiguedad = antiguedad;
    }
    public Area getArea() {
        return area;
    }
    public void setArea(Area area) {
        this.area = area;
    }
    public Empleado[] getEmpleados() {
        return empleados;
    }
    public void setEmpleados(Empleado[] empleados) {
        this.empleados = empleados;
    }
}
