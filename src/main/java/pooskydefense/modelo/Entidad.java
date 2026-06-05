package pooskydefense.modelo;

/**
 * Clase base para cualquier objeto del juego que tenga posicion horizontal
 * y altitud. Centraliza limites compartidos y validaciones basicas.
 */
public abstract class Entidad {
    /** Limite izquierdo de la pantalla. */
    protected static final double POSICION_MINIMA_X = 0;
    /** Limite derecho de la pantalla. */
    protected static final double POSICION_MAXIMA_X = 1000;
    /** Altitud minima permitida para zonas seguras del avion. */
    protected static final double ALTITUD_MINIMA_SEGURA = 1000;
    /** Altitud maxima permitida dentro del juego. */
    protected static final double ALTITUD_MAXIMA_SEGURA = 5000;

    /** Posicion horizontal actual. */
    protected double posX;
    /** Altitud actual. */
    protected double altitud;

    /**
     * Construye una entidad aplicando limites para no crearla fuera del area.
     */
    public Entidad(double posX, double altitud) {
        this.posX = limitar(posX, POSICION_MINIMA_X, POSICION_MAXIMA_X);
        this.altitud = limitar(altitud, 0, ALTITUD_MAXIMA_SEGURA);
    }

    /** Devuelve la posicion horizontal actual. */
    public double getPosX() {
        return posX;
    }

    /** Cambia la posicion horizontal respetando los bordes. */
    public void setPosX(double posX) {
        this.posX = limitar(posX, POSICION_MINIMA_X, POSICION_MAXIMA_X);
    }

    /** Devuelve la altitud actual. */
    public double getAltitud() {
        return altitud;
    }

    /** Cambia la altitud evitando valores menores que cero o excesivos. */
    public void setAltitud(double altitud) {
        this.altitud = limitar(altitud, 0, ALTITUD_MAXIMA_SEGURA);
    }

    /** Cada subclase define como interpreta el movimiento horizontal. */
    public abstract void mover(Direccion d);

    /** Funcion utilitaria para encerrar un valor entre minimo y maximo. */
    protected double limitar(double valor, double minimo, double maximo) {
        return Math.max(minimo, Math.min(maximo, valor));
    }
}
