package Controllers;

import DAO.DBLogin;
import Utility.Language;
import Utility.Utilities;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

import static javafx.collections.FXCollections.observableArrayList;

public class LoginController implements Initializable {
    public Button loginButton;
    public ComboBox<Language> languageBox;
    public TextField passwordField;
    public TextField userNameField;
    private final ObservableList<Language> languages = observableArrayList();
    public Label loginLabel;
    public Label userNameLabel;
    public Label passwordLabel;
    public Label locationLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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

    public void OnUpdateLanguage(ActionEvent actionEvent) {
        Language language = languageBox.getSelectionModel().getSelectedItem();
        language.setLanguage();

        loginLabel.setText(Language.getLanguage().getString("login"));
        loginButton.setText(Language.getLanguage().getString("login"));
        userNameLabel.setText(Language.getLanguage().getString("userName"));
        userNameField.setPromptText(Language.getLanguage().getString("userNameField"));
        passwordLabel.setText(Language.getLanguage().getString("password"));
        passwordField.setPromptText(Language.getLanguage().getString("passwordField"));
    }
}
