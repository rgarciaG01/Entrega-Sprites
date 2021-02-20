package principal;

import java.awt.Color;
import java.awt.Graphics;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import java.util.Random;

import javax.imageio.ImageIO;

import javax.swing.JPanel;


/**

 * @author Ricardo Martín García
 * 
 * Clase que representa un objeto en pantalla

*/
 
public class Sprite {

    private BufferedImage buffer;

    private Color color = Color.BLACK;
    public int ancho;
    public int alto;
    private int posX;
    public int velX;
    public int velY;

    public Sprite(String rutaImg, int ancho, int alto, int posX, int posY) {
        this.ancho = ancho;
        this.alto = alto;
        this.posX = posX;
        this.posY = posY;
        inicializarBuffer(rutaImg);
        velX = obtenerAle();
        velY = obtenerAle();

    }

    public Sprite(Color color, int ancho, int alto, int posX, int posY) {
        this.color = color;
        this.ancho = ancho;
        this.alto = alto;
        this.posX = posX;
        this.posY = posY;
        velX = obtenerAle();
        velY = obtenerAle();

    }

    public int obtenerAle() {
        Random ale = new Random();
        Random aleNegativo = new Random();

        int numero = 0;

        while (numero == 0) {
            numero = ale.nextInt(15);
            if (numero != 0) {
                if (aleNegativo.nextInt(2) == 1) {
                    numero = Math.abs(numero);
                } else {
                    numero = -Math.abs(numero);

                }
            }
        }

        return numero;
    }

    public void inicializarBuffer(String ruta) {
        try {
            buffer = ImageIO.read(new File(ruta));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void estampar(Graphics g, JPanel panel, double segundos) {

        g.drawImage(buffer.getScaledInstance((int) (ancho * 1.30), (int) (alto * 1.40), BufferedImage.SCALE_SMOOTH),
                getPosX(), getPosY(), null);

    }

    private int posY;

    public BufferedImage getBuffer() {
        return this.buffer;
    }

    public void setBuffer(BufferedImage buffer) {
        this.buffer = buffer;
    }

    public Color getColor() {
        return this.color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getAncho() {
        return this.ancho;
    }

    public void setAncho(int ancho) {
        this.ancho = ancho;
    }

    public int getAlto() {
        return this.alto;
    }

    public void setAlto(int alto) {
        this.alto = alto;
    }

    public int getPosX() {
        return this.posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return this.posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public int getVelX() {
        return this.velX;
    }

    public void setVelX(int velX) {
        this.velX = velX;
    }

    public int getVelY() {
        return this.velY;
    }

    public void setVelY(int velY) {
        this.velY = velY;
    }

    public void mover(int anchoVentana, int altoVentana) {
        this.setPosY(this.getPosY() + this.getVelY());

    }
    //Mueve el submarino  y hace comprobaciones para que no se salga 
    public void moverNave(int ancho, int alto, int horizonte) {
        if (posX <= 0) {
            posX = 0;
        }
        if (posX - this.getAncho() > ancho) {
            posX = ancho;
        }
        if (posY < horizonte) {
            posY = horizonte;
        }
        if (posY - this.getAlto() > alto) {
            posY = alto;
        }

        this.setPosY(this.getPosY() + this.getVelY());
        this.setPosX(this.getPosX() + this.getVelX());

    }

    public boolean comprobarColision(Sprite otroSprite) {
        boolean colisionEnX = false;
        boolean colisionEnY = false;
        boolean colision = false;

        // Calcular colision X

        if (this.getPosX() < otroSprite.getPosX()) {
            if ((this.getPosX() + this.ancho) > otroSprite.getPosX()) {
                colisionEnX = true;
            }
        } else {
            if ((otroSprite.getPosX() + otroSprite.getAncho() > this.getPosX())) {
                colisionEnX = true;
            }

        }

        // Calcular colision Y

        if (this.getPosY() < otroSprite.getPosY()) {

            if ((this.getPosY() + this.alto) > otroSprite.getPosY()) {
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

}
