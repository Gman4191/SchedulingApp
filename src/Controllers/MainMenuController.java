package Controllers;

import DAO.DBAppointment;
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

    public TableView<Appointment> appointmentTable;
    public TableColumn<Appointment, Integer> appointmentIdCol;
    public TableColumn<Appointment, String> appointmentTitleCol;
    public TableColumn<Appointment, String> appointmentTypeCol;
    public TableColumn<Appointment, String> appointmentDescCol;
    public TableColumn<Appointment, String> appointmentLocationCol;
    public TableColumn<Appointment, String> appointmentContactCol;
    public TableColumn<Appointment, LocalDate> appointmentDateCol;
    public TableColumn<Appointment, String> appointmentStartCol;
    public TableColumn<Appointment, String> appointmentEndCol;
    public TableColumn<Appointment, String> appointmentCustomerCol;
    public TableColumn<Appointment, String> appointmentUserCol;
    public Tab appointmentTab;
    public TabPane tabPane;
    public RadioButton allButton;
    public RadioButton monthButton;
    public RadioButton weekButton;
    public ComboBox<String> appointmentTypeBox;
    public ComboBox<Month> appointmentMonthBox;
    public Label totalAppointments;
    public TableView<Appointment> contactApptTable;
    public TableColumn<Appointment, Integer> contactApptIdCol;
    public TableColumn<Appointment, String> contactApptTitleCol;
    public TableColumn<Appointment, String> contactApptTypeCol;
    public TableColumn<Appointment, String> contactApptDescCol;
    public TableColumn<Appointment, String> contactApptLocationCol;
    public TableColumn<Appointment, LocalDate> contactApptDateCol;
    public TableColumn<Appointment, String> contactApptStartCol;
    public TableColumn<Appointment, String> contactApptEndCol;
    public TableColumn<Appointment, String> contactApptCustomerCol;
    public TableColumn<Appointment, String> contactApptUserCol;
    public ComboBox<Contact> contactBox;
    public TableView<Appointment> customerApptTable;
    public TableColumn<Appointment, Integer> custApptIDCol;
    public TableColumn<Appointment, String> custApptTitleCol;
    public TableColumn<Appointment, String> custApptTypeCol;
    public TableColumn<Appointment, String> custApptDescCol;
    public TableColumn<Appointment, String> custApptLocationCol;
    public TableColumn<Appointment, String> custApptContactCol;
    public TableColumn<Appointment, LocalDate> custApptDateCol;
    public TableColumn<Appointment, String> custApptStartCol;
    public TableColumn<Appointment, String> custApptEndCol;
    public TableColumn<Appointment, String> custApptUserCol;
    public ComboBox<Customer> customerBox;

    /**
     * Initialize the main menu
     * <p>LAMBDA JUSTIFICATION: The lambda function used for the first-level division name
     * adds readability by replacing the division id in the table view.</p>
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
        appointmentMonthBox.setCellFactory(m -> new ListCell<>(){
            @Override
            protected void updateItem(Month month, boolean isEmpty)
            {
                super.updateItem(month, isEmpty);
                if(isEmpty || month == null) setText(null);
                else setText(month.getDisplayName(TextStyle.FULL, Locale.getDefault()));
            }
        });

        appointmentMonthBox.setConverter(new StringConverter<Month>() {
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
        contactBox.setItems(DBAppointment.getAllContacts());

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
            Customer selectedCustomer = customerTable.getSelectionModel().getSelectedItem();

            if(selectedCustomer == null)
                throw new Exception("A customer must be selected for deletion");

            if(Utilities.displayConfirmationMessage("Are you sure you want to permanently delete " + selectedCustomer.getName() +
                                                    " and their associated appointments?"))
            {
                Utilities.displayMessage("Customer, " + selectedCustomer.getName() + ", was deleted.");
                DBCustomer.deleteCustomer(selectedCustomer);
                customerTable.setItems(DBCustomer.getAllCustomers());
                appointmentTable.setItems(DBAppointment.getAllAppointments());
            }
        } catch(Exception e)
        {
            Utilities.displayErrorMessage(e.getMessage());
        }
    }

    public void OnAddAppointment(ActionEvent actionEvent) throws IOException {
        Parent root = new FXMLLoader(getClass().getResource("/Views/addAppointmentView.fxml")).load();
        Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void OnModifyAppointment(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/modifyAppointmentView.fxml"));
        Parent root = loader.load();
        ModifyAppointmentController controller = loader.getController();
        loader.setController(controller);

        try{
            Appointment selectedAppointment = appointmentTable.getSelectionModel().getSelectedItem();

            if(selectedAppointment == null)
                throw new Exception("An appointment must be selected for modification");

            controller.setAppointmentData(selectedAppointment);
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
    
    public void returnToAppointmentTab()
    {
        tabPane.getSelectionModel().select(appointmentTab);
    }

    public void OnDeleteAppointment(ActionEvent actionEvent) {
        try{
            Appointment selectedAppointment = appointmentTable.getSelectionModel().getSelectedItem();

            if(selectedAppointment == null)
                throw new Exception("An appointment must be selected for deletion");

            if(Utilities.displayConfirmationMessage("Are you sure you want to cancel the " + selectedAppointment.getType() +
                                                    " appointment no." + selectedAppointment.getId()))
            {
                DBAppointment.deleteAppointment(selectedAppointment);
                Utilities.displayMessage(selectedAppointment.getType() + " appointment no." + selectedAppointment.getId() +
                        " has been cancelled");
                appointmentTable.setItems(DBAppointment.getAllAppointments());
            }
        } catch(Exception e)
        {
            Utilities.displayErrorMessage(e.getMessage());
        }
    }

    public void filterByAll(ActionEvent actionEvent) {
        appointmentTable.setItems(DBAppointment.getAllAppointments());
    }

    public void filterByMonth(ActionEvent actionEvent) {
        LocalDate monthStart = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth());
        LocalDate monthEnd = LocalDate.now().with(TemporalAdjusters.lastDayOfMonth());

        appointmentTable.setItems(DBAppointment.getFilteredAppointments(monthStart, monthEnd));
    }

    public void filterByWeek(ActionEvent actionEvent) {
        LocalDate weekStart = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));
        LocalDate weekEnd = LocalDate.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.SATURDAY));

        appointmentTable.setItems(DBAppointment.getFilteredAppointments(weekStart, weekEnd));
    }

    public void OnGetAppointmentTotals(ActionEvent actionEvent) {
        String type = appointmentTypeBox.getSelectionModel().getSelectedItem();
        Month month = appointmentMonthBox.getSelectionModel().getSelectedItem();

        if(type == null || month == null)
            return;

        totalAppointments.setText("Total: " + DBReport.getAppointmentTotal(type, month));
    }

    public void OnSelectContact(ActionEvent actionEvent) {
        Contact selectedContact = contactBox.getSelectionModel().getSelectedItem();

        if(selectedContact == null)
            return;

        contactApptTable.setItems(DBReport.getContactAppointments(selectedContact));
    }

    public void OnSelectCustomer(ActionEvent actionEvent) {
        Customer selectedCustomer = customerBox.getSelectionModel().getSelectedItem();

        if(selectedCustomer == null)
            return;

        customerApptTable.setItems(DBReport.getCustomerAppointments(selectedCustomer));
    }
}
