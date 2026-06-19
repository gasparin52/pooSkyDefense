package pooskydefense.modelo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EscuadronTest {

    @Test
    void noAgregaMasDronesQueLaCapacidad() {
        Escuadron escuadron = new Escuadron(2);

        escuadron.agregarDrone(new Drone(10));
        escuadron.agregarDrone(new Drone(10));
        escuadron.agregarDrone(new Drone(10));

        assertEquals(2, escuadron.getDrones().size());
    }

    @Test
    void noActivaMasDeCuatroDronesSimultaneamente() {
        Escuadron escuadron = new Escuadron(6);
        for (int i = 0; i < 6; i++) {
            escuadron.agregarDrone(new Drone(10));
        }

        for (int i = 0; i < 6; i++) {
            escuadron.activarSiguienteDrone();
        }

        assertEquals(4, escuadron.dronesActivos());
        assertFalse(escuadron.puedeActivarDrone());
    }

    @Test
    void informaSiTodaviaQuedanDronesPorSalir() {
        Escuadron escuadron = new Escuadron(1);
        escuadron.agregarDrone(new Drone(10));

        assertTrue(escuadron.quedanDronesPorSalir());

        escuadron.activarSiguienteDrone();

        assertFalse(escuadron.quedanDronesPorSalir());
    }
}
