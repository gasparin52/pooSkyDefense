package pooskydefense.modelo;

/**
 * Resultado de la detonacion de un misil.
 * La explosion mide distancia al avion y delega el efecto concreto
 * en una estrategia que decide puntos, dano o perdida de vida.
 */
public class Explosion {

    /** Posicion horizontal donde ocurrio la explosion. */
    private final double posX;
    /** Altitud donde ocurrio la explosion. */
    private final double altitud;

    /** Crea una explosion en la posicion recibida. */
    public Explosion(double posX, double altitud) {
        this.posX = posX;
        this.altitud = altitud;
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

    /** Devuelve los puntos que corresponden para una distancia dada. */
    public int calcularPuntos(double distancia) {
        if (distancia > 150) {
            return 40;
        }
        if (distancia >= 80) {
            return 20;
        }
        return 0;
    }

    /** Selecciona la estrategia de impacto segun el rango de distancia. */
    private EfectoExplosion seleccionarEfecto(double distancia) {
        if (distancia > 150) {
            return new EfectoLejana();
        }
        if (distancia >= 80) {
            return new EfectoMediana();
        }
        if (distancia >= 20) {
            return new EfectoCercana();
        }
        return new EfectoCritica();
    }

    /** Devuelve la posicion horizontal de la explosion. */
    public double getPosX() {
        return posX;
    }

    /** Devuelve la altitud de la explosion. */
    public double getAltitud() {
        return altitud;
    }
}
