package Controllers;

import DAO.DBLogin;
import Models.Appointment;
import Models.User;
import Utility.Utilities;
import com.sun.glass.ui.ClipboardAssistance;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    /**
     * The format of the local date and time
     */
    public final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:ss");
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
    /**
     * Resource component that guides language translation
     */
    private final static ResourceBundle language = ResourceBundle.getBundle("Utility/Translation", Locale.getDefault());

    /**
     * Initialize the UI components
     * @param url the URL
     * @param resourceBundle the resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loginLabel.setText(language.getString("login"));
        loginButton.setText(language.getString("login"));
        userNameLabel.setText(language.getString("userName"));
        userNameField.setPromptText(language.getString("userNameField"));
        passwordLabel.setText(language.getString("password"));
        passwordField.setPromptText(language.getString("passwordField"));
        locationLabel.setText(ZoneId.systemDefault().getId());
    }

    /**
     * Attempt to login to the scheduling application
     * @param actionEvent the handled login event
     * @throws IOException when the main menu page fails to load
     */
    public void OnLogin(ActionEvent actionEvent) throws IOException {

        try{
            // Get the user id if the user name and password is valid
            int id = DBLogin.verifyUser(userNameField.getText(), passwordField.getText());
            User.setUserName(userNameField.getText());
            User.setId(id);

            // Check for upcoming appointments
            checkUpcomingAppointments();

            // Record the successful user login attempt
            recordLoginAttempt(true);
        } catch(Exception e)
        {
            // Record the unsuccessful user login attempt
            recordLoginAttempt(false);

            // Notify the user of the unsuccessful login attempt
            Alert alert = new Alert(Alert.AlertType.ERROR, language.getString(e.getMessage()));
            alert.setHeaderText(language.getString("error"));
            alert.show();
            return;
        }

        // Display the main menu
        Parent root = new FXMLLoader(getClass().getResource("../Views/mainMenuView.fxml")).load();
        Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Check for upcoming appointments
     */
    public void checkUpcomingAppointments()
    {
        Appointment a = DBLogin.getUpcomingAppointment(LocalDateTime.now());
        if(a != null)
        {
            Utilities.displayMessage(language.getString("a1") + " " + a.getId() + " " +
                    language.getString("a2") + " " + a.getDate() +
                    language.getString("a3") + " " + a.getStart());
        } else
        {
            Utilities.displayMessage(language.getString("noAppointments"));
        }
    }

    /**
     * Record all login attempts
     * @param loginSuccess the success value of the login attempt
     */
    public void recordLoginAttempt(boolean loginSuccess)
    {
        FileWriter output = null;
        String log;

        try {
            // Open the file in append mode
            output = new FileWriter("login_activity.txt", true);

            // Create the log entry based on whether the login attempt was successful or not
            if(loginSuccess) {
                log = "User " + userNameField.getText() + " made a successful login attempt at " + " " +
                      Utilities.changeTimeZone(LocalDateTime.now(), ZoneId.systemDefault(), ZoneId.of("UTC"))
                        .format(formatter) + " UTC\n";
            }else{
                log = "User " + userNameField.getText() + " made an unsuccessful login attempt at " + " " +
                      Utilities.changeTimeZone(LocalDateTime.now(), ZoneId.systemDefault(), ZoneId.of("UTC"))
                        .format(formatter) + " UTC\n";
            }
            output.append(log);
        } catch(IOException e)
        {
            e.printStackTrace();
        } finally {
            // Close the file
            try{
                if(output != null)
                    output.close();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }

    }
}
