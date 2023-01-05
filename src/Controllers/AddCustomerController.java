package Controllers;

import DAO.DBCustomer;
import Models.Country;
import Models.Customer;
import Models.FirstLevelDivision;
import Utility.Utilities;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddCustomerController implements Initializable {

    /**
     * Contains the customer phone number
     */
    public TextField phoneNumberField;
    /**
     * Contains the customer postal code
     */
    public TextField postalCodeField;
    /**
     * Contains the customer address
     */
    public TextField addressField;
    /**
     * Contains the customer name
     */
    public TextField nameField;
    /**
     * Drop down of countries
     */
    public ComboBox<Country> countryBox;

    /**
     * Drop down of first-level divisions
     */
    public ComboBox<FirstLevelDivision> divisionBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        countryBox.setItems(DBCustomer.getAllCountries());
    }

    /**
     * Save the added customer to the database
     * @param actionEvent the handled save event
     * @throws IOException when the main menu fails to load
     */
    public void OnSave(ActionEvent actionEvent) throws IOException {
        // Validate the entered data
        if(!validateData())
            return;

        // Add a new customer
        Customer newCustomer = new Customer(0, nameField.getText(), addressField.getText(), postalCodeField.getText(),
                                            phoneNumberField.getText(), divisionBox.getSelectionModel().getSelectedItem().getId(),
                                            countryBox.getSelectionModel().getSelectedItem().getId(),
                                            countryBox.getSelectionModel().getSelectedItem().getCountry());
        DBCustomer.addCustomer(newCustomer);

        // Return to the main menu
        Parent root = new FXMLLoader(getClass().getResource("/Views/mainMenuView.fxml")).load();
        Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Cancel adding the customer to the database
     * @param actionEvent the handled cancel event
     * @throws IOException when the main menu fails to load
     */
    public void OnCancel(ActionEvent actionEvent) throws IOException{
        Parent root = new FXMLLoader(getClass().getResource("/Views/mainMenuView.fxml")).load();
        Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Validate the entered customer data
     * @return true if the entered data is valid
     */
    public boolean validateData()
    {
        try
        {
            if(nameField.getText().isEmpty())
                throw new Exception("Customer name can not be blank");

            if(addressField.getText().isEmpty())
                throw new Exception("Address can not be blank");

            if(postalCodeField.getText().isEmpty())
                throw new Exception("Postal code can not be blank");

            if(phoneNumberField.getText().isEmpty())
                throw new Exception("Phone number can not be blank");

            if(countryBox.getSelectionModel().isEmpty())
                throw new Exception("A Country must be selected");

            if(divisionBox.getSelectionModel().isEmpty())
                throw new Exception("A Division must be selected");
        } catch(Exception e)
        {
            Utilities.displayErrorMessage(e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * Update the first-level division options when a country is selected
     * @param actionEvent the handled selection event
     */
    public void OnSelectCountry(ActionEvent actionEvent) {
        divisionBox.setItems(DBCustomer.getDivisions(countryBox.getSelectionModel().getSelectedItem()));
    }
}
