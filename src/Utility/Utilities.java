package Utility;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import java.time.*;
import java.util.Optional;

public class Utilities {
    /**
     * Display an error message in a dialog box
     * @param message the error message to display
     */
    public static void displayErrorMessage(String message)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR, message);
        alert.show();
    }

    /**
     * Display a dialog box asking for a confirmation
     * @param message the confirmation message
     * @return true if confirmed
     */
    public static boolean displayConfirmationMessage(String message)
    {
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION, message);

        Optional<ButtonType> result = confirmation.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }

    /**
     * Display a message in a dialog box
     * @param message the message to display
     */
    public static void displayMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(message);
        alert.show();
    }

    /**
     * Change the time zone of a given time
     * @param time the time to change zones
     * @param currentZone the zone to change from
     * @param desiredZone the zone to change to
     * @return the time in the desired time zone
     */
    public static LocalTime changeTimeZone(LocalTime time, ZoneId currentZone, ZoneId desiredZone)
    {
        return ZonedDateTime.of(LocalDate.now(), time, currentZone).withZoneSameInstant(desiredZone).toLocalTime();
    }

    /**
     * Change the time zone of a given date and time
     * @param dateTime the date and time to change zones
     * @param currentZone the zone to change from
     * @param desiredZone the zone to change to
     * @return the date and time in the desired time zone
     */
    public static LocalDateTime changeTimeZone(LocalDateTime dateTime, ZoneId currentZone, ZoneId desiredZone)
    {
        return ZonedDateTime.of(dateTime, currentZone).withZoneSameInstant(desiredZone).toLocalDateTime();
    }
}
