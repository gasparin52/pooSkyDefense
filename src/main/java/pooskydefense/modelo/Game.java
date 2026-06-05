package pooskydefense.modelo;

/**
 * Objeto principal del dominio.
 * Coordina jugador, nivel actual y progresion de dificultad.
 */
public class Game {

    /** Cada nivel incrementa las velocidades y frecuencia en un 15%. */
    private static final double FACTOR_DIFICULTAD = 1.15;

    /** Nivel actual mostrado por el juego. */
    private int nivel;
    /** Numero del nivel usado para crear el siguiente objeto Nivel. */
    private int numero;
    /** Velocidad base de los drones para el nivel actual. */
    private double velDrones;
    /** Velocidad base de los misiles para el nivel actual. */
    private double velMisiles;
    /** Frecuencia base de disparo del nivel actual. */
    private double frecDisparos;
    /** Copia del puntaje del jugador para exponerlo facilmente. */
    private int puntuacion;
    /** Nivel que se esta jugando en este momento. */
    private Nivel nivelActual;
    /** Jugador de la partida. */
    private final Jugador jugador;

    /** Construye una partida nueva con valores iniciales de dificultad. */
    public Game() {
        this.nivel = 1;
        this.numero = 1;
        this.velDrones = 12;
        this.velMisiles = 150;
        this.frecDisparos = 0.25;
        this.jugador = new Jugador(new Avion(500, 3000));
        iniciarNivel();
    }

    /** Crea el nivel actual usando las velocidades configuradas en el juego. */
    public void iniciarNivel() {
        this.nivelActual = new Nivel(numero, velDrones, velMisiles, frecDisparos);
    }

    /** Ejecuta un paso del juego y evalua si hay que avanzar de nivel. */
    public void tick() {
        if (!jugador.estaVivo()) {
            return;
        }

        nivelActual.tick(jugador);
        puntuacion = jugador.getPuntos();

        if (nivelActual.estaCompletado()) {
            avanzarNivel();
        }
    }

    /** Incrementa la dificultad multiplicando los parametros principales. */
    public void calcularVelocidades() {
        velDrones *= FACTOR_DIFICULTAD;
        velMisiles *= FACTOR_DIFICULTAD;
        frecDisparos *= FACTOR_DIFICULTAD;
    }

    /** Otorga bonus de nivel y crea automaticamente el siguiente nivel. */
    public void avanzarNivel() {
        jugador.agregarPuntos(300);
        puntuacion = jugador.getPuntos();
        numero++;
        nivel = numero;
        calcularVelocidades();
        iniciarNivel();
    }

    /** Devuelve el numero de nivel visible para la partida. */
    public int getNivel() {
        return nivel;
    }

    /** Devuelve el numero interno usado para crear niveles. */
    public int getNumero() {
        return numero;
    }

    /** Devuelve la velocidad actual de drones. */
    public double getVelDrones() {
        return velDrones;
    }

    /** Devuelve la velocidad actual de misiles. */
    public double getVelMisiles() {
        return velMisiles;
    }

    /** Devuelve la frecuencia actual de disparo. */
    public double getFrecDisparos() {
        return frecDisparos;
    }

    /** Devuelve la puntuacion copiada desde el jugador. */
    public int getPuntuacion() {
        return puntuacion;
    }

    /** Devuelve el nivel en juego. */
    public Nivel getNivelActual() {
        return nivelActual;
    }

    /** Devuelve el jugador asociado a la partida. */
    public Jugador getJugador() {
        return jugador;
    }
}
