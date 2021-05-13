package DBAccess;

import Model.Users;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * The DBUsers class is used to retrieve all the users from the data base.
 * */
public class DBUsers {
    public static ObservableList<Users> getAllUsers(){
        ObservableList<Users> ulist = FXCollections.observableArrayList();

        try{

            String sql = "SELECT * from users";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int userId = rs.getInt("User_ID");
                String userName = rs.getString("User_Name");
                String userPassword = rs.getString("Password");
                Date createdDate = rs.getDate("Create_Date");
                String createdBy = rs.getString("Created_By");
                Date lastUpdated = rs.getDate("Last_Update");
                String lastUpdatedBy = rs.getString("Last_Updated_By");
                Users U = new Users(userId, userName, userPassword, createdDate, createdBy, lastUpdated, lastUpdatedBy);
                ulist.add(U);
            }
        } catch (SQLException throwables){
            throwables.printStackTrace();
        }

        return ulist;
    }
}
