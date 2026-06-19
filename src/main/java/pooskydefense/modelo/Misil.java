package pooskydefense.modelo;

/**
 * Proyectil que desciende hasta su altura de detonacion.
 */
public class Misil extends Entidad {

    private final double velCaida;
    private final double altDetonacion;
    private boolean activo;

    public Misil(double posX, double altitud, double velCaida, double altDetonacion) {
        super(posX, altitud);
        this.velCaida = velCaida;
        this.altDetonacion = altDetonacion;
        this.activo = true;
    }

    @Override
    public void mover(Direccion direccion) {
        descender();
    }

    public void descender() {
        if (!activo) {
            return;
        }

        setAltitud(altitud - velCaida);
    }

    public boolean debeExplotar() {
        return activo && altitud <= altDetonacion;
    }

    public Explosion explotar() {
        this.activo = false;
        return new Explosion(posX, altitud);
    }

    public double getVelCaida() {
        return velCaida;
    }

    public double getAltDetonacion() {
        return altDetonacion;
    }

    public boolean isActivo() {
        return activo;
    }

    @Override
    public String toString() {
        return "Misil{posX=" + posX + ", altitud=" + altitud
                + ", velCaida=" + velCaida + ", altDetonacion=" + altDetonacion
                + ", activo=" + activo + "}";
    }
}
