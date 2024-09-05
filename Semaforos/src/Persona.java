import javax.swing.*;
import java.awt.*;

class Persona {
    private int x, y;
    private int velocidad;
    private boolean cruzando;
    private Image imagenPersona;

    private static final int RADIO_DETECCION_CALLE = 100; // Radio de detección de proximidad a la calle

    public Persona(int x, int y, int velocidad, String rutaImagen) {
        this.x = x;
        this.y = y;
        this.velocidad = velocidad;
        this.cruzando = false;
        this.imagenPersona = new ImageIcon(rutaImagen).getImage();
    }

    public void actualizar(Semaforo semaforo, int posicionCalleY) {
        if (estaCercaDeCalle(posicionCalleY)) {
            if (semaforo.getColor() == Color.RED) {
                cruzando = true;
            } else {
                cruzando = false; // Detener el cruce si no está en rojo
            }
        } else {
            cruzando = true; // Continuar moviéndose si no está cerca de la calle
        }

        mover();
    }

    private boolean estaCercaDeCalle(int posicionCalleY) {
        return Math.abs(y - posicionCalleY) < RADIO_DETECCION_CALLE;
    }

    private void mover() {
        if (cruzando) {
            y -= velocidad;  // La persona se mueve hacia arriba
        }

        // Reiniciar la posición cuando sale de la pantalla
        if (y < -10) {
            y = 800;
            cruzando = false;  // Detener el cruce hasta el próximo semáforo rojo
        }
    }

    public void dibujar(Graphics g) {
        g.drawImage(imagenPersona, x, y, 50, 50, null);
    }
}