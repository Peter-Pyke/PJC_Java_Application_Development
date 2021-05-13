package Model;
/**
 * The Contacts class is a model class. This method is used to hold the customer information taken from the
 * data base.
 * */
public class Contacts {
    private int contactID;
    private String contactName;
    private String email;
/**
 * This is the Constructor method for the Contacts class.
 * @param contactID
 * @param contactName
 * @param email
 * */
    public Contacts(int contactID, String contactName, String email){
        this.contactID = contactID;
        this.contactName = contactName;
        this.email = email;
    }
/**
 * Getter methods for the Contact class.
 * */
    public int getContactID() {
        return contactID;
    }

    public String getContactName() {
        return contactName;
    }

    public String getEmail() {
        return email;
    }

    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public void setEmail(String email) {
        this.email = email;
    }
/**
 * This is an override method for the toString class. This method lets the toString method return the additional
 * information specified below.
 * */
    @Override
    public String toString() {
        return contactName + " (Contact_ID: "+contactID+")";
    }

}
