package pooskydefense.modelo;

/**
 * Efecto de un impacto critico.
 */
public class EfectoCritica implements EfectoExplosion {

    @Override
    public void aplicar(Jugador jugador, Avion avion) {
        jugador.perderVida();
        // Se restaura para evitar que el mismo tick descuente una segunda vida.
        avion.restaurar();
    }
}
