package Model;

import java.sql.*;

/**
 * The Customer class is a model class. This class is used to hold the customer information pulled from the
 * data base.
 * */
public class Customers {
    private int customerID;
    private String customerName;
    private String customerAddress;
    private String postalCode;
    private String phoneNumber;
    private Date createDate;
    private String createdBy;
    private Timestamp lastUpdate;
    private String lastUpdatedBy;
    private int divisionID;

/**
 * This is the constructor method for the Customer class.
 * @param customerID id of customer
 * @param customerName name of customer
 * @param customerAddress address of customer
 * @param postalCode postal code
 * @param phoneNumber phone# of customer
 * @param createdBy user name of who created the customer
 * @param lastUpdatedBy user name of who updated the customer info
 * @param divisionID id of division/state the customer is from
 * */
    public Customers(Integer customerID, String customerName, String customerAddress, String postalCode, String phoneNumber, String createdBy, String lastUpdatedBy, int divisionID){

        this.customerID = customerID;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
        this.createdBy = createdBy;
        this.lastUpdatedBy = lastUpdatedBy;
        this.divisionID = divisionID;

    }
        public int getCustomerID(){return customerID;}
        public String getCustomerName(){return customerName;}
        public String getCustomerAddress(){return customerAddress;}
        public String getPostalCode(){return postalCode;}
        public String getPhoneNumber(){return phoneNumber;}
        public int getDivisionID(){return divisionID;}

/**
 * This is an override method for the toString method. This lets the toString method return what is specified
 * below the customerName.
 * */
    @Override
    public String toString() {
        return customerName;
    }
}

