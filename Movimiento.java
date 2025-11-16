//Diego Rocabado
//Santiago Dirón

public class Movimiento {
import java.io.Serializable;

public class Movimiento implements Serializable {
    private static final long serialVersionUID = 1L;

    private int mes;
    private String Fecha; // "Ingreso" o "Egreso"
    private Empleado empleado;
    private Area areaOrigen;
    private Area areaDestino;
    
    public Movimiento(int mes, String fecha, Empleado empleado, Area areaOrigen, Area areaDestino) {
        this.mes = mes;
        Fecha = fecha;
        this.empleado = empleado;
        this.areaOrigen = areaOrigen;
        this.areaDestino = areaDestino;
    }
    // Getters and Setters
    public int getMes() {
        return mes;
    }
    public void setMes(int mes) {
        this.mes = mes;
    }
    public String getFecha() {
        return Fecha;
    }
    public void setFecha(String fecha) {
        this.Fecha = fecha;
    }
    public Empleado getEmpleado() {
        return empleado;
    }
    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }
    public Area getAreaOrigen() {
        return areaOrigen;
    }
    public void setAreaOrigen(Area areaOrigen) {
        this.areaOrigen = areaOrigen;
    }
    public Area getAreaDestino() {
        return areaDestino;
    }
    public void setAreaDestino(Area areaDestino) {
        this.areaDestino = areaDestino;
    }
}