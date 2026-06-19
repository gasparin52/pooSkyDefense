package pooskydefense.modelo;

/**
 * Estrategia que resuelve el efecto de una explosion segun su distancia.
 */
public interface EfectoExplosion {
    void aplicar(Jugador jugador, Avion avion);
}
