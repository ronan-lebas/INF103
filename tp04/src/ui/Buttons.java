package ui;

import javax.swing.*;
import java.awt.*;

public class Buttons extends JPanel {
    public Buttons() {
        super();
        setBackground(Color.GRAY);
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(50,50));
        JButton test = new JButton();
        add(test,BorderLayout.CENTER);
    }
}
