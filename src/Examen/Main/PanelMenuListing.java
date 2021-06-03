package Examen.Main;

import java.awt.event.*;
import javax.swing.*;

public class PanelMenuListing extends JPanel {
	private JButton buttonListeDateLot, buttonListeLocalEnCours;
	private JLabel labelIntroListe;
	private MainWindow mWindow;

	private class ListingDateListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			mWindow.getCont().removeAll();
			mWindow.getCont().add(new PanelListingDateLot(mWindow));
			mWindow.getCont().validate();
			mWindow.getCont().repaint();
		}
	}

	private class ListingLocalListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			mWindow.getCont().removeAll();
			mWindow.getCont().add(new PanelListingLocalStatut(mWindow));
			mWindow.getCont().validate();
			mWindow.getCont().repaint();
		}
	}

	public PanelMenuListing(MainWindow window) {
		this.setLayout(null);
		this.setBounds(0, 0, 1920, 1000);
		this.mWindow = window;

		buttonListeDateLot = new JButton("Recherche en fonction de la date et du lot");
		buttonListeDateLot.addActionListener(new ListingDateListener());
		buttonListeDateLot.setBounds(1300, 500, 400, 200);

		buttonListeLocalEnCours = new JButton("Recherche en fonction du local/En Cours");
		buttonListeLocalEnCours.addActionListener(new ListingLocalListener());
		buttonListeLocalEnCours.setBounds(620, 500, 400, 200);

		labelIntroListe = new JLabel("Menu des recherches disponibles");
		labelIntroListe.setBounds(960, 140, 200, 200);

		this.add(buttonListeDateLot);
		this.add(buttonListeLocalEnCours);
		this.add(labelIntroListe);
		this.setVisible(true);
	}
}
