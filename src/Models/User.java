package Models;

public class User {

    /**
     * The user name
     */
    private static String userName;

    /**
     * Get the name of the user
     * @return the user's name
     */
    public static String getUserName(){return userName;}

    /**
     * Set the name of the user
     * @param newUserName the name to set
     */
    public static void setUserName(String newUserName){userName = newUserName;}
}
