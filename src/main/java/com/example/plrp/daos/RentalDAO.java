package com.example.plrp.daos;

import com.example.plrp.pojos.Rental;

import java.sql.SQLException;
import java.util.List;

public interface RentalDAO {

    int create(Rental rental) throws SQLException;
    List<Rental> retrieve() throws SQLException;
    void delete(Rental rental) throws SQLException;

}
