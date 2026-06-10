package pooskydefense.modelo;

/**
 * Caso de impacto directo.
 * El avion no pierde energia: el jugador pierde una vida inmediatamente.
 */
public class EfectoCritica implements EfectoExplosion {

    @Override
    public void aplicar(Jugador jugador, Avion avion) {
        jugador.perderVida();
        // Restaurar el avion para evitar que Nivel.tick() detecte estaDestruido()
        // y descuente una segunda vida en el mismo tick.
        avion.restaurar();
    }
}
