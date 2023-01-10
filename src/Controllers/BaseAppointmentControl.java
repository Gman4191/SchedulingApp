package Controllers;

import DAO.DBAppointment;
import DAO.DBConnection;
import DAO.DBCustomer;
import Models.Contact;
import Models.Customer;
import javafx.collections.ObservableList;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import static javafx.collections.FXCollections.observableArrayList;

public class BaseAppointmentControl {
    protected final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
    protected final static ZoneId businessZone = ZoneId.of("America/New_York");
    protected final static String businessStartTime = "08:00";
    protected final static String businessEndTime = "22:00";

    protected static ObservableList<String> types = observableArrayList();
    protected static ObservableList<Customer> customers = observableArrayList();
    protected static ObservableList<Contact> contacts = observableArrayList();

    protected static ObservableList<String> getTypes()
    {
        if(types.isEmpty())
            types.addAll("Tire Installation", "Tire Rotation", "Tire Repair", "Battery Replacement", "Oil Change");

        return types;
    }

    protected static ObservableList<Customer> getCustomers()
    {
        return customers;
    }
    protected static void setCustomers()
    {
        customers = DBCustomer.getAllCustomers();
    }
    protected static ObservableList<Contact> getContacts()
    {
        return contacts;
    }

    protected static void setContacts()
    {
        contacts = DBAppointment.getAllContacts();
    }
}
