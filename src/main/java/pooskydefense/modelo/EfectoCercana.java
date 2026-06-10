package pooskydefense.modelo;

/**
 * Caso de impacto cercano.
 * No da puntos y quita una porcion importante de energia.
 */
public class EfectoCercana implements EfectoExplosion {

    /** Porcentaje de energia que se pierde en un impacto cercano. */
    private static final double DANO_CERCANO = 40;

    @Override
    public void aplicar(Jugador jugador, Avion avion) {
        avion.recibirDano(DANO_CERCANO);
    }
}
