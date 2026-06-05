package pooskydefense.app;

import pooskydefense.controlador.Controlador;
import pooskydefense.modelo.Direccion;
import pooskydefense.modelo.Game;

/**
 * Punto de entrada del programa.
 * Aca se crea el juego, se simulan un par de comandos del usuario
 * y luego se ejecutan varios ticks para ver avanzar la logica.
 */
public class Main {
    /**
     * Metodo principal de Java.
     * No usa los argumentos de consola porque esta pensado como demo minima.
     */
    public static void main(String[] args) {
        // Se crea el juego completo con su jugador, avion y primer nivel.
        Game game = new Game();

        // El controlador representa la capa que recibe acciones del usuario.
        Controlador controlador = new Controlador(game);

        // Simulamos un movimiento horizontal y un cambio de altitud.
        controlador.moverAvion(Direccion.DERECHA);
        controlador.cambiarAltitudAvion(3200);

        // Cada iteracion representa un instante de juego.
        for (int i = 1; i <= 20; i++) {
            game.tick();
        }

        // Se muestran algunos datos finales para verificar el estado.
        System.out.println("Nivel actual: " + game.getNivel());
        System.out.println("Puntos: " + game.getJugador().getPuntos());
        System.out.println("Vidas: " + game.getJugador().getVidas());
        System.out.println("Energia del avion: " + game.getJugador().getAvion().getEnergia());
    }
}
