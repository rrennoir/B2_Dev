package Examen.Main;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Examen.accessBD.*;

public class PanelListingLocalStatut extends JPanel {

	private MainWindow mWindow;
	private JLabel localLabel;
	private JComboBox<String> localBox;
	private JPanel SearchPanel;
	private JScrollPane resultPanel;
	private JTable tableResultat;
	private JButton buttonResearch;

	private class ResearchButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (resultPanel.getComponentCount() == 1) {
				resultPanel.remove(tableResultat);
			}
			tableResultat = new JTable(buildJTable((String) localBox.getSelectedItem()));
			resultPanel.add(tableResultat);
			resultPanel.setViewportView(tableResultat);
		}
	}

	public PanelListingLocalStatut(MainWindow window) {
		this.mWindow = window;
		this.setBounds(0, 0, 1920, 1000);
		this.setLayout(new GridLayout(2, 1));
		// Panel Recherche
		localBox = new JComboBox<String>(getLocalArray());
		localLabel = new JLabel("Local : ");
		buttonResearch = new JButton("Recherche");
		buttonResearch.addActionListener(new ResearchButtonListener());
		SearchPanel = new JPanel();
		SearchPanel.setLayout(new GridLayout(2, 1));
		SearchPanel.add(localLabel);
		SearchPanel.add(localBox);
		SearchPanel.add(buttonResearch);
		// Panel Result (Géré dans bouton Recherche)
		resultPanel = new JScrollPane();
		// Panel General
		this.add(SearchPanel);
		this.add(resultPanel);
		this.setVisible(true);
	}

	// Récupére Array des Locaux
	private String[] getLocalArray() {
		String sqlinstruction = "SELECT * FROM pcunit GROUP BY pcunit.local ";

		List<String> ListeLocal = new ArrayList<>();
		String[] Array;
		try {
			PreparedStatement prepStat = mWindow.getConnexion().prepareStatement(sqlinstruction);
			ResultSet rs = prepStat.executeQuery();
			while (rs.next()) {
				ListeLocal.add(rs.getString("Local"));
			}
			Array = ListeLocal.toArray(new String[ListeLocal.size()]);

		} catch (SQLException e) {
			Array = ListeLocal.toArray(new String[ListeLocal.size()]);
		}
		return Array;
	}

	// Création Modele de Table
	private TableModelGen buildJTable(String local) {
		TableModelGen table = null;
		String sqlinstruction = "SELECT interv.* FROM intervention interv INNER JOIN  pcunit  pc ON (interv.FkPcUnit = pc.IdPcUnit) WHERE pc.Local = '"
				+ local + "' AND interv.EtatInterv = 'Signalé'";
		try {
			PreparedStatement prepStat = mWindow.getConnexion().prepareStatement(sqlinstruction);
			table = AccessBDGen.creerTableModel(prepStat);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return table;
	}
}
