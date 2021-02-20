package pantallas;

import java.awt.Graphics;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.*;
import java.awt.Image;


import java.awt.Color;
import java.awt.Font;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;

import javax.imageio.ImageIO;


import principal.*;
import niveles.*;
/**
 * @author Ricardo Martín García
 * 
 * Pantalla que se muestra al destruir todos los barcos (ganar)
*/
public class PantallaVictoria implements Pantalla {
    double segundos;
    Image imgutilizar;
    Image imgOriginal;
    String mensaje;
    Font fuente;
    PanelJuego panelJuego;
    double seguAUX;
    int barcos;

    public PantallaVictoria(double segundos, PanelJuego panelJuego, int barcosDestruidos) {
        this.panelJuego = panelJuego;
        this.segundos = segundos;
        fuente = new Font("Arial", Font.BOLD, 60);
        barcos = barcosDestruidos;

        try {
            imgOriginal = ImageIO.read(new File("Imagenes\\victoria.jpg"));
            imgutilizar = imgOriginal.getScaledInstance(panelJuego.getWidth(), panelJuego.getHeight(),
                    BufferedImage.SCALE_SMOOTH);
        } catch (IOException e) {
            e.printStackTrace();
        }
        inicializarPantalla(panelJuego);
    }

    @Override
    public void inicializarPantalla(PanelJuego panelJuego) {
        // Comprobamos que hemos superado el record
        try {
            seguAUX = getSeg();
            if (seguAUX == 0) {
                setSeg(segundos);
                mensaje = "Superaste el record";

            } else {
                if (seguAUX > segundos) {
                    setSeg(segundos);
                    mensaje = "Superaste el record";

                } else {
                    mensaje = "No superaste el record";
                }
            }

        } catch (IOException e) {
        }
    }

    @Override
    public void pintarPantalla(Graphics g) {
        g.drawImage(imgutilizar, 0, 0, null);
        g.setFont(fuente);
        g.setColor(Color.YELLOW);
        DecimalFormat df = new DecimalFormat("#0.00");
        g.drawString(mensaje, (panelJuego.getWidth() / 2) - 280, 80);
        g.setColor(Color.YELLOW);

        g.drawString("TIEMPO: " + df.format(segundos), (panelJuego.getWidth() / 2) - 200,
                (panelJuego.getHeight() / 2) + 80);
        g.setColor(Color.RED);
        g.drawString("Enemigos Destruidos: " + barcos, (panelJuego.getWidth() / 2) - 350,
                (panelJuego.getHeight() / 2) + 160);
        g.setColor(Color.YELLOW);

        g.drawString("¡¡Click para volver a jugar!!", (panelJuego.getWidth() / 2) - 400, panelJuego.getHeight() - 100);

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
        if (panelJuego.getWidth() > 0 && panelJuego.getHeight() > 0) {
            imgutilizar = imgOriginal.getScaledInstance(panelJuego.getWidth(), panelJuego.getHeight(),
                    BufferedImage.SCALE_SMOOTH);

        }
    }

    // Método que actualiza el fichero de puntuación
    public void setSeg(double segundos) {
        try {
            FileOutputStream fos = new FileOutputStream(new File("score.dat"));
            DataOutputStream dos = new DataOutputStream(fos);

            dos.writeDouble(segundos);

            dos.close();

        } catch (Exception e) {
            System.out.println("Error en la operaciónes de E/S");
        }
    }

    // Método que lee el fichero(si existe ) y obtiene el timepo guardado
    public static double getSeg() throws IOException {
        double segAUX = 0;
        DataInputStream dis = null;
        File file = new File("score.dat");
        if (file.exists()) {
            try {
                FileInputStream fis = new FileInputStream(file);
                dis = new DataInputStream(fis);

                while (true) {
                    segAUX = dis.readDouble();
                }

            } catch (EOFException e) {
            } catch (IOException e) {
            } finally {
                dis.close();
            }
        } else {
            segAUX = 0;

        }

        return segAUX;
    }

    @Override
    public void presionarTeclas(KeyEvent e) {
        // TODO Auto-generated method stub

    }

}
