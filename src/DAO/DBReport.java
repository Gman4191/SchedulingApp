package DAO;

import Models.Appointment;
import Models.Contact;
import Models.Customer;
import Utility.Utilities;
import javafx.collections.ObservableList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.*;
import static javafx.collections.FXCollections.observableArrayList;

public class DBReport extends DBBase{

    /**
     * Get all appointment types
     * @return a list of all appointment types
     */
    public static ObservableList<String> getAllTypes()
    {
        ObservableList<String> types = observableArrayList();
        types.addAll("Tire Installation", "Tire Rotation", "Tire Repair", "Battery Replacement", "Oil Change");
        String query = "SELECT Type FROM Appointments";

         try{
             PreparedStatement select = DBConnection.getConnection().prepareStatement(query);
             select.executeQuery();
             ResultSet resultSet = select.getResultSet();

             // Add any used appointment types to the list if the list does not contain them yet
             while(resultSet.next())
             {
                 String type = resultSet.getString("Type");
                 if(!types.contains(type))
                    types.add(type);
             }
         } catch(SQLException e)
         {
             e.printStackTrace();
         }

         return types;
    }

    /**
     * Get the total number of appointments of a specific type in a given month
     * @param type the appointment type
     * @param month the month
     * @return the monthly appointment total
     */
    public static int getAppointmentTotal(String type, Month month) {
        int total = 0;
        String query = "SELECT COUNT(*) as Total FROM Appointments WHERE Type = ? AND MONTH(Start) = ?;";

        try{
            PreparedStatement select = DBConnection.getConnection().prepareStatement(query);
            select.setString(1, type);
            select.setInt(2, month.getValue());
            select.executeQuery();
            ResultSet resultSet = select.getResultSet();

            if(resultSet.next())
                total = resultSet.getInt("Total");
        } catch(SQLException e)
        {
            e.printStackTrace();
        }

        return total;
    }

    /**
     * Get all the appointments associated with a given contact
     * @param contact the contact to filter by
     * @return a list of appointments
     */
    public static ObservableList<Appointment> getContactAppointments(Contact contact)
    {
        ObservableList<Appointment> appointments = observableArrayList();
        String query = "SELECT Appointment_ID, Title, Description, Location, Type, Start, End, a.Customer_ID, a.User_ID, " +
                       "a.Contact_ID, Customer_Name, User_Name, Contact_Name " +
                       "FROM Appointments a JOIN Customers cu JOIN Contacts co JOIN Users u " +
                       "ON a.Customer_ID = cu.Customer_ID AND a.User_ID = u.User_ID AND a.Contact_ID = co.Contact_ID " +
                       "WHERE a.Contact_ID = ?;";

        try{
            PreparedStatement select = DBConnection.getConnection().prepareStatement(query);
            select.setInt(1, contact.getId());
            select.executeQuery();
            ResultSet rs = select.getResultSet();

            while(rs.next())
            {
                // Get the appointment start and end time
                LocalDateTime appointmentStart = LocalDateTime.parse(rs.getString("Start"), formatter);
                LocalDateTime appointmentEnd = LocalDateTime.parse(rs.getString("End"), formatter);

                // Convert the appointment start and end time to the current time zone
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
     * Get all the appointments associated with a given customer
     * @param customer the customer
     * @return a list of appointments
     */
    public static ObservableList<Appointment> getCustomerAppointments(Customer customer)
    {
        ObservableList<Appointment> appointments = observableArrayList();
        String query = "SELECT Appointment_ID, Title, Description, Location, Type, Start, End, a.Customer_ID, a.User_ID, a.Contact_ID, " +
                "Customer_Name, User_Name, Contact_Name " +
                "FROM Appointments a JOIN Customers cu JOIN Contacts co JOIN Users u " +
                "ON a.Customer_ID = cu.Customer_ID AND a.User_ID = u.User_ID AND a.Contact_ID = co.Contact_ID " +
                "WHERE a.Customer_ID = ?;";

        try{
            PreparedStatement select = DBConnection.getConnection().prepareStatement(query);
            select.setInt(1, customer.getId());
            select.executeQuery();
            ResultSet resultSet = select.getResultSet();

            while(resultSet.next())
            {
                LocalDateTime appointmentStart = LocalDateTime.parse(resultSet.getString("Start"), formatter);
                LocalDateTime appointmentEnd = LocalDateTime.parse(resultSet.getString("End"), formatter);
                LocalTime startTime = Utilities.changeTimeZone(appointmentStart.toLocalTime(), dbTimeZone, ZoneId.systemDefault());
                LocalTime endTime = Utilities.changeTimeZone(appointmentEnd.toLocalTime(), dbTimeZone, ZoneId.systemDefault());
                Appointment a = new Appointment(resultSet.getInt("Appointment_ID"), resultSet.getString("Title"),
                        resultSet.getString("Description"), resultSet.getString("Location"),
                        resultSet.getString("Type"), appointmentStart.toLocalDate(), startTime,
                        endTime, resultSet.getInt("Customer_ID"),
                        resultSet.getString("Customer_Name"), resultSet.getInt("User_ID"),
                        resultSet.getString("User_Name"), resultSet.getInt("Contact_ID"),
                        resultSet.getString("Contact_Name"));
                appointments.add(a);
            }
        } catch(SQLException e)
        {
            e.printStackTrace();
        }

        return appointments;
    }
}
