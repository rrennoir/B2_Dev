package Examen.Main;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Examen.accessBD.*;
import java.text.*;

public class PanelListingDateLot extends JPanel {

	private MainWindow mWindow;
	private JComboBox<String> boxLot;
	private JButton buttonSearch;
	private JLabel labelLot, labelDate;
	private JTextField dateField;
	private JPanel searchPanel;
	private JScrollPane resultPanel;
	private JTable tableResultat;

	private class ResearchListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (dateField.getText().matches("\\d{2}/\\d{2}/\\d{4}")) {
				if (resultPanel.getComponentCount() == 1) {
					resultPanel.remove(tableResultat);
				}
				tableResultat = new JTable(
						buildJTable(Integer.parseInt((String) boxLot.getSelectedItem()), getDateFromField()));

				resultPanel.add(tableResultat);
				resultPanel.setViewportView(tableResultat);

			}
		}
	}

	public PanelListingDateLot(MainWindow window) {
		this.mWindow = window;
		this.setBounds(0, 0, 1920, 1000);
		this.setLayout(new GridLayout(2, 1));
		
		// Panel Recherche
		boxLot = new JComboBox<String>(getLotArray());
		buttonSearch = new JButton("Lancer recherche");
		buttonSearch.addActionListener(new ResearchListener());
		labelLot = new JLabel("Lot:");
		labelDate = new JLabel("Date :");
		labelDate.setToolTipText("dd/MM/yyyy");
		dateField = new JTextField();

		searchPanel = new JPanel();
		searchPanel.setLayout(new GridLayout(3, 1));
		searchPanel.add(labelDate);
		searchPanel.add(dateField);
		searchPanel.add(labelLot);
		searchPanel.add(boxLot);
		searchPanel.add(buttonSearch);
		
		// Panel Resultat
		resultPanel = new JScrollPane();

		this.add(searchPanel);
		this.add(resultPanel);
		this.setVisible(true);
	}

	// Récupére les différents Lot
	private String[] getLotArray() {
		String sqlinstruction = "SELECT * FROM lotconfiguration ";

		List<String> ListeLot = new ArrayList<>();
		try {
			PreparedStatement prepStat = mWindow.getConnexion().prepareStatement(sqlinstruction);
			ResultSet rs = prepStat.executeQuery();
			while (rs.next()) {
				ListeLot.add(rs.getString("NoLot"));

			}
			String[] Array = ListeLot.toArray(new String[ListeLot.size()]);

			return Array;
		} catch (SQLException e) {
			String[] Array = ListeLot.toArray(new String[ListeLot.size()]);
			return Array;
		}
	}

	// Génére le tableau des valeurs
	private TableModelGen buildJTable(int lot, java.sql.Date date) {
		String sqlinstruction = "SELECT interv.* FROM pcunit  INNER JOIN intervention as interv  WHERE (interv.DateSignalement > "
				+ "'" + date + "'" + ") AND ( pcunit.FkLot = " + lot + " ) GROUP BY interv.NoInterv;";

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

	// Récupére la date en format java et la transforme en Date format SQL
	private java.sql.Date getDateFromField() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		java.util.Date TFAsdate = null;

		try {
			TFAsdate = dateFormat.parse(dateField.getText());
		} catch (ParseException pe) {
		}

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		java.sql.Date date = java.sql.Date.valueOf(sdf.format(TFAsdate));

		return date;
	}
}
