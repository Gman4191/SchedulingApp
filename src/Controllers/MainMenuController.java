package Controllers;

import DAO.DBAppointment;
import DAO.DBCustomer;
import Models.Appointment;
import Models.Customer;
import Utility.Utilities;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
    public TableColumn<Appointment, LocalTime> appointmentStartCol;
    public TableColumn<Appointment, LocalTime> appointmentEndCol;
    public TableColumn<Appointment, String> appointmentCustomerCol;
    public TableColumn<Appointment, String> appointmentUserCol;

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
        appointmentStartCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        appointmentEndCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        appointmentCustomerCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        appointmentUserCol.setCellValueFactory(new PropertyValueFactory<>("userName"));
        appointmentContactCol.setCellValueFactory(new PropertyValueFactory<>("contactName"));
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
            }
        } catch(Exception e)
        {
            Utilities.displayErrorMessage(e.getMessage());
        }
    }
}
