package ui;

import java.awt.*;

import javax.swing.*;

public class Panel extends JPanel {
    public Panel() {
        super();
        setBackground(Color.black);
        setLayout(new BorderLayout());
        Buttons buttons = new Buttons();
        add(buttons,BorderLayout.SOUTH);
    }
}
