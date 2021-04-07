package Model;

import java.sql.Date;
import java.sql.Timestamp;

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


    public Customers(int customerID, String customerName, String customerAddress, String postalCode, String phoneNumber){
        this.customerID = customerID;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;

    }
        public int getCustomerID(){return customerID;}
        public String getCustomerName(){return customerName;}
        public String getCustomerAddress(){return customerAddress;}
        public String getPostalCode(){return postalCode;}
        public String getPhoneNumber(){return phoneNumber;}

    @Override
    public String toString() {
        return customerName;
    }
}
