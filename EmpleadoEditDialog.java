//Diego Rocabado - Número de estudiante: 305310
//Santiago Dirón - Número de estudiante: 359644

import javax.swing.*;
import java.awt.*;

public class EmpleadoEditDialog extends JDialog {
    private Sistema sistema;
    private Empleado empleado;
    private JTextField txtCelular;
    private JTextField txtSalario;
    private JButton btnGuardar;
    private JButton btnCancelar;

    public EmpleadoEditDialog(Frame parent, Sistema sistema, Empleado empleado) {
        super(parent, "Editar Empleado", true);
        this.sistema = sistema;
        this.empleado = empleado;
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));

        JPanel panelInfo = new JPanel(new GridLayout(5, 2, 5, 5));
        panelInfo.setBorder(BorderFactory.createTitledBorder("Información del Empleado"));

        panelInfo.add(new JLabel("Legajo:"));
        panelInfo.add(new JLabel(String.valueOf(empleado.getLegajo())));

        panelInfo.add(new JLabel("Nombre:"));
        panelInfo.add(new JLabel(empleado.getNombre()));

        panelInfo.add(new JLabel("Apellido:"));
        panelInfo.add(new JLabel(empleado.getApellido()));

        panelInfo.add(new JLabel("Cédula:"));
        panelInfo.add(new JLabel(empleado.getCedula()));

        panelInfo.add(new JLabel("Área:"));
        panelInfo.add(new JLabel(empleado.getArea() != null ? empleado.getArea().getNombre() : "N/A"));

        JPanel panelEdicion = new JPanel(new GridLayout(2, 2, 5, 5));
        panelEdicion.setBorder(BorderFactory.createTitledBorder("Datos Editables"));

        panelEdicion.add(new JLabel("Celular:"));
        txtCelular = new JTextField(empleado.getCelular() != null ? empleado.getCelular() : "");
        panelEdicion.add(txtCelular);

        panelEdicion.add(new JLabel("Salario Mensual:"));
        txtSalario = new JTextField(String.valueOf(empleado.getSalarioMensual()));
        panelEdicion.add(txtSalario);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnGuardar = new JButton("Guardar");
        btnGuardar.addActionListener(e -> guardarCambios());
        btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(e -> dispose());
        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);

        JPanel panelCentral = new JPanel(new GridLayout(2, 1, 10, 10));
        panelCentral.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelCentral.add(panelInfo);
        panelCentral.add(panelEdicion);

        add(panelCentral, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(getParent());
    }

    private void guardarCambios() {
        try {
            String celular = txtCelular.getText().trim();
            if (!celular.isEmpty() && !celular.matches("\\d{8,15}")) {
                JOptionPane.showMessageDialog(this, 
                    "El celular debe contener entre 8 y 15 dígitos", 
                    "Error de validación", JOptionPane.ERROR_MESSAGE);
                txtCelular.requestFocus();
                return;
            }

            double salario;
            try {
                salario = Double.parseDouble(txtSalario.getText().trim());
                if (salario < 0) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, 
                    "El salario debe ser un número positivo", 
                    "Error de validación", JOptionPane.ERROR_MESSAGE);
                txtSalario.requestFocus();
                return;
            }

            sistema.actualizarEmpleado(empleado, celular, salario);

            JOptionPane.showMessageDialog(this, 
                "Empleado actualizado exitosamente", 
                "Éxito", JOptionPane.INFORMATION_MESSAGE);

            dispose();

        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error inesperado: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}
