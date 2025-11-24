//Diego Rocabado - Número de estudiante: 305310
//Santiago Dirón - Número de estudiante: 359644

import javax.swing.*;
import java.awt.*;

public class CVViewDialog extends JDialog {
    private Empleado empleado;
    private String contenidoCV;

    public CVViewDialog(Frame parent, Empleado empleado, String contenidoCV) {
        super(parent, "Currículum Vitae", true);
        this.empleado = empleado;
        this.contenidoCV = contenidoCV;
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));

        JPanel panelInfo = new JPanel(new GridLayout(1, 4, 10, 5));
        panelInfo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        panelInfo.add(new JLabel("Empleado: " + empleado.getNombre() + " " + empleado.getApellido()));
        panelInfo.add(new JLabel("Legajo: " + empleado.getLegajo()));
        panelInfo.add(new JLabel("Cédula: " + empleado.getCedula()));
        panelInfo.add(new JLabel("Archivo: " + empleado.getPathCV()));

        JTextArea textArea = new JTextArea(contenidoCV);
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        textArea.setWrapStyleWord(true);
        textArea.setLineWrap(true);
        textArea.setCaretPosition(0);

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));

        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBoton.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(e -> dispose());
        panelBoton.add(btnCerrar);

        add(panelInfo, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(panelBoton, BorderLayout.SOUTH);

        setSize(700, 500);
        setLocationRelativeTo(getParent());
    }
}
