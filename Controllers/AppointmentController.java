package Controllers;


import DBAccess.DBAppointments;
import DBAccess.DBContacts;
import DBAccess.DBCustomers;
import DBAccess.DBUsers;
import DBAccess.DBConnection;
import DBAccess.DBPreparedStatement;
import DBAccess.DBQuery;
import Model.Appointments;
import Model.Contacts;
import Model.Customers;
import Model.Users;
import Utilities.ConvertTime;
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
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.TimeZone;

public class AppointmentController implements Initializable {

    @FXML
    private TextField appointmentIDTxt;

    @FXML
    private TextField titleTxt;

    @FXML
    private TextArea descriptionTxtArea;

    @FXML
    private TextField locationTxt;

    @FXML
    private ComboBox<Contacts> contactsComboBox;

    @FXML
    private TextField TypeTxt;

    @FXML
    private DatePicker startDatePicker;

    @FXML
    private ComboBox<String> startTimeComboBox;

    @FXML
    private DatePicker endDatePicker;

    @FXML
    private ComboBox<String> endTimeComboBox;

    @FXML
    private TextField customerIDTxt;

    @FXML
    private TextField userIDTxt;

    @FXML
    private ComboBox<Customers> customerComboBox;

    @FXML
    private TableView<Appointments> appointmentTableView;

    @FXML
    private TableColumn<Appointments, Integer> appIDCol;

    @FXML
    private TableColumn<Appointments, String> titleCol;

    @FXML
    private TableColumn<Appointments, String> descriptionCol;

    @FXML
    private TableColumn<Appointments, String> locationCol;

    @FXML
    private TableColumn<Appointments, String> contactCol;

    @FXML
    private TableColumn<Appointments, String> typeCol;

    @FXML
    private TableColumn<Appointments, Date> startDateCol;

    @FXML
    private TableColumn<Appointments, Date> endDateCol;

    @FXML
    private TableColumn<Appointments, Integer> customerIDCol;
    private static String userNameApp = null; // String object to hold the username of the person who logged in.
    private static String userPasswordApp = null; // String object to hold the password of the person who logged in.

    public void passLoginInfo1(String userName1, String userPassword1) {
        userNameApp = userName1;
        userPasswordApp = userPassword1;
    }

    @FXML
    void onActionCustomerComboBox(ActionEvent event) {
        updateTable();
        int customerID = customerComboBox.getSelectionModel().getSelectedItem().getCustomerID();
        customerIDTxt.setText(String.valueOf(customerID));
    }
    public void upDateApp() throws SQLException{
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh':'mm a");
        LocalTime selectedTime = LocalTime.parse(startTimeComboBox.getSelectionModel().getSelectedItem(),formatter);
        LocalTime selectedTime2 = LocalTime.parse(endTimeComboBox.getSelectionModel().getSelectedItem(), formatter);
        Connection conn = DBConnection.getConnection();

        String sqlStatement = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?,"
                + " Start = ?, End = ?, Last_Updated_By = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? "
                + "WHERE Appointment_ID =?;";

        DBPreparedStatement.setPreparedStatement(conn, sqlStatement);

        PreparedStatement ps = DBPreparedStatement.getPreparedStatement();


        String title = titleTxt.getText();
        String description = descriptionTxtArea.getText();
        String location = locationTxt.getText();
        String type = TypeTxt.getText();
        String start = startDatePicker.getValue().toString() + " " + selectedTime.toString();
        String end = endDatePicker.getValue().toString() + " " + selectedTime2.toString();
        String lastUpdatedBy = userNameApp;
        int customerID = Integer.valueOf(customerIDTxt.getText());
        int userID = Integer.valueOf(userIDTxt.getText());
        int contactID = contactsComboBox.getSelectionModel().getSelectedItem().getContactID();
        int AppointmentID = Integer.valueOf(appointmentIDTxt.getText());

        //key-value map
        ps.setString(1, title);
        ps.setString(2, description);
        ps.setString(3, location);
        ps.setString(4, type);
        ps.setString(5, start);
        ps.setString(6, end);
        ps.setString(7, lastUpdatedBy);
        ps.setInt(8, customerID);
        ps.setInt(9, userID);
        ps.setInt(10, contactID);
        ps.setInt(11, AppointmentID);

        ps.execute();

    }
    public void addApp() throws SQLException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh':'mm a");
        //Change lines below to convert timeZone
        LocalTime selectedTime = LocalTime.parse(startTimeComboBox.getSelectionModel().getSelectedItem(),formatter);
        LocalTime selectedTime2 = LocalTime.parse(endTimeComboBox.getSelectionModel().getSelectedItem(), formatter);
        Connection conn = DBConnection.getConnection();

