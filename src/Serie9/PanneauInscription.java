package Serie9;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PanneauInscription extends JPanel {
    JPanel panFormulaire, panBouttons;

    PanneauInscription(int width, int height, Fenetre fen) {
        this.setBounds(10, 10, 330, 300);
        this.setLayout(new BorderLayout());
        panFormulaire = new PanneauFormulaire();
        panBouttons = new PanneauBouttons((PanneauFormulaire) panFormulaire, fen);

        this.add(panBouttons, BorderLayout.SOUTH);
        this.add(panFormulaire, BorderLayout.CENTER);
    }
}
