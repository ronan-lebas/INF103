package maze;

import java.lang.Math;
import java.awt.*;
/**
 * The Hexagon class represents a hexagon polygon with a center point (x, y), a distance from the center to closest sides (d), 
 * and a color. It extends the Polygon class and contains methods for painting the hexagon.
 */
public class Hexagon extends Polygon {
    private int x;
    private int y;
    private float d;
    public final int npoints;
    //the attribute color is used by the hexagon to paint itself, and is copied from the corresponding box in the maze
    private Color color;

    /**
     * Returns the color of the hexagon.
     * 
     * @return the color of the hexagon
     */
    public Color getColor() {
        return color;
    }

    /**
     * Paints the hexagon with its color.
     * 
     * @param g the graphics object to paint with
     */
    public void paint(Graphics g) {
        g.setColor(color);
        g.fillPolygon(this);
    }

    /**
     * Paints the hexagon with the specified color.
     * 
     * @param g     the graphics object to paint with
     * @param color the color to paint the hexagon with
     */
    public void paint(Graphics g, Color color) {
        g.setColor(color);
        g.fillPolygon(this);
    }

    /**
     * Constructs a new Hexagon with the specified center point, distance from
     * center to closest sides, and color.
     * 
     * @param x     the x-coordinate of the center point
     * @param y     the y-coordinate of the center point
     * @param d     the distance from the center point to the closest sides of the hexagon
     * @param color the color of the hexagon
     */
    public Hexagon(int x, int y, int d, Color color) {
        npoints = 6;
        this.x = x;
        this.y = y;
        this.d = d;
        this.color = color;

        //the hexagon is constructed by computing and adding the six points of the hexagon to the polygon
        double a = x;
        double b = y;
        a = x + d;
        b = y - d * Math.tan(Math.PI / 6);
        addPoint((int) Math.round(a), (int) Math.round(b));

        a = x + d;
        b = y + d * Math.tan(Math.PI / 6);
        addPoint((int) Math.round(a), (int) Math.round(b));

        a = x;
        b = y + d / Math.cos(Math.PI / 6);
        addPoint((int) Math.round(a), (int) Math.round(b));

        a = x - d;
        b = y + d * Math.tan(Math.PI / 6);
        addPoint((int) Math.round(a), (int) Math.round(b));

        a = x - d;
        b = y - d * Math.tan(Math.PI / 6);
        addPoint((int) Math.round(a), (int) Math.round(b));

        a = x;
        b = y - d / Math.cos(Math.PI / 6);
        addPoint((int) Math.round(a), (int) Math.round(b));
    }
}
