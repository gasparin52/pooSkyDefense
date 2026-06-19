package pooskydefense.modelo;

//Base comun para cualquier objeto del juego con posicion y altitud.

public abstract class Entidad {
    protected static final double POSICION_MINIMA_X = 0;
    protected static final double POSICION_MAXIMA_X = 1000;
    protected static final double ALTITUD_MINIMA_SEGURA = 1000;
    protected static final double ALTITUD_MAXIMA_SEGURA = 5000;

    protected double posX;
    protected double altitud;

    public Entidad(double posX, double altitud) {
        this.posX = limitar(posX, POSICION_MINIMA_X, POSICION_MAXIMA_X);
        this.altitud = limitar(altitud, 0, ALTITUD_MAXIMA_SEGURA);
    }

    public double getPosX() {
        return posX;
    }

    public void setPosX(double posX) {
        this.posX = limitar(posX, POSICION_MINIMA_X, POSICION_MAXIMA_X);
    }

    public double getAltitud() {
        return altitud;
    }

    public void setAltitud(double altitud) {
        this.altitud = limitar(altitud, 0, ALTITUD_MAXIMA_SEGURA);
    }

    public abstract void mover(Direccion d);

    protected static double limitar(double valor, double minimo, double maximo) {
        return Math.max(minimo, Math.min(maximo, valor));
    }
}
