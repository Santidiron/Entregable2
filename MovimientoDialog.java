//Diego Rocabado - Número de estudiante: 305310
//Santiago Dirón - Número de estudiante: 359644

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MovimientoDialog extends JDialog {
    private Sistema sistema;
    private JComboBox<String> comboEmpleados;
    private JTextField txtAreaOrigen;
    private JComboBox<String> comboAreaDestino;
    private Empleado[] empleadosArray;
    private Area[] areasArray;
    
    public MovimientoDialog(JFrame parent, Sistema sistema) {
        super(parent, "Movimiento de Empleado", true);
        this.sistema = sistema;
        
        setSize(500, 300);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout(10, 10));
        
        // Create main panel
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Employee selection
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(new JLabel("Empleado:"), gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        List<Empleado> empleados = sistema.getEmpleados();
        empleadosArray = empleados.toArray(new Empleado[0]);
        String[] empleadosNombres = new String[empleadosArray.length];
        for (int i = 0; i < empleadosArray.length; i++) {
            empleadosNombres[i] = empleadosArray[i].getLegajo() + " - " + 
                                  empleadosArray[i].getNombre() + " " + 
                                  empleadosArray[i].getApellido();
        }
        comboEmpleados = new JComboBox<>(empleadosNombres);
        comboEmpleados.addActionListener(e -> actualizarAreaOrigen());
        mainPanel.add(comboEmpleados, gbc);
        
        // Source area (read-only)
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        mainPanel.add(new JLabel("Área Origen:"), gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        txtAreaOrigen = new JTextField();
        txtAreaOrigen.setEditable(false);
        mainPanel.add(txtAreaOrigen, gbc);
        
        // Destination area selection
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;
        mainPanel.add(new JLabel("Área Destino:"), gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        List<Area> areas = sistema.getAreas();
        areasArray = areas.toArray(new Area[0]);
        String[] areasNombres = new String[areasArray.length];
        for (int i = 0; i < areasArray.length; i++) {
            areasNombres[i] = areasArray[i].getId() + " - " + areasArray[i].getNombre();
        }
        comboAreaDestino = new JComboBox<>(areasNombres);
        mainPanel.add(comboAreaDestino, gbc);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        
        JButton btnAceptar = new JButton("Aceptar");
        JButton btnCancelar = new JButton("Cancelar");
        
        btnAceptar.addActionListener(e -> realizarMovimiento());
        btnCancelar.addActionListener(e -> dispose());
        
        buttonPanel.add(btnAceptar);
        buttonPanel.add(btnCancelar);
        
        // Add panels to dialog
        add(mainPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        
        // Initialize source area
        if (empleadosArray.length > 0) {
            actualizarAreaOrigen();
        }
    }
    
    private void actualizarAreaOrigen() {
        int selectedIndex = comboEmpleados.getSelectedIndex();
        if (selectedIndex >= 0 && selectedIndex < empleadosArray.length) {
            Empleado empleado = empleadosArray[selectedIndex];
            Area areaActual = empleado.getArea();
            if (areaActual != null) {
                txtAreaOrigen.setText(areaActual.getId() + " - " + areaActual.getNombre());
            } else {
                txtAreaOrigen.setText("Sin área asignada");
            }
        }
    }
    
    private void realizarMovimiento() {
        int empleadoIndex = comboEmpleados.getSelectedIndex();
        int areaDestinoIndex = comboAreaDestino.getSelectedIndex();
        
        if (empleadoIndex < 0 || areaDestinoIndex < 0) {
            JOptionPane.showMessageDialog(this, 
                "Debe seleccionar un empleado y un área destino", 
                "Error de Validación", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        Empleado empleado = empleadosArray[empleadoIndex];
        Area areaDestino = areasArray[areaDestinoIndex];
        
        // Check if employee is already in the destination area
        if (empleado.getArea() != null && empleado.getArea().getId() == areaDestino.getId()) {
            JOptionPane.showMessageDialog(this, 
                "El empleado ya está en el área destino", 
                "Error de Validación", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Try to move employee
        boolean exito = sistema.moverEmpleado(empleado, areaDestino);
        
        if (!exito) {
            double salarioAnual = empleado.getSalarioMensual() * 12;
            JOptionPane.showMessageDialog(this, 
                "El área destino no tiene presupuesto suficiente.\n" +
                "Presupuesto disponible: " + areaDestino.getPresupuestoAnual() + "\n" +
                "Salario anual del empleado: " + salarioAnual, 
                "Error de Presupuesto", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        JOptionPane.showMessageDialog(this, 
            "Movimiento realizado exitosamente.\n" +
            "Los presupuestos han sido actualizados.", 
            "Éxito", 
            JOptionPane.INFORMATION_MESSAGE);
        
        dispose();
    }
}
