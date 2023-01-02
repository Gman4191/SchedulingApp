package Controllers;

import DAO.DBLogin;
import Utility.Language;
import Utility.Utilities;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.ResultSet;
import java.util.Locale;
import java.util.ResourceBundle;

import static javafx.collections.FXCollections.observableArrayList;

public class LoginController implements Initializable {
    public Button loginButton;
    public ComboBox<Language> languageBox;
    public TextField passwordField;
    public TextField userNameField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DBLogin.selectUsers();
        ObservableList<Language> languages = observableArrayList();
        languages.addAll(new Language("en"), new Language("fr"));
        languageBox.setItems(languages);
        languageBox.setValue(languages.get(0));
    }


    public void OnLogin(ActionEvent actionEvent) {
        try{
            DBLogin.verifyUser(userNameField.getText(), passwordField.getText());
        } catch(Exception e)
        {
            Utilities.displayErrorMessage(e.getMessage());
        }
    }
}
