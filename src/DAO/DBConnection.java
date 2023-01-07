package DAO;

import java.sql.Connection;
import java.sql.DriverManager;

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

    /**
     * Make a connection to the database
     * @return the made database connection
     */
    public static Connection makeConnection()
    {
        try
        {
            Class.forName(MYSQLJBCDRIVER);
            conn = DriverManager.getConnection(jdbcURL, userName, password);
        } catch(Exception e)
        {
            e.printStackTrace();
        }
        return conn;
    }

    /**
     * Get the database connection
     * @return the database connection
     */
    public static Connection getConnection()
    {
        return conn;
    }

    /**
     * Close the database connection
     */
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
