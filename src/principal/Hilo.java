package principal;


import java.awt.Color;

/**
 * @author Ricardo Martín García
 * 
 * Hilo que sirve apr cambiar el color a la pantalla de inicio continuamente
*/

public class Hilo extends Thread {

    int rojo;
    int azul;
    int verde;
    boolean continua;
    int espera;
    Color colorRef;

    public Hilo() {
        rojo = 0;
        azul = 0;
        verde = 0;
        continua = true;
        espera = 5;

    }

    @Override
    public void run() {

        while (continua) {

            while (rojo < 255 && continua == true) {
                try {
                    sleep(espera);

                } catch (Exception e) {
                    // TODO: handle exception
                }

                colorRef = new Color(rojo, verde, azul);

                if (azul > 0) {
                    azul--;
                }
                rojo++;
            }
            while (verde < 255 && continua == true) {
                try {
                    sleep(espera);

                } catch (Exception e) {
                    // TODO: handle exception
                }
                colorRef = new Color(rojo, verde, azul);
                rojo--;
                verde++;
            }
            while (azul < 255 && continua == true) {
                try {
                    sleep(espera);

                } catch (Exception e) {
                    // TODO: handle exception
                }
                colorRef = new Color(rojo, verde, azul);
                verde--;
                azul++;
            }
        }

    }

    public Color getColor() {
        return colorRef;
    }

}
