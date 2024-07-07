package com.example.plrp.impl;

import com.example.plrp.daos.RepairDAO;
import com.example.plrp.pojos.Item;
import com.example.plrp.pojos.Repair;
import com.example.plrp.states.RepairState;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.example.plrp.AirsoftManagementSystem.conn;

public class RepairImpl implements RepairDAO {

    @Override
    public int create(Repair repair) throws SQLException {
        String query = "INSERT INTO REPAIR (ItemID, State, Timestamp, Requester, Comments) VALUES (?,?,?,?,?)";

        ItemImpl itemImpl = new ItemImpl();
        Item findItem = itemImpl.retrieve(repair.getItemId());

        PreparedStatement statement = conn.prepareStatement(query);
        statement.setString(1, repair.getItemId());
        statement.setString(2, String.valueOf(findItem.isWorking() ? RepairState.WORKING : RepairState.BROKEN));
        statement.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
        statement.setString(4, repair.getRequester());
        statement.setString(5, repair.getComments());

        int n = statement.executeUpdate();
        return n;
    }

    @Override
    public List<Repair> retrieve() throws SQLException {
        String query = "SELECT * FROM REPAIR";
        PreparedStatement statement = conn.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();
        List<Repair> repairs = new ArrayList<>();
        while (resultSet.next()) {
            Repair repair = new Repair();
            repair.setId(resultSet.getLong("RepairID"));
            repair.setItemId(resultSet.getString("ItemID"));
            repair.setState(RepairState.valueOf(resultSet.getString("State")));
            repair.setTimestamp(resultSet.getTimestamp("Timestamp"));
            repair.setRequester(resultSet.getString("Requester"));
            repair.setComments(resultSet.getString("Comments"));
            repairs.add(repair);
        }
        return repairs;
    }

    @Override
    public void delete(Repair repair) throws SQLException {
        String query = "DELETE FROM REPAIR WHERE RepairID = ?";
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setLong(1, repair.getId());
        statement.executeUpdate();
    }

}
