package com.example.plrp;

import com.example.plrp.impl.ItemImpl;
import com.example.plrp.impl.SessionImpl;
import com.example.plrp.pojos.Item;
import com.example.plrp.pojos.Session;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ResourceBundle;

public class ProcessController implements Initializable {

    private ItemImpl itemImpl = new ItemImpl();
    private SessionImpl sessionImpl = new SessionImpl();

    private String barcode;
    private Item item;

    @FXML
    private TextField barcodePrompt;
    @FXML
    private TextField model;
    @FXML
    private TextField powerOutput;
    @FXML
    private Text signedIn;
    @FXML
    private Text working;
    @FXML
    private Text available;
    @FXML
    private Button signInOutBtn;
    @FXML
    private Button repairRequestBtn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        barcodePrompt.setText(barcode);
        try {
            item = itemImpl.retrieve(barcode);
            model.setText(item.getModel());
            powerOutput.setText(item.getPowerOutput() + "J");
            signedIn.setText(!item.isSignedOut() ? "Signed In" : "Signed Out");
            working.setText(item.isWorking() ? "Working" : "Broken");
            available.setText(item.isAvailable() ? "Available" : "Unavailable");
            signInOutBtn.setText(item.isSignedOut() ? "Sign In" : "Signout");
            repairRequestBtn.setText(item.isWorking() ? "Request Repair" : "Resolve Repair");

            // Display in appropriate colour
            signedIn.setFill(!item.isSignedOut() ? Color.GREEN : Color.RED);
            working.setFill(item.isWorking() ? Color.GREEN : Color.RED);
            available.setFill(item.isAvailable() ? Color.GREEN : Color.RED);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ProcessController(String barcode) {
        this.barcode = barcode;
    }

    @FXML
    protected void onBackClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("main-view.fxml"));
        AirsoftManagementSystem.primaryStage.setScene(new Scene(fxmlLoader.load(), AirsoftManagementSystem.SCR_W, AirsoftManagementSystem.SCR_H));
    }

    @FXML
    protected void onSignInOutClick() throws IOException, SQLException {
        Item currentItem = itemImpl.retrieve(barcode);
        signInOutBtn.setText(currentItem.isSignedOut() ? "Sign In" : "Sign Out");

        if (currentItem.isSignedOut()) {
            currentItem.setSignedOut(!currentItem.isSignedOut());
            itemImpl.update(currentItem);

            Session session = new Session(currentItem);
            session.setTimestamp(Timestamp.valueOf(LocalDateTime.now()));
            sessionImpl.create(session);

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("main-view.fxml"));
            AirsoftManagementSystem.primaryStage.setScene(new Scene(fxmlLoader.load(), AirsoftManagementSystem.SCR_W, AirsoftManagementSystem.SCR_H));
        } else {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("signout-view.fxml"));
            fxmlLoader.setController(new SignoutController(barcode));
            AirsoftManagementSystem.primaryStage.setScene(new Scene(fxmlLoader.load(), AirsoftManagementSystem.SCR_W, AirsoftManagementSystem.SCR_H));
        }
    }

    @FXML
    protected void onRequestRepairClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("repair-view.fxml"));
        fxmlLoader.setController(new RepairController(barcode));
        AirsoftManagementSystem.primaryStage.setScene(new Scene(fxmlLoader.load(), AirsoftManagementSystem.SCR_W, AirsoftManagementSystem.SCR_H));
    }

    @FXML
    protected void onEditClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("register-or-update-view.fxml"));
        fxmlLoader.setController(new RegisterOrUpdateController(barcode, false));
        AirsoftManagementSystem.primaryStage.setScene(new Scene(fxmlLoader.load(), AirsoftManagementSystem.SCR_W, AirsoftManagementSystem.SCR_H));
    }

    @FXML
    protected void onUnregisterClick() throws IOException {
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION, "Unregister this item?");
        Optional<ButtonType> result = confirmation.showAndWait();
        if (result.get() == ButtonType.OK) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("main-view.fxml"));
            AirsoftManagementSystem.primaryStage.setScene(new Scene(fxmlLoader.load(), AirsoftManagementSystem.SCR_W, AirsoftManagementSystem.SCR_H));

            try {
                itemImpl.delete(barcode);
                Alert message = new Alert(Alert.AlertType.INFORMATION, "Successfully deleted item from database.");
                message.show();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else if (result.get() == ButtonType.CANCEL) {
            confirmation.close();
        }
    }

}