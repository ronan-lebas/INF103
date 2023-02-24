package ui;

import javax.swing.event.MouseInputAdapter;
import javax.swing.*;
import java.awt.event.*;
/**
 * MouseDetector is a class that listens to mouse events on a Panel and notifies the GUI accordingly.
 */
public class MouseDetector extends MouseInputAdapter {
    private Panel panel;

    /**
     * Constructs a new MouseDetector object with a given Panel.
     * 
     * @param panel the Panel to detect mouse events on
     */
    public MouseDetector(Panel panel) {
        this.panel = panel;
    }

    /**
     * Invoked when a mouse button has been pressed on the Panel.
     * 
     * @param e the MouseEvent that occurred
     */
    @Override
    public final void mousePressed(MouseEvent e) {
        panel.getGUI().click(e.getX(), e.getY(), SwingUtilities.isLeftMouseButton(e));
    }

    /**
     * Invoked when a mouse button has been released on the Panel.
     * 
     * @param e the MouseEvent that occurred
     */
    @Override
    public final void mouseReleased(MouseEvent e) {
        panel.getGUI().mouseReleased();
    }

    /**
     * Invoked when the mouse is being dragged on the Panel.
     * 
     * @param e the MouseEvent that occurred
     */
    @Override
    public final void mouseDragged(MouseEvent e) {
        panel.getGUI().click(e.getX(), e.getY(), SwingUtilities.isLeftMouseButton(e));
    }

}
