package pooskydefense.modelo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JugadorTest {

    @Test
    void ganaUnaVidaExtraAlLlegarAlPrimerUmbral() {
        Jugador jugador = new Jugador(new Avion(500, 3000));

        jugador.agregarPuntos(1000);

        assertEquals(1000, jugador.getPuntos());
        assertEquals(4, jugador.getVidas());
        assertEquals(2000, jugador.getPuntosProximaVida());
    }

    @Test
    void ganaMultiplesVidasSiSuperaVariosUmbralesDeUnaVez() {
        Jugador jugador = new Jugador(new Avion(500, 3000));

        jugador.agregarPuntos(2500);

        assertEquals(2500, jugador.getPuntos());
        assertEquals(5, jugador.getVidas());
        assertEquals(3000, jugador.getPuntosProximaVida());
    }

    @Test
    void perderVidaNoPuedeBajarDeCero() {
        Jugador jugador = new Jugador(new Avion(500, 3000));

        jugador.perderVida();
        jugador.perderVida();
        jugador.perderVida();
        jugador.perderVida();

        assertEquals(0, jugador.getVidas());
        assertTrue(!jugador.estaVivo());
    }

    @Test
    void reiniciarAvionRestauraEnergiaYPosicionSegura() {
        Avion avion = new Avion(50, 2000);
        Jugador jugador = new Jugador(avion);
        avion.recibirDano(70);
        avion.setPosX(123);
        avion.cambiarAltitud(1500);

        jugador.reiniciarAvion();

        assertEquals(100, avion.getEnergia());
        assertEquals(500, avion.getPosX());
        assertEquals(3000, avion.getAltitud());
    }
}
