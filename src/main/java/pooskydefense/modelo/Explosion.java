package pooskydefense.modelo;

/**
 * Resultado de la detonacion de un misil.
 * La explosion mide distancia al avion y delega el efecto concreto
 * en una estrategia que decide puntos, dano o perdida de vida.
 *
 * Extiende Entidad porque es un objeto posicionado en el mundo,
 * pero no se mueve, por lo que mover() es un no-op.
 */
public class Explosion extends Entidad {

    /** Umbral de distancia para considerar la explosion como lejana. */
    private static final double DISTANCIA_LEJANA = 150;
    /** Umbral de distancia para considerar la explosion como mediana. */
    private static final double DISTANCIA_MEDIANA = 80;
    /** Umbral de distancia para considerar la explosion como cercana. */
    private static final double DISTANCIA_CERCANA = 20;

    /** Crea una explosion en la posicion recibida. */
    public Explosion(double posX, double altitud) {
        super(posX, altitud);
    }

    /** Las explosiones no se mueven; este metodo no hace nada. */
    @Override
    public void mover(Direccion d) {
        // No-op: las explosiones son estaticas.
    }

    /** Calcula la distancia euclidiana entre la explosion y el avion. */
    public double calcularDistancia(Avion avion) {
        double distanciaX = posX - avion.getPosX();
        double distanciaY = altitud - avion.getAltitud();
        return Math.hypot(distanciaX, distanciaY);
    }

    /** Busca la estrategia correcta segun la distancia y la ejecuta. */
    public void aplicarEfecto(Jugador jugador, Avion avion) {
        double distancia = calcularDistancia(avion);
        EfectoExplosion efecto = seleccionarEfecto(distancia);
        efecto.aplicar(jugador, avion);
    }

    /** Selecciona la estrategia de impacto segun el rango de distancia. */
    private EfectoExplosion seleccionarEfecto(double distancia) {
        if (distancia > DISTANCIA_LEJANA) {
            return new EfectoLejana();
        }
        if (distancia >= DISTANCIA_MEDIANA) {
            return new EfectoMediana();
        }
        if (distancia >= DISTANCIA_CERCANA) {
            return new EfectoCercana();
        }
        return new EfectoCritica();
    }

    @Override
    public String toString() {
        return "Explosion{posX=" + posX + ", altitud=" + altitud + "}";
    }
}
