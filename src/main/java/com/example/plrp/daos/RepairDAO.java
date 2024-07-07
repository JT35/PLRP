package com.example.plrp.daos;

import com.example.plrp.pojos.Repair;

import java.sql.SQLException;
import java.util.List;

public interface RepairDAO {

    int create(Repair repair) throws SQLException;
    List<Repair> retrieve() throws SQLException;
    void delete(Repair repair) throws SQLException;

}
