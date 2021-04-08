package Model;

import java.sql.Date;

public class Users {
    private int userID;
    private String userName;
    private String userPassword;
    private Date dateCreated;
    private String createdBy;
    private Date lastUpdated;
    private String lastUpdatedBy;


    public Users (int userID, String userName, String userPassword, Date dateCreated, String createdBy, Date lastUpdated, String lastUpdatedBy){
        this.userID = userID;
        this.userName = userName;
        this.userPassword = userPassword;
        this.dateCreated = dateCreated;
        this.createdBy = createdBy;
        this.lastUpdated = lastUpdated;
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public int getUserID() {
        return userID;
    }
    public String getUserName(){
        return userName;
    }
    public String getUserPassword(){
        return userPassword;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }
}
