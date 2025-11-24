//Diego Rocabado - Número de estudiante: 305310
//Santiago Dirón - Número de estudiante: 359644

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Sistema sistema = new Sistema();
            VentanaBienvenida ventanaBienvenida = new VentanaBienvenida(sistema);
            ventanaBienvenida.setVisible(true);
        });
    }
}
