package Serie8;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PanneauFormulaire extends JPanel {
    public JTextField matriculeField = new JTextField();
    public JTextField prenomField = new JTextField();
    public JTextField nomField = new JTextField();
    public JTextField ddnField = new JTextField();
    public JTextField sectionField = new JTextField();

    public JCheckBox boursierCBox = new JCheckBox("Boursier");
    public JCheckBox etrangerCBox = new JCheckBox("Étranger");

    private String[] origines = { "Europe", "Afrique", "Asie", "Amérique", "Océanie" };
    public JComboBox origineBox = new JComboBox<String>(origines);

    public JRadioButton inscrtiptionButton = new JRadioButton("Nouvel étudiant");
    public JRadioButton reinscriptionButton = new JRadioButton("Réinscription");
    private ButtonGroup inscriptionGroup = new ButtonGroup();

    PanneauFormulaire(int width, int height) {
        this.setBounds(0, 0, 270, 300);

        GridLayout layout = new GridLayout(8, 2);
        layout.setVgap(5);
        layout.setHgap(5);
        this.setLayout(layout);

        SectionActionListener sectionActionListener = new SectionActionListener();
        BoxActionListener boxActionListener = new BoxActionListener();
        ButtonActionListener buttonActionListener = new ButtonActionListener();

        matriculeField.addActionListener(sectionActionListener);
        this.add(new JLabel("Matricule :", SwingConstants.RIGHT));
        this.add(matriculeField);

        this.add(new JLabel("Prénom :", SwingConstants.RIGHT));
        this.add(prenomField);

        this.add(new JLabel("Nom :", SwingConstants.RIGHT));
        this.add(nomField);

        this.add(new JLabel("Date de naissance :", SwingConstants.RIGHT));
        this.add(ddnField);

        this.add(new JLabel("Section :", SwingConstants.RIGHT));
        sectionField.setEnabled(false);
        this.add(sectionField);

        boursierCBox.addActionListener(boxActionListener);
        this.add(boursierCBox);
        etrangerCBox.addActionListener(boxActionListener);
        this.add(etrangerCBox);

        this.add(new JLabel("Orgine :", SwingConstants.RIGHT));
        origineBox.setEnabled(false);
        this.add(origineBox);

        inscrtiptionButton.addActionListener(buttonActionListener);
        reinscriptionButton.addActionListener(buttonActionListener);
        inscriptionGroup.add(inscrtiptionButton);
        inscriptionGroup.add(reinscriptionButton);
        this.add(inscrtiptionButton);
        this.add(reinscriptionButton);
    }

    public class BoxActionListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            JCheckBox checkbox = (JCheckBox) event.getSource();
            if (checkbox == etrangerCBox)
                origineBox.setEnabled(checkbox.isSelected());
        }
    }

    public class ButtonActionListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            JRadioButton radioButton = (JRadioButton) event.getSource();
            if (radioButton == inscrtiptionButton && radioButton.isSelected() && boursierCBox.isSelected())
                JOptionPane.showMessageDialog(null, "Va au secrétatiat connard", "Hey",
                        JOptionPane.INFORMATION_MESSAGE);

            else if (radioButton == reinscriptionButton && radioButton.isSelected())
                JOptionPane.showMessageDialog(null, "Va au secrétatiat connard pour renter la liste de tes dispenses.",
                        "Hey", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public class SectionActionListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            JTextField textField = (JTextField) event.getSource();
            if (textField == matriculeField) {
                char sectionIdentifier = textField.getText().charAt(0);

                switch (sectionIdentifier) {
                case '1':
                    sectionField.setText("Technologie  de l’informatique");
                    break;

                case '2':
                    sectionField.setText("Sécurité des systèmes");
                    break;

                case '3':
                    sectionField.setText("Informatique  de gestion");
                    break;

                case '4':
                    sectionField.setText("Marketing");
                    break;

                case '5':
                    sectionField.setText("Automatique");
                    break;

                case '6':
                    sectionField.setText("Droit");
                    break;

                case '7':
                    sectionField.setText("Comptabilité");
                    break;

                case '8':
                    sectionField.setText("Régendat");
                    break;

                default:
                    JOptionPane.showMessageDialog(null, "Matricule incorrecte sale merde.", "Matricule incorrect",
                            JOptionPane.ERROR_MESSAGE);
                    break;
                }
            }
        }
    }
}
