package Model;

public class Division {
    private int divisionID;
    private String division;


    public Division(int divisionID, String division){
        this.division = division;
        this.divisionID = divisionID;
    }

    public int getDivisionID() {
        return divisionID;
    }

    public String getDivision() {
        return division;
    }

    @Override
    public String toString() {
        return division;
    }
}
