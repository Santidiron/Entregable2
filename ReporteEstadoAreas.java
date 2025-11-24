//Diego Rocabado - Número de estudiante: 305310
//Santiago Dirón - Número de estudiante: 359644

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

public class ReporteEstadoAreas extends JFrame {
    private Sistema sistema;
    private JList<String> areasList;
    private DefaultListModel<String> areasListModel;
    private JPanel empleadosPanel;
    private JLabel lblAreaInfo;
    private Map<String, AreaData> areasDataMap;

    public ReporteEstadoAreas(Sistema sistema) {
        this.sistema = sistema;
        this.areasDataMap = new HashMap<>();
        initComponents();
    }

    private void initComponents() {
        setTitle("Reporte de Estado de Áreas");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        setSize(1000, 650);

        // Panel principal con dos columnas
        JPanel mainPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // === IZQUIERDA: Lista de áreas ===
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBorder(BorderFactory.createTitledBorder("Áreas"));
        leftPanel.setPreferredSize(new Dimension(250, 0));

        areasListModel = new DefaultListModel<>();
        areasList = new JList<>(areasListModel);
        areasList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        areasList.setCellRenderer(new AreaCellRenderer());
        areasList.setFont(new Font("Arial", Font.PLAIN, 14));

        // Listener para selección de área
        areasList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String selectedArea = areasList.getSelectedValue();
                if (selectedArea != null) {
                    loadEmpleados(selectedArea);
                }
            }
        });

        JScrollPane areasScrollPane = new JScrollPane(areasList);
        leftPanel.add(areasScrollPane, BorderLayout.CENTER);

        // === DERECHA: Grilla de empleados + Etiqueta abajo ===
        JPanel rightPanel = new JPanel(new BorderLayout(5, 5));
        rightPanel.setBorder(BorderFactory.createTitledBorder("Empleados"));

        // Panel de empleados - ARRIBA (layout se configura dinámicamente)
        empleadosPanel = new JPanel();
        empleadosPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        JScrollPane empleadosScrollPane = new JScrollPane(empleadosPanel);
        rightPanel.add(empleadosScrollPane, BorderLayout.CENTER);

        // Etiqueta con información del área - ABAJO
        lblAreaInfo = new JLabel("Seleccione un área para ver sus empleados", SwingConstants.CENTER);
        lblAreaInfo.setFont(new Font("Arial", Font.BOLD, 13));
        lblAreaInfo.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(1, 0, 0, 0, Color.GRAY),
            BorderFactory.createEmptyBorder(8, 5, 8, 5)
        ));
        lblAreaInfo.setOpaque(true);
        lblAreaInfo.setBackground(new Color(240, 240, 240));
        rightPanel.add(lblAreaInfo, BorderLayout.SOUTH);

        // Agregar paneles al panel principal
        mainPanel.add(leftPanel);
        mainPanel.add(rightPanel);

        add(mainPanel, BorderLayout.CENTER);

        // Cargar datos
        loadAreas();

        setLocationRelativeTo(null);
    }

    private void loadAreas() {
        areasListModel.clear();
        areasDataMap.clear();

        // Crear lista para ordenar por porcentaje
        List<AreaData> areaDataList = new ArrayList<>();

        for (Area area : sistema.getAreas()) {
            if (area != null) {
                double presupuestoUsado = calcularPresupuestoUsado(area);
                double porcentaje = area.getPresupuestoAnual() > 0 ?
                    (presupuestoUsado / area.getPresupuestoAnual()) * 100 : 0;

                AreaData ad = new AreaData();
                ad.area = area;
                ad.presupuestoUsado = presupuestoUsado;
                ad.porcentaje = porcentaje;
                areaDataList.add(ad);

                areasDataMap.put(area.getNombre(), ad);
            }
        }

        // Ordenar por porcentaje DECRECIENTE
        areaDataList.sort((a, b) -> Double.compare(b.porcentaje, a.porcentaje));

        // Agregar a la lista
        for (AreaData ad : areaDataList) {
            areasListModel.addElement(ad.area.getNombre());
        }
    }

    private void loadEmpleados(String areaNombre) {
        empleadosPanel.removeAll();
        empleadosPanel.setLayout(new GridBagLayout());

        AreaData areaData = areasDataMap.get(areaNombre);
        if (areaData == null) return;

        Area area = areaData.area;

        // Actualizar etiqueta con información del área
        lblAreaInfo.setText(String.format("Área seleccionada: %s — Presupuesto asignado: %.2f%%",
            area.getNombre(), areaData.porcentaje));

        // Obtener empleados del área
        List<Empleado> empleadosList = new ArrayList<>();
        for (Empleado emp : sistema.getEmpleados()) {
            if (emp != null && emp.getArea() != null && emp.getArea().getId() == area.getId()) {
                empleadosList.add(emp);
            }
        }

        if (!empleadosList.isEmpty()) {
            // Ordenar empleados alfabéticamente por nombre
            empleadosList.sort((a, b) -> a.getNombre().compareToIgnoreCase(b.getNombre()));

            // Calcular rango de salarios para colorear (escala negro a azul)
            double minSalario = empleadosList.stream()
                .mapToDouble(Empleado::getSalarioMensual)
                .min().orElse(0);
            double maxSalario = empleadosList.stream()
                .mapToDouble(Empleado::getSalarioMensual)
                .max().orElse(1);

            // Configurar GridBagConstraints para control total del espaciado
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(3, 3, 3, 3); // Espaciado mínimo: 3px en todos lados
            gbc.anchor = GridBagConstraints.NORTHWEST;
            gbc.fill = GridBagConstraints.NONE;

            int fila = 0;
            int columna = 0;

            for (Empleado emp : empleadosList) {
                // Crear un botón personalizado con bordes redondeados
                JButton btnEmpleado = new JButton(emp.getNombre()) {
                    @Override
                    protected void paintComponent(Graphics g) {
                        Graphics2D g2 = (Graphics2D) g.create();
                        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                        // Dibujar fondo con bordes redondeados
                        g2.setColor(getBackground());
                        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);

                        // Dibujar texto
                        g2.setColor(getForeground());
                        g2.setFont(getFont());
                        FontMetrics fm = g2.getFontMetrics();
                        int textX = (getWidth() - fm.stringWidth(getText())) / 2;
                        int textY = ((getHeight() - fm.getHeight()) / 2) + fm.getAscent();
                        g2.drawString(getText(), textX, textY);

                        g2.dispose();
                    }

                    @Override
                    protected void paintBorder(Graphics g) {
                        Graphics2D g2 = (Graphics2D) g.create();
                        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                        g2.setColor(Color.GRAY);
                        g2.setStroke(new BasicStroke(1.5f));
                        g2.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, 20, 20);
                        g2.dispose();
                    }
                };

                btnEmpleado.setPreferredSize(new Dimension(160, 45));
                btnEmpleado.setFont(new Font("Arial", Font.PLAIN, 12));

                // Calcular color en escala negro (mínimo) a azul (máximo)
                double ratio = (maxSalario > minSalario) ?
                    (emp.getSalarioMensual() - minSalario) / (maxSalario - minSalario) : 0.5;

                // Escala de negro (0,0,0) a azul (0,0,255)
                int blue = (int) (ratio * 255);

                btnEmpleado.setBackground(new Color(0, 0, blue));
                btnEmpleado.setForeground(Color.WHITE);
                btnEmpleado.setFocusPainted(false);
                btnEmpleado.setContentAreaFilled(false);
                btnEmpleado.setOpaque(false);
                btnEmpleado.setBorderPainted(false);
                btnEmpleado.setCursor(new Cursor(Cursor.HAND_CURSOR));

                // Agregar listener para mostrar detalles
                btnEmpleado.addActionListener(e -> mostrarDetallesEmpleado(emp));

                // Posicionar en la grilla
                gbc.gridx = columna;
                gbc.gridy = fila;
                empleadosPanel.add(btnEmpleado, gbc);

                // Avanzar a la siguiente columna, máximo 3 por fila
                columna++;
                if (columna >= 3) {
                    columna = 0;
                    fila++;
                }
            }

            // Agregar componente invisible al final para empujar todo hacia arriba
            gbc.gridx = 0;
            gbc.gridy = fila + 1;
            gbc.weighty = 1.0;
            gbc.fill = GridBagConstraints.BOTH;
            empleadosPanel.add(Box.createGlue(), gbc);

        } else {
            JLabel lblNoEmpleados = new JLabel("No hay empleados en esta área");
            lblNoEmpleados.setHorizontalAlignment(SwingConstants.CENTER);
            empleadosPanel.add(lblNoEmpleados);
        }

        empleadosPanel.revalidate();
        empleadosPanel.repaint();
    }

    private double calcularPresupuestoUsado(Area area) {
        double total = 0;
        // Obtener empleados del área desde el sistema
        for (Empleado emp : sistema.getEmpleados()) {
            if (emp != null && emp.getArea() != null && emp.getArea().getId() == area.getId()) {
                total += emp.getSalarioMensual() * 12; // Salario anual
            }
        }
        return total;
    }

    private void mostrarDetallesEmpleado(Empleado empleado) {
        String detalles = String.format(
            "═══════════════════════════════\n" +
            "    INFORMACIÓN DEL EMPLEADO\n" +
            "═══════════════════════════════\n\n" +
            "Legajo: %d\n" +
            "Nombre: %s %s\n" +
            "Cédula: %s\n" +
            "Celular: %s\n" +
            "Salario Mensual: $%,.2f\n" +
            "Antigüedad: %d años\n" +
            "Manager: %s\n" +
            "Área: %s",
            empleado.getLegajo(),
            empleado.getNombre(),
            empleado.getApellido(),
            empleado.getCedula(),
            empleado.getCelular(),
            empleado.getSalarioMensual(),
            empleado.getAntiguedad(),
            empleado.getManager() != null ? empleado.getManager().getNombre() : "Sin Manager",
            empleado.getArea() != null ? empleado.getArea().getNombre() : "Sin Área"
        );

        JTextArea textArea = new JTextArea(detalles);
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));

        JOptionPane.showMessageDialog(
            this,
            textArea,
            "Detalles del Empleado - " + empleado.getNombre(),
            JOptionPane.INFORMATION_MESSAGE
        );
    }

    // Clase auxiliar para almacenar datos de área
    private static class AreaData {
        Area area;
        double presupuestoUsado;
        double porcentaje;
    }

    // Renderer personalizado para la lista de áreas (con colores según porcentaje)
    private class AreaCellRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value,
                int index, boolean isSelected, boolean cellHasFocus) {
            JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            label.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
            ));
            label.setFont(new Font("Arial", Font.PLAIN, 13));

            if (value != null && !isSelected) {
                String areaNombre = value.toString();
                AreaData areaData = areasDataMap.get(areaNombre);

                if (areaData != null) {
                    double porcentaje = areaData.porcentaje;

                    // Aplicar colores según el porcentaje
                    if (porcentaje > 90) {
                        label.setBackground(Color.RED);
                        label.setForeground(Color.WHITE);
                    } else if (porcentaje >= 70) {
                        label.setBackground(Color.YELLOW);
                        label.setForeground(Color.BLACK);
                    } else {
                        label.setBackground(Color.LIGHT_GRAY);
                        label.setForeground(Color.BLACK);
                    }
                    label.setOpaque(true);
                }
            }

            return label;
        }
    }

    // Método main para pruebas
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Crear datos de prueba
            Sistema sistema = createTestData();

            ReporteEstadoAreas frame = new ReporteEstadoAreas(sistema);
            frame.setVisible(true);
        });
    }

    private static Sistema createTestData() {
        Sistema sistema = new Sistema();

        // Crear áreas
        Area areaIT = new Area(1, "Tecnología", "Área de desarrollo y sistemas", 400000, null);
        Area areaVentas = new Area(2, "Ventas", "Área comercial", 200000, null);
        Area areaRRHH = new Area(3, "Recursos Humanos", "Área de gestión de personal", 180000, null);

        sistema.agregarArea(areaIT);
        sistema.agregarArea(areaVentas);
        sistema.agregarArea(areaRRHH);

        // Crear managers
        Manager manager1 = new Manager("Juan Pérez", "1.234.567-8", "099-000-001", 5, areaIT, null);
        Manager manager2 = new Manager("María García", "2.345.678-9", "099-000-002", 8, areaVentas, null);
        Manager manager3 = new Manager("Carlos López", "3.456.789-0", "099-000-003", 3, areaRRHH, null);

        sistema.agregarManager(manager1);
        sistema.agregarManager(manager2);
        sistema.agregarManager(manager3);

        // Crear empleados para Área 1 - IT
        sistema.agregarEmpleado(new Empleado(1001, "Ana", "Martínez", "4.567.890-1", "099-111-001", null, 2, 5000, manager1, areaIT));
        sistema.agregarEmpleado(new Empleado(1002, "Pedro", "Sánchez", "5.678.901-2", "099-111-002", null, 4, 6500, manager1, areaIT));
        sistema.agregarEmpleado(new Empleado(1003, "Laura", "Rodríguez", "6.789.012-3", "099-111-003", null, 1, 4500, manager1, areaIT));
        sistema.agregarEmpleado(new Empleado(1004, "Diego", "Fernández", "7.890.123-4", "099-111-004", null, 6, 7500, manager1, areaIT));
        sistema.agregarEmpleado(new Empleado(1005, "Sofia", "González", "8.901.234-5", "099-111-005", null, 3, 5500, manager1, areaIT));

        // Crear empleados para Área 2 - Ventas
        sistema.agregarEmpleado(new Empleado(2001, "Roberto", "Díaz", "9.012.345-6", "099-222-001", null, 5, 4000, manager2, areaVentas));
        sistema.agregarEmpleado(new Empleado(2002, "Carmen", "Torres", "1.023.456-7", "099-222-002", null, 2, 3500, manager2, areaVentas));
        sistema.agregarEmpleado(new Empleado(2003, "Miguel", "Ramírez", "1.234.560-8", "099-222-003", null, 7, 5000, manager2, areaVentas));
        sistema.agregarEmpleado(new Empleado(2004, "Elena", "Castro", "2.345.601-9", "099-222-004", null, 1, 3000, manager2, areaVentas));

        // Crear empleados para Área 3 - RRHH
        sistema.agregarEmpleado(new Empleado(3001, "Fernando", "Morales", "3.456.012-0", "099-333-001", null, 4, 4200, manager3, areaRRHH));
        sistema.agregarEmpleado(new Empleado(3002, "Patricia", "Vega", "4.560.123-1", "099-333-002", null, 6, 4800, manager3, areaRRHH));
        sistema.agregarEmpleado(new Empleado(3003, "Javier", "Ruiz", "5.601.234-2", "099-333-003", null, 2, 3800, manager3, areaRRHH));

        return sistema;
    }
}

