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
import javafx.collections.FXCollections;
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

/**
 * This is the AppointmentController class and will provide all the functionality to the Appointment Scene.
 * */
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
    private ComboBox<Users> userIDComboBox;

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
    private TableColumn<Appointments, LocalDate> startDateCol;

    @FXML
    private TableColumn<Appointments, LocalDate> endDateCol;

    @FXML
    private TableColumn<Appointments, Integer> customerIDCol;

    private static String userNameApp = null; // String object to hold the username of the person who logged in.
    private static String userPasswordApp = null; // String object to hold the password of the person who logged in.
    public boolean resultForOverLap; //Used in my checkForOverLap method and in a if statement inside my OnAction add App method.
    /**
 * passLoginInfo1 method is used to pass the username and password stored in the MainSceneController class to
 * the AppointmentController class.
 * @param userName_App userName of the person logged in.
 * @param userPassword_App password of the person logged in.
 * */
    public void passLoginInfo1(String userName_App, String userPassword_App) {
        userNameApp = userName_App;
        userPasswordApp = userPassword_App;
    }
//---------------------------------------------UTILITY METHODS --------------------------------------------------
/**
 * The upDateApp method is called when the update button is clicked in the appointment scene. This method updates
 * the selected appointment with the new information given by the user.
 * */
    public void upDateApp() throws SQLException{
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH':'mm':'ss");
        LocalTime selectedStartTime = ConvertTime.startTimeToGMT(startTimeComboBox.getSelectionModel().getSelectedItem(),startDatePicker.getValue());
        LocalTime selectedEndTime = ConvertTime.endTimeToGMT(endTimeComboBox.getSelectionModel().getSelectedItem(), endDatePicker.getValue());
        String startTimeGMT = selectedStartTime.format(formatter);
        String endTimeGMT = selectedEndTime.format(formatter);

        String startTime = startTimeComboBox.getSelectionModel().getSelectedItem();
        String endTime = endTimeComboBox.getSelectionModel().getSelectedItem();
        LocalDate startDate = startDatePicker.getValue();
        LocalDate endDate = endDatePicker.getValue();

        if(selectedStartTime.compareTo(selectedEndTime) < 0 && ConvertTime.hoursOfOperation(startTime,endTime,startDate,endDate)) {
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
            String start = startDatePicker.getValue().toString() + " " + startTimeGMT;
            String end = endDatePicker.getValue().toString() + " " + endTimeGMT;
            String lastUpdatedBy = userNameApp;
            int customerID = Integer.valueOf(customerIDTxt.getText());
            int userID = userIDComboBox.getSelectionModel().getSelectedItem().getUserID();
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
            setUpTable();
        }
        else {
            Alert error = new Alert(Alert.AlertType.WARNING);
            error.setTitle("Warning Dialog");
            error.setContentText("Start Date/Time must be before End Date/Time and Times must be within operating hours");
            error.getDialogPane().setPrefWidth(800);
            error.showAndWait();
        }
    }
    /**
     * The addApp method creates a new appointment with the information provided by the user.
     * */
    public void addApp() {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH':'mm':'ss");
            LocalTime selectedStartTime = ConvertTime.startTimeToGMT(startTimeComboBox.getSelectionModel().getSelectedItem(), startDatePicker.getValue()); //LocalTime.parse(startTimeComboBox.getSelectionModel().getSelectedItem(),formatter);
            LocalTime selectedEndTime = ConvertTime.endTimeToGMT(endTimeComboBox.getSelectionModel().getSelectedItem(), endDatePicker.getValue());
            String startTimeGMT = selectedStartTime.format(formatter);
            String endTimeGMT = selectedEndTime.format(formatter);

            String startTime = startTimeComboBox.getSelectionModel().getSelectedItem();
            String endTime = endTimeComboBox.getSelectionModel().getSelectedItem();
            LocalDate startDate = startDatePicker.getValue();
            LocalDate endDate = endDatePicker.getValue();

            if (selectedStartTime.compareTo(selectedEndTime) < 0 && ConvertTime.hoursOfOperation(startTime, endTime, startDate, endDate)) {

                Connection conn = DBConnection.getConnection();
                String sqlStatement = "INSERT INTO appointments(Title, Description, Location, Type, Start, End, Created_By, Last_Updated_By, Customer_ID, User_ID, Contact_ID)"
                        + "Values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

                DBPreparedStatement.setPreparedStatement(conn, sqlStatement);

                PreparedStatement ps = DBPreparedStatement.getPreparedStatement();

                String title = titleTxt.getText();
                String description = descriptionTxtArea.getText();
                String location = locationTxt.getText();
                String type = TypeTxt.getText();
                String start = startDatePicker.getValue().toString() + " " + startTimeGMT;
                String end = endDatePicker.getValue().toString() + " " + endTimeGMT;
                String createdBy = userNameApp;
                String lastUpdatedBy = userNameApp;
                int customerID = Integer.valueOf(customerIDTxt.getText());
                int userID = userIDComboBox.getSelectionModel().getSelectedItem().getUserID();
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
                filterTableByCustomer();
            } else {
                Alert error = new Alert(Alert.AlertType.WARNING);
                error.setTitle("Warning Dialog");
                error.setContentText("Start Date/Time must be before End Date/Time and Times must be within operating hours");
                error.getDialogPane().setPrefWidth(800);
                error.showAndWait();
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }
    /***
     *The deleteApp method removes the selected appointment from the database.
     */
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
            filterTableByCustomer();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }
    /**
     * The setUpTable method fills the appointment table view with all the appointments from the data base.
     * */
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
    /**
     * The filterTableByCustomer method displays all the appointments associated with the customer that is
     * currently selected inside of the customer combo box.
     */
    public void filterTableByCustomer() {
        int customerID;
        if(customerComboBox.getSelectionModel().isEmpty()){
            customerID = appointmentTableView.getSelectionModel().getSelectedItem().getCustomerID();
        }
        else{
            customerID = customerComboBox.getSelectionModel().getSelectedItem().getCustomerID();
        }
        ObservableList<Appointments> allAppointments = DBAppointments.getAllAppointments();
        FilteredList<Appointments> selectedCustomerAppointments = new FilteredList<>(allAppointments, i -> i.getCustomerID() == customerID);
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
    /**
     * The filterTableByMonth method set up the appointment table view with only the appointments for the current
     * month.
     * */
    public void filterTableByMonth(){
        int currentMonth = LocalDate.now().getMonthValue();
        ObservableList<Appointments> allAppointments = DBAppointments.getAllAppointments();
        FilteredList<Appointments> appointmentsThisMonth = new FilteredList<>(allAppointments, i -> i.getStart().toLocalDateTime().getMonthValue() == currentMonth);
        appointmentTableView.setItems(appointmentsThisMonth);
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
     * The filterTableByWeek method sets up the appointment table view with only the appointments in the
     * current week. Example: If the date is 05-13-2021 a Thursday appointments will be shown up to an including
     * 05-20-2021 the following Thursday.
     * */
    public void filterTableByWeek(){
        int DayOfTheYear = LocalDate.now().getDayOfYear();
        ObservableList<Appointments> allAppointments = DBAppointments.getAllAppointments();
        FilteredList<Appointments> appointmentsThisWeek = new FilteredList<>(allAppointments, i -> i.getStart().toLocalDateTime().getDayOfYear() <= (DayOfTheYear + 8));
        appointmentTableView.setItems(appointmentsThisWeek);
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
     * The insertUserID method sets the UserID text field with the userID of whoever is loggen in.
     * */
    public void insertUserID(){
        ObservableList<Users> allUsers = DBUsers.getAllUsers();
        int index = 0;
        while(index < allUsers.size()){
            Users myUser = allUsers.get(index);
            String myUsersPassword = myUser.getUserPassword();
            String myUserName = myUser.getUserName();
            if (userPasswordApp.equals(myUsersPassword) && userNameApp.equals(myUserName)) {
               userIDComboBox.setValue(myUser);
            }
            index++;
        }
    }
    /**
     * The insertCustomerID method sets the CustomerID text field with the Id of the customer either selected
     * in the customer combo box or the customer assoicated with the appointment selected in the table.
     * */
    public void insertCustomerID(){
            customerIDTxt.setText(String.valueOf(customerComboBox.getSelectionModel().getSelectedItem().getCustomerID()));
    }
    /**
     * The checkForOverLap method takes the customerID and start time of the appointment to be saved or updated
     * and checks it against all the appointments in the data base that customer already has to ensure there is
     * no overlapping appointments.
     * */
    public void checkForOverLap() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh':'mm a");
        String customerIDTXT = customerIDTxt.getText();
        ZoneId utcTime = ZoneId.of("UTC");
        ZoneId myTime = ZonedDateTime.now().getZone();
        ObservableList<Appointments> allAppointments = DBAppointments.getAllAppointments();
        //Getting start date and time to compare
        LocalDate dateStart = startDatePicker.getValue();
        LocalTime timeStart = LocalTime.parse(startTimeComboBox.getValue(), formatter);
        ZonedDateTime timeStartZoned = ZonedDateTime.of(dateStart, timeStart, myTime);
        ZonedDateTime timeStartInUTC = timeStartZoned.withZoneSameInstant(utcTime);
        LocalTime startTimeToCompare = timeStartInUTC.toLocalTime();
        System.out.println(startTimeToCompare+ " StartTimeToCompare from picker on screen");
        //Getting end date and time to compare
        LocalTime timeEnd = LocalTime.parse(endTimeComboBox.getValue(), formatter);
        ZonedDateTime timeEndZoned = ZonedDateTime.of(dateStart, timeEnd, myTime);
        ZonedDateTime timeEndInUTC = timeEndZoned.withZoneSameInstant(utcTime);
        LocalTime endTimeToCompare = timeEndInUTC.toLocalTime();
        System.out.println(endTimeToCompare + " EndTimeToCompare from picker on screen");
        /*
        Loops through all appointments to find the ones associated with the selected customer.
        When it finds an appointment for that customer it checks the start time against the times chosen
        in the start and end combo boxes. If the start time of the appointment to be saved is the same as
        an exciting appointment or if it is between the start and end time of an exciting appointment a
        Warning message will appear telling the user that the customer has an appointment at that time already.
         */
        for (int i = 0; i < allAppointments.size(); i++) {
            Appointments selectedAppointment = allAppointments.get(i);
            LocalDate date = selectedAppointment.getStart().toLocalDateTime().toLocalDate();
            LocalTime timeStart2 = selectedAppointment.getStart().toLocalDateTime().toLocalTime();
            LocalTime timeEnd2 = selectedAppointment.getEnd().toLocalDateTime().toLocalTime();
            int customerIDFromApp = selectedAppointment.getCustomerID();
            String customerIDFromAppString = String.valueOf(customerIDFromApp);
            if (customerIDFromAppString.equals(customerIDTXT) && dateStart.equals(date)) {
                System.out.println(date+" data base appointment");
                System.out.println(timeStart2+" data base appointment timeStart2");
                System.out.println(timeEnd2+ " data base appointment");
                if ((startTimeToCompare.isAfter(timeStart2) && startTimeToCompare.isBefore(timeEnd2)) || timeStart2.equals(startTimeToCompare) || (timeStart2.isAfter(startTimeToCompare) && timeStart2.isBefore(endTimeToCompare))) {
                    resultForOverLap = true;
                }

            }
        }
    }
    //-------------------------------------ON ACTION METHODS-----------------------------------------------------------
    /**
     * The onActionAppAddBtn method contains the actions to be taken when the add button is clicked inside the
     * appointment scene. This method does a few different checks before adding the appointment to the data base.
     * @param event
     * */
    @FXML
    void onActionAppAddBtn(ActionEvent event) {
       try {
           ObservableList<Integer> appointmentIDs = FXCollections.observableArrayList();
           ObservableList<Appointments> allAppointments = DBAppointments.getAllAppointments();
           resultForOverLap = false;
           checkForOverLap();
           System.out.println(resultForOverLap);
           int index = 0;
           while(index < allAppointments.size()){
               appointmentIDs.add(allAppointments.get(index).getAppointmentID());
               index++;
           }
           if(!appointmentIDTxt.getText().isEmpty()) {
               if (appointmentIDs.contains(Integer.valueOf(appointmentIDTxt.getText()))) {
                   Alert error = new Alert(Alert.AlertType.WARNING);
                   error.setTitle("Warning Dialog");
                   error.setContentText("AppointmentID already in use. Click update or reset ID");
                   error.showAndWait();
               }
           }
           else if(resultForOverLap){
               Alert error = new Alert(Alert.AlertType.WARNING);
               error.setTitle("Warning Dialog");
               error.setContentText("Customer already has an appointment at this time!");
               error.showAndWait();

           }
           else if(startDatePicker.getValue().isAfter(endDatePicker.getValue())){
               Alert error = new Alert(Alert.AlertType.WARNING);
               error.setTitle("Warning Dialog");
               error.setContentText("Start date must be before end date!");
               error.showAndWait();
           }
           else {
               addApp();
           }
       }
       catch(NumberFormatException e){
           e.printStackTrace();
       }
    }
    /**
     * The onActionStartPicker method contains the actions taken when the start date picker is clicked.
     * @param event
     * */
    @FXML
    void onActionStartPicker(ActionEvent event){
        try {
            LocalTime open = LocalTime.of(8, 00);
            LocalTime close = LocalTime.of(22, 00);
            //This check is done here as well as when the time is selected to ensure it is not missed.
            LocalTime myStartTime = ConvertTime.getStartESTTime(startTimeComboBox.getSelectionModel().getSelectedItem(), startDatePicker.getValue());

            if (myStartTime.compareTo(open) < 0 || myStartTime.compareTo(close) >= 0) {
                Alert error = new Alert(Alert.AlertType.WARNING);
                error.setTitle("Warning Dialog");
                error.setContentText("Selected time is outside hours of operation (EST).");
                error.showAndWait();
            }
        } catch (NullPointerException e) {
            //NullPointerException is going to happen if they select date before time.
        }
    }
    /**
     * The onActionStartTime method contains the actions to be taken when the start time combo box is clicked.
     * @param event
     * */
    @FXML
    void onActionStartTime(ActionEvent event) {
        try {
            LocalTime open = LocalTime.of(8, 00);
            LocalTime close = LocalTime.of(22, 00);

            LocalTime myStartTime = ConvertTime.getStartESTTime(startTimeComboBox.getSelectionModel().getSelectedItem(), startDatePicker.getValue());

            if (myStartTime.compareTo(open) < 0 || myStartTime.compareTo(close) >= 0) {
                Alert error = new Alert(Alert.AlertType.WARNING);
                error.setTitle("Warning Dialog");
                error.setContentText("Selected time is outside hours of operation (EST).");
                error.showAndWait();
            }
        } catch (NullPointerException e) {
                //Do nothing. NullPointerException is going to happen if they select time before date.
        }
    }
    /**
     * The onActionEndPicker method contains the actions to be taken when the end date picker is clicked.
     * @param event
     * */
    @FXML
    void onActionEndPicker(ActionEvent event){
        try {
            LocalTime open = LocalTime.of(8, 00);
            LocalTime close = LocalTime.of(22, 00);
            //This check is done here and when the time is selected to ensure it is not missed.
            LocalTime myEndTime = ConvertTime.getStartESTTime(endTimeComboBox.getSelectionModel().getSelectedItem(), startDatePicker.getValue());

            if (myEndTime.compareTo(open) < 0 || myEndTime.compareTo(close) > 0) {
                Alert error = new Alert(Alert.AlertType.WARNING);
                error.setTitle("Warning Dialog");
                error.setContentText("Selected time is outside hours of operation (EST).");
                error.showAndWait();
            }
        } catch (NullPointerException e) {
            //NullPointerException is going to happen if they select date before time.
        }
    }
    /**
     * The onActionEndTime method contains the actions to be taken when the end time combo box is selected.
     * @param event
     * */
    @FXML
    void onActionEndTime(ActionEvent event) {
        try {
            LocalTime open = LocalTime.of(8, 00);
            LocalTime close = LocalTime.of(22, 00);
            LocalTime myEndTime = ConvertTime.getEndESTTime(endTimeComboBox.getSelectionModel().getSelectedItem(),endDatePicker.getValue());
            if (myEndTime.compareTo(open) < 0 || myEndTime.compareTo(close) > 0) {
                Alert error = new Alert(Alert.AlertType.WARNING);
                error.setTitle("Warning Dialog");
                error.setContentText("Selected time is outside hours of operation (EST).");
                error.showAndWait();
            }
        }
        catch (NullPointerException e){
          //Do nothing. NullPointerException is going to happen if they select the time before date.
        }

    }
    /**
     * The onActionMainMenuBtn method brings the user back to the main scene when he clicked the Main menu button
     * inside the appointment scene.
     * @param event
     * */
    @FXML
    void onActionMainMenuBtn(ActionEvent event) throws IOException {
        Stage stage;
        Parent scene;
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/Scenes/Main_Scene.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    /**
     * The onActionAppDeleteBtn method calls the deleteApp method to remove the selected appointment from the
     * data base.
     * @param event
     * */
    @FXML
    void onActionAppDeleteBtn(ActionEvent event) {
    try {
        deleteApp();
    }
    catch(NullPointerException e){
        Alert error = new Alert(Alert.AlertType.WARNING);
        error.setTitle("Warning Dialog");
        error.setContentText("Please Select Appointment from Table!");
        error.showAndWait();
    }
    }
    /**
     * The onActionAppUpdateBtn method contains the actions to be taken when the update button is clicked inside
     * the appointment scene. The method a check before updated the data base with the new
     * information provide by the user.
     * @param event
     * */
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
    /**
     * The onActionMonthRBtn method calls the filterTableByMonth method which sets the table up with only the
     * appointments for the current month.
     * @param event
     * */
    @FXML
    void onActionMonthRBtn(ActionEvent event) {
    filterTableByMonth();
    }
    /**
     * The onActionWeekRBtn method calls the filterTableByWeek method which sets the table up with only the
     * appointment for the current week.
     * @param event
     * */
    @FXML
    void onActionWeekRBtn(ActionEvent event) {
        filterTableByWeek();
    }
    /**
     * The onActionAllAppointments method calls the setUpTable method with sets the table up with all the
     * appointments from the data base when the all appointments button is clicked inside the appointment scene.
     * @param event
     * */
    @FXML
    void onActionAllAppointments(ActionEvent event){
    setUpTable();
    }
    /**
     * The onActionResetIDBtn method clears the appointmentID text field. This is needed in case the user
     * selected an appointment to update but then decided he wanted to create a new appointment.
     * @param event
     * */
    @FXML
    void onActionResetIDBtn(ActionEvent event){
    appointmentIDTxt.clear();
    }
    /**
     * The onActionClearFormBtn method resets the scene clearing all fields when the clear form button is selected
     * inside the appointment scene. This method can be used when the users wants to start over.
     * @param event
     * */
    @FXML
    void onActionClearFormBtn(ActionEvent event) throws IOException{
        Stage stage;
        Parent scene;
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/Scenes/Appointment_Scene.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    /**
     * The onActionCustomerComboBox method contains the actions to be taken when the user selects a customer from
     * the combo box inside the appointments scene. This method sets the table view up with only the appointments
     * for the customer selected.
     * @param event
     * */
    @FXML
    void onActionCustomerComboBox(ActionEvent event){
        try {
            filterTableByCustomer();
            insertCustomerID();
        }
        catch(NullPointerException e){
            //Do nothing. NullPointer will be thrown when combo box is cleared due to no customer being selected.
        }
    }
    /**
     * The initialize method controls the set up of the appointment scene. This method also controls the actions
     * taken when the users clicks on an appointment in the table view.
     * @param resourceBundle
     * @param url
     * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customerComboBox.setItems(DBCustomers.getAllCustomers()); // sets up the customer combo box.
        contactsComboBox.setItems(DBContacts.getAllContacts());// sets up the contacts combo box.
        userIDComboBox.setItems(DBUsers.getAllUsers());
        setUpTable(); //set the table view with all the appointments currently in the data base.

        //The code below fills the form in with the information of the appointment clicked in the table view.
        appointmentTableView.setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>() {
            @Override
            public void handle(javafx.scene.input.MouseEvent mouseEvent) {
                try {
                    ZoneId myZoneId = ZoneId.of(TimeZone.getDefault().getID());
                    ZoneId UTC = ZoneId.of("UTC");
                    DateTimeFormatter myformatter = DateTimeFormatter.ofPattern("hh':'mm a");

                    appointmentIDTxt.setText(String.valueOf(appointmentTableView.getSelectionModel().getSelectedItem().getAppointmentID()));
                    titleTxt.setText(appointmentTableView.getSelectionModel().getSelectedItem().getTitle());
                    descriptionTxtArea.setText(appointmentTableView.getSelectionModel().getSelectedItem().getDescription());
                    locationTxt.setText(appointmentTableView.getSelectionModel().getSelectedItem().getLocation());
                    TypeTxt.setText(appointmentTableView.getSelectionModel().getSelectedItem().getType());

                    LocalDateTime startTimeFromTimeStamp = appointmentTableView.getSelectionModel().getSelectedItem().getStart().toLocalDateTime();
                    ZonedDateTime dataBaseStartTimeUTC = ZonedDateTime.of(startTimeFromTimeStamp, UTC);
                    ZonedDateTime displayStartTimeZoned = dataBaseStartTimeUTC.withZoneSameInstant(myZoneId);
                    LocalTime startTimeToDisplay = displayStartTimeZoned.toLocalTime();

                    LocalDateTime endTimeFromTimeStamp = appointmentTableView.getSelectionModel().getSelectedItem().getEnd().toLocalDateTime();
                    ZonedDateTime dataBaseEndTimeUTC = ZonedDateTime.of(endTimeFromTimeStamp, UTC);
                    ZonedDateTime displayEndTimeZoned = dataBaseEndTimeUTC.withZoneSameInstant(myZoneId);
                    LocalTime endTimeToDisplay = displayEndTimeZoned.toLocalTime();

                    startTimeComboBox.setValue(startTimeToDisplay.format(myformatter));
                    endTimeComboBox.setValue(endTimeToDisplay.format(myformatter));
                    LocalDate startDate = startTimeFromTimeStamp.toLocalDate();
                    LocalDate endDate = endTimeFromTimeStamp.toLocalDate();
                    startDatePicker.setValue(startDate);
                    endDatePicker.setValue(endDate);
                    customerIDTxt.setText(String.valueOf(appointmentTableView.getSelectionModel().getSelectedItem().getCustomerID()));
                    insertUserID();
                    contactsComboBox.setValue(DBContacts.getAllContacts().get(appointmentTableView.getSelectionModel().getSelectedItem().getContactID() - 1));
                    customerComboBox.getSelectionModel().clearSelection();
                }
                catch (NullPointerException e){
                    //Do nothing if you click on the table but not on an appointment a null pointer will be thrown.
                }
                }
        });

        DateTimeFormatter myFormatter = DateTimeFormatter.ofPattern("hh':'mm a"); //Declaring a formatter object
        LocalTime open = LocalTime.of(8, 0); //LocalTime to represent the opening hours of operation
        LocalTime close = LocalTime.of(22, 1); //LocalTime to represent the closing hours of operation.

        //This loop fills the start time and end time combo boxes with my times(String objects) in 30 minute increments.
        while (open.isBefore(close.plusSeconds(1))){
            String timeToEnter  = open.format(myFormatter);
            startTimeComboBox.getItems().add(timeToEnter);
            endTimeComboBox.getItems().add(timeToEnter);
            open = open.plusMinutes(30);
        }

    }
}