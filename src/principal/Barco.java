package principal;

import java.awt.Graphics;

import javax.swing.JPanel;

/**
 * @author Ricardo Martín García
 * 
 * Sprite que representa los barcos 
*/

public class Barco extends Sprite {
    int vidas;

    public Barco(String rutaImg, int ancho, int alto, int posX, int posY, int velX) {
        super(rutaImg, ancho, alto, posX, posY);
        this.velX = velX;
    }

    public int getVidas() {
        return this.vidas;
    }

    public void setVidas(int vidas) {
        this.vidas = vidas;
    }

    @Override
    public void mover(int anchoVentana, int altoVentana) {
        this.setPosX(this.getPosX() + this.getVelX());

        // Comprobar ancho
        if (this.getPosX() + this.getAncho() >= anchoVentana) {
            this.setVelX(-Math.abs(this.getVelX()));
        }
        if (this.getPosX() < 0) {
            this.setVelX(Math.abs(this.getVelX()));
        }
        // Comprobar alto

    }

    @Override
    public void estampar(Graphics g, JPanel panel, double segundos) {
        if (velX < 0) {
            inicializarBuffer("Imagenes\\barcoIz.png");
        } else {
            inicializarBuffer("Imagenes\\barco.png");

        }
        super.estampar(g, panel, segundos);
    }

}
