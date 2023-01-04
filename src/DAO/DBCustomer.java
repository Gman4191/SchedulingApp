package DAO;

import Models.Country;
import Models.Customer;
import Models.FirstLevelDivision;
import Models.User;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static javafx.collections.FXCollections.observableArrayList;

public class DBCustomer {
    public static ObservableList<Customer> getAllCustomers()
    {
        ObservableList<Customer> customers = observableArrayList();
        String query = "SELECT Customer_ID, Customer_Name, Address, Postal_Code, Phone, c.Division_ID, Country " +
                       "FROM Customers c JOIN first_level_divisions d JOIN Countries country" +
                " ON c.Division_ID = d.Division_ID AND d.Country_ID = country.Country_ID;";
        ResultSet resultSet;
        try{
            PreparedStatement select = DBConnection.getConnection().prepareStatement(query);
            select.executeQuery();
            resultSet = select.getResultSet();
            while(resultSet.next())
            {
                Customer c = new Customer(resultSet.getInt("Customer_ID"), resultSet.getString("Customer_Name"),
                                          resultSet.getString("Address"), resultSet.getString("Postal_Code"),
                                          resultSet.getString("Phone"), resultSet.getInt("Division_ID"),
                                          resultSet.getString("Country"));
                customers.add(c);
            }
        } catch(SQLException e)
        {
            e.printStackTrace();
        }
        return customers;
    }
    public static ObservableList<Country> getAllCountries()
    {
        ObservableList<Country> countries = observableArrayList();
        String query = "SELECT Country_ID, Country FROM Countries;";
        ResultSet resultSet;

        try{
            PreparedStatement select = DBConnection.getConnection().prepareStatement(query);
            select.executeQuery();
            resultSet = select.getResultSet();

            while(resultSet.next())
            {
                countries.add(new Country(resultSet.getInt("Country_ID"),
                        resultSet.getString("Country")));
            }
        } catch(SQLException e)
        {
            e.printStackTrace();
        }

        return countries;
    }

    public static ObservableList<FirstLevelDivision> getDivisions(Country selectedCountry)
    {
        ObservableList<FirstLevelDivision> divisions = observableArrayList();
        String query = "SELECT Division_ID, Division FROM first_level_divisions WHERE Country_ID = ?;";
        ResultSet resultSet;

        try{
            PreparedStatement select = DBConnection.getConnection().prepareStatement(query);
            select.setInt(1, selectedCountry.getId());
            select.executeQuery();
            resultSet = select.getResultSet();

            while(resultSet.next())
            {
                divisions.add(new FirstLevelDivision(resultSet.getInt("Division_ID"),
                        resultSet.getString("Division"), selectedCountry.getId()));
            }
        } catch(SQLException e)
        {
            e.printStackTrace();
        }

        return divisions;
    }

    public static String getDivisionName(int divisionId)
    {
        String divisionName = null;
        String query = "SELECT Division FROM first_level_divisions WHERE Division_ID = ?;";
        ResultSet resultSet;

        try{
            PreparedStatement select = DBConnection.getConnection().prepareStatement(query);
            select.setInt(1, divisionId);
            select.executeQuery();
            resultSet = select.getResultSet();

            if(resultSet.next())
                divisionName = resultSet.getString("Division");
        } catch(SQLException e)
        {
            e.printStackTrace();
        }

        return divisionName;
    }

    public static void addCustomer(Customer newCustomer)
    {
        String query = "INSERT INTO Customers(Customer_ID, Customer_Name, Address, Postal_Code, Phone, Create_Date, " +
                "Created_By, Last_Update, Last_Updated_By, Division_ID)" +
                "VALUES(null, ?, ?, ?, ?, curdate(), ?, curdate(), ?, ?);";
        try{
            PreparedStatement insert = DBConnection.getConnection().prepareStatement(query);
            insert.setString(1, newCustomer.getName());
            insert.setString(2, newCustomer.getAddress());
            insert.setString(3, newCustomer.getPostalCode());
            insert.setString(4, newCustomer.getPhone());
            insert.setString(5, User.getUserName());
            insert.setString(6, User.getUserName());
            insert.setInt(7, newCustomer.getDivisionId());
            insert.execute();
        } catch(SQLException e)
        {
            e.printStackTrace();
        }
    }

    public static void updateCustomer(Customer selectedCustomer)
    {
        String query = "UPDATE Customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?," +
                "Last_Update = CURRENT_TIMESTAMP, Last_Updated_By = ?, Division_ID = ? WHERE Customer_ID = ?;";
        try{
            PreparedStatement update = DBConnection.getConnection().prepareStatement(query);
            update.setString(1, selectedCustomer.getName());
            update.setString(2, selectedCustomer.getAddress());
            update.setString(3, selectedCustomer.getPostalCode());
            update.setString(4, selectedCustomer.getPhone());
            update.setString(5, User.getUserName());
            update.setInt(6, selectedCustomer.getDivisionId());
            update.setInt(7, selectedCustomer.getId());
            update.execute();
        } catch(SQLException e)
        {
            e.printStackTrace();
        }
    }
}
