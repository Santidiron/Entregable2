//Diego Rocabado - Número de estudiante: 305310
//Santiago Dirón - Número de estudiante: 359644

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class EmpleadoForm extends JFrame 
{
    private Sistema sistema;
    private JTextField txtNombre;
    private JTextField txtApellido;
    private JTextField txtCedula;
    private JTextField txtCelular;
    private JTextField txtCV;
    private JTextField txtAntiguedad;
    private JTextField txtSalario;
    private JComboBox<Manager> cmbManager;
    private JComboBox<Area> cmbArea;
    private JLabel lblLegajo;
    private JButton btnExplorar;
    private JButton btnGuardar;
    private JButton btnCancelar;

    public EmpleadoForm(Sistema sistema) 
    {
        this.sistema = sistema;
        initComponents();
        cargarDatos();
    }

    private void initComponents() 
    {
        setTitle("Alta de Empleado");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        panelFormulario.add(new JLabel("Legajo:"), gbc);
        
        gbc.gridx = 1;
        lblLegajo = new JLabel(String.valueOf(sistema.generarLegajo()));
        lblLegajo.setFont(new Font("Arial", Font.BOLD, 12));
        panelFormulario.add(lblLegajo, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panelFormulario.add(new JLabel("Nombre: *"), gbc);
        
        gbc.gridx = 1;
        txtNombre = new JTextField(20);
        panelFormulario.add(txtNombre, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panelFormulario.add(new JLabel("Apellido: *"), gbc);
        
        gbc.gridx = 1;
        txtApellido = new JTextField(20);
        panelFormulario.add(txtApellido, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panelFormulario.add(new JLabel("Cédula: *"), gbc);
        
        gbc.gridx = 1;
        txtCedula = new JTextField(20);
        panelFormulario.add(txtCedula, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        panelFormulario.add(new JLabel("Celular:"), gbc);
        
        gbc.gridx = 1;
        txtCelular = new JTextField(20);
        panelFormulario.add(txtCelular, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        panelFormulario.add(new JLabel("Currículum (.txt):"), gbc);
        
        gbc.gridx = 1;
        JPanel panelCV = new JPanel(new BorderLayout(5, 0));
        txtCV = new JTextField(15);
        txtCV.setEditable(false);
        btnExplorar = new JButton("Explorar...");
        btnExplorar.addActionListener(e -> explorarCV());
        panelCV.add(txtCV, BorderLayout.CENTER);
        panelCV.add(btnExplorar, BorderLayout.EAST);
        panelFormulario.add(panelCV, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        panelFormulario.add(new JLabel("Antigüedad (años):"), gbc);
        
        gbc.gridx = 1;
        txtAntiguedad = new JTextField(20);
        txtAntiguedad.setText("0");
        panelFormulario.add(txtAntiguedad, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        panelFormulario.add(new JLabel("Salario Mensual:"), gbc);
        
        gbc.gridx = 1;
        txtSalario = new JTextField(20);
        txtSalario.setText("0.0");
        panelFormulario.add(txtSalario, gbc);

        gbc.gridx = 0;
        gbc.gridy = 8;
        panelFormulario.add(new JLabel("Manager: *"), gbc);
        
        gbc.gridx = 1;
        cmbManager = new JComboBox<>();
        cmbManager.setRenderer(new DefaultListCellRenderer() 
        {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, 
                    int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Manager) {
                    Manager m = (Manager) value;
                    setText(m.getNombre() + " (" + m.getCedula() + ")");
                }
                return this;
            }
        });
        panelFormulario.add(cmbManager, gbc);

        gbc.gridx = 0;
        gbc.gridy = 9;
        panelFormulario.add(new JLabel("Área: *"), gbc);
        
        gbc.gridx = 1;
        cmbArea = new JComboBox<>();
        cmbArea.setRenderer(new DefaultListCellRenderer() 
        {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, 
                    int index, boolean isSelected, boolean cellHasFocus) 
            {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Area) {
                    Area a = (Area) value;
                    setText(a.getNombre() + " (ID: " + a.getId() + ")");
                }
                return this;
            }
        });
        panelFormulario.add(cmbArea, gbc);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnGuardar = new JButton("Guardar");
        btnGuardar.addActionListener(e -> guardarEmpleado());
        btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(e -> dispose());
        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);

        add(panelFormulario, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        JLabel lblNota = new JLabel("* Campos obligatorios");
        lblNota.setFont(new Font("Arial", Font.ITALIC, 10));
        lblNota.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        add(lblNota, BorderLayout.NORTH);

        pack();
        setLocationRelativeTo(null);
    }

    private void cargarDatos() 
    {
        cmbManager.removeAllItems();
        for (Manager manager : sistema.getManagers()) 
        {
            cmbManager.addItem(manager);
        }

        cmbArea.removeAllItems();
        for (Area area : sistema.getAreas()) 
        {
            cmbArea.addItem(area);
        }
    }

    private void explorarCV() 
    {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new javax.swing.filechooser.FileFilter() 
        {
            @Override
            public boolean accept(File f) {
                return f.isDirectory() || f.getName().toLowerCase().endsWith(".txt");
            }

            @Override
            public String getDescription() {
                return "Archivos de texto (*.txt)";
            }
        });

        int resultado = fileChooser.showOpenDialog(this);
        if (resultado == JFileChooser.APPROVE_OPTION) 
        {
            File archivo = fileChooser.getSelectedFile();
            txtCV.setText(archivo.getAbsolutePath());
        }
    }

    private void guardarEmpleado() 
    {
        try 
        {
            if (txtNombre.getText().trim().isEmpty()) 
            {
                JOptionPane.showMessageDialog(this, "El nombre es obligatorio", 
                    "Error de validación", JOptionPane.ERROR_MESSAGE);
                txtNombre.requestFocus();
                return;
            }

            if (txtApellido.getText().trim().isEmpty()) 
            {
                JOptionPane.showMessageDialog(this, "El apellido es obligatorio", 
                    "Error de validación", JOptionPane.ERROR_MESSAGE);
                txtApellido.requestFocus();
                return;
            }

            if (txtCedula.getText().trim().isEmpty()) 
            {
                JOptionPane.showMessageDialog(this, "La cédula es obligatoria", 
                    "Error de validación", JOptionPane.ERROR_MESSAGE);
                txtCedula.requestFocus();
                return;
            }

            if (cmbManager.getSelectedItem() == null) 
            {
                JOptionPane.showMessageDialog(this, "Debe seleccionar un manager", 
                    "Error de validación", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (cmbArea.getSelectedItem() == null) 
            {
                JOptionPane.showMessageDialog(this, "Debe seleccionar un área", 
                    "Error de validación", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String celular = txtCelular.getText().trim();
            if (!celular.isEmpty() && !celular.matches("\\d{8,15}")) 
            {
                JOptionPane.showMessageDialog(this, 
                    "El celular debe contener entre 8 y 15 dígitos", 
                    "Error de validación", JOptionPane.ERROR_MESSAGE);
                txtCelular.requestFocus();
                return;
            }

            int antiguedad = 0;
            try {
                antiguedad = Integer.parseInt(txtAntiguedad.getText().trim());
                if (antiguedad < 0) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException e) 
            {
                JOptionPane.showMessageDialog(this, 
                    "La antigüedad debe ser un número entero positivo", 
                    "Error de validación", JOptionPane.ERROR_MESSAGE);
                txtAntiguedad.requestFocus();
                return;
            }

            double salario = 0;
            try 
            {
                salario = Double.parseDouble(txtSalario.getText().trim());
                if (salario < 0) 
                {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException e) 
            {
                JOptionPane.showMessageDialog(this, 
                    "El salario debe ser un número positivo", 
                    "Error de validación", JOptionPane.ERROR_MESSAGE);
                txtSalario.requestFocus();
                return;
            }

            String pathCV = txtCV.getText().trim();
            if (!pathCV.isEmpty()) 
            {
                if (!pathCV.toLowerCase().endsWith(".txt")) 
                {
                    JOptionPane.showMessageDialog(this, 
                        "El archivo CV debe ser de tipo .txt", 
                        "Error de validación", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                File archivoCV = new File(pathCV);
                if (!archivoCV.exists()) {
                    JOptionPane.showMessageDialog(this, 
                        "El archivo CV no existe", 
                        "Error de validación", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            Empleado empleado = sistema.crearEmpleado(
                txtNombre.getText().trim(),
                txtApellido.getText().trim(),
                txtCedula.getText().trim(),
                celular,
                pathCV.isEmpty() ? null : pathCV,
                antiguedad,
                salario,
                (Manager) cmbManager.getSelectedItem(),
                (Area) cmbArea.getSelectedItem()
            );

            JOptionPane.showMessageDialog(this, 
                "Empleado creado exitosamente\nLegajo: " + empleado.getLegajo(), 
                "Éxito", JOptionPane.INFORMATION_MESSAGE);

            dispose();

        } catch (IllegalArgumentException e) 
        {
            JOptionPane.showMessageDialog(this, e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) 
        {
            JOptionPane.showMessageDialog(this, 
                "Error inesperado: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}