        String sqlStatement = "INSERT INTO appointments(Title, Description, Location, Type, Start, End, Created_By, Last_Updated_By, Customer_ID, User_ID, Contact_ID)"
                + "Values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        DBPreparedStatement.setPreparedStatement(conn, sqlStatement);

        PreparedStatement ps = DBPreparedStatement.getPreparedStatement();


        String title = titleTxt.getText();
        String description = descriptionTxtArea.getText();
        String location = locationTxt.getText();
        String type = TypeTxt.getText();
        String start = startDatePicker.getValue().toString() + " " + selectedTime.toString(); //Right code to Convert the selecedTime to UTC.
        String end = endDatePicker.getValue().toString() + " " + selectedTime2.toString(); //Same as above
        String createdBy = userNameApp;
        String lastUpdatedBy = userNameApp;
        int customerID = Integer.valueOf(customerIDTxt.getText());
        int userID = Integer.valueOf(userIDTxt.getText());
        int contactID = contactsComboBox.getSelectionModel().getSelectedItem().getContactID();

        //key-value map
        ps.setString(1, title);
        ps.setString(2, description);
        ps.setString(3, location);
        ps.setString(4, type);
        ps.setString(5, start);
        ps.setString(6, end);
        ps.setString(7, createdBy);
        ps.setString(8, lastUpdatedBy);
        ps.setInt(9, customerID);
        ps.setInt(10, userID);
        ps.setInt(11, contactID);

