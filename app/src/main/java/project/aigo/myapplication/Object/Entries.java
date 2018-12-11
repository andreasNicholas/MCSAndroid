package project.aigo.myapplication.Object;

import java.util.Vector;

public class Entries {
    public static Vector<Entries> entryList = new Vector<>();
    private int x;
    private int y;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
