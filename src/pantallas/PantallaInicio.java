package pantallas;

import java.awt.Graphics;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.Color;
import java.awt.Font;

import principal.*;
import niveles.*;
/**
 * @author Ricardo Martín García
 * 
 * Primera pantalla que se muestra 
*/
public class PantallaInicio implements Pantalla {

    // Objetos
    PanelJuego panelJuego;

    // Fuentes
    Font fuente;
    Color color;

    Hilo hilo;

    public PantallaInicio(PanelJuego paneljuego) {
        panelJuego = paneljuego;
        fuente = new Font("Arial", Font.BOLD, 90);
        hilo = new Hilo();
        hilo.start();

    }

    @Override
    public void inicializarPantalla(PanelJuego panelJuego) {

    }

    @Override
    public void pintarPantalla(Graphics g) {
        g.setColor(hilo.getColor());
        g.fillRect(0, 0, panelJuego.getWidth(), panelJuego.getHeight());
        g.setFont(fuente);
        g.setColor(Color.WHITE);
        g.drawString("Guerra Submarina", (panelJuego.getWidth() / 2) - 400, (panelJuego.getHeight() / 2) - 70);
        g.setColor(Color.RED);
        g.drawRect((panelJuego.getWidth() / 2) - 455, panelJuego.getHeight() / 2 +10, 900, 125);
        g.drawString("Haz para comenzar", (panelJuego.getWidth() / 2) - 415, panelJuego.getHeight() / 2+100);

    }

    @Override
    public void ejecutarFrame() {

    }

    @Override
    public void pulsarRaton(MouseEvent e) {
        panelJuego.cambiarPantalla(new PantallaJuego(panelJuego));

    }

    @Override
    public void moverRaton(MouseEvent e, int alto) {

    }

    @Override
    public void redimensinarPantalla(ComponentEvent e) {

    }

    @Override
    public void presionarTeclas(KeyEvent e) {
        // TODO Auto-generated method stub

    }

}
