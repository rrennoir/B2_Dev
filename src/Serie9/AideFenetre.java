package Serie9;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AideFenetre extends JFrame {
    private JButton back;
    private Container cont;

    AideFenetre() {
        super("Aide");
        this.setBounds(0, 0, 300, 400);

        back = new JButton("Retour menu principal");
        ButtonActionListener buttonListener = new ButtonActionListener();
        back.setBounds(50, 50, 200, 50);
        back.addActionListener(buttonListener);

        cont = getContentPane();
        cont.setLayout(null);
        cont.add(back);

        this.setVisible(true);
    }

    public class ButtonActionListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            AideFenetre.this.dispose();
        }
    }
}
