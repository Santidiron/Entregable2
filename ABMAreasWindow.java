//Diego Rocabado - Número de estudiante: 305310
//Santiago Dirón - Número de estudiante: 359644

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ABMAreasWindow extends JFrame {
    private Sistema sistema;
    private JTable tablaAreas;
    private DefaultTableModel modeloTabla;
    private JTextField txtNombre;
    private JTextArea txtDescripcion;
    private JTextField txtPresupuesto;
    
    public ABMAreasWindow(Sistema sistema) {
        this.sistema = sistema;
        
        setTitle("ABM Áreas");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        
        // Create main panel with padding
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Create table for areas
        String[] columnNames = {"ID", "Nombre", "Descripción", "Presupuesto"};
        modeloTabla = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table read-only
            }
        };
        tablaAreas = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaAreas);
        
        // Create form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Datos del Área"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Name field
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Nombre:"), gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        txtNombre = new JTextField(20);
        formPanel.add(txtNombre, gbc);
        
        // Description field
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        formPanel.add(new JLabel("Descripción:"), gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        txtDescripcion = new JTextArea(3, 20);
        txtDescripcion.setLineWrap(true);
        txtDescripcion.setWrapStyleWord(true);
        JScrollPane descScrollPane = new JScrollPane(txtDescripcion);
        formPanel.add(descScrollPane, gbc);
        
        // Budget field
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;
        formPanel.add(new JLabel("Presupuesto Anual:"), gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        txtPresupuesto = new JTextField(20);
        formPanel.add(txtPresupuesto, gbc);
        
        // Create button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        
        JButton btnAgregar = new JButton("Agregar");
        JButton btnModificar = new JButton("Modificar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnMovimiento = new JButton("Movimiento de Empleado");
        
        buttonPanel.add(btnAgregar);
        buttonPanel.add(btnModificar);
        buttonPanel.add(btnEliminar);
        buttonPanel.add(btnMovimiento);
        
        // Add action listeners
        btnAgregar.addActionListener(e -> agregarArea());
        btnModificar.addActionListener(e -> modificarArea());
        btnEliminar.addActionListener(e -> eliminarArea());
        btnMovimiento.addActionListener(e -> abrirDialogoMovimiento());
        
        // Layout components
        JPanel topPanel = new JPanel(new BorderLayout(10, 10));
        topPanel.add(formPanel, BorderLayout.NORTH);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        
        add(mainPanel);
        
        // Load initial data
        actualizarTabla();
    }
    
    private void agregarArea() {
        try {
            String nombre = txtNombre.getText().trim();
            String descripcion = txtDescripcion.getText().trim();
            String presupuestoStr = txtPresupuesto.getText().trim();
            
            // Validate input
            if (nombre.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "El nombre es obligatorio", 
                    "Error de Validación", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (presupuestoStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "El presupuesto es obligatorio", 
                    "Error de Validación", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            int presupuesto = Integer.parseInt(presupuestoStr);
            
            Area nuevaArea = sistema.agregarArea(nombre, descripcion, presupuesto);
            
            if (nuevaArea == null) {
                if (sistema.existeAreaConNombre(nombre)) {
                    JOptionPane.showMessageDialog(this, 
                        "Ya existe un área con ese nombre", 
                        "Error de Validación", 
                        JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, 
                        "El presupuesto debe ser mayor a 0", 
                        "Error de Validación", 
                        JOptionPane.ERROR_MESSAGE);
                }
                return;
            }
            
            // Clear form
            limpiarFormulario();
            
            // Update table
            actualizarTabla();
            
            JOptionPane.showMessageDialog(this, 
                "Área agregada exitosamente con ID: " + nuevaArea.getId(), 
                "Éxito", 
                JOptionPane.INFORMATION_MESSAGE);
            
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, 
                "El presupuesto debe ser un número válido", 
                "Error de Validación", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void modificarArea() {
        int selectedRow = tablaAreas.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, 
                "Debe seleccionar un área de la tabla", 
                "Error", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int areaId = (int) modeloTabla.getValueAt(selectedRow, 0);
        Area area = sistema.buscarAreaPorId(areaId);
        
        if (area == null) {
            JOptionPane.showMessageDialog(this, 
                "Área no encontrada", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        String nuevaDescripcion = txtDescripcion.getText().trim();
        sistema.modificarArea(area, nuevaDescripcion);
        
        actualizarTabla();
        limpiarFormulario();
        
        JOptionPane.showMessageDialog(this, 
            "Área modificada exitosamente. Solo se puede modificar la descripción.", 
            "Éxito", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void eliminarArea() {
        int selectedRow = tablaAreas.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, 
                "Debe seleccionar un área de la tabla", 
                "Error", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int areaId = (int) modeloTabla.getValueAt(selectedRow, 0);
        Area area = sistema.buscarAreaPorId(areaId);
        
        if (area == null) {
            JOptionPane.showMessageDialog(this, 
                "Área no encontrada", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int confirmacion = JOptionPane.showConfirmDialog(this, 
            "¿Está seguro de eliminar el área '" + area.getNombre() + "'?", 
            "Confirmar Eliminación", 
            JOptionPane.YES_NO_OPTION);
        
        if (confirmacion != JOptionPane.YES_OPTION) {
            return;
        }
        
        boolean eliminado = sistema.eliminarArea(area);
        
        if (!eliminado) {
            JOptionPane.showMessageDialog(this, 
                "No se puede eliminar el área porque tiene empleados asignados", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        actualizarTabla();
        limpiarFormulario();
        
        JOptionPane.showMessageDialog(this, 
            "Área eliminada exitosamente", 
            "Éxito", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void abrirDialogoMovimiento() {
        if (sistema.getEmpleados().isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "No hay empleados registrados en el sistema", 
                "Información", 
                JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        if (sistema.getAreas().size() < 2) {
            JOptionPane.showMessageDialog(this, 
                "Se necesitan al menos 2 áreas para realizar un movimiento", 
                "Información", 
                JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        MovimientoDialog dialog = new MovimientoDialog(this, sistema);
        dialog.setVisible(true);
        
        // Update table after dialog closes
        actualizarTabla();
    }
    
    private void actualizarTabla() {
        // Clear table
        modeloTabla.setRowCount(0);
        
        // Get areas sorted by name
        List<Area> areas = sistema.getAreasSortedByName();
        
        // Add areas to table
        for (Area area : areas) {
            Object[] row = {
                area.getId(),
                area.getNombre(),
                area.getDescripcion(),
                area.getPresupuestoAnual()
            };
            modeloTabla.addRow(row);
        }
    }
    
    private void limpiarFormulario() {
        txtNombre.setText("");
        txtDescripcion.setText("");
        txtPresupuesto.setText("");
    }
}
