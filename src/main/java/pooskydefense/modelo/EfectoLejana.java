package pooskydefense.modelo;

/**
 * Caso de esquive perfecto.
 * La explosion queda lejos y solo otorga puntos.
 */
public class EfectoLejana implements EfectoExplosion {

    @Override
    public void aplicar(Jugador jugador, Avion avion) {
        jugador.agregarPuntos(40);
    }
}
