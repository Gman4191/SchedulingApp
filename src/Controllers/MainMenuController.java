package Controllers;

import DAO.DBAppointment;
import DAO.DBContact;
import DAO.DBCustomer;
import DAO.DBReport;
import Models.Appointment;
import Models.Contact;
import Models.Customer;
import Utility.Utilities;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import java.io.IOException;
import java.net.URL;
import java.time.*;
import java.time.format.TextStyle;
import java.time.temporal.TemporalAdjusters;
import java.util.Locale;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {
    /**
     * The main menu options
     */
    public TabPane mainMenuTabPane;

    // region Customers
    /**
     * Table displaying the customers
     */
    public TableView<Customer> customerTable;
    /**
     * Contains customer names
     */
    public TableColumn<Customer, String> customerNameCol;
    /**
     * Contains customer first-level divisions
     */
    public TableColumn<Customer, String> divisionCol;
    /**
     * Contains customer addresses
     */
    public TableColumn<Customer, String> addressCol;
    /**
     * Contains customer postal codes
     */
    public TableColumn<Customer, String> postalCodeCol;
    /**
     * Contains customer phone numbers
     */
    public TableColumn<Customer, String> phoneNumberCol;
    /**
     * Contains customer country names
     */
    public TableColumn<Customer, String> countryCol;
    /**
     * Contains the overview of all appointments
     */
    //endregion

    // region Appointments
    public Tab appointmentTab;
    /**
     * Table displaying the appointments
     */
    public TableView<Appointment> appointmentTable;
    /**
     * Contains appointment ids
     */
    public TableColumn<Appointment, Integer> appointmentIdCol;
    /**
     * Contains appointment titles
     */
    public TableColumn<Appointment, String> appointmentTitleCol;
    /**
     * Contains appointment types
     */
    public TableColumn<Appointment, String> appointmentTypeCol;
    /**
     * Contains appointment descriptions
     */
    public TableColumn<Appointment, String> appointmentDescCol;
    /**
     * Contains appointment locations
     */
    public TableColumn<Appointment, String> appointmentLocationCol;
    /**
     * Contains appointment contact names
     */
    public TableColumn<Appointment, String> appointmentContactCol;
    /**
     * Contains appointment dates
     */
    public TableColumn<Appointment, LocalDate> appointmentDateCol;
    /**
     * Contains appointment start times
     */
    public TableColumn<Appointment, String> appointmentStartCol;
    /**
     * Contains appointment end times
     */
    public TableColumn<Appointment, String> appointmentEndCol;
    /**
     * Contains appointment customer names
     */
    public TableColumn<Appointment, String> appointmentCustomerCol;
    /**
     * Contains appointment user names
     */
    public TableColumn<Appointment, String> appointmentUserCol;
    /**
     * Button to display all appointments
     */
    public RadioButton allButton;
    /**
     * Button to display appointments filtered by the current month
     */
    public RadioButton monthButton;
    /**
     * Button to display appointments filtered by the current week
     */
    public RadioButton weekButton;
    // endregion

    // region Reports
    /**
     * Appointment type options for the monthly appointment totals
     */
    public ComboBox<String> appointmentTypeBox;
    /**
     * Appointment month options for the monthly appointment totals
     */
    public ComboBox<Month> appointmentMonthBox;
    /**
     * Contains the result of the monthly appointment total report
     */
    public Label totalAppointments;
    /**
     * Contact name options
     */
    public ComboBox<Contact> contactBox;
    /**
     * Table of appointments filtered by contact name
     */
    public TableView<Appointment> contactApptTable;
    /**
     * Contains appointment ids
     */
    public TableColumn<Appointment, Integer> contactApptIdCol;
    /**
     * Contains appointment titles
     */
    public TableColumn<Appointment, String> contactApptTitleCol;
    /**
     * Contains appointment types
     */
    public TableColumn<Appointment, String> contactApptTypeCol;
    /**
     * Contains appointment descriptions
     */
    public TableColumn<Appointment, String> contactApptDescCol;
    /**
     * Contains appointment locations
     */
    public TableColumn<Appointment, String> contactApptLocationCol;
    /**
     * Contains appointment dates
     */
    public TableColumn<Appointment, LocalDate> contactApptDateCol;
    /**
     * Contains appointment start times
     */
    public TableColumn<Appointment, String> contactApptStartCol;
    /**
     * Contains appointment end times
     */
    public TableColumn<Appointment, String> contactApptEndCol;
    /**
     * Contains appointment customer names
     */
    public TableColumn<Appointment, String> contactApptCustomerCol;
    /**
     * Contains appointment user names
     */
    public TableColumn<Appointment, String> contactApptUserCol;
    /**
     * Customer name options
     */
    public ComboBox<Customer> customerBox;
    /**
     * Table of appointments filtered by customer name
     */
    public TableView<Appointment> customerApptTable;
    /**
     * Contains appointment ids
     */
    public TableColumn<Appointment, Integer> custApptIDCol;
    /**
     * Contains appointment titles
     */
    public TableColumn<Appointment, String> custApptTitleCol;
    /**
     * Contains appointment types
     */
    public TableColumn<Appointment, String> custApptTypeCol;
    /**
     * Contains appointment descriptions
     */
    public TableColumn<Appointment, String> custApptDescCol;
    /**
     * Contains appointment locations
     */
    public TableColumn<Appointment, String> custApptLocationCol;
    /**
     * Contains appointment contact names
     */
    public TableColumn<Appointment, String> custApptContactCol;
    /**
     * Contains appointment dates
     */
    public TableColumn<Appointment, LocalDate> custApptDateCol;
    /**
     * Contains appointment start times
     */
    public TableColumn<Appointment, String> custApptStartCol;
    /**
     * Contains appointment end times
     */
    public TableColumn<Appointment, String> custApptEndCol;
    /**
     * Contains appointment user names
     */
    public TableColumn<Appointment, String> custApptUserCol;
    // endregion

    /**
     * Initialize the main menu
     * <p>LAMBDA JUSTIFICATIONS: The lambda function used for the first-level division name
     * adds readability by replacing the division id in the table view.</p>
     * <p>Every appointment table uses a lambda function to customize the display of their start and end times.
     * The start and end time lambdas add the current time zone to the entries to be more descriptive.</p>
     * <p>The month drop down menu in the first report uses lambda functions to change how the month is displayed.
     * Originally, the months are displayed in all capital letters, but now, only the first letter is capitalized.</p>
     * @param url the URL
     * @param resourceBundle the resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Initialize the customer table
        customerTable.setItems(DBCustomer.getAllCustomers());
        customerNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        countryCol.setCellValueFactory(new PropertyValueFactory<>("country"));
        divisionCol.setCellValueFactory(c -> new SimpleStringProperty(DBCustomer.getDivision(
                                                                          c.getValue().getDivisionId()).getDivision()));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        postalCodeCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        phoneNumberCol.setCellValueFactory(new PropertyValueFactory<>("phone"));

        //Initialize the appointments table
        appointmentTable.setItems(DBAppointment.getAllAppointments());
        appointmentIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        appointmentTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        appointmentDescCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        appointmentLocationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        appointmentTypeCol.setCellValueFactory((new PropertyValueFactory<>("type")));
        appointmentDateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        appointmentStartCol.setCellValueFactory(s -> new SimpleStringProperty(s.getValue().getStart().toString() + " " +
                ZoneId.systemDefault().getDisplayName(TextStyle.SHORT, Locale.ENGLISH)));
        appointmentEndCol.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().getEnd().toString() + " " +
                ZoneId.systemDefault().getDisplayName(TextStyle.SHORT, Locale.ENGLISH)));
        appointmentCustomerCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        appointmentUserCol.setCellValueFactory(new PropertyValueFactory<>("userName"));
        appointmentContactCol.setCellValueFactory(new PropertyValueFactory<>("contactName"));

        // Initialize the appointment types drop down in the first report
        appointmentTypeBox.setItems(DBReport.getAllTypes());

        // Initialize the months drop down in the first report
        appointmentMonthBox.getItems().addAll(Month.values());
        appointmentMonthBox.setVisibleRowCount(6);

        // Display the name of each month option in the drop down
        appointmentMonthBox.setCellFactory(m -> new ListCell<>(){
            @Override
            protected void updateItem(Month month, boolean isEmpty)
            {
                super.updateItem(month, isEmpty);
                if(isEmpty || month == null) setText(null);
                else setText(month.getDisplayName(TextStyle.FULL, Locale.getDefault()));
            }
        });

        // Display the name of the selected month
        appointmentMonthBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(Month month){
                if(month == null) return "";
                else
                    return month.getDisplayName(TextStyle.FULL, Locale.getDefault());
            }

            @Override
            public Month fromString(String string)
            {
                return null;
            }
        });

        // Initialize the drop down of contact options
        contactBox.setItems(DBContact.getAllContacts());

        // Initialize the contact appointment report table
        contactApptIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        contactApptTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        contactApptDescCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        contactApptLocationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        contactApptTypeCol.setCellValueFactory((new PropertyValueFactory<>("type")));
        contactApptDateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        contactApptStartCol.setCellValueFactory(s -> new SimpleStringProperty(s.getValue().getStart().toString() + " " +
                ZoneId.systemDefault().getDisplayName(TextStyle.SHORT, Locale.ENGLISH)));
        contactApptEndCol.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().getEnd().toString() + " " +
                ZoneId.systemDefault().getDisplayName(TextStyle.SHORT, Locale.ENGLISH)));
        contactApptCustomerCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        contactApptUserCol.setCellValueFactory(new PropertyValueFactory<>("userName"));

        // Initialize the drop down of customer options
        customerBox.setItems(DBCustomer.getAllCustomers());

        // Initialize the customer appointment table
        custApptIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        custApptTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        custApptDescCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        custApptLocationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        custApptTypeCol.setCellValueFactory((new PropertyValueFactory<>("type")));
        custApptDateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        custApptStartCol.setCellValueFactory(s -> new SimpleStringProperty(s.getValue().getStart().toString() + " " +
                ZoneId.systemDefault().getDisplayName(TextStyle.SHORT, Locale.ENGLISH)));
        custApptEndCol.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().getEnd().toString() + " " +
                ZoneId.systemDefault().getDisplayName(TextStyle.SHORT, Locale.ENGLISH)));
        custApptContactCol.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        custApptUserCol.setCellValueFactory(new PropertyValueFactory<>("userName"));
    }

    /**
     * Load and display the customer add form
     * @param actionEvent the handled add customer event
     * @throws IOException when the customer add form fails to load
     */
    public void onCustomerAdd(ActionEvent actionEvent) throws IOException {
        Parent root = new FXMLLoader(getClass().getResource("/Views/addCustomerView.fxml")).load();
        Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Load and display the customer modify form
     * @param actionEvent the handled modify event
     * @throws IOException when the customer modify form fails to load
     */
    public void onCustomerModify(ActionEvent actionEvent) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/modifyCustomerView.fxml"));
        Parent root = loader.load();
        ModifyCustomerController controller = loader.getController();
        loader.setController(controller);

        try {
            Customer selectedCustomer = customerTable.getSelectionModel().getSelectedItem();

            if(selectedCustomer == null)
                throw new Exception("A customer must be selected for modification");

            controller.setCustomerData(selectedCustomer);
        } catch(Exception e)
        {
            Utilities.displayErrorMessage(e.getMessage());
            return;
        }

        Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Attempt a customer record deletion
     * @param actionEvent the handled customer delete event
     */
    public void onCustomerDelete(ActionEvent actionEvent) {
        try{
            // Get the selected customer
            Customer selectedCustomer = customerTable.getSelectionModel().getSelectedItem();

            if(selectedCustomer == null)
                throw new Exception("A customer must be selected for deletion");

            // Ask for confirmation before deleting the customer and their associated appointments
            if(Utilities.displayConfirmationMessage("Are you sure you want to permanently delete " + selectedCustomer.getName() +
                                                    " and their associated appointments?"))
            {
                // Delete the selected customer and their associated appointments
                Utilities.displayMessage("Customer, " + selectedCustomer.getName() + ", was deleted.");
                DBCustomer.deleteCustomer(selectedCustomer);

                // Update the customer and appointment tables
                customerTable.setItems(DBCustomer.getAllCustomers());
                appointmentTable.setItems(DBAppointment.getAllAppointments());
                customerBox.setItems(DBCustomer.getAllCustomers());
                customerApptTable.setItems(null);
            }
        } catch(Exception e)
        {
            Utilities.displayErrorMessage(e.getMessage());
        }
    }

    /**
     * Load and display the add appointment form
     * @param actionEvent the handled add appointment event
     * @throws IOException when the add appointment form fails to load
     */
    public void OnAddAppointment(ActionEvent actionEvent) throws IOException {
        Parent root = new FXMLLoader(getClass().getResource("/Views/addAppointmentView.fxml")).load();
        Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Load and display the modify appointment form
     * @param actionEvent the handled modify appointment event
     * @throws IOException when the modify appointment form fails to load
     */
    public void OnModifyAppointment(ActionEvent actionEvent) throws IOException {
        // Load the modify appointment form
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/modifyAppointmentView.fxml"));
        Parent root = loader.load();
        ModifyAppointmentController controller = loader.getController();
        loader.setController(controller);

        try{
            // Get the selected appointment
            Appointment selectedAppointment = appointmentTable.getSelectionModel().getSelectedItem();

            if(selectedAppointment == null)
                throw new Exception("An appointment must be selected for modification");

            // Populate the form with the selected appointment's information
            controller.setAppointmentData(selectedAppointment);
        } catch(Exception e)
        {
            Utilities.displayErrorMessage(e.getMessage());
            return;
        }

        // Display the modify appointment form
        Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Delete a selected appointment
     * @param actionEvent the handled delete appointment event
     */
    public void OnDeleteAppointment(ActionEvent actionEvent) {
        try{
            // Get the selected appointment
            Appointment selectedAppointment = appointmentTable.getSelectionModel().getSelectedItem();

            if(selectedAppointment == null)
                throw new Exception("An appointment must be selected for deletion");

            // Ask for confirmation to delete the selected appointment
            if(Utilities.displayConfirmationMessage("Are you sure you want to cancel the " + selectedAppointment.getType() +
                                                    " appointment no." + selectedAppointment.getId()))
            {
                // Delete the selected appointment
                DBAppointment.deleteAppointment(selectedAppointment);
                Utilities.displayMessage(selectedAppointment.getType() + " appointment no." + selectedAppointment.getId() +
                        " has been cancelled");

                // Update the appointment table
                appointmentTable.setItems(DBAppointment.getAllAppointments());
            }
        } catch(Exception e)
        {
            Utilities.displayErrorMessage(e.getMessage());
        }
    }

    /**
     * Return to the appointment tab in the main menu
     */
    public void returnToAppointmentTab()
    {
        mainMenuTabPane.getSelectionModel().select(appointmentTab);
    }

    /**
     * Display all appointments in the appointments table
     * @param actionEvent the handled filter event
     */
    public void filterByAll(ActionEvent actionEvent) {
        appointmentTable.setItems(DBAppointment.getAllAppointments());
    }

    /**
     * Display the appointments filtered by the current month in the appointments table
     * @param actionEvent the handled filter event
     */
    public void filterByMonth(ActionEvent actionEvent) {
        LocalDate monthStart = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth());
        LocalDate monthEnd = LocalDate.now().with(TemporalAdjusters.lastDayOfMonth());

        appointmentTable.setItems(DBAppointment.getFilteredAppointments(monthStart, monthEnd));
    }

    /**
     * Display the appointments filtered by the current week in the appointments table
     * @param actionEvent the handled filter event
     */
    public void filterByWeek(ActionEvent actionEvent) {
        LocalDate weekStart = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));
        LocalDate weekEnd = LocalDate.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.SATURDAY));

        appointmentTable.setItems(DBAppointment.getFilteredAppointments(weekStart, weekEnd));
    }

    /**
     * Display the total number of appointments of a specific type in a given month
     * @param actionEvent the handled total event
     */
    public void OnGetAppointmentTotals(ActionEvent actionEvent) {
        String type = appointmentTypeBox.getSelectionModel().getSelectedItem();
        Month month = appointmentMonthBox.getSelectionModel().getSelectedItem();

        if(type == null || month == null)
            return;

        totalAppointments.setText("Total: " + DBReport.getAppointmentTotal(type, month));
    }

    /**
     * Display the appointments associated with the selected contact
     * @param actionEvent the handled select event
     */
    public void OnSelectContact(ActionEvent actionEvent) {
        Contact selectedContact = contactBox.getSelectionModel().getSelectedItem();

        if(selectedContact == null)
            return;

        contactApptTable.setItems(DBReport.getContactAppointments(selectedContact));
    }

    /**
     * Display the appointments associated with the selected customer
     * @param actionEvent the handled select event
     */
    public void OnSelectCustomer(ActionEvent actionEvent) {
        Customer selectedCustomer = customerBox.getSelectionModel().getSelectedItem();

        if(selectedCustomer == null)
            return;

        customerApptTable.setItems(DBReport.getCustomerAppointments(selectedCustomer));
    }
}
