package Controllers;


import DBAccess.DBAppointments;
import DBAccess.DBCustomers;
import DataBase.DBConnection;
import DataBase.DBPreparedStatement;
import DataBase.DBQuery;
import Model.Appointments;
import Model.Contacts;
import Model.Customers;
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
    private ComboBox<Time> startTimeComboBox;

    @FXML
    private DatePicker endDatePicker;

    @FXML
    private ComboBox<Time> endTimeComboBox;

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
    private static String userName = null; // String object to hold the username of the person who logged in.
    private static String userPassword = null; // String object to hold the password of the person who logged in.
    public void passLoginInfo(String userName1, String userPassword1){
        userName = userName1;
        userPassword = userPassword1;
    }
    ObservableList<LocalTime> times = FXCollections.observableArrayList();
    LocalTime start = LocalTime.of(6,0);
    LocalTime end = LocalTime.of(8,0);
    while(start.isBefore(end.plusSeconds(1))){
        startTimeComboBox.getItems().add(start);
        start = start.plusMinutes(10);
    }

    public void addApp() throws SQLException{

        Connection conn = DBConnection.getConnection();

        String sqlStatement = "INSERT INTO appointments(Title, Description, Location, Type, Start, End, Created_By, Last_Updated_By, Customer_ID, User_ID)"
                        + "Values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        DBPreparedStatement.setPreparedStatement(conn, sqlStatement);

        PreparedStatement ps = DBPreparedStatement.getPreparedStatement();

        String title = titleTxt.getText();
        String description = descriptionTxtArea.getText();
        String location = locationTxt.getText();
        String type = TypeTxt.getText();
        LocalDate start = startDatePicker.getValue();
        LocalDate end = endDatePicker.getValue();
        String createdBy = userName;
        String lastUpdatedBy = userName;
        int customerID = Integer.getInteger(customerIDTxt.getText());
        int userID = Integer.getInteger(userIDTxt.getText());
        int contactID = contactsComboBox.getSelectionModel().getSelectedItem().getContactID();

        //key-value map
        ps.setString(1, title);
        ps.setString(2, description);
        ps.setString(3, location);
        ps.setString(4, type);
        //ps.setDate(5,start);
        //ps.setDate(6, end);
        ps.setString(8, createdBy);
        ps.setString(10, lastUpdatedBy);
        ps.setInt(11,customerID);
        ps.setInt(12, userID);
        ps.setInt(13, contactID);

        ps.execute();

    }
    @FXML
    void onActionAppAddBtn(ActionEvent event) {
        LocalDate startDate = startDatePicker.getValue();
        LocalTime mytime = LocalTime.of(12,30);
        LocalDateTime myDate = LocalDateTime.of(startDate, mytime);
        System.out.println(myDate);
    }

    @FXML
    void onActionAppDeleteBtn(ActionEvent event) {

    }

    @FXML
    void onActionAppUpdateBtn(ActionEvent event) {

    }
    @FXML
    void onActionMonthRBtn(ActionEvent event) {

    }

    @FXML
    void onActionWeekRBtn(ActionEvent event) {

    }
    @FXML
    void onActionCustomerComboBox(ActionEvent event) {
    updateTable();
    }
    /**
     * This is my updateTable method. This method displays all the appointments associated with the customer that is
     * currently selected inside of the customer combo box.
     * */
    public void updateTable(){
        ObservableList<Appointments> allAppointments = DBAppointments.getAllAppointments();
        FilteredList<Appointments> selectedCustomerAppointments = new FilteredList<>(allAppointments, i-> i.getCustomerID() == customerComboBox.getSelectionModel().getSelectedItem().getCustomerID());
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
    }
}