import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
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

    // Retorna texto con grupos (pares, ternas, cuartas) por nombre de carta
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
                    resultado += Grupo.values()[contador].toString()
                            + " de " + NombreCarta.values()[indice].toString() + "\n";
                }
                indice++;
            }
        }
        return resultado;
    }

    // NUEVO - Punto 4: retorna texto con escaleras de la misma pinta (secuencias de 3 o mas)
    public String getEscaleras() {
        String resultado = "No se encontraron escaleras";
        StringBuilder sb = new StringBuilder();

        for (Pinta pinta : Pinta.values()) {
            // Reunir cartas de esta pinta
            List<Carta> cartasMismaPinta = new ArrayList<>();
            for (Carta c : cartas) {
                if (c != null && c.getPinta() == pinta) {
                    cartasMismaPinta.add(c);
                }
            }

            // Necesitamos al menos 3 cartas para formar escalera
            if (cartasMismaPinta.size() < 3) continue;

            // Ordenar por valor del nombre (AS=1, DOS=2, ... KING=13)
            cartasMismaPinta.sort(Comparator.comparingInt(c -> c.getNombre().getValor()));

            // Buscar secuencias consecutivas
            int inicio = 0;
            while (inicio < cartasMismaPinta.size()) {
                int fin = inicio;
                while (fin + 1 < cartasMismaPinta.size() &&
                       cartasMismaPinta.get(fin + 1).getNombre().getValor()
                       == cartasMismaPinta.get(fin).getNombre().getValor() + 1) {
                    fin++;
                }

                int longitud = fin - inicio + 1;
                if (longitud >= 3) {
                    // Determinar nombre de la figura según longitud
                    String tipoFigura;
                    switch (longitud) {
                        case 3:  tipoFigura = "TERNA";   break;
                        case 4:  tipoFigura = "CUARTA";  break;
                        default: tipoFigura = "ESCALERA DE " + longitud; break;
                    }
                    String cartaInicio = cartasMismaPinta.get(inicio).getNombre().toString();
                    String cartaFin    = cartasMismaPinta.get(fin).getNombre().toString();
                    sb.append(tipoFigura).append(" EN ESCALERA de ")
                      .append(cartaInicio).append(" a ").append(cartaFin)
                      .append(" de ").append(pinta.toString()).append("\n");
                }
                inicio = fin + 1;
            }
        }

        if (sb.length() > 0) {
            resultado = sb.toString();
        }
        return resultado;
    }

    // NUEVO - Punto 4: suma el valor de las cartas que NO forman grupos ni escaleras
    public int calcularPuntaje() {
        // Marcar qué cartas están en grupos (2 o más del mismo nombre)
        boolean[] enGrupo = new boolean[TOTAL_CARTAS];
        int[] contadores = new int[NombreCarta.values().length];
        for (Carta carta : cartas) {
            contadores[carta.getNombre().ordinal()]++;
        }
        for (int i = 0; i < TOTAL_CARTAS; i++) {
            if (contadores[cartas[i].getNombre().ordinal()] >= 2) {
                enGrupo[i] = true;
            }
        }

        // Marcar qué cartas están en escaleras de la misma pinta
        boolean[] enEscalera = new boolean[TOTAL_CARTAS];
        for (Pinta pinta : Pinta.values()) {
            // Reunir índices y cartas de esta pinta
            List<Integer> indices = new ArrayList<>();
            List<Carta> cartasMismaPinta = new ArrayList<>();
            for (int i = 0; i < TOTAL_CARTAS; i++) {
                if (cartas[i] != null && cartas[i].getPinta() == pinta) {
                    indices.add(i);
                    cartasMismaPinta.add(cartas[i]);
                }
            }

            if (cartasMismaPinta.size() < 3) continue;

            // Ordenar por valor manteniendo el índice original
            List<int[]> pares = new ArrayList<>(); // [indiceOriginal, valorNombre]
            for (int i = 0; i < cartasMismaPinta.size(); i++) {
                pares.add(new int[]{indices.get(i), cartasMismaPinta.get(i).getNombre().getValor()});
            }
            pares.sort(Comparator.comparingInt(p -> p[1]));

            // Detectar secuencias consecutivas >= 3 y marcarlas
            int inicio = 0;
            while (inicio < pares.size()) {
                int fin = inicio;
                while (fin + 1 < pares.size() &&
                       pares.get(fin + 1)[1] == pares.get(fin)[1] + 1) {
                    fin++;
                }
                if (fin - inicio + 1 >= 3) {
                    for (int k = inicio; k <= fin; k++) {
                        enEscalera[pares.get(k)[0]] = true;
                    }
                }
                inicio = fin + 1;
            }
        }

        // Sumar solo las cartas que NO están en grupo ni en escalera
        int puntaje = 0;
        for (int i = 0; i < TOTAL_CARTAS; i++) {
            if (!enGrupo[i] && !enEscalera[i]) {
                NombreCarta nombre = cartas[i].getNombre();
                // AS, JACK, QUEEN, KING valen 10; el resto su número
                if (nombre == NombreCarta.AS || nombre == NombreCarta.JACK
                        || nombre == NombreCarta.QUEEN || nombre == NombreCarta.KING) {
                    puntaje += 10;
                } else {
                    puntaje += nombre.getValor();
                }
            }
        }
        return puntaje;
    }

    // NUEVO - Suma el valor de TODAS las cartas sin excluir nada
    public int calcularPuntajeTotal() {
        int puntaje = 0;
        for (Carta carta : cartas) {
            NombreCarta nombre = carta.getNombre();
            if (nombre == NombreCarta.AS || nombre == NombreCarta.JACK
                    || nombre == NombreCarta.QUEEN || nombre == NombreCarta.KING) {
                puntaje += 10;
            } else {
                puntaje += nombre.getValor();
            }
        }
        return puntaje;
    }

    // Se mantiene por compatibilidad con código anterior
    public boolean tieneEscaleraMismaPinta() {
        return !getEscaleras().equals("No se encontraron escaleras");
    }

}