package project.aigo.myapplication.Object;

import java.util.Vector;

public class Branch {
    public static Vector<Branch> branchList = new Vector<>();
    public static Vector<Branch> userBranchList = new Vector<>();

    private int branchId;
    private String branchName;
    private int sportId;
    private String sportName;

    public int getBranchId() {
        return branchId;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
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
