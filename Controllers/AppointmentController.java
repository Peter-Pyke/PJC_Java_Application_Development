package Controllers;


import DBAccess.DBAppointments;
import DBAccess.DBContacts;
import DBAccess.DBCustomers;
import DBAccess.DBUsers;
import DataBase.DBConnection;
import DataBase.DBPreparedStatement;
import DataBase.DBQuery;
import Model.Appointments;
import Model.Contacts;
import Model.Customers;
import Model.Users;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.*;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

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
                + " Start = ?, End = ?, Last_Updated_By = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ?"
                + "WHERE Appointment_ID = ?";

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
        ps.setInt(1, AppointmentID);
        ps.setString(2, title);
        ps.setString(3, description);
        ps.setString(4, location);
        ps.setString(5, type);
        ps.setString(6, start);
        ps.setString(7, end);
        ps.setString(8, lastUpdatedBy);
        ps.setInt(9, customerID);
        ps.setInt(10, userID);
        ps.setInt(11, contactID);

        ps.execute();

    }
    public void addApp() throws SQLException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh':'mm a");
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
        String start = startDatePicker.getValue().toString() + " " + selectedTime.toString();
        String end = endDatePicker.getValue().toString() + " " + selectedTime2.toString();
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
    void onActionAppDeleteBtn(ActionEvent event) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh':'mm a");
        String time =startTimeComboBox.getSelectionModel().getSelectedItem();
        System.out.println(time);
        LocalTime stringToTime = LocalTime.parse(time, formatter);
        System.out.println(stringToTime);
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

    @FXML
    void onActionFillUserIDBtn(){
        ObservableList<Users> allUsers = DBUsers.getAllUsers();
        int index = 0;
        while(index < allUsers.size()){
            Users myUser = allUsers.get(index);
            String myUsersPassword = myUser.getUserPassword();
            if (userPasswordApp.equals(myUsersPassword)) {
                userIDTxt.setText(String.valueOf(myUser.getUserID()));
            }
            index++;
        }
    }
    @FXML
    void onActionAllAppointments(){
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

        LocalTime mytime = LocalTime.now();
        DateTimeFormatter myformatter = DateTimeFormatter.ofPattern("hh':'mm a");
        String time = mytime.format(myformatter);
        System.out.println(time);

        LocalTime open = LocalTime.of(8, 0);
        LocalTime close = LocalTime.of(22, 1);

        while (open.isBefore(close.plusSeconds(1))){
            String timeToEnter  = open.format(myformatter);
            startTimeComboBox.getItems().add(timeToEnter);
            endTimeComboBox.getItems().add(timeToEnter);
            open = open.plusMinutes(30);
        }

    }
}