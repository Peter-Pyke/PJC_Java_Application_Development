package Model;

import DBAccess.DBAppointments;
import DataBase.DBConnection;
import javafx.collections.ObservableList;

import java.sql.*;

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

    public ObservableList<Appointments> getAllCustomerAppointments(){
        ObservableList<Appointments> allAppointments = DBAppointments.getAllAppointments();
        ObservableList<Appointments> selectedCustomerAppointments = null;
        int index = 0;
        while(index < allAppointments.size()){
            Integer currentIndexID = allAppointments.get(index).getCustomerID();
            Integer customerID = this.getCustomerID();
            if(currentIndexID.compareTo(customerID) == 1) {
                selectedCustomerAppointments.add(allAppointments.get(index));
            }
            index++;

        }
        return selectedCustomerAppointments;
    }

    @Override
    public String toString() {
        return customerName;
    }
}
