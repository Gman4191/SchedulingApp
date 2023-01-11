package DAO;

import Models.Appointment;
import Utility.Utilities;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;

public class DBLogin extends DBBase{

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

    /**
     * Get any existing upcoming appointment information
     * @param currentTime the current time
     * @return an upcoming appointment
     */
    public static Appointment getUpcomingAppointment(LocalDateTime currentTime)
    {
        String query = "SELECT Appointment_ID, Title, Description, Location, Type, Start, End, a.Customer_ID, a.User_ID, a.Contact_ID, " +
                "Customer_Name, User_Name, Contact_Name " +
                "FROM Appointments a JOIN Customers cu JOIN Contacts co JOIN Users u " +
                "ON a.Customer_ID = cu.Customer_ID AND a.User_ID = u.User_ID AND a.Contact_ID = co.Contact_ID " +
                "WHERE TIMESTAMPDIFF(MINUTE, START, ?) <= 15;";

        try{
            PreparedStatement select = DBConnection.getConnection().prepareStatement(query);
            select.setTimestamp(1, Timestamp.valueOf(currentTime));
            select.executeQuery();
            ResultSet rs = select.getResultSet();

            if(rs.next())
            {
                // Get the start and end time of the appointment
                LocalDateTime appointmentStart = LocalDateTime.parse(rs.getString("Start"), formatter);
                LocalDateTime appointmentEnd = LocalDateTime.parse(rs.getString("End"), formatter);

                // Convert the appointment start and end time to the current time zone
                LocalTime startTime = Utilities.changeTimeZone(appointmentStart.toLocalTime(), dbTimeZone, ZoneId.systemDefault());
                LocalTime endTime = Utilities.changeTimeZone(appointmentEnd.toLocalTime(), dbTimeZone, ZoneId.systemDefault());

                // Return the upcoming appointment information
                return new Appointment(rs.getInt("Appointment_ID"), rs.getString("Title"),
                                       rs.getString("Description"), rs.getString("Location"),
                                       rs.getString("Type"), appointmentStart.toLocalDate(), startTime,
                                       endTime, rs.getInt("Customer_ID"),
                                       rs.getString("Customer_Name"), rs.getInt("User_ID"),
                                       rs.getString("User_Name"), rs.getInt("Contact_ID"),
                                       rs.getString("Contact_Name"));
            }
        } catch(SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
}
