package DBAccess;

import DataBase.DBConnection;
import Model.Customers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBCustomers {
    public static ObservableList<Customers> getAllCustomers(){
        ObservableList<Customers> clist = FXCollections.observableArrayList();
        try{
            String sql = "SELECT * from customers";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                int customerID = rs.getInt("Customer_ID");
                String customerName = rs.getString("Customer_Name");
                String customerAddress = rs.getString("Address");
                String postalCode = rs.getString("Postal_Code");
                String phoneNumber = rs.getString("Phone");
                int customerDivisionID = rs.getInt("Division_ID");

                Customers C = new Customers(customerID, customerName, customerAddress, postalCode, phoneNumber, customerDivisionID);
                clist.add(C);
            }
        } catch (SQLException throwables){
            throwables.printStackTrace();
        }
        return clist;
    }

}
