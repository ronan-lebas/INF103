package maze;
import java.lang.Math;
import java.awt.*;
public class Hexagon extends Polygon {
    private int x;
    private int y;
    private float d;
    public final int npoints;
    private Color color;

    public Color getColor() {
        return color;
    }
    
    public void paint(Graphics2D g) {
        g.setColor(color);
        g.fillPolygon(this);
    }
    public Hexagon(int x, int y, int d, Color color) {
        npoints = 6;
        this.x = x;
        this.y = y;
        this.d = d;
        this.color = color;

        double a = x;
        double b = y;
        a = x + d;
        b = y - d*Math.tan(Math.PI/6);
        addPoint((int)Math.round(a),(int)Math.round(b));

        a = x + d;
        b = y + d*Math.tan(Math.PI/6);
        addPoint((int)Math.round(a),(int)Math.round(b));

        a = x;
        b = y + d/Math.cos(Math.PI/6);
        addPoint((int)Math.round(a),(int)Math.round(b));

        a = x - d;
        b = y + d*Math.tan(Math.PI/6);
        addPoint((int)Math.round(a),(int)Math.round(b));

        a = x - d;
        b = y - d*Math.tan(Math.PI/6);
        addPoint((int)Math.round(a),(int)Math.round(b));


        a = x;
        b = y - d/Math.cos(Math.PI/6);
        addPoint((int)Math.round(a),(int)Math.round(b));

        

    }
}