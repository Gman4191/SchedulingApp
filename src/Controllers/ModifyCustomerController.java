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

    public TextField idField;
    public TextField nameField;
    public TextField addressField;
    public TextField postalCodeField;
    public TextField phoneNumberField;
    public ComboBox<Country> countryBox;
    public ComboBox<FirstLevelDivision> divisionBox;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        countryBox.setItems(DBCustomer.getAllCountries());
    }

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
    public void OnSave(ActionEvent actionEvent) throws IOException {
        if(!validateData())
            return;



        Parent root = new FXMLLoader(getClass().getResource("/Views/MainMenuView.fxml")).load();
        Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void OnCancel(ActionEvent actionEvent) throws IOException{
        Parent root = new FXMLLoader(getClass().getResource("/Views/MainMenuView.fxml")).load();
        Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

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
