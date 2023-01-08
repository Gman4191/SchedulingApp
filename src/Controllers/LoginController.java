package Controllers;

import DAO.DBLogin;
import Models.Appointment;
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
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.ResourceBundle;

import static javafx.collections.FXCollections.observableArrayList;

public class LoginController implements Initializable {

    /**
     * The login button
     */
    public Button loginButton;
    /**
     * Contains the user's password
     */
    public TextField passwordField;
    /**
     * Contains the user's name
     */
    public TextField userNameField;
    /**
     * Drop down of language options
     */
    public ComboBox<Language> languageBox;
    /**
     * Languages to translate the page between
     */
    private final ObservableList<Language> languages = observableArrayList();
    /**
     * Login page title
     */
    public Label loginLabel;
    /**
     * Label describing the user name text field
     */
    public Label userNameLabel;
    /**
     * Label describing the password text field
     */
    public Label passwordLabel;
    /**
     * Label noting the user's location
     */
    public Label locationLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        languages.addAll(new Language("en"), new Language("fr"));
        languageBox.setItems(languages);
        languageBox.setValue(languages.get(0));
        locationLabel.setText(ZoneId.systemDefault().getDisplayName(TextStyle.FULL, Locale.ENGLISH));
    }

    /**
     * Attempt to login to the scheduling application
     * @param actionEvent the handled login event
     * @throws IOException when the main menu page fails to load
     */
    public void OnLogin(ActionEvent actionEvent) throws IOException {
        try{
            int id = DBLogin.verifyUser(userNameField.getText(), passwordField.getText());
            User.setUserName(userNameField.getText());
            User.setId(id);
            checkUpcomingAppointments();
        } catch(Exception e)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR, Language.getLanguage().getString(e.getMessage()));
            alert.setHeaderText(Language.getLanguage().getString("error"));
            alert.show();
            return;
        }

        Parent root = new FXMLLoader(getClass().getResource("../Views/mainMenuView.fxml")).load();
        Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Update the language used in the login page
     * @param actionEvent
     */
    public void OnUpdateLanguage(ActionEvent actionEvent) {
        Language language = languageBox.getSelectionModel().getSelectedItem();
        language.setLanguage();

        loginLabel.setText(Language.getLanguage().getString("login"));
        loginButton.setText(Language.getLanguage().getString("login"));
        userNameLabel.setText(Language.getLanguage().getString("userName"));
        userNameField.setPromptText(Language.getLanguage().getString("userNameField"));
        passwordLabel.setText(Language.getLanguage().getString("password"));
        passwordField.setPromptText(Language.getLanguage().getString("passwordField"));
        locationLabel.setText(ZoneId.systemDefault().getDisplayName(TextStyle.FULL, Language.getLanguage().getLocale()));
    }

    public void checkUpcomingAppointments()
    {
        Appointment a = DBLogin.getUpcomingAppointment(LocalDateTime.now());
        if(a != null)
        {
            Utilities.displayMessage(Language.getLanguage().getString("a1") + " " + a.getId() + " " +
                    Language.getLanguage().getString("a2") + " " + a.getDate() +
                    Language.getLanguage().getString("a3") + " " + a.getStart());
        } else
        {
            Utilities.displayMessage(Language.getLanguage().getString("noAppointments"));
        }
    }
}
