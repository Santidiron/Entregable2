//Diego Rocabado - Número de estudiante: 305310
//Santiago Dirón - Número de estudiante: 359644

import javax.swing.*;
import java.awt.*;

/**
 * Ventana de bienvenida (Splash Screen) que se muestra al iniciar la aplicación.
 * Muestra el logo, los nombres y números de estudiante de los autores.
 * Después de 3-5 segundos, pregunta cómo iniciar el sistema.
 */
public class VentanaBienvenida extends JFrame {
    private Sistema sistema;

    public VentanaBienvenida(Sistema sistema) {
        this.sistema = sistema;
        configurarVentana();
        inicializarComponentes();
        iniciarTransicion();
    }

    private void configurarVentana() {
        setTitle("Sistema de Gestión de Empleados");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setUndecorated(true);
    }

    private void inicializarComponentes() {
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BorderLayout());
        panelPrincipal.setBackground(new Color(41, 128, 185));
        panelPrincipal.setBorder(BorderFactory.createLineBorder(new Color(52, 73, 94), 3));

        // Panel central con logo y texto
        JPanel panelCentro = new JPanel();
        panelCentro.setLayout(new BoxLayout(panelCentro, BoxLayout.Y_AXIS));
        panelCentro.setBackground(new Color(41, 128, 185));
        panelCentro.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        // Logo (placeholder)
        JLabel labelLogo = new JLabel("📊");
        labelLogo.setFont(new Font("Dialog", Font.PLAIN, 100));
        labelLogo.setForeground(Color.WHITE);
        labelLogo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelCentro.add(labelLogo);

        panelCentro.add(Box.createRigidArea(new Dimension(0, 30)));

        // Título
        JLabel labelTitulo = new JLabel("Sistema de Gestión de Empleados");
        labelTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        labelTitulo.setForeground(Color.WHITE);
        labelTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelCentro.add(labelTitulo);

        panelCentro.add(Box.createRigidArea(new Dimension(0, 50)));

        // Autores
        JLabel labelAutores = new JLabel("Desarrollado por:");
        labelAutores.setFont(new Font("Arial", Font.PLAIN, 14));
        labelAutores.setForeground(Color.WHITE);
        labelAutores.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelCentro.add(labelAutores);

        panelCentro.add(Box.createRigidArea(new Dimension(0, 10)));

        JLabel labelAutor1 = new JLabel("Diego Rocabado - Número: 305310");
        labelAutor1.setFont(new Font("Arial", Font.BOLD, 16));
        labelAutor1.setForeground(Color.WHITE);
        labelAutor1.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelCentro.add(labelAutor1);

        panelCentro.add(Box.createRigidArea(new Dimension(0, 5)));

        JLabel labelAutor2 = new JLabel("Santiago Dirón - Número: 359644");
        labelAutor2.setFont(new Font("Arial", Font.BOLD, 16));
        labelAutor2.setForeground(Color.WHITE);
        labelAutor2.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelCentro.add(labelAutor2);

        panelPrincipal.add(panelCentro, BorderLayout.CENTER);
        add(panelPrincipal);
    }

    private void iniciarTransicion() {
        Timer timer = new Timer(3000, e -> {
            mostrarDialogoOpciones();
        });
        timer.setRepeats(false);
        timer.start();
    }

    private void mostrarDialogoOpciones() {
        String[] opciones = {
            "Sistema nuevo vacío",
            "Cargar sistema guardado",
            "Datos ficticios precargados"
        };

        int opcion = JOptionPane.showOptionDialog(
            this,
            "¿Cómo desea iniciar el sistema?",
            "Inicializar Sistema",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            opciones,
            opciones[2]
        );

        switch (opcion) {
            case 0: // Sistema nuevo vacío
                abrirVentanaPrincipal();
                break;

            case 1: // Cargar sistema guardado
                cargarSistemaGuardado();
                break;

            case 2: // Datos ficticios precargados
                cargarDatosPrecargados();
                break;

            default: // Usuario cerró el diálogo
                System.exit(0);
                break;
        }
    }

    private void cargarSistemaGuardado() {
        try {
            Persistencia persistencia = new Persistencia();
            Sistema sistemaGuardado = persistencia.cargarDatos("sistema.dat");

            if (sistemaGuardado != null) {
                this.sistema = sistemaGuardado;
                JOptionPane.showMessageDialog(
                    this,
                    "Sistema cargado exitosamente.",
                    "Carga Exitosa",
                    JOptionPane.INFORMATION_MESSAGE
                );
                abrirVentanaPrincipal();
            } else {
                int respuesta = JOptionPane.showConfirmDialog(
                    this,
                    "No se encontró un sistema guardado.\n¿Desea iniciar con datos precargados?",
                    "Sistema no encontrado",
                    JOptionPane.YES_NO_OPTION
                );

                if (respuesta == JOptionPane.YES_OPTION) {
                    cargarDatosPrecargados();
                } else {
                    abrirVentanaPrincipal();
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                this,
                "Error al cargar el sistema: " + ex.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
            abrirVentanaPrincipal();
        }
    }

    private void cargarDatosPrecargados() {
        try {
            sistema.cargarDatosPrecargados();
            JOptionPane.showMessageDialog(
                this,
                "Datos precargados exitosamente:\n" +
                "- " + sistema.getAreas().size() + " áreas\n" +
                "- " + sistema.getManagers().size() + " managers\n" +
                "- " + sistema.getEmpleados().size() + " empleados\n" +
                "- " + sistema.getMovimientos().size() + " movimientos",
                "Carga Exitosa",
                JOptionPane.INFORMATION_MESSAGE
            );
            abrirVentanaPrincipal();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                this,
                "Error al cargar datos precargados: " + ex.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
            abrirVentanaPrincipal();
        }
    }

    private void abrirVentanaPrincipal() {
        VentanaPrincipal ventanaPrincipal = new VentanaPrincipal(sistema);
        ventanaPrincipal.setVisible(true);
        dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Sistema sistema = new Sistema();
            VentanaBienvenida ventana = new VentanaBienvenida(sistema);
            ventana.setVisible(true);
        });
    }
}

