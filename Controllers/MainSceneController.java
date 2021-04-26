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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This is my MainSceneController class and will be used to give the main screen after login its functionality.
 * */
public class MainSceneController<size> implements Initializable {
    Stage stage; //Declaring stage object to be used later on.
    Parent scene; //Declaring scene object to be used later on.
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
                if(customerName.isEmpty()){
                    Alert error = new Alert(Alert.AlertType.WARNING);
                    error.setTitle("Warning Dialog");
                    error.setContentText("Please Enter A Valid Name!");
                    error.showAndWait();
                }
                else if(customerAddress.isEmpty()){
                    Alert error = new Alert(Alert.AlertType.WARNING);
                    error.setTitle("Warning Dialog");
                    error.setContentText("Please Enter A Valid Address!");
                    error.showAndWait();
                }
                else if(postalCode.isEmpty()){
                    Alert error = new Alert(Alert.AlertType.WARNING);
                    error.setTitle("Warning Dialog");
                    error.setContentText("Please Enter A Valid Postal Code!");
                    error.showAndWait();
                }
                else if(!phoneNumber.matches("\\d{3}-\\d{3}-\\d{4}")){
                    Alert error = new Alert(Alert.AlertType.WARNING);
                    error.setTitle("Warning Dialog");
                    error.setContentText("Please Enter A Valid phone#!");
                    error.showAndWait();
                }
                else if(stateCBox.getSelectionModel().isEmpty()){
                    Alert error = new Alert(Alert.AlertType.WARNING);
                    error.setTitle("Warning Dialog");
                    error.setContentText("Please Select A Division!");
                    error.showAndWait();
                }
                else if(countryCBox.getSelectionModel().isEmpty()){
                    Alert error = new Alert(Alert.AlertType.WARNING);
                    error.setTitle("Warning Dialog");
                    error.setContentText("Please Select A Country!");
                    error.showAndWait();
                }
                else {
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
                }
        } catch (SQLException e) {
            e.printStackTrace(); // prints out error messages
        }
    }
    public void upDateCustomer(){
        try {
            String customerName = customerNameTxt.getText();
            String customerAddress = customerAddressTxt.getText();
            String postalCode = customerPostalCodeTxt.getText();
            String phoneNumber = customerPhoneTxt.getText();
            String createdBy = userName;
            String lastUpdateBy = userPassword;
            Division selectedDivision = stateCBox.getSelectionModel().getSelectedItem();
            if (customerName.isEmpty()) {
                Alert error = new Alert(Alert.AlertType.WARNING);
                error.setTitle("Warning Dialog");
                error.setContentText("Please Enter A Valid Name!");
                error.showAndWait();
            } else if (customerAddress.isEmpty()) {
                Alert error = new Alert(Alert.AlertType.WARNING);
                error.setTitle("Warning Dialog");
                error.setContentText("Please Enter A Valid Address!");
                error.showAndWait();
            } else if (postalCode.isEmpty()) {
                Alert error = new Alert(Alert.AlertType.WARNING);
                error.setTitle("Warning Dialog");
                error.setContentText("Please Enter A Valid Postal Code!");
                error.showAndWait();
            } else if (!phoneNumber.matches("\\d{3}-\\d{3}-\\d{4}")) {
                Alert error = new Alert(Alert.AlertType.WARNING);
                error.setTitle("Warning Dialog");
                error.setContentText("Please Enter A Valid phone#!");
                error.showAndWait();
            } else if (stateCBox.getSelectionModel().isEmpty()) {
                Alert error = new Alert(Alert.AlertType.WARNING);
                error.setTitle("Warning Dialog");
                error.setContentText("Please Select A Division!");
                error.showAndWait();
            } else if (countryCBox.getSelectionModel().isEmpty()) {
                Alert error = new Alert(Alert.AlertType.WARNING);
                error.setTitle("Warning Dialog");
                error.setContentText("Please Select A Country!");
                error.showAndWait();
            } else if(customerComboBox.getSelectionModel().isEmpty()){
                Alert error = new Alert(Alert.AlertType.WARNING);
                error.setTitle("Warning Dialog");
                error.setContentText("Please Select A Customer!");
                error.showAndWait();
            }
            else {
                int customerDivisionID = selectedDivision.getDivisionID();
                int customerID = customerComboBox.getSelectionModel().getSelectedItem().getCustomerID();

                Connection conn = DBConnection.getConnection(); // Create Connection Object
                DBQuery.setStatement(conn);
                Statement statement = DBQuery.getStatement(); //Get Statement reference

                // Raw SQL update statement
                String updateStatement = "UPDATE customers SET Customer_Name = '" + customerName
                        + "', Address = '" + customerAddress + "', Postal_Code = '" + postalCode + "', "
                        + "Phone = '" + phoneNumber + "', Created_By = '" + createdBy + "', Last_Updated_By = '" + lastUpdateBy
                        + "', Division_ID = '" + customerDivisionID + "' WHERE Customer_ID = '" + customerID + "';";

                //Execute statement
                statement.execute(updateStatement);

                updateTableView();

                //This if else statement prints a line letting you know if the code above worked.
                if (statement.getUpdateCount() > 0) {
                    System.out.println(statement.getUpdateCount() + " row(s) affected!");
                } else {
                    System.out.println("No Change");
                }
            }
            } catch(SQLException e){
                e.printStackTrace(); // prints out error messages
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
    /**
     * Set UpDate Customer method. This method takes the selected customer's information and populates
     * the on screen form with that information.
     * */
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
    /**
     * Delete Customer method. This method removes the customer currently selected in the Table View.
     * */
    public void deleteCustomer(){
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
    /**
     * Clear Form method. This method resets all the text fields and combo boxes on the screen to a default.
     * */
    public void clearForm(){
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
     * On Action Delete Customer method. Calls the deleteCustomer method which removes the customer
     * currently selected in the table view from the database.
     * */
    @FXML
    void onActionDeleteCustomer(ActionEvent event) {
       deleteCustomer();
    }
    /**
     * On Action Country Combo Box method. Calls the filter Division method which filters the Division
     * combo box with only the divisions in the selected country.
     * */
    @FXML
    void onActionCountryCBox(ActionEvent event) {
        filterDivision();
    }
    /**
     * On Action Customer Combo Box method. Calls set UpDate Customer method which auto populates
     * the on screen form with the selected customer's information.
     * */
    @FXML
    void onActionCustomerCBox(ActionEvent event) {
        setUpDateCustomer();
    }
    /**
     * On Action Add Customer method. Calls addCustomer method if no customer is currently
     * selected in the combo box. Otherwise it provides an error message.
     * */
    @FXML
    void onActionAddCustomer(ActionEvent event) {
        if(!(customerComboBox.getSelectionModel().isEmpty())){
            Alert error = new Alert(Alert.AlertType.WARNING);
            error.setTitle("Warning Dialog");
            error.setContentText("Please use UpDate button or clear form before adding.");
            error.showAndWait();
        }
        else {
            addCustomer(); //addCustomer method is called to insert a new customer into the database.
        }
    }
    /**
     * On Action Update Customer method. Calls the Update Customer method which replaces
     * the selected customer information with the information provided on the screen.
     * */
    @FXML
    void onActionUpdateCustomer(ActionEvent event) {
    upDateCustomer();
    }
    /**
     * On Action Appointments method. This method changes the scene to the appointment scene.
     * */
    @FXML
    void onActionAppointmentsBtn(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/Scenes/Appointment_Scene.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    /**
     * On Action Clear Form method. This method calls the clear form method to reset the data
     * on the form.
     * */
    @FXML
    void onActionClearFormBtn(ActionEvent event) {
    clearForm();
    }
    /**
    * This is the initialize method and it controls what happens when the Main Scene is loaded.
    * @param resourceBundle
    * @param url
    * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        /* The two lines below retrieve the Countries and Customers from my data
        base and hold them in two lists.
        */
        ObservableList<Countries> countryList = DBCountries.getAllCountries();
        ObservableList<Customers> customerList = DBCustomers.getAllCustomers();

        customerIDTxt.setText("Auto Generated"); // Sets the Customer ID text field.
        customerComboBox.setItems(customerList); // Sets up Customer Combo box.
        countryCBox.setItems(countryList);       // Sets up Country Combo box.

        //The follow is to set up the Table View with all the customers information.
        allCustomerTableView.setItems(customerList);
        customerIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        customerAddressCol.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
        customerNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        customerPhoneCol.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        customerPostalCodeCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        customerDivisionCol.setCellValueFactory(new PropertyValueFactory<>("divisionID"));
    }

}