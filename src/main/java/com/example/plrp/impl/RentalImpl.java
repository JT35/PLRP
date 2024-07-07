package com.example.plrp.impl;

import com.example.plrp.daos.RentalDAO;
import com.example.plrp.pojos.Rental;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.example.plrp.AirsoftManagementSystem.conn;

public class RentalImpl implements RentalDAO {

    @Override
    public int create(Rental rental) throws SQLException {
        String query = "INSERT INTO RENTAL (SessionID, Name, PhoneNo) VALUES (?,?,?)";

        PreparedStatement statement = conn.prepareStatement(query);
        statement.setLong(1, rental.getSessionId());
        statement.setString(2, rental.getName());
        statement.setString(3, rental.getTel());

        int n = statement.executeUpdate();
        return n;
    }

    @Override
    public List<Rental> retrieve() throws SQLException {
        String query = "SELECT * FROM RENTAL";
        PreparedStatement statement = conn.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();
        List<Rental> rentals = new ArrayList<>();
        while (resultSet.next()) {
            Rental rental = new Rental();
            rental.setId(resultSet.getLong("RentalID"));
            rental.setSessionId(resultSet.getLong("SessionID"));
            rental.setName(resultSet.getString("Name"));
            rental.setTel(resultSet.getString("PhoneNo"));
            rentals.add(rental);
        }
        return rentals;
    }

    @Override
    public void delete(Rental rental) throws SQLException {
        String query = "DELETE FROM RENTAL WHERE RENTAL.RentalID = ?";
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setLong(1, rental.getId());
        statement.executeUpdate();
    }

}
