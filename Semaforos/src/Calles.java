import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Calles extends JPanel {

    private Semaforo semaforo1;
    private List<Vehiculo> vehiculos;
    private Persona persona;
    int ancho = 300;

    public Calles() {
        semaforo1 = new Semaforo(0, 0, this, true, 2, 1);

        vehiculos = new ArrayList<>();
        vehiculos.add(new Vehiculo(0, 300, 5, Color.RED, Vehiculo.DIRECCION_HORIZONTAL, "Semaforos/src/images/carroRojo.png"));

        // Inicializar la persona
        persona = new Persona(700, 800, 2, "Semaforos/src/images/persona.png");

        Timer timer = new Timer(30, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actualizar();
                repaint();
            }
        });
        timer.start();
    }

    private void actualizar() {
        for (Vehiculo vehiculo : vehiculos) {
            vehiculo.mover(semaforo1);  // Mueve los vehículos
        }

        // Actualizar el estado de la persona según su proximidad a la calle y el color del semáforo
        int posicionCalleY = getHeight() / 2; // Centro de la pantalla en Y (posición de la calle)
        persona.actualizar(semaforo1, posicionCalleY);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int width = getWidth();
        int height = getHeight();

        int verticalSemaforoX = width / 2 - 170;
        int verticalSemaforoY = height / 2 - 190;


        semaforo1.setPosition(verticalSemaforoX, verticalSemaforoY);

        // Color de fondo
        g.setColor(new Color(138, 198, 123));
        g.fillRect(0, 0, width, height);

        // Dibujar la calle vertical
        g.setColor(Color.DARK_GRAY);

        // Dibujar la calle horizontal
        g.fillRect(0, height / 2 - ancho / 2, width, ancho);

        // Delimitando el Centro
        int centroInicioX = width / 2 - ancho / 2;
        int centroFinX = width / 2 + ancho / 2;
        int centroInicioY = height / 2 - ancho / 2;
        int centroFinY = height / 2 + ancho / 2;

        // Dibujando líneas discontinuas en la calle vertical
        g.setColor(Color.yellow);
        int longitudLinea = 30;
        int espacio = 20;

        // Dibujando líneas discontinuas en la calle horizontal
        for (int x = 0; x < width; x += longitudLinea + espacio) {
            if (x < centroInicioX || x > centroFinX) {
                g.fillRect(x, height / 2 - 5, longitudLinea, 10);
            }
        }

        // **Dibujar el paso peatonal en el centro de la calle horizontal**
        int pasoPeatonalAncho = 10; // Ancho de cada línea del paso peatonal
        int pasoPeatonalEspacio = 10; // Espacio entre las líneas del paso peatonal
        int pasoPeatonalLongitud = ancho; // Longitud de las líneas del paso peatonal
        g.setColor(Color.WHITE);
        for (int i = 0; i < ancho / 2; i += pasoPeatonalAncho + pasoPeatonalEspacio) {
            g.fillRect(centroInicioX + i, height / 2 - ancho / 2, pasoPeatonalAncho, pasoPeatonalLongitud);
        }

        // Dibujar los semáforos
        semaforo1.dibujar(g);

        // Dibujar los vehículos
        for (Vehiculo vehiculo : vehiculos) {
            vehiculo.dibujar(g);
        }

        // Dibujar la persona
        persona.dibujar(g);
    }
    public static void main(String[] args) {
        JFrame frame = new JFrame("Semáforos");
        Calles calles = new Calles();

        // Configurar para que se abra en pantalla completa
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.add(calles);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}

