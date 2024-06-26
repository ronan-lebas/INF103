package ui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

/**
 * A JPanel class for drawing and managing the maze and its solution.
 * Contains a toggle button for showing or hiding the solution.
 */
public class Panel extends JPanel {
    private GUI gui;
    private JToggleButton toggle;

    /**
     * Constructs a new Panel with the specified GUI instance.
     * 
     * @param gui the GUI instance associated with this Panel
     */
    public Panel(GUI gui) {
        super();
        this.gui = gui;

        setBackground(Color.GRAY);
        setLayout(new BorderLayout());
        int n = 2 * getGUI().getMaze().getD() * getGUI().getMaze().getWidth() + 2 * getGUI().getMaze().getBorder();
        int m = 2 * getGUI().getMaze().getD() * getGUI().getMaze().getHeight()
                + (int) (1.5 * getGUI().getMaze().getBorder());
        setPreferredSize(new Dimension(n, m));
        JToggleButton toggle = new JToggleButton("Show solution");
        add(toggle, BorderLayout.SOUTH);
        toggle.addActionListener(new ActionListener() {
            /**
             * Invoked when the toggle button is clicked. Toggles the showing of the
             * solution in the maze.
             * If showing the solution is turned on, solves the maze and repaints the panel.
             * 
             * @param event the event that triggered this action
             */
            public void actionPerformed(ActionEvent event) {
                //copies the state of the toggle button to a maze attribute
                gui.getMaze().setShowSolution(toggle.isSelected());
                if (gui.getMaze().getShowSolution())
                    gui.solve(getGraphics());
                else
                    repaint();
            }
        });
        this.toggle = toggle;
        //adds a MouseDetector to the panel to listen for clicks and drags
        addMouseListener(new MouseDetector(this));
        addMouseMotionListener(new MouseDetector(this));
    }

    /**
     * Draws the maze and its solution (if showing the solution is turned on).
     * 
     * @param g the Graphics object used for drawing
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //turns on anti-aliasing
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        //asks the maze to draw itself
        getGUI().getMaze().paintHexagons(g2);
        //shows the solution if the toggle button is selected
        if (gui.getMaze().getShowSolution())
            gui.solve(g);
    }

    /**
     * Notifies the panel that it needs to be updated and repainted.
     */
    public void notifyForUpdate() {
        //handles the case when there isn't a solution
        if(! gui.getMaze().getShowSolution() && toggle.isSelected()) eraseSolution();
        
        repaint();
    }

    /**
     * Erases the solution from the maze and turns off the toggle button.
     */
    public void eraseSolution() {
        toggle.setSelected(false);
        JOptionPane.showMessageDialog(this, "There is no solution", "Error", JOptionPane.ERROR_MESSAGE);
        repaint();
    }

    /**
     * Returns the GUI instance associated with this Panel.
     * 
     * @return gui the GUI instance associated with this Panel
     */
    public GUI getGUI() {
        return gui;
    }

}