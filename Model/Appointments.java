package Model;

import java.sql.Timestamp;

/**
 * The Appointments class is used as a model. When the appointment information is pulled from the data base it
 * is held inside of appointment class objects.
 * */
public class Appointments {
    private int appointmentID;
    private String title;
    private String description;
    private String location;
    private String type;
    private Timestamp start;
    private Timestamp end;
    private Timestamp createDate;
    private String createdBy;
    private Timestamp lastUpdated;
    private String lastUpdatedBy;
    private int customerID;
    private int userID;
    private int contactID;

/**
 * This is the Appointments constructor.
 * @param appointmentID ID for appointment
 * @param title String containing the title for the appointment
 * @param description String containing description of appointment
 * @param location String containing name of the location for the appointment
 * @param type String with name of type of appointment
 * @param start TimeStamp with time the appointment starts
 * @param end TimeStamp with the time the appointment ends
 * @param createDate Date of when the appointment was created
 * @param createdBy String with name of user that created the appointment
 * @param lastUpdated Date of when the appointment was last updated
 * @param lastUpdatedBy String with the name of the user that last updated the appointment
 * @param customerID int of customer id associated with appointment
 * @param contactID int of contact id associated with the appointment
 * @param userID int of the user id associated with the appointment
 * */
    public Appointments(int appointmentID,String title,String description,String location,String type,Timestamp start,Timestamp end,Timestamp createDate,String createdBy,Timestamp lastUpdated,String lastUpdatedBy,int customerID,int userID,int contactID){
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
/**
 * Getter methods to return the parameters of the Appointment class.
 * */
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

    public Timestamp getEnd() {
        return end;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public Timestamp getLastUpdated() {
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
