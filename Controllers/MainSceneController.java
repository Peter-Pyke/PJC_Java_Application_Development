package Controllers;
import DBAccess.DBCountries;
import DBAccess.DBCustomers;
import DBAccess.DBDivisions;
import Model.Countries;
import Model.Customers;
import Model.Division;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * This is my MainSceneController class and will be used to give the main screen after login its functionality.
 * */
public class MainSceneController implements Initializable {

    @FXML
    private ComboBox<Customers> customerComboBox;

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
    private GridPane calendarGrid;

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
    /**
     * This is my onActionLoadCustomerBtn method and controls what happens when the load customer button is clicked.
     * @param event
     * */
    @FXML
    void onActionLoadCustomerBtn(ActionEvent event) {


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){


        ObservableList<Countries> countryList = DBCountries.getAllCountries();
        ObservableList<Division> divisionList = DBDivisions.getAllDivision();
        ObservableList<Customers> customerList = DBCustomers.getAllCustomers();
        allCustomerTableView.setItems(customerList);
        customerIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        customerAddressCol.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
        customerNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        customerPhoneCol.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        customerPostalCodeCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        customerStateCol.setCellValueFactory(new PropertyValueFactory<>("divisionID"));
        customerComboBox.setItems(customerList);
        countryCBox.setItems(countryList);
        stateCBox.setItems(divisionList);
    }

}