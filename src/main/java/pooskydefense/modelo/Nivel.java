package pooskydefense.modelo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

// Nivel activo de la partida.

public class Nivel {

    private static final int CANTIDAD_DRONES_POR_NIVEL = 10;

    private final int numero;
    private final Escuadron escuadron;
    private final List<Misil> misiles;
    private final List<Explosion> explosiones;
    private final double velDrones;
    private final double velMisiles;
    private final double frecDisparos;
    private double tiempoDesdeUltimoDisparo;

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

    // El orden del tick evita inconsistencias entre drones, misiles y vidas.

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

    private void inicializarDrones() {
        for (int i = 0; i < CANTIDAD_DRONES_POR_NIVEL; i++) {
            escuadron.agregarDrone(new Drone(velDrones));
        }
    }

    private void activarDroneSiCorresponde() {
        escuadron.activarSiguienteDrone();
    }

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

    private void explosionsCleanup() {
        explosiones.clear();
    }

    public boolean estaCompletado() {
        return !escuadron.quedanDronesPorSalir()
                && escuadron.dronesActivos() == 0
                && misiles.isEmpty()
                && explosiones.isEmpty();
    }

    public int getNumero() {
        return numero;
    }

    public Escuadron getEscuadron() {
        return escuadron;
    }

    public List<Misil> getMisiles() {
        return List.copyOf(misiles);
    }

    public List<Explosion> getExplosiones() {
        return List.copyOf(explosiones);
    }
}
