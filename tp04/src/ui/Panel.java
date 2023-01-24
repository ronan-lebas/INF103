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
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        g.setColor(Color.blue);
        Hexagon h = new Hexagon(200, 200, 100);
        g.fillPolygon(h.getXList(),h.getYList(),6);
     }   

    }