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
public class MainSceneController implements Initializable {
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
                    +"VALUES('"+ customerName +"', '"+ customerAddress+"', '"+ postalCode +"', '"+ phoneNumber+"', '"+ createdBy +"', '"+
                    lastUpdateBy +"', '"+ customerDivisionID +"') ";

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
        addCustomer(); //addCustomer method is called to insert a new customer into the database.
    }


    @FXML
    void onActionCBoxState(ActionEvent event) {
//Add code to filter out division based on what country is selected.
    }

    @FXML
    void onActionDeleteCustomer(ActionEvent event) {
        try {
            Connection conn = DBConnection.getConnection(); // Create Connection Object
            DBQuery.setStatement(conn);
            Statement statement = DBQuery.getStatement(); //Get Statement reference

            Customers selectedCustomer = allCustomerTableView.getSelectionModel().getSelectedItem(); //Get customer currently selected in table.
            String selectedCustomerName = selectedCustomer.getCustomerName(); //Get the name of the customer currently selected in the table.

            // Raw SQL delete statement
            String deleteStatement = "DELETE FROM customers WHERE Customer_Name = '" + selectedCustomerName + "'";

            //Execute statement
            statement.execute(deleteStatement);

            Alert error = new Alert(Alert.AlertType.WARNING);
            error.setTitle("Warning Dialog");
            error.setContentText(selectedCustomerName + " has been deleted!");
            error.showAndWait();
            updateTableView();
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
    void onActionTestBtn(ActionEvent event) {
        try {
            ObservableList<Appointments> allAppointments = DBAppointments.getAllAppointments();
            FilteredList<Appointments> selectedCustomerAppointments = new FilteredList<>(allAppointments, i-> i.getCustomerID() == allCustomerTableView.getSelectionModel().getSelectedItem().getCustomerID());
            System.out.println(selectedCustomerAppointments);
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
        int lastCustomerIndex = myCustomers.size() - 1;
        Customers lastCustomer = myCustomers.get(lastCustomerIndex);
        int nextCustomerID = lastCustomer.getCustomerID() +1;

        customerIDTxt.setText(String.valueOf(nextCustomerID));

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
        stateCBox.setItems(divisionList);

    }

}