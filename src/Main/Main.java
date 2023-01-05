package Main;

import DAO.DBConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
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
