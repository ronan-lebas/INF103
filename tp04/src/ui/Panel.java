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
        //int n = 500;
        //int m = 500;
        setPreferredSize(new Dimension(n,m));
        //Buttons buttons = new Buttons(this);
        //add(buttons,BorderLayout.SOUTH);
        JToggleButton toggle = new JToggleButton("Show solution");
        add(toggle,BorderLayout.SOUTH);
        toggle.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent event) {
            gui.getMaze().setShowSolution(toggle.isSelected());
            System.out.println(gui.getMaze().getShowSolution());
            if(gui.getMaze().getShowSolution()) gui.solve();
            else repaint();
        }} );
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
        if(gui.getMaze().getShowSolution()) gui.solve();
     }

     public void notifyForUpdate(){
         repaint();
     }

}