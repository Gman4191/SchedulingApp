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
import java.time.format.DateTimeFormatter;

import static javafx.collections.FXCollections.observableArrayList;

public class DBReport {
    public final static String dateTimePattern = "yyyy-MM-dd HH:mm:ss";
    public final static ZoneId databaseTimeZone = ZoneId.of("UTC");

    public static ObservableList<String> getAllTypes()
    {
        ObservableList<String> types = observableArrayList();
        String query = "SELECT Type FROM Appointments";
         try{
             PreparedStatement select = DBConnection.getConnection().prepareStatement(query);
             select.executeQuery();
             ResultSet resultSet = select.getResultSet();

             while(resultSet.next())
             {
                 types.add(resultSet.getString("Type"));
             }
         } catch(SQLException e)
         {
             e.printStackTrace();
         }

         return types;
    }

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

    public static ObservableList<Appointment> getContactAppointments(Contact contact)
    {
        ObservableList<Appointment> appointments = observableArrayList();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateTimePattern);
        String query = "SELECT Appointment_ID, Title, Description, Location, Type, Start, End, a.Customer_ID, a.User_ID, a.Contact_ID, " +
                "Customer_Name, User_Name, Contact_Name " +
                "FROM Appointments a JOIN Customers cu JOIN Contacts co JOIN Users u " +
                "ON a.Customer_ID = cu.Customer_ID AND a.User_ID = u.User_ID AND a.Contact_ID = co.Contact_ID " +
                "WHERE a.Contact_ID = ?;";

        try{
            PreparedStatement select = DBConnection.getConnection().prepareStatement(query);
            select.setInt(1, contact.getId());
            select.executeQuery();
            ResultSet resultSet = select.getResultSet();

            while(resultSet.next())
            {
                LocalDateTime appointmentStart = LocalDateTime.parse(resultSet.getString("Start"), formatter);
                LocalDateTime appointmentEnd = LocalDateTime.parse(resultSet.getString("End"), formatter);
                LocalTime startTime = Utilities.changeTimeZone(appointmentStart.toLocalTime(), databaseTimeZone, ZoneId.systemDefault());
                LocalTime endTime = Utilities.changeTimeZone(appointmentEnd.toLocalTime(), databaseTimeZone, ZoneId.systemDefault());
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

    public static ObservableList<Appointment> getCustomerAppointments(Customer customer)
    {
        ObservableList<Appointment> appointments = observableArrayList();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateTimePattern);
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
                LocalTime startTime = Utilities.changeTimeZone(appointmentStart.toLocalTime(), databaseTimeZone, ZoneId.systemDefault());
                LocalTime endTime = Utilities.changeTimeZone(appointmentEnd.toLocalTime(), databaseTimeZone, ZoneId.systemDefault());
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
