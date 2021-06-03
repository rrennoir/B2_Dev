package Examen.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class PanInsert extends JPanel {

    private JComboBox<String> cbFkPcUnit, cbFkTypeIntervention, cbFkFournIntervenant;
    private JRadioButton rbInterventionClose, rbInterventionOnGoing, rbINterventionNoted, rbReturnClose,
            rbReturnOnGoing, rbReturnNoted, rbResultClose, rbResultOnGoing, rbResultNoted;
    private ButtonGroup bgInterventionState, bgReturnState, bgResultState;
    private JCheckBox chbFollowSupplier;
    private JTextField tfContactDate, tfChargeDate, tfReturnDate, tfInternTime, tfDescription, tfSignaleur, tfPreneur,
            tfDateRemise;
    private MainWindow mWindow;

    private int width = 900, height = 500;

    PanInsert(MainWindow mw) {
        this.mWindow = mw;

        int xOffset = (mw.getWidth() - width) / 2;
        int yOffset = (mw.getHeight() - height) / 2;

        this.setBounds(xOffset - 10, yOffset - 35, width, height);
        this.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        this.setLayout(new BorderLayout(0, 5));

        JPanel pFormInput = new JPanel(new GridLayout(15, 1, 0, 2));
        cbFkPcUnit = new JComboBox<String>(getArrayFromSQL("SELECT IdPcUnit FROM  PcUnit", "IdPcUnit"));
        cbFkTypeIntervention = new JComboBox<String>(getArrayFromSQL("SELECT LibelleTypeInt FROM  TypeIntervention", "LibelleTypeInt"));
        cbFkFournIntervenant = new JComboBox<String>(getArrayFromSQL("SELECT NomFourn FROM  Fournisseur", "NomFourn"));
        cbFkFournIntervenant.setSelectedIndex(-1);
        chbFollowSupplier = new JCheckBox();
        chbFollowSupplier.addActionListener(new AlFollowSupp());
        tfContactDate = new JTextField();
        tfContactDate.setEnabled(false);
        tfChargeDate = new JTextField();
        tfChargeDate.setEnabled(false);
        tfReturnDate = new JTextField();
        tfReturnDate.setEnabled(false);
        tfInternTime = new JTextField();
        tfDescription = new JTextField();
        tfSignaleur = new JTextField();
        tfPreneur = new JTextField();
        tfDateRemise = new JTextField();

        // Groupe Bouton Etat Retour
        rbReturnClose = new JRadioButton("OK");
        rbReturnOnGoing = new JRadioButton("Déclassé");
        rbReturnNoted = new JRadioButton("en Suspens");
        bgReturnState = new ButtonGroup();
        bgReturnState.add(rbReturnClose);
        bgReturnState.add(rbReturnOnGoing);
        bgReturnState.add(rbReturnNoted);

        JPanel pButtonGoupReturn = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        pButtonGoupReturn.add(rbReturnClose);
        pButtonGoupReturn.add(rbReturnOnGoing);
        pButtonGoupReturn.add(rbReturnNoted);

        // Groupe Bouton Etat Resultat
        rbResultClose = new JRadioButton("OK");
        rbResultOnGoing = new JRadioButton("Déclassé");
        rbResultNoted = new JRadioButton("en Suspens");
        bgResultState = new ButtonGroup();
        bgResultState.add(rbResultClose);
        bgResultState.add(rbResultOnGoing);
        bgResultState.add(rbResultNoted);

        JPanel pButtonGoupResult = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        pButtonGoupResult.add(rbResultClose);
        pButtonGoupResult.add(rbResultOnGoing);
        pButtonGoupResult.add(rbResultNoted);

        // Groupe Bouton Etat Intervention
        rbInterventionClose = new JRadioButton("Cloturé");
        rbInterventionOnGoing = new JRadioButton("Encours");
        rbINterventionNoted = new JRadioButton("Signalé");
        bgInterventionState = new ButtonGroup();
        bgInterventionState.add(rbInterventionClose);
        bgInterventionState.add(rbInterventionOnGoing);
        bgInterventionState.add(rbINterventionNoted);

        JPanel pButtonGoup = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        pButtonGoup.add(rbInterventionClose);
        pButtonGoup.add(rbInterventionOnGoing);
        pButtonGoup.add(rbINterventionNoted);

        pFormInput.add(cbFkPcUnit);
        pFormInput.add(cbFkTypeIntervention);
        pFormInput.add(cbFkFournIntervenant);
        pFormInput.add(chbFollowSupplier);
        pFormInput.add(tfContactDate);
        pFormInput.add(tfChargeDate);
        pFormInput.add(tfReturnDate);
        pFormInput.add(pButtonGoupReturn);
        pFormInput.add(tfInternTime);
        pFormInput.add(tfDateRemise);
        pFormInput.add(tfDescription);
        pFormInput.add(tfSignaleur);
        pFormInput.add(tfPreneur);
        pFormInput.add(pButtonGoup);
        pFormInput.add(pButtonGoupResult);

        JPanel pFormLabel = new JPanel(new GridLayout(15, 1, 0, 2));
        pFormLabel.add(new JLabel("Pc Unit:", JLabel.RIGHT));
        pFormLabel.add(new JLabel("Type d'intervention:", JLabel.RIGHT));
        pFormLabel.add(new JLabel("Fournisseur intervenant:", JLabel.RIGHT));
        pFormLabel.add(new JLabel("Suivi via fournisseur:", JLabel.RIGHT));
        pFormLabel.add(new JLabel("Date de contact:", JLabel.RIGHT));
        pFormLabel.add(new JLabel("Date de prise en charge:", JLabel.RIGHT));
        pFormLabel.add(new JLabel("Date de retour:", JLabel.RIGHT));
        pFormLabel.add(new JLabel("État de retour:", JLabel.RIGHT));
        pFormLabel.add(new JLabel("Temps interne:", JLabel.RIGHT));
        pFormLabel.add(new JLabel("Date remise en service:", JLabel.RIGHT));
        pFormLabel.add(new JLabel("Description du problème:", JLabel.RIGHT));
        pFormLabel.add(new JLabel("Signaleur de l'incident:", JLabel.RIGHT));
        pFormLabel.add(new JLabel("Preneur en charge", JLabel.RIGHT));
        pFormLabel.add(new JLabel("État intervention:", JLabel.RIGHT));
        pFormLabel.add(new JLabel("Résultat:", JLabel.RIGHT));

        JPanel pForm = new JPanel(new BorderLayout(10, 0));
        pForm.add(pFormLabel, BorderLayout.WEST);
        pForm.add(pFormInput, BorderLayout.CENTER);

        JButton bBack = new JButton("Retour");
        JButton bReset = new JButton("Réinitialisé");
        JButton bInsert = new JButton("Inseré");

        bBack.addActionListener(new AlBack());
        bReset.addActionListener(new AlReset());
        bInsert.addActionListener(new AlInset());

        JPanel pButtonForm = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        pButtonForm.add(bBack);
        pButtonForm.add(bReset);
        pButtonForm.add(bInsert);

        this.add(new JLabel("Insertion", JLabel.CENTER), BorderLayout.NORTH);
        this.add(pForm, BorderLayout.CENTER);
        this.add(pButtonForm, BorderLayout.SOUTH);
    }

    public void Reset() {
        cbFkPcUnit.setSelectedIndex(-1);
        cbFkFournIntervenant.setSelectedIndex(-1);
        cbFkTypeIntervention.setSelectedIndex(-1);
        tfChargeDate.setText("");
        tfContactDate.setText("");
        tfInternTime.setText("");
        tfReturnDate.setText("");
        chbFollowSupplier.setSelected(false);
        rbINterventionNoted.setSelected(true);
    }

    public void SetFollowSupp(boolean state) {
        tfContactDate.setEnabled(state);
        tfChargeDate.setEnabled(state);
        tfReturnDate.setEnabled(state);
        tfInternTime.setEnabled(!state);
    }

    private class AlBack implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            PanInsert.this.mWindow.Welcome();
        }
    }

    private class AlReset implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            PanInsert.this.Reset();
        }
    }

    private class AlInset implements ActionListener {
        public void actionPerformed(ActionEvent e) {

            String error_info = "";
            if (cbFkPcUnit.getSelectedIndex() == -1) {
                cbFkPcUnit.setBackground(Color.RED);
                error_info += "Pc Unit est incorrect\n";

            } else
                cbFkPcUnit.setBackground(Color.WHITE);

            if (cbFkTypeIntervention.getSelectedIndex() == -1) {
                cbFkTypeIntervention.setBackground(Color.RED);
                error_info += "Type d'intervention est incorrect\n";

            } else
                cbFkTypeIntervention.setBackground(Color.WHITE);

            if (cbFkFournIntervenant.getSelectedIndex() == -1 && chbFollowSupplier.isSelected()) {
                cbFkFournIntervenant.setBackground(Color.RED);
                error_info += "Fournisseur intervenant est incorrect\n";

            } else
                cbFkFournIntervenant.setBackground(Color.WHITE);

            if (chbFollowSupplier.isSelected() && tfChargeDate.getText().equals("")) {
                tfChargeDate.setBackground(Color.RED);
                error_info += "Date de prise en charge est incorrect\n";

            } else
                tfChargeDate.setBackground(Color.WHITE);

            if (chbFollowSupplier.isSelected() && tfContactDate.getText().equals("")) {
                tfContactDate.setBackground(Color.RED);
                error_info += "Date de contact est incorrect\n";

            } else
                tfContactDate.setBackground(Color.WHITE);

            if (chbFollowSupplier.isSelected() && tfReturnDate.getText().equals("")) {
                tfReturnDate.setBackground(Color.RED);
                error_info += "Date de retour est incorrect\n";

            } else
                tfReturnDate.setBackground(Color.WHITE);

            if (rbInterventionClose.isSelected() && !rbResultClose.isSelected() && !rbResultOnGoing.isSelected()
                    && !rbResultNoted.isSelected()) {
                error_info += "ResultNoted doit être indiqué\n";

            }
            if (tfInternTime.getText().equals("") && !chbFollowSupplier.isSelected()) {
                tfInternTime.setBackground(Color.RED);
                error_info += "Temps interne est incorrect\n";

            } else
                tfInternTime.setBackground(Color.WHITE);

            if (!tfInternTime.getText().matches("^[0-9]*$") && !chbFollowSupplier.isSelected()) {
                tfInternTime.setBackground(Color.RED);
                error_info += "Temps interne est incorrect\n";

            } else
                tfInternTime.setBackground(Color.WHITE);

            if (tfPreneur.getText().equals("")) {
                tfPreneur.setBackground(Color.RED);
                error_info += "Preneur en charge est incorrect\n";

            } else
                tfPreneur.setBackground(Color.WHITE);

            if (chbFollowSupplier.isSelected() && !rbINterventionNoted.isSelected()) {
                error_info += "État intervention est incorrect\n";
            }

            if (!rbInterventionClose.isSelected() && !rbInterventionOnGoing.isSelected()
                    && !rbINterventionNoted.isSelected())
                error_info += "État intervention doit être indiqué\n";

            if (error_info.equals("")) {
                JOptionPane.showMessageDialog(null, "Insertion réussie", "Succes", JOptionPane.INFORMATION_MESSAGE);
                if (chbFollowSupplier.isSelected())
                    InsertWithFollow();
                else
                    InsertWithoutFollow();

            } else
                JOptionPane.showMessageDialog(null, error_info, "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private class AlFollowSupp implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            JCheckBox checkbox = (JCheckBox) event.getSource();
            if (checkbox == chbFollowSupplier)
                PanInsert.this.SetFollowSupp(checkbox.isSelected());
        }
    }

    private void InsertWithFollow() {
        String sqlinstruction = "INSERT INTO intervention VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement prepStat = mWindow.getConnexion().prepareStatement(sqlinstruction);
            // NoInterv
            prepStat.setInt(1, getMaxNoInterv() + 1);
            // DateSignalement
            prepStat.setDate(2, getTodayDate());
            // DescritptifBref
            if (tfDescription.getText().equals("")) {
                prepStat.setNull(3, Types.VARCHAR);
            } else {
                prepStat.setString(3, tfDescription.getText());
            }
            // SignaleurIncident
            if (tfSignaleur.getText().equals("")) {
                prepStat.setNull(4, Types.VARCHAR);
            } else {
                prepStat.setString(4, tfSignaleur.getText());
            }
            // PreneurEnCharge
            prepStat.setString(5, tfPreneur.getText());
            // EtatInterv
            if (rbInterventionClose.isSelected()) {
                prepStat.setString(6, "Clôturé");
            }
            if (rbInterventionOnGoing.isSelected()) {
                prepStat.setString(6, "Encours");
            }
            if (rbINterventionNoted.isSelected()) {
                prepStat.setString(6, "Signalé");
            }
            prepStat.setBoolean(7, true);
            prepStat.setDate(8, getDateFromField(tfContactDate));
            prepStat.setDate(9, getDateFromField(tfChargeDate));
            prepStat.setDate(10, getDateFromField(tfReturnDate));
            // EtatRetour
            if (rbReturnClose.isSelected()) {
                prepStat.setString(11, "OK");
            }
            if (rbReturnOnGoing.isSelected()) {
                prepStat.setString(11, "Déclassé");
            }
            if (rbReturnNoted.isSelected()) {
                prepStat.setString(11, "en Suspens");
            }
            // DateRemise
            if (tfDateRemise.getText().equals("")) {
                prepStat.setNull(12, Types.TIMESTAMP);
            } else {
                prepStat.setDate(12, getDateFromField(tfDateRemise));
            }
            // TempsInterne
            prepStat.setNull(13, Types.INTEGER);
            // Resultat
            if (rbResultClose.isSelected()) {
                prepStat.setString(14, "OK");
            }
            if (rbResultOnGoing.isSelected()) {
                prepStat.setString(14, "Déclassé");
            }
            if (rbResultNoted.isSelected()) {
                prepStat.setString(14, "en Suspens");
            } else
                prepStat.setNull(14, Types.VARCHAR);
            // PCUnit
            prepStat.setString(15, cbFkPcUnit.getSelectedItem().toString());

            // TypeIntervention
            prepStat.setString(16, returnTypeIntervID());

            // Fournisseur
            prepStat.setString(17, returnFournisseurID());

            // Exécution de l'instruction complétée
            prepStat.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void InsertWithoutFollow() {
        String sqlinstruction = "INSERT INTO intervention VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        try {
            PreparedStatement prepStat = mWindow.getConnexion().prepareStatement(sqlinstruction);
            // NoInterv
            prepStat.setInt(1, getMaxNoInterv() + 1);
            // DateSignalement
            prepStat.setDate(2, getTodayDate());
            // DescritptifBref
            if (tfDescription.getText().equals("")) {
                prepStat.setNull(3, Types.VARCHAR);
            } else {
                prepStat.setString(3, tfDescription.getText());
            }
            // SignaleurIncident
            if (tfSignaleur.getText().equals("")) {
                prepStat.setNull(4, Types.VARCHAR);
            } else {
                prepStat.setString(4, tfSignaleur.getText());
            }
            // PreneurEnCharge
            prepStat.setString(5, tfPreneur.getText());
            // EtatInterv
            if (rbInterventionClose.isSelected()) {
                prepStat.setString(6, "Cléturé");
            }
            if (rbInterventionOnGoing.isSelected()) {
                prepStat.setString(6, "Encours");
            }
            if (rbINterventionNoted.isSelected()) {
                prepStat.setString(6, "Signalé");
            }
            // SuiviFournisseur
            prepStat.setBoolean(7, false);
            // Date Null sans suivi Fournisseur +EtatRetour Null
            prepStat.setNull(8, Types.TIMESTAMP);
            prepStat.setNull(9, Types.TIMESTAMP);
            prepStat.setNull(10, Types.TIMESTAMP);
            prepStat.setNull(11, Types.VARCHAR);

            // DateRemise
            if (tfDateRemise.getText().equals("")) {
                prepStat.setNull(12, Types.TIMESTAMP);
            } else {
                prepStat.setDate(12, getDateFromField(tfDateRemise));
            }
            // tempsInterne
            prepStat.setInt(13, Integer.parseInt(tfInternTime.getText()));
            // Resultat
            if (rbResultClose.isSelected()) {
                prepStat.setString(14, "OK");
            }
            if (rbResultOnGoing.isSelected()) {
                prepStat.setString(14, "Déclassé");
            }
            if (rbResultNoted.isSelected()) {
                prepStat.setString(14, "en Suspens");
            } else
                prepStat.setNull(14, Types.VARCHAR);
            // PCUnit
            prepStat.setString(15, cbFkPcUnit.getSelectedItem().toString());

            // TypeIntervention
            prepStat.setString(16, returnTypeIntervID());

            // Fournisseur
            if (cbFkFournIntervenant.getSelectedIndex() == -1)
                prepStat.setNull(17, Types.VARCHAR);
            else
                prepStat.setString(17, returnFournisseurID());

            // Exécution de l'instruction complétée
            prepStat.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    // Récupéré Data à partir d'un field
    private java.sql.Date getDateFromField(JTextField dateF) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        java.util.Date TFAsdate = null;

        try {
            TFAsdate = dateFormat.parse(dateF.getText());
        } catch (ParseException pe) {
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        java.sql.Date date = java.sql.Date.valueOf(sdf.format(TFAsdate));

        return date;
    }

    // Récupération date du jour en java.sql.Date directement
    private java.sql.Date getTodayDate() {
        long millis = System.currentTimeMillis();
        java.sql.Date date = new java.sql.Date(millis);
        return date;
    }

    private int getMaxNoInterv() {
        String sqlinstruction = "Select NoInterv from Intervention";
        List<Integer> noIntervList = new ArrayList<>();
        try {
            PreparedStatement prepStat = mWindow.getConnexion().prepareStatement(sqlinstruction);
            ResultSet rs = prepStat.executeQuery(sqlinstruction);
            while (rs.next()) {
                noIntervList.add(rs.getInt("NoInterv"));

            }
            noIntervList.add(0);
        } catch (SQLException e) {
        }
        int[] ArrayNoInterv = new int[noIntervList.size()];
        for (int i = 0; i < ArrayNoInterv.length; i++) {
            ArrayNoInterv[i] = noIntervList.get(i).intValue();
        }

        int max = 0;
        for (int counter = 0; counter < ArrayNoInterv.length; counter++) {
            if (ArrayNoInterv[counter] > max) {
                max = ArrayNoInterv[counter];
            }
        }

        return max;
    }

    private String returnTypeIntervID() {
        switch (cbFkTypeIntervention.getSelectedItem().toString()) {
            case "Panne Disque":
                return "I01";
            case "Carte Mére":
                return "I02";
            case "OS":
                return "I03";
            case "Applications":
                return "I04";
            case "Alimentation":
                return "I11";
            case " Carte Réseau":
                return "I21";
            case "Repréparation de la machine":
                return "I31";
            case "DVD":
                return "I32";
        }
        return null;
    }

    private String returnFournisseurID() {
        System.out.println(cbFkFournIntervenant.getSelectedItem().toString());
        switch (cbFkFournIntervenant.getSelectedItem().toString()) {
            case "Priminfo":
                return "F01";
            case "SHS":
                return "F02";
            case "BMX":
                return "F03";
        }
        return null;
    }

    private String[] getArrayFromSQL(String instruction, String name) {
        List<String> ListeTI = new ArrayList<String>();
        String[] Array;
        try {
            PreparedStatement prepStat = mWindow.getConnexion().prepareStatement(instruction);
            ResultSet rs = prepStat.executeQuery();
            while (rs.next()) {
                ListeTI.add(rs.getString(name));
            }
            Array = ListeTI.toArray(new String[ListeTI.size()]);

        } catch (SQLException e) {
            Array = ListeTI.toArray(new String[ListeTI.size()]);
        }
        return Array;
    }
}
