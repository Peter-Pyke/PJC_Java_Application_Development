package DBAccess;

import Model.Division;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * The DBDivisions class is used to retrieve all the divisions from the data base.
 * */
public class DBDivisions {
    public static ObservableList<Division> getAllDivision(){
        ObservableList<Division> dlist = FXCollections.observableArrayList();

        try{

            String sql = "SELECT * from first_level_divisions";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                String divisionName = rs.getString("Division");
                int divisionId = rs.getInt("Division_ID");
                Date createDate = rs.getDate("Create_Date");
                String createdBy = rs.getString("Created_By");
                Date lastUpdate = rs.getDate("Last_Update");
                String lastUpdatedBy = rs.getString("Last_Updated_By");
                int countryID = rs.getInt("COUNTRY_ID");

                Division D = new Division(divisionId, divisionName, createDate, createdBy, lastUpdate,lastUpdatedBy, countryID);
                dlist.add(D);
            }
        } catch (SQLException throwables){
            throwables.printStackTrace();
        }

        return dlist;
    }

}
