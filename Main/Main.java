package Main;

import DBAccess.DBConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.sql.SQLException;


/**
 * This is my Main class which contains my start and main methods. It is the beginning point of my program.
 * @author Peter Chouinard. StudentID: #001162524
 * */
public class Main extends Application {

    /**
     * This is my start method and is the first method called by my program.
     * @param primaryStage the first stage loaded by my program.
     * */
    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("/Scenes/Login Scene.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

/**
 * This is my main method which connects to the database and launches the program.
 * */
    public static void main(String[] args) throws SQLException {

        DBConnection.startConnection();
        launch(args);
        DBConnection.closeConnection();
    }
}
