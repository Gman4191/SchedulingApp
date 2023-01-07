package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ModifyAppointmentController implements Initializable {
    public TextField idField;
    public TextField titleField;
    public TextField locationField;
    public TextField descriptionField;
    public ComboBox typeBox;
    public ComboBox customerBox;
    public DatePicker dateField;
    public ComboBox startBox;
    public ComboBox endBox;
    public ComboBox contactBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void OnSave(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/mainMenuView.fxml"));
        Parent root = loader.load();
        MainMenuController controller = loader.getController();
        loader.setController(controller);

        controller.returnToAppointmentTab();

        Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void OnCancel(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/mainMenuView.fxml"));
        Parent root = loader.load();
        MainMenuController controller = loader.getController();
        loader.setController(controller);

        controller.returnToAppointmentTab();

        Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
