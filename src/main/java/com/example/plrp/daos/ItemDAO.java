package com.example.plrp.daos;

import com.example.plrp.pojos.Item;

import java.sql.SQLException;
import java.util.List;

public interface ItemDAO {

    int create(Item item) throws SQLException;
    Item retrieve(String id) throws SQLException;
    List<Item> retrieveAll(String query) throws SQLException;
    void update(Item item) throws SQLException;
    void delete(String id) throws SQLException;

}
