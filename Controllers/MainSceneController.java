package Controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;

public class MainSceneController implements Initializable {

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
    private GridPane calendarGrid;

    @FXML
    private TableView<?> allCustomerTableView;

    @FXML
    private TableColumn<?, ?> customerIDCol;

    @FXML
    private TableColumn<?, ?> customerNameCol;

    @FXML
    private TableColumn<?, ?> customerAddressCol;

    @FXML
    private TableColumn<?, ?> customerPostalCodeCol;

    @FXML
    private TableColumn<?, ?> customerPhoneCol;

    @FXML
    private TableColumn<?, ?> customerCountryCol;

    @FXML
    private TableColumn<?, ?> customerStateCol;

    @FXML
    void onActionAddcustomer(ActionEvent event) {

    }

    @FXML
    void onActionCBoxCountry(ActionEvent event) {

    }

    @FXML
    void onActionCBoxCustomer(ActionEvent event) {

    }

    @FXML
    void onActionCBoxMonth(ActionEvent event) {

    }

    @FXML
    void onActionCBoxState(ActionEvent event) {

    }

    @FXML
    void onActionDeleteCustomer(ActionEvent event) {

    }

    @FXML
    void onActionUpdateCustomer(ActionEvent event) {

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){

    }

}