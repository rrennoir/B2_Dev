package Examen.Main;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Examen.accessBD.*;

public class PanelListeIntervention extends JPanel {
	private MainWindow mWindow;
	private JTable tableIntervention;
	private JPanel panelChoix;
	private JScrollPane panelIntervention;
	private JList<String> listTable;
	private JButton buttonInterv, buttonTable;

	private class InterventionBoutonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			setupListingIntervention();

		}
	}

	private class TableBoutonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (panelIntervention.getComponentCount() == 1) {
				panelIntervention.removeAll();
			}
			panelIntervention.add(listTable);
			panelIntervention.setViewportView(listTable);

		}
	}

	public PanelListeIntervention(MainWindow window) {
		this.mWindow = window;
		this.setBounds(0, 0, 1920, 1000);
		this.setLayout(new GridLayout(2, 1));
		// Cr�ation Panel
		panelChoix = new JPanel();
		panelChoix.setLayout(new GridLayout(1, 2));
		panelIntervention = new JScrollPane();
		// Bouton Choix
		buttonTable = new JButton("Lister Tables");
		buttonTable.addActionListener(new TableBoutonListener());
		buttonInterv = new JButton("Lister Interventions");
		buttonInterv.addActionListener(new InterventionBoutonListener());
		panelChoix.add(buttonTable);
		panelChoix.add(buttonInterv);
		// Panel Liste Table
		listTable = new JList<String>(getTableArray());
		this.add(panelChoix);
		this.add(panelIntervention);
		this.setVisible(true);

	}

	private TableModelGen buildJTable() {
		String sqlinstruction = "SELECT * FROM intervention";

		TableModelGen table = null;
		try {
			PreparedStatement prepStat = mWindow.getConnexion().prepareStatement(sqlinstruction);
			table = AccessBDGen.creerTableModel(prepStat);

			return table;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return table;
	}

	public void setupListingIntervention() {
		if (panelIntervention.getComponentCount() == 1) {
			panelIntervention.removeAll();
		}
		tableIntervention = new JTable(buildJTable());
		panelIntervention.add(tableIntervention);
		panelIntervention.setViewportView(tableIntervention);
		panelIntervention.setVisible(true);
	}

	public String[] getTableArray() {
		List<String> ListeTables = new ArrayList<String>();
		try {
			DatabaseMetaData dbmb = mWindow.getConnexion().getMetaData();
			ResultSet rs = dbmb.getTables(null, null, "%", null);
			while (rs.next()) {
				ListeTables.add(rs.getString(3));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		String[] ArrayTable = ListeTables.toArray(new String[ListeTables.size()]);
		return ArrayTable;
	}
}
