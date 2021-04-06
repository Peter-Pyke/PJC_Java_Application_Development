package Controllers;
import DBAccess.DBCountries;
import Model.Countries;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * This is the LoginController class which provides the functionality to the Login Scene.
 * */
public class LoginController implements Initializable {
    Stage stage;
    Parent scene;

    @FXML
    private TextField userIdTxt;

    @FXML
    private TextField userPasswordTxt;

    @FXML
    private Label locationTxt;

    @FXML
    void onActionEnterBtn(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/Scenes/Main_Scene.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
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
