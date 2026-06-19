package pooskydefense.vista;

import pooskydefense.controlador.Controlador;
import pooskydefense.modelo.Direccion;
import pooskydefense.modelo.Game;
import pooskydefense.modelo.Misil;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

// Ventana principal del juego
public class VentanaJuego extends JFrame {

    private static final int DELAY_TIMER_MS = 50;
    private static final int PAUSA_EVENTO_MS = 2000;

    public VentanaJuego(Game game, Controlador controlador) {
        PanelJuego panel = new PanelJuego(game);
        add(panel);
        pack();

        setTitle("SkyDefense");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);

        panel.setFocusable(true);
        panel.requestFocusInWindow();

        final int[] ticksPausaRestantes = {0};
        final int[] nivelAnterior = {game.getNivel()};
        final int[] vidasAnteriores = {game.getJugador().getVidas()};

        panel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (ticksPausaRestantes[0] > 0) {
                    return;
                }

                switch (e.getKeyCode()) {
                    case KeyEvent.VK_RIGHT -> controlador.moverAvion(Direccion.DERECHA);
                    case KeyEvent.VK_LEFT  -> controlador.moverAvion(Direccion.IZQUIERDA);
                    case KeyEvent.VK_UP    -> controlador.subirAvion();
                    case KeyEvent.VK_DOWN  -> controlador.bajarAvion();
                }
            }
        });

        // Timer: cada 50ms avanza el juego y redibuja la pantalla
        Timer timer = new Timer(DELAY_TIMER_MS, e -> {
            if (ticksPausaRestantes[0] > 0) {
                ticksPausaRestantes[0]--;
                panel.repaint();
                return;
            }

            double avionPosXAntes = game.getJugador().getAvion().getPosX();
            double avionAltitudAntes = game.getJugador().getAvion().getAltitud();

            // Guardar solo los misiles que van a detonar en este tick para mostrar la explosion
            List<double[]> explosionesPendientes = new ArrayList<>();
            for (Misil m : game.getNivelActual().getMisiles()) {
                double altitudDespuesDeCaer = m.getAltitud() - m.getVelCaida();
                if (altitudDespuesDeCaer <= m.getAltDetonacion()) {
                    explosionesPendientes.add(new double[]{m.getPosX(), altitudDespuesDeCaer});
                }
            }

            // Avanzar el juego
            game.tick();

            for (double[] pos : explosionesPendientes) {
                panel.agregarExplosionEn(pos[0], pos[1]);
            }

            if (game.getJugador().getVidas() < vidasAnteriores[0]) {
                panel.explotarAvionEn(avionPosXAntes, avionAltitudAntes);
                ticksPausaRestantes[0] = PAUSA_EVENTO_MS / DELAY_TIMER_MS;
            } else if (game.getNivel() > nivelAnterior[0]) {
                ticksPausaRestantes[0] = PAUSA_EVENTO_MS / DELAY_TIMER_MS;
            }

            vidasAnteriores[0] = game.getJugador().getVidas();
            nivelAnterior[0] = game.getNivel();

            panel.repaint();
        });
        timer.start();

        setVisible(true);
    }
}
