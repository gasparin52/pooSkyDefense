package pooskydefense.vista;

import pooskydefense.modelo.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

// Panel donde se dibuja todo el juego
public class PanelJuego extends JPanel {

    private final Game game;
    private double energiaAnterior;
    private int vidasAnteriores;
    private int ticksFlash;
    private int ticksAvionOculto;
    private final List<ExplosionVisual> explosionesVisuales;
    private final List<DanoVisual> danosVisuales;
    private final List<FragmentoVisual> fragmentosAvion;

    private static class ExplosionVisual {
        int x;
        int y;
        int framesRestantes;
        int framesTotales;
        String tipo;

        ExplosionVisual(int x, int y, String tipo, int framesTotales) {
            this.x = x;
            this.y = y;
            this.tipo = tipo;
            this.framesTotales = framesTotales;
            this.framesRestantes = framesTotales;
        }
    }

    private static class DanoVisual {
        int x;
        int y;
        int framesRestantes;
        int dano;
        int energiaRestante;

        DanoVisual(int x, int y, int dano, int energiaRestante) {
            this.x = x;
            this.y = y;
            this.dano = dano;
            this.energiaRestante = energiaRestante;
            this.framesRestantes = 20;
        }
    }

    private static class FragmentoVisual {
        double x;
        double y;
        double velX;
        double velY;
        int tamano;
        int framesRestantes;
        int framesTotales;
        Color color;

        FragmentoVisual(double x, double y, double velX, double velY, int tamano, int framesTotales, Color color) {
            this.x = x;
            this.y = y;
            this.velX = velX;
            this.velY = velY;
            this.tamano = tamano;
            this.framesTotales = framesTotales;
            this.framesRestantes = framesTotales;
            this.color = color;
        }
    }

