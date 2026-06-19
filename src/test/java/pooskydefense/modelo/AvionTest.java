package pooskydefense.modelo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AvionTest {

    @Test
    void limitaLaAltitudInicialAlRangoSeguro() {
        Avion avionBajo = new Avion(500, 200);
        Avion avionAlto = new Avion(500, 6000);

        assertEquals(1000, avionBajo.getAltitud());
        assertEquals(4200, avionAlto.getAltitud());
    }

    @Test
    void moverRespetaLosBordesDePantalla() {
        Avion avion = new Avion(1000, 3000);

        avion.mover(Direccion.DERECHA);
        assertEquals(1000, avion.getPosX());

        avion.setPosX(0);
        avion.mover(Direccion.IZQUIERDA);
        assertEquals(0, avion.getPosX());
    }

    @Test
    void recibirDanoNuncaDejaEnergiaNegativa() {
        Avion avion = new Avion(500, 3000);

        avion.recibirDano(150);

        assertEquals(0, avion.getEnergia());
        assertTrue(avion.estaDestruido());
    }

    @Test
    void restaurarDevuelveLaEnergiaInicial() {
        Avion avion = new Avion(500, 3000);
        avion.recibirDano(80);

        avion.restaurar();

        assertEquals(100, avion.getEnergia());
    }
}
