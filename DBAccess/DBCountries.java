package DBAccess;


import DataBase.DBConnection;
import Model.Countries;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.sql.SQLException;

/**
 * This is the DBCountries class, it contains methods to retrieve country info from the database.
 * */
public class DBCountries {
    /**
     * This is the getAllCountries method and is used print the names of all the countries currently in the database.
     * @retrun clist. An observablelist of countries found in the database.
     * */
    public static ObservableList<Countries> getAllCountries(){
        ObservableList<Countries> clist = FXCollections.observableArrayList();

        try{

            String sql = "SELECT * from countries";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int countryId = rs.getInt("Country_ID");
                String countryName = rs.getString("Country");
                Countries C = new Countries(countryId, countryName);
                clist.add(C);
            }
        } catch (SQLException throwables){
            throwables.printStackTrace();
        }

        return clist;
    }

    public static void checkDataConversion(){
        System.out.println("CREATE DATA TEST");
        String sql = "select Create_Date from countries";
        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Timestamp ts = rs.getTimestamp("Create_Date");
                System.out.println("CD: " + ts.toLocalDateTime().toString());
            }
        }
            catch (SQLException throwables){
                throwables.printStackTrace();
            }
        }
    }

