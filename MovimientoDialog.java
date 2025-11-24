
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MovimientoDialog extends JDialog
{
    private Sistema sistema;
    private JComboBox<String> comboMes;
    private JComboBox<String> comboAreaOrigen;
    private JComboBox<String> comboEmpleados;
    private JComboBox<String> comboAreaDestino;
    private JTextArea txtInfoPresupuesto;
    private Area[] areasOrigenArray;
    private Empleado[] empleadosArray;
    private Area[] areasDestinoArray;

    private static final String[] MESES =
            {
        "Enero (mes 1)", "Febrero (mes 2)", "Marzo (mes 3)", "Abril (mes 4)",
        "Mayo (mes 5)", "Junio (mes 6)", "Julio (mes 7)", "Agosto (mes 8)",
        "Septiembre (mes 9)", "Octubre (mes 10)", "Noviembre (mes 11)", "Diciembre (mes 12)"
    };

    public MovimientoDialog(JFrame parent, Sistema sistema)
    {
        super(parent, "Realizar Movimiento de Empleado", true);
        this.sistema = sistema;
        
        setSize(700, 600);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout(10, 10));

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        mainPanel.add(new JLabel("Mes del movimiento:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        comboMes = new JComboBox<>(MESES);
        comboMes.setSelectedIndex(0);
        comboMes.addActionListener(e -> actualizarInformacion());
        mainPanel.add(comboMes, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        mainPanel.add(new JLabel("Área de origen:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        List<Area> areasOrigen = sistema.getAreas();
        areasOrigenArray = areasOrigen.toArray(new Area[0]);
        String[] areasOrigenNombres = new String[areasOrigenArray.length];
        for (int i = 0; i < areasOrigenArray.length; i++) {
            areasOrigenNombres[i] = areasOrigenArray[i].getNombre();
        }
        comboAreaOrigen = new JComboBox<>(areasOrigenNombres);
        comboAreaOrigen.addActionListener(e -> cargarEmpleadosDeArea());
        mainPanel.add(comboAreaOrigen, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;
        mainPanel.add(new JLabel("Empleado:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        comboEmpleados = new JComboBox<>();
        comboEmpleados.addActionListener(e -> actualizarInformacion());
        mainPanel.add(comboEmpleados, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0;
        mainPanel.add(new JLabel("Área destino:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        List<Area> areasDestino = sistema.getAreas();
        areasDestinoArray = areasDestino.toArray(new Area[0]);
        String[] areasDestinoNombres = new String[areasDestinoArray.length];
        for (int i = 0; i < areasDestinoArray.length; i++) {
            areasDestinoNombres[i] = areasDestinoArray[i].getNombre();
        }
        comboAreaDestino = new JComboBox<>(areasDestinoNombres);
        comboAreaDestino.addActionListener(e -> actualizarInformacion());
        mainPanel.add(comboAreaDestino, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;

        JPanel infoPanel = new JPanel(new BorderLayout());
        infoPanel.setBorder(BorderFactory.createTitledBorder("Información del Movimiento"));

        txtInfoPresupuesto = new JTextArea(10, 40);
        txtInfoPresupuesto.setEditable(false);
        txtInfoPresupuesto.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(txtInfoPresupuesto);
        infoPanel.add(scrollPane, BorderLayout.CENTER);

        mainPanel.add(infoPanel, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        
        JButton btnAceptar = new JButton("Realizar Movimiento");
        JButton btnCancelar = new JButton("Cancelar");
        
        btnAceptar.addActionListener(e -> realizarMovimiento());
        btnCancelar.addActionListener(e -> dispose());
        
        buttonPanel.add(btnAceptar);
        buttonPanel.add(btnCancelar);
        
        add(mainPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        
        cargarEmpleadosDeArea();
    }
    
    private void cargarEmpleadosDeArea() {
        int areaIndex = comboAreaOrigen.getSelectedIndex();
        if (areaIndex < 0) return;

        Area areaSeleccionada = areasOrigenArray[areaIndex];

        List<Empleado> empleadosEnArea = new java.util.ArrayList<>();
        for (Empleado emp : sistema.getEmpleados()) {
            if (emp.getArea() != null && emp.getArea().getId() == areaSeleccionada.getId()) {
                empleadosEnArea.add(emp);
            }
        }

        empleadosArray = empleadosEnArea.toArray(new Empleado[0]);

        comboEmpleados.removeAllItems();
        for (Empleado emp : empleadosArray) {
            comboEmpleados.addItem(emp.getNombre() + " " + emp.getApellido() +
                                  " (Legajo: " + emp.getLegajo() +
                                  ", Salario: $" + String.format("%.2f", emp.getSalarioMensual()) + ")");
        }

        actualizarInformacion();
    }

    private void actualizarInformacion() {
        int mesIndex = comboMes.getSelectedIndex();
        int empleadoIndex = comboEmpleados.getSelectedIndex();
        int areaDestinoIndex = comboAreaDestino.getSelectedIndex();

        if (empleadoIndex < 0 || empleadoIndex >= empleadosArray.length ||
            areaDestinoIndex < 0) {
            txtInfoPresupuesto.setText("Seleccione todos los campos para ver la información.");
            return;
        }

        int mes = mesIndex + 1;
        Empleado empleado = empleadosArray[empleadoIndex];
        Area areaOrigen = empleado.getArea();
        Area areaDestino = areasDestinoArray[areaDestinoIndex];

        int mesesRestantes = 13 - mes;

        double salarioMensual = empleado.getSalarioMensual();
        double costoMovimiento = salarioMensual * mesesRestantes;

        double presupuestoUsadoDestino = 0;
        for (Empleado emp : sistema.getEmpleados()) {
            if (emp.getArea() != null && emp.getArea().getId() == areaDestino.getId()) {
                presupuestoUsadoDestino += emp.getSalarioMensual() * 12;
            }
        }
        double presupuestoDisponibleDestino = areaDestino.getPresupuestoAnual() - presupuestoUsadoDestino;
        double porcentajeUsadoDestino = (presupuestoUsadoDestino / areaDestino.getPresupuestoAnual()) * 100;

        double presupuestoUsadoOrigen = 0;
        for (Empleado emp : sistema.getEmpleados()) {
            if (emp.getArea() != null && emp.getArea().getId() == areaOrigen.getId()) {
                presupuestoUsadoOrigen += emp.getSalarioMensual() * 12;
            }
        }
        double presupuestoDisponibleOrigen = areaOrigen.getPresupuestoAnual() - presupuestoUsadoOrigen;
        double porcentajeUsadoOrigen = (presupuestoUsadoOrigen / areaOrigen.getPresupuestoAnual()) * 100;

        double liberacionOrigen = salarioMensual * mesesRestantes;
        double nuevoUsadoOrigen = presupuestoUsadoOrigen - (salarioMensual * 12);
        double nuevoDisponibleOrigen = presupuestoDisponibleOrigen + liberacionOrigen;
        double nuevoPorcentajeOrigen = (nuevoUsadoOrigen / areaOrigen.getPresupuestoAnual()) * 100;

        double nuevoUsadoDestino = presupuestoUsadoDestino + (salarioMensual * 12);
        double nuevoDisponibleDestino = presupuestoDisponibleDestino - costoMovimiento;
        double nuevoPorcentajeDestino = (nuevoUsadoDestino / areaDestino.getPresupuestoAnual()) * 100;

        StringBuilder info = new StringBuilder();
        info.append("═══════════════════════════════════════════════════════════════\n");
        info.append("  INFORMACIÓN DEL MOVIMIENTO\n");
        info.append("═══════════════════════════════════════════════════════════════\n\n");

        info.append("EMPLEADO: ").append(empleado.getNombre()).append(" ").append(empleado.getApellido()).append("\n");
        info.append("  • Legajo: ").append(empleado.getLegajo()).append("\n");
        info.append("  • Salario Mensual: $").append(String.format("%.2f", salarioMensual)).append("\n");
        info.append("  • Mes del movimiento: ").append(MESES[mesIndex]).append("\n");
        info.append("  • Meses restantes del año: ").append(mesesRestantes).append(" (meses ").append(mes).append(" a 12)\n");
        info.append("  • Costo del movimiento: $").append(String.format("%.2f", costoMovimiento)).append("\n\n");

        info.append("───────────────────────────────────────────────────────────────\n");
        info.append("ÁREA DE ORIGEN: ").append(areaOrigen.getNombre()).append("\n");
        info.append("───────────────────────────────────────────────────────────────\n");
        info.append("  Situación ACTUAL:\n");
        info.append("    • Presupuesto Anual: $").append(String.format("%.2f", (double)areaOrigen.getPresupuestoAnual())).append("\n");
        info.append("    • Presupuesto Usado: $").append(String.format("%.2f", presupuestoUsadoOrigen));
        info.append(" (").append(String.format("%.1f", porcentajeUsadoOrigen)).append("%)\n");
        info.append("    • Presupuesto Disponible: $").append(String.format("%.2f", presupuestoDisponibleOrigen)).append("\n\n");

        info.append("  Situación DESPUÉS del movimiento:\n");
        info.append("    • Se liberan: $").append(String.format("%.2f", liberacionOrigen)).append(" (").append(mesesRestantes).append(" meses)\n");
        info.append("    • Presupuesto Usado: $").append(String.format("%.2f", nuevoUsadoOrigen));
        info.append(" (").append(String.format("%.1f", nuevoPorcentajeOrigen)).append("%)\n");
        info.append("    • Presupuesto Disponible: $").append(String.format("%.2f", nuevoDisponibleOrigen)).append("\n\n");

        info.append("───────────────────────────────────────────────────────────────\n");
        info.append("ÁREA DE DESTINO: ").append(areaDestino.getNombre()).append("\n");
        info.append("───────────────────────────────────────────────────────────────\n");
        info.append("  Situación ACTUAL:\n");
        info.append("    • Presupuesto Anual: $").append(String.format("%.2f", (double)areaDestino.getPresupuestoAnual())).append("\n");
        info.append("    • Presupuesto Usado: $").append(String.format("%.2f", presupuestoUsadoDestino));
        info.append(" (").append(String.format("%.1f", porcentajeUsadoDestino)).append("%)\n");
        info.append("    • Presupuesto Disponible: $").append(String.format("%.2f", presupuestoDisponibleDestino)).append("\n\n");

        info.append("  Situación DESPUÉS del movimiento:\n");
        info.append("    • Se requieren: $").append(String.format("%.2f", costoMovimiento)).append(" (").append(mesesRestantes).append(" meses)\n");
        info.append("    • Presupuesto Usado: $").append(String.format("%.2f", nuevoUsadoDestino));
        info.append(" (").append(String.format("%.1f", nuevoPorcentajeDestino)).append("%)\n");
        info.append("    • Presupuesto Disponible: $").append(String.format("%.2f", nuevoDisponibleDestino)).append("\n\n");

        info.append("═══════════════════════════════════════════════════════════════\n");

        if (areaOrigen.getId() == areaDestino.getId()) {
            info.append("⚠️  ERROR: El empleado ya está en el área seleccionada\n");
        } else if (presupuestoDisponibleDestino < costoMovimiento) {
            info.append("❌ MOVIMIENTO NO PERMITIDO:\n");
            info.append("   El área destino NO tiene presupuesto suficiente.\n");
            info.append("   Faltante: $").append(String.format("%.2f", costoMovimiento - presupuestoDisponibleDestino)).append("\n");
        } else {
            info.append("✓  MOVIMIENTO PERMITIDO:\n");
            info.append("   El área destino tiene presupuesto suficiente.\n");
        }

        info.append("═══════════════════════════════════════════════════════════════\n");

        txtInfoPresupuesto.setText(info.toString());
        txtInfoPresupuesto.setCaretPosition(0);
    }
    
    private void realizarMovimiento() {
        int mesIndex = comboMes.getSelectedIndex();
        int empleadoIndex = comboEmpleados.getSelectedIndex();
        int areaDestinoIndex = comboAreaDestino.getSelectedIndex();
        
        if (empleadoIndex < 0 || areaDestinoIndex < 0) {
            JOptionPane.showMessageDialog(this, 
                "Debe seleccionar un empleado y un área destino", 
                "Error de Validación", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int mes = mesIndex + 1;
        Empleado empleado = empleadosArray[empleadoIndex];
        Area areaDestino = areasDestinoArray[areaDestinoIndex];
        Area areaOrigen = empleado.getArea();

        if (areaOrigen.getId() == areaDestino.getId()) {
            JOptionPane.showMessageDialog(this,
                "El empleado ya está en el área destino seleccionada",
                "Error de Validación",
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int mesesRestantes = 13 - mes;
        double costoMovimiento = empleado.getSalarioMensual() * mesesRestantes;

        boolean exito = sistema.moverEmpleado(empleado, areaDestino, mes);

        if (!exito) {
            double presupuestoUsado = 0;
            for (Empleado emp : sistema.getEmpleados()) {
                if (emp.getArea() != null && emp.getArea().getId() == areaDestino.getId()) {
                    presupuestoUsado += emp.getSalarioMensual() * 12;
                }
            }
            double presupuestoDisponible = areaDestino.getPresupuestoAnual() - presupuestoUsado;
            double faltante = costoMovimiento - presupuestoDisponible;

            JOptionPane.showMessageDialog(this,
                "El área destino NO tiene presupuesto suficiente.\n\n" +
                "Presupuesto Anual del Área: $" + String.format("%.2f", (double)areaDestino.getPresupuestoAnual()) + "\n" +
                "Presupuesto Disponible: $" + String.format("%.2f", presupuestoDisponible) + "\n" +
                "Costo del Movimiento: $" + String.format("%.2f", costoMovimiento) + " (" + mesesRestantes + " meses)\n" +
                "Faltante: $" + String.format("%.2f", faltante),
                "Error de Presupuesto",
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        JOptionPane.showMessageDialog(this,
            "Movimiento realizado exitosamente.\n\n" +
            "Empleado: " + empleado.getNombre() + " " + empleado.getApellido() + "\n" +
            "De: " + areaOrigen.getNombre() + "\n" +
            "A: " + areaDestino.getNombre() + "\n" +
            "Mes: " + MESES[mesIndex] + "\n" +
            "Costo: $" + String.format("%.2f", costoMovimiento) + " (" + mesesRestantes + " meses)\n\n" +
            "Los presupuestos han sido ajustados correctamente.",
            "Éxito",
            JOptionPane.INFORMATION_MESSAGE);
        
        dispose();
    }
}
