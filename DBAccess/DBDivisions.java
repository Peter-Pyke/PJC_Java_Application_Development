package DBAccess;

import DataBase.DBConnection;
import Model.Division;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
                Division D = new Division(divisionId, divisionName);
                dlist.add(D);
            }
        } catch (SQLException throwables){
            throwables.printStackTrace();
        }

        return dlist;
    }

}
