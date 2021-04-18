package Controllers;
import DBAccess.DBCountries;
import DBAccess.DBCustomers;
import DBAccess.DBDivisions;
import DataBase.DBConnection;
import DataBase.DBQuery;
import Model.Countries;
import Model.Customers;
import Model.Division;
import com.mysql.cj.protocol.Resultset;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

import javax.xml.transform.Result;
import java.io.PrintStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

/**
 * This is my MainSceneController class and will be used to give the main screen after login its functionality.
 * */
public class MainSceneController implements Initializable {

    @FXML
    private ComboBox<Customers> customerComboBox;

    @FXML
    private ComboBox<Countries> countryCBox;

    @FXML
    private ComboBox<Division> stateCBox;
    @FXML
    private TextField customerNameTxt;

    @FXML
    private TextField customerAddressTxt;

    @FXML
    private TextField customerPostalCodeTxt;

    @FXML
    private TextField customerPhoneTxt;

    @FXML
    private TextField customerIDTxt;

    @FXML
    private GridPane calendarGrid;

    @FXML
    private TableView<Customers> allCustomerTableView;

    @FXML
    private TableColumn<Customers, Integer> customerIDCol;

    @FXML
    private TableColumn<Customers, String> customerNameCol;

    @FXML
    private TableColumn<Customers, String> customerAddressCol;

    @FXML
    private TableColumn<Customers, String> customerPostalCodeCol;

    @FXML
    private TableColumn<Customers, String> customerPhoneCol;

    @FXML
    private TableColumn<Countries, String> customerCountryCol;

    @FXML
    private TableColumn<?, ?> customerStateCol;

    @FXML
    void onActionAddcustomer(ActionEvent event) {

    }

    @FXML
    void onActionCBoxCountry(ActionEvent event) {

    }

    @FXML
    void onActionLoadCustomerBtn(ActionEvent event) {

    }

    @FXML
    void onActionCBoxMonth(ActionEvent event) {

    }

    @FXML
    void onActionCBoxState(ActionEvent event) {
//Add code to filter out division based on what country is selected.
    }

    @FXML
    void onActionDeleteCustomer(ActionEvent event) {

    }

    @FXML
    void onActionUpdateCustomer(ActionEvent event) {

    }

    //Add code to filter out division based on what country is selected.
    public void JDBC1method() {
        // example from first webinar of JDBC interfaces
        try {
            Connection conn = DBConnection.getConnection(); // Create Statement Object
            DBQuery.setSatement(conn);
            Statement statement = DBQuery.getStatement(); //Get Statement reference

            // Raw SQL insert statement
            String insertStatement = "INSERT INTO countries(Country, Create_Date, Created_By, Last_Updated_By) VALUES('China', '2020-02-22 00:00:00', 'admin', 'admin') ";

            //Execute statement
            statement.execute(insertStatement);

            if (statement.getUpdateCount() > 0) {
                System.out.println(statement.getUpdateCount() + " row(s) affected!");
            } else {
                System.out.println("No Change");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
        public void JDBC2method(){
            try {
                Connection conn = DBConnection.getConnection(); // Create Statement Object
                DBQuery.setSatement(conn);
                Statement statement = DBQuery.getStatement(); //Get Statement reference
                String selectStatement = "SELECT * FROM countries"; // SELECT statement
                statement.execute(selectStatement); //Execute statement
                ResultSet rs = statement.getResultSet(); // Get ResultSet

                //Forward Scroll ResultSet
                while(rs.next()){
                    String country = rs.getString("Country");

                    System.out.println(country);
                }
            }
            catch(SQLException e){
                e.printStackTrace();
            }
        }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        JDBC2method();
        ObservableList<Countries> countryList = DBCountries.getAllCountries();
        ObservableList<Division> divisionList = DBDivisions.getAllDivision();
        ObservableList<Customers> customerList = DBCustomers.getAllCustomers();


        allCustomerTableView.setItems(customerList);
        customerIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        customerAddressCol.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
        customerNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        customerPhoneCol.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        customerPostalCodeCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        customerStateCol.setCellValueFactory(new PropertyValueFactory<>("divisionID"));
        customerComboBox.setItems(customerList);
        countryCBox.setItems(countryList);
        stateCBox.setItems(divisionList);

    }

}