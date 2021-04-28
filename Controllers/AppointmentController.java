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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.*;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
    public void addApp() throws SQLException {
        Connection conn = DBConnection.getConnection();

        String sqlStatement = "INSERT INTO appointments(Title, Description, Location, Type, Start, End, Created_By, Last_Updated_By, Customer_ID, User_ID, Contact_ID)"
                + "Values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        DBPreparedStatement.setPreparedStatement(conn, sqlStatement);

        PreparedStatement ps = DBPreparedStatement.getPreparedStatement();

        String title = titleTxt.getText();
        String description = descriptionTxtArea.getText();
        String location = locationTxt.getText();
        String type = TypeTxt.getText();
        Date start = Date.valueOf(startDatePicker.getValue());
        Date end = Date.valueOf(endDatePicker.getValue());
        String createdBy = userNameApp;
        String lastUpdatedBy = userNameApp;
        System.out.println(customerIDTxt.getText());
        int customerID = Integer.getInteger(customerIDTxt.getText());
        int userID = Integer.getInteger(userIDTxt.getText());
        int contactID = contactsComboBox.getSelectionModel().getSelectedItem().getContactID();



        //key-value map
        ps.setString(1, title);
        ps.setString(2, description);
        ps.setString(3, location);
        ps.setString(4, type);
        ps.setDate(5,start);
        ps.setDate(6, end);
        ps.setString(8, createdBy);
        ps.setString(10, lastUpdatedBy);
        ps.setInt(11, customerID);
        ps.setInt(12, userID);
        ps.setInt(13, contactID);

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

    }

    @FXML
    void onActionAppUpdateBtn(ActionEvent event) {
        System.out.println(customerIDTxt.getText());
    }

    @FXML
    void onActionMonthRBtn(ActionEvent event) {

    }

    @FXML
    void onActionWeekRBtn(ActionEvent event) {

    }

    @FXML
    void onActionStartComboBox(ActionEvent event){

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
    LocalTime selectedTime = LocalTime.of(0,0);
    LocalTime selectedTime2 = LocalTime.of(0,0);

    public void getLocalTimeEnd(){

        int endIndex = endTimeComboBox.getSelectionModel().getSelectedIndex();

        switch(endIndex){
            case 0: selectedTime2 = LocalTime.of(8,0);
                break;
            case 1: selectedTime2 = LocalTime.of(8,30);
                break;
            case 2: selectedTime2 = LocalTime.of(9,0);
                break;
            case 3: selectedTime2 = LocalTime.of(9,30);
                break;
            case 4: selectedTime2 = LocalTime.of(10,0);
                break;
            case 5: selectedTime2 = LocalTime.of(10,30);
                break;
            case 6: selectedTime2 = LocalTime.of(11,0);
                break;
            case 7: selectedTime2 = LocalTime.of(11,30);
                break;
            case 8: selectedTime2 = LocalTime.of(12,0);
                break;
            case 9: selectedTime2 = LocalTime.of(12,30);
                break;
            case 10: selectedTime2 = LocalTime.of(13,0);
                break;
            case 11: selectedTime2 = LocalTime.of(13,30);
                break;
            case 12: selectedTime2 = LocalTime.of(14,0);
                break;
            case 13: selectedTime2 = LocalTime.of(14,30);
                break;
            case 14: selectedTime2 = LocalTime.of(15,0);
                break;
            case 15: selectedTime2 = LocalTime.of(15,30);
                break;
            case 16: selectedTime2 = LocalTime.of(16,0);
                break;
            case 17: selectedTime2 = LocalTime.of(16,30);
                break;
            case 18: selectedTime2 = LocalTime.of(17,0);
                break;
            case 19: selectedTime2 = LocalTime.of(17,30);
                break;
            case 20: selectedTime2 = LocalTime.of(18,0);
                break;
            case 21: selectedTime2 = LocalTime.of(18,30);
                break;
            case 22: selectedTime2 = LocalTime.of(19,0);
                break;
            case 23: selectedTime2 = LocalTime.of(19,30);
                break;
            case 24: selectedTime2 = LocalTime.of(20,0);
                break;
            case 25: selectedTime2 = LocalTime.of(20,30);
                break;
            case 26: selectedTime2 = LocalTime.of(21,0);
                break;
            case 27: selectedTime2 = LocalTime.of(21,30);
                break;
            case 28: selectedTime2 = LocalTime.of(22,0);
                break;
        }

    }
    public void getLocalTimeStart(){

        int startIndex = startTimeComboBox.getSelectionModel().getSelectedIndex();

        switch(startIndex){
            case 0: selectedTime = LocalTime.of(8,0);
                break;
            case 1: selectedTime = LocalTime.of(8,30);
                break;
            case 2: selectedTime = LocalTime.of(9,0);
                break;
            case 3: selectedTime = LocalTime.of(9,30);
                break;
            case 4: selectedTime = LocalTime.of(10,0);
                break;
            case 5: selectedTime = LocalTime.of(10,30);
                break;
            case 6: selectedTime = LocalTime.of(11,0);
                break;
            case 7: selectedTime = LocalTime.of(11,30);
                break;
            case 8: selectedTime = LocalTime.of(12,0);
                break;
            case 9: selectedTime = LocalTime.of(12,30);
                break;
            case 10: selectedTime = LocalTime.of(13,0);
                break;
            case 11: selectedTime = LocalTime.of(13,30);
                break;
            case 12: selectedTime = LocalTime.of(14,0);
                break;
            case 13: selectedTime = LocalTime.of(14,30);
                break;
            case 14: selectedTime = LocalTime.of(15,0);
                break;
            case 15: selectedTime = LocalTime.of(15,30);
                break;
            case 16: selectedTime = LocalTime.of(16,0);
                break;
            case 17: selectedTime = LocalTime.of(16,30);
                break;
            case 18: selectedTime = LocalTime.of(17,0);
                break;
            case 19: selectedTime = LocalTime.of(17,30);
                break;
            case 20: selectedTime = LocalTime.of(18,0);
                break;
            case 21: selectedTime = LocalTime.of(18,30);
                break;
            case 22: selectedTime = LocalTime.of(19,0);
                break;
            case 23: selectedTime = LocalTime.of(19,30);
                break;
            case 24: selectedTime = LocalTime.of(20,0);
                break;
            case 25: selectedTime = LocalTime.of(20,30);
                break;
            case 26: selectedTime = LocalTime.of(21,0);
                break;
            case 27: selectedTime = LocalTime.of(21,30);
                break;
            case 28: selectedTime = LocalTime.of(22,0);
                break;
        }

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customerComboBox.setItems(DBCustomers.getAllCustomers());
        contactsComboBox.setItems(DBContacts.getAllContacts());

        LocalTime start = LocalTime.of(8, 0);
        LocalTime am = LocalTime.of(12, 31);
        LocalTime pm = LocalTime.of(10, 1);
        LocalTime myTimeS = LocalTime.of(1, 0);


        while (start.isBefore(am.plusSeconds(1))) {
            String myTimeString = start.toString();
            if(start.isBefore(LocalTime.of(11,59))) {
                startTimeComboBox.getItems().add(myTimeString + " am");
                endTimeComboBox.getItems().add(myTimeString + " am");
            }
            else if(start.isAfter(LocalTime.of(11, 59))){
                startTimeComboBox.getItems().add(start + " pm");
                endTimeComboBox.getItems().add(start + " pm");
            }
            start = start.plusMinutes(30);
        }
        while (myTimeS.isBefore(pm.plusSeconds(0))) {
            String myTimeString = myTimeS.toString();
            startTimeComboBox.getItems().add(myTimeString + " pm");
            endTimeComboBox.getItems().add(myTimeString + " pm");
            myTimeS = myTimeS.plusMinutes(30);
        }
    }
}