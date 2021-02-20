package principal;

import java.awt.Color;

import java.awt.Graphics;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


import javax.imageio.ImageIO;

import javax.swing.JPanel;


/**
 * @author Ricardo Martín García
 * 
 * Sprite que representa los misiles del submarino 
*/

public class Disparo extends Sprite {
    private BufferedImage buffer;

    int velocidad;

    public Disparo(Color color, int ancho, int alto, int posX, int posY, int velo) {
        super(color, ancho, alto, posX, posY);
        velocidad = velo;
    }

    public Disparo(String rutaImg, int ancho, int alto, int posX, int posY, int velo) {
        super(rutaImg, ancho, alto, posX, posY);
        velocidad = velo;

        inicializarBuffer(rutaImg);
        velX = obtenerAle();
        velY = obtenerAle();

    }

    @Override
    public boolean comprobarColision(Sprite otroSprite) {
        boolean colisionEnX = false;
        boolean colisionEnY = false;
        boolean colision = false;

        // Calcular colision X

        if (this.getPosX() < otroSprite.getPosX()) {
            if ((this.getPosX() + this.getAncho()) > otroSprite.getPosX()) {
                colisionEnX = true;
            }
        } else {
            if ((otroSprite.getPosX() + otroSprite.getAncho() > this.getPosX())) {
                colisionEnX = true;
            }

        }

        // Calcular colision Y

        if (this.getPosY() < otroSprite.getPosY()) {

            if ((this.getPosY() + this.getAlto()) > otroSprite.getPosY()) {
                colisionEnY = true;
            }
        } else {
            if ((otroSprite.getPosY() + otroSprite.getAlto() > this.getPosY())) {
                colisionEnY = true;
            }

        }

        if (colisionEnX && colisionEnY) {
            colision = true;
        }

        return colision;
    }

    @Override
    public void mover(int anchoVentana, int altoVentana) {
        if (getPosY() + getAlto() > 0) {
            this.setPosY(this.getPosY() - velocidad);

        }
    }

    @Override

    public void inicializarBuffer(String ruta) {
        try {
            buffer = ImageIO.read(new File(ruta));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void estampar(Graphics g, JPanel panel, double segundos) {
        g.drawImage(buffer.getScaledInstance(ancho, alto, BufferedImage.SCALE_SMOOTH), getPosX(), getPosY(), null);

    }

}
