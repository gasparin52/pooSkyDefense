package pooskydefense.modelo;

/**
 * Efecto de una explosion cercana.
 */
public class EfectoCercana implements EfectoExplosion {

    private static final double DANO_CERCANO = 40;

    @Override
    public void aplicar(Jugador jugador, Avion avion) {
        avion.recibirDano(DANO_CERCANO);
    }
}
