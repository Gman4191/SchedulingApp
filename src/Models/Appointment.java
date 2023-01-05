package Models;

import java.time.LocalDateTime;

public class Appointment {
    private int id;
    private String title;
    private String description;
    private String location;
    private String type;
    private LocalDateTime start;
    private LocalDateTime end;
    private int customerId;
    private String customerName;
    private int userId;
    private String userName;
    private int contactId;
    private String contactName;

    public Appointment(int id, String title, String description, String location, String type, LocalDateTime start,
                       LocalDateTime end, int customerId, String customerName, int userId, String userName, int contactId, String contactName)
    {
        this.id = id;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.customerId = customerId;
        this.customerName = customerName;
        this.userId = userId;
        this.userName = userName;
        this.contactId = contactId;
        this.contactName = contactName;
    }

    public int getId(){return id;}
    public void setId(int id){this.id = id;}
    public String getTitle(){return title;}
    public void setTitle(String title){this.title = title;}
    public String getDescription(){return description;}
    public void setDescription(String description){this.description = description;}
    public String getLocation(){return location;}
    public void setLocation(String location){this.location = location;}
    public String getType(){return type;}
    public void setType(String type){this.type = type;}
    public LocalDateTime getStart(){return start;}
    public void setStart(LocalDateTime start){this.start = start;}
    public LocalDateTime getEnd(){return end;}
    public void setEnd(LocalDateTime end){this.end = end;}
    public int getCustomerId(){return customerId;}
    public void setCustomerId(int customerId){this.customerId = customerId;}
    public String getCustomerName(){return customerName;}
    public void setCustomerName(String customerName){this.customerName = customerName;}
    public int getUserId(){return userId;}
    public void setUserId(int userId){this.userId = userId;}
    public String getUserName(){return userName;}
    public void setUserName(String userName){this.userName = userName;}
    public int getContactId(){return contactId;}
    public void setContactId(int contactId){this.contactId = contactId;}
    public String getContactName(){return contactName;}
    public void setContactName(String contactName){this.contactName = contactName;}
}
