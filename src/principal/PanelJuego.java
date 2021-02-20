package principal;


import java.awt.Graphics;

import java.awt.event.*;

import javax.swing.JPanel;


import pantallas.PantallaInicio;


/**
 * @author Ricardo Martín García
 * 
 * Este es el panel principal que controla la acción de la pantala 
 * 
*/
public class PanelJuego extends JPanel
        implements Runnable, MouseMotionListener, ComponentListener, MouseListener, KeyListener {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    Pantalla pantallaActual;

    public PanelJuego() {
        this.addMouseMotionListener(this);
        this.addComponentListener(this);
        this.addMouseListener(this);
        this.addKeyListener(this);
        pantallaActual = new PantallaInicio(this);
        new Thread(this).start();
        this.setFocusable(true);

    }

    // Método que se llama automáticamente para pintar el componente.
    @Override
    public void paintComponent(Graphics g) {
        pantallaActual.pintarPantalla(g);

    }

    @Override
    public void run() {
        while (true) {

            pantallaActual.ejecutarFrame();
            repaint();

        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        pantallaActual.moverRaton(e, this.getHeight());

    }

    @Override
    public void componentResized(ComponentEvent e) {
        pantallaActual.redimensinarPantalla(e);
    }

    @Override
    public void componentMoved(ComponentEvent e) {

    }

    @Override
    public void componentShown(ComponentEvent e) {

    }

    @Override
    public void componentHidden(ComponentEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mousePressed(MouseEvent e) {
        pantallaActual.pulsarRaton(e);

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    public void cambiarPantalla(Pantalla pantalla) {
        pantallaActual = pantalla;
    }

    // Teclado

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void keyPressed(KeyEvent e) {
        pantallaActual.presionarTeclas(e);

    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub

    }

}
