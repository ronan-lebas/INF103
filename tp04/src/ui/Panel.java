package ui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import graph.Graph;
import maze.Maze;

public class Panel extends JPanel {
    private Maze maze;
    private GUI gui;
    
    public GUI getGUI(Panel this){return gui;}
    
    private Hexagon[][] hexagonList;
    public Hexagon[][] getHexagonList(){return hexagonList;}
    private final int d = 50;
    private final int border = 2*d;
    private final int origin = border + d/5;

    public Panel(GUI gui, Maze maze) {
        super();
        this.maze = maze;
        this.gui = gui;
        hexagonList = new Hexagon[this.maze.getWidth()][this.maze.getHeight()];
        for(int i = 0; i < maze.getWidth(); i++) {
            for(int j = 0; j < maze.getHeight(); j++) {
                switch((maze.getBoxes()[i][j]).getLabel()) {
                    case "W": hexagonList[i][j] = new Hexagon(origin + (d+d/20)*((j%2)+2*i), origin + (d-d/10)*(2*j), d, maze.getBoxes()[i][j].getColor()); break;
                    case "E": hexagonList[i][j] = new Hexagon(origin + (d+d/20)*((j%2)+2*i), origin + (d-d/10)*(2*j), d, maze.getBoxes()[i][j].getColor()); break;
                    case "A": hexagonList[i][j] = new Hexagon(origin + (d+d/20)*((j%2)+2*i), origin + (d-d/10)*(2*j), d, maze.getBoxes()[i][j].getColor()); break;
                    case "D": hexagonList[i][j] = new Hexagon(origin + (d+d/20)*((j%2)+2*i), origin + (d-d/10)*(2*j), d, maze.getBoxes()[i][j].getColor()); break;
                }
        }
    }

        setBackground(Color.GRAY);
        setLayout(new BorderLayout());
        int n = 2*d*maze.getWidth() + 2*border;
        int m = 2*d*maze.getHeight() + (int) (1.5*border);
        setPreferredSize(new Dimension(n,m));
        Buttons buttons = new Buttons(this);
        add(buttons,BorderLayout.SOUTH);
        repaint();
        addMouseListener(new MouseDetector(this));
    }
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        for(int i = 0; i < maze.getWidth(); i++) {
            for(int j = 0; j < maze.getHeight(); j++) {
                g.setColor(hexagonList[i][j].getColor());
                g.fillPolygon(hexagonList[i][j]);
            }
        }

     }
    
    public void repaintHexagon(Graphics g, int i, int j, Color color) {
        g.setColor(color);
        g.fillPolygon(hexagonList[i][j]);
    }

}