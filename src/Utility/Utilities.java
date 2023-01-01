package Utility;

import javafx.scene.control.Alert;

public class Utilities {
    public static void displayErrorMessage(String message)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR, message);
        alert.show();
    }
}
