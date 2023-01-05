package Controllers;

import DAO.DBCustomer;
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
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {

    public TableView<Customer> customerTable;
    public TableColumn<Customer, String> customerNameCol;
    public TableColumn<Customer, String> divisionCol;
    public TableColumn<Customer, String> addressCol;
    public TableColumn<Customer, String> postalCodeCol;
    public TableColumn<Customer, String> phoneNumberCol;
    public TableColumn<Customer, String> countryCol;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customerTable.setItems(DBCustomer.getAllCustomers());
        customerNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        countryCol.setCellValueFactory(new PropertyValueFactory<>("country"));
        divisionCol.setCellValueFactory(c -> new SimpleStringProperty(DBCustomer.getDivision(
                                                                          c.getValue().getDivisionId()).getDivision()));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        postalCodeCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        phoneNumberCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
    }

    public void onCustomerAdd(ActionEvent actionEvent) throws IOException {
        Parent root = new FXMLLoader(getClass().getResource("/Views/addCustomerView.fxml")).load();
        Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

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

    public void onCustomerDelete(ActionEvent actionEvent) {
        try{
            Customer selectedCustomer = customerTable.getSelectionModel().getSelectedItem();

            if(selectedCustomer == null)
                throw new Exception("A customer must be selected for deletion");

            if(Utilities.displayConfirmationMessage("Are you sure you want to delete customer: " + selectedCustomer.getName() +
                                                    " and their associated appointments?"))
            {
                Utilities.displayMessage("Customer, " + selectedCustomer.getName() + ", was deleted.");
                DBCustomer.deleteCustomer(selectedCustomer);
            }
        } catch(Exception e)
        {
            Utilities.displayErrorMessage(e.getMessage());
        }

    }
}
