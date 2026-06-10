package pooskydefense.modelo;

/**
 * Caso de esquive perfecto.
 * La explosion queda lejos y solo otorga puntos.
 */
public class EfectoLejana implements EfectoExplosion {

    /** Puntos obtenidos por esquive perfecto. */
    private static final int PUNTOS_LEJANA = 40;

    @Override
    public void aplicar(Jugador jugador, Avion avion) {
        jugador.agregarPuntos(PUNTOS_LEJANA);
    }
}
