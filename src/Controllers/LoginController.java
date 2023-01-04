package Controllers;

import DAO.DBLogin;
import Models.User;
import Utility.Language;
import Utility.Utilities;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.ZoneId;
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

        locationLabel.setText(ZoneId.systemDefault().toString());
    }


    public void OnLogin(ActionEvent actionEvent) throws IOException {
        try{
            DBLogin.verifyUser(userNameField.getText(), passwordField.getText());
            User.setUserName(userNameField.getText());
        } catch(Exception e)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR, Language.getLanguage().getString(e.getMessage()));
            alert.setHeaderText(Language.getLanguage().getString("error"));
            alert.show();
            return;
        }

        Parent root = new FXMLLoader(getClass().getResource("../Views/MainMenuView.fxml")).load();
        Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
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
