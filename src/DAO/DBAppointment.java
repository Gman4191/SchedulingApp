package DAO;

import Models.Appointment;
import Models.Contact;
import Models.User;
import Utility.Utilities;
import javafx.collections.ObservableList;
import java.sql.*;
import java.time.*;
import static javafx.collections.FXCollections.observableArrayList;

public class DBAppointment extends DBBase{

    /**
     * Get all the appointment records in the database
     * @return a list of appointments
     */
    public static ObservableList<Appointment> getAllAppointments()
    {
        ObservableList<Appointment> appointments = observableArrayList();
        String query = "SELECT Appointment_ID, Title, Description, Location, Type, Start, End, a.Customer_ID, a.User_ID," +
                       " a.Contact_ID, Customer_Name, User_Name, Contact_Name " +
                       "FROM Appointments a JOIN Customers cu JOIN Contacts co JOIN Users u " +
                       "ON a.Customer_ID = cu.Customer_ID AND a.User_ID = u.User_ID AND a.Contact_ID = co.Contact_ID;";

        try{
            PreparedStatement select = DBConnection.getConnection().prepareStatement(query);
            select.executeQuery();
            ResultSet rs = select.getResultSet();

            while(rs.next())
            {
                // Get the start and end time of the appointment
                LocalDateTime appointmentStart = LocalDateTime.parse(rs.getString("Start"), formatter);
                LocalDateTime appointmentEnd = LocalDateTime.parse(rs.getString("End"), formatter);

                // Convert the start and end times to the current time zone
                LocalTime startTime = Utilities.changeTimeZone(appointmentStart.toLocalTime(), dbTimeZone, ZoneId.systemDefault());
                LocalTime endTime = Utilities.changeTimeZone(appointmentEnd.toLocalTime(), dbTimeZone, ZoneId.systemDefault());

                // Create a new appointment
                Appointment a = new Appointment(rs.getInt("Appointment_ID"), rs.getString("Title"),
                                                rs.getString("Description"), rs.getString("Location"),
                                                rs.getString("Type"), appointmentStart.toLocalDate(),
                                                startTime, endTime, rs.getInt("Customer_ID"),
                                                rs.getString("Customer_Name"), rs.getInt("User_ID"),
                                                rs.getString("User_Name"), rs.getInt("Contact_ID"),
                                                rs.getString("Contact_Name"));

                // Add the appointment to the list
                appointments.add(a);
            }
        } catch(SQLException e)
        {
            e.printStackTrace();
        }

        return appointments;
    }

    /**
     * Get all appointments between a specified start and end date
     * @param start the start date restriction
     * @param end the end date restriction
     * @return a list of appointments occurring between the start and end date
     */
    public static ObservableList<Appointment> getFilteredAppointments(LocalDate start, LocalDate end)
    {
        ObservableList<Appointment> appointments = observableArrayList();
        String query = "SELECT Appointment_ID, Title, Description, Location, Type, Start, End, a.Customer_ID, a.User_ID, " +
                       "a.Contact_ID, Customer_Name, User_Name, Contact_Name " +
                       "FROM Appointments a JOIN Customers cu JOIN Contacts co JOIN Users u " +
                       "ON a.Customer_ID = cu.Customer_ID AND a.User_ID = u.User_ID AND a.Contact_ID = co.Contact_ID " +
                       "WHERE DATE(Start) >= ? AND DATE(End) <= ?;";

        try{
            PreparedStatement select = DBConnection.getConnection().prepareStatement(query);
            select.setDate(1, Date.valueOf(start));
            select.setDate(2, Date.valueOf(end));
            select.executeQuery();
            ResultSet rs = select.getResultSet();

            while(rs.next())
            {
                // Get the start and end times of the appointment
                LocalDateTime appointmentStart = LocalDateTime.parse(rs.getString("Start"), formatter);
                LocalDateTime appointmentEnd = LocalDateTime.parse(rs.getString("End"), formatter);

                // Convert the start and end times to the current time zone
                LocalTime startTime = Utilities.changeTimeZone(appointmentStart.toLocalTime(), dbTimeZone, ZoneId.systemDefault());
                LocalTime endTime = Utilities.changeTimeZone(appointmentEnd.toLocalTime(), dbTimeZone, ZoneId.systemDefault());

                // Create a new appointment
                Appointment a = new Appointment(rs.getInt("Appointment_ID"), rs.getString("Title"),
                                                rs.getString("Description"), rs.getString("Location"),
                                                rs.getString("Type"), appointmentStart.toLocalDate(), startTime,
                                                endTime, rs.getInt("Customer_ID"),
                                                rs.getString("Customer_Name"), rs.getInt("User_ID"),
                                                rs.getString("User_Name"), rs.getInt("Contact_ID"),
                                                rs.getString("Contact_Name"));

                // Add the appointment to the list
                appointments.add(a);
            }
        } catch(SQLException e)
        {
            e.printStackTrace();
        }

        return appointments;
    }

