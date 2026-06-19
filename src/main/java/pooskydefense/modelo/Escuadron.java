package pooskydefense.modelo;

import java.util.ArrayList;
import java.util.List;

// Agrupa los drones de un nivel y limita cuántos pueden estar activos a la vez.

public class Escuadron {

    private static final int MAXIMO_DRONES_ACTIVOS = 4;

    private final int capacidad;
    private final List<Drone> drones;

    public Escuadron(int capacidad) {
        this.capacidad = capacidad;
        this.drones = new ArrayList<>();
    }

    public void agregarDrone(Drone drone) {
        if (drones.size() < capacidad) {
            drones.add(drone);
        }
    }

    public int dronesActivos() {
        return (int) drones.stream().filter(Drone::isActivo).count();
    }

    public boolean puedeActivarDrone() {
        return dronesActivos() < MAXIMO_DRONES_ACTIVOS;
    }

    public void activarSiguienteDrone() {
        if (!puedeActivarDrone()) {
            return;
        }

        drones.stream()
                .filter(drone -> !drone.isActivo() && !drone.fueDesplegado())
                .findFirst()
                .ifPresent(Drone::activar);
    }

    public boolean quedanDronesPorSalir() {
        return drones.stream().anyMatch(drone -> !drone.fueDesplegado());
    }

    public List<Drone> getDrones() {
        return List.copyOf(drones);
    }
}
