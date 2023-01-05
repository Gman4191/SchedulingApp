package DAO;

import java.sql.*;

public class DBLogin {

    /**
     * Verify the user's credentials
     * @param userName the user's name
     * @param password the user's password
     * @throws Exception if the user's credentials are rejected
     */
    public static void verifyUser(String userName, String password) throws Exception
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
            return;
        }

        // If a user is not found, reject the user'ss credentials
        if(!resultSet.next())
            throw new Exception("loginError");
    }
}
