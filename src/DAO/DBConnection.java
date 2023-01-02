package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class DBConnection {
    private static final String protocol = "jdbc";
    private static final String userName = "sqlUser";
    private static final String vendorName = ":mysql:";
    private static final String serverIp = "//127.0.0.1:3306/";
    private static final String password = "Passw0rd!";
    private static final String databaseName = "client_schedule";
    private static final String MYSQLJBCDRIVER = "com.mysql.cj.jdbc.Driver";

    private static final String jdbcURL = protocol + vendorName + serverIp + databaseName;

    private static Connection conn = null;
    public static Connection makeConnection()
    {
        try
        {
            Class.forName(MYSQLJBCDRIVER);
            conn = DriverManager.getConnection(jdbcURL, userName, password);
            System.out.println("Connection made!");
        } catch(Exception e)
        {
            e.printStackTrace();
        }
        return conn;
    }

    public static Connection getConnection()
    {
        return conn;
    }
    public static void closeConnection()
    {
        try{
            conn.close();
        } catch(Exception e)
        {
            // Do nothing
        }
    }
}
