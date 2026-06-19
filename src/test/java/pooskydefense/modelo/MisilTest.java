package pooskydefense.modelo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MisilTest {

    @Test
    void descenderReduceLaAltitudSegunLaVelocidad() {
        Misil misil = new Misil(100, 4000, 150, 3000);

        misil.descender();

        assertEquals(3850, misil.getAltitud());
    }

    @Test
    void debeExplotarCuandoLlegaALaAltitudDeDetonacion() {
        Misil misil = new Misil(100, 3100, 100, 3000);

        misil.descender();

        assertTrue(misil.debeExplotar());
    }

    @Test
    void explotarDesactivaElMisilYDevuelveUnaExplosionEnLaMismaPosicion() {
        Misil misil = new Misil(250, 1800, 100, 1700);

        Explosion explosion = misil.explotar();

        assertFalse(misil.isActivo());
        assertEquals(250, explosion.getPosX());
        assertEquals(1800, explosion.getAltitud());
    }
}
