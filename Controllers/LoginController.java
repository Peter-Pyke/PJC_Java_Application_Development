package Controllers;
import DBAccess.DBCountries;
import Model.Countries;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private TextField userIdTxt;

    @FXML
    private TextField userPasswordTxt;

    @FXML
    private Label locationTxt;

    @FXML
    void onActionLoginEnter(ActionEvent event) {

    }
@Override
    public void initialize(URL url, ResourceBundle resourceBundle){

}
public void showMe(ActionEvent actionEvent){
    ObservableList<Countries> countryList = DBCountries.getAllCountries();
    for(Countries C : countryList){
        System.out.println("Country Id :" + C.getId() + " Name : " + C.getName());
    }
}
}
