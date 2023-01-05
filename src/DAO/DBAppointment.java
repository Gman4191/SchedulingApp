package DAO;

import Models.Appointment;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

import static javafx.collections.FXCollections.observableArrayList;

public class DBAppointment {
    public static ObservableList<Appointment> getAllAppointments()
    {
        ObservableList<Appointment> appointments = observableArrayList();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
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
                LocalDateTime start = LocalDateTime.parse(resultSet.getString("Start"), formatter);
                LocalDateTime end = LocalDateTime.parse(resultSet.getString("End"), formatter);
                Appointment a = new Appointment(resultSet.getInt("Appointment_ID"), resultSet.getString("Title"),
                                        resultSet.getString("Description"), resultSet.getString("Location"),
                                        resultSet.getString("Type"), start, end, resultSet.getInt("Customer_ID"),
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
