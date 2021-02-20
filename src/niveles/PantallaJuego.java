package niveles;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;

import javax.imageio.ImageIO;

import java.awt.image.*;
import java.awt.*;
import java.awt.event.*;

import principal.*;
import pantallas.*;

/**
 * @author Ricardo Martín García
 */

public class PantallaJuego implements Pantalla {
    // Constantes del juego
    private static final int LADO_SPRITE = 40;
    private static final int VELOCIDAD_SUBMARINO = 3;
    private static final int NUMERO_ENEMIGOS = 20;
    public static final int INTENSIDAD = 3;
    int barcosDestruidos;

    // Objetos
    private ArrayList<Sprite> minas;
    private Sprite submarino;
    ArrayList<Barco> barcos;
    ArrayList<Barco> barcosEnReserva;

    Disparo disparo;
    PanelJuego panelJuego;

    // Tiempo
    public double segundos;
    public double segundosOriginal;

    // Fuentes
    Font fuente;
    DecimalFormat df;
    Boolean disparoLaser;

    // Imagenes
    Graphics grafico;
    Image imgOriginal;
    Image imgutilizar;

    // Variable para saber el horizonte del mar
    int horizonte;

    // Constructor
    public PantallaJuego(PanelJuego paneljuego) {
        this.panelJuego = paneljuego;
        minas = new ArrayList<>();
        inicializarPantalla(panelJuego);
        fuente = new Font("Arial", Font.BOLD, 20);
        df = new DecimalFormat("#0.00");
        segundosOriginal = System.nanoTime();
        disparoLaser = false;
        Dimension dimen = Toolkit.getDefaultToolkit().getScreenSize();
        horizonte = (int) dimen.getHeight() / 4;
        barcos = new ArrayList<>();
        barcosEnReserva = new ArrayList<>();
        barcosDestruidos = 0;

        /**
         * Rellenamos los primeros barcos de reserva (que són los que todavia no estan
         * en pantalla ), segun se van eliminando de el arra de barcos se cojeran de
         * aqui
         */
        for (int i = 0; i < NUMERO_ENEMIGOS/2; i++) {
            barcosEnReserva.add(new Barco("Imagenes\\barco.png", 200, 100, -200 - (i * 50), horizonte - 40, 5));
            barcosEnReserva.add(new Barco("Imagenes\\barco.png", 200, 100, 1600 + (i * 50), horizonte - 40, -5));

        }
        /**
         * Metemos el primero
         */
        barcos.add(barcosEnReserva.get(0));
        barcosEnReserva.remove(0);

    }

