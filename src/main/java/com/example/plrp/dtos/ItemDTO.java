package com.example.plrp.dtos;

import java.sql.Timestamp;

public interface ItemDTO {

    String getId();
    void setId(String id);
    String getModel();
    void setModel(String model);
    float getPowerOutput();
    void setPowerOutput(float powerOutput);
    boolean isSignedOut();
    void setSignedOut(boolean signedOut);
    boolean isWorking();
    void setWorking(boolean working);
    boolean isAvailable();
    Timestamp getTimeRegistered();
    void setTimeRegistered(Timestamp timeRegistered);

}