    /**
     * Verify that an appointment does not overlap with another appointment
     * @param date the appointment date
     * @param appointmentStart the appointment start time
     * @param appointmentEnd the appointment end time
     * @return true if there are no overlapping appointments
     */
    public static boolean validateAppointmentTimes(LocalDate date, LocalTime appointmentStart, LocalTime appointmentEnd)
    {
        String query = "SELECT Start, End FROM Appointments WHERE (Start BETWEEN ? AND ?) OR (END BETWEEN ? AND ?);";

        try{
            // Convert the appointment start and end time to the database time zone (UTC)
            appointmentStart = Utilities.changeTimeZone(appointmentStart, ZoneId.systemDefault(), dbTimeZone);
            appointmentEnd = Utilities.changeTimeZone(appointmentEnd, ZoneId.systemDefault(), dbTimeZone);

            // Get the exact points in time the appointment starts and ends
            LocalDateTime start = LocalDateTime.of(date, appointmentStart);
            LocalDateTime end = LocalDateTime.of(date, appointmentEnd);

            // Prepare and execute the search for overlapping appointments
            PreparedStatement select = DBConnection.getConnection().prepareStatement(query);
            select.setTimestamp(1, Timestamp.valueOf(start));
            select.setTimestamp(2, Timestamp.valueOf(end));
            select.setTimestamp(3, Timestamp.valueOf(start));
            select.setTimestamp(4, Timestamp.valueOf(end));
            select.executeQuery();
            ResultSet resultSet = select.getResultSet();

            // Return false if an overlapping appointment is found
            if(resultSet.next())
                return false;
        } catch(SQLException e)
        {
            e.printStackTrace();
        }

        return true;
    }

    /**
     * Verify that an appointment does not overlap with another appointment
     * @param id the appointment id
     * @param date the appointment date
     * @param appointmentStart the appointment start time
     * @param appointmentEnd the appointment end time
     * @return true if there are no overlapping appointments
     */
    public static boolean validateAppointmentTimes(int id, LocalDate date, LocalTime appointmentStart, LocalTime appointmentEnd)
    {
        String query = "SELECT Appointment_ID, Start, End FROM Appointments " +
                       "WHERE Appointment_ID != ? AND ((Start BETWEEN ? AND ?) OR (END BETWEEN ? AND ?));";

        try{
            // Convert the appointment start and end times to the database time zone (UTC)
            appointmentStart = Utilities.changeTimeZone(appointmentStart, ZoneId.systemDefault(), dbTimeZone);
            appointmentEnd = Utilities.changeTimeZone(appointmentEnd, ZoneId.systemDefault(), dbTimeZone);

            // Get the exact points in time the appointment starts and ends
            LocalDateTime start = LocalDateTime.of(date, appointmentStart);
            LocalDateTime end = LocalDateTime.of(date, appointmentEnd);

            // Prepare and execute the search for overlapping appointments
            PreparedStatement select = DBConnection.getConnection().prepareStatement(query);
            select.setInt(1, id);
            select.setTimestamp(2, Timestamp.valueOf(start));
            select.setTimestamp(3, Timestamp.valueOf(end));
            select.setTimestamp(4, Timestamp.valueOf(start));
            select.setTimestamp(5, Timestamp.valueOf(end));
            select.executeQuery();
            ResultSet resultSet = select.getResultSet();

            // Return false if an overlapping appointment is found
            if(resultSet.next()) {
                return false;
            }
        } catch(SQLException e)
        {
            e.printStackTrace();
        }

        return true;
    }

