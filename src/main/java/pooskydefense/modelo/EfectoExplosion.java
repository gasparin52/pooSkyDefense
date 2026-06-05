package pooskydefense.modelo;

/**
 * Interfaz estrategia para aplicar el resultado de una explosion.
 * Cada implementacion representa un rango de distancia distinto.
 */
public interface EfectoExplosion {
    /** Aplica puntos, dano o perdida de vida segun la estrategia concreta. */
    void aplicar(Jugador jugador, Avion avion);
}
