package com.example.plrp;

import com.example.plrp.impl.ItemImpl;
import com.example.plrp.impl.RepairImpl;
import com.example.plrp.pojos.Item;
import com.example.plrp.pojos.Repair;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class RepairController implements Initializable {

    private String scannedBarcode;

    @FXML
    private TextField barcode;
    @FXML
    private TextField submitter;
    @FXML
    private TextArea comments;

    public RepairController(String barcode) {
        this.scannedBarcode = barcode;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        barcode.setText(scannedBarcode);
    }

    @FXML
    protected void onSubmitClick() throws IOException {
        try {
            ItemImpl itemImpl = new ItemImpl();
            Item item = itemImpl.retrieve(scannedBarcode);
            item.setWorking(!item.isWorking());
            itemImpl.update(item);

            RepairImpl repairImpl = new RepairImpl();
            Repair repair = new Repair(item);
            repair.setTimestamp(Timestamp.valueOf(LocalDateTime.now()));
            if (!submitter.getText().isBlank())
                repair.setRequester(submitter.getText());
            if (!comments.getText().isBlank())
                repair.setComments(comments.getText());
            repairImpl.create(repair);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("process-view.fxml"));
            fxmlLoader.setController(new ProcessController(scannedBarcode));
            AirsoftManagementSystem.primaryStage.setScene(new Scene(fxmlLoader.load(), AirsoftManagementSystem.SCR_W, AirsoftManagementSystem.SCR_H));
        }
    }

    @FXML
    protected void onCancelClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("process-view.fxml"));
        fxmlLoader.setController(new ProcessController(scannedBarcode));
        AirsoftManagementSystem.primaryStage.setScene(new Scene(fxmlLoader.load(), AirsoftManagementSystem.SCR_W, AirsoftManagementSystem.SCR_H));
    }

}
