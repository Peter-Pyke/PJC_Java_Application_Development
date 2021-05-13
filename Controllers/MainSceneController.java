package Controllers;
import DBAccess.*;
import Model.*;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.TimeZone;

/**
 * This is my MainSceneController class and will be used to give the main scene its functionality.
 * */
public class MainSceneController<size> implements Initializable {
    Stage stage; //Declaring stage object to be used later on.

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
    private static int userID; //int to hold userID of the user who logged in.
   //----------------------------------PASS METHODS-----------------------------------------------------------
    /**
     * This is the passLoginInfo method, it is used to get the username and password that was used to login.
     * The username and password is than stored in the private static string objects above until needed.
     * @param userName_App username of person logged in.
     * @param userPassword_App password of person logged in.
     * */
    public void passLoginInfo(String userName_App, String userPassword_App){
        userName = userName_App;
        userPassword = userPassword_App;
    }
    /**
     * This is the appointmentCheck method, it is called in the login Controller class and gives the user
     * a warning message if they have an appointment within 15 minutes of login.
     * */
    public void appointmentCheck(){

        ObservableList<Users> allUsers = DBUsers.getAllUsers();
        ZoneId myZoneId = ZoneId.of(TimeZone.getDefault().getID());
        ZoneId UTC = ZoneId.of("UTC");

        for(int i = 0; i < allUsers.size(); i++){
            if((allUsers.get(i).getUserName().equals(userName)) && (allUsers.get(i).getUserPassword().equals(userPassword))){
                userID = allUsers.get(i).getUserID();
            }
        }
        ObservableList<Appointments> allAppointments = DBAppointments.getAllAppointments();
        FilteredList<Appointments> userAppointments = new FilteredList<>(allAppointments, i -> i.getUserID() == userID );
        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime   = LocalTime.now();

        for(int j = 0; j < userAppointments.size(); j++){
            Appointments appointment = userAppointments.get(j);
            LocalDateTime dataBaseTime = appointment.getStart().toLocalDateTime();
            ZonedDateTime dataBaseStartTimeUTC = ZonedDateTime.of(dataBaseTime,UTC);
            LocalDate dateToCompare = dataBaseStartTimeUTC.toLocalDate();// use this local date to compare
            ZonedDateTime displayStartTimeZoned = dataBaseStartTimeUTC.withZoneSameInstant(myZoneId);
            LocalTime timeToCompare = displayStartTimeZoned.toLocalTime(); // use this time to compare

            if(currentDate.equals(dateToCompare) && (currentTime.isBefore(timeToCompare) && currentTime.isAfter(timeToCompare.minusMinutes(15)))){
                Alert error = new Alert(Alert.AlertType.WARNING);
                error.setTitle("15 Minute Warning");
                error.setContentText("AppointmentID: "+appointment.getAppointmentID()+" is at "+ timeToCompare.toString());
                error.showAndWait();
            }
        }
    }
    //--------------------------------UTILITY METHODS---------------------------------------------------------
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
                    error.setContentText("Please enter A valid phone# in the following format: 123-456-7890.");
                    error.getDialogPane().setPrefWidth(600);
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
    /**
     * The upDateCustomer method is called when the update button is clicked in the MainScene(Customer Creation Scene).
     * This method updates the selected customer information inside the data base.
     * */
    public void upDateCustomer(){
        try {
            String customerName = customerNameTxt.getText();
            String customerAddress = customerAddressTxt.getText();
            String postalCode = customerPostalCodeTxt.getText();
            String phoneNumber = customerPhoneTxt.getText();
            String lastUpdateBy = userName;
            Division selectedDivision = stateCBox.getValue();
            Boolean countrySelection = (countryCBox.getValue() == null);
            Boolean stateSelection = (stateCBox.getValue() == null);
            System.out.println(stateSelection);

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
                error.setContentText("Please enter A valid phone# in the following format: 123-456-7890");
                error.getDialogPane().setPrefWidth(600);
                error.showAndWait();
            } else if(stateSelection){
                Alert error = new Alert(Alert.AlertType.WARNING);
                error.setTitle("Warning Dialog");
                error.setContentText("Please Select A Division!");
                error.showAndWait();
            } else if (countrySelection) {
                Alert error = new Alert(Alert.AlertType.WARNING);
                error.setTitle("Warning Dialog");
                error.setContentText("Please Select A Country!");
                error.showAndWait();
            } else if(allCustomerTableView.getSelectionModel().isEmpty()){
                Alert error = new Alert(Alert.AlertType.WARNING);
                error.setTitle("Warning Dialog");
                error.setContentText("Please Select A Customer!");
                error.showAndWait();
            }
            else {
                int customerDivisionID = selectedDivision.getDivisionID();
                int customerID = allCustomerTableView.getSelectionModel().getSelectedItem().getCustomerID();

                Connection conn = DBConnection.getConnection(); // Create Connection Object
                DBQuery.setStatement(conn);
                Statement statement = DBQuery.getStatement(); //Get Statement reference

                // Raw SQL update statement
                String updateStatement = "UPDATE customers SET Customer_Name = '" + customerName
                        + "', Address = '"+ customerAddress + "', Postal_Code = '" + postalCode + "', "
                        + "Phone = '" + phoneNumber + "', Last_Updated_By = '" + lastUpdateBy
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
            } catch(NullPointerException e){
            e.printStackTrace();
            }
            catch(SQLException i){
            i.printStackTrace();
            }
    }
    /**
     * The filterDivision method sets the Division combo box up with the Divisions that match
     * the country currently selected in the country combo box.
     * */
    public void filterDivision(){
        try {
            ObservableList<Division> allDivision = DBDivisions.getAllDivision();
            FilteredList<Division> filteredDivisions = new FilteredList<Division>(allDivision, i -> i.getCountryID() == countryCBox.getSelectionModel().getSelectedItem().getId());
            stateCBox.setItems(filteredDivisions);
            stateCBox.setValue(null);
        }
        catch(NullPointerException e){
            //Do nothing. The clear form button will cause a NullPointerException.
        }
    }
    /**
     * Set UpDate Customer method. This method takes the selected customer's information and populates
     * the on screen form with that information.
     * */
    public void setUpDateCustomer(){
        try {
            Customers selectedCustomer = allCustomerTableView.getSelectionModel().getSelectedItem();
            customerIDTxt.setText(String.valueOf(selectedCustomer.getCustomerID()));
            customerNameTxt.setText(selectedCustomer.getCustomerName());
            customerAddressTxt.setText(selectedCustomer.getCustomerAddress());
            customerPhoneTxt.setText(selectedCustomer.getPhoneNumber());
            customerPostalCodeTxt.setText(selectedCustomer.getPostalCode());
            ObservableList<Division> allDivisions = DBDivisions.getAllDivision();
            int divisionID = (allCustomerTableView.getSelectionModel().getSelectedItem().getDivisionID());
            ObservableList<Countries> allCountries = DBCountries.getAllCountries();

            if(divisionID <= 49) {
                Division divisionInUS = allDivisions.get(divisionID - 1);
                int countryIndex = (divisionInUS.getCountryID() - 1);
                Countries countryOfSelectedCustomer = (allCountries.get(countryIndex));
                countryCBox.setValue(countryOfSelectedCustomer);
                stateCBox.setValue(divisionInUS);
            }
            else if(divisionID == 52){
                Countries countryOfSelectedCustomer = allCountries.get(0);
                countryCBox.setValue(countryOfSelectedCustomer);
                Division hawaii = allDivisions.get(49);
                stateCBox.setValue(hawaii);
            }
            else if(divisionID == 54){
                Countries countryOfSelectedCustomer = allCountries.get(0);
                countryCBox.setValue(countryOfSelectedCustomer);
                Division Alaska = allDivisions.get(50);
                stateCBox.setValue(Alaska);
            }
            else if (divisionID > 54 && divisionID <= 72){
                Division divisionInUK = allDivisions.get(divisionID - 9);
                int countryIndex = (divisionInUK.getCountryID() - 1);
                Countries countryOfSelectedCustomer = (allCountries.get(countryIndex));
                countryCBox.setValue(countryOfSelectedCustomer);
                stateCBox.setValue(divisionInUK);
            }
            else if(divisionID > 72 && divisionID <= 104){
                Division divisionINCan = allDivisions.get(divisionID - 37);
                int countryIndex = (divisionINCan.getCountryID() - 1);
                Countries countryOfSelectedCustomer = (allCountries.get(countryIndex));
                countryCBox.setValue(countryOfSelectedCustomer);
                stateCBox.setValue(divisionINCan);
            }
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
            ObservableList<Appointments> allAppointments = DBAppointments.getAllAppointments();
            FilteredList<Appointments> selectedCustomerAppointments = new FilteredList<>(allAppointments, i -> i.getCustomerID() == allCustomerTableView.getSelectionModel().getSelectedItem().getCustomerID());

           if(!selectedCustomerAppointments.isEmpty()){
                Alert error = new Alert(Alert.AlertType.WARNING);
                error.setTitle("Warning Dialog");
                error.setContentText("Please Delete Associated Appointments First!");
                error.showAndWait();
            }
            else {
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
            }
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
            countryCBox.setValue(null);
            stateCBox.setValue(null);
        }
        catch (NullPointerException e){
            //Do nothing
        }
    }
    //----------------------------------------ON ACTION METHODS-------------------------------------------------
    /**
     * On Action Delete Customer method. Calls the deleteCustomer method which removes the customer
     * currently selected in the table view from the database.
     * */
    @FXML
    void onActionDeleteCustomer(ActionEvent event) {
       if(allCustomerTableView.getSelectionModel().isEmpty()){
           Alert error = new Alert(Alert.AlertType.WARNING);
           error.setTitle("Warning Dialog");
           error.setContentText("Please Select A Customer From The Table!");
           error.showAndWait();
       }
       else {
           deleteCustomer();
       }
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
        if(!(customerIDTxt.getText().isEmpty())){
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

        FXMLLoader loader1 = new FXMLLoader();
        loader1.setLocation(getClass().getResource("/Scenes/Appointment_Scene.fxml"));
        Parent mainSceneAdd1 = loader1.load();
        Scene scene2 = new Scene(mainSceneAdd1);
        AppointmentController pass1 = loader1.getController();
        pass1.passLoginInfo1(userName, userPassword);
        pass1.insertUserID();
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(scene2);
        stage.show();
    }
    /**
     * On Action Clear Form method. This method calls the clear form method to reset the data
     * on the form.
     * */
    @FXML
    public void onActionClearFormBtn(ActionEvent event) {
    clearForm();
    }
    /**
     * This method brings the user to the Report Scene.
     * @param event
     * */
    @FXML
    public void onActionReportsBtn(ActionEvent event) throws IOException{
        Stage stage;
        Parent scene;
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/Scenes/Report_Scene.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
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
        countryCBox.setItems(countryList);       // Sets up Country Combo box.

        //The follow is to set up the Table View with all the customers information.
        allCustomerTableView.setItems(customerList);
        customerIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        customerAddressCol.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
        customerNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        customerPhoneCol.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        customerPostalCodeCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        customerDivisionCol.setCellValueFactory(new PropertyValueFactory<>("divisionID"));

        allCustomerTableView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(javafx.scene.input.MouseEvent mouseEvent) {
                try {
                    setUpDateCustomer();
                } catch (NullPointerException e) {
                    //Do nothing if you click on the table but not on an appointment a null pointer will be thrown.
                }
            }
        });
    }
    }