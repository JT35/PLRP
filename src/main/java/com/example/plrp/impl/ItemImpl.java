package com.example.plrp.impl;

import com.example.plrp.daos.ItemDAO;
import com.example.plrp.pojos.Item;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.example.plrp.AirsoftManagementSystem.conn;

public class ItemImpl implements ItemDAO {

    @Override
    public int create(Item item) throws SQLException {
        String query = "INSERT INTO ITEM (ItemID, Model, PowerOutput, Registered) VALUES (?,?,?,?)";
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setString(1, item.getId());
        statement.setString(2, item.getModel());
        statement.setDouble(3, item.getPowerOutput());
        statement.setTimestamp(4, item.getTimeRegistered());
        int n = statement.executeUpdate();
        return n;
    }

    @Override
    public List<Item> retrieveAll(String query) throws SQLException {
        PreparedStatement statement = conn.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();
        List<Item> items = new ArrayList<>();

        while (resultSet.next()) {
            Item item = new Item();
            item.setId(resultSet.getString("ItemID"));
            item.setModel(resultSet.getString("Model"));
            item.setPowerOutput(resultSet.getFloat("PowerOutput"));
            item.setSignedOut(resultSet.getBoolean("SignedOut"));
            item.setWorking(resultSet.getBoolean("Working"));
            item.setTimeRegistered(resultSet.getTimestamp("Registered"));
            items.add(item);
        }
        return items;
    }

    @Override
    public Item retrieve(String id) throws SQLException {
        String query = "SELECT * FROM ITEM WHERE ItemID = ?";
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setString(1, id);

        Item item = new Item();
        ResultSet resultSet = statement.executeQuery();
        boolean check = false;

        while (resultSet.next()) {
            check = true;
            item.setId(resultSet.getString("ItemID"));
            item.setModel(resultSet.getString("Model"));
            item.setPowerOutput(resultSet.getFloat("PowerOutput"));
            item.setSignedOut(resultSet.getBoolean("SignedOut"));
            item.setWorking(resultSet.getBoolean("Working"));
            item.setTimeRegistered(resultSet.getTimestamp("Registered"));
        }

        return check ? item : null;
    }

    @Override
    public void delete(String id) throws SQLException {
        String query = "DELETE FROM ITEM WHERE ItemID = ?";
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setString(1, id);
        statement.executeUpdate();
    }

    @Override
    public void update(Item item) throws SQLException {
        String query = "UPDATE ITEM SET Model = ?, PowerOutput = ?, SignedOut = ?, Working = ? WHERE ItemID = ?";
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setString(1, item.getModel());
        statement.setFloat(2, item.getPowerOutput());
        statement.setBoolean(3, item.isSignedOut());
        statement.setBoolean(4, item.isWorking());
        statement.setString(5, item.getId());
        statement.executeUpdate();
    }

    // Write functions to return specific queries
    public String baseQuery() {
        return "SELECT * FROM ITEM";
    }
    public String retrieveWithBarcode(String id) {
        return baseQuery() + " WHERE ItemID = '" + id + "'";
    }
    public String retrieveExceptBarcode(String id) {
        return baseQuery() + " WHERE ItemID <> '" + id + "'";
    }
    public String retrieveWithFilter(String id, String condition, boolean state) {
        return baseQuery() + " WHERE ItemID = '" + id + "' AND " + condition + " = " + state;
    }
    public String retrieveExceptFilter(String id, String condition, boolean state) {
        return baseQuery() + " WHERE NOT (ItemID = '" + id + "' AND " + condition + " = " + state + ")";
    }
    public String retrieveWithTwoFilters(String id, String cond1, boolean state1, String cond2, boolean state2) {
        return baseQuery() + " WHERE ItemID = '" + id + "' AND " + cond1 + " = " + state1 + " AND " + cond2 + " = " + state2;
    }
    public String retrieveExceptTwoFilters(String id, String cond1, boolean state1, String cond2, boolean state2) {
        return baseQuery() + " WHERE NOT (ItemID = '" + id + "' AND " + cond1 + " = " + state1 + " AND " + cond2 + " = " + state2 + ")";
    }

}
