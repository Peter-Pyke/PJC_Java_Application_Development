package Controllers;
import DBAccess.DBCountries;
import DBAccess.DBUsers;
import Model.Countries;
import Model.Users;
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

    public boolean checkPasswordAndUserName(){
        ObservableList<Users> myusers = DBUsers.getAllUsers();
        for (int index = 0; index < myusers.size(); index++){
            if (userPasswordTxt.getText().equals(myusers.get(index).getUserPassword()) && userIdTxt.getText().equals(myusers.get(index).getUserName())){
                return true;
            }
        }
        return false;
    }

    @FXML
    void onActionEnterBtn(ActionEvent event) throws IOException {
        if (checkPasswordAndUserName()) {
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/Scenes/Main_Scene.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
        else {
            Alert error = new Alert(Alert.AlertType.WARNING);
            error.setTitle("Warning Dialog");
            error.setContentText("Incorrect User Name or Password!");
            error.showAndWait();

        }
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

}
