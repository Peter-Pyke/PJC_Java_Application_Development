package Model;

import java.sql.Timestamp;
import java.time.Month;
/**
 * The Reports class is a model class. This class is similar to the appointments class but with slightly different
 * parameters. The Reports class is used to display specific information regarding appointments.
 * */
public class Reports {
    private Month month;
    private String type;
    private int count;

    private int appointmentID;
    private String title;
    private String description;
    private String location;
    private Timestamp start;
    private Timestamp end;
    private int customerID;
    private int contactID;
/**
 * This is the first constructor for the Reports class. This method is used for the type and month reporting.
 * @param type name of the type of appointment
 * @param month month the appointment takes place
 * @param count number of appointments with the same month and type
 * */
    public Reports(Month month, String type, int count){
      setMonth(month);
      setType(type);
      setCount(count);
    }
    /**
     * This is the second constructor for the Reports class. This method is used for both the location report and
     * the contacts report.
     * @param type name of the type of appointment
     * @param customerID id of customer for that appointment
     * @param contactID id of the contact for that appointment
     * @param start date and time the appointment will start
     * @param location name of the location the appointment is at
     * @param description description of the appointment
     * @param appointmentID id of the appointment
     * @param end date and time the appointment will end
     * @param title title for the appointment
     * */
    public Reports(int appointmentID,String title,String description,String location,String type,Timestamp start,Timestamp end,int customerID,int contactID){

        setType(type);
        setAppointmentID(appointmentID);
        setContactID(contactID);
        setTitle(title);
        setDescription(description);
        setLocation(location);
        setStart(start);
        setEnd(end);
        setCustomerID(customerID);
    }

    public String getType() {
        return type;
    }

    public int getCount() {
        return count;
    }

    public Month getMonth() {
        return month;
    }

    public String getLocation() {
        return location;
    }

    public String getDescription() {
        return description;
    }

    public int getAppointmentID() {
        return appointmentID;
    }

    public int getContactID() {
        return contactID;
    }

    public int getCustomerID() {
        return customerID;
    }

    public Timestamp getEnd() {
        return end;
    }

    public Timestamp getStart() {
        return start;
    }

    public String getTitle() {
        return title;
    }

    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setStart(Timestamp start) {
        this.start = start;
    }

    public void setEnd(Timestamp end) {
        this.end = end;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }


    public void setCount(int count) {
        this.count = count;
    }

    public void setMonth(Month month) {
        this.month = month;
    }

    public void setType(String type) {
        this.type = type;
    }
}

