package Utility;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class Utilities {
    public static void displayErrorMessage(String message)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR, message);
        alert.show();
    }

    public static boolean displayConfirmationMessage(String message)
    {
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION, message);

        Optional<ButtonType> result = confirmation.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK)
        {
            return true;
        }

        return false;
    }

    public static void displayMessage(String message)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(message);
        alert.show();
    }
}
