package pooskydefense.controlador;

import pooskydefense.modelo.Direccion;
import pooskydefense.modelo.Game;

//Traduce acciones externas en mensajes hacia el modelo.

public class Controlador {

    private final Game game;

    public Controlador(Game game) {
        this.game = game;
    }

    public void moverAvion(Direccion direccion) {
        game.getJugador().getAvion().mover(direccion);
    }

    public void cambiarAltitudAvion(double altitud) {
        game.getJugador().getAvion().cambiarAltitud(altitud);
    }

    public void subirAvion() {
        double altitudActual = game.getJugador().getAvion().getAltitud();
        game.getJugador().getAvion().cambiarAltitud(altitudActual + 100);
    }

    public void bajarAvion() {
        double altitudActual = game.getJugador().getAvion().getAltitud();
        game.getJugador().getAvion().cambiarAltitud(altitudActual - 100);
    }
}
