package pooskydefense.modelo;

/**
 * Estado global del jugador.
 */
public class Jugador {

    private static final int VIDAS_INICIALES = 3;
    private static final int PUNTOS_VIDA_EXTRA = 1000;

    private int puntos;
    private int vidas;
    private int puntosProximaVida;
    private final Avion avion;

    public Jugador(Avion avion) {
        this.avion = avion;
        this.puntos = 0;
        this.vidas = VIDAS_INICIALES;
        this.puntosProximaVida = PUNTOS_VIDA_EXTRA;
    }

    public void agregarPuntos(int puntos) {
        if (puntos <= 0) {
            return;
        }

        this.puntos += puntos;
        while (this.puntos >= puntosProximaVida) {
            ganarVida();
            puntosProximaVida += PUNTOS_VIDA_EXTRA;
        }
    }

    public void perderVida() {
        if (vidas > 0) {
            vidas--;
        }
    }

    public void ganarVida() {
        vidas++;
    }

    public int getPuntos() {
        return puntos;
    }

    public int getVidas() {
        return vidas;
    }

    public int getPuntosProximaVida() {
        return puntosProximaVida;
    }

    public Avion getAvion() {
        return avion;
    }

    public boolean estaVivo() {
        return vidas > 0;
    }

    public void reiniciarAvion() {
        avion.restaurar();
        avion.cambiarAltitud(3000);
        avion.setPosX(500);
    }

    @Override
    public String toString() {
        return "Jugador{puntos=" + puntos + ", vidas=" + vidas + ", avion=" + avion + "}";
    }
}
