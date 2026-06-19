package pooskydefense.modelo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ExplosionTest {

    @Test
    void explosionLejanaOtorgaCuarentaPuntosSinDano() {
        Jugador jugador = new Jugador(new Avion(500, 3000));
        Avion avion = jugador.getAvion();
        Explosion explosion = new Explosion(700, 3000);

        explosion.aplicarEfecto(jugador, avion);

        assertEquals(40, jugador.getPuntos());
        assertEquals(100, avion.getEnergia());
        assertEquals(3, jugador.getVidas());
    }

    @Test
    void explosionMedianaOtorgaPuntosYHaceDano() {
        Jugador jugador = new Jugador(new Avion(500, 3000));
        Avion avion = jugador.getAvion();
        Explosion explosion = new Explosion(580, 3000);

        explosion.aplicarEfecto(jugador, avion);

        assertEquals(20, jugador.getPuntos());
        assertEquals(80, avion.getEnergia());
        assertEquals(3, jugador.getVidas());
    }

    @Test
    void explosionCercanaSoloHaceDano() {
        Jugador jugador = new Jugador(new Avion(500, 3000));
        Avion avion = jugador.getAvion();
        Explosion explosion = new Explosion(530, 3000);

        explosion.aplicarEfecto(jugador, avion);

        assertEquals(0, jugador.getPuntos());
        assertEquals(60, avion.getEnergia());
        assertEquals(3, jugador.getVidas());
    }

    @Test
    void explosionCriticaQuitaUnaVidaYRestauraElAvion() {
        Jugador jugador = new Jugador(new Avion(500, 3000));
        Avion avion = jugador.getAvion();
        avion.recibirDano(60);
        Explosion explosion = new Explosion(510, 3000);

        explosion.aplicarEfecto(jugador, avion);

        assertEquals(2, jugador.getVidas());
        assertEquals(100, avion.getEnergia());
        assertEquals(0, jugador.getPuntos());
    }

    @Test
    void respetaLosUmbralesExactosDeDistancia() {
        Jugador jugadorOchenta = new Jugador(new Avion(500, 3000));
        Explosion explosionOchenta = new Explosion(580, 3000);
        explosionOchenta.aplicarEfecto(jugadorOchenta, jugadorOchenta.getAvion());

        Jugador jugadorVeinte = new Jugador(new Avion(500, 3000));
        Explosion explosionVeinte = new Explosion(520, 3000);
        explosionVeinte.aplicarEfecto(jugadorVeinte, jugadorVeinte.getAvion());

        assertEquals(20, jugadorOchenta.getPuntos());
        assertEquals(80, jugadorOchenta.getAvion().getEnergia());
        assertEquals(60, jugadorVeinte.getAvion().getEnergia());
        assertEquals(3, jugadorVeinte.getVidas());
    }
}
