package ui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class UI extends JFrame {

    public UI() {
        super("Labyrinthe");
        Panel panel = new Panel();
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

        load.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent event) {System.out.println("tets");}} );






        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(700,700));
        pack();
        setVisible(true);


    }
}
