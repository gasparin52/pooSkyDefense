package pooskydefense.modelo;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Enemigo aereo que entra por un extremo de la pantalla y cruza hacia el otro.
 * Cada drone puede desplegarse una sola vez por nivel.
 */
public class Drone extends Entidad {

    /** Altura minima aleatoria para aparicion de drones. */
    private static final double ALTITUD_MINIMA_DRON = 1500;
    /** Altura maxima aleatoria para aparicion de drones. */
    private static final double ALTITUD_MAXIMA_DRON = 4500;

    /** Velocidad horizontal propia de este drone. */
    private final double velocidad;
    /** Indica si el drone esta actualmente en pantalla. */
    private boolean activo;
    /** Indica si el drone ya fue usado alguna vez en el nivel. */
    private boolean desplegado;
    /** Direccion en la que se movera durante su recorrido. */
    private Direccion direccionActual;

    /** Crea un drone apagado y le asigna una futura aparicion aleatoria. */
    public Drone(double velocidad) {
        super(0, ALTITUD_MINIMA_DRON);
        this.velocidad = velocidad;
        this.activo = false;
        this.desplegado = false;
        this.direccionActual = Direccion.DERECHA;
        asignarExtremoAleatorio();
    }

    /** Activa el drone solo si todavia no habia sido desplegado. */
    public void activar() {
        if (desplegado) {
            return;
        }

        this.activo = true;
        this.desplegado = true;
        asignarExtremoAleatorio();
    }

    /** Saca al drone de la pantalla sin borrarlo del escuadron. */
    public void desactivar() {
        this.activo = false;
    }

    /** Elige si arranca desde la izquierda o la derecha y define su altitud. */
    private void asignarExtremoAleatorio() {
        boolean desdeIzquierda = ThreadLocalRandom.current().nextBoolean();
        this.posX = desdeIzquierda ? POSICION_MINIMA_X : POSICION_MAXIMA_X;
        this.altitud = ThreadLocalRandom.current().nextDouble(ALTITUD_MINIMA_DRON, ALTITUD_MAXIMA_DRON + 1);
        this.direccionActual = desdeIzquierda ? Direccion.DERECHA : Direccion.IZQUIERDA;
    }

    @Override
    public void mover(Direccion direccion) {
        if (!activo) {
            return;
        }

        // El desplazamiento usa la velocidad propia del drone.
        if (direccion == Direccion.DERECHA) {
            setPosX(posX + velocidad);
        } else {
            setPosX(posX - velocidad);
        }
    }

    /** Mueve usando la direccion que le toco al aparecer. */
    public void mover() {
        mover(direccionActual);
    }

    /** Crea un misil en la misma posicion del drone con altura de detonacion aleatoria. */
    public Misil lanzarMisil(double velMisil) {
        double altitudDetonacion = ThreadLocalRandom.current().nextDouble(1200, 4500 + 1);
        return new Misil(posX, altitud, velMisil, altitudDetonacion);
    }

    /** Sirve para saber si ya llego al borde contrario y debe salir de escena. */
    public boolean llegoAlBordeOpuesto() {
        if (direccionActual == Direccion.DERECHA) {
            return posX >= POSICION_MAXIMA_X;
        }
        return posX <= POSICION_MINIMA_X;
    }

    /** Devuelve la velocidad horizontal del drone. */
    public double getVelocidad() {
        return velocidad;
    }

    /** Informa si el drone esta activo en pantalla. */
    public boolean isActivo() {
        return activo;
    }

    /** Informa si ya fue usado antes en este nivel. */
    public boolean fueDesplegado() {
        return desplegado;
    }

    @Override
    public String toString() {
        return "Drone{posX=" + posX + ", altitud=" + altitud
                + ", velocidad=" + velocidad + ", activo=" + activo
                + ", desplegado=" + desplegado + ", dir=" + direccionActual + "}";
    }
}
