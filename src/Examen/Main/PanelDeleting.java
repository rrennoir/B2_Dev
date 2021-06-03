package Examen.Main;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Examen.accessBD.*;

public class PanelDeleting extends JPanel {
	private MainWindow mWindow;
	private JLabel labelNoInterv, labelTypeInterv;
	private JComboBox<String> boxNoInterv, boxTypeInterv;
	private JButton buttonShowSelection, buttonConfirm, buttonNegate;
	private JPanel panelTypeInterv, panelButton;
	private JScrollPane panelSelected;
	private JTable tableSelected;
	private DefaultComboBoxModel modelComboxNoInterv;

	private class BoutonSelectionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {

			panelSelected.remove(tableSelected);
			tableSelected = new JTable(buildJTable((String) boxTypeInterv.getSelectedItem()));
			panelSelected.add(tableSelected);
			panelSelected.setViewportView(tableSelected);
			buttonConfirm.setEnabled(true);
			buttonNegate.setEnabled(true);
			boxNoInterv.setEnabled(true);

			updateNoIntervComboxModelContent(getNoIntervArray((String) boxTypeInterv.getSelectedItem()));
		}
	}

	private class BoutonConfirmerListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			deleteSelectedIntervention((int) boxNoInterv.getSelectedItem());
		}
	}

	private class BoutonAnnulerListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			resetDeletePage();
		}
	}

	public PanelDeleting(MainWindow window) {
		this.mWindow = window;
		this.setBounds(0, 0, 1920, 1000);
		this.setLayout(new GridLayout(3, 1));

		// Premier Panel S�lection Type Intervention
		labelTypeInterv = new JLabel("Type Intervention : ");
		boxTypeInterv = new JComboBox<String>(getTypeIntervArray());
		buttonShowSelection = new JButton("Montrer S�lection");
		buttonShowSelection.addActionListener(new BoutonSelectionListener());

		panelTypeInterv = new JPanel();
		panelTypeInterv.setLayout(new GridLayout(2, 2));
		panelTypeInterv.add(labelTypeInterv);
		panelTypeInterv.add(boxTypeInterv);
		panelTypeInterv.add(buttonShowSelection);

		// Second Panel : JTable Selected
		panelSelected = new JScrollPane();

		tableSelected = new JTable(buildJTable("xxx"));// Ajout d'une table vide pour "mettre en place " l'interface /
														// Pas avoir un panel enti�rement blanc
		panelSelected.add(tableSelected);
		panelSelected.setViewportView(tableSelected);

		// Troisi�me Panel: COnfirmation de la suppression
		panelButton = new JPanel();
		panelButton.setLayout(new GridLayout(2, 2));
		labelNoInterv = new JLabel("N� de l'intervention � supprimer");
		modelComboxNoInterv = new DefaultComboBoxModel();
		boxNoInterv = new JComboBox<String>(modelComboxNoInterv);
		boxNoInterv.setEnabled(false);
		buttonConfirm = new JButton("Confirmer");
		buttonConfirm.addActionListener(new BoutonConfirmerListener());
		buttonConfirm.setEnabled(false);
		buttonNegate = new JButton("Annuler");
		buttonNegate.addActionListener(new BoutonAnnulerListener());
		buttonNegate.setEnabled(false);
		panelButton.add(labelNoInterv);
		panelButton.add(boxNoInterv);
		panelButton.add(buttonNegate);
		panelButton.add(buttonConfirm);
		this.setVisible(true);

		// Ajout des Panels
		this.add(panelTypeInterv);
		this.add(panelSelected);
		this.add(panelButton);

	}

	// Cr�ation Mod�le de Table
	private TableModelGen buildJTable(String typeintervention) {
		TableModelGen table = null;
		String sqlinstruction = "SELECT interv.* FROM intervention interv INNER JOIN  typeintervention tint ON (interv.FkTypeInterv = tint.CodeTypeInt) WHERE tint.LibelleTypeInt = '"
				+ typeintervention + "'";
		try {
			PreparedStatement prepStat = mWindow.getConnexion().prepareStatement(sqlinstruction);
			table = AccessBDGen.creerTableModel(prepStat);
			return table;
		} catch (SQLException e) {
			e.printStackTrace();
			return table;
		}

	}

	// R�cup�ration Array des Types d'intervention
	private String[] getTypeIntervArray() {
		String sqlinstruction = "SELECT * FROM typeintervention ";

		List<String> ListeTypeIntervention = new ArrayList<>();
		try {
			PreparedStatement prepStat = mWindow.getConnexion().prepareStatement(sqlinstruction);
			ResultSet rs = prepStat.executeQuery();
			while (rs.next()) {
				ListeTypeIntervention.add(rs.getString("LibelleTypeInt"));
			}
			String[] Array = ListeTypeIntervention.toArray(new String[ListeTypeIntervention.size()]);

			return Array;
		} catch (SQLException e) {
			String[] Array = ListeTypeIntervention.toArray(new String[ListeTypeIntervention.size()]);
			return Array;
		}
	}

	// Met � jour le contenu de la comboBox NoInterv
	public void updateNoIntervComboxModelContent(int[] Array) {
		int size = modelComboxNoInterv.getSize();
		if (size > 0) {
			modelComboxNoInterv.removeAllElements();
		}
		for (int NoInterv : Array) {
			modelComboxNoInterv.addElement(NoInterv);

		}
		modelComboxNoInterv.removeElement((Integer) 0);
		this.boxNoInterv.setModel(modelComboxNoInterv);
	}

	// R�cup�re l'Array des NoIntervention pour la m�thode mettant � jour la
	// comboBox
	private int[] getNoIntervArray(String typeintervention) {
		String sqlinstruction = "SELECT interv.NoInterv FROM intervention interv INNER JOIN  typeintervention tint ON (interv.FkTypeInterv = tint.CodeTypeInt) WHERE tint.LibelleTypeInt = '"
				+ typeintervention + "'";
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
		return ArrayNoInterv;
	}

	private void deleteSelectedIntervention(int NoInterv) {
		String sqlinstruction = " DELETE FROM intervention WHERE NoInterv = ?";
		try {
			PreparedStatement prepStat = mWindow.getConnexion().prepareStatement(sqlinstruction);
			prepStat.setInt(1, NoInterv);
			int updatedLine = prepStat.executeUpdate();
			System.out.print(updatedLine);
		} catch (SQLException e) {
		}
	}

	private void resetDeletePage() {
		panelSelected.remove(tableSelected);
		tableSelected = new JTable(buildJTable("xxx"));
		panelSelected.add(tableSelected);
		panelSelected.setViewportView(tableSelected);
		buttonNegate.setEnabled(false);
		buttonConfirm.setEnabled(false);
		boxNoInterv.setEnabled(false);

	}
}
