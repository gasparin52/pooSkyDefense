package pooskydefense.modelo;

/**
 * Proyectil que baja verticalmente hasta alcanzar su altura de detonacion.
 * En ese momento deja de existir y crea una explosion.
 */
public class Misil extends Entidad {

    /** Velocidad de descenso por tick. */
    private final double velCaida;
    /** Altura en la cual debe detonar. */
    private final double altDetonacion;
    /** Indica si el misil sigue existiendo. */
    private boolean activo;

    /** Crea un misil con posicion inicial y parametros de caida. */
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

    /** Hace bajar el misil una cantidad fija. */
    public void descender() {
        if (!activo) {
            return;
        }

        setAltitud(altitud - velCaida);
    }

    /** Informa si ya llego a la altura en la que debe explotar. */
    public boolean debeExplotar() {
        return activo && altitud <= altDetonacion;
    }

    /** Desactiva el misil y devuelve la explosion generada en su posicion actual. */
    public Explosion explotar() {
        this.activo = false;
        return new Explosion(posX, altitud);
    }

    /** Devuelve la velocidad de descenso. */
    public double getVelCaida() {
        return velCaida;
    }

    /** Devuelve la altitud programada para explotar. */
    public double getAltDetonacion() {
        return altDetonacion;
    }

    /** Informa si el misil sigue activo. */
    public boolean isActivo() {
        return activo;
    }
}
