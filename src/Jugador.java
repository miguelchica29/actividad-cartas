import java.util.Random;

import javax.swing.JPanel;

public class Jugador {
    private final int TOTAL_CARTAS = 10;
    private final int MARGEN_IZQUIERDA = 10;
    private final int MARGEN_SUPERIOR = 10;
    private final int DISTANCIA = 40;

    private Random r = new Random();
    private Carta[] cartas = new Carta[TOTAL_CARTAS];

    public void repartir() {
        for (int i = 0; i < TOTAL_CARTAS; i++) {
            cartas[i] = new Carta(r);
        }
    }

    public void mostrar(JPanel pnl) {
        pnl.setLayout(null);
        pnl.removeAll();
        int xCarta = MARGEN_IZQUIERDA + TOTAL_CARTAS * DISTANCIA;
        for (Carta carta : cartas) {
            carta.mostrar(xCarta, MARGEN_SUPERIOR, pnl);
            xCarta -= DISTANCIA;
        }
        pnl.repaint();
    }

    public String getGrupos() {
        String resultado = "No se encontraron grupos";
        int[] contadores = new int[NombreCarta.values().length];
        for (Carta carta : cartas) {
            contadores[carta.getNombre().ordinal()]++;
        }

        boolean hayGrupos = false;
        for (int contador : contadores) {
            if (contador >= 2) {
                hayGrupos = true;
                break;
            }
        }
        if (hayGrupos) {
            resultado = "Se encontraron los siguientes grupos:\n";
            int indice = 0;
            for (int contador : contadores) {
                if (contador >= 2) {
                    resultado+= Grupo.values()[contador].toString()+ " de " +NombreCarta.values()[indice].toString()+"\n";
                }
                indice++;
            }
        }
        return resultado;
    }
}
