//Diego Rocabado - Número de estudiante: 305310
//Santiago Dirón - Número de estudiante: 359644

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ReporteMovimientos extends JFrame {
    private JTable tableMovimientos;
    private DefaultTableModel tableModel;
    private JComboBox<String> comboMes;
    private JComboBox<String> comboArea;
    private JTextField txtEmpleado;
    private JButton btnAplicarFiltros;
    private JButton btnLimpiarFiltros;
    private JButton btnExportarCSV;
    
    private List<Movimiento> movimientos;
    private List<Movimiento> movimientosFiltrados;
    private List<Area> areas;
    
    public ReporteMovimientos(List<Movimiento> movimientos, List<Area> areas) {
        this.movimientos = movimientos != null ? movimientos : new ArrayList<>();
        this.areas = areas != null ? areas : new ArrayList<>();
        this.movimientosFiltrados = new ArrayList<>(this.movimientos);
        
        initComponents();
        cargarDatos();
    }
    
    private void initComponents() {
        setTitle("Reporte de Movimientos");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Panel principal
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Panel de filtros
        JPanel panelFiltros = crearPanelFiltros();
        mainPanel.add(panelFiltros, BorderLayout.NORTH);
        
        // Tabla de movimientos
        String[] columnas = {"Mes", "Área Origen", "Área Destino", "Empleado", "Fecha"};
        tableModel = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableMovimientos = new JTable(tableModel);
        tableMovimientos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableMovimientos.setAutoCreateRowSorter(true);
        
        JScrollPane scrollPane = new JScrollPane(tableMovimientos);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        
        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnExportarCSV = new JButton("Exportar a CSV");
        btnExportarCSV.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exportarCSV();
            }
        });
        panelBotones.add(btnExportarCSV);
        mainPanel.add(panelBotones, BorderLayout.SOUTH);
        
        add(mainPanel);
    }
    
    private JPanel crearPanelFiltros() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Filtros"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Filtro por mes
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Mes:"), gbc);
        
        gbc.gridx = 1;
        String[] meses = {"Todos", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
        comboMes = new JComboBox<>(meses);
        panel.add(comboMes, gbc);
        
        // Filtro por área
        gbc.gridx = 2;
        gbc.gridy = 0;
        panel.add(new JLabel("Área:"), gbc);
        
        gbc.gridx = 3;
        comboArea = new JComboBox<>();
        comboArea.addItem("Todas");
        for (Area area : areas) {
            comboArea.addItem(area.getNombre());
        }
        panel.add(comboArea, gbc);
        
        // Filtro por empleado
        gbc.gridx = 4;
        gbc.gridy = 0;
        panel.add(new JLabel("Empleado:"), gbc);
        
        gbc.gridx = 5;
        txtEmpleado = new JTextField(15);
        panel.add(txtEmpleado, gbc);
        
        // Botones
        gbc.gridx = 6;
        gbc.gridy = 0;
        btnAplicarFiltros = new JButton("Aplicar Filtros");
        btnAplicarFiltros.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                aplicarFiltros();
            }
        });
        panel.add(btnAplicarFiltros, gbc);
        
        gbc.gridx = 7;
        btnLimpiarFiltros = new JButton("Limpiar Filtros");
        btnLimpiarFiltros.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarFiltros();
            }
        });
        panel.add(btnLimpiarFiltros, gbc);
        
        return panel;
    }
    
    private void cargarDatos() {
        // Ordenar por mes (más reciente primero)
        movimientosFiltrados.sort(new Comparator<Movimiento>() {
            @Override
            public int compare(Movimiento m1, Movimiento m2) {
                return Integer.compare(m2.getMes(), m1.getMes());
            }
        });
        
        actualizarTabla();
    }
    
    private void actualizarTabla() {
        tableModel.setRowCount(0);
        
        for (Movimiento mov : movimientosFiltrados) {
            String areaOrigen = mov.getAreaOrigen() != null ? mov.getAreaOrigen().getNombre() : "N/A";
            String areaDestino = mov.getAreaDestino() != null ? mov.getAreaDestino().getNombre() : "N/A";
            String empleado = mov.getEmpleado() != null ? 
                mov.getEmpleado().getNombre() + " " + mov.getEmpleado().getApellido() : "N/A";
            
            Object[] fila = {
                mov.getMes(),
                areaOrigen,
                areaDestino,
                empleado,
                mov.getFecha()
            };
            tableModel.addRow(fila);
        }
    }
    
    private void aplicarFiltros() {
        movimientosFiltrados = new ArrayList<>();
        
        String mesSeleccionado = (String) comboMes.getSelectedItem();
        String areaSeleccionada = (String) comboArea.getSelectedItem();
        String empleadoBuscado = txtEmpleado.getText().trim().toLowerCase();
        
        for (Movimiento mov : movimientos) {
            boolean cumpleMes = true;
            boolean cumpleArea = true;
            boolean cumpleEmpleado = true;
            
            // Filtro por mes
            if (!"Todos".equals(mesSeleccionado)) {
                int mes = Integer.parseInt(mesSeleccionado);
                cumpleMes = mov.getMes() == mes;
            }
            
            // Filtro por área (origen o destino)
            if (!"Todas".equals(areaSeleccionada)) {
                String areaOrigen = mov.getAreaOrigen() != null ? mov.getAreaOrigen().getNombre() : "";
                String areaDestino = mov.getAreaDestino() != null ? mov.getAreaDestino().getNombre() : "";
                cumpleArea = areaOrigen.equals(areaSeleccionada) || areaDestino.equals(areaSeleccionada);
            }
            
            // Filtro por empleado
            if (!empleadoBuscado.isEmpty() && mov.getEmpleado() != null) {
                String nombreCompleto = (mov.getEmpleado().getNombre() + " " + 
                    mov.getEmpleado().getApellido()).toLowerCase();
                cumpleEmpleado = nombreCompleto.contains(empleadoBuscado);
            }
            
            if (cumpleMes && cumpleArea && cumpleEmpleado) {
                movimientosFiltrados.add(mov);
            }
        }
        
        // Ordenar por mes (más reciente primero)
        movimientosFiltrados.sort(new Comparator<Movimiento>() {
            @Override
            public int compare(Movimiento m1, Movimiento m2) {
                return Integer.compare(m2.getMes(), m1.getMes());
            }
        });
        
        actualizarTabla();
    }
    
    private void limpiarFiltros() {
        comboMes.setSelectedIndex(0);
        comboArea.setSelectedIndex(0);
        txtEmpleado.setText("");
        movimientosFiltrados = new ArrayList<>(movimientos);
        cargarDatos();
    }
    
    private void exportarCSV() {
        // Validar que hay datos para exportar
        if (movimientosFiltrados.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "No hay datos para exportar.",
                "Advertencia",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Abrir JFileChooser
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar archivo CSV");
        fileChooser.setSelectedFile(new File("movimientos.csv"));
        
        int userSelection = fileChooser.showSaveDialog(this);
        
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            
            // Agregar extensión .csv si no la tiene
            if (!fileToSave.getName().toLowerCase().endsWith(".csv")) {
                fileToSave = new File(fileToSave.getAbsolutePath() + ".csv");
            }
            
            try {
                escribirCSV(fileToSave);
                JOptionPane.showMessageDialog(this,
                    "Archivo exportado exitosamente en:\n" + fileToSave.getAbsolutePath(),
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this,
                    "Error al escribir el archivo:\n" + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void escribirCSV(File archivo) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter(archivo, StandardCharsets.UTF_8))) {
            
            // Escribir encabezados
            writer.write("Mes,Area Origen,Area Destino,Empleado,Fecha");
            writer.newLine();
            
            // Escribir datos
            for (Movimiento mov : movimientosFiltrados) {
                String areaOrigen = mov.getAreaOrigen() != null ? 
                    escaparCSV(mov.getAreaOrigen().getNombre()) : "N/A";
                String areaDestino = mov.getAreaDestino() != null ? 
                    escaparCSV(mov.getAreaDestino().getNombre()) : "N/A";
                String empleado = mov.getEmpleado() != null ? 
                    escaparCSV(mov.getEmpleado().getNombre() + " " + mov.getEmpleado().getApellido()) : "N/A";
                String fecha = escaparCSV(mov.getFecha());
                
                writer.write(String.format("%d,%s,%s,%s,%s",
                    mov.getMes(), areaOrigen, areaDestino, empleado, fecha));
                writer.newLine();
            }
        }
    }
    
    private String escaparCSV(String valor) {
        if (valor == null) {
            return "";
        }
        // Si el valor contiene comas, comillas o saltos de línea, encerrarlo entre comillas
        if (valor.contains(",") || valor.contains("\"") || valor.contains("\n")) {
            // Duplicar las comillas dentro del valor
            valor = valor.replace("\"", "\"\"");
            return "\"" + valor + "\"";
        }
        return valor;
    }
    
    // Método main para pruebas
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Crear datos de prueba
                List<Area> areas = new ArrayList<>();
                Area area1 = new Area(1, "Desarrollo", "Area de desarrollo", 100000, null);
                Area area2 = new Area(2, "Testing", "Area de testing", 80000, null);
                Area area3 = new Area(3, "Recursos Humanos", "Area de RRHH", 90000, null);
                areas.add(area1);
                areas.add(area2);
                areas.add(area3);
                
                List<Movimiento> movimientos = new ArrayList<>();
                
                Empleado emp1 = new Empleado(1, "Juan", "Pérez", "12345678", "555-1234", 
                    "/cv/juan.pdf", 5, 50000, null, area1);
                Empleado emp2 = new Empleado(2, "María", "García", "87654321", "555-5678", 
                    "/cv/maria.pdf", 3, 45000, null, area2);
                Empleado emp3 = new Empleado(3, "Carlos", "López", "11223344", "555-9999", 
                    "/cv/carlos.pdf", 7, 60000, null, area1);
                
                movimientos.add(new Movimiento(12, "2024-12-15", emp1, area1, area2));
                movimientos.add(new Movimiento(11, "2024-11-20", emp2, area2, area3));
                movimientos.add(new Movimiento(10, "2024-10-05", emp3, area1, area3));
                movimientos.add(new Movimiento(12, "2024-12-01", emp2, area3, area1));
                movimientos.add(new Movimiento(9, "2024-09-15", emp1, area2, area1));
                
                ReporteMovimientos frame = new ReporteMovimientos(movimientos, areas);
                frame.setVisible(true);
            }
        });
    }
}
