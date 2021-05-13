package DBAccess;

import Model.Appointments;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
/**
 * The DBAppointments class is used to retrieve all the appointments from the data base.
 * */
public class DBAppointments {
    public static ObservableList<Appointments> getAllAppointments(){
        ObservableList<Appointments> appList = FXCollections.observableArrayList();

        try{

            String sql = "SELECT * from appointments";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int appointmentID = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                Timestamp start = rs.getTimestamp("Start");
                Timestamp end = rs.getTimestamp("End");
                Timestamp createdDate = rs.getTimestamp("Create_Date");
                String createdBy = rs.getString("Created_By");
                Timestamp lastUpdated = rs.getTimestamp("Last_Update");
                String lastUpdatedBy = rs.getString("Last_Updated_By");
                int customerID = rs.getInt("Customer_ID");
                int userID = rs.getInt("User_ID");
                int contactID = rs.getInt("Contact_ID");
                Appointments App = new Appointments(appointmentID, title, description, location, type, start, end, createdDate, createdBy, lastUpdated, lastUpdatedBy, customerID, userID, contactID);
                appList.add(App);
            }
        } catch (SQLException throwables){
            throwables.printStackTrace();
        }

        return appList;
    }
}

