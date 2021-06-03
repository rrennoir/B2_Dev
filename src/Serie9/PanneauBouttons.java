package Serie9;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PanneauBouttons extends JPanel {
    Fenetre fen;
    private PanneauFormulaire formulaire;
    private JButton buttonRetour = new JButton("Retour");
    private JButton buttonReinitialiser = new JButton("Réinitialiser");
    private JButton buttonValidation = new JButton("Validation");

    PanneauBouttons(PanneauFormulaire formulaire, Fenetre fen) {
        this.fen = fen;
        this.setBounds(0, 0, 50, 50);
        this.setLayout(new FlowLayout());
        this.formulaire = formulaire;
        ButtonActionListener buttonListner = new ButtonActionListener();
        BValidateListener bValidationListner = new BValidateListener();
        ButtonRetourActionListener buttonRetourListner = new ButtonRetourActionListener();
        buttonReinitialiser.addActionListener(buttonListner);
        buttonValidation.addActionListener(bValidationListner);
        buttonRetour.addActionListener(buttonRetourListner);
        this.add(buttonRetour);
        this.add(buttonValidation);
        this.add(buttonReinitialiser);
    }

    public class ButtonActionListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            JButton buttonReset = (JButton) event.getSource();
            if (buttonReset == buttonReinitialiser)
                formulaire.resetFormulaire();
        }
    }

    public class ButtonRetourActionListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            JButton button = (JButton) event.getSource();
            if (button == buttonRetour)
                PanneauBouttons.this.fen.retourMenu();
        }
    }

    public class BValidateListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            JButton Bvalidate = (JButton) event.getSource();
            if (Bvalidate == buttonValidation)
                formulaire.validateFormulaire();
        }
    }
}
