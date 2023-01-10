package DAO;

import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;

import static javafx.collections.FXCollections.observableArrayList;

public class DBReport {
    public static ObservableList<String> getAllTypes()
    {
        ObservableList<String> types = observableArrayList();
        String query = "SELECT Type FROM Appointments";
         try{
             PreparedStatement select = DBConnection.getConnection().prepareStatement(query);
             select.executeQuery();
             ResultSet resultSet = select.getResultSet();

             while(resultSet.next())
             {
                 types.add(resultSet.getString("Type"));
             }
         } catch(SQLException e)
         {
             e.printStackTrace();
         }

         return types;
    }

    public static int getAppointmentTotal(String type, Month month) {
        int total = 0;
        String query = "SELECT COUNT(*) as Total FROM Appointments WHERE Type = ? AND MONTH(Start) = ?;";

        try{
            PreparedStatement select = DBConnection.getConnection().prepareStatement(query);
            select.setString(1, type);
            select.setInt(2, month.getValue());
            select.executeQuery();
            ResultSet resultSet = select.getResultSet();

            if(resultSet.next())
                total = resultSet.getInt("Total");
        } catch(SQLException e)
        {
            e.printStackTrace();
        }

        return total;
    }

}
