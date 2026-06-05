package pooskydefense.modelo;

/**
 * Caso de impacto cercano.
 * No da puntos y quita una porcion importante de energia.
 */
public class EfectoCercana implements EfectoExplosion {

    @Override
    public void aplicar(Jugador jugador, Avion avion) {
        avion.recibirDano(40);
    }
}
