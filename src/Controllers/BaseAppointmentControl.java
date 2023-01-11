package Controllers;

import DAO.DBContact;
import DAO.DBCustomer;
import Models.Contact;
import Models.Customer;
import javafx.collections.ObservableList;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import static javafx.collections.FXCollections.observableArrayList;

public class BaseAppointmentControl {
    /**
     * The format of the appointment times
     */
    protected final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
    /**
     * The business time zone
     */
    protected final static ZoneId businessZone = ZoneId.of("America/New_York");
    /**
     * The business starting time
     */
    protected final static String businessStartTime = "08:00";
    /**
     * The business ending time
     */
    protected final static String businessEndTime = "22:00";

    /**
     * A list of appointment types
     */
    protected static ObservableList<String> types = observableArrayList();
    /**
     * A list of customers
     */
    protected static ObservableList<Customer> customers = observableArrayList();
    /**
     * A list of contacts
     */
    protected static ObservableList<Contact> contacts = observableArrayList();

    /**
     * Get the appointment types
     * @return a list of appointment types
     */
    protected static ObservableList<String> getTypes()
    {
        if(types.isEmpty())
            types.addAll("Tire Installation", "Tire Rotation", "Tire Repair", "Battery Replacement", "Oil Change");

        return types;
    }

    /**
     * Get all the customers
     * @return a list of customers
     */
    protected static ObservableList<Customer> getCustomers()
    {
        return customers;
    }

    /**
     * Update the customer list to include all the customers in the database
     */
    protected static void setCustomers()
    {
        customers = DBCustomer.getAllCustomers();
    }

    /**
     * Get all the contacts
     * @return a list of contacts
     */
    protected static ObservableList<Contact> getContacts()
    {
        return contacts;
    }

    /**
     * Update the contact list to include all the contacts in the database
     */
    protected static void setContacts()
    {
        contacts = DBContact.getAllContacts();
    }
}
