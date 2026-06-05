package pooskydefense.modelo;

/**
 * Representa el avion del jugador.
 * Tiene energia, se mueve horizontalmente y puede cambiar de altitud
 * solo dentro del rango seguro indicado por los requerimientos.
 */
public class Avion extends Entidad {

    /** Energia inicial del avion al comenzar la partida o tras reiniciarse. */
    private static final double ENERGIA_INICIAL = 100;
    /** Cantidad de pixeles o unidades que avanza por cada movimiento horizontal. */
    private static final double DESPLAZAMIENTO = 10;

    /** Energia restante del avion. */
    private double energia;

    /** Crea un avion con energia completa y posicion inicial valida. */
    public Avion(double posX, double altitud) {
        super(posX, ALTITUD_MINIMA_SEGURA);
        this.energia = ENERGIA_INICIAL;
        cambiarAltitud(altitud);
        setPosX(posX);
    }

    @Override
    public void mover(Direccion direccion) {
        // Si va a la derecha se suma desplazamiento; si no, se resta.
        // setPosX evita que salga de pantalla.
        if (direccion == Direccion.DERECHA) {
            setPosX(posX + DESPLAZAMIENTO);
            return;
        }

        setPosX(posX - DESPLAZAMIENTO);
    }

    /** Cambia la altitud solo si queda entre 1000 y 5000 metros inclusive. */
    public void cambiarAltitud(double nuevaAltitud) {
        if (nuevaAltitud >= ALTITUD_MINIMA_SEGURA && nuevaAltitud <= ALTITUD_MAXIMA_SEGURA) {
            altitud = nuevaAltitud;
        }
    }

    /** Resta energia segun el porcentaje de dano recibido. */
    public void recibirDano(double porcentaje) {
        energia -= porcentaje;

        if (energia < 0) {
            energia = 0;
        }
    }

    /** Devuelve la energia actual del avion. */
    public double getEnergia() {
        return energia;
    }

    /** Informa si la energia llego a cero. */
    public boolean estaDestruido() {
        return energia <= 0;
    }

    /** Restaura la energia completa del avion. */
    public void restaurar() {
        energia = ENERGIA_INICIAL;
    }
}
