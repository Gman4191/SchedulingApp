package Controllers;

import DAO.DBAppointment;
import DAO.DBCustomer;
import Models.Contact;
import Models.Customer;
import Utility.Utilities;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.ResourceBundle;

import static javafx.collections.FXCollections.observableArrayList;

public class ModifyAppointmentController extends BaseAppointmentControl implements Initializable {
    public TextField idField;
    public TextField titleField;
    public TextField locationField;
    public TextField descriptionField;
    public ComboBox<String> typeBox;
    public ComboBox<Customer> customerBox;
    public DatePicker dateField;
    public ComboBox<LocalTime> startBox;
    public ComboBox<LocalTime> endBox;
    public ComboBox<Contact> contactBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialize the appointment type options
        ObservableList<String> types = observableArrayList();
        types.addAll("Tire Installation", "Tire Rotation", "Tire Repair", "Battery Replacement", "Oil Change");
        typeBox.setItems(types);

        // Initialize the customer options
        customerBox.setItems(DBCustomer.getAllCustomers());

        //Initialize the contact options
        contactBox.setItems(DBAppointment.getAllContacts());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        LocalTime businessEnd = Utilities.changeTimeZone(LocalTime.parse(businessEndTime, formatter), businessZone, ZoneId.systemDefault());
        LocalTime businessTime = Utilities.changeTimeZone(LocalTime.parse(businessStartTime, formatter), businessZone, ZoneId.systemDefault());

        // Get a list of appointment times in thirty minute increments
        ObservableList<LocalTime> appointmentTimes = observableArrayList();
        for(; businessTime.isBefore(businessEnd) || businessTime.equals(businessEnd); businessTime = businessTime.plusMinutes(30))
            appointmentTimes.add(businessTime);

        // Initialize the start and end time selection boxes
        startBox.setItems(appointmentTimes);
        startBox.setCellFactory(listView -> new ListCell<>() {
            @Override
            protected void updateItem(LocalTime time, boolean isEmpty) {
                super.updateItem(time, isEmpty);
                if (isEmpty || time == null) setText(null);
                else setText(time + " " +
                        ZoneId.systemDefault().getDisplayName(TextStyle.SHORT, Locale.ENGLISH));
            }
        });
        endBox.setItems(appointmentTimes);
        endBox.setCellFactory(listView -> new ListCell<>() {
            @Override
            protected void updateItem(LocalTime time, boolean isEmpty) {
                super.updateItem(time, isEmpty);
                if (isEmpty || time == null) setText(null);
                else setText(time + " " +
                        ZoneId.systemDefault().getDisplayName(TextStyle.SHORT, Locale.ENGLISH));
            }
        });
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

    private boolean validateData(){
        LocalDate appointmentDate;
        try{
            if(titleField.getText().isEmpty())
                throw new Exception("The appointment title can not be blank");

            if(descriptionField.getText().isEmpty())
                throw new Exception("The appointment description can not be blank");

            if(locationField.getText().isEmpty())
                throw new Exception("The appointment location can not be blank");

            if(typeBox.getSelectionModel().isEmpty())
                throw new Exception("The appointment type must be selected");

            if(customerBox.getSelectionModel().isEmpty())
                throw new Exception("The customer associated with the appointment must be selected");

            if(contactBox.getSelectionModel().isEmpty())
                throw new Exception("The contact associated with the appointment must be selected");

            // Validate the date, whether entered via text or selected through the calendar
            if(dateField.getValue() == null) {
                if (dateField.getEditor().getText().isEmpty())
                    throw new Exception("The appointment date must be selected");
                else
                    appointmentDate = LocalDate.parse(dateField.getEditor().getText(), DateTimeFormatter.ofPattern("MM/dd/yyyy"));
            } else {
                appointmentDate = dateField.getValue();
            }

            if(startBox.getSelectionModel().isEmpty())
                throw new Exception("The appointment start time must be selected");

            if(endBox.getSelectionModel().isEmpty())
                throw new Exception("The appointment end time must be selected");

            if(startBox.getValue().isAfter(endBox.getValue()))
                throw new Exception("The appointment start time must be before the end time");

            if(!DBAppointment.validateSelectedTimes(appointmentDate, startBox.getValue(), endBox.getValue()))
                throw new Exception("The selected time overlaps with another appointment. Please try again");
        } catch(DateTimeParseException e){
            Utilities.displayErrorMessage("The entered date must be of the format 'MM/dd/yyyy'");
            return false;
        } catch(Exception e){
            Utilities.displayErrorMessage(e.getMessage());
            return false;
        }
        return true;
    }
}
