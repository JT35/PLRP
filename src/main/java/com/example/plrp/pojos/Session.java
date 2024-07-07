package com.example.plrp.pojos;

import com.example.plrp.dtos.SessionDTO;
import com.example.plrp.states.SessionState;

import java.sql.Timestamp;
import java.util.concurrent.atomic.AtomicLong;

public class Session implements SessionDTO {

    private static final AtomicLong counter = new AtomicLong(0);
    private long id;
    private String itemId;
    private SessionState state;
    private Timestamp timestamp;

    public Session() {}

    public Session(Item item) {
        id = counter.incrementAndGet();
        itemId = item.getId();
        state = !item.isSignedOut() ? SessionState.IN : SessionState.OUT;
    }

    @Override
    public long getId() { return id; }

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
    public SessionState getState() {
        return state;
    }

    @Override
    public void setState(SessionState state) {
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

}
