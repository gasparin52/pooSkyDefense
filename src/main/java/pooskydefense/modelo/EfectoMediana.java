package pooskydefense.modelo;

/**
 * Efecto de una explosion a distancia media.
 */
public class EfectoMediana implements EfectoExplosion {

    private static final int PUNTOS_MEDIANA = 20;
    private static final double DANO_MEDIANO = 20;

    @Override
    public void aplicar(Jugador jugador, Avion avion) {
        jugador.agregarPuntos(PUNTOS_MEDIANA);
        avion.recibirDano(DANO_MEDIANO);
    }
}
