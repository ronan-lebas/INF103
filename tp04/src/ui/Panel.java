package ui;

import java.awt.*;

import javax.swing.*;

import maze.Maze;

public class Panel extends JPanel {
    private Maze maze;
    private Hexagon[][] hexagonList;

    public Panel(Maze maze) {
        super();
        this.maze = maze;
        hexagonList = new Hexagon[this.maze.getWidth()][this.maze.getHeight()];
        int d = 50;
        int origin = 100;
        for(int i = 0; i < maze.getWidth(); i++) {
            for(int j = 0; j < maze.getHeight(); j++) {
                hexagonList[i][j] = new Hexagon(origin + (d+d/10)*((j%2)+2*i), origin + (d)*(2*j), d);
            }
        }

        setBackground(Color.black);
        setLayout(new BorderLayout());
        Buttons buttons = new Buttons();
        add(buttons,BorderLayout.SOUTH);
        repaint();
    }
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        g.setColor(Color.blue);
        for(int i = 0; i < maze.getWidth(); i++) {
            for(int j = 0; j < maze.getHeight(); j++) {
                g.fillPolygon(hexagonList[i][j]);
            }
        }

     }   

}