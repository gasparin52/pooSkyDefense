package pooskydefense.modelo;

/**
 * Caso de esquive parcial.
 * Da puntos pero tambien reduce energia del avion.
 */
public class EfectoMediana implements EfectoExplosion {

    /** Puntos obtenidos por esquive parcial. */
    private static final int PUNTOS_MEDIANA = 20;
    /** Porcentaje de energia que se pierde en un impacto mediano. */
    private static final double DANO_MEDIANO = 20;

    @Override
    public void aplicar(Jugador jugador, Avion avion) {
        jugador.agregarPuntos(PUNTOS_MEDIANA);
        avion.recibirDano(DANO_MEDIANO);
    }
}
