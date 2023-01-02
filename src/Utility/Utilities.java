package Utility;

import javafx.scene.control.Alert;

public class Utilities {
    public static void displayErrorMessage(String message)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR, Language.getLanguage().getString(message));
        alert.setHeaderText(Language.getLanguage().getString("error"));
        alert.show();
    }
}
