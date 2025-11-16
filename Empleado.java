//Diego Rocabado
//Santiago Dirón

public class Empleado {
    private int legajo;
    private String nombre;
    private String apellido;
    private String cedula;
    private String celular;
    private String pathCV;
    private int antiguedad;
    private double salarioMensual;
    private Manager manager;
    private Area area;

    public Empleado(int legajo, String nombre, String apellido, String cedula, String celular, String pathCV,
                    int antiguedad, double salarioMensual, Manager manager, Area area) {
        this.legajo = legajo;
        this.nombre = nombre;
        this.apellido = apellido;
        this.cedula = cedula;
        this.celular = celular;
        this.pathCV = pathCV;
        this.antiguedad = antiguedad;
        this.salarioMensual = salarioMensual;
        this.manager = manager;
        this.area = area;
    }

    // Getters and Setters
    public int getLegajo() {
        return legajo;
    }
    public void setLegajo(int legajo) {
        this.legajo = legajo;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getApellido() {
        return apellido;
    }
    public void setApellido(String apellido) {
        this.apellido = apellido;
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
    public String getPathCV() {
        return pathCV;
    }
    public void setPathCV(String pathCV) {
        this.pathCV = pathCV;
    }
    public int getAntiguedad() {
        return antiguedad;
    }
    public void setAntiguedad(int antiguedad) {
        this.antiguedad = antiguedad;  
    }
    public double getSalarioMensual() {
        return salarioMensual;
    }
    public void setSalarioMensual(double salarioMensual) {
        this.salarioMensual = salarioMensual;
    }
    public Manager getManager() {
        return manager;
    }
    public void setManager(Manager manager) {
        this.manager = manager;
    }
    public Area getArea() {
        return area;
    }
    public void setArea(Area area) {
        this.area = area;
    }
}
