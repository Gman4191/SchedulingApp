package DAO;

import Models.Customer;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static javafx.collections.FXCollections.observableArrayList;

public class DBCustomer {
    public static ObservableList<Customer> getAllCustomers()
    {
        ObservableList<Customer> customers = observableArrayList();
        String query = "SELECT Customer_ID, Customer_Name, Address, Postal_Code, Phone, Division_ID " +
                       "FROM Customers;";
        ResultSet resultSet;
        try{
            PreparedStatement select = DBConnection.getConnection().prepareStatement(query);
            select.executeQuery();
            resultSet = select.getResultSet();
            while(resultSet.next())
            {
                Customer c = new Customer(resultSet.getInt("Customer_ID"), resultSet.getString("Customer_Name"),
                                          resultSet.getString("Address"), resultSet.getString("Postal_Code"),
                                          resultSet.getString("Phone"), resultSet.getInt("Division_ID"));
                customers.add(c);
            }
        } catch(SQLException e)
        {
            e.printStackTrace();
        }
        return customers;
    }

    public static String getDivision(int divisionId)
    {
        String division = null;
        String query = "SELECT Division FROM first_level_divisions WHERE Division_ID = ?;";
        ResultSet resultSet;

        try{
            PreparedStatement select = DBConnection.getConnection().prepareStatement(query);
            select.setInt(1, divisionId);
            select.executeQuery();
            resultSet = select.getResultSet();

            if(resultSet.next())
                division = resultSet.getString("Division");
        } catch(SQLException e)
        {
            e.printStackTrace();
        }

        return division;
    }
}
