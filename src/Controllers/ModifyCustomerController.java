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

public class ModifyCustomerController implements Initializable {

    /**
     * Contains the customer id
     */
    public TextField idField;
    /**
     * Contains the customer name
     */
    public TextField nameField;
    /**
     * Contains the customer address
     */
    public TextField addressField;
    /**
     * Contains the customer postal code
     */
    public TextField postalCodeField;
    /**
     * Contains the customer phone number
     */
    public TextField phoneNumberField;
    /**
     * Drop down of country options
     */
    public ComboBox<Country> countryBox;
    /**
     * Drop down of first-level division options
     */
    public ComboBox<FirstLevelDivision> divisionBox;

    /**
     * Initialize the UI components
     * @param url the URL
     * @param resourceBundle the resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    /**
     * Populate the page with the selected customer's information
     * @param selectedCustomer the selected customer
     */
    public void setCustomerData(Customer selectedCustomer)
    {
        idField.setText(((Integer)selectedCustomer.getId()).toString());
        nameField.setText(selectedCustomer.getName());
        addressField.setText(selectedCustomer.getAddress());
        postalCodeField.setText(selectedCustomer.getPostalCode());
        phoneNumberField.setText(selectedCustomer.getPhone());

        countryBox.setItems(DBCustomer.getAllCountries());
        int i = 0;
        for(; i < countryBox.getItems().size(); i++)
            if(countryBox.getItems().get(i).getId() == selectedCustomer.getCountryId())
                break;
        countryBox.getSelectionModel().select(i);

        divisionBox.setItems(DBCustomer.getDivisions(countryBox.getSelectionModel().getSelectedItem()));
        for(i = 0; i < divisionBox.getItems().size(); i++)
            if(divisionBox.getItems().get(i).getId() == selectedCustomer.getDivisionId())
                break;
        divisionBox.getSelectionModel().select(i);
    }

    /**
     * Save the modified customer
     * @param actionEvent the handled save customer event
     * @throws IOException when the main menu fails to load
     */
    public void OnSave(ActionEvent actionEvent) throws IOException {
        // Validate the modified customer information
        if(!validateData())
            return;

        // Create an updated customer
        Customer updatedCustomer = new Customer(Integer.parseInt(idField.getText()), nameField.getText(), addressField.getText(), postalCodeField.getText(),
                                                phoneNumberField.getText(), divisionBox.getSelectionModel().getSelectedItem().getId(),
                                                countryBox.getSelectionModel().getSelectedItem().getId(),
                                                countryBox.getSelectionModel().getSelectedItem().getCountry());

        // Update the database with the modified customer information
        DBCustomer.updateCustomer(updatedCustomer);

        // Return to the main menu
        Parent root = new FXMLLoader(getClass().getResource("/Views/mainMenuView.fxml")).load();
        Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Cancel modifying the selected customer
     * @param actionEvent the handled cancel modify event
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
     * Validate the entered customer information
     * @return true if the entered information is valid
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
        divisionBox.setValue(null);
    }
}
