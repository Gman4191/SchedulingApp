package Main;

import DAO.DBConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class Main extends Application {

    /**
     * Load and display the main menu
     * @param primaryStage the primary stage
     * @throws IOException when the main menu fails to load
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = new FXMLLoader(getClass().getResource("../Views/loginView.fxml")).load();
        primaryStage.setTitle("Scheduling System");
        primaryStage.setScene(new Scene(root, 900, 600));
        primaryStage.show();
    }

    /**
     * Establish connection to the database and launch the application
     * @param args the application arguments
     */
    public static void main(String[] args) {
        DBConnection.makeConnection();
        launch(args);
        DBConnection.closeConnection();
    }
}
