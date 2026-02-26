import java.awt.event.MouseAdapter;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Carta {

    private int indice;

    public Carta(Random r) {
        this.indice = r.nextInt(52) + 1;
    }

    public void mostrar(int x, int y, JPanel pnl) {
        JLabel lblCarta = new JLabel();
        String archivoImagen = "imagenes/CARTA" + this.indice + ".jpg";
        ImageIcon imgCarta = new ImageIcon(getClass().getResource(archivoImagen));
        lblCarta.setIcon(imgCarta);
        pnl.add(lblCarta);
        lblCarta.setBounds(x, y, imgCarta.getIconWidth(), imgCarta.getIconHeight());

        lblCarta.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JOptionPane.showMessageDialog(null, getNombre().toString() + " de " +getPinta().toString());
            }
        });
    }

    public Pinta getPinta() {
        if (indice <= 13) {
            return Pinta.TREBOL;
        } else if (indice <= 26) {
            return Pinta.PICA;
        } else if (indice <= 39) {
            return Pinta.CORAZON;
        } else {
            return Pinta.DIAMANTE;
        }
    }

    public NombreCarta getNombre() {
        int residuo = indice % 13;
        if (residuo == 0) {
            residuo = 13;
        }
        return NombreCarta.values()[residuo - 1];
    }

}
