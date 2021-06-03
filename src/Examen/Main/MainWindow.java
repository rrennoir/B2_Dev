package Examen.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import Examen.accessBD.*;

public class MainWindow extends JFrame {

    private int width = 1280, height = 720;
    private Container cont;
    private JMenuBar menuBar = new JMenuBar();
    private JMenu mMenu, mOption, mHelp;
    private JMenuItem miConnection, miInsert, miList, miSearch, miDelete, miExit;
    private JLabel hello = new JLabel("Hello there");
    private WindowConnection wc;
    private Connection connexion;

    public Connection getConnexion() {
        return connexion;
    }

    private PanelMenuListing panelMenuListing;
    private PanelListeIntervention panelList;
    private PanelDeleting panelDelete;
    private PanInsert panelInsert;

    public MainWindow() {
        super("Suivi de maintenance du matériel Informatique");
        this.setBounds((1920 - width) / 2, (1080 - height) / 2, width, height);

        mMenu = new JMenu("Menu");
        mOption = new JMenu("Options");
        mHelp = new JMenu("Aide");

        miConnection = new JMenuItem("Connexion");
        miExit = new JMenuItem("Quitter");
        miInsert = new JMenuItem("Insertion");
        miInsert.addActionListener(new InsertionMenuListener());
        miList = new JMenuItem("List");
        miList.addActionListener(new ListerMenuListener());
        miDelete = new JMenuItem("Suppression");
        miDelete.addActionListener(new SuppressionMenuListener());
        miSearch = new JMenuItem("Recherche");
        miSearch.addActionListener(new RechercheMenuListener());

        miConnection.addActionListener(new AlConnection());
        miExit.addActionListener(new AlExit());

        mMenu.add(miConnection);
        mMenu.add(miExit);

        mOption.add(miInsert);
        mOption.add(miList);
        mOption.add(miDelete);
        mOption.add(miSearch);
        mOption.setEnabled(false);

        this.setJMenuBar(menuBar);
        menuBar.add(mMenu);
        menuBar.add(mOption);
        menuBar.add(mHelp);

        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        cont = getContentPane();
        cont.setLayout(null);
        hello.setBounds(60, 50, 250, 150);
        cont.add(hello);

        this.setVisible(true);

        OpenConnectionWindow();
    }

    private void OpenConnectionWindow() {
        wc = new WindowConnection();
        wc.addConnectionListener(new CrConnexion());
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    private class AlConnection implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            OpenConnectionWindow();
        }
    }

    private class AlExit implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }

    private class CrConnexion implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                connexion = AccessBDGen.connecter("intervmaintenance", wc.getNameFieldText(),
                        wc.getPasswordFieldText());
                wc.dispose();

                mOption.setEnabled(true);
                panelMenuListing = new PanelMenuListing(MainWindow.this);
                panelList = new PanelListeIntervention(MainWindow.this);
                panelDelete = new PanelDeleting(MainWindow.this);
                panelInsert = new PanInsert(MainWindow.this);
                miConnection.setEnabled(false);

            } catch (SQLTimeoutException sqltoe) {
                // Doesn't throw SQLTimeoutException ?
                wc.setErrorMessage("timeout");

            } catch (SQLException sqlE) {
                wc.setErrorMessage("error");
            }
        }
    }

    private class RechercheMenuListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {

            cont.removeAll();
            cont.add(panelMenuListing);
            cont.validate();
            cont.repaint();
            // MainWindow.this.setBounds(0, 0, 1920, 1080);
        }
    }

    private class ListerMenuListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {

            cont.removeAll();
            cont.add(panelList);
            cont.validate();
            cont.repaint();
            // MainWindow.this.setBounds(0, 0, 1920, 1080);
        }
    }

    private class SuppressionMenuListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {

            cont.removeAll();
            cont.add(panelDelete);
            cont.validate();
            cont.repaint();
            // MainWindow.this.setBounds(0, 0, 1920, 1080);
        }
    }

    private class InsertionMenuListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {

            cont.removeAll();
            cont.add(panelInsert);
            cont.validate();
            cont.repaint();
        }
    }

    public Container getCont() {
        return cont;
    }

    public void Welcome() {
        cont.removeAll();
        cont.add(hello);
        cont.validate();
        cont.repaint();
    }
}
