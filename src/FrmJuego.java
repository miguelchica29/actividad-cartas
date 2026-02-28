import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class FrmJuego extends JFrame {

    JPanel pnlJugador1, pnlJugador2;
    Jugador jugador1 = new Jugador();
    Jugador jugador2 = new Jugador();
    JTabbedPane tpJugadores;

    public FrmJuego() {
        setSize(800, 400);
        setTitle("Juego de Cartas");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        JButton btnRepartir = new JButton("Repartir");
        btnRepartir.setBounds(10, 10, 100, 25);
        add(btnRepartir);

        JButton btnVerificar = new JButton("Verificar");
        btnVerificar.setBounds(120, 10, 100, 25);
        add(btnVerificar);

        tpJugadores = new JTabbedPane();
        tpJugadores.setBounds(10, 50, getWidth() - 40, getHeight() - 100);
        add(tpJugadores);

        pnlJugador1 = new JPanel();
        pnlJugador1.setBackground(new Color(0, 255, 0));
        pnlJugador1.setLayout(null);

        pnlJugador2 = new JPanel();
        pnlJugador2.setBackground(new Color(0, 255, 255));
        pnlJugador2.setLayout(null);

        tpJugadores.add(pnlJugador1, "Martín Estrada Contreras");
        tpJugadores.add(pnlJugador2, "Raul Vidal");

        // Un solo ActionListener por botón
        btnRepartir.addActionListener(e -> repartirCartas());
        btnVerificar.addActionListener(e -> verificarGrupos());
    }

    private void repartirCartas() {
        jugador1.repartir();
        jugador2.repartir();
        jugador1.mostrar(pnlJugador1);
        jugador2.mostrar(pnlJugador2);
    }

    private void verificarGrupos() {
        // Determinar qué jugador está seleccionado
        Jugador jugadorActual = (tpJugadores.getSelectedIndex() == 0) ? jugador1 : jugador2;

        // Armar el mensaje con grupos, escaleras y puntaje
        String mensaje = "";
        mensaje += "=== GRUPOS ===\n";
        mensaje += jugadorActual.getGrupos();
        mensaje += "\n=== ESCALERAS (misma pinta) ===\n";
        mensaje += jugadorActual.getEscaleras();
        mensaje += "\n=== PUNTAJE (cartas sin figura) ===\n";
        mensaje += "Puntaje: " + jugadorActual.calcularPuntaje();
        mensaje += "\n=== PUNTAJE TOTAL (todas las cartas) ===\n";
        mensaje += "Puntaje total: " + jugadorActual.calcularPuntajeTotal();

        JOptionPane.showMessageDialog(null, mensaje);
    }

}