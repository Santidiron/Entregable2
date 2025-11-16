//Diego Rocabado
//Santiago Dirón

import javax.swing.*;
import java.awt.*;

/**
 * Ventana de bienvenida (Splash Screen) que se muestra al iniciar la aplicación.
 * Muestra el logo, los nombres de los autores y transiciona automáticamente
 * a la ventana principal después de 3 segundos.
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
        
        JLabel labelAutor1 = new JLabel("Diego Rocabado");
        labelAutor1.setFont(new Font("Arial", Font.BOLD, 16));
        labelAutor1.setForeground(Color.WHITE);
        labelAutor1.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelCentro.add(labelAutor1);
        
        panelCentro.add(Box.createRigidArea(new Dimension(0, 5)));
        
        JLabel labelAutor2 = new JLabel("Santiago Dirón");
        labelAutor2.setFont(new Font("Arial", Font.BOLD, 16));
        labelAutor2.setForeground(Color.WHITE);
        labelAutor2.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelCentro.add(labelAutor2);
        
        panelPrincipal.add(panelCentro, BorderLayout.CENTER);
        
        // Barra de progreso
        JProgressBar progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);
        progressBar.setForeground(new Color(52, 152, 219));
        progressBar.setBackground(new Color(44, 62, 80));
        panelPrincipal.add(progressBar, BorderLayout.SOUTH);
        
        add(panelPrincipal);
    }
    
    private void iniciarTransicion() {
        Timer timer = new Timer(3000, e -> {
            VentanaPrincipal ventanaPrincipal = new VentanaPrincipal(sistema);
            ventanaPrincipal.setVisible(true);
            dispose();
        });
        timer.setRepeats(false);
        timer.start();
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Sistema sistema = new Sistema();
            VentanaBienvenida ventana = new VentanaBienvenida(sistema);
            ventana.setVisible(true);
        });
    }
}
