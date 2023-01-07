package Models;

public class User {

    /**
     * The user name
     */
    private static String userName;
    /**
     * The user id
     */
    private static int id;

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

    /**
     * Get the user id
     * @return the user id
     */
    public static int getId(){return id;}

    /**
     * Set the user id
     * @param newId the id to set
     */
    public static void setId(int newId){id = newId;}
}
