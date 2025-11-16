//Diego Rocabado
//Santiago Dirón

import javax.swing.*;
import java.awt.*;

/**
 * Clase base abstracta para todos los paneles de la aplicación.
 * Proporciona funcionalidad común y un diseño consistente.
 */
public abstract class PanelBase extends JPanel {
    protected Sistema sistema;
    
    public PanelBase(Sistema sistema) {
        this.sistema = sistema;
        setLayout(new BorderLayout());
        inicializarComponentes();
    }
    
    /**
     * Método abstracto para inicializar los componentes específicos de cada panel.
     * Debe ser implementado por las clases hijas.
     */
    protected abstract void inicializarComponentes();
    
    /**
     * Método para actualizar los datos del panel.
     * Puede ser sobrescrito por las clases hijas si es necesario.
     */
    public void actualizarDatos() {
        // Implementación por defecto vacía
    }
}
