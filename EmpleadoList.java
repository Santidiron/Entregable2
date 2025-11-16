//Diego Rocabado
//Santiago Dirón

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class EmpleadoList extends JFrame {
    private Sistema sistema;
    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private JButton btnAgregar;
    private JButton btnEditar;
    private JButton btnVerCV;
    private JButton btnActualizar;

    public EmpleadoList(Sistema sistema) {
        this.sistema = sistema;
        initComponents();
        cargarEmpleados();
    }

    private void initComponents() {
        setTitle("Lista de Empleados");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Crear modelo de tabla
        String[] columnas = {"Legajo", "Nombre", "Apellido", "Cédula", "Salario", "Manager", "Área"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // No permitir edición directa
            }
        };

        // Crear tabla
        tabla = new JTable(modeloTabla);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabla.setAutoCreateRowSorter(true);
        
        // Configurar ancho de columnas
        tabla.getColumnModel().getColumn(0).setPreferredWidth(80);  // Legajo
        tabla.getColumnModel().getColumn(1).setPreferredWidth(120); // Nombre
        tabla.getColumnModel().getColumn(2).setPreferredWidth(120); // Apellido
        tabla.getColumnModel().getColumn(3).setPreferredWidth(100); // Cédula
        tabla.getColumnModel().getColumn(4).setPreferredWidth(100); // Salario
        tabla.getColumnModel().getColumn(5).setPreferredWidth(120); // Manager
        tabla.getColumnModel().getColumn(6).setPreferredWidth(120); // Área

        // Agregar tabla con scroll
        JScrollPane scrollPane = new JScrollPane(tabla);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(scrollPane, BorderLayout.CENTER);

        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        
        btnAgregar = new JButton("Agregar Empleado");
        btnAgregar.addActionListener(e -> agregarEmpleado());
        panelBotones.add(btnAgregar);

        btnEditar = new JButton("Editar");
        btnEditar.addActionListener(e -> editarEmpleado());
        btnEditar.setEnabled(false);
        panelBotones.add(btnEditar);

        btnVerCV = new JButton("Ver CV");
        btnVerCV.addActionListener(e -> verCV());
        btnVerCV.setEnabled(false);
        panelBotones.add(btnVerCV);

        btnActualizar = new JButton("Actualizar Lista");
        btnActualizar.addActionListener(e -> cargarEmpleados());
        panelBotones.add(btnActualizar);

        add(panelBotones, BorderLayout.SOUTH);

        // Listener para habilitar/deshabilitar botones
        tabla.getSelectionModel().addListSelectionListener(e -> {
            boolean haySeleccion = tabla.getSelectedRow() != -1;
            btnEditar.setEnabled(haySeleccion);
            btnVerCV.setEnabled(haySeleccion);
        });

        setSize(900, 500);
        setLocationRelativeTo(null);
    }

    private void cargarEmpleados() {
        // Limpiar tabla
        modeloTabla.setRowCount(0);

        // Obtener empleados ordenados por nombre
        List<Empleado> empleados = sistema.obtenerEmpleadosOrdenados();

        // Agregar empleados a la tabla
        for (Empleado emp : empleados) {
            Object[] fila = new Object[7];
            fila[0] = emp.getLegajo();
            fila[1] = emp.getNombre();
            fila[2] = emp.getApellido();
            fila[3] = emp.getCedula();
            fila[4] = String.format("%.2f", emp.getSalarioMensual());
            fila[5] = emp.getManager() != null ? emp.getManager().getNombre() : "N/A";
            fila[6] = emp.getArea() != null ? emp.getArea().getNombre() : "N/A";
            modeloTabla.addRow(fila);
        }
    }

    private void agregarEmpleado() {
        // Verificar que existan managers y áreas
        if (sistema.getManagers().isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Debe crear al menos un manager antes de agregar empleados", 
                "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (sistema.getAreas().isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Debe crear al menos un área antes de agregar empleados", 
                "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        EmpleadoForm form = new EmpleadoForm(sistema);
        form.setVisible(true);
        
        // Actualizar lista cuando se cierre el formulario
        form.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                cargarEmpleados();
            }
        });
    }

    private void editarEmpleado() {
        int filaSeleccionada = tabla.getSelectedRow();
        if (filaSeleccionada == -1) {
            return;
        }

        // Obtener legajo de la fila seleccionada
        int legajo = (Integer) tabla.getValueAt(filaSeleccionada, 0);
        Empleado empleado = sistema.buscarEmpleadoPorLegajo(legajo);

        if (empleado == null) {
            JOptionPane.showMessageDialog(this, 
                "No se encontró el empleado", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Mostrar diálogo de edición
        EmpleadoEditDialog dialog = new EmpleadoEditDialog(this, sistema, empleado);
        dialog.setVisible(true);

        // Actualizar lista
        cargarEmpleados();
    }

    private void verCV() {
        int filaSeleccionada = tabla.getSelectedRow();
        if (filaSeleccionada == -1) {
            return;
        }

        // Obtener legajo de la fila seleccionada
        int legajo = (Integer) tabla.getValueAt(filaSeleccionada, 0);
        Empleado empleado = sistema.buscarEmpleadoPorLegajo(legajo);

        if (empleado == null) {
            JOptionPane.showMessageDialog(this, 
                "No se encontró el empleado", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (empleado.getPathCV() == null || empleado.getPathCV().isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Este empleado no tiene CV cargado", 
                "Información", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Leer contenido del CV
        String contenidoCV = sistema.leerCVEmpleado(empleado);
        if (contenidoCV == null) {
            JOptionPane.showMessageDialog(this, 
                "No se pudo leer el archivo CV", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Mostrar CV en un diálogo
        CVViewDialog dialog = new CVViewDialog(this, empleado, contenidoCV);
        dialog.setVisible(true);
    }

    public static void main(String[] args) {
        // Configurar look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Crear sistema y datos de prueba
        Sistema sistema = new Sistema();
        
        // Crear áreas de prueba
        Area area1 = new Area(1, "Desarrollo", "Área de desarrollo de software", 500000, new Empleado[0]);
        Area area2 = new Area(2, "Recursos Humanos", "Área de gestión de personal", 300000, new Empleado[0]);
        sistema.agregarArea(area1);
        sistema.agregarArea(area2);

        // Crear managers de prueba
        Manager manager1 = new Manager("Juan", "12345678", "099123456", 5, area1, new Empleado[0]);
        Manager manager2 = new Manager("María", "87654321", "099654321", 3, area2, new Empleado[0]);
        sistema.agregarManager(manager1);
        sistema.agregarManager(manager2);

        // Mostrar ventana principal
        SwingUtilities.invokeLater(() -> {
            EmpleadoList frame = new EmpleadoList(sistema);
            frame.setVisible(true);
        });
    }
}
