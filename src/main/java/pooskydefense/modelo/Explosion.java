package pooskydefense.modelo;

//Resultado de la detonacion de un misil.
//Mide la distancia al avion y delega el efecto en una estrategia.

public class Explosion extends Entidad {

    private static final double DISTANCIA_LEJANA = 150;
    private static final double DISTANCIA_MEDIANA = 80;
    private static final double DISTANCIA_CERCANA = 20;

    public Explosion(double posX, double altitud) {
        super(posX, altitud);
    }

    @Override
    public void mover(Direccion d) {
    }

    public double calcularDistancia(Avion avion) {
        double distanciaX = posX - avion.getPosX();
        double distanciaY = altitud - avion.getAltitud();
        return Math.hypot(distanciaX, distanciaY);
    }

    public void aplicarEfecto(Jugador jugador, Avion avion) {
        double distancia = calcularDistancia(avion);
        EfectoExplosion efecto = seleccionarEfecto(distancia);
        efecto.aplicar(jugador, avion);
    }

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
