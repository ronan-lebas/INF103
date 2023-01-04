package ui;
import java.lang.Math;
public class Hexagon {
    private int x;
    private int y;
    private float d;
    private int[] xList;
    private int[] yList;
    public Hexagon(int x, int y, int d) {
        this.x = x;
        this.y = y;
        this.d = d;
        this.xList = new int[6];
        this.yList = new int[6];
        
        double a = x;
        double b = y;
        a = a + d;
        b = b - d*Math.tan(Math.PI/3);
        xList[xList.length] = (int)Math.round(a);
        yList[yList.length] = (int)Math.round(b);


    }
}