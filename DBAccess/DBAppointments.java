package DBAccess;

import DataBase.DBConnection;
import Model.Appointments;
import Model.Users;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDateTime;

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
                Date start = rs.getDate("Start");
                Date end = rs.getDate("End");
                Date createdDate = rs.getDate("Create_Date");
                String createdBy = rs.getString("Created_By");
                Date lastUpdated = rs.getDate("Last_Update");
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

