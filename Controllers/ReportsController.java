package Controllers;

import DBAccess.DBAppointments;
import Model.Appointments;
import Model.Reports;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.Month;

public class ReportsController {
    @FXML
    private TableView<Reports> reportTableView;

    @FXML
    private TableColumn<Reports, Month> C1;

    @FXML
    private TableColumn<Reports, String> C2;

    @FXML
    private TableColumn<Reports, Integer> C3;

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
            ObservableList<Reports> myReport = FXCollections.observableArrayList();
            ObservableList<String> uniqueAppTyps = FXCollections.observableArrayList();

            for(int j = 0; j <allAppointments.size(); j++) {
                String type = allAppointments.get(j).getType();
                if (!uniqueAppTyps.contains(type)){
                    uniqueAppTyps.add(type);
                }
            }
        for(int inner = 0; inner < uniqueAppTyps.size(); inner++){
            String appType = uniqueAppTyps.get(inner);
            System.out.println(appType);

            for(int i = 1; i < 13; i++){
                Month month = Month.of(i);
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
        }


    }

