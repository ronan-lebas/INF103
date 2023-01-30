package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Buttons extends JPanel {
    private Panel panel;
    public Buttons(Panel panel) {
        super();
        this.panel = panel;
        setBackground(Color.GRAY);
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(50,50));
        
        JButton solve = new JButton();
        add(solve,BorderLayout.CENTER);
        solve.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent event) {panel.getGUI().solve();}} );
    }
}
