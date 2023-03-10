package Controllers;

import DAO.DBAppointment;
import Models.Appointment;
import Models.Contact;
import Models.Customer;
import Models.User;
import Utility.Utilities;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import java.io.IOException;
import java.net.URL;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.ResourceBundle;
import static javafx.collections.FXCollections.observableArrayList;

public class AddAppointmentController extends BaseAppointmentControl implements Initializable {
    /**
     * Appointment id
     */
    public TextField idField;
    /**
     * Appointment title
     */
    public TextField titleField;
    /**
     * Appointment type options
     */
    public ComboBox<String> typeBox;
    /**
     * Appointment description
     */
    public TextField descriptionField;
    /**
     * Appointment location
     */
    public TextField locationField;
    /**
     * Appointment date options
     */
    public DatePicker dateField;
    /**
     * Appointment end time options
     */
    public ComboBox<LocalTime> endBox;
    /**
     * Appointment start time options
     */
    public ComboBox<LocalTime> startBox;
    /**
     * Appointment customer options
     */
    public ComboBox<Customer> customerBox;
    /**
     * Appointment contact options
     */
    public ComboBox<Contact> contactBox;

    /**
     * Initialize the UI components
     * <p>LAMBDA JUSTIFICATION: The start and end drop downs use two lambda functions each to customize how their entries are displayed.
     *  The lambda functions add the user's time zone to the time options in the start and end drop downs to be more descriptive.</p>
     * @param url the URL
     * @param resourceBundle the resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialize the appointment type options
        typeBox.setItems(getTypes());

        // Initialize the customer options
        setCustomers();
        customerBox.setItems(getCustomers());

        //Initialize the contact options
        setContacts();
        contactBox.setItems(getContacts());

        // Get the business start and end times in the current time zone
        LocalTime businessEnd = Utilities.changeTimeZone(LocalTime.parse(businessEndTime, formatter),
                                                         businessZone, ZoneId.systemDefault());
        LocalTime businessStart = Utilities.changeTimeZone(LocalTime.parse(BaseAppointmentControl.businessStartTime, formatter),
                                                                businessZone, ZoneId.systemDefault());

        // Get a list of appointment times in thirty minute increments
        ObservableList<LocalTime> appointmentTimes = observableArrayList();
        for(; businessStart.isBefore(businessEnd) || businessStart.equals(businessEnd); businessStart = businessStart.plusMinutes(30))
            appointmentTimes.add(businessStart);

        // Initialize the start time drop down box
        startBox.setItems(appointmentTimes);

        // Display the current time zone next to each time entry
        startBox.setCellFactory(listView -> new ListCell<>() {
            @Override
            protected void updateItem(LocalTime time, boolean isEmpty) {
                super.updateItem(time, isEmpty);
                if (isEmpty || time == null) setText(null);
                else setText(time + " " +
                        ZoneId.systemDefault().getDisplayName(TextStyle.SHORT, Locale.getDefault()));
            }
        });

        // Display the current time zone next to the selected time entry
        startBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(LocalTime localTime) {
                return localTime + " " + ZoneId.systemDefault().getDisplayName(TextStyle.SHORT, Locale.getDefault());
            }

            @Override
            public LocalTime fromString(String s) {
                return null;
            }
        });

        // Initialize the end time drop down box
        endBox.setItems(appointmentTimes);

        // Display the current time zone next to each time entry
        endBox.setCellFactory(listView -> new ListCell<>() {
            @Override
            protected void updateItem(LocalTime time, boolean isEmpty) {
                super.updateItem(time, isEmpty);
                if (isEmpty || time == null) setText(null);
                else setText(time + " " +
                        ZoneId.systemDefault().getDisplayName(TextStyle.SHORT, Locale.getDefault()));
            }
        });

        // Display the current time zone next to the selected time entry
        endBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(LocalTime localTime) {
                return localTime + " " + ZoneId.systemDefault().getDisplayName(TextStyle.SHORT, Locale.getDefault());
            }

            @Override
            public LocalTime fromString(String s) {
                return null;
            }
        });
    }

    /**
     * Save a new appointment record to the appointments table
     * @param actionEvent the handled save event
     * @throws IOException when the main menu fails to load
     */
    public void OnSave(ActionEvent actionEvent) throws IOException {
        // Validate the entered appointment data
        if(!validateData())
            return;

        // Get the selected appointment's date
        LocalDate appointmentDate;
        if(dateField.getValue() == null)
            appointmentDate = LocalDate.parse(dateField.getEditor().getText(), DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        else
            appointmentDate = dateField.getValue();

        // Create a new appointment
        Appointment appointment = new Appointment(0, titleField.getText(), descriptionField.getText(), locationField.getText(),
                                            typeBox.getValue(), appointmentDate, startBox.getValue(), endBox.getValue(),
                                            customerBox.getValue().getId(), customerBox.getValue().getName(), User.getId(), User.getUserName(),
                                            contactBox.getValue().getId(), contactBox.getValue().getName());

        // Add the appointment to the database
        DBAppointment.addAppointment(appointment);


        // Return to the appointments tab in the main menu
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

    /**
     * Cancel the addition of new appointment information
     * @param actionEvent the handled cancel event
     * @throws IOException when the main menu fails to load
     */
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

    /**
     * Validate the entered appointment information
     * @return true if the entered data is valid
     */
    private boolean validateData(){
        LocalDate appointmentDate;
        try{
            if(titleField.getText().isEmpty())
                throw new Exception("The appointment title can not be blank");

            if(locationField.getText().isEmpty())
                throw new Exception("The appointment location can not be blank");

            if(descriptionField.getText().isEmpty())
                throw new Exception("The appointment description can not be blank");

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

            if(!DBAppointment.validateAppointmentTimes(appointmentDate, startBox.getValue(), endBox.getValue()))
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