        ps.execute();

    }
    @FXML
    void onActionAppAddBtn(ActionEvent event) {
       try {
           addApp();
       }
       catch(SQLException e){
           e.printStackTrace();
       }
    }
    @FXML
    void onActionStartPicker(ActionEvent event){
        try {
            LocalTime open = LocalTime.of(8, 00);
            LocalTime close = LocalTime.of(22, 00);

            LocalTime myStartTime = ConvertTime.getStartESTTime(startTimeComboBox.getSelectionModel().getSelectedItem(), startDatePicker.getValue());

            if (myStartTime.compareTo(open) < 0 || myStartTime.compareTo(close) >= 0) {
                startTimeComboBox.getSelectionModel().clearSelection();
                Alert error = new Alert(Alert.AlertType.WARNING);
                error.setTitle("Warning Dialog");
                error.setContentText("Selected time is outside hours of operation (EST).");
                error.showAndWait();
            }
        } catch (NullPointerException e) {
            //NullPointerException is going to happen if they select date before time.
        }
    }
    @FXML
    void onActionStartTime(ActionEvent event) {
        try {
            LocalTime open = LocalTime.of(8, 00);
            LocalTime close = LocalTime.of(22, 00);

            LocalTime myStartTime = ConvertTime.getStartESTTime(startTimeComboBox.getSelectionModel().getSelectedItem(), startDatePicker.getValue());

            if (myStartTime.compareTo(open) < 0 || myStartTime.compareTo(close) >= 0) {
                startTimeComboBox.getSelectionModel().clearSelection();
                Alert error = new Alert(Alert.AlertType.WARNING);
                error.setTitle("Warning Dialog");
                error.setContentText("Selected time is outside hours of operation (EST).");
                error.showAndWait();
            }
        } catch (NullPointerException e) {
                //NullPointerException is going to happen if they select time before date.
        }
    }
    @FXML
    void onActionEndTime(ActionEvent event) {
        try {
            LocalTime open = LocalTime.of(8, 00);
            LocalTime close = LocalTime.of(22, 00);
            LocalTime myEndTime = ConvertTime.getEndESTTime(endTimeComboBox.getSelectionModel().getSelectedItem(),endDatePicker.getValue());
            if (myEndTime.compareTo(open) < 0 || myEndTime.compareTo(close) >= 0) {
                System.out.println("OutSide Working Hours!");
            }
        }
        catch (NullPointerException e){
            Alert error = new Alert(Alert.AlertType.WARNING);
            error.setTitle("Warning Dialog");
            error.setContentText("Please Select A Date First.");
            error.showAndWait();
        }

    }
    @FXML
    void onActionMainMenuBtn(ActionEvent event) throws IOException {
        Stage stage;
        Parent scene;
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/Scenes/Main_Scene.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    public void deleteApp(){
        try {
                Connection conn = DBConnection.getConnection(); // Create Connection Object
                DBQuery.setStatement(conn);
                Statement statement = DBQuery.getStatement(); //Get Statement reference

                Appointments selectedAppointment = appointmentTableView.getSelectionModel().getSelectedItem();
                int selectedAppointmentID = selectedAppointment.getAppointmentID();

                // Raw SQL delete statement
                String deleteStatement = "DELETE FROM appointments WHERE Appointment_ID = '" + selectedAppointmentID + "'";

                //Execute statement
                statement.execute(deleteStatement);

                int selectedAppID = selectedAppointmentID;
                Alert error = new Alert(Alert.AlertType.WARNING);
                error.setTitle("Warning Dialog");
                error.setContentText("Appointment_ID: " + selectedAppID + " has been deleted!");
                error.showAndWait();
                appointmentTableView.setItems(DBAppointments.getAllAppointments());
            }
        catch (SQLException e){
            e.printStackTrace();
        }
    }
    @FXML
    void onActionAppDeleteBtn(ActionEvent event) {
    deleteApp();
    }

    @FXML
    void onActionAppUpdateBtn(ActionEvent event) {
        try {
            if(appointmentTableView.getSelectionModel().isEmpty()){
                Alert error = new Alert(Alert.AlertType.WARNING);
                error.setTitle("Warning Dialog");
                error.setContentText("Please Select Appointment from Table!");
                error.showAndWait();
            }
            else if(!(String.valueOf(appointmentTableView.getSelectionModel().getSelectedItem().getCustomerID()).equals(customerIDTxt.getText()))){
                Alert error = new Alert(Alert.AlertType.WARNING);
                error.setTitle("Warning Dialog");
                error.setContentText("Please Insert Correct Customer ID!");
                error.showAndWait();
            }
            else {
                appointmentIDTxt.setText(String.valueOf(appointmentTableView.getSelectionModel().getSelectedItem().getAppointmentID()));
                upDateApp();
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
    @FXML
    void onActionMonthRBtn(ActionEvent event) {

    }

    @FXML
    void onActionWeekRBtn(ActionEvent event) {

    }
    public void insertUserID(){
        ObservableList<Users> allUsers = DBUsers.getAllUsers();
        int index = 0;
        while(index < allUsers.size()){
            Users myUser = allUsers.get(index);
            String myUsersPassword = myUser.getUserPassword();
            String myUserName = myUser.getUserName();
            if (userPasswordApp.equals(myUsersPassword) && userNameApp.equals(myUserName)) {
                userIDTxt.setText(String.valueOf(myUser.getUserID()));
            }
            index++;
        }
    }
    @FXML
    void onActionInsertUserIDBtn(ActionEvent event){
        ObservableList<Users> allUsers = DBUsers.getAllUsers();
        int index = 0;
        while(index < allUsers.size()){
            Users myUser = allUsers.get(index);
            String myUsersPassword = myUser.getUserPassword();
            String myUserName = myUser.getUserName();
            if (userPasswordApp.equals(myUsersPassword) && userNameApp.equals(myUserName)) {
                userIDTxt.setText(String.valueOf(myUser.getUserID()));
            }
            index++;
        }
    }
    @FXML
    void onActionInsertCusIDBtn(ActionEvent event){
        try {
            if(customerComboBox.getSelectionModel().isEmpty() || appointmentTableView.getSelectionModel().isEmpty()){
                Alert error = new Alert(Alert.AlertType.WARNING);
                error.setTitle("Warning Dialog");
                error.setResizable(true);
                error.setContentText("Please Select Customer from drop down or Appointment from Table! Note: When selecting Customer, the ID will be automatically be inserted");
                error.getDialogPane().setPrefWidth(800);
                error.showAndWait();
            }
            customerIDTxt.setText(String.valueOf(appointmentTableView.getSelectionModel().getSelectedItem().getCustomerID()));
        }
        catch(NullPointerException e){
            //Do nothing. Alert is giving above for this issue.
        }
        }
    public void setUpTable(){
        ObservableList<Appointments> allAppointments = DBAppointments.getAllAppointments();
        appointmentTableView.setItems(allAppointments);
        appIDCol.setCellValueFactory(new PropertyValueFactory<>("AppointmentID"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("Title"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("Description"));
        locationCol.setCellValueFactory(new PropertyValueFactory<>("Location"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("Type"));
        startDateCol.setCellValueFactory(new PropertyValueFactory<>("Start"));
        endDateCol.setCellValueFactory(new PropertyValueFactory<>("End"));
        contactCol.setCellValueFactory(new PropertyValueFactory<>("ContactID"));
        customerIDCol.setCellValueFactory(new PropertyValueFactory<>("CustomerID"));
    }
    @FXML
    void onActionAllAppointments(){
    setUpTable();
    }
    /**
     * This is my updateTable method. This method displays all the appointments associated with the customer that is
     * currently selected inside of the customer combo box.
     */
    public void updateTable() {
        ObservableList<Appointments> allAppointments = DBAppointments.getAllAppointments();
        FilteredList<Appointments> selectedCustomerAppointments = new FilteredList<>(allAppointments, i -> i.getCustomerID() == customerComboBox.getSelectionModel().getSelectedItem().getCustomerID());
        appointmentTableView.setItems(selectedCustomerAppointments);
        appIDCol.setCellValueFactory(new PropertyValueFactory<>("AppointmentID"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("Title"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("Description"));
        locationCol.setCellValueFactory(new PropertyValueFactory<>("Location"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("Type"));
        startDateCol.setCellValueFactory(new PropertyValueFactory<>("Start"));
        endDateCol.setCellValueFactory(new PropertyValueFactory<>("End"));
        contactCol.setCellValueFactory(new PropertyValueFactory<>("ContactID"));
        customerIDCol.setCellValueFactory(new PropertyValueFactory<>("CustomerID"));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customerComboBox.setItems(DBCustomers.getAllCustomers());
        contactsComboBox.setItems(DBContacts.getAllContacts());

        setUpTable();

        appointmentTableView.setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>() {
            @Override
            public void handle(javafx.scene.input.MouseEvent mouseEvent) {
                appointmentIDTxt.setText(String.valueOf(appointmentTableView.getSelectionModel().getSelectedItem().getAppointmentID()));
                titleTxt.setText(appointmentTableView.getSelectionModel().getSelectedItem().getTitle());
                descriptionTxtArea.setText(appointmentTableView.getSelectionModel().getSelectedItem().getDescription());
                locationTxt.setText(appointmentTableView.getSelectionModel().getSelectedItem().getLocation());
                TypeTxt.setText(appointmentTableView.getSelectionModel().getSelectedItem().getType());
                DateTimeFormatter myformatter = DateTimeFormatter.ofPattern("hh':'mm a");
                Timestamp startTime = appointmentTableView.getSelectionModel().getSelectedItem().getStart();
                LocalDateTime startTime1 = startTime.toLocalDateTime();
                String startTime2 = startTime1.format(myformatter);
                Timestamp endTime = appointmentTableView.getSelectionModel().getSelectedItem().getEnd();
                LocalDateTime endTime1 = endTime.toLocalDateTime();
                String endTime2 = endTime1.format(myformatter);
                startTimeComboBox.setValue(startTime2);
                endTimeComboBox.setValue(endTime2);
                LocalDate startDate = startTime.toLocalDateTime().toLocalDate();
                LocalDate endDate = endTime.toLocalDateTime().toLocalDate();
                startDatePicker.setValue(startDate);
                endDatePicker.setValue(endDate);
                customerIDTxt.setText(String.valueOf(appointmentTableView.getSelectionModel().getSelectedItem().getCustomerID()));
                insertUserID();
            }
        });


        DateTimeFormatter myFormatter = DateTimeFormatter.ofPattern("hh':'mm a");
        LocalTime open = LocalTime.of(8, 0);
        LocalTime close = LocalTime.of(22, 1);

        while (open.isBefore(close.plusSeconds(1))){
            String timeToEnter  = open.format(myFormatter);
            startTimeComboBox.getItems().add(timeToEnter);
            endTimeComboBox.getItems().add(timeToEnter);
            open = open.plusMinutes(30);
        }

    }
}