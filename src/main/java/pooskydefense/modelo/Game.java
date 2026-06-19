package pooskydefense.modelo;

/**
 * Objeto principal del dominio.
 * Coordina jugador, nivel actual y progresion de dificultad.
 */
public class Game {

    private static final double FACTOR_DIFICULTAD = 1.15;
    private static final double VEL_DRONES_INICIAL = 12;
    private static final double VEL_MISILES_INICIAL = 15;
    private static final double FREC_DISPAROS_INICIAL = 0.25;

    private int nivel;
    private double velDrones;
    private double velMisiles;
    private double frecDisparos;
    private Nivel nivelActual;
    private final Jugador jugador;

    public Game() {
        this.nivel = 1;
        this.velDrones = VEL_DRONES_INICIAL;
        this.velMisiles = VEL_MISILES_INICIAL;
        this.frecDisparos = FREC_DISPAROS_INICIAL;
        this.jugador = new Jugador(new Avion(500, 3000));
        iniciarNivel();
    }

    public void iniciarNivel() {
        this.nivelActual = new Nivel(nivel, velDrones, velMisiles, frecDisparos);
    }

    public void tick() {
        if (!jugador.estaVivo()) {
            return;
        }

        nivelActual.tick(jugador);

        if (nivelActual.estaCompletado()) {
            avanzarNivel();
        }
    }

    public void calcularVelocidades() {
        velDrones *= FACTOR_DIFICULTAD;
        velMisiles *= FACTOR_DIFICULTAD;
        frecDisparos *= FACTOR_DIFICULTAD;
    }

    public void avanzarNivel() {
        jugador.agregarPuntos(300);
        nivel++;
        calcularVelocidades();
        iniciarNivel();
    }

    public int getNivel() {
        return nivel;
    }

    public double getVelDrones() {
        return velDrones;
    }

    public double getVelMisiles() {
        return velMisiles;
    }

    public double getFrecDisparos() {
        return frecDisparos;
    }

    public int getPuntuacion() {
        return jugador.getPuntos();
    }

    public Nivel getNivelActual() {
        return nivelActual;
    }

    public Jugador getJugador() {
        return jugador;
    }
}
