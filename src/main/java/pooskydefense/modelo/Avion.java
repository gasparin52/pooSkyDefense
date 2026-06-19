package pooskydefense.modelo;

// Avion controlado por el jugador.

public class Avion extends Entidad {

    private static final double ENERGIA_INICIAL = 100;
    private static final double DESPLAZAMIENTO = 10;
    private static final double ALTITUD_MAXIMA_AVION = 4200;

    private double energia;

    public Avion(double posX, double altitud) {
        super(posX, limitar(altitud, ALTITUD_MINIMA_SEGURA, ALTITUD_MAXIMA_AVION));
        this.energia = ENERGIA_INICIAL;
    }

    @Override
    public void mover(Direccion direccion) {
        if (direccion == Direccion.DERECHA) {
            setPosX(posX + DESPLAZAMIENTO);
            return;
        }

        setPosX(posX - DESPLAZAMIENTO);
    }

    public void cambiarAltitud(double nuevaAltitud) {
        if (nuevaAltitud >= ALTITUD_MINIMA_SEGURA && nuevaAltitud <= ALTITUD_MAXIMA_AVION) {
            altitud = nuevaAltitud;
        }
    }

    public void recibirDano(double porcentaje) {
        energia -= porcentaje;

        if (energia < 0) {
            energia = 0;
        }
    }

    public double getEnergia() {
        return energia;
    }

    public boolean estaDestruido() {
        return energia <= 0;
    }

    public void restaurar() {
        energia = ENERGIA_INICIAL;
    }

    @Override
    public String toString() {
        return "Avion{posX=" + posX + ", altitud=" + altitud + ", energia=" + energia + "}";
    }
}
