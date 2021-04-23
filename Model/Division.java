package Model;

import java.sql.Date;

public class Division {
    private int divisionID;
    private String division;
    private Date createDate;
    private String createdBy;
    private Date lastUpdate;
    private String lastUpdatedBy;
    private int countryID;


    public Division(int divisionID, String division, Date createDate, String createdBy, Date lastUpdate, String lastUpdatedBy, int countryID){

        this.division = division;
        this.divisionID = divisionID;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.countryID = countryID;
    }

    public int getDivisionID() {
        return divisionID;
    }

    public String getDivision() {
        return division;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public int getCountryID() {
        return countryID;
    }

    @Override
    public String toString() {
        return division;
    }
}
