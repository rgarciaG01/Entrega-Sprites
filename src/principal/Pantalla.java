package principal;

import java.awt.Graphics;
import java.awt.event.*;
/**
 * @author Ricardo Martín García
 * 
 * Interfaz con los métodos que todas las pantalla tiene que tener 
*/
public interface Pantalla {

    public void inicializarPantalla(PanelJuego panelJuego);

    public void pintarPantalla(Graphics g);

    public void ejecutarFrame();

    public void pulsarRaton(MouseEvent e);

    public void moverRaton(MouseEvent e, int alto);

    public void redimensinarPantalla(ComponentEvent e);

    public void presionarTeclas(KeyEvent e);

}
