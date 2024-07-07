package com.example.plrp.pojos;

import com.example.plrp.dtos.ItemDTO;

import java.sql.Timestamp;

public class Item implements ItemDTO {

    private String id;
    private String model;
    private float powerOutput;

    private boolean isSignedOut;
    private boolean isWorking;

    private Timestamp registered;

    public Item() {
        isSignedOut = false;
        isWorking = true;
    }

    public Item(String id, String model, float powerOutput) {
        this.id = id;
        this.model = model;
        this.powerOutput = powerOutput;
        isSignedOut = false;
        isWorking = true;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getModel() {
        return model;
    }

    @Override
    public void setModel(String model) {
        this.model = model;
    }

    @Override
    public float getPowerOutput() {
        return powerOutput;
    }

    @Override
    public void setPowerOutput(float powerOutput) {
        this.powerOutput = powerOutput;
    }

    @Override
    public boolean isSignedOut() {
        return isSignedOut;
    }

    @Override
    public void setSignedOut(boolean signedOut) {
        this.isSignedOut = signedOut;
    }

    @Override
    public boolean isWorking() {
        return isWorking;
    }

    @Override
    public void setWorking(boolean working) {
        this.isWorking = working;
    }

    @Override
    public boolean isAvailable() {
        return !isSignedOut() && isWorking();
    }

    @Override
    public Timestamp getTimeRegistered() {
        return registered;
    }

    @Override
    public void setTimeRegistered(Timestamp timeRegistered) {
        this.registered = timeRegistered;
    }

}
