package com.example.plrp.pojos;

import com.example.plrp.dtos.RentalDTO;

import java.util.concurrent.atomic.AtomicLong;

public class Rental implements RentalDTO {

    private static final AtomicLong counter = new AtomicLong(0);
    private long id;
    private long sessionId;
    private String name;
    private String tel;

    public Rental() {}

    public Rental(long sessionId, String name, String tel) {
        id = counter.incrementAndGet();
        this.sessionId = sessionId;
        this.name = name;
        this.tel = tel;
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
    public long getSessionId() {
        return sessionId;
    }

    @Override
    public void setSessionId(long sessionId) {
        this.sessionId = sessionId;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getTel() {
        return tel;
    }

    @Override
    public void setTel(String tel) {
        this.tel = tel;
    }

}
