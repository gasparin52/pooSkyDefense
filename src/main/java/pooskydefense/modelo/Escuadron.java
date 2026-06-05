package pooskydefense.modelo;

import java.util.ArrayList;
import java.util.List;

/**
 * Agrupa los drones de un nivel y controla cuántos pueden estar activos.
 * El maximo simultaneo es 4, como piden los requerimientos.
 */
public class Escuadron {

    /** Cantidad maxima de drones activos al mismo tiempo. */
    private static final int MAXIMO_DRONES_ACTIVOS = 4;

    /** Cantidad total de drones que puede contener el escuadron. */
    private final int capacidad;
    /** Lista completa de drones del nivel. */
    private final List<Drone> drones;

    /** Crea un escuadron vacio con la capacidad indicada. */
    public Escuadron(int capacidad) {
        this.capacidad = capacidad;
        this.drones = new ArrayList<>();
    }

    /** Agrega un drone siempre que no se supere la capacidad del nivel. */
    public void agregarDrone(Drone drone) {
        if (drones.size() < capacidad) {
            drones.add(drone);
        }
    }

    /** Cuenta cuántos drones estan activos en este instante. */
    public int dronesActivos() {
        return (int) drones.stream().filter(Drone::isActivo).count();
    }

    /** Indica si aun se puede poner otro drone en pantalla. */
    public boolean puedeActivarDrone() {
        return dronesActivos() < MAXIMO_DRONES_ACTIVOS;
    }

    /** Activa el primer drone que todavia no haya sido desplegado. */
    public void activarSiguienteDrone() {
        if (!puedeActivarDrone()) {
            return;
        }

        drones.stream()
                .filter(drone -> !drone.isActivo() && !drone.fueDesplegado())
                .findFirst()
                .ifPresent(Drone::activar);
    }

    /** Informa si aun quedan drones pendientes de salir. */
    public boolean quedanDronesPorSalir() {
        return drones.stream().anyMatch(drone -> !drone.fueDesplegado());
    }

    /** Devuelve una copia inmodificable de la lista de drones. */
    public List<Drone> getDrones() {
        return List.copyOf(drones);
    }
}
