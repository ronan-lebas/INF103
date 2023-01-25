package ui;


import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import maze.*;
import main.*;


public class UI extends JFrame {
    private Main main;
    private Maze maze;
    private boolean modeSelected;
    public UI(Main main,Maze maze) {
        super("Labyrinthe");
        this.maze = maze;
        this.modeSelected = false;
        Panel panel = new Panel(maze);
        setContentPane(panel);
        
        JMenuBar menuBar = new JMenuBar();
        JMenu file = new JMenu("File");
        JMenu mode = new JMenu("Mode");
        JMenuItem load = new JMenuItem("Load");
        JMenuItem save = new JMenuItem("Save");
        JMenuItem solve = new JMenuItem("Solve");
        JMenuItem edit = new JMenuItem("Edit");
        setJMenuBar(menuBar);
        menuBar.add(file);
        menuBar.add(mode);
        file.add(load);
        file.add(save);
        mode.add(solve);
        mode.add(edit);

        load.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent event) {main.load();}} );
        save.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent event) {main.save();}} );
        solve.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent event) {modeSelected = false;}} );
        edit.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent event) {modeSelected = true;}} );        




        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(700,700));
        pack();
        setVisible(true);


    }
}
