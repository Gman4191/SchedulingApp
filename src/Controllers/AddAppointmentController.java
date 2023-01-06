package Controllers;

import DAO.DBAppointment;
import Utility.Utilities;
import javafx.collections.ObservableList;
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
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import static javafx.collections.FXCollections.observableArrayList;

public class AddAppointmentController implements Initializable {
    private final static String pattern = "HH:mm";
    private final static ZoneId businessZone = ZoneId.of("America/New_York");
    private final static String businessStartTime = "08:00";
    private final static String businessEndTime = "22:00";

    public ComboBox<LocalTime> endBox;
    public ComboBox<LocalTime> startBox;
    public DatePicker dateField;
    public ComboBox<String> typeBox;
    public TextField descriptionField;
    public TextField locationField;
    public TextField titleField;
    public TextField idField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> types = observableArrayList();
        types.addAll("Tire Installation", "Tire Rotation", "Tire Repair", "Battery Replacement", "Oil Change");
        typeBox.setItems(types);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        LocalTime businessEnd = Utilities.changeTimeZone(LocalTime.parse(businessEndTime, formatter), businessZone, ZoneId.systemDefault());
        LocalTime businessTime = Utilities.changeTimeZone(LocalTime.parse(businessStartTime, formatter), businessZone, ZoneId.systemDefault());

        // Get a list of appointment times in thirty minute increments
        ObservableList<LocalTime> appointmentTimes = observableArrayList();
        for(; businessTime.isBefore(businessEnd) || businessTime.equals(businessEnd); businessTime = businessTime.plusMinutes(30))
            appointmentTimes.add(businessTime);

        // Initialize the start and end time selection boxes
        startBox.setItems(appointmentTimes);
        endBox.setItems(appointmentTimes);
    }

    public void OnSave(ActionEvent actionEvent) throws IOException {
        if(!validateData())
            return;

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

    private boolean validateData(){
        try{
            if(titleField.getText().isEmpty())
                throw new Exception("The appointment title can not be blank");

            if(descriptionField.getText().isEmpty())
                throw new Exception("The appointment description can not be blank");

            if(locationField.getText().isEmpty())
                throw new Exception("The appointment location can not be blank");

            if(typeBox.getSelectionModel().isEmpty())
                throw new Exception("The appointment type must be selected");

            if(dateField.getValue() == null)
                throw new Exception("The appointment date must be selected");

            if(startBox.getSelectionModel().isEmpty())
                throw new Exception("The appointment start time must be selected");

            if(endBox.getSelectionModel().isEmpty())
                throw new Exception("The appointment end time must be selected");

            if(startBox.getValue().isAfter(endBox.getValue()))
                throw new Exception("The appointment start time must be before the end time");

            if(!DBAppointment.validateSelectedTimes(dateField.getValue(), startBox.getValue(), endBox.getValue()))
                throw new Exception("The selected time overlaps with another appointment. Please try again");
        } catch(Exception e){
            Utilities.displayErrorMessage(e.getMessage());
            return false;
        }
        return true;
    }
}
