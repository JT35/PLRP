package com.example.plrp.dtos;

import com.example.plrp.states.SessionState;

import java.sql.Timestamp;

public interface SessionDTO {

    long getId();
    void setId(long id);
    String getItemId();
    void setItemId(String itemId);
    SessionState getState();
    void setState(SessionState state);
    Timestamp getTimestamp();
    void setTimestamp(Timestamp timestamp);

}
