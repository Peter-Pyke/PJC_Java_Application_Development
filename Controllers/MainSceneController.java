package Controllers;
import DBAccess.DBAppointments;
import DBAccess.DBCountries;
import DBAccess.DBCustomers;
import DBAccess.DBDivisions;
import DataBase.DBConnection;
import DataBase.DBQuery;
import Model.Appointments;
import Model.Countries;
import Model.Customers;
import Model.Division;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

/**
 * This is my MainSceneController class and will be used to give the main screen after login its functionality.
 * */
public class MainSceneController<size> implements Initializable {
    Stage stage;
    Parent scene;
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
    private TableColumn<Customers, Integer> customerDivisionCol;

    private static String userName = null; // String object to hold the username of the person who logged in.
    private static String userPassword = null; // String object to hold the password of the person who logged in.

    /**
     * This is the passLoginInfo method, it is used to get the username and password that was used to login.
     * The username and password is than stored in the private static string objects above until needed.
     * @param userName1 username of person logged in.
     * @param userPassword1 password of person logged in.
     * */
    public void passLoginInfo(String userName1, String userPassword1){
        userName = userName1;
        userPassword = userPassword1;
    }
    /**
     * This is my updateTableView method. This method is used to place all the customers and their information
     * into the table view.
     * */
    public void updateTableView(){
        ObservableList<Customers> customerList = DBCustomers.getAllCustomers();

        //The follow is to set up the Table View with all the customers information.
        allCustomerTableView.setItems(customerList);
        customerIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        customerAddressCol.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
        customerNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        customerPhoneCol.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        customerPostalCodeCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        customerDivisionCol.setCellValueFactory(new PropertyValueFactory<>("divisionID"));
    }
    /**
     * This is my addCustomer method. This method collects the information in the text fields and uses it to
     * create a new customer in our database.
     * */
    public void addCustomer(){
        try {
                String customerName = customerNameTxt.getText();
                String customerAddress = customerAddressTxt.getText();
                String postalCode = customerPostalCodeTxt.getText();
                String phoneNumber = customerPhoneTxt.getText();
                String createdBy = userName;
                String lastUpdateBy = userPassword;
                Division selectedDivision = stateCBox.getSelectionModel().getSelectedItem();
                int customerDivisionID = selectedDivision.getDivisionID();

                Connection conn = DBConnection.getConnection(); // Create Connection Object
                DBQuery.setStatement(conn);
                Statement statement = DBQuery.getStatement(); //Get Statement reference

                // Raw SQL insert statement
                String insertStatement = "INSERT INTO customers(Customer_Name, Address, Postal_Code, Phone, Created_By, Last_Updated_By, Division_ID)"
                        + "VALUES('" + customerName + "', '" + customerAddress + "', '" + postalCode + "', '" + phoneNumber + "', '" + createdBy + "', '" +
                        lastUpdateBy + "', '" + customerDivisionID + "') ";

                //Execute statement
                statement.execute(insertStatement);

                updateTableView();

                //This if else statement prints a line letting you know if the code above worked.
                if (statement.getUpdateCount() > 0) {
                    System.out.println(statement.getUpdateCount() + " row(s) affected!");
                } else {
                    System.out.println("No Change");
                }
        } catch (SQLException e) {
            e.printStackTrace(); // prints out error messages
        }
    }
    /**
     * This is the onActionAddCustomer method and it decides what will happen when the add button is clicked in the main menu.
     * */
    @FXML
    void onActionAddCustomer(ActionEvent event) {
        if(!(customerComboBox.getSelectionModel().isEmpty())){
            Alert error = new Alert(Alert.AlertType.WARNING);
            error.setTitle("Warning Dialog");
            error.setContentText("CustomerID already in use. Please use UpDate or clear form before adding.");
            error.showAndWait();
        }
        else {
            addCustomer(); //addCustomer method is called to insert a new customer into the database.
        }
    }
    /**
     * This is my filterDivision method. This method sets the Division combo box up with the Divisions that match
     * the country currently selected in the country combo box.
     * */
    public void filterDivision(){
        ObservableList<Division> allDivision = DBDivisions.getAllDivision();
        FilteredList<Division> filteredDivisions = new FilteredList<Division>(allDivision, i -> i.getCountryID() == countryCBox.getSelectionModel().getSelectedItem().getId());
        stateCBox.setItems(filteredDivisions);
    }
    @FXML
    void onActionCountryCBox(ActionEvent event) {
        filterDivision();
    }
    @FXML
    void onActionCustomerCBox(ActionEvent event) {
        //Write code to auto populate text files with the information from the customer currently selected in the combo box.
            setUpDateCustomer();
    }
    public void setUpDateCustomer(){
        try {
            Customers selectedCustomer = customerComboBox.getSelectionModel().getSelectedItem();
            customerIDTxt.setText(String.valueOf(selectedCustomer.getCustomerID()));
            customerNameTxt.setText(selectedCustomer.getCustomerName());
            customerAddressTxt.setText(selectedCustomer.getCustomerAddress());
            customerPhoneTxt.setText(selectedCustomer.getPhoneNumber());
            customerPostalCodeTxt.setText(selectedCustomer.getPostalCode());
            ObservableList<Division> allDivisions = DBDivisions.getAllDivision();
            int divisionIndex = (customerComboBox.getSelectionModel().getSelectedItem().getDivisionID() - 1);
            Division divisionOfSelectedCustomer = allDivisions.get(divisionIndex);
            stateCBox.setValue(divisionOfSelectedCustomer);
            int countryIndex = (divisionOfSelectedCustomer.getCountryID() - 1);
            ObservableList<Countries> allCountries = DBCountries.getAllCountries();
            Countries countryOfSelectedCustomer = (allCountries.get(countryIndex));
            countryCBox.setValue(countryOfSelectedCustomer);
        }
        catch (NullPointerException e){
            //Do nothing
        }
    }

