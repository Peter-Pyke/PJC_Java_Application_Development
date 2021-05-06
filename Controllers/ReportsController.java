package Controllers;

import DBAccess.DBAppointments;
import Model.Appointments;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.Month;

public class ReportsController {
    @FXML
    private TableView<Appointments> reportTableView;

    @FXML
    private TableColumn<Appointments, Integer> C1;

    @FXML
    private TableColumn<Appointments, ?> C2;

    @FXML
    private TableColumn<Appointments, ?> C3;

    @FXML
    private TableColumn<Appointments, ?> C4;

    @FXML
    private TableColumn<Appointments, ?> C5;

    @FXML
    private TableColumn<Appointments, ?> C6;

    @FXML
    private TableColumn<Appointments, ?> C7;

    @FXML
    private TableColumn<Appointments, ?> C8;

    @FXML
    private TableColumn<Appointments, ?> C9;

    @FXML
    void onActionContactSchedule(ActionEvent event) {

    }

    @FXML
    void onActionMainMenu(ActionEvent event) {

    }

    @FXML
    void onActionMyChoice(ActionEvent event) {

    }

    @FXML
    void onActionTypeMonth(ActionEvent event) {
        ObservableList<Appointments> allAppointments = DBAppointments.getAllAppointments();
        for(int i = 0; i < allAppointments.size(); i++){
            Month month = allAppointments.get(i).getStart().toLocalDateTime().getMonth();
            allAppointments.get(i).setMonthID(month);
        }
        reportTableView.setItems(allAppointments);
        C1.setCellValueFactory(new PropertyValueFactory<>("monthlyAppointments"));
    }
}
