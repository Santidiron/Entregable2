//Diego Rocabado - Número de estudiante: 305310
//Santiago Dirón - Número de estudiante: 359644

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReporteInteligenteWindow extends JFrame {
    
    private JComboBox<String> comboEmpleados;
    private JTextArea areaEmpleadoInfo;
    private List<JCheckBox> areaCheckboxes;
    private JButton btnGenerar;
    private JButton btnGuardar;
    private JTextArea areaResultado;
    private JLabel lblLoading;
    
    private List<Empleado> empleados;
    private List<Area> areas;
    private GeminiService geminiService;
    
    public ReporteInteligenteWindow(List<Empleado> empleados, List<Area> areas) {
        this.empleados = empleados;
        this.areas = areas;
        this.geminiService = new GeminiService();
        this.areaCheckboxes = new ArrayList<>();
        
        initializeUI();
    }
    
    private void initializeUI() {
        setTitle("Reporte Inteligente - Análisis de Transición de Empleados");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(null);
        
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        
        JPanel topPanel = createTopPanel();
        
        JPanel centerPanel = createCenterPanel();
        
        JPanel bottomPanel = createBottomPanel();
        
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
        
        if (!geminiService.isAPIKeyConfigured()) {
            JOptionPane.showMessageDialog(this,
                "Advertencia: La API Key de Gemini no está configurada.\n" +
                "El sistema funcionará en modo fallback sin análisis de IA.\n" +
                "Configure la variable de entorno GEMINI_API_KEY para habilitar la IA.",
                "API Key No Configurada",
                JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private JPanel createTopPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        
        JPanel selectionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        selectionPanel.add(new JLabel("Seleccionar Empleado:"));
        
        comboEmpleados = new JComboBox<>();
        for (Empleado emp : empleados) {
            comboEmpleados.addItem(emp.getNombre() + " " + emp.getApellido() + " (Legajo: " + emp.getLegajo() + ")");
        }
        comboEmpleados.setPreferredSize(new Dimension(300, 25));
        comboEmpleados.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarInfoEmpleado();
            }
        });
        selectionPanel.add(comboEmpleados);
        
        JPanel infoPanel = new JPanel(new BorderLayout());
        infoPanel.setBorder(new TitledBorder("Información del Empleado"));
        
        areaEmpleadoInfo = new JTextArea(5, 40);
        areaEmpleadoInfo.setEditable(false);
        areaEmpleadoInfo.setBackground(new Color(240, 240, 240));
        areaEmpleadoInfo.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollInfo = new JScrollPane(areaEmpleadoInfo);
        infoPanel.add(scrollInfo, BorderLayout.CENTER);
        
        panel.add(selectionPanel, BorderLayout.NORTH);
        panel.add(infoPanel, BorderLayout.CENTER);
        
        if (empleados.size() > 0) {
            actualizarInfoEmpleado();
        }
        
        return panel;
    }
    
    private JPanel createCenterPanel() {
        JPanel panel = new JPanel(new GridLayout(1, 2, 10, 0));
        
        JPanel areasPanel = new JPanel(new BorderLayout());
        areasPanel.setBorder(new TitledBorder("Áreas a Analizar"));
        
        JPanel checkboxPanel = new JPanel();
        checkboxPanel.setLayout(new BoxLayout(checkboxPanel, BoxLayout.Y_AXIS));
        
        for (Area area : areas) {
            JCheckBox checkbox = new JCheckBox(area.getNombre());
            checkbox.setSelected(true);
            areaCheckboxes.add(checkbox);
            
            JPanel itemPanel = new JPanel(new BorderLayout());
            itemPanel.add(checkbox, BorderLayout.NORTH);
            
            JTextArea descArea = new JTextArea("  " + area.getDescripcion());
            descArea.setEditable(false);
            descArea.setLineWrap(true);
            descArea.setWrapStyleWord(true);
            descArea.setBackground(checkboxPanel.getBackground());
            descArea.setFont(new Font("SansSerif", Font.PLAIN, 11));
            itemPanel.add(descArea, BorderLayout.CENTER);
            
            checkboxPanel.add(itemPanel);
            checkboxPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        }
        
        JScrollPane scrollAreas = new JScrollPane(checkboxPanel);
        areasPanel.add(scrollAreas, BorderLayout.CENTER);
        
        JPanel resultsPanel = new JPanel(new BorderLayout());
        resultsPanel.setBorder(new TitledBorder("Resultado del Análisis"));
        
        areaResultado = new JTextArea();
        areaResultado.setEditable(false);
        areaResultado.setLineWrap(true);
        areaResultado.setWrapStyleWord(true);
        areaResultado.setFont(new Font("SansSerif", Font.PLAIN, 12));
        JScrollPane scrollResultado = new JScrollPane(areaResultado);
        resultsPanel.add(scrollResultado, BorderLayout.CENTER);
        
        lblLoading = new JLabel("⏱ Procesando...", SwingConstants.CENTER);
        lblLoading.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblLoading.setForeground(new Color(0, 100, 200));
        lblLoading.setVisible(false);
        resultsPanel.add(lblLoading, BorderLayout.NORTH);
        
        panel.add(areasPanel);
        panel.add(resultsPanel);
        
        return panel;
    }
    
    private JPanel createBottomPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        
        btnGenerar = new JButton("🤖 Generar Reporte con IA");
        btnGenerar.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnGenerar.setPreferredSize(new Dimension(250, 40));
        btnGenerar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generarReporte();
            }
        });
        
        btnGuardar = new JButton("💾 Guardar Reporte");
        btnGuardar.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnGuardar.setPreferredSize(new Dimension(200, 40));
        btnGuardar.setEnabled(false);
        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarReporte();
            }
        });
        
        panel.add(btnGenerar);
        panel.add(btnGuardar);
        
        return panel;
    }
    
    private void actualizarInfoEmpleado() {
        int selectedIndex = comboEmpleados.getSelectedIndex();
        if (selectedIndex >= 0 && selectedIndex < empleados.size()) {
            Empleado emp = empleados.get(selectedIndex);
            
            StringBuilder info = new StringBuilder();
            info.append("Legajo: ").append(emp.getLegajo()).append("\n");
            info.append("Nombre: ").append(emp.getNombre()).append(" ").append(emp.getApellido()).append("\n");
            info.append("Cédula: ").append(emp.getCedula()).append("\n");
            info.append("Celular: ").append(emp.getCelular()).append("\n");
            info.append("Antigüedad: ").append(emp.getAntiguedad()).append(" años\n");
            info.append("Salario: $").append(String.format("%.2f", emp.getSalarioMensual())).append("\n");
            
            if (emp.getArea() != null) {
                info.append("Área actual: ").append(emp.getArea().getNombre()).append("\n");
            }
            
            if (emp.getManager() != null) {
                info.append("Manager: ").append(emp.getManager().getNombre()).append("\n");
            }
            
            info.append("CV: ").append(emp.getPathCV() != null ? emp.getPathCV() : "No especificado");
            
            areaEmpleadoInfo.setText(info.toString());
        }
    }
    
    private void generarReporte() {
        int selectedIndex = comboEmpleados.getSelectedIndex();
        if (selectedIndex < 0 || selectedIndex >= empleados.size()) {
            JOptionPane.showMessageDialog(this,
                "Por favor, seleccione un empleado.",
                "Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        List<Area> areasSeleccionadas = new ArrayList<>();
        for (int i = 0; i < areaCheckboxes.size(); i++) {
            if (areaCheckboxes.get(i).isSelected()) {
                areasSeleccionadas.add(areas.get(i));
            }
        }
        
        if (areasSeleccionadas.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Por favor, seleccione al menos un área para analizar.",
                "Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        Empleado empleadoSeleccionado = empleados.get(selectedIndex);
        
        btnGenerar.setEnabled(false);
        btnGuardar.setEnabled(false);
        lblLoading.setVisible(true);
        areaResultado.setText("Generando reporte...\n\nEsto puede tomar unos segundos...");
        
        SwingWorker<String, Void> worker = new SwingWorker<String, Void>() {
            @Override
            protected String doInBackground() throws Exception {
                return geminiService.generarReporteInteligente(empleadoSeleccionado, areasSeleccionadas);
            }
            
            @Override
            protected void done() {
                try {
                    String resultado = get();
                    areaResultado.setText(resultado);
                    areaResultado.setCaretPosition(0);
                    btnGuardar.setEnabled(true);
                } catch (Exception e) {
                    areaResultado.setText("Error al generar el reporte: " + e.getMessage());
                } finally {
                    lblLoading.setVisible(false);
                    btnGenerar.setEnabled(true);
                }
            }
        };
        
        worker.execute();
    }
    
    private void guardarReporte() {
        String contenido = areaResultado.getText();
        if (contenido == null || contenido.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "No hay contenido para guardar.",
                "Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar Reporte");
        fileChooser.setSelectedFile(new java.io.File("reporte_inteligente.txt"));
        
        int userSelection = fileChooser.showSaveDialog(this);
        
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            java.io.File fileToSave = fileChooser.getSelectedFile();
            
            try (FileWriter writer = new FileWriter(fileToSave)) {
                writer.write(contenido);
                JOptionPane.showMessageDialog(this,
                    "Reporte guardado exitosamente en:\n" + fileToSave.getAbsolutePath(),
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this,
                    "Error al guardar el archivo: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    public static void main(String[] args) {
        List<Empleado> empleados = new ArrayList<>();
        List<Area> areas = new ArrayList<>();
        
        Area area1 = new Area(1, "Desarrollo", "Área de desarrollo de software", 500000, new Empleado[0]);
        Area area2 = new Area(2, "Marketing", "Área de marketing y ventas", 300000, new Empleado[0]);
        Area area3 = new Area(3, "Recursos Humanos", "Área de gestión de personal", 250000, new Empleado[0]);
        areas.add(area1);
        areas.add(area2);
        areas.add(area3);
        
        Empleado emp1 = new Empleado(1001, "Juan", "Pérez", "12345678", "555-1234", 
                                      "/ruta/cv1.txt", 5, 50000.0, null, area1);
        Empleado emp2 = new Empleado(1002, "María", "González", "87654321", "555-5678",
                                      "/ruta/cv2.txt", 3, 45000.0, null, area2);
        empleados.add(emp1);
        empleados.add(emp2);
        
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                ReporteInteligenteWindow window = new ReporteInteligenteWindow(empleados, areas);
                window.setVisible(true);
            }
        });
    }
}
