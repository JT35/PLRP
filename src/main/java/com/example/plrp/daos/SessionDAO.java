package com.example.plrp.daos;

import com.example.plrp.pojos.Session;

import java.sql.SQLException;
import java.util.List;

public interface SessionDAO {

    int create(Session session) throws SQLException;
    List<Session> retrieve() throws SQLException;
    Session retrieve(long id) throws SQLException;
    void delete(Session session) throws SQLException;

}
