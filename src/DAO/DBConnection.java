package DAO;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    /**
     * The java database protocol
     */
    private static final String protocol = "jdbc";
    /**
     * The database user name
     */
    private static final String userName = "sqlUser";
    /**
     * The database password
     */
    private static final String password = "Passw0rd!";
    /**
     * The database vendor name
     */
    private static final String vendorName = ":mysql:";
    /**
     * The database server IP address
     */
    private static final String serverIp = "//127.0.0.1:3306/";
    /**
     * The database name
     */
    private static final String databaseName = "client_schedule";
    /**
     * The java database driver
     */
    private static final String MYSQLJDBCDRIVER = "com.mysql.cj.jdbc.Driver";
    /**
     * The connection URL
     */
    private static final String jdbcURL = protocol + vendorName + serverIp + databaseName;
    /**
     * The database connection
     */
    private static Connection conn = null;

    /**
     * Make a connection to the database
     * @return the made database connection
     */
    public static Connection makeConnection()
    {
        try
        {
            Class.forName(MYSQLJDBCDRIVER);
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
