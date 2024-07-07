package com.example.plrp.impl;

import com.example.plrp.daos.SessionDAO;
import com.example.plrp.pojos.Session;
import com.example.plrp.states.SessionState;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.example.plrp.AirsoftManagementSystem.conn;

public class SessionImpl implements SessionDAO {

    @Override
    public int create(Session session) throws SQLException {
        String query = "INSERT INTO SESSION (ItemID, State, Timestamp) VALUES (?,?,?)";

        PreparedStatement statement = conn.prepareStatement(query);
        statement.setString(1, session.getItemId());
        statement.setString(2, String.valueOf(session.getState()));
        statement.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));

        int n = statement.executeUpdate();
        return n;
    }

    @Override
    public List<Session> retrieve() throws SQLException {
        String query = "SELECT * FROM SESSION";
        PreparedStatement statement = conn.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();
        List<Session> sessions = new ArrayList<>();
        while (resultSet.next()) {
            Session session = new Session();
            session.setId(resultSet.getLong("SessionID"));
            session.setItemId(resultSet.getString("ItemID"));
            session.setState(SessionState.valueOf(resultSet.getString("State")));
            session.setTimestamp(resultSet.getTimestamp("Timestamp"));
            sessions.add(session);
        }
        return sessions;
    }

    @Override
    public Session retrieve(long id) throws SQLException {
        String query = "SELECT * FROM SESSION WHERE SessionID = ?";
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setLong(1, id);

        Session session = new Session();
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            session.setId(resultSet.getLong("SessionID"));
            session.setItemId(resultSet.getString("ItemID"));
            session.setState(SessionState.valueOf(resultSet.getString("State")));
            session.setTimestamp(resultSet.getTimestamp("Timestamp"));
        }
        return session;
    }

    @Override
    public void delete(Session session) throws SQLException {
        String query = "DELETE FROM SESSION WHERE SessionID = ?";
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setLong(1, session.getId());
        statement.executeUpdate();
    }

}
