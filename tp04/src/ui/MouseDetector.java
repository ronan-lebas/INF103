package ui;

import javax.swing.event.MouseInputAdapter;

import javax.swing.*;
import java.awt.event.*;

public class MouseDetector extends MouseInputAdapter{
    private Panel panel;
    public MouseDetector(Panel panel){this.panel = panel;}
    @Override
    public final void mousePressed(MouseEvent e){
        panel.getGUI().click(e.getX(),e.getY(),SwingUtilities.isLeftMouseButton(e));
    }
    @Override
    public final void mouseReleased(MouseEvent e){
        panel.getGUI().mouseReleased(e.getX(),e.getY());
    }
    @Override
    public final void mouseDragged(MouseEvent e){
        panel.getGUI().click(e.getX(),e.getY(),SwingUtilities.isLeftMouseButton(e));
    }

}
