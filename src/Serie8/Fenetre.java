package Serie8;

import javax.swing.*;
import java.awt.*;

public class Fenetre extends JFrame
{
    private int width = 290;
    private int height = 340;
    private Container cont;
    private JPanel panPrive;

    public Fenetre()
    {
        super("Inscription à l’IESN");
        setBounds(0, 0, width, height);

        MyWindowListener w = new MyWindowListener();
        this.addWindowListener(w);

        panPrive = new PanneauFormulaire(width, height);
        
        cont = getContentPane();
        cont.setLayout(null);
        cont.add(panPrive);

        setVisible(true);
    }
}
