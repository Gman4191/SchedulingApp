package DAO;

import Models.Appointment;
import Utility.Utilities;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class DBLogin {
    public final static String dateTimePattern = "yyyy-MM-dd HH:mm:ss";
    public final static ZoneId databaseTimeZone = ZoneId.of("UTC");
    /**
     * Verify the user's credentials
     * @param userName the user's name
     * @param password the user's password
     * @throws Exception if the user's credentials are rejected
     * @return the id of the found user
     */
    public static int verifyUser(String userName, String password) throws Exception
    {
        ResultSet resultSet;
        String query = "SELECT * FROM users WHERE User_Name LIKE ? AND Password LIKE ?";

        // Search for a user with the given credentials
        try {
            PreparedStatement select = DBConnection.getConnection().prepareStatement(query);
            select.setString(1, userName);
            select.setString(2, password);
            select.executeQuery();
            resultSet = select.getResultSet();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            return -1;
        }

        // If a user is not found, reject the user's credentials
        if(!resultSet.next())
            throw new Exception("loginError");

        return resultSet.getInt("User_ID");
    }

    public static Appointment getUpcomingAppointment(LocalDateTime currentTime)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateTimePattern);
        String query = "SELECT Appointment_ID, Title, Description, Location, Type, Start, End, a.Customer_ID, a.User_ID, a.Contact_ID, " +
                "Customer_Name, User_Name, Contact_Name " +
                "FROM Appointments a JOIN Customers cu JOIN Contacts co JOIN Users u " +
                "ON a.Customer_ID = cu.Customer_ID AND a.User_ID = u.User_ID AND a.Contact_ID = co.Contact_ID " +
                "WHERE TIMESTAMPDIFF(MINUTE, START, ?) <= 15;";

        try{
            PreparedStatement select = DBConnection.getConnection().prepareStatement(query);
            select.setTimestamp(1, Timestamp.valueOf(currentTime));
            select.executeQuery();
            ResultSet resultSet = select.getResultSet();

            if(resultSet.next())
            {
                LocalDateTime appointmentStart = LocalDateTime.parse(resultSet.getString("Start"), formatter);
                LocalDateTime appointmentEnd = LocalDateTime.parse(resultSet.getString("End"), formatter);
                LocalTime startTime = Utilities.changeTimeZone(appointmentStart.toLocalTime(), databaseTimeZone, ZoneId.systemDefault());
                LocalTime endTime = Utilities.changeTimeZone(appointmentEnd.toLocalTime(), databaseTimeZone, ZoneId.systemDefault());
                return new Appointment(resultSet.getInt("Appointment_ID"), resultSet.getString("Title"),
                        resultSet.getString("Description"), resultSet.getString("Location"),
                        resultSet.getString("Type"), appointmentStart.toLocalDate(), startTime,
                        endTime, resultSet.getInt("Customer_ID"),
                        resultSet.getString("Customer_Name"), resultSet.getInt("User_ID"),
                        resultSet.getString("User_Name"), resultSet.getInt("Contact_ID"),
                        resultSet.getString("Contact_Name"));
            }
        } catch(SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
}
