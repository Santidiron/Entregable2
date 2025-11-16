//Diego Rocabado
//Santiago Dirón

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SistemaGUI extends JFrame {
    private Sistema sistema;
    private JTextArea outputArea;
    private JMenuBar menuBar;
    
    public SistemaGUI() {
        sistema = new Sistema();
        inicializarGUI();
    }
    
    private void inicializarGUI() {
        setTitle("Sistema de Gestión de Personal");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Create menu bar
        menuBar = new JMenuBar();
        
        // Datos menu
        JMenu datosMenu = new JMenu("Datos");
        JMenuItem cargarDatosItem = new JMenuItem("Cargar Datos Precargados");
        cargarDatosItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cargarDatosPrecargados();
            }
        });
        
        JMenuItem limpiarItem = new JMenuItem("Limpiar Consola");
        limpiarItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                outputArea.setText("");
            }
        });
        
        datosMenu.add(cargarDatosItem);
        datosMenu.addSeparator();
        datosMenu.add(limpiarItem);
        
        menuBar.add(datosMenu);
        setJMenuBar(menuBar);
        
        // Output area
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(outputArea);
        
        add(scrollPane, BorderLayout.CENTER);
        
        // Welcome message
        outputArea.append("=== Sistema de Gestión de Personal ===\n");
        outputArea.append("Seleccione 'Datos → Cargar Datos Precargados' para cargar datos de ejemplo.\n\n");
    }
    
    private void cargarDatosPrecargados() {
        // Check if system already has data
        if (sistema.tieneDatos()) {
            int opcion = JOptionPane.showConfirmDialog(
                this,
                "El sistema ya contiene datos.\n¿Desea continuar y agregar más datos precargados?",
                "Advertencia: Sistema con Datos",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
            );
            
            if (opcion != JOptionPane.YES_OPTION) {
                outputArea.append("Operación cancelada por el usuario.\n\n");
                return;
            }
        }
        
        // Confirmation dialog
        int confirmacion = JOptionPane.showConfirmDialog(
            this,
            "¿Está seguro que desea cargar los datos precargados?\n" +
            "Esto creará:\n" +
            "- 5 áreas (Personal, RRHH, Seguridad, Comunicaciones, Marketing)\n" +
            "- 4 managers\n" +
            "- 12 empleados\n" +
            "- 5 movimientos históricos\n" +
            "- 12 archivos CV en la carpeta cvs/",
            "Confirmar Carga de Datos",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            try {
                outputArea.append("Cargando datos precargados...\n");
                sistema.cargarDatosPrecargados();
                
                outputArea.append("\n=== DATOS CARGADOS EXITOSAMENTE ===\n\n");
                mostrarResumenDatos();
                
                JOptionPane.showMessageDialog(
                    this,
                    "¡Datos precargados exitosamente!\n\n" +
                    "Áreas: " + sistema.getAreas().size() + "\n" +
                    "Managers: " + sistema.getManagers().size() + "\n" +
                    "Empleados: " + sistema.getEmpleados().size() + "\n" +
                    "Movimientos: " + sistema.getMovimientos().size(),
                    "Carga Exitosa",
                    JOptionPane.INFORMATION_MESSAGE
                );
            } catch (Exception ex) {
                outputArea.append("\nERROR al cargar datos: " + ex.getMessage() + "\n");
                JOptionPane.showMessageDialog(
                    this,
                    "Error al cargar los datos: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
                ex.printStackTrace();
            }
        } else {
            outputArea.append("Carga de datos cancelada.\n\n");
        }
    }
    
    private void mostrarResumenDatos() {
        outputArea.append("Áreas cargadas: " + sistema.getAreas().size() + "\n");
        for (Area area : sistema.getAreas()) {
            outputArea.append("  - " + area.getNombre() + " (Presupuesto: $" + 
                            area.getPresupuestoAnual() + ")\n");
            outputArea.append("    " + area.getDescripcion() + "\n");
        }
        
        outputArea.append("\nManagers cargados: " + sistema.getManagers().size() + "\n");
        for (Manager manager : sistema.getManagers()) {
            outputArea.append("  - " + manager.getNombre() + " (Cédula: " + 
                            manager.getCedula() + ", Antigüedad: " + 
                            manager.getAntiguedad() + " años)\n");
            outputArea.append("    Área: " + manager.getArea().getNombre() + "\n");
        }
        
        outputArea.append("\nEmpleados cargados: " + sistema.getEmpleados().size() + "\n");
        for (Empleado empleado : sistema.getEmpleados()) {
            outputArea.append("  - " + empleado.getNombre() + " " + empleado.getApellido() + 
                            " (Legajo: " + empleado.getLegajo() + 
                            ", Salario: $" + empleado.getSalarioMensual() + ")\n");
            outputArea.append("    Área: " + empleado.getArea().getNombre() + 
                            ", Manager: " + empleado.getManager().getNombre() + "\n");
        }
        
        outputArea.append("\nMovimientos cargados: " + sistema.getMovimientos().size() + "\n");
        for (Movimiento movimiento : sistema.getMovimientos()) {
            outputArea.append("  - Mes " + movimiento.getMes() + ": " + 
                            movimiento.getEmpleado().getNombre() + " " + 
                            movimiento.getEmpleado().getApellido() + "\n");
            outputArea.append("    De " + movimiento.getAreaOrigen().getNombre() + 
                            " a " + movimiento.getAreaDestino().getNombre() + "\n");
        }
        
        outputArea.append("\n");
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                SistemaGUI gui = new SistemaGUI();
                gui.setVisible(true);
            }
        });
    }
}