    /**
     * Add a new appointment to the database
     * @param appointment the appointment to add
     */
    public static void addAppointment(Appointment appointment)
    {
        String query = "INSERT INTO Appointments(Appointment_ID, Title, Description, Location, Type, Start, End, Create_Date," +
                "Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) " +
                "VALUES(null, ?, ?, ?, ? ,?, ?, curdate(), ?, CURRENT_TIMESTAMP, ?, ?, ?, ?);";

        try{
            // Convert the appointment start and end time to the database time zone (UTC)
            LocalTime appointmentStart = Utilities.changeTimeZone(appointment.getStart(), ZoneId.systemDefault(), dbTimeZone);
            LocalTime appointmentEnd = Utilities.changeTimeZone(appointment.getEnd(), ZoneId.systemDefault(), dbTimeZone);

            // Get the exact points in time the appointment starts and ends
            LocalDateTime start = LocalDateTime.of(appointment.getDate(), appointmentStart);
            LocalDateTime end = LocalDateTime.of(appointment.getDate(), appointmentEnd);

            // Prepare and execute the addition
            PreparedStatement insert = DBConnection.getConnection().prepareStatement(query);
            insert.setString(1, appointment.getTitle());
            insert.setString(2, appointment.getDescription());
            insert.setString(3, appointment.getLocation());
            insert.setString(4, appointment.getType());
            insert.setTimestamp(5, Timestamp.valueOf(start));
            insert.setTimestamp(6, Timestamp.valueOf(end));
            insert.setString(7, User.getUserName());
            insert.setString(8, User.getUserName());
            insert.setInt(9, appointment.getCustomerId());
            insert.setInt(10, appointment.getUserId());
            insert.setInt(11, appointment.getContactId());
            insert.execute();
        } catch(SQLException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Update an appointment in the database
     * @param newAppointment the updated appointment
     */
    public static void updateAppointment(Appointment newAppointment)
    {
        String query = "UPDATE Appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, " +
                       "Last_Update = CURRENT_TIMESTAMP, Last_Updated_By = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? " +
                       "WHERE Appointment_ID = ?;";
        try{
            // Convert the appointment start and end times to the database time zone (UTC)
            LocalTime appointmentStart = Utilities.changeTimeZone(newAppointment.getStart(), ZoneId.systemDefault(), dbTimeZone);
            LocalTime appointmentEnd = Utilities.changeTimeZone(newAppointment.getEnd(), ZoneId.systemDefault(), dbTimeZone);

            // Get the exact points in time the appointment starts and ends
            LocalDateTime start = LocalDateTime.of(newAppointment.getDate(), appointmentStart);
            LocalDateTime end = LocalDateTime.of(newAppointment.getDate(), appointmentEnd);

            // Prepare and execute the update
            PreparedStatement update = DBConnection.getConnection().prepareStatement(query);
            update.setString(1, newAppointment.getTitle());
            update.setString(2, newAppointment.getDescription());
            update.setString(3, newAppointment.getLocation());
            update.setString(4, newAppointment.getType());
            update.setTimestamp(5, Timestamp.valueOf(start));
            update.setTimestamp(6, Timestamp.valueOf(end));
            update.setString(7, User.getUserName());
            update.setInt(8, newAppointment.getCustomerId());
            update.setInt(9, newAppointment.getUserId());
            update.setInt(10, newAppointment.getContactId());
            update.setInt(11, newAppointment.getId());

            update.execute();
        } catch(SQLException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Delete an appointment from the database
     * @param selectedAppointment the selected appointment to delete
     */
    public static void deleteAppointment(Appointment selectedAppointment){
        String query = "DELETE FROM Appointments WHERE Appointment_ID = ?;";

        try{
            PreparedStatement delete = DBConnection.getConnection().prepareStatement(query);
            delete.setInt(1, selectedAppointment.getId());
            delete.execute();
        } catch(SQLException e){
            e.printStackTrace();
        }
    }
}
