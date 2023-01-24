package ui;
import java.lang.Math;
public class Hexagon {
    private int x;
    private int y;
    private float d;
    private int[] xList;
    private int[] yList;
    
    public int[] getXList(){
        return this.xList;
    }
    public int[] getYList(){
        return this.yList;
    }
    
    public Hexagon(int x, int y, int d) {
        this.x = x;
        this.y = y;
        this.d = d;
        this.xList = new int[6];
        this.yList = new int[6];

        double a = x;
        double b = y;
        a = x + d;
        b = y - d*Math.tan(Math.PI/3);
        xList[0] = (int)Math.round(a);
        yList[0] = (int)Math.round(b);

        a = x + d;
        b = y + d*Math.tan(Math.PI/3);
        xList[1] = (int)Math.round(a);
        yList[1] = (int)Math.round(b);

        a = x - d;
        b = y - d*Math.tan(Math.PI/3);
        xList[2] = (int)Math.round(a);
        yList[2] = (int)Math.round(b);

        a = x - d;
        b = y + d*Math.tan(Math.PI/3);
        xList[3] = (int)Math.round(a);
        yList[3] = (int)Math.round(b);

        a = x;
        b = y - d/Math.cos(Math.PI/3);
        xList[4] = (int)Math.round(a);
        yList[4] = (int)Math.round(b);

        a = x;
        b = y + d/Math.cos(Math.PI/3);
        xList[5] = (int)Math.round(a);
        yList[5] = (int)Math.round(b);

    }
}