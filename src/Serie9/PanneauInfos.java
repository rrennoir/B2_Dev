package Serie9;

import javax.swing.*;

public class PanneauInfos extends JPanel {
    private JLabel IesnInfo;

    public PanneauInfos(int width, int height)
    {
        this.setBounds(0, 0, width, height);

        // IesnInfo = new JLabel("<html>I<br>E<br>S<br>N<br></html>");
        IesnInfo = new JLabel("some informationn");
        IesnInfo.setBounds(50, 50, 200, 50);
        this.add(IesnInfo);
    }

}
