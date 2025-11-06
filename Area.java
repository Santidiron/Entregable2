//Diego Rocabado
//Santiago Dirón

public class Area{
    private int id;
    private String nombre;
    private String descripcion;
    private int presupuestoAnual;
    private Empleado empleados[];

    public Area(int id, String nombre, String descripcion, int presupuestoAnual, Empleado[] empleados) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.presupuestoAnual = presupuestoAnual;
        this.empleados = empleados;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    ;
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getDescripcion() {}
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public int getPresupuestoAnual() {
        return presupuestoAnual;
    }
    public void setPresupuestoAnual(int presupuestoAnual) {
        this.presupuestoAnual = presupuestoAnual;
    }
    public Empleado[] getEmpleados() {
        return empleados;
    }
    public void setEmpleados(Empleado[] empleados) {
        this.empleados = empleados;
    }
}
