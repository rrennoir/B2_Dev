package Serie9;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Fenetre extends JFrame {
    private int width = 370;
    private int height = 400;
    private Container cont;
    private JMenuBar barre = new JMenuBar();
    private JMenu menuApp, menuEtu, menuInfo;
    private JMenuItem menuItemQuit, menuItemInscription, menuItemIesn, menuItemAide;
    private JPanel panInscription, panInfo;
    private JLabel hello = new JLabel("Bienvenue dans l’application HENALLUX");

    public Fenetre() {
        super("Inscription à l’IESN");
        setBounds(0, 0, width, height);

        menuApp = new JMenu("Application");
        menuEtu = new JMenu("Etudiant");
        menuInfo = new JMenu("Information");

        setJMenuBar(barre);
        barre.add(menuApp);
        barre.add(menuEtu);
        barre.add(menuInfo);

        menuItemQuit = new JMenuItem("Quitter");
        menuItemInscription = new JMenuItem("Inscription");
        menuItemIesn = new JMenuItem("IESN");
        menuItemAide = new JMenuItem("Aide");

        ActionQuit actionQuitListener = new ActionQuit();
        ActionIesn actionIesnListener = new ActionIesn();
        ActionAide actionAideListener = new ActionAide();
        ActionInscription actionInscriptionListener = new ActionInscription();
        menuItemQuit.addActionListener(actionQuitListener);
        menuItemIesn.addActionListener(actionIesnListener);
        menuItemAide.addActionListener(actionAideListener);
        menuItemInscription.addActionListener(actionInscriptionListener);

        menuApp.add(menuItemQuit);
        menuEtu.add(menuItemInscription);
        menuInfo.add(menuItemIesn);
        menuInfo.add(menuItemAide);

        MyWindowListener w = new MyWindowListener();
        this.addWindowListener(w);

        panInscription = new PanneauInscription(width, height, this);
        panInfo = new PanneauInfos(width, height);

        cont = getContentPane();
        cont.setLayout(null);
        hello.setBounds(60, 50, 250, 150);
        cont.add(hello);
        setVisible(true);
    }

    public void retourMenu() {
        cont.removeAll();
        cont.add(hello);
        cont.validate();
        cont.repaint();
    }

    private class ActionQuit implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }

    private class ActionIesn implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            cont.removeAll();
            cont.add(panInfo);
            cont.validate();
            cont.repaint();
        }
    }

    private class ActionAide implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            AideFenetre fenetre = new AideFenetre();
        }
    }

    private class ActionInscription implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            cont.removeAll();
            cont.add(panInscription);
            cont.validate();
            cont.repaint();
        }
    }
}
