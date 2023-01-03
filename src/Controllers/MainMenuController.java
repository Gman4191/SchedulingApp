package Controllers;

import DAO.DBCustomer;
import Models.Customer;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {

    public TableView<Customer> customerTable;
    public TableColumn<Customer, String> customerNameCol;
    public TableColumn<Customer, String> divisionCol;
    public TableColumn<Customer, String> addressCol;
    public TableColumn<Customer, String> postalCodeCol;
    public TableColumn<Customer, String> phoneNumberCol;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customerTable.setItems(DBCustomer.getAllCustomers());
        customerNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        postalCodeCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        phoneNumberCol.setCellValueFactory(new PropertyValueFactory<>("phone"));

        divisionCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Customer, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Customer, String> c) {
                String division = DBCustomer.getDivision(c.getValue().getDivisionId());
                return new SimpleStringProperty(division);
            }
        });
    }
}
