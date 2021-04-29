package Model;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Appointments {
    private int appointmentID;
    private String title;
    private String description;
    private String location;
    private String type;
    private Timestamp start;
    private Date end;
    private Date createDate;
    private String createdBy;
    private Date lastUpdated;
    private String lastUpdatedBy;
    private int customerID;
    private int userID;
    private int contactID;

    public Appointments(int appointmentID,String title,String description,String location,String type,Timestamp start,Date end,Date createDate,String createdBy,Date lastUpdated,String lastUpdatedBy,int customerID,int userID,int contactID){
        this.appointmentID = appointmentID;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdated = lastUpdated;
        this.lastUpdatedBy = lastUpdatedBy;
        this.customerID = customerID;
        this.userID = userID;
        this.contactID = contactID;

    }

    public int getAppointmentID() {
        return appointmentID;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

    public String getType() {
        return type;
    }

    public Timestamp getStart() {
        return start;
    }

    public Date getEnd() {
        return end;
    }

    public Date getCreateDate() {
        return createDate;
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

    public int getCustomerID() {
        return customerID;
    }

    public int getUserID() {
        return userID;
    }

    public int getContactID() {
        return contactID;
    }



}
