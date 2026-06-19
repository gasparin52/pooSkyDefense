package pooskydefense.app;

import pooskydefense.controlador.Controlador;
import pooskydefense.modelo.Game;
import pooskydefense.vista.VentanaJuego;

import javax.swing.SwingUtilities;

// Punto de entrada del programa
public class Main {
    public static void main(String[] args) {
        Game game = new Game();
        Controlador controlador = new Controlador(game);

        // SwingUtilities.invokeLater asegura que la ventana se cree en el hilo correcto
        SwingUtilities.invokeLater(() -> new VentanaJuego(game, controlador));
    }
}
