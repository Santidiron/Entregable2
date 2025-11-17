//Diego Rocabado
//Santiago Dirón

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.*;

public class ReporteEstadoAreas extends JFrame {
    private JTable areasTable;
    private JTable empleadosTable;
    private DefaultTableModel areasTableModel;
    private DefaultTableModel empleadosTableModel;
    private Area[] areas;
    
    public ReporteEstadoAreas(Area[] areas) {
        this.areas = areas;
        initComponents();
    }
    
    private void initComponents() {
        setTitle("Reporte de Estado de Áreas");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(1000, 700);
        
        // Panel principal dividido
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitPane.setDividerLocation(300);
        
        // Panel superior: Tabla de Áreas
        JPanel areasPanel = new JPanel(new BorderLayout());
        areasPanel.setBorder(BorderFactory.createTitledBorder("Áreas"));
        
        String[] areasColumns = {"Nombre", "Presupuesto Anual", "Presupuesto Usado", "Porcentaje Usado"};
        areasTableModel = new DefaultTableModel(areasColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        areasTable = new JTable(areasTableModel);
        areasTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        areasTable.setRowHeight(25);
        
        // Renderizador personalizado para colorear las filas
        areasTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                
                if (!isSelected) {
                    // Obtener el porcentaje de la fila
                    String porcentajeStr = (String) table.getValueAt(row, 3);
                    double porcentaje = Double.parseDouble(porcentajeStr.replace("%", ""));
                    
                    if (porcentaje > 90) {
                        c.setBackground(new Color(255, 200, 200)); // Rojo claro
                    } else if (porcentaje >= 70) {
                        c.setBackground(new Color(255, 255, 150)); // Amarillo
                    } else {
                        c.setBackground(new Color(200, 255, 200)); // Verde claro
                    }
                } else {
                    c.setBackground(table.getSelectionBackground());
                }
                
                return c;
            }
        });
        
        // Listener para selección de área
        areasTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = areasTable.getSelectedRow();
                if (selectedRow >= 0) {
                    String areaName = (String) areasTable.getValueAt(selectedRow, 0);
                    Area selectedArea = findAreaByName(areaName);
                    if (selectedArea != null) {
                        loadEmpleados(selectedArea);
                    }
                }
            }
        });
        
        JScrollPane areasScrollPane = new JScrollPane(areasTable);
        areasPanel.add(areasScrollPane, BorderLayout.CENTER);
        
        // Panel inferior: Tabla de Empleados
        JPanel empleadosPanel = new JPanel(new BorderLayout());
        empleadosPanel.setBorder(BorderFactory.createTitledBorder("Empleados del Área Seleccionada"));
        
        String[] empleadosColumns = {"Legajo", "Nombre", "Apellido", "Salario", "Manager"};
        empleadosTableModel = new DefaultTableModel(empleadosColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        empleadosTable = new JTable(empleadosTableModel);
        empleadosTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        empleadosTable.setRowHeight(25);
        
        // Listener para selección de empleado
        empleadosTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = empleadosTable.getSelectedRow();
                if (selectedRow >= 0) {
                    int legajo = (Integer) empleadosTable.getValueAt(selectedRow, 0);
                    Empleado selectedEmpleado = findEmpleadoByLegajo(legajo);
                    if (selectedEmpleado != null) {
                        showEmpleadoDetails(selectedEmpleado);
                    }
                }
            }
        });
        
        JScrollPane empleadosScrollPane = new JScrollPane(empleadosTable);
        empleadosPanel.add(empleadosScrollPane, BorderLayout.CENTER);
        
        splitPane.setTopComponent(areasPanel);
        splitPane.setBottomComponent(empleadosPanel);
        
        add(splitPane, BorderLayout.CENTER);
        
        // Cargar datos de áreas
        loadAreas();
        
        setLocationRelativeTo(null);
    }
    
    private void loadAreas() {
        areasTableModel.setRowCount(0);
        
        // Crear lista para ordenar por porcentaje
        java.util.List<AreaData> areaDataList = new ArrayList<>();
        
        for (Area area : areas) {
            if (area != null) {
                double presupuestoUsado = calcularPresupuestoUsado(area);
                double porcentaje = (presupuestoUsado / area.getPresupuestoAnual()) * 100;
                
                AreaData ad = new AreaData();
                ad.area = area;
                ad.presupuestoUsado = presupuestoUsado;
                ad.porcentaje = porcentaje;
                areaDataList.add(ad);
            }
        }
        
        // Ordenar por porcentaje decreciente
        areaDataList.sort((a, b) -> Double.compare(b.porcentaje, a.porcentaje));
        
        // Agregar a la tabla
        for (AreaData ad : areaDataList) {
            Object[] row = {
                ad.area.getNombre(),
                String.format("$%,.2f", (double)ad.area.getPresupuestoAnual()),
                String.format("$%,.2f", ad.presupuestoUsado),
                String.format("%.2f%%", ad.porcentaje)
            };
            areasTableModel.addRow(row);
        }
    }
    
    private double calcularPresupuestoUsado(Area area) {
        double total = 0;
        Empleado[] empleados = area.getEmpleados();
        if (empleados != null) {
            for (Empleado emp : empleados) {
                if (emp != null) {
                    total += emp.getSalarioMensual() * 12;
                }
            }
        }
        return total;
    }
    
    private void loadEmpleados(Area area) {
        empleadosTableModel.setRowCount(0);
        
        Empleado[] empleados = area.getEmpleados();
        if (empleados != null && empleados.length > 0) {
            // Ordenar empleados alfabéticamente por nombre
            java.util.List<Empleado> empleadosList = new ArrayList<>();
            for (Empleado emp : empleados) {
                if (emp != null) {
                    empleadosList.add(emp);
                }
            }
            
            empleadosList.sort((a, b) -> a.getNombre().compareToIgnoreCase(b.getNombre()));
            
            // Calcular rango de salarios para colorear
            double minSalario = empleadosList.stream()
                .mapToDouble(Empleado::getSalarioMensual)
                .min().orElse(0);
            double maxSalario = empleadosList.stream()
                .mapToDouble(Empleado::getSalarioMensual)
                .max().orElse(1);
            
            // Agregar empleados a la tabla
            for (Empleado emp : empleadosList) {
                String managerName = emp.getManager() != null ? emp.getManager().getNombre() : "Sin Manager";
                Object[] row = {
                    emp.getLegajo(),
                    emp.getNombre(),
                    emp.getApellido(),
                    String.format("$%,.2f", emp.getSalarioMensual()),
                    managerName
                };
                empleadosTableModel.addRow(row);
            }
            
            // Aplicar renderizador de color para salarios
            empleadosTable.getColumnModel().getColumn(3).setCellRenderer(
                new SalarioColorRenderer(minSalario, maxSalario)
            );
        }
    }
    
    private Area findAreaByName(String name) {
        for (Area area : areas) {
            if (area != null && area.getNombre().equals(name)) {
                return area;
            }
        }
        return null;
    }
    
    private Empleado findEmpleadoByLegajo(int legajo) {
        for (Area area : areas) {
            if (area != null && area.getEmpleados() != null) {
                for (Empleado emp : area.getEmpleados()) {
                    if (emp != null && emp.getLegajo() == legajo) {
                        return emp;
                    }
                }
            }
        }
        return null;
    }
    
    private void showEmpleadoDetails(Empleado empleado) {
        EmpleadoDetailsDialog dialog = new EmpleadoDetailsDialog(this, empleado);
        dialog.setVisible(true);
    }
    
    // Clase auxiliar para ordenar áreas
    private static class AreaData {
        Area area;
        double presupuestoUsado;
        double porcentaje;
    }
    
    // Renderizador para colorear botones de empleados según salario
    private static class SalarioColorRenderer extends DefaultTableCellRenderer {
        private double minSalario;
        private double maxSalario;
        
        public SalarioColorRenderer(double minSalario, double maxSalario) {
            this.minSalario = minSalario;
            this.maxSalario = maxSalario;
        }
        
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            
            if (!isSelected && column == 3) { // Columna de salario
                // Obtener el salario de la fila
                String salarioStr = (String) value;
                double salario = Double.parseDouble(salarioStr.replace("$", "").replace(",", ""));
                
                // Calcular intensidad del color (de negro a azul)
                double ratio = (maxSalario > minSalario) ? 
                    (salario - minSalario) / (maxSalario - minSalario) : 0.5;
                
                // Color de negro a azul
                int blue = (int) (ratio * 255);
                int red = (int) ((1 - ratio) * 50);
                int green = (int) ((1 - ratio) * 50);
                
                c.setBackground(new Color(red, green, blue));
                c.setForeground(Color.WHITE);
            } else if (!isSelected) {
                c.setBackground(Color.WHITE);
                c.setForeground(Color.BLACK);
            }
            
            return c;
        }
    }
    
    // Método main para pruebas
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Crear datos de prueba
            Area[] areas = createTestData();
            
            ReporteEstadoAreas frame = new ReporteEstadoAreas(areas);
            frame.setVisible(true);
        });
    }
    
    private static Area[] createTestData() {
        // Crear managers
        Manager manager1 = new Manager("Juan Pérez", "1234567", "555-0001", 5, null, null);
        Manager manager2 = new Manager("María García", "2345678", "555-0002", 8, null, null);
        Manager manager3 = new Manager("Carlos López", "3456789", "555-0003", 3, null, null);
        
        // Crear empleados para Área 1 - IT
        Empleado[] empleadosIT = new Empleado[5];
        empleadosIT[0] = new Empleado(1001, "Ana", "Martínez", "4567890", "555-1001", "/cv/ana.pdf", 2, 5000, manager1, null);
        empleadosIT[1] = new Empleado(1002, "Pedro", "Sánchez", "5678901", "555-1002", "/cv/pedro.pdf", 4, 6500, manager1, null);
        empleadosIT[2] = new Empleado(1003, "Laura", "Rodríguez", "6789012", "555-1003", "/cv/laura.pdf", 1, 4500, manager1, null);
        empleadosIT[3] = new Empleado(1004, "Diego", "Fernández", "7890123", "555-1004", "/cv/diego.pdf", 6, 7500, manager1, null);
        empleadosIT[4] = new Empleado(1005, "Sofia", "González", "8901234", "555-1005", "/cv/sofia.pdf", 3, 5500, manager1, null);
        
        // Crear empleados para Área 2 - Ventas
        Empleado[] empleadosVentas = new Empleado[4];
        empleadosVentas[0] = new Empleado(2001, "Roberto", "Díaz", "9012345", "555-2001", "/cv/roberto.pdf", 5, 4000, manager2, null);
        empleadosVentas[1] = new Empleado(2002, "Carmen", "Torres", "0123456", "555-2002", "/cv/carmen.pdf", 2, 3500, manager2, null);
        empleadosVentas[2] = new Empleado(2003, "Miguel", "Ramírez", "1234560", "555-2003", "/cv/miguel.pdf", 7, 5000, manager2, null);
        empleadosVentas[3] = new Empleado(2004, "Elena", "Castro", "2345601", "555-2004", "/cv/elena.pdf", 1, 3000, manager2, null);
        
        // Crear empleados para Área 3 - RRHH
        Empleado[] empleadosRRHH = new Empleado[3];
        empleadosRRHH[0] = new Empleado(3001, "Fernando", "Morales", "3456012", "555-3001", "/cv/fernando.pdf", 4, 4200, manager3, null);
        empleadosRRHH[1] = new Empleado(3002, "Patricia", "Vega", "4560123", "555-3002", "/cv/patricia.pdf", 6, 4800, manager3, null);
        empleadosRRHH[2] = new Empleado(3003, "Javier", "Ruiz", "5601234", "555-3003", "/cv/javier.pdf", 2, 3800, manager3, null);
        
        // Crear áreas
        Area areaIT = new Area(1, "Tecnología", "Área de desarrollo y sistemas", 400000, empleadosIT);
        Area areaVentas = new Area(2, "Ventas", "Área comercial", 200000, empleadosVentas);
        Area areaRRHH = new Area(3, "Recursos Humanos", "Área de gestión de personal", 180000, empleadosRRHH);
        
        // Asignar áreas a empleados
        for (Empleado emp : empleadosIT) {
            if (emp != null) emp.setArea(areaIT);
        }
        for (Empleado emp : empleadosVentas) {
            if (emp != null) emp.setArea(areaVentas);
        }
        for (Empleado emp : empleadosRRHH) {
            if (emp != null) emp.setArea(areaRRHH);
        }
        
        return new Area[]{areaIT, areaVentas, areaRRHH};
    }
}
