package Model;

import java.sql.Date;
/**
 * The Division class is a model class. This class is used to hold the division information pulled from the
 * data base.
 * */
public class Division {
    private int divisionID;
    private String division;
    private Date createDate;
    private String createdBy;
    private Date lastUpdate;
    private String lastUpdatedBy;
    private int countryID;

/**
 * This is the constructor for the Division class.
 * @param divisionID id of the division
 * @param division name of the division
 * @param createDate date the division was created
 * @param createdBy name of user who created the division
 * @param lastUpdate date updates were last made
 * @param lastUpdatedBy user name of who did the updates
 * @param countryID id of the country the division is in
 * */
    public Division(int divisionID, String division, Date createDate, String createdBy, Date lastUpdate, String lastUpdatedBy, int countryID){

        this.division = division;
        this.divisionID = divisionID;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.countryID = countryID;
    }
/**
 * Getter methods.
 * */
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
/**
 * Override toString method which lets it return what is specified below the division name.
 * */
    @Override
    public String toString() {
        return division;
    }
}
