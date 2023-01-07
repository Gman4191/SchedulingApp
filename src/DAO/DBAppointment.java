package DAO;

import Models.Appointment;
import Models.Contact;
import Models.Customer;
import Utility.Utilities;
import javafx.collections.ObservableList;
import jdk.jshell.execution.Util;

import java.sql.*;
import java.time.*;
import java.time.format.DateTimeFormatter;

import static javafx.collections.FXCollections.observableArrayList;

public class DBAppointment {
    public final static String dateTimePattern = "yyyy-MM-dd HH:mm:ss";
    public final static ZoneId databaseTimeZone = ZoneId.of("America/New_York");

    public static ObservableList<Appointment> getAllAppointments()
    {
        ObservableList<Appointment> appointments = observableArrayList();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateTimePattern);
        String query = "SELECT Appointment_ID, Title, Description, Location, Type, Start, End, a.Customer_ID, a.User_ID, a.Contact_ID, " +
                       "Customer_Name, User_Name, Contact_Name " +
                       "FROM Appointments a JOIN Customers cu JOIN Contacts co JOIN Users u " +
                       "ON a.Customer_ID = cu.Customer_ID AND a.User_ID = u.User_ID AND a.Contact_ID = co.Contact_ID;";

        try{
            PreparedStatement select = DBConnection.getConnection().prepareStatement(query);
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

    public static boolean validateSelectedTimes(LocalDate date, LocalTime appointmentStart, LocalTime appointmentEnd)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateTimePattern);
        String query = "SELECT Start, End FROM Appointments WHERE (Start BETWEEN ? AND ?) OR (END BETWEEN ? AND ?);";

        try{
            appointmentStart = Utilities.changeTimeZone(appointmentStart, ZoneId.systemDefault(), databaseTimeZone);
            System.out.println(appointmentStart);
            appointmentEnd = Utilities.changeTimeZone(appointmentEnd, ZoneId.systemDefault(), databaseTimeZone);
            LocalDateTime start = LocalDateTime.of(date, appointmentStart);
            LocalDateTime end = LocalDateTime.of(date, appointmentEnd);
            PreparedStatement select = DBConnection.getConnection().prepareStatement(query);
            select.setTimestamp(1, Timestamp.valueOf(start));
            select.setTimestamp(2, Timestamp.valueOf(end));
            select.setTimestamp(3, Timestamp.valueOf(start));
            select.setTimestamp(4, Timestamp.valueOf(end));
            select.executeQuery();
            ResultSet resultSet = select.getResultSet();

            if(resultSet.next())
                return false;
        } catch(SQLException e)
        {
            e.printStackTrace();
        }

        return true;
    }

    public static ObservableList<Contact> getAllContacts(){
        ObservableList<Contact> contacts = observableArrayList();
        String query = "SELECT Contact_ID, Contact_Name, Email FROM Contacts;";

        try{
            PreparedStatement select = DBConnection.getConnection().prepareStatement(query);
            select.executeQuery();
            ResultSet resultSet = select.getResultSet();

            while(resultSet.next())
            {
                Contact c = new Contact(resultSet.getInt("Contact_ID"), resultSet.getString("Contact_Name"),
                                        resultSet.getString("Email"));
                contacts.add(c);
            }
        } catch(SQLException e)
        {
            e.printStackTrace();
        }

        return contacts;
    }

    public static void addAppointment(Appointment appointment)
    {
        String query = "INSERT INTO Appointments(Appointment_ID, Title, Description, Location, Type, Start, End, Create_Date," +
                "Create_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) " +
                "VALUES(null, ?, ?, ?, ? ,?, ?, curdate(), ?, curdate(), ?, ?, ?, ?);";
        
    }
}
