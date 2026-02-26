import java.awt.Color;
import java.util.Random;

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
        pnlJugador2 = new JPanel();
        pnlJugador2.setBackground(new Color(0, 255, 255));

        tpJugadores.add(pnlJugador1, "Martín Estrada Contreras");
        tpJugadores.add(pnlJugador2, "Raul Vidal");

        // eventos
        btnRepartir.addActionListener(e -> {
            repartirCartas();
        });
        btnVerificar.addActionListener(e -> {
            verificarGrupos();
        });

    }

    private void repartirCartas() {
        jugador1.repartir();
        jugador2.repartir();
        jugador1.mostrar(pnlJugador1);
        jugador2.mostrar(pnlJugador2);
    }

    private void verificarGrupos() {
        String resultado = "";
        if (tpJugadores.getSelectedIndex() == 0) {
            resultado = jugador1.getGrupos();
        }
        else{
            resultado = jugador2.getGrupos();
        }
        JOptionPane.showMessageDialog(null, resultado);
    }
}
