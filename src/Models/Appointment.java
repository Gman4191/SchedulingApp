package Models;

import java.time.LocalDate;
import java.time.LocalTime;

public class Appointment {
    /**
     * The appointment id
     */
    private int id;
    /**
     * The appointment title
     */
    private String title;
    /**
     * The appointment description
     */
    private String description;
    /**
     * The appointment location
     */
    private String location;
    /**
     * The appointment type
     */
    private String type;
    /**
     * The appointment date
     */
    private LocalDate date;
    /**
     * The appointment start time
     */
    private LocalTime start;
    /**
     * The appointment end time
     */
    private LocalTime end;
    /**
     * The id of the customer associated with the appointment
     */
    private int customerId;
    /**
     * The name of the customer associated with the appointment
     */
    private String customerName;
    /**
     * The appointment user id
     */
    private int userId;
    /**
     * The appointment user name
     */
    private String userName;
    /**
     * The id of the contact associated with the appointment
     */
    private int contactId;
    /**
     * The name of the contact associated with the appointment
     */
    private String contactName;

    /**
     * Create a new appointment
     * @param id the appointment id
     * @param title the appointment title
     * @param description the appointment description
     * @param location the appointment location
     * @param type the appointment type
     * @param date the appointment date
     * @param start the appointment start time
     * @param end the appointment end time
     * @param customerId the appointment customer id
     * @param customerName the appointment customer name
     * @param userId the appointment user id
     * @param userName the appointment user name
     * @param contactId the appointment contact id
     * @param contactName the appointment contact name
     */
    public Appointment(int id, String title, String description, String location, String type, LocalDate date, LocalTime start,
                       LocalTime end, int customerId, String customerName, int userId, String userName, int contactId, String contactName)
    {
        this.id = id;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.date = date;
        this.start = start;
        this.end = end;
        this.customerId = customerId;
        this.customerName = customerName;
        this.userId = userId;
        this.userName = userName;
        this.contactId = contactId;
        this.contactName = contactName;
    }

    /**
     * Get the appointment id
     * @return the appointment id
     */
    public int getId(){return id;}

    /**
     * Set the appointment id
     * @param id the id to set
     */
    public void setId(int id){this.id = id;}

    /**
     * Get the appointment title
     * @return the appointment title
     */
    public String getTitle(){return title;}

    /**
     * Set the appointment title
     * @param title the title to set
     */
    public void setTitle(String title){this.title = title;}

    /**
     * Get the appointment description
     * @return the appointment description
     */
    public String getDescription(){return description;}

    /**
     * Set the appointment description
     * @param description the description to set
     */
    public void setDescription(String description){this.description = description;}

    /**
     * Get the appointment location
     * @return the appointment location
     */
    public String getLocation(){return location;}

    /**
     * Set the appointment location
     * @param location the location to set
     */
    public void setLocation(String location){this.location = location;}

    /**
     * Get the appointment type
     * @return the appointment type
     */
    public String getType(){return type;}

    /**
     * Set the appointment type
     * @param type the type to set
     */
    public void setType(String type){this.type = type;}

    /**
     * Get the appointment date
     * @return the appointment date
     */
    public LocalDate getDate(){return date;}

    /**
     * Set the appointment date
     * @param date the date to set
     */
    public void setDate(LocalDate date){this.date = date;}

    /**
     * Get the appointment start time
     * @return the appointment start time
     */
    public LocalTime getStart(){return start;}

    /**
     * Set the appointment start time
     * @param start the start time to set
     */
    public void setStart(LocalTime start){this.start = start;}

    /**
     * Get the appointment end time
     * @return the appointment end time
     */
    public LocalTime getEnd(){return end;}

    /**
     * Set the appointment end time
     * @param end the end time to set
     */
    public void setEnd(LocalTime end){this.end = end;}

    /**
     * Get the customer id associated with the appointment
     * @return the appointment customer id
     */
    public int getCustomerId(){return customerId;}

    /**
     * Set the customer id associated with the appointment
     * @param customerId the id to set
     */
    public void setCustomerId(int customerId){this.customerId = customerId;}

    /**
     * Get the customer name associated with the appointment
     * @return the customer name associated with the appointment
     */
    public String getCustomerName(){return customerName;}

    /**
     * Set the customer name associated with the appointment
     * @param customerName the name to set
     */
    public void setCustomerName(String customerName){this.customerName = customerName;}

    /**
     * Get the user id associated with the appointment
     * @return the user id associated with the appointment
     */
    public int getUserId(){return userId;}

    /**
     * Set the user id associate with the appointment
     * @param userId the id to set
     */
    public void setUserId(int userId){this.userId = userId;}

    /**
     * Get the user name associated with the appointment
     * @return the user name associated with the appointment
     */
    public String getUserName(){return userName;}

    /**
     * Set the user name associated with the appointment
     * @param userName the user name to set
     */
    public void setUserName(String userName){this.userName = userName;}

    /**
     * Get the contact id associated with the appointment
     * @return the contact id associated with the appointment
     */
    public int getContactId(){return contactId;}

    /**
     * Set the contact id associated with the appointment
     * @param contactId the id to set
     */
    public void setContactId(int contactId){this.contactId = contactId;}

    /**
     * Get the contact name associated with the appointment
     * @return the contact name associated with the appointment
     */
    public String getContactName(){return contactName;}

    /**
     * Set the contact name associated with the appointment
     * @param contactName the contact name to set
     */
    public void setContactName(String contactName){this.contactName = contactName;}
}
