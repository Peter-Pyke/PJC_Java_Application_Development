package Controllers;

import DBAccess.DBAppointments;
import DBAccess.DBContacts;
import Model.Appointments;
import Model.Contacts;
import Model.Reports;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.Month;
import java.util.ResourceBundle;

public class ReportsController implements Initializable {
    @FXML
    private ComboBox<Contacts> contactsComboBox;
    @FXML
    private ComboBox<String> locationComboBox;

    @FXML
    private TableView<Reports> reportTableView;

    @FXML
    private TableColumn<Reports, Month> C1;

    @FXML
    private TableColumn<Reports, String> C2;

    @FXML
    private TableColumn<Reports, Integer> C3;

    @FXML
    private TableColumn<Reports, String> C4;

    @FXML
    private TableColumn<Reports, String> C5;

    @FXML
    private TableColumn<Reports, String> C6;

    @FXML
    private TableColumn<Reports, String> C7;

    @FXML
    private TableColumn<Reports, Integer> C8;

    @FXML
    private TableColumn<Reports, Integer> C9;

    public void forMatTableColumns(){
        C1.setText("AppointmentID");
        C1.setCellValueFactory(new PropertyValueFactory<>("AppointmentID"));
        C2.setText("Title");
        C2.setCellValueFactory(new PropertyValueFactory<>("Title"));
        C3.setText("Description");
        C3.setCellValueFactory(new PropertyValueFactory<>("Description"));
        C4.setText("Location");
        C4.setCellValueFactory(new PropertyValueFactory<>("Location"));
        C5.setText("Type");
        C5.setCellValueFactory(new PropertyValueFactory<>("Type"));
        C6.setText("Start");
        C6.setCellValueFactory(new PropertyValueFactory<>("Start"));
        C7.setText("End");
        C7.setCellValueFactory(new PropertyValueFactory<>("End"));
        C8.setText("ContactID");
        C8.setCellValueFactory(new PropertyValueFactory<>("ContactID"));
        C9.setText("CustomerID");
        C9.setCellValueFactory(new PropertyValueFactory<>("CustomerID"));
    }

    public ObservableList<Reports> convertAppToReport() {
        ObservableList<Appointments> allAppointments = DBAppointments.getAllAppointments();
        ObservableList<Reports> reportsList = FXCollections.observableArrayList();
        for (int i = 0; i < allAppointments.size(); i++) {
            Appointments appointment = allAppointments.get(i);
            int appID = appointment.getAppointmentID();
            String appTitle = appointment.getTitle();
            String appDes = appointment.getDescription();
            String appLocation = appointment.getLocation();
            String appType = appointment.getType();
            Timestamp appStart = appointment.getStart();
            Timestamp appEnd = appointment.getEnd();
            int appContactID = appointment.getContactID();
            int appUserID = appointment.getUserID();
            Reports report = new Reports(appID, appTitle, appDes, appLocation, appType, appStart, appEnd, appContactID, appUserID);
            reportsList.add(report);
        }
        return reportsList;
    }
    @FXML
    void onActionContactsComboBox(ActionEvent event) {

        ObservableList<Reports> allReports = convertAppToReport();
        FilteredList<Reports> reportsForTable = new FilteredList<>(allReports, i -> i.getContactID() == contactsComboBox.getSelectionModel().getSelectedItem().getContactID());
        reportTableView.setItems(reportsForTable);
        forMatTableColumns();
    }

    @FXML
    void onActionMainMenu(ActionEvent event) throws IOException{
        Stage stage;
        Parent scene;
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/Scenes/Main_Scene.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void onActionLocationComboBox(ActionEvent event) {
        ObservableList<Reports> allReports = convertAppToReport();
        FilteredList<Reports> reportsForTable = new FilteredList<>(allReports, i -> i.getLocation().equals(locationComboBox.getSelectionModel().getSelectedItem()));
        reportTableView.setItems(reportsForTable);
        forMatTableColumns();
    }
    @FXML
    void onActionTypeMonth(ActionEvent event) throws IOException {
            ObservableList<Appointments> allAppointments = DBAppointments.getAllAppointments();
            ObservableList<Reports> myReport = FXCollections.observableArrayList();
            ObservableList<String> uniqueAppTyps = FXCollections.observableArrayList();

            for(int j = 0; j <allAppointments.size(); j++) {
                String type = allAppointments.get(j).getType();
                if (!uniqueAppTyps.contains(type)){
                    uniqueAppTyps.add(type);
                }
            }
        for(int i = 0; i < uniqueAppTyps.size(); i++){
            String appType = uniqueAppTyps.get(i);

            for(int inner = 1; inner < 13; inner++){
                Month month = Month.of(inner);
                int count = 0;

                    for(int inner2 = 0; inner2 < allAppointments.size(); inner2++){
                        Month appMonth = allAppointments.get(inner2).getStart().toLocalDateTime().getMonth();
                        if(month.equals(appMonth) && appType.equals(allAppointments.get(inner2).getType())){
                            count++;
                        }
                    }
                    if(!(count == 0)) {
                        Reports monthAndType = new Reports(month, appType, count);
                        myReport.add(monthAndType);
                    }
                }
            }

            reportTableView.setItems(myReport);
            C1.setCellValueFactory(new PropertyValueFactory<>("month"));
            C1.setText("Month");
            C2.setCellValueFactory(new PropertyValueFactory<>("type"));
            C2.setText("Type");
            C3.setCellValueFactory(new PropertyValueFactory<>("count"));
            C3.setText("Number of Appointments");
            C4.setCellValueFactory(new PropertyValueFactory<>(""));
            C4.setText("C4");
            C5.setCellValueFactory(new PropertyValueFactory<>(""));
            C5.setText("C5");
            C6.setCellValueFactory(new PropertyValueFactory<>(""));
            C6.setText("C6");
            C7.setCellValueFactory(new PropertyValueFactory<>(""));
            C7.setText("C7");
            C8.setCellValueFactory(new PropertyValueFactory<>(""));
            C8.setText("C8");
            C9.setCellValueFactory(new PropertyValueFactory<>(""));
            C9.setText("C9");

        }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        contactsComboBox.setItems(DBContacts.getAllContacts());

        ObservableList<Appointments> allAppointments = DBAppointments.getAllAppointments();
        ObservableList<String> locations = FXCollections.observableArrayList();
        for (int i = 0; i < allAppointments.size(); i++) {
            String location = allAppointments.get(i).getLocation();
            if(!(locations.contains(location))) {
                locations.add(location);
            }
        }
        locationComboBox.setItems(locations);

    }

    }

