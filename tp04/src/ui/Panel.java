package ui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import graph.Graph;
import maze.Hexagon;
import maze.Maze;

public class Panel extends JPanel {
    private GUI gui;
    
    public GUI getGUI(Panel this){return gui;}
    

    public Panel(GUI gui) {
        super();
        this.gui = gui;
        

        setBackground(Color.GRAY);
        setLayout(new BorderLayout());
        int n = 2*getGUI().getMaze().getD()*getGUI().getMaze().getWidth() + 2*getGUI().getMaze().getBorder();
        int m = 2*getGUI().getMaze().getD()*getGUI().getMaze().getHeight() + (int) (1.5*getGUI().getMaze().getBorder());
        setPreferredSize(new Dimension(n,m));
        Buttons buttons = new Buttons(this);
        add(buttons,BorderLayout.SOUTH);
        repaint();
        addMouseListener(new MouseDetector(this));
        addMouseMotionListener(new MouseDetector(this));
    }
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        getGUI().getMaze().paintHexagons(g2);

     }
     public void notifyForUpdate(){
         repaint();
     }
    
     public void repaintHexagon(Graphics g, int i, int j, Color color) {
        g.setColor(color);
        g.fillPolygon(getGUI().getMaze().getHexagonList()[i][j]);
    }

}