    @FXML
    void onActionDeleteCustomer(ActionEvent event) {
        try {
            Connection conn = DBConnection.getConnection(); // Create Connection Object
            DBQuery.setStatement(conn);
            Statement statement = DBQuery.getStatement(); //Get Statement reference

            Customers selectedCustomer = allCustomerTableView.getSelectionModel().getSelectedItem(); //Get customer currently selected in table.
            int selectedCustomerID = selectedCustomer.getCustomerID(); //Get the name of the customer currently selected in the table.

            // Raw SQL delete statement
            String deleteStatement = "DELETE FROM customers WHERE Customer_ID = '" + selectedCustomerID + "'";

            //Execute statement
            statement.execute(deleteStatement);

            String selectedCustomerName = selectedCustomer.getCustomerName();
            Alert error = new Alert(Alert.AlertType.WARNING);
            error.setTitle("Warning Dialog");
            error.setContentText(selectedCustomerName + " has been deleted!");
            error.showAndWait();
            updateTableView();
            customerComboBox.setItems(DBCustomers.getAllCustomers());
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    @FXML
    void onActionUpdateCustomer(ActionEvent event) {

    }
    // Example from first webinar of JDBC interfaces placed into a method. This will not be part of the project.
    public void JDBC1method() {

        try {
            Connection conn = DBConnection.getConnection(); // Create Statement Object
            DBQuery.setStatement(conn);
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
    //Example from the second JDBC webinar placed in a method. This will not be part of the project.
        public void JDBC2method(){
            try {
                Connection conn = DBConnection.getConnection(); // Create Statement Object
                DBQuery.setStatement(conn);
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
    @FXML
    void onActionAppointmentsBtn(ActionEvent event) throws IOException {

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/Scenes/Appointment_Scene.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    @FXML
    void onActionClearFormBtn(ActionEvent event) {

        try {
            customerIDTxt.setText("Auto Generated");
            customerNameTxt.setText("");
            customerAddressTxt.setText("");
            customerPhoneTxt.setText("");
            customerPostalCodeTxt.setText("");
            customerComboBox.setItems(DBCustomers.getAllCustomers());
            countryCBox.setValue(DBCountries.getAllCountries().get(0));
            stateCBox.setValue(DBDivisions.getAllDivision().get(0));
        }
        catch (NullPointerException e){
            e.printStackTrace();
        }

    }
/**
 * This is the initialize method and it controls what happens when the Main Scene is loaded.
 * @param resourceBundle
 * @param url
 * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ObservableList<Customers> myCustomers = DBCustomers.getAllCustomers();
        customerIDTxt.setText("Auto Generated");

        JDBC2method(); //Example method to print the countries.

        /* The three lines below retrieve the Counties, Divisions and Customers from my data
        base and hold them in three lists.
        */
        ObservableList<Countries> countryList = DBCountries.getAllCountries();
        ObservableList<Division> divisionList = DBDivisions.getAllDivision();
        ObservableList<Customers> customerList = DBCustomers.getAllCustomers();

//The follow is to set up the Table View with all the customers information.
        allCustomerTableView.setItems(customerList);
        customerIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        customerAddressCol.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
        customerNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        customerPhoneCol.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        customerPostalCodeCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        customerDivisionCol.setCellValueFactory(new PropertyValueFactory<>("divisionID"));
        customerComboBox.setItems(customerList);
        countryCBox.setItems(countryList);


    }

}