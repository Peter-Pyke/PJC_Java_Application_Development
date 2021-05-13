package DBAccess;

import Model.Contacts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * The DBContacts class is used to retrieve all the contacts from the data base.
 * */
public class DBContacts {
    public static ObservableList<Contacts> getAllContacts(){
        ObservableList<Contacts> clist = FXCollections.observableArrayList();

        try{

            String sql = "SELECT * from contacts";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int contactId = rs.getInt("Contact_ID");
                String contactName = rs.getString("Contact_Name");
                String email = rs.getString("Email");

                Contacts C = new Contacts(contactId, contactName, email);
                clist.add(C);
            }
        } catch (SQLException throwables){
            throwables.printStackTrace();
        }

        return clist;
    }
}

