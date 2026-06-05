package pooskydefense.controlador;

import pooskydefense.modelo.Direccion;
import pooskydefense.modelo.Game;

/**
 * Capa de control del juego.
 * Su rol es traducir acciones externas en mensajes hacia el modelo.
 */
public class Controlador {

    /** Referencia al juego que se esta controlando. */
    private final Game game;

    /** Recibe el juego sobre el cual operara el controlador. */
    public Controlador(Game game) {
        this.game = game;
    }

    /** Ordena al avion moverse a izquierda o derecha. */
    public void moverAvion(Direccion direccion) {
        game.getJugador().getAvion().mover(direccion);
    }

    /** Ordena al avion cambiar su altitud si la nueva altura es valida. */
    public void cambiarAltitudAvion(double altitud) {
        game.getJugador().getAvion().cambiarAltitud(altitud);
    }
}
