package ui;
import java.lang.Math;
import java.awt.Polygon;
public class Hexagon extends Polygon {
    private int x;
    private int y;
    private float d;
    public final int npoints;
    
    public Hexagon(int x, int y, int d) {
        npoints = 6;
        this.x = x;
        this.y = y;
        this.d = d;

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