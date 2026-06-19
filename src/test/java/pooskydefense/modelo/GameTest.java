package pooskydefense.modelo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

class GameTest {

    @Test
    void iniciaConValoresEsperados() {
        Game game = new Game();

        assertEquals(1, game.getNivel());
        assertEquals(12, game.getVelDrones());
        assertEquals(15, game.getVelMisiles());
        assertEquals(0.25, game.getFrecDisparos());
        assertEquals(0, game.getPuntuacion());
        assertEquals(1, game.getNivelActual().getNumero());
    }

    @Test
    void avanzarNivelSumaPuntosYEscalaLaDificultad() {
        Game game = new Game();

        game.avanzarNivel();

        assertEquals(2, game.getNivel());
        assertEquals(300, game.getPuntuacion());
        assertEquals(13.8, game.getVelDrones(), 0.0001);
        assertEquals(17.25, game.getVelMisiles(), 0.0001);
        assertEquals(0.2875, game.getFrecDisparos(), 0.0001);
        assertEquals(2, game.getNivelActual().getNumero());
    }

    @Test
    void getPuntuacionDelegAAlJugador() {
        Game game = new Game();

        game.getJugador().agregarPuntos(40);

        assertEquals(40, game.getPuntuacion());
    }

    @Test
    void exponeSiempreElMismoJugador() {
        Game game = new Game();

        assertSame(game.getJugador(), game.getJugador());
    }
}
