//Diego Rocabado - Número de estudiante: 305310
//Santiago Dirón - Número de estudiante: 359644

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
    private Persistencia persistencia;

    public VentanaPrincipal(Sistema sistema) {
        this.sistema = sistema;
        this.persistencia = new Persistencia();
        configurarVentana();
        crearMenuBar();
        inicializarComponentes();
        configurarF1();
        configurarPersistenciaAutomatica();
    }

    private void configurarVentana() {
        setTitle("Sistema de Gestión de Empleados");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
    }

    /**
     * Configura el listener para la tecla F1 que muestra información de autores
     */
    private void configurarF1() {
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
            @Override
            public boolean dispatchKeyEvent(KeyEvent e) {
                if (e.getID() == KeyEvent.KEY_PRESSED && e.getKeyCode() == KeyEvent.VK_F1) {
                    mostrarInformacionAutores();
                    return true;
                }
                return false;
            }
        });
    }

    /**
     * Muestra ventana con información detallada de los autores
     */
    private void mostrarInformacionAutores() {
        JDialog dialogo = new JDialog(this, "Información de Autores", true);
        dialogo.setSize(400, 300);
        dialogo.setLocationRelativeTo(this);
        dialogo.setLayout(new BorderLayout(10, 10));

        JPanel panelContenido = new JPanel();
        panelContenido.setLayout(new BoxLayout(panelContenido, BoxLayout.Y_AXIS));
        panelContenido.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panelContenido.setBackground(new Color(240, 240, 250));

        JLabel lblTitulo = new JLabel("Sistema de Gestión de Empleados");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelContenido.add(lblTitulo);

        panelContenido.add(Box.createRigidArea(new Dimension(0, 20)));

        JLabel lblSubtitulo = new JLabel("Desarrollado por:");
        lblSubtitulo.setFont(new Font("Arial", Font.BOLD, 14));
        lblSubtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelContenido.add(lblSubtitulo);

        panelContenido.add(Box.createRigidArea(new Dimension(0, 15)));

        JLabel lblAutor1 = new JLabel("Diego Rocabado");
        lblAutor1.setFont(new Font("Arial", Font.PLAIN, 14));
        lblAutor1.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelContenido.add(lblAutor1);

        JLabel lblNumero1 = new JLabel("Número de estudiante: 305310");
        lblNumero1.setFont(new Font("Arial", Font.ITALIC, 12));
        lblNumero1.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelContenido.add(lblNumero1);

        panelContenido.add(Box.createRigidArea(new Dimension(0, 10)));

        JLabel lblAutor2 = new JLabel("Santiago Dirón");
        lblAutor2.setFont(new Font("Arial", Font.PLAIN, 14));
        lblAutor2.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelContenido.add(lblAutor2);

        JLabel lblNumero2 = new JLabel("Número de estudiante: 359644");
        lblNumero2.setFont(new Font("Arial", Font.ITALIC, 12));
        lblNumero2.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelContenido.add(lblNumero2);

        panelContenido.add(Box.createRigidArea(new Dimension(0, 20)));

        JLabel lblInfo = new JLabel("Obligatorio 2 - Programación II");
        lblInfo.setFont(new Font("Arial", Font.PLAIN, 12));
        lblInfo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelContenido.add(lblInfo);

        JLabel lblFecha = new JLabel("Noviembre 2025");
        lblFecha.setFont(new Font("Arial", Font.PLAIN, 12));
        lblFecha.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelContenido.add(lblFecha);

        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnCerrar.addActionListener(e -> dialogo.dispose());

        JPanel panelBoton = new JPanel();
        panelBoton.add(btnCerrar);

        dialogo.add(panelContenido, BorderLayout.CENTER);
        dialogo.add(panelBoton, BorderLayout.SOUTH);

        dialogo.setVisible(true);
    }

    /**
     * Configura la persistencia automática al cerrar la aplicación
     */
    private void configurarPersistenciaAutomatica() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int respuesta = JOptionPane.showConfirmDialog(
                    VentanaPrincipal.this,
                    "¿Desea guardar los cambios antes de salir?",
                    "Confirmar Salida",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE
                );

                if (respuesta == JOptionPane.YES_OPTION) {
                    try {
                        persistencia.guardarDatos("sistema.dat", sistema);
                        JOptionPane.showMessageDialog(
                            VentanaPrincipal.this,
                            "Sistema guardado exitosamente.",
                            "Guardado Exitoso",
                            JOptionPane.INFORMATION_MESSAGE
                        );
                        System.exit(0);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(
                            VentanaPrincipal.this,
                            "Error al guardar: " + ex.getMessage(),
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                        );
                    }
                } else if (respuesta == JOptionPane.NO_OPTION) {
                    System.exit(0);
                }
            }
        });
    }

    private void crearMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        // ===== MENÚ ARCHIVO =====
        JMenu menuArchivo = new JMenu("Archivo");

        JMenuItem itemGuardar = new JMenuItem("Guardar");
        itemGuardar.addActionListener(e -> {
            try {
                persistencia.guardarDatos("sistema.dat", sistema);
                JOptionPane.showMessageDialog(
                    this,
                    "Sistema guardado exitosamente.",
                    "Guardado Exitoso",
                    JOptionPane.INFORMATION_MESSAGE
                );
                actualizarEstado("Sistema guardado");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(
                    this,
                    "Error al guardar: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        });

        JMenuItem itemCargar = new JMenuItem("Cargar");
        itemCargar.addActionListener(e -> {
            try {
                Sistema sistemaGuardado = persistencia.cargarDatos("sistema.dat");
                if (sistemaGuardado != null) {
                    this.sistema = sistemaGuardado;
                    JOptionPane.showMessageDialog(
                        this,
                        "Sistema cargado exitosamente.",
                        "Carga Exitosa",
                        JOptionPane.INFORMATION_MESSAGE
                    );
                    actualizarEstado("Sistema cargado");
                } else {
                    JOptionPane.showMessageDialog(
                        this,
                        "No se encontró un sistema guardado.",
                        "Información",
                        JOptionPane.INFORMATION_MESSAGE
                    );
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(
                    this,
                    "Error al cargar: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        });

        JMenuItem itemSalir = new JMenuItem("Salir");
        itemSalir.addActionListener(e -> dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING)));

        menuArchivo.add(itemGuardar);
        menuArchivo.add(itemCargar);
        menuArchivo.addSeparator();
        menuArchivo.add(itemSalir);

        // ===== MENÚ ÁREAS =====
        JMenu menuAreas = new JMenu("Áreas");

        JMenuItem itemAltaArea = new JMenuItem("Alta");
        itemAltaArea.addActionListener(e -> {
            ABMAreasWindow ventana = new ABMAreasWindow(sistema);
            ventana.setVisible(true);
        });

        JMenuItem itemBajaArea = new JMenuItem("Baja");
        itemBajaArea.addActionListener(e -> {
            ABMAreasWindow ventana = new ABMAreasWindow(sistema);
            ventana.setVisible(true);
        });

        JMenuItem itemModificacionArea = new JMenuItem("Modificación");
        itemModificacionArea.addActionListener(e -> {
            ABMAreasWindow ventana = new ABMAreasWindow(sistema);
            ventana.setVisible(true);
        });

        JMenuItem itemRealizarMovimiento = new JMenuItem("Realizar movimiento");
        itemRealizarMovimiento.addActionListener(e -> {
            MovimientoDialog dialog = new MovimientoDialog(this, sistema);
            dialog.setVisible(true);
        });

        menuAreas.add(itemAltaArea);
        menuAreas.add(itemBajaArea);
        menuAreas.add(itemModificacionArea);
        menuAreas.addSeparator();
        menuAreas.add(itemRealizarMovimiento);

        // ===== MENÚ MANAGERS =====
        JMenu menuManagers = new JMenu("Managers");

        JMenuItem itemAltaManager = new JMenuItem("Alta");
        itemAltaManager.addActionListener(e -> {
            GestionManagersGUI ventana = new GestionManagersGUI(sistema);
            ventana.setVisible(true);
        });

        JMenuItem itemBajaManager = new JMenuItem("Baja");
        itemBajaManager.addActionListener(e -> {
            GestionManagersGUI ventana = new GestionManagersGUI(sistema);
            ventana.setVisible(true);
        });

        JMenuItem itemModificacionManager = new JMenuItem("Modificación");
        itemModificacionManager.addActionListener(e -> {
            GestionManagersGUI ventana = new GestionManagersGUI(sistema);
            ventana.setVisible(true);
        });

        menuManagers.add(itemAltaManager);
        menuManagers.add(itemBajaManager);
        menuManagers.add(itemModificacionManager);

        // ===== MENÚ EMPLEADOS =====
        JMenu menuEmpleados = new JMenu("Empleados");

        JMenuItem itemAltaEmpleado = new JMenuItem("Alta");
        itemAltaEmpleado.addActionListener(e -> {
            EmpleadoList ventana = new EmpleadoList(sistema);
            ventana.setVisible(true);
        });

        menuEmpleados.add(itemAltaEmpleado);

        // ===== MENÚ REPORTES =====
        JMenu menuReportes = new JMenu("Reportes");

        JMenuItem itemReporteInteligente = new JMenuItem("Reporte inteligente");
        itemReporteInteligente.addActionListener(e -> {
            ReporteInteligenteWindow ventana = new ReporteInteligenteWindow(
                sistema.getEmpleados(),
                sistema.getAreas()
            );
            ventana.setVisible(true);
        });

        JMenuItem itemReporteEstadoAreas = new JMenuItem("Reporte de estado de áreas");
        itemReporteEstadoAreas.addActionListener(e -> {
            ReporteEstadoAreas ventana = new ReporteEstadoAreas(sistema);
            ventana.setVisible(true);
        });

        JMenuItem itemReporteMovimientos = new JMenuItem("Reporte de movimientos");
        itemReporteMovimientos.addActionListener(e -> {
            ReporteMovimientos ventana = new ReporteMovimientos(
                sistema.getMovimientos(),
                sistema.getAreas()
            );
            ventana.setVisible(true);
        });

        menuReportes.add(itemReporteInteligente);
        menuReportes.add(itemReporteEstadoAreas);
        menuReportes.add(itemReporteMovimientos);

        // Agregar menús a la barra
        menuBar.add(menuArchivo);
        menuBar.add(menuAreas);
        menuBar.add(menuManagers);
        menuBar.add(menuEmpleados);
        menuBar.add(menuReportes);

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
}

