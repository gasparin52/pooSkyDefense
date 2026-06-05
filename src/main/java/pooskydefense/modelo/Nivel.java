package pooskydefense.modelo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Representa un nivel concreto de la partida.
 * Aca viven el escuadron, los misiles en vuelo y las explosiones temporales.
 */
public class Nivel {

    /** Cada nivel arranca con 10 drones disponibles. */
    private static final int CANTIDAD_DRONES_POR_NIVEL = 10;

    /** Numero identificador del nivel. */
    private final int numero;
    /** Grupo que administra los drones de este nivel. */
    private final Escuadron escuadron;
    /** Misiles actualmente en vuelo. */
    private final List<Misil> misiles;
    /** Explosiones generadas en el tick actual. */
    private final List<Explosion> explosiones;
    /** Velocidad horizontal aplicada a cada drone creado en este nivel. */
    private final double velDrones;
    /** Velocidad de caida de los misiles de este nivel. */
    private final double velMisiles;
    /** Cantidad de disparos por tick aproximadamente. */
    private final double frecDisparos;
    /** Contador de tiempo usado para decidir cuando disparan los drones. */
    private double tiempoDesdeUltimoDisparo;

    /** Crea el nivel y prepara todas sus entidades iniciales. */
    public Nivel(int numero, double velDrones, double velMisiles, double frecDisparos) {
        this.numero = numero;
        this.escuadron = new Escuadron(CANTIDAD_DRONES_POR_NIVEL);
        this.misiles = new ArrayList<>();
        this.explosiones = new ArrayList<>();
        this.velDrones = velDrones;
        this.velMisiles = velMisiles;
        this.frecDisparos = frecDisparos;
        this.tiempoDesdeUltimoDisparo = 0;
        inicializarDrones();
    }

    /**
     * Ejecuta un tick completo del nivel.
     * El orden importa: primero aparecen y se mueven drones,
     * despues se actualizan misiles y finalmente se revisa energia del avion.
     */
    public void tick(Jugador jugador) {
        activarDroneSiCorresponde();
        moverDrones();
        actualizarMisiles(jugador);
        manejarDisparos();
        explosionsCleanup();

        if (jugador.getAvion().estaDestruido()) {
            jugador.perderVida();
            if (jugador.estaVivo()) {
                jugador.reiniciarAvion();
            }
        }
    }

    /** Crea los 10 drones del nivel y los guarda en el escuadron. */
    private void inicializarDrones() {
        for (int i = 0; i < CANTIDAD_DRONES_POR_NIVEL; i++) {
            escuadron.agregarDrone(new Drone(velDrones));
        }
    }

    /** Intenta activar otro drone si todavia no se alcanzo el maximo simultaneo. */
    private void activarDroneSiCorresponde() {
        escuadron.activarSiguienteDrone();
    }

    /** Mueve todos los drones activos y desactiva los que llegan al borde opuesto. */
    private void moverDrones() {
        for (Drone drone : escuadron.getDrones()) {
            if (!drone.isActivo()) {
                continue;
            }

            drone.mover();
            if (drone.llegoAlBordeOpuesto()) {
                drone.desactivar();
            }
        }
    }

    /** Controla cada cuanto disparan los drones segun la frecuencia del nivel. */
    private void manejarDisparos() {
        tiempoDesdeUltimoDisparo += 1;
        double ticksEntreDisparos = frecDisparos <= 0 ? Double.POSITIVE_INFINITY : 1 / frecDisparos;

        if (tiempoDesdeUltimoDisparo < ticksEntreDisparos) {
            return;
        }

        for (Drone drone : escuadron.getDrones()) {
            if (drone.isActivo()) {
                misiles.add(drone.lanzarMisil(velMisiles));
            }
        }
        tiempoDesdeUltimoDisparo = 0;
    }

    /** Baja los misiles y convierte en explosion a los que llegan a su altitud de detonacion. */
    private void actualizarMisiles(Jugador jugador) {
        Iterator<Misil> iterador = misiles.iterator();
        while (iterador.hasNext()) {
            Misil misil = iterador.next();
            misil.descender();

            if (!misil.debeExplotar()) {
                continue;
            }

            Explosion explosion = misil.explotar();
            explosion.aplicarEfecto(jugador, jugador.getAvion());
            explosiones.add(explosion);
            iterador.remove();
        }
    }

    /** Las explosiones duran un solo tick en este modelo simplificado. */
    private void explosionsCleanup() {
        explosiones.clear();
    }

    /** Un nivel termina cuando no quedan drones por salir, ni drones activos, ni misiles ni explosiones. */
    public boolean estaCompletado() {
        return !escuadron.quedanDronesPorSalir()
                && escuadron.dronesActivos() == 0
                && misiles.isEmpty()
                && explosiones.isEmpty();
    }

    /** Devuelve el numero del nivel. */
    public int getNumero() {
        return numero;
    }

    /** Devuelve el escuadron asociado al nivel. */
    public Escuadron getEscuadron() {
        return escuadron;
    }

    /** Devuelve una vista inmodificable de los misiles actuales. */
    public List<Misil> getMisiles() {
        return List.copyOf(misiles);
    }

    /** Devuelve una vista inmodificable de las explosiones actuales. */
    public List<Explosion> getExplosiones() {
        return List.copyOf(explosiones);
    }
}
