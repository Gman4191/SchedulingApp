package DAO;

import Models.Contact;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static javafx.collections.FXCollections.observableArrayList;

public class DBContact {
    /**
     * Get all the contact records in the database
     * @return all contact records
     */
    public static ObservableList<Contact> getAllContacts(){
        ObservableList<Contact> contacts = observableArrayList();
        String query = "SELECT Contact_ID, Contact_Name, Email FROM Contacts;";

        try{
            PreparedStatement select = DBConnection.getConnection().prepareStatement(query);
            select.executeQuery();
            ResultSet resultSet = select.getResultSet();

            while(resultSet.next())
            {
                Contact c = new Contact(resultSet.getInt("Contact_ID"), resultSet.getString("Contact_Name"),
                        resultSet.getString("Email"));
                contacts.add(c);
            }
        } catch(SQLException e)
        {
            e.printStackTrace();
        }

        return contacts;
    }
}
