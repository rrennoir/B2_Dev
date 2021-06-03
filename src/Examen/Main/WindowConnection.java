package Examen.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class WindowConnection extends JFrame {

    private int width = 260, height = 230;

    private JPanel pWindow, pForm, pTitle, pConnection;
    private JButton bConnection;
    private JTextField tfName, tfPassword;
    private JLabel lTitle, lName, lPassword, lError;
    private Container cont;

    public WindowConnection() {

        super("Connexion");
        this.setBounds((1920 - width) / 2, (1080 - height) / 2, width, height);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                WindowConnection.this.dispose();
            }
        });

        lTitle = new JLabel("Connexion", JLabel.CENTER);
        pTitle = new JPanel(new FlowLayout());
        pTitle.setBounds(0, 0, 225, 100);
        pTitle.add(lTitle);

        tfName = new JTextField();
        tfPassword = new JTextField();
        lName = new JLabel("Nom: ", JLabel.RIGHT);
        lPassword = new JLabel("Mot de passe: ", JLabel.RIGHT);

        pForm = new JPanel(new GridLayout(2, 2, 0, 10));
        pForm.add(lName);
        pForm.add(tfName);
        pForm.add(lPassword);
        pForm.add(tfPassword);

        lError = new JLabel("");
        lError.setHorizontalAlignment(JLabel.CENTER);
        lError.setForeground(Color.RED);

        bConnection = new JButton("Connexion");
        bConnection.setHorizontalAlignment(JButton.CENTER);

        pConnection = new JPanel(new GridLayout(2, 1, 0, 10));
        pConnection.add(lError);
        pConnection.add(bConnection);

        pWindow = new JPanel(new BorderLayout(0, 10));
        pWindow.setBounds(9, 5, 225, 175);
        pWindow.add(pTitle, BorderLayout.NORTH);
        pWindow.add(pForm, BorderLayout.CENTER);
        pWindow.add(pConnection, BorderLayout.SOUTH);

        cont = getContentPane();
        cont.setLayout(null);
        cont.add(pWindow);

        this.setVisible(true);
    }

    public void addConnectionListener(ActionListener al) {
        bConnection.addActionListener(al);
    }

    public String getNameFieldText() {
        return tfName.getText();
    }

    public String getPasswordFieldText() {
        return tfPassword.getText();
    }

    public void setErrorMessage(String cause) {
        if (cause.equals("timeout"))
            lError.setText("Connexion Timeout");

        else if (cause.equals("error"))
            lError.setText("Connexion échouée");

        tfName.setText(null);
        tfPassword.setText(null);
    }
}