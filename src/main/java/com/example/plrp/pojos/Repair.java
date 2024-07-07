package com.example.plrp.pojos;

import com.example.plrp.dtos.RepairDTO;
import com.example.plrp.states.RepairState;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.concurrent.atomic.AtomicLong;

public class Repair implements RepairDTO {

    private static final AtomicLong counter = new AtomicLong(0);
    private long id;
    private String itemId;
    private RepairState state;
    private Timestamp timestamp;
    private String requester;
    private String comments;

    public Repair() {
        requester = "Anonymous";
        comments = "None";
    }

    public Repair(Item item) throws SQLException {
        id = counter.incrementAndGet();
        itemId = item.getId();
        state = item.isWorking() ? RepairState.WORKING : RepairState.BROKEN;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String getItemId() {
        return itemId;
    }

    @Override
    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    @Override
    public RepairState getState() {
        return state;
    }

    @Override
    public void setState(RepairState state) {
        this.state = state;
    }

    @Override
    public Timestamp getTimestamp() {
        return timestamp;
    }

    @Override
    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String getRequester() {
        return requester;
    }

    @Override
    public void setRequester(String requester) {
        this.requester = requester;
    }

    @Override
    public String getComments() {
        return comments;
    }

    @Override
    public void setComments(String comments) {
        this.comments = comments;
    }

}
