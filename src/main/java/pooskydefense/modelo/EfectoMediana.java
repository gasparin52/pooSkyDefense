package pooskydefense.modelo;

/**
 * Caso de esquive parcial.
 * Da puntos pero tambien reduce energia del avion.
 */
public class EfectoMediana implements EfectoExplosion {

    @Override
    public void aplicar(Jugador jugador, Avion avion) {
        jugador.agregarPuntos(20);
        avion.recibirDano(20);
    }
}
