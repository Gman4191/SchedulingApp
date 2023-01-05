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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        countryBox.setItems(DBCustomer.getAllCountries());
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

        Country selectedCountry = DBCustomer.getCountry(selectedCustomer.getCountryId());
        countryBox.getSelectionModel().select(selectedCountry);

        divisionBox.setItems(DBCustomer.getDivisions(selectedCountry));
        divisionBox.getSelectionModel().select(DBCustomer.getDivision(selectedCustomer.getDivisionId()));
    }

    /**
     * Save the modified customer
     * @param actionEvent the handled save customer event
     * @throws IOException when the main menu fails to load
     */
    public void OnSave(ActionEvent actionEvent) throws IOException {
        if(!validateData())
            return;

        Customer updatedCustomer = new Customer(Integer.parseInt(idField.getText()), nameField.getText(), addressField.getText(), postalCodeField.getText(),
                                                phoneNumberField.getText(), divisionBox.getSelectionModel().getSelectedItem().getId(),
                                                countryBox.getSelectionModel().getSelectedItem().getId(),
                                                countryBox.getSelectionModel().getSelectedItem().getCountry());
        DBCustomer.updateCustomer(updatedCustomer);

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
        } catch(Exception e)
        {
            Utilities.displayErrorMessage(e.getMessage());
            return false;
        }
        return true;
    }
}
