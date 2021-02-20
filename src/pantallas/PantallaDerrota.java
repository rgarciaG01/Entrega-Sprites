package pantallas;

import java.awt.Graphics;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.*;
import java.awt.Image;


import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


import principal.*;
import niveles.*;
/**
 * @author Ricardo Martín García
 * 
 * Esta pantalla se muestra cuando hemos perdido el juego 
*/
public class PantallaDerrota implements Pantalla {
    Image imgutilizar;
    Image imgOriginal;
    Font fuente;
    PanelJuego panelJuego;
    int barcosDes;

    public PantallaDerrota(PanelJuego panelJuego, int barcosDestruidos) {
        this.panelJuego = panelJuego;
        fuente = new Font("Arial", Font.BOLD, 60);
        barcosDes = barcosDestruidos;

        try {
            imgOriginal = ImageIO.read(new File("Imagenes\\derrota.jpg"));
            imgutilizar = imgOriginal.getScaledInstance(panelJuego.getWidth(), panelJuego.getHeight(),
                    BufferedImage.SCALE_SMOOTH);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void inicializarPantalla(PanelJuego panelJuego) {

    }

    @Override
    public void pintarPantalla(Graphics g) {
        g.drawImage(imgutilizar, 0, 0, null);
        g.setFont(fuente);
        g.setColor(Color.YELLOW);
        g.drawString("¡¡DERROTA!!", (panelJuego.getWidth() / 2) - 190, 80);
        g.drawString("Barcos destruidos: " + barcosDes, (panelJuego.getWidth() / 2) - 300, 160);

        g.setColor(Color.WHITE);
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

    @Override
    public void presionarTeclas(KeyEvent e) {
        // TODO Auto-generated method stub

    }

}