    @Override
    public void inicializarPantalla(PanelJuego panelJuego) {
        disparo = null;

        try {

            imgOriginal = ImageIO.read(new File("Imagenes\\fondo.jpg"));
            imgutilizar = imgOriginal.getScaledInstance(panelJuego.getWidth(), panelJuego.getHeight(),
                    BufferedImage.SCALE_SMOOTH);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Metodo que pinta los elementos por pantalla
     */
    @Override
    public void pintarPantalla(Graphics g) {
        grafico = g;
        rellenarFondo(g);
        for (int i = 0; i < minas.size(); i++) {
            minas.get(i).estampar(g, panelJuego, segundos);
        }
        if (disparo != null) {
            disparo.estampar(g, panelJuego, segundos);
        }
        if (submarino != null) {
            submarino.estampar(g, panelJuego, segundos);

        }
        if (!barcos.isEmpty()) {

            for (int i = 0; i < barcos.size(); i++) {
                barcos.get(i).estampar(g, panelJuego, segundos);

            }
        }

        g.dispose();

    }

    /**
     * Ejecuta el grame
     */
    @Override
    public void ejecutarFrame() {
        contarTimepo();
        try {
            Thread.sleep(25);
        } catch (InterruptedException e) {

        }
        if (minas.size() == 0) {
        }
        // Comprobamos si algún asteroides choca con la nave teniendo en cuenta que la
        // nave no sea null
        if (submarino != null) {
            for (int i = 0; i < minas.size(); i++) {
                if (minas.get(i).comprobarColision(submarino)) {

                    panelJuego.cambiarPantalla(new PantallaDerrota(panelJuego, barcosDestruidos));
                }

            }
        }

        // Si barcos no esta vacio entonces hacemos que suelten minas uno de ellos
        // (aleatorio)
        if (!barcos.isEmpty()) {

            for (int i = 0; i < barcos.size(); i++) {
                // Si no hay ninguna mina la añadimos sin mas
                if (minas.size() == 0) {
                    minas.add(new Sprite("Imagenes\\mina.png", LADO_SPRITE, LADO_SPRITE, barcos.get(i).getPosX() + 40,
                            horizonte + 50));
                    minas.get(i).setVelY(5);
                }
                // Si ya hay minas(siempre hay por el if anterior) entonces seleccionamos el
                // barco que dispara la mina
                if (minas.size() < i + 1) {
                    Random rd = new Random();
                    int barcoQdispara = rd.nextInt(barcos.size());
                    minas.add(new Sprite("Imagenes\\mina.png", LADO_SPRITE, LADO_SPRITE,
                            barcos.get(barcoQdispara).getPosX() + 40, horizonte + 50));
                    minas.get(i).setVelY(4);
                }

            }

        } else {
            // Si no hay nungun barco en el array de barcos significa que hemos ganado
            panelJuego.cambiarPantalla(new PantallaVictoria(segundos, panelJuego, NUMERO_ENEMIGOS));

        }
        // Si barcos no está vacio y disparo no es null se comprueba las colisiones
        if (!barcos.isEmpty() && disparo != null) {

            for (int i = 0; i < barcos.size(); i++) {
                if (disparo != null) {
                    if (disparo.comprobarColision(barcos.get(i))) {
                        barcos.remove(i);
                        barcosDestruidos++;
                        // Aquí vamos cojiendo los barcos de la reserva y los metemos en barcos(que es
                        // el array de barcos que está en pantalla )
                        while (!barcosEnReserva.isEmpty() && barcos.size() < INTENSIDAD) {
                            barcos.add(barcosEnReserva.get(0));
                            barcosEnReserva.remove(0);
                        }
                        // Si hay impacto el disparo desaparece
                        disparo = null;
                    }
                }
            }
        }

        for (int i = 0; i < minas.size(); i++) {
            minas.get(i).mover(panelJuego.getWidth(), panelJuego.getHeight());
            // Si las minas llegan al suelo se auto destruyen
            if (minas.get(i).getPosY() > 890) {
                if (minas.size() == 0) {
                    minas.remove(0);
                }
                minas.remove(i);
            }

        }
        // Mueve el submarino si existe
        if (submarino != null) {
            submarino.moverNave(panelJuego.getWidth(), panelJuego.getHeight(), horizonte);
        }
        // Mueve los barcos (si hay )
        if (!barcos.isEmpty()) {
            for (int i = 0; i < barcos.size(); i++) {
                barcos.get(i).mover(panelJuego.getWidth(), panelJuego.getHeight());
            }
        }
        // Mueve el disparo si existe
        if (disparo != null) {
            disparo.mover(panelJuego.getWidth(), panelJuego.getHeight());
            if (disparo.getPosY() + disparo.getAlto() <= 0) {
                disparo = null;
            }
        }

    }

    @Override
    public void pulsarRaton(MouseEvent e) {

        if (e.getButton() == 3) {
            // Si hacemos click derecho aparece el submarino
            if (submarino == null) {
                submarino = new Submarino("Imagenes\\submarino.png", 200, 100, e.getX() - 120, e.getY() - 70);
                submarino.setVelX(0);
                submarino.setVelY(0);
                // Esto para obtener la posicion del barco

            }

        }

    }

    @Override
    public void moverRaton(MouseEvent e, int alto) {
        // Nada
    }

    @Override
    public void redimensinarPantalla(ComponentEvent e) {
        if (panelJuego.getWidth() > 0 && panelJuego.getHeight() > 0) {
            imgutilizar = imgOriginal.getScaledInstance(panelJuego.getWidth(), panelJuego.getHeight(),
                    BufferedImage.SCALE_SMOOTH);

        }
    }

    private void contarTimepo() {
        segundos = (System.nanoTime() - segundosOriginal) / 1000000000;

    }

    private void rellenarFondo(Graphics g) {
        grafico.drawImage(imgutilizar, 0, 0, null);

        g.setFont(fuente);
        g.setColor(Color.WHITE);
        DecimalFormat df = new DecimalFormat("#0.00");
        g.drawString("Tiempo: "+df.format(segundos), 60, 40);
        g.setColor(Color.RED);
        g.drawString("Barcos Hundidos: "+barcosDestruidos+"/"+NUMERO_ENEMIGOS, 60, 80);


    }

    @Override
    public void presionarTeclas(KeyEvent e) {
        /**
         * En este evento recibimos una tecla, de ella obtenemos si codigo que puede
         * ser; 38 = Arriba 40 = Abajo 39 = Derecha 37 = Izquierda 32 = Spacio(disparar)
         */
        if (submarino != null) {
            switch (e.getKeyCode()) {
                case 38: {
                    submarino.velY = -VELOCIDAD_SUBMARINO;
                    break;
                }

                case 40: {
                    submarino.velY = +VELOCIDAD_SUBMARINO;
                    break;

                }

                case 39: {
                    submarino.velX = VELOCIDAD_SUBMARINO;
                    break;

                }

                case 37: {
                    submarino.velX = -VELOCIDAD_SUBMARINO;
                    break;

                }

                case 32: {
                    if (submarino != null && disparo == null) {
                        disparo = new Disparo("Imagenes\\misil.png", 50, 60, submarino.getPosX() + 20,
                                submarino.getPosY() + 20, 20);

                    }
                }
            }
        }

    }

}
