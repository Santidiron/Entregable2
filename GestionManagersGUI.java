//Diego Rocabado - Número de estudiante: 305310
//Santiago Dirón - Número de estudiante: 359644

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class GestionManagersGUI extends JFrame {
    private Sistema sistema;
    private JTable tablaManagers;
    private DefaultTableModel modeloTabla;
    private JButton btnAgregar, btnModificar, btnEliminar, btnRefrescar;
    
    public GestionManagersGUI(Sistema sistema) {
        this.sistema = sistema;
        inicializarComponentes();
        cargarDatos();
    }
    
    private void inicializarComponentes() {
        setTitle("Gestión de Managers");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        
        // Panel superior con título
        JPanel panelTitulo = new JPanel();
        JLabel lblTitulo = new JLabel("Gestión de Managers");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        panelTitulo.add(lblTitulo);
        add(panelTitulo, BorderLayout.NORTH);
        
        // Panel central con tabla
        String[] columnas = {"Nombre", "Cédula", "Antigüedad", "Celular", "Cantidad Empleados a Cargo"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaManagers = new JTable(modeloTabla);
        tablaManagers.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(tablaManagers);
        add(scrollPane, BorderLayout.CENTER);
        
        // Panel inferior con botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        
        btnAgregar = new JButton("Agregar Manager");
        btnAgregar.addActionListener(e -> mostrarDialogoAgregar());
        
        btnModificar = new JButton("Modificar Manager");
        btnModificar.addActionListener(e -> mostrarDialogoModificar());
        
        btnEliminar = new JButton("Eliminar Manager");
        btnEliminar.addActionListener(e -> eliminarManager());
        
        btnRefrescar = new JButton("Refrescar");
        btnRefrescar.addActionListener(e -> cargarDatos());
        
        panelBotones.add(btnAgregar);
        panelBotones.add(btnModificar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnRefrescar);
        
        add(panelBotones, BorderLayout.SOUTH);
    }
    
    private void cargarDatos() {
        modeloTabla.setRowCount(0);
        List<Manager> managers = sistema.getManagers();
        
        // Ordenar por antigüedad descendente
        Collections.sort(managers, new Comparator<Manager>() {
            @Override
            public int compare(Manager m1, Manager m2) {
                return Integer.compare(m2.getAntiguedad(), m1.getAntiguedad());
            }
        });
        
        for (Manager m : managers) {
            Object[] fila = {
                m.getNombre(),
                m.getCedula(),
                m.getAntiguedad(),
                m.getCelular(),
                sistema.getCantidadEmpleadosACargo(m)
            };
            modeloTabla.addRow(fila);
        }
    }
    
    private void mostrarDialogoAgregar() {
        JDialog dialogo = new JDialog(this, "Agregar Manager", true);
        dialogo.setSize(400, 350);
        dialogo.setLocationRelativeTo(this);
        dialogo.setLayout(new BorderLayout(10, 10));
        
        JPanel panelCampos = new JPanel(new GridLayout(5, 2, 10, 10));
        panelCampos.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel lblNombre = new JLabel("Nombre:*");
        JTextField txtNombre = new JTextField();
        
        JLabel lblCedula = new JLabel("Cédula:*");
        JTextField txtCedula = new JTextField();
        
        JLabel lblAntiguedad = new JLabel("Antigüedad (años):*");
        JTextField txtAntiguedad = new JTextField();
        
        JLabel lblCelular = new JLabel("Celular:*");
        JTextField txtCelular = new JTextField();
        
        JLabel lblArea = new JLabel("Área:*");
        JComboBox<String> cmbArea = new JComboBox<>();
        for (Area a : sistema.getAreas()) {
            cmbArea.addItem(a.getNombre());
        }
        
        panelCampos.add(lblNombre);
        panelCampos.add(txtNombre);
        panelCampos.add(lblCedula);
        panelCampos.add(txtCedula);
        panelCampos.add(lblAntiguedad);
        panelCampos.add(txtAntiguedad);
        panelCampos.add(lblCelular);
        panelCampos.add(txtCelular);
        panelCampos.add(lblArea);
        panelCampos.add(cmbArea);
        
        dialogo.add(panelCampos, BorderLayout.CENTER);
        
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btnGuardar = new JButton("Guardar");
        JButton btnCancelar = new JButton("Cancelar");
        
        btnGuardar.addActionListener(e -> {
            try {
                // Validar campos requeridos
                if (txtNombre.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(dialogo, "El nombre es requerido", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                if (txtCedula.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(dialogo, "La cédula es requerida", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Validar formato de cédula
                if (!sistema.validarFormatoCedula(txtCedula.getText().trim())) {
                    JOptionPane.showMessageDialog(dialogo, "Formato de cédula inválido. Use el formato: X.XXX.XXX-X o XXXXXXXX", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Validar cédula única
                if (!sistema.esCedulaUnica(txtCedula.getText().trim())) {
                    JOptionPane.showMessageDialog(dialogo, "La cédula ya existe en el sistema", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Validar antigüedad
                int antiguedad;
                try {
                    antiguedad = Integer.parseInt(txtAntiguedad.getText().trim());
                    if (antiguedad <= 0) {
                        JOptionPane.showMessageDialog(dialogo, "La antigüedad debe ser mayor a 0", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(dialogo, "La antigüedad debe ser un número entero", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Validar celular
                if (txtCelular.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(dialogo, "El celular es requerido", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                if (!sistema.validarFormatoCelular(txtCelular.getText().trim())) {
                    JOptionPane.showMessageDialog(dialogo, "Formato de celular inválido. Use el formato: 09X XXX XXX", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Obtener área seleccionada
                String nombreArea = (String) cmbArea.getSelectedItem();
                Area areaSeleccionada = null;
                for (Area a : sistema.getAreas()) {
                    if (a.getNombre().equals(nombreArea)) {
                        areaSeleccionada = a;
                        break;
                    }
                }
                
                // Crear el manager
                Manager nuevoManager = new Manager(
                    txtNombre.getText().trim(),
                    txtCedula.getText().trim(),
                    txtCelular.getText().trim(),
                    antiguedad,
                    areaSeleccionada,
                    new Empleado[0]
                );
                
                if (sistema.agregarManager(nuevoManager)) {
                    JOptionPane.showMessageDialog(dialogo, "Manager agregado exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    cargarDatos();
                    dialogo.dispose();
                } else {
                    JOptionPane.showMessageDialog(dialogo, "Error al agregar el manager", "Error", JOptionPane.ERROR_MESSAGE);
                }
                
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialogo, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        btnCancelar.addActionListener(e -> dialogo.dispose());
        
        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);
        dialogo.add(panelBotones, BorderLayout.SOUTH);
        
        dialogo.setVisible(true);
    }
    
    private void mostrarDialogoModificar() {
        int filaSeleccionada = tablaManagers.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un manager", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String cedula = (String) modeloTabla.getValueAt(filaSeleccionada, 1);
        Manager manager = sistema.buscarManagerPorCedula(cedula);
        
        if (manager == null) {
            JOptionPane.showMessageDialog(this, "Manager no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        JDialog dialogo = new JDialog(this, "Modificar Manager", true);
        dialogo.setSize(400, 300);
        dialogo.setLocationRelativeTo(this);
        dialogo.setLayout(new BorderLayout(10, 10));
        
        JPanel panelCampos = new JPanel(new GridLayout(5, 2, 10, 10));
        panelCampos.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel lblNombre = new JLabel("Nombre:");
        JTextField txtNombre = new JTextField(manager.getNombre());
        txtNombre.setEditable(false);
        
        JLabel lblCedula = new JLabel("Cédula:");
        JTextField txtCedula = new JTextField(manager.getCedula());
        txtCedula.setEditable(false);
        
        JLabel lblAntiguedad = new JLabel("Antigüedad (años):");
        JTextField txtAntiguedad = new JTextField(String.valueOf(manager.getAntiguedad()));
        txtAntiguedad.setEditable(false);
        
        JLabel lblCelular = new JLabel("Celular:*");
        JTextField txtCelular = new JTextField(manager.getCelular());
        
        JLabel lblEmpleados = new JLabel("Empleados a cargo:");
        JTextField txtEmpleados = new JTextField(String.valueOf(sistema.getCantidadEmpleadosACargo(manager)));
        txtEmpleados.setEditable(false);
        
        panelCampos.add(lblNombre);
        panelCampos.add(txtNombre);
        panelCampos.add(lblCedula);
        panelCampos.add(txtCedula);
        panelCampos.add(lblAntiguedad);
        panelCampos.add(txtAntiguedad);
        panelCampos.add(lblCelular);
        panelCampos.add(txtCelular);
        panelCampos.add(lblEmpleados);
        panelCampos.add(txtEmpleados);
        
        dialogo.add(panelCampos, BorderLayout.CENTER);
        
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btnGuardar = new JButton("Guardar");
        JButton btnCancelar = new JButton("Cancelar");
        
        btnGuardar.addActionListener(e -> {
            try {
                // Validar celular
                if (txtCelular.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(dialogo, "El celular es requerido", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                if (!sistema.validarFormatoCelular(txtCelular.getText().trim())) {
                    JOptionPane.showMessageDialog(dialogo, "Formato de celular inválido. Use el formato: 09X XXX XXX", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Actualizar solo el celular
                manager.setCelular(txtCelular.getText().trim());
                
                if (sistema.actualizarManager(manager)) {
                    JOptionPane.showMessageDialog(dialogo, "Manager actualizado exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    cargarDatos();
                    dialogo.dispose();
                } else {
                    JOptionPane.showMessageDialog(dialogo, "Error al actualizar el manager", "Error", JOptionPane.ERROR_MESSAGE);
                }
                
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialogo, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        btnCancelar.addActionListener(e -> dialogo.dispose());
        
        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);
        dialogo.add(panelBotones, BorderLayout.SOUTH);
        
        dialogo.setVisible(true);
    }
    
    private void eliminarManager() {
        int filaSeleccionada = tablaManagers.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un manager", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String cedula = (String) modeloTabla.getValueAt(filaSeleccionada, 1);
        Manager manager = sistema.buscarManagerPorCedula(cedula);
        
        if (manager == null) {
            JOptionPane.showMessageDialog(this, "Manager no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Verificar si tiene empleados a cargo
        int cantidadEmpleados = sistema.getCantidadEmpleadosACargo(manager);
        if (cantidadEmpleados > 0) {
            JOptionPane.showMessageDialog(this, 
                "No se puede eliminar el manager porque tiene " + cantidadEmpleados + " empleado(s) a cargo", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Confirmación
        int confirmacion = JOptionPane.showConfirmDialog(this, 
            "¿Está seguro que desea eliminar el manager " + manager.getNombre() + "?", 
            "Confirmar eliminación", 
            JOptionPane.YES_NO_OPTION);
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            if (sistema.eliminarManager(manager)) {
                JOptionPane.showMessageDialog(this, "Manager eliminado exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                cargarDatos();
            } else {
                JOptionPane.showMessageDialog(this, "Error al eliminar el manager", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Sistema sistema = new Sistema();
            GestionManagersGUI gui = new GestionManagersGUI(sistema);
            gui.setVisible(true);
        });
    }
}
