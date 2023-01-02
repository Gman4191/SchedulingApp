package DAO;

import Utility.Utilities;
import com.sun.media.jfxmediaimpl.platform.Platform;

import javax.xml.transform.Result;
import java.sql.*;
import java.time.ZonedDateTime;

public class DBLogin {

    public static void verifyUser(String userName, String password) throws Exception
    {
        ResultSet resultSet;
        String query = "SELECT * FROM users WHERE User_Name LIKE ? AND Password LIKE ?";
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

        if(!resultSet.next())
            throw new Exception("loginError");
    }

    public static void selectUsers() {
        ResultSet resultSet;
        String query = "SELECT * FROM users";
        try
        {
            PreparedStatement select = DBConnection.getConnection().prepareStatement(query);
            select.executeQuery();
            resultSet = select.getResultSet();
        } catch(SQLException e)
        {
            e.printStackTrace();
            return;
        }

        try {
            while (resultSet.next()) {
                System.out.println(resultSet.getString("User_Name") + " : " + resultSet.getString("Password"));

            }
        } catch(SQLException e)
        {
            e.printStackTrace();
        }
    }
}