    public PanelJuego(Game game) {
        this.game = game;
        this.energiaAnterior = game.getJugador().getAvion().getEnergia();
        this.vidasAnteriores = game.getJugador().getVidas();
        this.ticksFlash = 0;
        this.ticksAvionOculto = 0;
        this.explosionesVisuales = new ArrayList<>();
        this.danosVisuales = new ArrayList<>();
        this.fragmentosAvion = new ArrayList<>();
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(1000, 600));
    }

    public void agregarExplosionEn(double posX, double altitud) {
        int px = toPixelX(posX);
        int py = toPixelY(altitud);

        Avion avion = game.getJugador().getAvion();
        double distancia = Math.hypot(posX - avion.getPosX(), altitud - avion.getAltitud());

        String tipo;
        if (distancia < 20) {
            tipo = "critica";
        } else if (distancia < 80) {
            tipo = "cercana";
        } else if (distancia < 150) {
            tipo = "mediana";
        } else {
            tipo = "lejana";
        }

        explosionesVisuales.add(new ExplosionVisual(px, py, tipo, 15));
    }

    public void explotarAvionEn(double posX, double altitud) {
        int px = toPixelX(posX);
        int py = toPixelY(altitud);
        ticksAvionOculto = 18;
        explosionesVisuales.add(new ExplosionVisual(px, py, "avion", 18));
        generarFragmentosAvion(px, py);
    }

    public void agregarDanoEnAvion(int dano, int energiaRestante) {
        Avion avion = game.getJugador().getAvion();
        int px = toPixelX(avion.getPosX());
        int py = toPixelY(avion.getAltitud()) - 25;
        danosVisuales.add(new DanoVisual(px, py, dano, energiaRestante));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        double energiaActual = game.getJugador().getAvion().getEnergia();
        int vidasActuales = game.getJugador().getVidas();

        if (vidasActuales < vidasAnteriores) {
            ticksFlash = 12;
        } else if (energiaActual < energiaAnterior) {
            ticksFlash = 8;
            int danoRecibido = (int) Math.round(energiaAnterior - energiaActual);
            int energiaRestante = (int) Math.round(energiaActual);
            agregarDanoEnAvion(danoRecibido, energiaRestante);
        }

        energiaAnterior = energiaActual;
        vidasAnteriores = vidasActuales;

        if (ticksFlash > 0) {
            ticksFlash--;
        }
        if (ticksAvionOculto > 0) {
            ticksAvionOculto--;
        }

        dibujarExplosiones(g);
        dibujarFragmentosAvion(g);
        dibujarDanos(g);
        dibujarMisiles(g);
        dibujarDrones(g);
        if (game.getJugador().estaVivo()) {
            dibujarAvion(g);
        }
        dibujarHUD(g);

        if (!game.getJugador().estaVivo()) {
            dibujarGameOver(g);
        }
    }

    private int toPixelX(double posX) {
        return (int) posX;
    }

    private int toPixelY(double altitud) {
        return (int) ((5000 - altitud) / 4000.0 * getHeight());
    }

    private void dibujarAvion(Graphics g) {
        if (ticksAvionOculto > 0) {
            return;
        }

        Avion avion = game.getJugador().getAvion();
        int cx = toPixelX(avion.getPosX());
        int cy = toPixelY(avion.getAltitud());

        if (ticksFlash > 0) {
            int vibracion = (ticksFlash % 2 == 0) ? 3 : -3;
            cx += vibracion;
            g.setColor(Color.RED);
        } else {
            g.setColor(Color.CYAN);
        }

        int[] puntosX = {cx, cx - 15, cx + 15};
        int[] puntosY = {cy - 15, cy + 10, cy + 10};
        g.fillPolygon(puntosX, puntosY, 3);
    }

    private void dibujarMisiles(Graphics g) {
        for (Misil m : game.getNivelActual().getMisiles()) {
            int mx = toPixelX(m.getPosX());
            int my = toPixelY(m.getAltitud());

            g.setColor(Color.YELLOW);
            g.fillRect(mx - 2, my - 5, 5, 10);
        }
    }

    private void dibujarExplosiones(Graphics g) {
        Iterator<ExplosionVisual> it = explosionesVisuales.iterator();
        while (it.hasNext()) {
            ExplosionVisual exp = it.next();
            int progreso = exp.framesTotales - exp.framesRestantes;
            int radio = switch (exp.tipo) {
                case "avion" -> 12 + progreso * 7;
                default -> progreso * 6;
            };

            Color color = switch (exp.tipo) {
                case "avion" -> new Color(255, 120, 20);
                case "critica" -> Color.WHITE;
                case "cercana" -> Color.ORANGE;
                case "mediana" -> Color.YELLOW;
                default -> new Color(100, 100, 100);
            };

            int alpha = (int) (255.0 * exp.framesRestantes / exp.framesTotales);
            int alphaRelleno = "avion".equals(exp.tipo) ? alpha / 2 : alpha / 3;
            g.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), alphaRelleno));
            g.fillOval(exp.x - radio, exp.y - radio, radio * 2, radio * 2);

            g.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha));
            g.drawOval(exp.x - radio, exp.y - radio, radio * 2, radio * 2);

            if ("avion".equals(exp.tipo)) {
                g.setColor(new Color(255, 240, 180, alpha));
                g.fillOval(exp.x - 5, exp.y - 5, 10, 10);
            }

            exp.framesRestantes--;
            if (exp.framesRestantes <= 0) {
                it.remove();
            }
        }
    }

    private void generarFragmentosAvion(int px, int py) {
        Color[] colores = {
                new Color(255, 180, 60),
                new Color(255, 120, 20),
                new Color(220, 220, 220),
                new Color(120, 240, 255)
        };

        for (int i = 0; i < 12; i++) {
            double angulo = ThreadLocalRandom.current().nextDouble(0, Math.PI * 2);
            double velocidad = ThreadLocalRandom.current().nextDouble(3.0, 8.0);
            double velX = Math.cos(angulo) * velocidad;
            double velY = Math.sin(angulo) * velocidad - 1.2;
            int tamano = ThreadLocalRandom.current().nextInt(4, 9);
            int frames = ThreadLocalRandom.current().nextInt(12, 22);
            Color color = colores[ThreadLocalRandom.current().nextInt(colores.length)];
            fragmentosAvion.add(new FragmentoVisual(px, py, velX, velY, tamano, frames, color));
        }
    }

    private void dibujarFragmentosAvion(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        Iterator<FragmentoVisual> it = fragmentosAvion.iterator();

        while (it.hasNext()) {
            FragmentoVisual fragmento = it.next();
            int alpha = (int) (255.0 * fragmento.framesRestantes / fragmento.framesTotales);
            g2.setColor(new Color(
                    fragmento.color.getRed(),
                    fragmento.color.getGreen(),
                    fragmento.color.getBlue(),
                    alpha
            ));

            int x = (int) Math.round(fragmento.x);
            int y = (int) Math.round(fragmento.y);
            int t = fragmento.tamano;
            int[] puntosX = {x, x - t, x + t};
            int[] puntosY = {y - t, y + t, y + t / 2};
            g2.fillPolygon(puntosX, puntosY, 3);

            fragmento.x += fragmento.velX;
            fragmento.y += fragmento.velY;
            fragmento.velY += 0.35;
            fragmento.framesRestantes--;

            if (fragmento.framesRestantes <= 0) {
                it.remove();
            }
        }

        g2.dispose();
    }

    private void dibujarDanos(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        Iterator<DanoVisual> it = danosVisuales.iterator();

        while (it.hasNext()) {
            DanoVisual dano = it.next();
            int alpha = (int) (255.0 * dano.framesRestantes / 20);
            int desplazamientoY = (20 - dano.framesRestantes) * 2;

            g2.setFont(new Font("Arial", Font.BOLD, 18));
            g2.setColor(new Color(255, 80, 80, alpha));
            g2.drawString("-" + dano.dano, dano.x - 10, dano.y - desplazamientoY);

            g2.setFont(new Font("Arial", Font.PLAIN, 12));
            g2.setColor(new Color(255, 255, 255, alpha));
            g2.drawString(dano.energiaRestante + "%", dano.x - 12, dano.y - desplazamientoY - 16);

            dano.framesRestantes--;
            if (dano.framesRestantes <= 0) {
                it.remove();
            }
        }

        g2.dispose();
    }

    private void dibujarDrones(Graphics g) {
        g.setColor(Color.RED);
        for (Drone d : game.getNivelActual().getEscuadron().getDrones()) {
            if (d.isActivo()) {
                g.fillOval(toPixelX(d.getPosX()) - 10, toPixelY(d.getAltitud()) - 10, 20, 20);
            }
        }
    }

    private void dibujarHUD(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString("Vidas: " + game.getJugador().getVidas(), 10, 20);
        g.drawString("Puntos: " + game.getJugador().getPuntos(), 10, 40);
        g.drawString("Nivel: " + game.getNivel(), 10, 60);
        g.drawString("Energia: " + (int) game.getJugador().getAvion().getEnergia() + "%", 10, 80);
    }

    private void dibujarGameOver(Graphics g) {
        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD, 48));
        g.drawString("GAME OVER", 350, 280);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 20));
        g.drawString("Puntos finales: " + game.getJugador().getPuntos(), 390, 330);
        g.drawString("Llegaste al nivel: " + game.getNivel(), 390, 360);
    }
}
