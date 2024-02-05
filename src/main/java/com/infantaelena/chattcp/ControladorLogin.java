package com.infantaelena.chattcp;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class ControladorLogin {
    @FXML
    private TextField nicknameField;

    @FXML
    private Button startButton;

    public ControladorLogin(TextField nicknameField, Button startButton) {
        this.nicknameField = nicknameField;
        this.startButton = startButton;
    }

    public ControladorLogin(){

    }

    public TextField getNicknameField() {
        return nicknameField;
    }

    public void setNicknameField(TextField nicknameField) {
        this.nicknameField = nicknameField;
    }

    public Button getStartButton() {
        return startButton;
    }

    public void setStartButton(Button startButton) {
        this.startButton = startButton;
    }
}
