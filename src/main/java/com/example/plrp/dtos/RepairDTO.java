package com.example.plrp.dtos;

import com.example.plrp.states.RepairState;

import java.sql.Timestamp;

public interface RepairDTO {

    long getId();
    void setId(long id);
    String getItemId();
    void setItemId(String itemId);
    RepairState getState();
    void setState(RepairState state);
    Timestamp getTimestamp();
    void setTimestamp(Timestamp timestamp);
    String getRequester();
    void setRequester(String requester);
    String getComments();
    void setComments(String comments);

}
