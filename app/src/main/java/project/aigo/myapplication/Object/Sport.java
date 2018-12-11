package project.aigo.myapplication.Object;

import java.util.Vector;

public class Sport {
    public static Vector<Sport> sportList = new Vector<>();

    private int sportId;
    private String sportName;

    public Sport(String sportName) {
        this.sportName = sportName;
    }

    public Sport() {

    }

    public int getSportId() {
        return sportId;
    }

    public void setSportId(int sportId) {
        this.sportId = sportId;
    }

    public String getSportName() {
        return sportName;
    }

    public void setSportName(String sportName) {
        this.sportName = sportName;
    }
}
