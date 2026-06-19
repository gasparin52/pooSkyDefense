package pooskydefense.modelo;

/**
 * Efecto de una explosion lejana.
 */
public class EfectoLejana implements EfectoExplosion {

    private static final int PUNTOS_LEJANA = 40;

    @Override
    public void aplicar(Jugador jugador, Avion avion) {
        jugador.agregarPuntos(PUNTOS_LEJANA);
    }
}
