//Diego Rocabado
//Santiago Dirón

import javax.swing.*;
import java.awt.*;
import java.awt.Desktop;
import java.io.File;

public class EmpleadoDetailsDialog extends JDialog {
    private Empleado empleado;
    
    public EmpleadoDetailsDialog(JFrame parent, Empleado empleado) {
        super(parent, "Detalles del Empleado", true);
        this.empleado = empleado;
        initComponents();
    }
    
    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setSize(450, 400);
        setLocationRelativeTo(getParent());
        
        // Panel principal con información
        JPanel mainPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Título
        JLabel titleLabel = new JLabel("Información del Empleado", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        
        // Información Personal
        JLabel personalLabel = new JLabel("INFORMACIÓN PERSONAL", JLabel.LEFT);
        personalLabel.setFont(new Font("Arial", Font.BOLD, 12));
        mainPanel.add(personalLabel);
        mainPanel.add(new JLabel("")); // Espacio
        
        addField(mainPanel, "Legajo:", String.valueOf(empleado.getLegajo()));
        addField(mainPanel, "Nombre:", empleado.getNombre());
        addField(mainPanel, "Apellido:", empleado.getApellido());
        addField(mainPanel, "Cédula:", empleado.getCedula());
        addField(mainPanel, "Celular:", empleado.getCelular());
        
        // Separador
        mainPanel.add(new JSeparator());
        mainPanel.add(new JSeparator());
        
        // Información Laboral
        JLabel laboralLabel = new JLabel("INFORMACIÓN LABORAL", JLabel.LEFT);
        laboralLabel.setFont(new Font("Arial", Font.BOLD, 12));
        mainPanel.add(laboralLabel);
        mainPanel.add(new JLabel("")); // Espacio
        
        addField(mainPanel, "Salario Mensual:", String.format("$%,.2f", empleado.getSalarioMensual()));
        addField(mainPanel, "Antigüedad:", empleado.getAntiguedad() + " años");
        addField(mainPanel, "Área:", empleado.getArea() != null ? empleado.getArea().getNombre() : "N/A");
        addField(mainPanel, "Manager:", empleado.getManager() != null ? empleado.getManager().getNombre() : "Sin Manager");
        
        // Panel de botones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        
        JButton verCVButton = new JButton("Ver CV Completo");
        verCVButton.addActionListener(e -> verCV());
        buttonPanel.add(verCVButton);
        
        JButton cerrarButton = new JButton("Cerrar");
        cerrarButton.addActionListener(e -> dispose());
        buttonPanel.add(cerrarButton);
        
        // Agregar componentes al diálogo
        add(titleLabel, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private void addField(JPanel panel, String label, String value) {
        JLabel labelComponent = new JLabel(label);
        labelComponent.setFont(new Font("Arial", Font.BOLD, 11));
        panel.add(labelComponent);
        
        JLabel valueComponent = new JLabel(value);
        panel.add(valueComponent);
    }
    
    private void verCV() {
        String cvPath = empleado.getPathCV();
        
        if (cvPath == null || cvPath.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "No hay CV disponible para este empleado.",
                "CV No Disponible",
                JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        File cvFile = new File(cvPath);
        
        if (!cvFile.exists()) {
            JOptionPane.showMessageDialog(this,
                "El archivo CV no se encuentra en la ruta especificada:\n" + cvPath,
                "Archivo No Encontrado",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            if (Desktop.isDesktopSupported()) {
                Desktop desktop = Desktop.getDesktop();
                if (desktop.isSupported(Desktop.Action.OPEN)) {
                    desktop.open(cvFile);
                } else {
                    JOptionPane.showMessageDialog(this,
                        "La función de abrir archivos no está soportada en este sistema.",
                        "Función No Soportada",
                        JOptionPane.WARNING_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this,
                    "Desktop no está soportado en este sistema.",
                    "Sistema No Soportado",
                    JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                "Error al abrir el archivo CV:\n" + ex.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
}
