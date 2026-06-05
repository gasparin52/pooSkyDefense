package pooskydefense.modelo;

/**
 * Modela al jugador y su estado global.
 * Guarda puntos, vidas y el avion que esta controlando.
 */
public class Jugador {

    /** Cantidad inicial de vidas al arrancar la partida. */
    private static final int VIDAS_INICIALES = 3;
    /** Cada vez que el puntaje supera este umbral se gana una vida. */
    private static final int PUNTOS_VIDA_EXTRA = 1000;

    /** Puntaje acumulado del jugador. */
    private int puntos;
    /** Vidas actualmente disponibles. */
    private int vidas;
    /** Proximo umbral de puntos que entrega una vida extra. */
    private int puntosProximaVida;
    /** Avion asociado al jugador. */
    private final Avion avion;

    /** Crea un jugador con vidas iniciales y puntaje en cero. */
    public Jugador(Avion avion) {
        this.avion = avion;
        this.puntos = 0;
        this.vidas = VIDAS_INICIALES;
        this.puntosProximaVida = PUNTOS_VIDA_EXTRA;
    }

    /** Suma puntos y revisa automaticamente si corresponde vida extra. */
    public void agregarPuntos(int puntos) {
        if (puntos <= 0) {
            return;
        }

        this.puntos += puntos;
        while (this.puntos >= puntosProximaVida) {
            ganarVida();
            puntosProximaVida += PUNTOS_VIDA_EXTRA;
        }
    }

    /** Resta una vida si todavia quedaban disponibles. */
    public void perderVida() {
        if (vidas > 0) {
            vidas--;
        }
    }

    /** Suma una vida al jugador. */
    public void ganarVida() {
        vidas++;
    }

    /** Devuelve el puntaje actual. */
    public int getPuntos() {
        return puntos;
    }

    /** Devuelve las vidas actuales. */
    public int getVidas() {
        return vidas;
    }

    /** Devuelve el proximo umbral para conseguir otra vida. */
    public int getPuntosProximaVida() {
        return puntosProximaVida;
    }

    /** Devuelve el avion controlado por el jugador. */
    public Avion getAvion() {
        return avion;
    }

    /** Informa si el jugador puede seguir jugando. */
    public boolean estaVivo() {
        return vidas > 0;
    }

    /** Reinicia el avion tras perderse una vida, dejandolo en una posicion segura. */
    public void reiniciarAvion() {
        avion.restaurar();
        avion.cambiarAltitud(3000);
        avion.setPosX(500);
    }
}
