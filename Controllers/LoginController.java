package Controllers;
import DBAccess.DBUsers;
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
import java.io.*;
import java.net.URL;
import java.sql.Timestamp;
import java.time.*;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * This is the LoginController class which provides the functionality to the Login Scene.
 * */
public class LoginController implements Initializable {
    Stage stage;
    Parent scene;

    @FXML
    private Label userIDLabel;

    @FXML
    private TextField userIdTxt;

    @FXML
    private Label passwordLabel;

    @FXML
    private PasswordField userPasswordTxt;

    @FXML
    private Button enterBtnLabel;

    @FXML
    private Label locationTxt;

    @FXML
    private Label locationLabel;

/**
 * This is the method that checks the password and username during log in.
 * */
    public boolean checkPasswordAndUserName(){
        ObservableList<Users> myusers = DBUsers.getAllUsers();
        for (int index = 0; index < myusers.size(); index++){
            if (userPasswordTxt.getText().equals(myusers.get(index).getUserPassword()) && userIdTxt.getText().equals(myusers.get(index).getUserName())){
                return true;
            }
        }
        return false;
    }
    public void fileLogin() throws IOException{
        String fileName = "PJC_Java_Application_Development/Login_Activity.txt";
        FileWriter fWriter = new FileWriter(fileName, true);
        PrintWriter outPutFile = new PrintWriter(fWriter);

        ZoneId utc = ZoneId.of("UTC");
        LocalDateTime timeUTC = LocalDateTime.now(utc);

        if(checkPasswordAndUserName()) {
            outPutFile.println(userIdTxt.getText() + " " + timeUTC.toString()+" Successful Logging");
        }
        else{
            outPutFile.println(userIdTxt.getText() + " " + timeUTC.toString()+" Failed Logging");
        }
        outPutFile.close();
    }
/**
 * On Action method, this method controls what happens when the enter button is clicked.
 * */
    @FXML
    void onActionEnterBtn(ActionEvent event) throws IOException {
        fileLogin();
        if (checkPasswordAndUserName()) {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/Scenes/Main_Scene.fxml"));
            Parent mainSceneAdd = loader.load();
            Scene scene1 = new Scene(mainSceneAdd);
            MainSceneController pass = loader.getController();
            pass.passLoginInfo(userIdTxt.getText(), userPasswordTxt.getText());
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.setScene(scene1);
            stage.show();

        }
        else {
            if (Locale.getDefault().getLanguage().equals("fr")) {
                ResourceBundle rb = ResourceBundle.getBundle("Main/Nat", Locale.getDefault());
                Alert error = new Alert(Alert.AlertType.WARNING);
                error.setTitle(rb.getString("Warning"));
                error.setContentText(rb.getString("Incorrect"));
                error.showAndWait();
            }
            else{
                Alert error = new Alert(Alert.AlertType.WARNING);
                error.setTitle("Warning Dialog");
                error.setContentText("Incorrect User Name or Password!");
                error.showAndWait();
            }

        }
    }
    /**
     * This is the initialize method which controls what happens when the Login Scene is loaded.
     * @param url
     * @param resourceBundle
     * */
@Override
    public void initialize(URL url, ResourceBundle resourceBundle){
    ZonedDateTime currentTimeZone = ZonedDateTime.now();
    String zoneID = currentTimeZone.getZone().getId();
    locationTxt.setText(zoneID);
    try {
        ResourceBundle rb = ResourceBundle.getBundle("Main/Nat", Locale.getDefault());
        if (Locale.getDefault().getLanguage().equals("fr")) {
            userIDLabel.setText(rb.getString("UserID"));
            passwordLabel.setText(rb.getString("Password"));
            enterBtnLabel.setText(rb.getString("Enter"));
            locationLabel.setText(rb.getString("Location"));
        }
        else if (Locale.getDefault().getLanguage().equals("en")){

        }
    }
    catch(MissingResourceException e){
        e.printStackTrace();
        Alert error = new Alert(Alert.AlertType.WARNING);
        error.setTitle("Warning Dialog");
        error.setContentText("Language not supported!");
        error.showAndWait();
        System.exit(0);
    }
}


}
