package com.infantaelena.chattcp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ControladorLogin {
    @FXML
    private TextField nicknameField;

    @FXML
    private Button startButton;


    private Chat chatServidor;

    public ControladorLogin(TextField nicknameField, Button startButton) {
        setNicknameField(nicknameField);
        this.startButton = startButton;
    }

    @FXML
    private void handleStartButtonAction(ActionEvent event) throws Exception {
        if(nicknameField.getText() != null && !nicknameField.getText().isEmpty()){
        Chat chat = new Chat();
        Stage primaryStage = new Stage();
        chat.start(primaryStage);

        ((Stage) startButton.getScene().getWindow()).close();
        }
    }


    public TextField getNicknameField() {
        return nicknameField;
    }

    public void setNicknameField(TextField nicknameField) {
        if (nicknameField != null){
            this.nicknameField = nicknameField;
        } else throw new RuntimeException("No hay nickname");
    }

    public Button getStartButton() {
        return startButton;
    }

    public void setStartButton(Button startButton) {
        this.startButton = startButton;
    }
    public Chat getChatServidor() {
        return chatServidor;
    }

    public void setChatServidor(Chat chatServidor) {
        this.chatServidor = chatServidor;
    }

    public ControladorLogin() {
    }
    public void initialize() {
        System.out.println("ControladorLogin initialized.");
    }
}
