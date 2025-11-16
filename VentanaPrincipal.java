//Diego Rocabado
//Santiago Dirón

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Ventana principal de la aplicación con menú y panel central dinámico.
 */
public class VentanaPrincipal extends JFrame {
    private Sistema sistema;
    private JPanel panelCentral;
    private JLabel labelEstado;
    
    public VentanaPrincipal(Sistema sistema) {
        this.sistema = sistema;
        configurarVentana();
        crearMenuBar();
        inicializarComponentes();
    }
    
    private void configurarVentana() {
        setTitle("Sistema de Gestión de Empleados");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
    }
    
    private void crearMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        
        // Menú Gestión
        JMenu menuGestion = new JMenu("Gestión");
        JMenuItem itemAreas = new JMenuItem("Áreas");
        JMenuItem itemManagers = new JMenuItem("Managers");
        JMenuItem itemEmpleados = new JMenuItem("Empleados");
        
        itemAreas.addActionListener(e -> mostrarMensaje("Gestión de Áreas"));
        itemManagers.addActionListener(e -> mostrarMensaje("Gestión de Managers"));
        itemEmpleados.addActionListener(e -> mostrarMensaje("Gestión de Empleados"));
        
        menuGestion.add(itemAreas);
        menuGestion.add(itemManagers);
        menuGestion.add(itemEmpleados);
        
        // Menú Movimientos
        JMenu menuMovimientos = new JMenu("Movimientos");
        JMenuItem itemRegistrarMovimiento = new JMenuItem("Registrar Movimiento");
        
        itemRegistrarMovimiento.addActionListener(e -> mostrarMensaje("Registrar Movimiento"));
        
        menuMovimientos.add(itemRegistrarMovimiento);
        
        // Menú Reportes
        JMenu menuReportes = new JMenu("Reportes");
        JMenuItem itemEstadoAreas = new JMenuItem("Estado de Áreas");
        JMenuItem itemMovimientos = new JMenuItem("Movimientos");
        JMenuItem itemReporteInteligente = new JMenuItem("Reporte Inteligente");
        
        itemEstadoAreas.addActionListener(e -> mostrarMensaje("Estado de Áreas"));
        itemMovimientos.addActionListener(e -> mostrarMensaje("Reporte de Movimientos"));
        itemReporteInteligente.addActionListener(e -> mostrarMensaje("Reporte Inteligente"));
        
        menuReportes.add(itemEstadoAreas);
        menuReportes.add(itemMovimientos);
        menuReportes.add(itemReporteInteligente);
        
        // Menú Datos
        JMenu menuDatos = new JMenu("Datos");
        JMenuItem itemCargarDatos = new JMenuItem("Cargar Datos Precargados");
        
        itemCargarDatos.addActionListener(e -> {
            sistema.cargarDatosPrecargados();
            actualizarEstado("Datos precargados exitosamente");
        });
        
        menuDatos.add(itemCargarDatos);
        
        // Menú Archivo
        JMenu menuArchivo = new JMenu("Archivo");
        JMenuItem itemGuardar = new JMenuItem("Guardar");
        JMenuItem itemCargar = new JMenuItem("Cargar");
        JMenuItem itemSalir = new JMenuItem("Salir");
        
        itemGuardar.addActionListener(e -> mostrarMensaje("Guardar"));
        itemCargar.addActionListener(e -> mostrarMensaje("Cargar"));
        itemSalir.addActionListener(e -> {
            int respuesta = JOptionPane.showConfirmDialog(
                this, 
                "¿Está seguro que desea salir?", 
                "Confirmar salida", 
                JOptionPane.YES_NO_OPTION
            );
            if (respuesta == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });
        
        menuArchivo.add(itemGuardar);
        menuArchivo.add(itemCargar);
        menuArchivo.addSeparator();
        menuArchivo.add(itemSalir);
        
        // Agregar menús a la barra
        menuBar.add(menuArchivo);
        menuBar.add(menuGestion);
        menuBar.add(menuMovimientos);
        menuBar.add(menuReportes);
        menuBar.add(menuDatos);
        
        setJMenuBar(menuBar);
    }
    
    private void inicializarComponentes() {
        // Panel Central
        panelCentral = new JPanel();
        panelCentral.setLayout(new BorderLayout());
        panelCentral.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Panel de bienvenida por defecto
        JPanel panelBienvenida = new JPanel();
        panelBienvenida.setLayout(new BoxLayout(panelBienvenida, BoxLayout.Y_AXIS));
        
        JLabel labelTitulo = new JLabel("Bienvenido al Sistema de Gestión de Empleados");
        labelTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        labelTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel labelSubtitulo = new JLabel("Seleccione una opción del menú para comenzar");
        labelSubtitulo.setFont(new Font("Arial", Font.PLAIN, 14));
        labelSubtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        panelBienvenida.add(Box.createVerticalGlue());
        panelBienvenida.add(labelTitulo);
        panelBienvenida.add(Box.createRigidArea(new Dimension(0, 20)));
        panelBienvenida.add(labelSubtitulo);
        panelBienvenida.add(Box.createVerticalGlue());
        
        panelCentral.add(panelBienvenida, BorderLayout.CENTER);
        add(panelCentral, BorderLayout.CENTER);
        
        // Barra de Estado
        JPanel panelEstado = new JPanel();
        panelEstado.setLayout(new FlowLayout(FlowLayout.LEFT));
        panelEstado.setBorder(BorderFactory.createEtchedBorder());
        
        labelEstado = new JLabel("Listo");
        panelEstado.add(labelEstado);
        
        add(panelEstado, BorderLayout.SOUTH);
    }
    
    /**
     * Método para cambiar el contenido del panel central.
     */
    public void cambiarPanelCentral(JPanel nuevoPanel) {
        panelCentral.removeAll();
        panelCentral.add(nuevoPanel, BorderLayout.CENTER);
        panelCentral.revalidate();
        panelCentral.repaint();
    }
    
    /**
     * Método para actualizar el texto de la barra de estado.
     */
    public void actualizarEstado(String mensaje) {
        labelEstado.setText(mensaje);
    }
    
    /**
     * Método auxiliar para mostrar mensajes temporales.
     */
    private void mostrarMensaje(String funcionalidad) {
        actualizarEstado(funcionalidad + " - Funcionalidad en desarrollo");
        JOptionPane.showMessageDialog(
            this, 
            "La funcionalidad de " + funcionalidad + " será implementada próximamente.",
            funcionalidad,
            JOptionPane.INFORMATION_MESSAGE
        );
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Sistema sistema = new Sistema();
            VentanaPrincipal ventana = new VentanaPrincipal(sistema);
            ventana.setVisible(true);
        });
    }
}
