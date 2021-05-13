package Model;

import java.sql.Date;
/**
 * The Users class is a model class. This class is used to hold the user information pulled from the data base.
 * */
public class Users {
    private int userID;
    private String userName;
    private String userPassword;
    private Date dateCreated;
    private String createdBy;
    private Date lastUpdated;
    private String lastUpdatedBy;

/**
 * This is the constructor method for the Users class.
 * @param userID id of the user
 * @param userName name of the user
 * @param userPassword user password
 * @param dateCreated date the user was created
 * @param createdBy user that created the user
 * @param lastUpdated date the user was last updated
 * @param lastUpdatedBy user that last updated the user
 * */
    public Users (int userID, String userName, String userPassword, Date dateCreated, String createdBy, Date lastUpdated, String lastUpdatedBy){
        this.userID = userID;
        this.userName = userName;
        this.userPassword = userPassword;
        this.dateCreated = dateCreated;
        this.createdBy = createdBy;
        this.lastUpdated = lastUpdated;
        this.lastUpdatedBy = lastUpdatedBy;
    }
/**
 * Getter methods.
 * */
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
