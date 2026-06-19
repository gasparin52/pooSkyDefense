package pooskydefense.modelo;

import java.util.concurrent.ThreadLocalRandom;

// Enemigo aereo que atraviesa la pantalla una sola vez por nivel.

public class Drone extends Entidad {

    private static final double ALTITUD_MINIMA_DRON = 3500;
    private static final double ALTITUD_MAXIMA_DRON = ALTITUD_MAXIMA_SEGURA;

    private final double velocidad;
    private boolean activo;
    private boolean desplegado;
    private Direccion direccionActual;

    public Drone(double velocidad) {
        super(0, ALTITUD_MINIMA_DRON);
        this.velocidad = velocidad;
        this.activo = false;
        this.desplegado = false;
        this.direccionActual = Direccion.DERECHA;
        asignarExtremoAleatorio();
    }

    public void activar() {
        if (desplegado) {
            return;
        }

        this.activo = true;
        this.desplegado = true;
        asignarExtremoAleatorio();
    }

    public void desactivar() {
        this.activo = false;
    }

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

        if (direccion == Direccion.DERECHA) {
            setPosX(posX + velocidad);
        } else {
            setPosX(posX - velocidad);
        }
    }

    public void mover() {
        mover(direccionActual);
    }

    public Misil lanzarMisil(double velMisil) {
        double altitudDetonacion = ThreadLocalRandom.current().nextDouble(1200, ALTITUD_MAXIMA_SEGURA + 1);
        return new Misil(posX, altitud, velMisil, altitudDetonacion);
    }

    public boolean llegoAlBordeOpuesto() {
        if (direccionActual == Direccion.DERECHA) {
            return posX >= POSICION_MAXIMA_X;
        }
        return posX <= POSICION_MINIMA_X;
    }

    public double getVelocidad() {
        return velocidad;
    }

    public boolean isActivo() {
        return activo;
    }

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
