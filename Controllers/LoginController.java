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

/**
 * This is the LoginController class which provides the functionality to the Login Scene.
 * */
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
    /**
     * This is the initialize method which is used as an override method for the LoginController. As far as I can tell is helps to
     * load the scene.
     * @param url
     * @param resourceBundle
     * */
@Override
    public void initialize(URL url, ResourceBundle resourceBundle){

}
/**
 * This is the onActionEnter method which controls what will happen when the enter button is clicked.
 * @param actionEvent
 * */
//currently using this method to retrieve country info from database as an example. Will be changes later.
public void showMe(ActionEvent actionEvent){
    ObservableList<Countries> countryList = DBCountries.getAllCountries();
    for(Countries C : countryList){
        System.out.println("Country Id :" + C.getId() + " Name : " + C.getName());
    }
}
}
